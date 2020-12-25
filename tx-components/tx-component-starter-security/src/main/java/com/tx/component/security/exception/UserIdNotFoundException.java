/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月30日
 * <修改描述:>
 */
package com.tx.component.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 用户
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月30日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UserIdNotFoundException extends AuthenticationException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 7114749250669366169L;
    
    /** <默认构造函数> */
    public UserIdNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
    
    /** <默认构造函数> */
    public UserIdNotFoundException(String msg) {
        super(msg);
    }
    
}
