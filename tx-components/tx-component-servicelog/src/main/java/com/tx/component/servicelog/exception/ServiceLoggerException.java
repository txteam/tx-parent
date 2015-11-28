/*
 * 描述： 日志模块顶级异常,所以日志模块都继承此异常
 * 修改人： rain
 * 修改时间： 2015年11月25日
 * 项目： tx-component-servicelog
 */
package com.tx.component.servicelog.exception;

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
    
    @Override
    protected String doGetErrorCode() {
        return "SERVICE_LOGGER_EXCEPTION";
    }
    
    @Override
    protected String doGetErrorMessage() {
        return "日志异常";
    }
    
    public ServiceLoggerException(String message, Object[] parameters) {
        super(message, parameters);
    }
    
    public ServiceLoggerException(String message, String... parameters) {
        super(message, parameters);
    }
    
    public ServiceLoggerException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ServiceLoggerException(String message) {
        super(message);
    }
    
}
