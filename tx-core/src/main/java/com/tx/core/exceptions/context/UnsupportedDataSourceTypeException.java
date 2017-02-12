/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-18
 * <修改描述:>
 */
package com.tx.core.exceptions.context;

import com.tx.core.exceptions.SILException;

/**
 * 不支持的数据类型异常
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-10-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UnsupportedDataSourceTypeException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -2519283583568061771L;
    
    /**
     * @return
     */
    @Override
    protected Integer errorCode() {
        return 155001;
    }
    
    /**
     * @return
     */
    @Override
    protected String errorMessage() {
        return "不支持的数据源类型";
    }
    
    /** <默认构造函数> */
    public UnsupportedDataSourceTypeException() {
        super();
    }
    
    /** <默认构造函数> */
    public UnsupportedDataSourceTypeException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public UnsupportedDataSourceTypeException(String message) {
        super(message);
    }
}
