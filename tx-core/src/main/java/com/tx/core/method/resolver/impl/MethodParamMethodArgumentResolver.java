/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月20日
 * <修改描述:>
 */
package com.tx.core.method.resolver.impl;

import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;

import com.tx.core.method.annotation.MethodParam;
import com.tx.core.method.request.InvokeRequest;

/**
 * 参数具@EventListenerParam注解的方法参数解析器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MethodParamMethodArgumentResolver
        extends AbstractNamedValueMethodArgumentResolver {
    
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
        if (parameter.hasParameterAnnotation(MethodParam.class)) {
            if (Map.class.isAssignableFrom(
                    parameter.nestedIfOptional().getNestedParameterType())) {
                String paramName = parameter
                        .getParameterAnnotation(MethodParam.class).name();
                return StringUtils.hasText(paramName);
            } else {
                return true;
            }
        } else {
            parameter = parameter.nestedIfOptional();
            return (!parameter.hasParameterAnnotations() && BeanUtils
                    .isSimpleProperty(parameter.getNestedParameterType()));
        }
    }
    
    /**
     * @param name
     * @param parameter
     * @param request
     * @return
     */
    @Override
    protected Object resolveName(String name, MethodParameter parameter,
            InvokeRequest request) {
        Object arg = null;
        
        Object[] paramValues = request.getParameterValues(name);
        if (paramValues != null) {
            arg = (paramValues.length == 1 ? paramValues[0] : paramValues);
        }
        
        return arg;
    }
    
    /**
     * Create the {@link NamedValueInfo} object for the given method parameter. Implementations typically
     * retrieve the method annotation by means of {@link MethodParameter#getParameterAnnotation(Class)}.
     * 
     * @param parameter the method parameter
     * @return the named value information
     */
    protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        MethodParam ann = parameter.getParameterAnnotation(MethodParam.class);
        
        return (ann != null ? new MethodParamNamedValueInfo(ann)
                : new MethodParamNamedValueInfo());
    }
    
    protected static class MethodParamNamedValueInfo extends NamedValueInfo {
        /** <默认构造函数> */
        public MethodParamNamedValueInfo() {
            super("", false);
        }
        
        /** <默认构造函数> */
        public MethodParamNamedValueInfo(MethodParam annotation) {
            super(annotation.name(), annotation.required());
        }
    }
}
