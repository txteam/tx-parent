/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.role.context;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tx.component.role.model.RoleType;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 角色注册表<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RoleTypeRegistry
        implements ApplicationContextAware, BeanNameAware, InitializingBean {
    
    /** spring容器 */
    private static ApplicationContext applicationContext;
    
    /** 实例名 */
    private static String beanName;
    
    /** 实例 */
    private static RoleTypeRegistry instance = null;
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        RoleTypeRegistry.applicationContext = applicationContext;
    }
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        RoleTypeRegistry.beanName = name;
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
    public static RoleTypeRegistry getInstance() {
        if (instance != null) {
            return instance;
        } else {
            RoleTypeRegistry.instance = applicationContext.getBean(beanName,
                    RoleTypeRegistry.class);
        }
        return RoleTypeRegistry.instance;
    }
    
    /** CacheManager */
    private CacheManager cacheManager;
    
    /** 缓存实例 */
    private Cache cache;
    
    /** 角色类型管理器集 */
    private List<RoleTypeManager> roleTypeManagers;
    
    /** 角色类型 */
    private RoleTypeManagerComposite composite;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notNull(cacheManager, "cacheManager is null.");
        AssertUtils.notEmpty(roleTypeManagers, "roleTypeManagers is empty.");
        
        this.cache = this.cacheManager.getCache("roleTypeCache");
        this.composite = new RoleTypeManagerComposite(roleTypeManagers,
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
     * 根据角色类型id获取对应的实例<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return RoleType [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public RoleType findById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        RoleType roleType = this.composite.findById(id);
        return roleType;
    }
    
    /**
     * 根据条件查询角色类型列表<br/>
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return List<RoleType> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<RoleType> queryList(Map<String, Object> params) {
        List<RoleType> resList = this.composite.queryList(params);
        return resList;
    }
}
