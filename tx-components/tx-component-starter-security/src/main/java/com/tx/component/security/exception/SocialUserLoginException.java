/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年7月23日
 * <修改描述:>
 */
package com.tx.component.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 第三方用户登陆异常<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年7月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SocialUserLoginException extends AuthenticationException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 3941764276141261391L;
    
    /** <默认构造函数> */
    public SocialUserLoginException(String msg) {
        super(msg);
    }
    
    /** <默认构造函数> */
    public SocialUserLoginException(String msg, Throwable t) {
        super(msg, t);
    }
    
}
