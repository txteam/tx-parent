/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月20日
 * <修改描述:>
 */
package com.tx.core.method.resolver.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import com.tx.core.method.annotation.MethodParam;
import com.tx.core.method.request.InvokeRequest;
import com.tx.core.method.resolver.MethodArgumentResolver;

/**
 * 参数具@MethodParam注解的方法参数解析器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MethodParamMapMethodArgumentResolver
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
        MethodParam requestParam = parameter
                .getParameterAnnotation(MethodParam.class);
        if (requestParam != null) {
            if (Map.class.isAssignableFrom(parameter.getParameterType())) {
                return !StringUtils.hasText(requestParam.name());
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
        Class<?> paramType = parameter.getParameterType();
        
        Map<String, Object[]> parameterMap = request.getParameterMap();
        
        if (MultiValueMap.class.isAssignableFrom(paramType)) {
            MultiValueMap<String, Object> result = new LinkedMultiValueMap<String, Object>(
                    parameterMap.size());
            for (Map.Entry<String, Object[]> entry : parameterMap.entrySet()) {
                for (Object value : entry.getValue()) {
                    result.add(entry.getKey(), value);
                }
            }
            return result;
        } else {
            Map<String, Object> result = new LinkedHashMap<String, Object>(
                    parameterMap.size());
            for (Map.Entry<String, Object[]> entry : parameterMap.entrySet()) {
                if (entry.getValue().length > 0) {
                    result.put(entry.getKey(), entry.getValue()[0]);
                }
            }
            return result;
        }
    }
    
}
