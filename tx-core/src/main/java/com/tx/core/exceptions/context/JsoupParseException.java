/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-11-20
 * <修改描述:>
 */
package com.tx.core.exceptions.context;

import com.tx.core.exceptions.SILException;

/**
 * Jsoup解析过程中出现异常<br/>
 * <功能详细描述>
 * 
 * @author  wanxin
 * @version  [版本号, 2013-11-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JsoupParseException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -4590978005225675896L;
    
    /**
     * @return
     */
    @Override
    protected Integer errorCode() {
        return 152000;
    }
    
    /**
     * @return
     */
    @Override
    protected String errorMessage() {
        return "JSOUP解析异常";
    }
    
    /** <默认构造函数> */
    public JsoupParseException() {
        super();
    }
    
    /** <默认构造函数> */
    public JsoupParseException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public JsoupParseException(String message) {
        super(message);
    }
}
