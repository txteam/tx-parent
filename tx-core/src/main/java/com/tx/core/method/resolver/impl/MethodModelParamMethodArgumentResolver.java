/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月20日
 * <修改描述:>
 */
package com.tx.core.method.resolver.impl;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.DataBinder;
import org.springframework.validation.annotation.Validated;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.method.annotation.MethodModelParam;
import com.tx.core.method.exceptions.MethodArgResolveBindException;
import com.tx.core.method.request.InvokeRequest;
import com.tx.core.method.resolver.MethodArgumentResolver;

/**
 * 参数具@EventListenerParam注解的方法参数解析器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MethodModelParamMethodArgumentResolver
        implements MethodArgumentResolver {
    
    /** 日志记录器 */
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * Class constructor.
     * @param annotationNotRequired if "true", non-simple method arguments and
     * return values are considered model attributes with or without a
     * {@code @ModelAttribute} annotation
     */
    public MethodModelParamMethodArgumentResolver() {
    }
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
    
    /**
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return (parameter.hasParameterAnnotation(MethodModelParam.class)
                && !BeanUtils.isSimpleProperty(parameter.getParameterType()));
    }
    
    /**
     * @param parameter
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter,
            InvokeRequest request) throws Exception {
        //获取参数名
        String name = getNameForParameter(parameter);
        
        //创建对象实体
        Object attribute = createAttribute(name, parameter, request);
        
        DataBinder binder = new DataBinder(attribute, name);
        binder.bind(new MutablePropertyValues(getParametersStartingWith(request, name + ".")));
        
        if (binder.getTarget() != null) {
            validateIfApplicable(binder, parameter);
            if (binder.getBindingResult().hasErrors()) {
                throw new MethodArgResolveBindException(
                        binder.getBindingResult());
            }
        }
        
        return binder.convertIfNecessary(binder.getTarget(),
                parameter.getParameterType(),
                parameter);
    }
    
    /**
     * Validate the model attribute if applicable.
     * <p>The default implementation checks for {@code @javax.validation.Valid},
     * Spring's {@link org.springframework.validation.annotation.Validated},
     * and custom annotations whose name starts with "Valid".
     * 
     * @param binder the DataBinder to be used
     * @param parameter the method parameter declaration
     */
    private void validateIfApplicable(DataBinder binder,
            MethodParameter parameter) {
        Annotation[] annotations = parameter.getParameterAnnotations();
        for (Annotation ann : annotations) {
            Validated validatedAnn = AnnotationUtils.getAnnotation(ann,
                    Validated.class);
            if (validatedAnn != null || ann.annotationType()
                    .getSimpleName()
                    .startsWith("Valid")) {
                Object hints = (validatedAnn != null ? validatedAnn.value()
                        : AnnotationUtils.getValue(ann));
                Object[] validationHints = (hints instanceof Object[]
                        ? (Object[]) hints : new Object[] { hints });
                binder.validate(validationHints);
                
                break;
            }
        }
    }
    
    /**
     * Extension point to create the model attribute if not found in the model.
     * The default implementation uses the default constructor.
     * 
     * @param attributeName the name of the attribute (never {@code null})
     * @param parameter the method parameter
     * @param invokeRequest the current request
     * @return the created model attribute (never {@code null})
     */
    private Object createAttribute(String attributeName,
            MethodParameter parameter, InvokeRequest invokeRequest)
            throws Exception {
        
        return BeanUtils.instantiateClass(parameter.getParameterType());
    }
    
    /**
     * Derive the model attribute name for the given method parameter based on
     * a {@code @ModelAttribute} parameter annotation (if present) or falling
     * back on parameter type based conventions.
     * 
     * @param parameter a descriptor for the method parameter
     * @return the derived name
     */
    private static String getNameForParameter(MethodParameter parameter) {
        MethodModelParam ann = parameter
                .getParameterAnnotation(MethodModelParam.class);
        String name = (ann != null ? ann.value() : null);
        return (StringUtils.hasText(name) ? name
                : Conventions.getVariableNameForParameter(parameter));
    }
    
    private static Map<String, Object[]> getParametersStartingWith(
            InvokeRequest request, String prefix) {
        AssertUtils.notNull(request, "Request must not be null");
        
        Iterator<String> paramNames = request.getParameterNames();
        Map<String, Object[]> params = new TreeMap<String, Object[]>();
        if (prefix == null) {
            prefix = "";
        }
        while (paramNames != null && paramNames.hasNext()) {
            String paramName = paramNames.next();
            
            if ("".equals(prefix) || paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                
                Object[] values = request.getParameterValues(paramName);
                if (values == null || values.length == 0) {
                    continue;
                } 
                
                params.put(unprefixed, values);
            }
        }
        return params;
    }
    
}
