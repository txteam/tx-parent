/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月22日
 * <修改描述:>
 */
package com.tx.component.event.exceptions;

import com.tx.core.exceptions.SILException;

/**
 * 事件监听器访问异常<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EventListenerAccessException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -2624829505522013719L;

    /**
     * @return
     */
    @Override
    protected Integer errorCode() {
        return super.errorCode();
    }

    /**
     * @return
     */
    @Override
    protected String errorMessage() {
        return super.errorMessage();
    }

    /** <默认构造函数> */
    public EventListenerAccessException() {
        super();
    }

    /** <默认构造函数> */
    public EventListenerAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    /** <默认构造函数> */
    public EventListenerAccessException(String message) {
        super(message);
    }
}
