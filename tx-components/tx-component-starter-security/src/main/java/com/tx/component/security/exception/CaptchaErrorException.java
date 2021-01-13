/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年7月23日
 * <修改描述:>
 */
package com.tx.component.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码错误异常<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年7月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CaptchaErrorException extends AuthenticationException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 1571408909137921500L;
    
    /** <默认构造函数> */
    public CaptchaErrorException(String msg) {
        super(msg);
    }
    
}
