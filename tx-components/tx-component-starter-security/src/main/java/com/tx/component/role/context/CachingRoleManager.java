/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.role.context;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.util.ClassUtils;

import com.tx.component.role.model.Role;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.querier.model.Querier;
import com.tx.core.util.CacheUtils;

/**
 * 角色类型业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
class CachingRoleManager implements RoleManager {
    
    private static final Class<?>[] FINDBYID_PARAMETER_TYPES = new Class<?>[] {
            String.class };
    
    private static final Class<?>[] QUERYLIST_PARAMETER_TYPES = new Class<?>[] {
            Querier.class };
    
    private static final Class<?>[] QUERYCHILDREN_PARAMETER_TYPES = new Class<?>[] {
            String.class, Querier.class };
    
    private static final Class<?>[] QUERYDESCENDANTS_PARAMETER_TYPES = new Class<?>[] {
            String.class, Querier.class };
    
    private final Class<?> beanClass;
    
    /** 角色类型Manager的实际实现类 */
    private RoleManager delegate;
    
    /** 角色类型缓存 */
    private Cache roleCache;
    
    /** 构造函数 */
    public CachingRoleManager(RoleManager delegate, Cache roleCache) {
        this.delegate = delegate;
        this.roleCache = roleCache;
        
        AssertUtils.notNull(delegate, "delegate is null.");
        AssertUtils.notNull(roleCache, "roleCache is null.");
        
        //对象可能已经被代理，这里需要取到被代理的类
        this.beanClass = ClassUtils.getUserClass(delegate);
    }
    
    /**
     * @param roleTypeId
     * @return
     */
    @Override
    public Role findRoleById(String roleId) {
        String cacheKey = CacheUtils.generateStringCacheKey(this.beanClass,
                "findRoleById",
                FINDBYID_PARAMETER_TYPES,
                new Object[] { roleId });
        
        ValueWrapper vw = this.roleCache.get(cacheKey);
        if (vw != null && vw.get() != null && Role.class.isInstance(vw.get())) {
            Role roleType = (Role) vw.get();
            return roleType;
        }
        
        Role role = this.delegate.findRoleById(roleId);
        if (role != null) {
            this.roleCache.put(cacheKey, role);
        }
        return role;
    }
    
    /**
     * @param querier
     * @return
     */
    @Override
    public List<Role> queryRoleList(Querier querier) {
        String cacheKey = CacheUtils.generateStringCacheKey(this.beanClass,
                "queryRoleList",
                QUERYLIST_PARAMETER_TYPES,
                new Object[] { querier });
        
        ValueWrapper vw = this.roleCache.get(cacheKey);
        if (vw != null && vw.get() != null && List.class.isInstance(vw.get())) {
            @SuppressWarnings("unchecked")
            List<Role> resList = (List<Role>) vw.get();
            return resList;
        }
        
        List<Role> resList = this.delegate.queryRoleList(querier);
        if (!CollectionUtils.isEmpty(resList)) {
            this.roleCache.put(cacheKey, resList);
        }
        return resList;
    }
    
    /**
     * @param parentId
     * @param querier
     * @return
     */
    @Override
    public List<Role> queryChildrenRoleByParentId(String parentId,
            Querier querier) {
        String cacheKey = CacheUtils.generateStringCacheKey(this.beanClass,
                "queryChildrenRoleByParentId",
                QUERYCHILDREN_PARAMETER_TYPES,
                new Object[] { parentId, querier });
        
        ValueWrapper vw = this.roleCache.get(cacheKey);
        if (vw != null && vw.get() != null && List.class.isInstance(vw.get())) {
            @SuppressWarnings("unchecked")
            List<Role> resList = (List<Role>) vw.get();
            return resList;
        }
        
        List<Role> resList = this.delegate.queryChildrenRoleByParentId(parentId,
                querier);
        if (!CollectionUtils.isEmpty(resList)) {
            this.roleCache.put(cacheKey, resList);
        }
        return resList;
    }
    
    /**
     * @param parentId
     * @param querier
     * @return
     */
    @Override
    public List<Role> queryDescendantsRoleByParentId(String parentId,
            Querier querier) {
        String cacheKey = CacheUtils.generateStringCacheKey(this.beanClass,
                "queryDescendantsByParentId",
                QUERYDESCENDANTS_PARAMETER_TYPES,
                new Object[] { parentId, querier });
        
        ValueWrapper vw = this.roleCache.get(cacheKey);
        if (vw != null && vw.get() != null && List.class.isInstance(vw.get())) {
            @SuppressWarnings("unchecked")
            List<Role> resList = (List<Role>) vw.get();
            return resList;
        }
        
        List<Role> resList = this.delegate.queryChildrenRoleByParentId(parentId,
                querier);
        if (!CollectionUtils.isEmpty(resList)) {
            this.roleCache.put(cacheKey, resList);
        }
        return resList;
    }
    
}
