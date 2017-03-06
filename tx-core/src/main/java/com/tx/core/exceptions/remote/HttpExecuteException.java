/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年7月2日
 * <修改描述:>
 */
package com.tx.core.exceptions.remote;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILErrorCodeEnum;

/**
 * 抽象Http执行时异常<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年7月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class HttpExecuteException extends RemoteAccessException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 693305735479794945L;
    
    /** 是否存在发生成功的可能性 */
    private boolean mybeSendSuccess;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SILErrorCodeEnum.HTTP_EXECUTE_ERROR;
    }
    
    /** <默认构造函数> */
    public HttpExecuteException(boolean mybeSendSuccess, String message,
            Throwable cause) {
        super(message, cause);
        this.mybeSendSuccess = mybeSendSuccess;
    }
    
    /** <默认构造函数> */
    public HttpExecuteException(boolean mybeSendSuccess, String message) {
        super(message);
        this.mybeSendSuccess = mybeSendSuccess;
    }
    
    /** <默认构造函数> */
    public HttpExecuteException() {
        super();
    }
    
    /** <默认构造函数> */
    public HttpExecuteException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public HttpExecuteException(String message) {
        super(message);
    }
    
    /**
     * @return 返回 mybeSendSuccess
     */
    public boolean isMybeSendSuccess() {
        return mybeSendSuccess;
    }
    
    /**
     * @param 对mybeSendSuccess进行赋值
     */
    public void setMybeSendSuccess(boolean mybeSendSuccess) {
        this.mybeSendSuccess = mybeSendSuccess;
    }
}
