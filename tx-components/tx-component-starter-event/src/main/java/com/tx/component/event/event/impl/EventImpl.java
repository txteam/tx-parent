/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月20日
 * <修改描述:>
 */
package com.tx.component.event.event.impl;

import com.tx.component.event.event.EventCallbackHandler;

/**
 * 事件的简单实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EventImpl extends AbstractEvent {
    
    /** <默认构造函数> */
    public EventImpl() {
        super();
    }
    
    /** <默认构造函数> */
    public EventImpl(String type) {
        super(type);
    }
    
    /** <默认构造函数> */
    public EventImpl(String type, EventCallbackHandler callback) {
        super(type, callback);
    }
}
