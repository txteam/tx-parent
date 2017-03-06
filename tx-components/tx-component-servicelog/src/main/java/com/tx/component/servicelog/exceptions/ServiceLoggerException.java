/*
 * 描述： 日志模块顶级异常,所以日志模块都继承此异常
 * 修改人： rain
 * 修改时间： 2015年11月25日
 * 项目： tx-component-servicelog
 */
package com.tx.component.servicelog.exceptions;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILException;

/**
 * 日志异常
 * 
 * @author rain
 * @version [版本号, 2015年11月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ServiceLoggerException extends SILException {
    
    private static final long serialVersionUID = -8110386005451207798L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SLCErrorCodeEnum.SERVICE_LOGGER_ERROR;
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
    public ServiceLoggerException() {
        super();
    }
    
    /** <默认构造函数> */
    public ServiceLoggerException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public ServiceLoggerException(String message) {
        super(message);
    }
}
