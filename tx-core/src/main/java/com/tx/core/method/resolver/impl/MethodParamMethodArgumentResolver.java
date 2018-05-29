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
public class MethodParamMethodArgumentResolver
        implements MethodArgumentResolver {
    
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
            if (Map.class.isAssignableFrom(parameter.nestedIfOptional().getNestedParameterType())) {
                String paramName = parameter.getParameterAnnotation(MethodParam.class).name();
                return StringUtils.hasText(paramName);
            }else {
                return true;
            }
        }else{
            parameter = parameter.nestedIfOptional();
            return BeanUtils.isSimpleProperty(parameter.getNestedParameterType());
        }
    }

    /**
     * @param methodParameter
     * @param invokeRequest
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
            InvokeRequest invokeRequest) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
}
