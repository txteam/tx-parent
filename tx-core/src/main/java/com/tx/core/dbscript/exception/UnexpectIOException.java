/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-22
 * <修改描述:>
 */
package com.tx.core.dbscript.exception;

import java.io.IOException;

import org.slf4j.helpers.MessageFormatter;

import com.tx.core.exceptions.SILException;

/**
 * 不期望出现的io异常
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UnexpectIOException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -3073311865917363209L;
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "IO_ERROR";
    }
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "读写文件错误";
    }
    
    /** <默认构造函数> */
    public UnexpectIOException(IOException io, String message) {
        super(message, io);
    }
    
    /** <默认构造函数> */
    public UnexpectIOException(IOException io, String message, Object[] params) {
        super(MessageFormatter.arrayFormat(message, params).getMessage(), io);
    }
    
    /** <默认构造函数> */
    public UnexpectIOException(IOException io, String message, String... params) {
        this(io, message, (Object[]) params);
    }
}
