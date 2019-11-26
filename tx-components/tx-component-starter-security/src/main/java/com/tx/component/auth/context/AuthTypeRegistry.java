/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.auth.context;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tx.component.auth.AuthConstants;
import com.tx.component.auth.model.AuthType;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 权限类型注册表<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthTypeRegistry
        implements ApplicationContextAware, BeanNameAware, InitializingBean {
    
    /** spring容器 */
    private static ApplicationContext applicationContext;
    
    /** 实例名 */
    private static String beanName;
    
    /** 实例 */
    private static AuthTypeRegistry instance = null;
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        AuthTypeRegistry.applicationContext = applicationContext;
    }
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        AuthTypeRegistry.beanName = name;
    }
    
    /**
     * 获取实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleTypeRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static AuthTypeRegistry getInstance() {
        if (instance != null) {
            return instance;
        } else {
            AuthTypeRegistry.instance = applicationContext.getBean(beanName,
                    AuthTypeRegistry.class);
        }
        return AuthTypeRegistry.instance;
    }
    
    /** CacheManager */
    private CacheManager cacheManager;
    
    /** 缓存实例 */
    private Cache cache;
    
    /** 权限类型类型管理器集 */
    private List<AuthTypeManager> authTypeManagers;
    
    /** 权限类型类型 */
    private AuthTypeManagerComposite composite;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notNull(cacheManager, "cacheManager is null.");
        
        //角色类型业务层
        this.authTypeManagers = new ArrayList<>(applicationContext
                .getBeansOfType(AuthTypeManager.class).values());
        
        AssertUtils.notEmpty(authTypeManagers, "authTypeManagers is empty.");
        
        //角色类型缓存
        this.cache = this.cacheManager
                .getCache(AuthConstants.CACHE_KEY_AUTH_TYPE);
        this.composite = new AuthTypeManagerComposite(authTypeManagers,
                this.cache);
    }
    
    /**
     * 获取缓存<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Cache [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Cache getCache() {
        return this.cache;
    }
    
    /**
     * 根据权限类型类型id获取对应的实例<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return RoleType [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public AuthType findById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        AuthType roleType = this.composite.findById(id);
        return roleType;
    }
    
    /**
     * 查询权限类型列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<AuthType> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<AuthType> queryList() {
        List<AuthType> resList = this.composite.queryList();        
        return resList;
    }
    
    /**
     * @param 对cacheManager进行赋值
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
    
}
