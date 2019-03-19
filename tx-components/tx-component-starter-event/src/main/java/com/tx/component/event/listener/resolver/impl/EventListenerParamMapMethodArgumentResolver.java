/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月20日
 * <修改描述:>
 */
package com.tx.component.event.listener.resolver.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

import com.tx.component.event.annotation.EventListenerParam;
import com.tx.component.event.event.Event;
import com.tx.component.event.listener.resolver.EventListenerMethodArgumentResolver;

/**
 * 参数具@EventListenerParam注解的方法参数解析器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EventListenerParamMapMethodArgumentResolver implements
        EventListenerMethodArgumentResolver {
    
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
        EventListenerParam requestParamAnnot = parameter.getParameterAnnotation(EventListenerParam.class);
        if (requestParamAnnot != null) {
            if (Map.class.isAssignableFrom(parameter.getParameterType())) {
                return !StringUtils.hasText(requestParamAnnot.value());
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
    public Object resolveArgument(MethodParameter parameter, Event event,
            Map<String, Object> params) throws Exception {
        Map<String, Object> result = new LinkedHashMap<String, Object>(
                params.size());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() != null) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }
    
}
