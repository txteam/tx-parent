/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-12
 * <修改描述:>
 */
package com.tx.component.servicelog.exception;

import com.tx.core.exceptions.ErrorCodeConstant;
import com.tx.core.exceptions.SILException;

/**
 * 业务日志异常
 * 1、由于业务日志与具体业务精密相关，业务日志异常如果记录出现异常，相关业务应该产生异常并回滚
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2012-12-12]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ServiceLoggerException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 7818559066881329564L;
    
    /**
     * <默认构造函数>
     */
    public ServiceLoggerException(String errorMessage, Throwable cause,
            String... parameters) {
        super(ErrorCodeConstant.LOG_SERVICELOG_EXCEPTION, errorMessage, cause);
    }
    
    /**
     * <默认构造函数>
     */
    public ServiceLoggerException(String errorMessage, String... parameters) {
        super(ErrorCodeConstant.LOG_SERVICELOG_EXCEPTION, errorMessage,
                parameters);
    }
}
