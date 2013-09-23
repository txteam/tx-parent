/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-23
 * <修改描述:>
 */
package com.tx.component.servicelog.exception;

import com.tx.core.exceptions.SILException;

/**
 * 不支持的业务日志类型异常<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UnsupportServiceLoggerTypeException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 3062103647172437045L;
    
    /** <默认构造函数> */
    public UnsupportServiceLoggerTypeException(String message,
            Object[] parameters) {
        super(message, parameters);
    }
    
    /** <默认构造函数> */
    public UnsupportServiceLoggerTypeException(String message) {
        super(message);
    }
}
