/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月15日
 * <修改描述:>
 */
package com.tx.component.security.model;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * JWT的内部凭证<br/>
 *  分两种情况：operator[Auth],client(loginAccount)[Role]
 *  
 *  头部(header), 荷载(Payload), 和签名(Signature)
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractUserDetail implements UserDetails {
    
    /** 注释内容 */
    private static final long serialVersionUID = -8753348111753770332L;
    
    /** 用户名 */
    private String username;
    
    /** 密码 */
    private String password;
    
    /**
     * @return
     */
    @Override
    public String getUsername() {
        return this.username;
    }
    
    /**
     * @return
     */
    @Override
    public String getPassword() {
        return this.password;
    }
    
    /**
     * 用户帐户是否已过期。过期的帐号不能认证
     * 这里默认为未过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    /**
     * 用户账户是否未锁定，锁定的帐号不能认证
     * 这里默认为未锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    /**
     * 判断用户凭证是否已过期。凭据有效：未过期；凭据无效：已过期<br/>
     * 这里默认为未过期，有效<br/>
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    /**
     * 判断用户是否启用或是禁用（是否有效）,如果已经禁用的用户不能被认证<br/>
     * 这里默认默认实现为启用<br/>
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
