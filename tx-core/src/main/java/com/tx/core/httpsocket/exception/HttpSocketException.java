/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-20
 * <修改描述:>
 */
package com.tx.core.httpsocket.exception;

import com.tx.core.exceptions.SILException;

/**
 * 参数非法异常<br/>
 * 
 * @author brady
 * @version [版本号, 2013-8-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HttpSocketException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 533782652240275430L;
    
    private int mark = -1;
    
    @Override
    protected String doGetErrorCode() {
        return "HTTPSOCKET_ERROR";
    }
    
    @Override
    protected String doGetErrorMessage() {
        return "HttpSocket异常";
    }
    
    /** <默认构造函数> */
    public HttpSocketException(String message, String... parameters) {
        super(message, parameters);
    }
    
    /** <默认构造函数> */
    public HttpSocketException(String message, Object[] parameters) {
        super(message, parameters);
    }
    
    /** <默认构造函数> */
    public HttpSocketException(String message, int mark) {
        super(message);
        this.mark = mark;
    }
    
    /** <默认构造函数> */
    public HttpSocketException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * @return 返回 mark
     */
    public int getMark() {
        return mark;
    }
    
    /**
     * @param 对mark进行赋值
     */
    public void setMark(int mark) {
        this.mark = mark;
    }
}
