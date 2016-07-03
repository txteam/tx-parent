/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年7月3日
 * <修改描述:>
 */
package com.tx.core.exceptions.argument;

import com.tx.core.exceptions.SILException;

/**
 * 不支持的编码类型<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年7月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UnsupportedEncodingArgException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 3406272906771257397L;
    
    /** <默认构造函数> */
    public UnsupportedEncodingArgException(String message, Object[] parameters) {
        super(message, parameters);
    }
    
    /** <默认构造函数> */
    public UnsupportedEncodingArgException(String message, String... parameters) {
        super(message, parameters);
    }
    
    /** <默认构造函数> */
    public UnsupportedEncodingArgException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public UnsupportedEncodingArgException(String message) {
        super(message);
    }
}
