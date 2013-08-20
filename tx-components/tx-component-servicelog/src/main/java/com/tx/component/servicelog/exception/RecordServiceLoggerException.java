/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-12
 * <修改描述:>
 */
package com.tx.component.servicelog.exception;

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
public class RecordServiceLoggerException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 7818559066881329564L;
    
    private Object logObjectInstance;
    
    private Class<?> logObjectType;

    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "RECORD_SERVICELOG_ERROR";
    }

    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "记录业务日志异常";
    }

    /** <默认构造函数> */
    public RecordServiceLoggerException(String message, Object[] parameters) {
        super(message, parameters);
    }

    /** <默认构造函数> */
    public RecordServiceLoggerException(String message, Throwable cause) {
        super(message, cause);
    }

    /** <默认构造函数> */
    public RecordServiceLoggerException(String message) {
        super(message);
    }

    /**
     * @return 返回 logObjectInstance
     */
    public Object getLogObjectInstance() {
        return logObjectInstance;
    }

    /**
     * @param 对logObjectInstance进行赋值
     */
    public void setLogObjectInstance(Object logObjectInstance) {
        this.logObjectInstance = logObjectInstance;
    }

    /**
     * @return 返回 logObjectType
     */
    public Class<?> getLogObjectType() {
        return logObjectType;
    }

    /**
     * @param 对logObjectType进行赋值
     */
    public void setLogObjectType(Class<?> logObjectType) {
        this.logObjectType = logObjectType;
    }
}
