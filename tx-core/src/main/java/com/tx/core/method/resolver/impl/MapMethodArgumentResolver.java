/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月20日
 * <修改描述:>
 */
package com.tx.core.method.resolver.impl;

import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import com.tx.core.method.annotations.MethodParam;
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
public class MapMethodArgumentResolver implements MethodArgumentResolver {
    
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
        MethodParam methodParamAnnotation = parameter
                .getParameterAnnotation(MethodParam.class);
        if (methodParamAnnotation != null) {
            if (Map.class.isAssignableFrom(parameter.getParameterType())) {
                return !StringUtils.hasText(methodParamAnnotation.value());
            }
        }
        return false;
    }
    
    /**
     * @param parameter
     * @param event
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter,
            InvokeRequest request) throws Exception {
        if (MultiValueMap.class
                .isAssignableFrom(parameter.getParameterType())) {
            return request.getParameterMultiValueMap();
        } else {
            return request.getParameterMap();
        }
    }
    
}
