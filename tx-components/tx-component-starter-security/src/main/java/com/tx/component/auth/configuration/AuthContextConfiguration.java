/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月23日
 * <修改描述:>
 */
package com.tx.component.auth.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.tx.component.auth.AuthConstants;
import com.tx.component.auth.context.AuthTypeRegistry;
import com.tx.component.auth.service.AuthConfigService;
import com.tx.component.auth.service.AuthEnumService;
import com.tx.component.auth.service.AuthTypeEnumService;
import com.tx.component.role.context.RoleRegistry;
import com.tx.component.role.service.RoleRefItemService;
import com.tx.component.role.service.RoleRefService;
import com.tx.component.role.service.impl.RoleRefServiceImpl;
import com.tx.component.security.starter.SecurityContextCacheConfiguration.SecurityContextCacheCustomizer;
import com.tx.component.security.starter.SecurityContextProperties;

/**
 * 权限容器自动配置项<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Import({ AuthContextPersisterConfiguration.class })
@Configuration
public class AuthContextConfiguration {
    
    /** 权限容器属性 */
    private AuthContextProperties properties;
    
    /** <默认构造函数> */
    public AuthContextConfiguration(SecurityContextProperties properties) {
        super();
        this.properties = properties.getRole() == null
                ? new AuthContextProperties() : properties.getAuth();
    }
    
    /**
     * 权限类型中枚举类型加载器<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleTypeEnumService [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = AuthConstants.BEAN_NAME_AUTH_TYPE_ENUM_SERVICE)
    public AuthTypeEnumService authTypeEnumService() {
        AuthTypeEnumService service = new AuthTypeEnumService();
        return service;
    }
    
    /**
     * 权限枚举类型加载器<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleEnumService [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = AuthConstants.BEAN_NAME_AUTH_ENUM_SERVICE)
    public AuthEnumService authEnumService() {
        AuthEnumService service = new AuthEnumService();
        return service;
    }
    
    /**
     * 权限配置业务层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleConfigService [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = AuthConstants.BEAN_NAME_AUTH_CONFIG_SERVICE)
    public AuthConfigService authConfigService() {
        AuthConfigService config = new AuthConfigService(
                StringUtils.isBlank(this.properties.getConfigLocation())
                        ? AuthConstants.DEFAULT_AUTH_CONFIG_PATH
                        : this.properties.getConfigLocation());
        return config;
    }
    
    /**
     * 权限类型注册表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleTypeRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = AuthConstants.BEAN_NAME_AUTH_TYPE_REGISTRY)
    public AuthTypeRegistry authTypeRegistry(
            SecurityContextCacheCustomizer cacheCustomizer) {
        AuthTypeRegistry registry = new AuthTypeRegistry();
        registry.setCacheManager(cacheCustomizer.getCacheManager());
        return registry;
    }
    
    /**
     * 权限注册表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = AuthConstants.BEAN_NAME_AUTH_REGISTRY)
    public RoleRegistry roleRegistry(
            SecurityContextCacheCustomizer cacheCustomizer) {
        RoleRegistry registry = new RoleRegistry();
        registry.setCacheManager(cacheCustomizer.getCacheManager());
        return registry;
    }
    
    /**
     * 权限注册表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = AuthConstants.BEAN_NAME_AUTH_REF_SERVICE)
    public RoleRefService roleRefService(
            RoleRefItemService roleRefItemService) {
        RoleRefServiceImpl service = new RoleRefServiceImpl(roleRefItemService);
        return service;
    }
}
