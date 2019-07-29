/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年2月26日
 * <修改描述:>
 */
package com.tx.component.security.context;

import org.springframework.beans.factory.BeanNameAware;

import com.tx.component.auth.AuthConstants;
import com.tx.component.auth.context.AuthRegistry;
import com.tx.component.auth.context.AuthTypeRegistry;
import com.tx.component.auth.service.AuthItemService;
import com.tx.component.auth.service.AuthRefService;
import com.tx.component.auth.service.AuthTypeItemService;
import com.tx.component.role.RoleConstants;
import com.tx.component.role.context.RoleRegistry;
import com.tx.component.role.context.RoleTypeRegistry;
import com.tx.component.role.service.RoleItemService;
import com.tx.component.role.service.RoleRefService;
import com.tx.component.role.service.RoleTypeItemService;

/**
 * 权限容器构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年2月26日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class SecurityContextBuilder extends SecurityContextConfigurator
        implements BeanNameAware, SecurityContextEngine {
    
    /** beanName实例 */
    protected static String beanName;
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        SecurityContextBuilder.beanName = name;
    }
    
    /**
     * @throws Exception
     */
    @Override
    protected void doBuild() throws Exception {
        //加载基础数据类<br/>
    }
    
    /**
     * @return
     */
    @Override
    public RoleRefService getRoleRefService() {
        RoleRefService service = applicationContext.getBean(
                RoleConstants.BEAN_NAME_ROLE_REF_SERVICE, RoleRefService.class);
        return service;
    }
    
    /**
     * @return
     */
    @Override
    public RoleTypeRegistry getRoleTypeRegistry() {
        RoleTypeRegistry registry = applicationContext.getBean(
                RoleConstants.BEAN_NAME_ROLE_TYPE_REGISTRY,
                RoleTypeRegistry.class);
        return registry;
    }
    
    /**
     * @return
     */
    @Override
    public RoleRegistry getRoleRegistry() {
        RoleRegistry registry = applicationContext.getBean(
                RoleConstants.BEAN_NAME_ROLE_REGISTRY, RoleRegistry.class);
        return registry;
    }
    
    /**
     * @return
     */
    @Override
    public RoleTypeItemService getRoleTypeItemService() {
        RoleTypeItemService service = applicationContext.getBean(
                RoleConstants.BEAN_NAME_ROLE_TYPE_ITEM_SERVICE,
                RoleTypeItemService.class);
        return service;
    }
    
    /**
     * @return
     */
    @Override
    public RoleItemService getRoleItemService() {
        RoleItemService service = applicationContext.getBean(
                RoleConstants.BEAN_NAME_ROLE_ITEM_SERVICE,
                RoleItemService.class);
        return service;
    }
    
    /**
     * @return
     */
    @Override
    public AuthRefService getAuthRefService() {
        AuthRefService service = applicationContext.getBean(
                AuthConstants.BEAN_NAME_AUTH_REF_SERVICE, AuthRefService.class);
        return service;
    }
    
    /**
     * @return
     */
    @Override
    public AuthTypeRegistry getAuthTypeRegistry() {
        AuthTypeRegistry registry = applicationContext.getBean(
                AuthConstants.BEAN_NAME_AUTH_TYPE_REGISTRY,
                AuthTypeRegistry.class);
        return registry;
    }
    
    /**
     * @return
     */
    @Override
    public AuthRegistry getAuthRegistry() {
        AuthRegistry registry = applicationContext.getBean(
                AuthConstants.BEAN_NAME_AUTH_REGISTRY, AuthRegistry.class);
        return registry;
    }
    
    /**
     * @return
     */
    @Override
    public AuthTypeItemService getAuthTypeItemService() {
        AuthTypeItemService service = applicationContext.getBean(
                AuthConstants.BEAN_NAME_AUTH_TYPE_ITEM_SERVICE,
                AuthTypeItemService.class);
        return service;
    }
    
    /**
     * @return
     */
    @Override
    public AuthItemService getAuthItemService() {
        AuthItemService service = applicationContext.getBean(
                AuthConstants.BEAN_NAME_AUTH_ITEM_SERVICE,
                AuthItemService.class);
        return service;
    }
}
