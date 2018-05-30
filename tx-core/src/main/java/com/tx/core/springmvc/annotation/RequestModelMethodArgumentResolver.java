/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月29日
 * <修改描述:>
 */
package com.tx.core.springmvc.annotation;

import java.lang.annotation.Annotation;

import org.springframework.beans.BeanUtils;
import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.bind.support.WebRequestDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * RquestMode<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月29日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RequestModelMethodArgumentResolver
        implements HandlerMethodArgumentResolver {
    
    /**
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //非简单对象，并且RequestModelParam.value,name 不为空
        return (parameter.hasParameterAnnotation(RequestModelParam.class)
                && !BeanUtils.isSimpleProperty(parameter.getParameterType()));
    }
    
    /**
     * 解析参数<br/>
     * 
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter,
            ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {
        //获取参数名
        String name = getNameForParameter(parameter);
        
        //设置参数，如果不存在，则自动创建该对象
        Object attribute = createAttribute(name,
                parameter,
                binderFactory,
                webRequest);
        
        //创建WebDataBinder
        //WebDataBinder binder = new RequestModelDataBinder(name, attribute,name);
        WebDataBinder binder = binderFactory.createBinder(webRequest,
                attribute,
                name);
        binder.setFieldDefaultPrefix((name != null ? name + "." : null));
        
        if (binder.getTarget() != null) {
            //写入参数
            bindRequestParameters(binder, webRequest);
            
            //验证参数合法性
            validateIfApplicable(binder, parameter);
            if (binder.getBindingResult().hasErrors()
                    && isBindExceptionRequired(binder, parameter)) {
                throw new BindException(binder.getBindingResult());
            }
        }
        
        return binder.convertIfNecessary(binder.getTarget(),
                parameter.getParameterType(),
                parameter);
    }
    
    /**
     * Whether to raise a fatal bind exception on validation errors.
     * 
     * @param binder the data binder used to perform data binding
     * @param parameter the method parameter declaration
     * @return {@code true} if the next method parameter is not of type {@link Errors}
     */
    private boolean isBindExceptionRequired(WebDataBinder binder,
            MethodParameter parameter) {
        int i = parameter.getParameterIndex();
        Class<?>[] paramTypes = parameter.getMethod().getParameterTypes();
        boolean hasBindingResult = (paramTypes.length > (i + 1)
                && Errors.class.isAssignableFrom(paramTypes[i + 1]));
        
        return !hasBindingResult;
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
    private void validateIfApplicable(WebDataBinder binder,
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
     * Extension point to bind the request to the target object.
     * 
     * @param binder the data binder instance to use for the binding
     * @param request the current request
     */
    private void bindRequestParameters(WebDataBinder binder,
            NativeWebRequest request) {
        ((WebRequestDataBinder) binder).bind(request);
    }
    
    /**
     * Extension point to create the model attribute if not found in the model.
     * The default implementation uses the default constructor.
     * 
     * @param attributeName the name of the attribute (never {@code null})
     * @param parameter the method parameter
     * @param binderFactory for creating WebDataBinder instance
     * @param webRequest the current request
     * @return the created model attribute (never {@code null})
     */
    private Object createAttribute(String attributeName,
            MethodParameter parameter, WebDataBinderFactory binderFactory,
            NativeWebRequest webRequest) throws Exception {
        
        return BeanUtils.instantiateClass(parameter.getParameterType());
    }
    
    /**
     * Derive the model attribute name for the given method parameter based on
     * a {@code @ModelAttribute} parameter annotation (if present) or falling
     * back on parameter type based conventions.
     * 
     * @param parameter a descriptor for the method parameter
     * @return the derived name
     * @see Conventions#getVariableNameForParameter(MethodParameter)
     */
    private static String getNameForParameter(MethodParameter parameter) {
        RequestModelParam ann = parameter
                .getParameterAnnotation(RequestModelParam.class);
        String name = (ann != null ? ann.value() : null);
        return (StringUtils.hasText(name) ? name
                : Conventions.getVariableNameForParameter(parameter));
    }
}
