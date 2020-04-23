/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年11月24日
 * <修改描述:>
 */
package com.tx.component.security.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.tx.component.auth.context.AuthRegistry;
import com.tx.component.auth.model.Auth;
import com.tx.component.role.context.RoleRegistry;
import com.tx.component.role.model.Role;
import com.tx.component.security.model.AuthAuthority;
import com.tx.component.security.model.RoleAuthority;

/**
 * 用以支持表达式解析<br/>
 *    support 'and' 'or'
 *    
 *    hasAuth([auth]) 当前用户是否拥有指定角色
 *    hasAnyAuth([auth1,auth2])  多个角色是一个以逗号进行分隔的字符串。如果当前用户拥有指定角色中的任意一个则返回true
 *    hasRole([role]) 当前用户是否拥有指定角色
 *    hasAnyRole([role1,role2])  多个角色是一个以逗号进行分隔的字符串。如果当前用户拥有指定角色中的任意一个则返回true
 *    hasAuthority([authority]) 等同于hasRole or hasAuth
 *    hasAnyAuthority([authority1,authority2]) 等同于hasAnyRole or hasAuth
 *    
 *    isAnonymous() 当前用户是否是一个匿名用户
 *    isRememberMe() 表示当前用户是否是通过Remember-Me自动登录的
 *    isAuthenticated() 表示当前用户是否已经登录认证成功了
 *    isFullyAuthenticated() 如果当前用户既不是一个匿名用户，同时又不是通过Remember-Me自动登录的，则返回true
 *    
 *    permitAll 总是返回true，表示允许所有的
 *    denyAll 总是返回false，表示拒绝所有的
 *    
 *    principle 代表当前用户的principle对象 
 *    authentication 直接从SecurityContext获取的当前Authentication对象
 * 
 * @author  Administrator
 * @version  [版本号, 2019年11月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SecurityAccessExpressionHolder implements SecurityResourceHolder {
    
    private boolean permitAll = true;
    
    private boolean denyAll = false;
    
    private Authentication authentication;
    
    private Object principle;
    
    private Object credentials;
    
    private String name;
    
    private Object details;
    
    private Collection<? extends GrantedAuthority> authorities;
    
    private Map<String, GrantedAuthority> authorityMap = new HashMap<String, GrantedAuthority>();
    
    private Map<String, GrantedAuthority> authMap = new HashMap<String, GrantedAuthority>();
    
    private Map<String, GrantedAuthority> roleMap = new HashMap<String, GrantedAuthority>();
    
    private AuthRegistry authRegistry;
    
    private RoleRegistry roleRegistry;
    
    /** <默认构造函数> */
    public SecurityAccessExpressionHolder() {
        super();
    }
    
    /**
     * 初始化方法<br/>
     */
    @Override
    public void init() {
        this.authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        this.principle = this.authentication != null
                ? authentication.getPrincipal() : null;
        this.authorities = this.authentication != null
                ? this.authentication.getAuthorities() : new ArrayList<>();
        
        this.credentials = this.authentication.getCredentials();
        this.name = this.authentication.getName();
        this.details = this.authentication.getDetails();
        for (GrantedAuthority a : this.authorities) {
            
            String authority = a.getAuthority();
            authorityMap.put(authority, a);
            if (a instanceof AuthAuthority) {
                authMap.put(((AuthAuthority) a).getAuth().getId(), a);
            } else if (a instanceof RoleAuthority) {
                roleMap.put(((RoleAuthority) a).getRole().getId(), a);
                if (!StringUtils.equals(((RoleAuthority) a).getRole().getId(),
                        a.getAuthority())) {
                    //如果authority不是id,则在role中多添加一个authority对应的映射
                    roleMap.put(a.getAuthority(), a);
                }
            } else {
                if (StringUtils.startsWithIgnoreCase(authority, "ROLE_")) {
                    roleMap.put(authority, a);
                } else {
                    authMap.put(authority, a);
                }
            }
        }
    }
    
    /**
     * 
     */
    @Override
    public void clear() {
        this.authMap.clear();
        this.roleMap.clear();
        this.authorityMap.clear();
    }
    
    /**
     * 是否拥有所有的指定权限<br/>
     * <功能详细描述>
     * @param authAuthorities
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean hasAuth(Collection<String> authAuthorities) {
        if (CollectionUtils.isEmpty(authAuthorities)) {
            return true;
        }
        boolean flag = true;
        for (String authTemp : authAuthorities) {
            if (StringUtils.isEmpty(authTemp)) {
                continue;
            }
            if (getAuthById(authTemp) == null) {
                continue;
            }
            if (!authMap.containsKey(authTemp)) {
                flag = false;
                break;
            }
        }
        return flag;
    }
    
    /**
     * 是否有其中任意权限<br/>
     * <功能详细描述>
     * @param authAuthorities
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean hasAnyAuth(Collection<String> authAuthorities) {
        if (CollectionUtils.isEmpty(authAuthorities)) {
            return true;
        }
        boolean flag = true;
        for (String authTemp : authAuthorities) {
            if (StringUtils.isEmpty(authTemp)) {
                continue;
            }
            if (getAuthById(authTemp) == null) {
                continue;
            }
            if (!authMap.containsKey(authTemp)) {
                flag = false;
            } else {
                flag = true;
                break;
            }
        }
        return flag;
    }
    
    /**
     * 是否拥有指定角色<br/>
     * <功能详细描述>
     * @param roleAuthorities
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean hasRole(Collection<String> roleAuthorities) {
        boolean flag = true;
        for (String roleTemp : roleAuthorities) {
            if (StringUtils.isEmpty(roleTemp)) {
                continue;
            }
            if (!roleMap.containsKey(roleTemp)) {
                flag = false;
                break;
            }
        }
        return flag;
    }
    
    /**
     * 是否拥有任意指定角色<br/>
     * <功能详细描述>
     * @param roleAuthorities
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean hasAnyRole(Collection<String> roleAuthorities) {
        boolean flag = true;
        for (String roleTemp : roleAuthorities) {
            if (StringUtils.isEmpty(roleTemp)) {
                continue;
            }
            if (!roleMap.containsKey(roleTemp)) {
                flag = false;
            } else {
                flag = true;
                break;
            }
        }
        return flag;
    }
    
    /**
     * 是否拥有指定权限或
     * <功能详细描述>
     * @param authorities
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean hasAuthority(Collection<String> authorities) {
        boolean flag = true;
        for (String authorityTemp : authorities) {
            if (StringUtils.isEmpty(authorityTemp)) {
                continue;
            }
            if (!authorityMap.containsKey(authorityTemp)) {
                flag = false;
                break;
            }
        }
        return flag;
    }
    
    /**
     * 是否拥有任意权限或角色<br/>
     * <功能详细描述>
     * @param authorities
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean hasAnyAuthority(Collection<String> authorities) {
        boolean flag = true;
        for (String authorityTemp : authorities) {
            if (StringUtils.isEmpty(authorityTemp)) {
                continue;
            }
            if (!authorityMap.containsKey(authorityTemp)) {
                flag = false;
            } else {
                flag = true;
                break;
            }
        }
        return flag;
    }
    
    /**
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isAnonymous() {
        boolean anonymous = this.authentication instanceof AnonymousAuthenticationToken;
        return anonymous;
    }
    
    /**
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isRememberMe() {
        boolean rememberMe = this.authentication instanceof RememberMeAuthenticationToken;
        return rememberMe;
    }
    
    /**
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isAuthenticated() {
        boolean authenticated = this.authentication.isAuthenticated();
        return authenticated;
    }
    
    /**
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isFullyAuthenticated() {
        boolean fullyAuthenticated = isAuthenticated() && !isRememberMe();
        return fullyAuthenticated;
    }
    
    /**
     * @return 返回 permitAll
     */
    public boolean isPermitAll() {
        return permitAll;
    }
    
    /**
     * @param 对permitAll进行赋值
     */
    public void setPermitAll(boolean permitAll) {
        this.permitAll = permitAll;
    }
    
    /**
     * @return 返回 denyAll
     */
    public boolean isDenyAll() {
        return denyAll;
    }
    
    /**
     * @param 对denyAll进行赋值
     */
    public void setDenyAll(boolean denyAll) {
        this.denyAll = denyAll;
    }
    
    /**
     * @return 返回 principle
     */
    public Object getPrinciple() {
        return principle;
    }
    
    /**
     * @param 对principle进行赋值
     */
    public void setPrinciple(Object principle) {
        this.principle = principle;
    }
    
    /**
     * @return 返回 authentication
     */
    public Authentication getAuthentication() {
        return authentication;
    }
    
    /**
     * @param 对authentication进行赋值
     */
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
    
    /**
     * @return 返回 authorities
     */
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    /**
     * @param 对authorities进行赋值
     */
    public void setAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
    
    /**
     * @return 返回 name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return 返回 details
     */
    public Object getDetails() {
        return details;
    }
    
    /**
     * @return 返回 credentials
     */
    public Object getCredentials() {
        return credentials;
    }
    
    /**
     * 获取权限注册机<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return AuthRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private AuthRegistry getAuthRegistry() {
        if (this.authRegistry != null) {
            return this.authRegistry;
        }
        this.authRegistry = AuthRegistry.getInstance();
        return this.authRegistry;
    }
    
    /**
     * 根据角色id获取权限实例<br/>
     * <功能详细描述>
     * @param authId
     * @return [参数说明]
     * 
     * @return Role [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected Auth getAuthById(String authId) {
        if (StringUtils.isBlank(authId)) {
            return null;
        }
        Auth auth = getAuthRegistry().findById(authId);
        return auth;
    }
    
    /**
     * 获取角色注册机<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private RoleRegistry getRoleRegistry() {
        if (this.roleRegistry != null) {
            return this.roleRegistry;
        }
        this.roleRegistry = RoleRegistry.getInstance();
        return this.roleRegistry;
    }
    
    /**
     * 根据角色id获取角色实例<br/>
     * <功能详细描述>
     * @param roleId
     * @return [参数说明]
     * 
     * @return Role [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected Role getRoleById(String roleId) {
        if (StringUtils.isBlank(roleId)) {
            return null;
        }
        Role role = getRoleRegistry().findById(roleId);
        return role;
    }
}
