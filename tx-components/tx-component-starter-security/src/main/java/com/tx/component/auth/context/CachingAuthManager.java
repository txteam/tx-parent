/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.auth.context;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.util.ClassUtils;

import com.tx.component.auth.model.Auth;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.querier.model.Querier;
import com.tx.core.util.CacheUtils;

/**
 * 权限类型业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
class CachingAuthManager implements AuthManager {
    
    private static final Class<?>[] FINDBYID_PARAMETER_TYPES = new Class<?>[] {
            String.class };
    
    private static final Class<?>[] QUERYLIST_PARAMETER_TYPES = new Class<?>[] {
            Querier.class };
    
    private static final Class<?>[] QUERYCHILDREN_PARAMETER_TYPES = new Class<?>[] {
            String.class, Querier.class };
    
    private static final Class<?>[] QUERYDESCENDANTS_PARAMETER_TYPES = new Class<?>[] {
            String.class, Querier.class };
    
    private final Class<?> beanClass;
    
    /** 权限类型Manager的实际实现类 */
    private AuthManager delegate;
    
    /** 权限类型缓存 */
    private Cache authCache;
    
    /** 构造函数 */
    public CachingAuthManager(AuthManager delegate, Cache authCache) {
        this.delegate = delegate;
        this.authCache = authCache;
        
        AssertUtils.notNull(delegate, "delegate is null.");
        AssertUtils.notNull(authCache, "authCache is null.");
        
        //对象可能已经被代理，这里需要取到被代理的类
        this.beanClass = ClassUtils.getUserClass(delegate);
    }
    
    /**
     * @param roleTypeId
     * @return
     */
    @Override
    public Auth findAuthById(String authId) {
        String cacheKey = CacheUtils.generateStringCacheKey(this.beanClass,
                "findRoleById",
                FINDBYID_PARAMETER_TYPES,
                new Object[] { authId });
        
        ValueWrapper vw = this.authCache.get(cacheKey);
        if (vw != null && vw.get() != null && Auth.class.isInstance(vw.get())) {
            Auth roleType = (Auth) vw.get();
            return roleType;
        }
        
        Auth auth = this.delegate.findAuthById(authId);
        if (auth != null) {
            this.authCache.put(cacheKey, auth);
        }
        return auth;
    }
    
    /**
     * @param querier
     * @return
     */
    @Override
    public List<Auth> queryAuthList(Querier querier) {
        String cacheKey = CacheUtils.generateStringCacheKey(this.beanClass,
                "queryRoleList",
                QUERYLIST_PARAMETER_TYPES,
                new Object[] { querier });
        
        ValueWrapper vw = this.authCache.get(cacheKey);
        if (vw != null && vw.get() != null && List.class.isInstance(vw.get())) {
            @SuppressWarnings("unchecked")
            List<Auth> resList = (List<Auth>) vw.get();
            return resList;
        }
        
        List<Auth> resList = this.delegate.queryAuthList(querier);
        if (!CollectionUtils.isEmpty(resList)) {
            this.authCache.put(cacheKey, resList);
        }
        return resList;
    }
    
    /**
     * @param parentId
     * @param querier
     * @return
     */
    @Override
    public List<Auth> queryChildrenAuthByParentId(String parentId,
            Querier querier) {
        String cacheKey = CacheUtils.generateStringCacheKey(this.beanClass,
                "queryChildrenRoleByParentId",
                QUERYCHILDREN_PARAMETER_TYPES,
                new Object[] { parentId, querier });
        
        ValueWrapper vw = this.authCache.get(cacheKey);
        if (vw != null && vw.get() != null && List.class.isInstance(vw.get())) {
            @SuppressWarnings("unchecked")
            List<Auth> resList = (List<Auth>) vw.get();
            return resList;
        }
        
        List<Auth> resList = this.delegate.queryChildrenAuthByParentId(parentId,
                querier);
        if (!CollectionUtils.isEmpty(resList)) {
            this.authCache.put(cacheKey, resList);
        }
        return resList;
    }
    
    /**
     * @param parentId
     * @param querier
     * @return
     */
    @Override
    public List<Auth> queryDescendantsAuthByParentId(String parentId,
            Querier querier) {
        String cacheKey = CacheUtils.generateStringCacheKey(this.beanClass,
                "queryDescendantsByParentId",
                QUERYDESCENDANTS_PARAMETER_TYPES,
                new Object[] { parentId, querier });
        
        ValueWrapper vw = this.authCache.get(cacheKey);
        if (vw != null && vw.get() != null && List.class.isInstance(vw.get())) {
            @SuppressWarnings("unchecked")
            List<Auth> resList = (List<Auth>) vw.get();
            return resList;
        }
        
        List<Auth> resList = this.delegate.queryChildrenAuthByParentId(parentId,
                querier);
        if (!CollectionUtils.isEmpty(resList)) {
            this.authCache.put(cacheKey, resList);
        }
        return resList;
    }
    
}
