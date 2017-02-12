/*
 * 描          述:  <描述>
 * 修  改   人:  Rain.he
 * 修改时间:  2015年4月28日
 * <修改描述:>
 */
package com.tx.core.exceptions.remote;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILErrorCodeEnum;
import com.tx.core.exceptions.SILException;

/**
 * 远程接口调用异常
 * 
 * @author Rain.he
 * @version [版本号, 2015年4月28日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class RemoteAccessException extends SILException {
    
    private static final long serialVersionUID = -5085684691190099695L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SILErrorCodeEnum.REMOTE_ACCESS_ERROR;
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
    public RemoteAccessException() {
        super();
    }
    
    /** <默认构造函数> */
    public RemoteAccessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public RemoteAccessException(String message) {
        super(message);
    }
}
