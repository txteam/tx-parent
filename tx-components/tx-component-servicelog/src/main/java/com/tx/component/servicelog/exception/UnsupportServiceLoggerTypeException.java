/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-23
 * <修改描述:>
 */
package com.tx.component.servicelog.exception;

/**
 * 不支持的业务日志类型异常<br/>
 * 
 * @author brady
 * @version [版本号, 2013-9-23]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UnsupportServiceLoggerTypeException extends ServiceLoggerException {
    
    private static final long serialVersionUID = 3062103647172437045L;
    
    public UnsupportServiceLoggerTypeException(String message) {
        super(message);
    }
    
    public UnsupportServiceLoggerTypeException(String message, Object[] parameters) {
        super(message, parameters);
    }
    
    public UnsupportServiceLoggerTypeException(String message, String... parameters) {
        super(message, parameters);
    }
    
}
