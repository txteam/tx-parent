/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月23日
 * <修改描述:>
 */
package com.tx.component.role.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.tx.component.role.RoleConstants;
import com.tx.component.role.context.RoleRegistry;
import com.tx.component.role.context.RoleTypeRegistry;
import com.tx.component.role.service.RoleConfigService;
import com.tx.component.role.service.RoleEnumService;
import com.tx.component.role.service.RoleTypeEnumService;

/**
 * 角色容器自动配置项<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
@Import({ RoleContextPersisterConfiguration.class })
public class RoleContextConfiguration {
    
    /** 角色容器属性 */
    private RoleContextProperties properties;
    
    /** <默认构造函数> */
    public RoleContextConfiguration(RoleContextProperties properties) {
        super();
        this.properties = properties;
    }
    
    /**
     * 角色类型中枚举类型加载器<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleTypeEnumService [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = RoleConstants.BEAN_NAME_ROLE_TYPE_ENUM_SERVICE)
    public RoleTypeEnumService roleTypeEnumService() {
        RoleTypeEnumService service = new RoleTypeEnumService();
        return service;
    }
    
    /**
     * 角色枚举类型加载器<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleEnumService [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = RoleConstants.BEAN_NAME_ROLE_ENUM_SERVICE)
    public RoleEnumService roleEnumService() {
        RoleEnumService service = new RoleEnumService();
        return service;
    }
    
    /**
     * 角色配置业务层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleConfigService [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = RoleConstants.BEAN_NAME_ROLE_CONFIG_SERVICE)
    public RoleConfigService roleConfigService() {
        RoleConfigService config = new RoleConfigService(
                this.properties.getConfigLocation());
        return config;
    }
    
    /**
     * 角色类型注册表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleTypeRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = RoleConstants.BEAN_NAME_ROLE_TYPE_REGISTRY)
    public RoleTypeRegistry roleTypeRegistry() {
        RoleTypeRegistry registry = new RoleTypeRegistry();
        return registry;
    }
    
    /**
     * 角色注册表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public RoleRegistry roleRegistry(){
        RoleRegistry registry = new RoleRegistry();
        return registry;
    }
}
