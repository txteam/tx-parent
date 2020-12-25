/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月22日
 * <修改描述:>
 */
package com.tx.component.security.exception;

/**
 * 第三方签入异常<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SocialAuthorizeException extends Exception {
    
    /** 注释内容 */
    private static final long serialVersionUID = -6304694102905618058L;
    
    /** <默认构造函数> */
    public SocialAuthorizeException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public SocialAuthorizeException(String message) {
        super(message);
    }
    
}
