/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-2-17
 * <修改描述:>
 */
package com.tx.component.configuration.exception;

import com.tx.core.exceptions.SILException;

/**
 * 配置属性不存在抛出的异常<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2014-2-17]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class NotExistException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 4273026101999032246L;
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "CONFIG_PROPERTY_NOT_EXIST_ERROR";
    }

    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "配置属性不存在";
    }

    /** <默认构造函数> */
    public NotExistException(String message, Object[] parameters) {
        super(message, parameters);
    }
    
    /** <默认构造函数> */
    public NotExistException(String message) {
        super(message);
    }
    
}
