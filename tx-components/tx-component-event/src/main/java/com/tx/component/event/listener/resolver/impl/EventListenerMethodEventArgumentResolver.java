/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月22日
 * <修改描述:>
 */
package com.tx.component.event.listener.resolver.impl;

import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;

import com.tx.component.event.event.Event;
import com.tx.component.event.listener.resolver.EventListenerMethodArgumentResolver;

/**
 * 事件类型参数的解析器
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EventListenerMethodEventArgumentResolver implements
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
        Class<?> paramType = parameter.getParameterType();
        if (Event.class.isAssignableFrom(paramType)) {
            return true;
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
        Class<?> paramType = parameter.getParameterType();
        Object arg = null;
        if (paramType.isInstance(event)) {
            arg = event;
        }
        return arg;
    }
    
}
