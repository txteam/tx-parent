/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-23
 * <修改描述:>
 */
package com.tx.component.servicelogger.exceptions;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILException;

/**
 * 不支持的业务日志类型异常<br/>
 * 
 * @author brady
 * @version [版本号, 2013-9-23]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ServiceLoggerBuildException extends SILException {
    
    private static final long serialVersionUID = 3062103647172437045L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        return SLCErrorCodeEnum.SERVICE_LOGGER_BUILD_ERROR;
    }
    
    /** <默认构造函数> */
    public ServiceLoggerBuildException() {
        super();
    }
    
    /** <默认构造函数> */
    public ServiceLoggerBuildException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public ServiceLoggerBuildException(String message) {
        super(message);
    }
}
