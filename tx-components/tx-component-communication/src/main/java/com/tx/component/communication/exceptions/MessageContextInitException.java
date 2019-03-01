/*
 * 描          述:  <描述>
 * 修  改   人:  Rain.he
 * 修改时间:  2015年3月30日
 * <修改描述:>
 */
package com.tx.component.communication.exceptions;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILException;

/**
 * 消息发送器容器初始化异常<br/>
 * 
 * @author Rain.he
 * @version [版本号, 2015年3月30日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MessageContextInitException extends SILException {
    
    private static final long serialVersionUID = -2323505371393070811L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return CommunicationContextErrorCodeEnum.COMMUNICATION_CONTEXT_INIT_ERROR;
    }
    
    /** <默认构造函数> */
    public MessageContextInitException() {
        super();
    }
    
    /** <默认构造函数> */
    public MessageContextInitException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public MessageContextInitException(String message) {
        super(message);
    }
    
}
