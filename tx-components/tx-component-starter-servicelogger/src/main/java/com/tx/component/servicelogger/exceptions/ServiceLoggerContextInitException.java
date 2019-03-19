/*
 * 描述： 日志容器初始化异常
 * 修改人： rain
 * 修改时间： 2015年11月25日
 * 项目： tx-component-servicelog
 */
package com.tx.component.servicelogger.exceptions;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILException;

/**
 * 日志容器初始化异常
 * 
 * @author rain
 * @version [版本号, 2015年11月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ServiceLoggerContextInitException extends SILException {
    
    /** 序列号 */
    private static final long serialVersionUID = 7697466145862922735L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SLCErrorCodeEnum.SERVICE_LOGGER_CONTEXT_INIT_ERROR;
    }
    
    /** <默认构造函数> */
    public ServiceLoggerContextInitException() {
        super();
    }
    
    /** <默认构造函数> */
    public ServiceLoggerContextInitException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public ServiceLoggerContextInitException(String message) {
        super(message);
    }
}
