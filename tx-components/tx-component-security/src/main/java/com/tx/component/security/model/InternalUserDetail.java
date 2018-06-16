/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月15日
 * <修改描述:>
 */
package com.tx.component.security.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
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
public class InternalUserDetail implements UserDetails {
    
    /** 注释内容 */
    private static final long serialVersionUID = -8753348111753770332L;
    
    /** 客户id */
    private String userId;
    
    /** 用户名 */
    private String username;
    
    /**
     * @param 对username进行赋值
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * @param 对password进行赋值
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * @return
     */
    @Override
    public String getPassword() {
        return this.password;
    }
    
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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return false;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }
}
