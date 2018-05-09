/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月20日
 * <修改描述:>
 */
package com.tx.component.event.event.impl;

import java.util.Map;

import com.tx.component.event.event.Event;
import com.tx.component.event.event.EventCallbackHandler;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 事件的简单实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractEvent implements Event {
    
    /** 事件类型名 */
    private String type;
    
    /** 事件回调方法 */
    private EventCallbackHandler callback;
    
    /** <默认构造函数> */
    public AbstractEvent() {
        super();
        this.type = this.getClass().getName();
    }
    
    /** <默认构造函数> */
    public AbstractEvent(String type) {
        super();
        this.type = type;
    }
    
    /** <默认构造函数> */
    public AbstractEvent(String type, EventCallbackHandler callback) {
        super();
        this.type = type;
        this.callback = callback;
    }
    
    /**
     * @return
     */
    @Override
    public String type() {
        return type;
    }
    
    /**
     * @param callback
     */
    @Override
    public void register(EventCallbackHandler callback) {
        AssertUtils.notNull(callback, "callback is null.");
        
        this.callback = callback;
    }
    
    /**
     * @param params
     */
    @Override
    public void callback(Map<String, Object> params) {
        if (this.callback != null) {
            this.callback.handle(params);
        }
    }
}
