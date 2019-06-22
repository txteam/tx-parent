/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-14
 * <修改描述:>
 */
package com.tx.component.security.exception;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILException;

/**
 * 权限容器初始化异常<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2012-12-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class SecurityContextException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 7273081031541085969L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SecurityContextErrorCodeEnum.AUTH_CONTEXT_ERROR;
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
    public SecurityContextException() {
        super();
    }
    
    /** <默认构造函数> */
    public SecurityContextException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public SecurityContextException(String message) {
        super(message);
    }
    
}
