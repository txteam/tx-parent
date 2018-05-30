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
public class MethodInvokeTargetException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -8485644381279537218L;
    
    private Throwable targetException;
    
    /** <默认构造函数> */
    public MethodInvokeTargetException() {
        super();
    }
    
    /** <默认构造函数> */
    public MethodInvokeTargetException(String message, Throwable cause,
            Throwable targetException) {
        super(message, cause);
        this.targetException = targetException;
    }
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SILErrorCodeEnum.METHOD_INVOKE_TARGET_ERROR;
    }
    
    /**
     * @return 返回 targetException
     */
    public Throwable getTargetException() {
        return targetException;
    }
    
    /**
     * @param 对targetException进行赋值
     */
    public void setTargetException(Throwable targetException) {
        this.targetException = targetException;
    }
}
