/*
 * 描          述:  <描述>
 * 修  改   人:  Rain.he
 * 修改时间:  2015年6月9日
 * <修改描述:>
 */
package com.tx.core.exceptions.io;

import com.tx.core.exceptions.SILException;

/**
 * IO读取异常
 * 
 * @author Rain.he
 * @version [版本号, 2015年6月9日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class IOReadException extends SILException {
    private static final long serialVersionUID = 6238513417075445465L;
    
    @Override
    protected String doGetErrorCode() {
        return "IO_READ_ERROR";
    }
    
    @Override
    protected String doGetErrorMessage() {
        return "IO读取异常";
    }
    
    /**
     * <默认构造函数><br/>
     * <构造功能简述>
     * 
     * @param message
     * @param parameters
     * 
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public IOReadException(String message, Object[] parameters) {
        super(message, parameters);
    }
    
    /**
     * <默认构造函数><br/>
     * <构造功能简述>
     * 
     * @param message
     * @param cause
     * 
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public IOReadException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * <默认构造函数><br/>
     * <构造功能简述>
     * 
     * @param message
     * 
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public IOReadException(String message) {
        super(message);
    }
    
}
