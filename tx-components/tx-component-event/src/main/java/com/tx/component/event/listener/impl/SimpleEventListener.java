/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月21日
 * <修改描述:>
 */
package com.tx.component.event.listener.impl;

import java.util.Map;

import com.tx.component.event.event.Event;
import com.tx.component.event.listener.EventListener;
import com.tx.component.event.listener.EventListenerHandler;
import com.tx.component.event.listener.EventListenerScopeEnum;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * EventListener的实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SimpleEventListener implements EventListener {
    
    /** 事件类型 */
    private String eventType;
    
    /** 事件的类型 */
    private EventListenerScopeEnum scope;
    
    /** 事件监听处理器 */
    private EventListenerHandler handler;
    
    /** order的优先级 */
    private int order = 0;
    
    /** <默认构造函数> */
    public SimpleEventListener() {
        super();
    }
    
    /** <默认构造函数> */
    public SimpleEventListener(String eventType, EventListenerScopeEnum scope,
            EventListenerHandler handler) {
        super();
        AssertUtils.notEmpty(eventType,"eventType is empty.");
        AssertUtils.notNull(scope,"scope is null.");
        AssertUtils.notNull(handler,"handler is null.");
        
        this.eventType = eventType;
        this.scope = scope;
        this.handler = handler;
    }

    /** <默认构造函数> */
    public SimpleEventListener(String eventType, EventListenerHandler handler) {
        super();
        AssertUtils.notEmpty(eventType,"eventType is empty.");
        AssertUtils.notNull(handler,"handler is null.");
        
        this.eventType = eventType;
        this.handler = handler;
        this.scope = EventListenerScopeEnum.AROUND;
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
    public String eventType() {
        return this.eventType;
    }
    
    /**
     * @return
     */
    @Override
    public EventListenerScopeEnum scope() {
        return this.scope;
    }
    
    /**
     * @param event
     * @param params
     */
    @Override
    public void execute(Event event, Map<String, Object> params) {
        this.handler.handle(event, params);
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
     * @param 对handler进行赋值
     */
    public void setHandler(EventListenerHandler handler) {
        this.handler = handler;
    }

    /**
     * @param 对order进行赋值
     */
    public void setOrder(int order) {
        this.order = order;
    }
}
