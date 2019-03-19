/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月19日
 * <修改描述:>
 */
package com.tx.component.event.listener.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;

import com.tx.component.event.event.Event;
import com.tx.component.event.exceptions.EventListenerAccessException;
import com.tx.component.event.listener.EventListener;
import com.tx.component.event.listener.EventListenerScopeEnum;
import com.tx.component.event.listener.resolver.EventListenerMethodArgumentResolver;
import com.tx.component.event.listener.resolver.EventListenerMethodArgumentResolverComposite;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.MessageUtils;

/**
 * HandlerMethod的事件监听器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class HandlerMethodEventListener implements EventListener {
    
    /** 事件类型 */
    private String eventType;
    
    /** 事件的类型 */
    private EventListenerScopeEnum scope;
    
    /** 处理方法句柄 */
    private HandlerMethod handlerMethod;
    
    /** 排序值 */
    private int order = 0;
    
    /** 事件监听器的参数解析器 */
    private EventListenerMethodArgumentResolverComposite resolver = null;
    
    /** <默认构造函数> */
    public HandlerMethodEventListener(String eventType,
            HandlerMethod handlerMethod,
            List<EventListenerMethodArgumentResolver> resolvers) {
        super();
        AssertUtils.notEmpty(eventType, "eventType is empty.");
        AssertUtils.notNull(handlerMethod, "handlerMethod is null.");
        
        this.eventType = eventType;
        this.handlerMethod = handlerMethod;
        this.scope = EventListenerScopeEnum.AROUND;
        
        this.resolver = new EventListenerMethodArgumentResolverComposite();
        this.resolver.addResolvers(resolvers);
    }
    
    /** <默认构造函数> */
    public HandlerMethodEventListener(String eventType,
            EventListenerScopeEnum scope, HandlerMethod handlerMethod,
            List<EventListenerMethodArgumentResolver> resolvers) {
        super();
        AssertUtils.notEmpty(eventType, "eventType is empty.");
        AssertUtils.notNull(handlerMethod, "handlerMethod is null.");
        AssertUtils.notNull(scope, "scope is null.");
        
        this.eventType = eventType;
        this.scope = scope;
        this.handlerMethod = handlerMethod;
        
        this.resolver = new EventListenerMethodArgumentResolverComposite();
        this.resolver.addResolvers(resolvers);
    }
    
    /** <默认构造函数> */
    public HandlerMethodEventListener(String eventType,
            EventListenerScopeEnum scope, HandlerMethod handlerMethod,
            List<EventListenerMethodArgumentResolver> resolvers, int order) {
        super();
        AssertUtils.notEmpty(eventType, "eventType is empty.");
        AssertUtils.notNull(handlerMethod, "handlerMethod is null.");
        AssertUtils.notNull(scope, "scope is null.");
        
        this.eventType = eventType;
        this.scope = scope;
        this.handlerMethod = handlerMethod;
        this.order = order;
        
        this.resolver = new EventListenerMethodArgumentResolverComposite();
        this.resolver.addResolvers(resolvers);
    }
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return this.order;
    }
    
    /**
     * @return
     */
    @Override
    public EventListenerScopeEnum scope() {
        return this.scope;
    }
    
    /**
     * @return
     */
    @Override
    public String eventType() {
        return this.eventType;
    }
    
    /**
     * @param event
     * @param params
     */
    @Override
    public void execute(Event event, Map<String, Object> params) {
        MethodParameter[] mps = this.handlerMethod.getMethodParameters();
        Object[] args = new Object[mps.length];
        Object bean = this.handlerMethod.getBean();
        Method method = this.handlerMethod.getMethod();
        
        int i = 0;
        for (MethodParameter methodParameterTemp : mps) {
            if (this.resolver.supportsParameter(methodParameterTemp)) {
                try {
                    args[i++] = this.resolver.resolveArgument(methodParameterTemp,
                            event,
                            params);
                } catch (EventListenerAccessException e) {
                    throw e;
                } catch (Exception e) {
                    throw new EventListenerAccessException(
                            MessageUtils.format("EventListener(bean:{} method:{}) resolveArgument exception.parameterIndex:{},exception:{}",
                            new Object[] { bean.getClass(), method,
                                    methodParameterTemp.getParameterIndex(), e }),
                            e
                            );
                }
            } else {
                args[i++] = null;
            }
        }
        try {
            method.invoke(bean, args);
        } catch (IllegalAccessException | IllegalArgumentException |InvocationTargetException e) {
            throw new EventListenerAccessException(
                    MessageUtils.format("EventListener(bean:{} method:{}) invoke exception.",
                            new Object[] { bean.getClass(), method })
                    ,e
                    );
        } 
    }
    
    /**
     * @param 对eventType进行赋值
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    
    /**
     * @param 对scope进行赋值
     */
    public void setScope(EventListenerScopeEnum scope) {
        this.scope = scope;
    }
    
    /**
     * @param 对handlerMethod进行赋值
     */
    public void setHandlerMethod(HandlerMethod handlerMethod) {
        this.handlerMethod = handlerMethod;
    }
    
    /**
     * @param 对order进行赋值
     */
    public void setOrder(int order) {
        this.order = order;
    }
}
