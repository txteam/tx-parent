/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月5日
 * <修改描述:>
 */
package com.tx.component.event.context;

import java.util.Map;

import com.tx.component.event.event.Event;
import com.tx.component.event.event.EventCallbackHandler;
import com.tx.component.event.event.impl.EventImpl;
import com.tx.component.event.listener.EventListener;
import com.tx.component.event.listener.EventListenerHandler;
import com.tx.component.event.listener.EventListenerScopeEnum;
import com.tx.component.event.listener.impl.SimpleEventListener;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 事件容器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EventContext extends EventContextBuilder {
    
    /** 事件容器唯一句柄 */
    private static EventContext context;
    
    /**
      * 事件容器<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return EventContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static EventContext getContext() {
        AssertUtils.notNull(context, "context is null.not init.");
        
        EventContext res = context;
        return res;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        
        EventContext.context = this;
    }
    
    /**
      * 获取当前线程的事件会话实例<br/> 
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return EventSessionContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public EventSessionContext getSession() {
        EventSessionContext session = EventSessionContext.getSession();
        return session;
    }
    
    /**
     * 触发指定的事件类型<br>
     *     传入的事件类型以及Map会组成一个默认的事件对象<br/>
     *<功能详细描述>
     * @param eventType
     * @param params [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void trigger(String eventType) {
        trigger(eventType, null, null);
    }
    
    /**
      * 触发指定的事件类型<br>
      *     传入的事件类型以及Map会组成一个默认的事件对象<br/>
      *<功能详细描述>
      * @param eventType
      * @param params [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void trigger(String eventType, Map<String, Object> params) {
        trigger(eventType, params, null);
    }
    
    /**
      * 触发指定的事件类型<br/>
      *     传入的事件类型以及Map会组成一个默认的事件对象<br/>
      * @param eventType
      * @param params
      * @param callback [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void trigger(String eventType, Map<String, Object> params,
            EventCallbackHandler callback) {
        AssertUtils.notEmpty(eventType, "eventType is empty.");
        Event event = new EventImpl(eventType, callback);
        
        doTrigger(event, params);
    }
    
    /**
     * 触发执行事件类型<br/> 
     *<功能详细描述>
     * @param event
     * @param params [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void trigger(Event event) {
        trigger(event, null);
    }
    
    /**
      * 触发执行事件类型<br/> 
      *<功能详细描述>
      * @param event
      * @param params [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void trigger(Event event, Map<String, Object> params) {
        AssertUtils.notNull(event, "event is empty.");
        AssertUtils.notEmpty(event.type(), "event.type is empty.");
        
        doTrigger(event, params);
    }
    
    /**
      * 添加一个事件监听器<br/> 
      *<功能详细描述>
      * @param eventListener [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void bind(EventListener eventListener) {
        AssertUtils.notNull(eventListener, "event is empty.");
        AssertUtils.notEmpty(eventListener.eventType(),
                "event.eventType is empty.");
        AssertUtils.notNull(eventListener.scope(), "event.scope is empty.");
        
        doBind(eventListener);
    }
    
    /**
      * 添加一个事件监听器<br/> 
      *<功能详细描述>
      * @param eventType
      * @param handler [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void bind(String eventType, EventListenerHandler handler) {
        AssertUtils.notNull(handler, "handler is empty.");
        AssertUtils.notEmpty(eventType, "eventType is empty.");
        
        EventListener eventListener = new SimpleEventListener(eventType,
                handler);
        doBind(eventListener);
    }
    
    /**
      * 添加一个事件监听器<br/> 
      *<功能详细描述>
      * @param eventType
      * @param scope
      * @param handler [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void bind(String eventType, EventListenerScopeEnum scope,
            EventListenerHandler handler) {
        AssertUtils.notNull(handler, "handler is empty.");
        AssertUtils.notEmpty(eventType, "eventType is empty.");
        
        EventListener eventListener = new SimpleEventListener(eventType, scope,
                handler);
        doBind(eventListener);
    }
}
