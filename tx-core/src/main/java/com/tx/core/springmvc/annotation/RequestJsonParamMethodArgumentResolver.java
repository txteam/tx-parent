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
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSONObject;

/**
 * JsonParam解析<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月29日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RequestJsonParamMethodArgumentResolver
        implements HandlerMethodArgumentResolver {
    
    /**
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return (parameter.hasParameterAnnotation(RequestJsonParam.class)
                && !BeanUtils.isSimpleProperty(parameter.getParameterType()));
    }
    
    /**
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
        String parameterValue = webRequest.getParameter(name);
        
        //解析对象
        Object attribute = JSONObject.parseObject(parameterValue,
                parameter.getParameterType());
        if (attribute == null) {
            return null;
        }
        
        //创建WebDataBinder
        WebDataBinder binder = binderFactory.createBinder(webRequest,
                attribute,
                name);
        if (binder.getTarget() != null) {
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
     * Derive the model attribute name for the given method parameter based on
     * a {@code @ModelAttribute} parameter annotation (if present) or falling
     * back on parameter type based conventions.
     * @param parameter a descriptor for the method parameter
     * @return the derived name
     * @see Conventions#getVariableNameForParameter(MethodParameter)
     */
    private static String getNameForParameter(MethodParameter parameter) {
        RequestJsonParam ann = parameter
                .getParameterAnnotation(RequestJsonParam.class);
        
        String name = (ann != null ? ann.value() : null);
        return (StringUtils.hasText(name) ? name
                : Conventions.getVariableNameForParameter(parameter));
    }
    
}
