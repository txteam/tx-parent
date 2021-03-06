/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月20日
 * <修改描述:>
 */
package com.tx.core.method.resolver.impl;

import java.lang.annotation.Annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.DataBinder;
import org.springframework.validation.annotation.Validated;

import com.tx.core.method.annotation.MethodModelAttribute;
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
public class MethodModelAttributeMethodArgumentResolver
        implements MethodArgumentResolver {
    
    /** 日志记录器 */
    protected final Log logger = LogFactory.getLog(getClass());
    
    /**
     * Class constructor.
     * @param annotationNotRequired if "true", non-simple method arguments and
     * return values are considered model attributes with or without a
     * {@code @ModelAttribute} annotation
     */
    public MethodModelAttributeMethodArgumentResolver() {
    }
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
    
    /**
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return (parameter.hasParameterAnnotation(MethodModelAttribute.class)
                || (!parameter.hasParameterAnnotations() && !BeanUtils
                        .isSimpleProperty(parameter.getParameterType())));
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
        binder.bind(new MutablePropertyValues(request.getParameterMap()));
        
        if (binder.getTarget() != null) {
            validateIfApplicable(binder, parameter);
            if (binder.getBindingResult().hasErrors()   ) {
                throw new MethodArgResolveBindException(binder.getBindingResult());
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
        MethodModelAttribute ann = parameter
                .getParameterAnnotation(MethodModelAttribute.class);
        String name = (ann != null ? ann.value() : null);
        return (StringUtils.hasText(name) ? name
                : Conventions.getVariableNameForParameter(parameter));
    }
    
}
