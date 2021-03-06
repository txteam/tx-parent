/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月9日
 * <修改描述:>
 */
package com.tx.component.security.starter;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.tx.component.auth.configuration.AuthContextProperties;
import com.tx.component.role.configuration.RoleContextProperties;
import com.tx.component.security.SecurityContextConstants;

/**
 * 权限容器属性<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@ConfigurationProperties(prefix = SecurityContextConstants.PROPERTIES_PREFIX)
public class SecurityContextProperties {
    
    /** 命令容器是否启动 */
    private boolean enable;
    
    /** 表是否自动初始化 */
    private boolean tableAutoInitialize = false;
    
    /** 所属模块 */
    private String module;
    
    /** 缓存CacheManager */
    private String cacheManagerRef;
    
    /** 缓存的有效期:默认缓存一天 */
    private Duration duration = Duration.ofDays(1);
    
    /** 角色容器配置 */
    private RoleContextProperties role;
    
    /** 角色容器配置 */
    private AuthContextProperties auth;
    
    /**
     * @return 返回 enable
     */
    public boolean isEnable() {
        return enable;
    }
    
    /**
     * @param 对enable进行赋值
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    
    /**
     * @return 返回 tableAutoInitialize
     */
    public boolean isTableAutoInitialize() {
        return tableAutoInitialize;
    }
    
    /**
     * @param 对tableAutoInitialize进行赋值
     */
    public void setTableAutoInitialize(boolean tableAutoInitialize) {
        this.tableAutoInitialize = tableAutoInitialize;
    }
    
    /**
     * @return 返回 module
     */
    public String getModule() {
        return module;
    }
    
    /**
     * @param 对module进行赋值
     */
    public void setModule(String module) {
        this.module = module;
    }
    
    /**
     * @return 返回 duration
     */
    public Duration getDuration() {
        return duration;
    }
    
    /**
     * @param 对duration进行赋值
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }
    
    /**
     * @return 返回 cacheManagerRef
     */
    public String getCacheManagerRef() {
        return cacheManagerRef;
    }
    
    /**
     * @param 对cacheManagerRef进行赋值
     */
    public void setCacheManagerRef(String cacheManagerRef) {
        this.cacheManagerRef = cacheManagerRef;
    }
    
    /**
     * @return 返回 role
     */
    public RoleContextProperties getRole() {
        return role;
    }
    
    /**
     * @param 对role进行赋值
     */
    public void setRole(RoleContextProperties role) {
        this.role = role;
    }
    
    /**
     * @return 返回 auth
     */
    public AuthContextProperties getAuth() {
        return auth;
    }
    
    /**
     * @param 对auth进行赋值
     */
    public void setAuth(AuthContextProperties auth) {
        this.auth = auth;
    }
    
}
