/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-14
 * <修改描述:>
 */
package com.tx.component.security.exception;

import com.tx.core.exceptions.ErrorCode;

/**
 * 权限容器初始化异常<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2012-12-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SecurityContextInitException extends SecurityContextException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 7273081031541085969L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SecurityContextErrorCodeEnum.AUTH_CONTEXT_INIT_ERROR;
    }
    
    /** <默认构造函数> */
    public SecurityContextInitException() {
        super();
    }
    
    /** <默认构造函数> */
    public SecurityContextInitException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public SecurityContextInitException(String message) {
        super(message);
    }
    
}
