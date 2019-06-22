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

import com.tx.component.role.model.RoleType;
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
class CachingRoleTypeManager implements RoleTypeManager {
    
    private static final Class<?>[] FINDBYID_PARAMETER_TYPES = new Class<?>[] {
            String.class };
    
    private static final Class<?>[] QUERYLIST_PARAMETER_TYPES = new Class<?>[] {
            Querier.class };
    
    private final Class<?> beanClass;
    
    /** 角色类型Manager的实际实现类 */
    private RoleTypeManager delegate;
    
    /** 角色类型缓存 */
    private Cache roleTypeCache;
    
    /** 构造函数 */
    public CachingRoleTypeManager(RoleTypeManager delegate,
            Cache roleTypeCache) {
        this.delegate = delegate;
        this.roleTypeCache = roleTypeCache;
        
        AssertUtils.notNull(delegate, "delegate is null.");
        AssertUtils.notNull(roleTypeCache, "roleTypeCache is null.");
        
        //对象可能已经被代理，这里需要取到被代理的类
        this.beanClass = ClassUtils.getUserClass(delegate);
    }
    
    /**
     * @param roleTypeId
     * @return
     */
    public RoleType findRoleTypeById(String roleTypeId) {
        String cacheKey = CacheUtils.generateStringCacheKey(this.beanClass,
                "findById",
                FINDBYID_PARAMETER_TYPES,
                new Object[] { roleTypeId });
        
        ValueWrapper vw = this.roleTypeCache.get(cacheKey);
        if (vw != null && vw.get() != null
                && RoleType.class.isInstance(vw.get())) {
            RoleType roleType = (RoleType) vw.get();
            return roleType;
        }
        
        RoleType roleType = this.delegate.findRoleTypeById(roleTypeId);
        if (roleType != null) {
            this.roleTypeCache.put(cacheKey, roleType);
        }
        return roleType;
    }
    
    /**
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<RoleType> queryRoleTypeList(Querier querier) {
        String cacheKey = CacheUtils.generateStringCacheKey(this.beanClass,
                "queryList",
                QUERYLIST_PARAMETER_TYPES,
                new Object[] { querier });
        
        ValueWrapper vw = this.roleTypeCache.get(cacheKey);
        if (vw != null && vw.get() != null && List.class.isInstance(vw.get())) {
            List<RoleType> resList = (List<RoleType>) vw.get();
            return resList;
        }
        
        List<RoleType> resList = this.delegate.queryRoleTypeList(querier);
        if (!CollectionUtils.isEmpty(resList)) {
            this.roleTypeCache.put(cacheKey, resList);
        }
        return resList;
    }
}
