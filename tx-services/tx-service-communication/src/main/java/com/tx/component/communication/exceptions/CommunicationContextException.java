/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年2月12日
 * <修改描述:>
 */
package com.tx.component.communication.exceptions;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILException;

/**
 * 消息容器异常<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年2月12日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CommunicationContextException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 8004281833212245202L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return CommunicationContextErrorCodeEnum.COMMUNICATION_CONTEXT_ERROR;
    }
    
    /**
     * @return
     */
    @Override
    protected final Integer errorCode() {
        return super.errorCode();
    }
    
    /**
     * @return
     */
    @Override
    protected final String errorMessage() {
        return super.errorMessage();
    }
    
    /** <默认构造函数> */
    public CommunicationContextException() {
        super();
    }
    
    /** <默认构造函数> */
    public CommunicationContextException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public CommunicationContextException(String message) {
        super(message);
    }
    
}
