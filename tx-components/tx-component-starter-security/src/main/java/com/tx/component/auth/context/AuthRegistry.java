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
import com.tx.component.auth.model.Auth;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 权限注册表<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthRegistry
        implements ApplicationContextAware, BeanNameAware, InitializingBean {
    
    /** spring容器 */
    private static ApplicationContext applicationContext;
    
    /** 实例名 */
    private static String beanName;
    
    /** 实例 */
    private static AuthRegistry instance = null;
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        AuthRegistry.applicationContext = applicationContext;
    }
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        AuthRegistry.beanName = name;
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
    public static AuthRegistry getInstance() {
        if (instance != null) {
            return instance;
        } else {
            AuthRegistry.instance = applicationContext.getBean(beanName,
                    AuthRegistry.class);
            return instance;
        }
    }
    
    /** CacheManager */
    private CacheManager cacheManager;
    
    /** 权限类型管理器集 */
    private List<AuthManager> authManagers;
    
    /** 缓存实例 */
    private Cache cache;
    
    /** 权限类型 */
    private AuthManagerComposite composite;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notNull(cacheManager, "cacheManager is null.");
        AssertUtils.notEmpty(authManagers, "roleManagers is empty.");
        
        //角色类型业务层
        this.authManagers = new ArrayList<>(
                applicationContext.getBeansOfType(AuthManager.class).values());
        
        AssertUtils.notEmpty(authManagers, "authManagers is empty.");
        
        //角色类型缓存
        this.cache = this.cacheManager.getCache(AuthConstants.CACHE_KEY_AUTH);
        this.composite = new AuthManagerComposite(authManagers, this.cache);
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
     * 根据id查询权限实例<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return Role [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Auth findById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        Auth auth = this.composite.findById(id);
        return auth;
    }
    
    /**
     * 查询权限列表<br/>
     * <功能详细描述>
     * @param authTypeId
     * @return [参数说明]
     * 
     * @return List<Role> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<Auth> queryList(String authTypeId) {
        List<Auth> resList = this.composite.queryList(authTypeId);
        return resList;
    }
    
    /**
     * 根父节点查询子级角色列表<br/>
     * <功能详细描述>
     * @param parentId
     * @param authTypeId
     * @return [参数说明]
     * 
     * @return List<Auth> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<Auth> queryChildrenByParentId(String parentId,
            String authTypeId) {
        List<Auth> resList = this.composite.queryChildrenByParentId(parentId,
                authTypeId);
        return resList;
    }
    
    /**
     * 嵌套查询子级角色列表<br/>
     * <功能详细描述>
     * @param parentId
     * @param authTypeId
     * @return [参数说明]
     * 
     * @return List<Auth> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<Auth> queryDescendantsByParentId(String parentId,
            String authTypeId) {
        List<Auth> resList = this.composite.queryDescendantsByParentId(parentId,
                authTypeId);
        return resList;
    }
    
    /**
     * @param 对cacheManager进行赋值
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
    
}
