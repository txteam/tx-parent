/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月29日
 * <修改描述:>
 */
package com.tx.core.method.exceptions;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILErrorCodeEnum;
import com.tx.core.exceptions.SILException;

/**
 * 方法注入：方法调用访问异常<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月29日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MethodInvokeAccessException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 2284197027618096353L;
    
    /** <默认构造函数> */
    public MethodInvokeAccessException() {
        super();
    }
    
    /** <默认构造函数> */
    public MethodInvokeAccessException(String message) {
        super(message);
    }
    
    /** <默认构造函数> */
    public MethodInvokeAccessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SILErrorCodeEnum.METHOD_INVOKE_ACCESS_ERROR;
    }
}
