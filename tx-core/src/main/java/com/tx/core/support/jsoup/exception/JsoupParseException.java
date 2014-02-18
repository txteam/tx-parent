/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-11-20
 * <修改描述:>
 */
package com.tx.core.support.jsoup.exception;

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
    protected String doGetErrorCode() {
        return "JSOUP_PARSE_ERROR";
    }
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "文件解析异常";
    }
    
    public JsoupParseException(String message, Object[] parameters) {
        super(message, parameters);
    }
    
    public JsoupParseException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public JsoupParseException(String message) {
        super(message);
    }
    
}
