/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年7月2日
 * <修改描述:>
 */
package com.tx.core.exceptions.http;


/**
 * http请求发送前异常<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年7月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class HttpExcutingException extends AbstractHttpExecuteException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 6753606423104501946L;
    
    private int statusCode;
    
    private String reasonPhrase;
    
    /** <默认构造函数> */
    public HttpExcutingException(boolean mybeSendSuccess, String message,
            int statusCode, String reasonPhrase) {
        super(mybeSendSuccess, message);
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }
    
    /** <默认构造函数> */
    public HttpExcutingException(boolean mybeSendSuccess, String message,
            Throwable cause) {
        super(mybeSendSuccess, message, cause);
    }
    
    /**
     * @return 返回 statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }
    
    /**
     * @return 返回 reasonPhrase
     */
    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
