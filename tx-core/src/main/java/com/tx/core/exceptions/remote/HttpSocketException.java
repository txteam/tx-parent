/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-20
 * <修改描述:>
 */
package com.tx.core.exceptions.remote;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILErrorCodeEnum;

/**
 * 参数非法异常<br/>
 * 
 * @author brady
 * @version [版本号, 2013-8-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HttpSocketException extends RemoteAccessException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 533782652240275430L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SILErrorCodeEnum.HTTP_SOCKET_ERROR;
    }
    
    /** <默认构造函数> */
    public HttpSocketException() {
        super();
    }
    
    /** <默认构造函数> */
    public HttpSocketException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public HttpSocketException(String message) {
        super(message);
    }
}
