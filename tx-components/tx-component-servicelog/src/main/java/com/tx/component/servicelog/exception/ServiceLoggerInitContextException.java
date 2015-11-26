/*
 * 描述： 日志容器初始化异常
 * 修改人： rain
 * 修改时间： 2015年11月25日
 * 项目： tx-component-servicelog
 */
package com.tx.component.servicelog.exception;

/**
 * 日志容器初始化异常
 * 
 * @author rain
 * @version [版本号, 2015年11月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ServiceLoggerInitContextException extends ServiceLoggerException {
    private static final long serialVersionUID = 7697466145862922735L;
    
    @Override
    protected String doGetErrorCode() {
        return "SERVICE_LOGGER_INIT_CONTEXT_EXCEPTION";
    }
    
    @Override
    protected String doGetErrorMessage() {
        return "日志容器初始化异常";
    }
    
    public ServiceLoggerInitContextException(String message, Object[] parameters) {
        super(message, parameters);
    }
    
    public ServiceLoggerInitContextException(String message, String... parameters) {
        super(message, parameters);
    }
    
    public ServiceLoggerInitContextException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ServiceLoggerInitContextException(String message) {
        super(message);
    }
    
}
