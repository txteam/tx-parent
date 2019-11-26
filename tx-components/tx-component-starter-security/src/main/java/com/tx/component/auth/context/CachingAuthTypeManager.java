/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.auth.context;

import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.util.ClassUtils;

import com.tx.component.auth.model.AuthType;
import com.tx.core.exceptions.util.AssertUtils;
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
class CachingAuthTypeManager implements AuthTypeManager {
    
    private static final Class<?>[] FINDBYID_PARAMETER_TYPES = new Class<?>[] {
            String.class };
    
    private final Class<?> beanClass;
    
    /** 权限类型Manager的实际实现类 */
    private AuthTypeManager delegate;
    
    /** 权限类型缓存 */
    private Cache authTypeCache;
    
    /** 构造函数 */
    public CachingAuthTypeManager(AuthTypeManager delegate,
            Cache authTypeCache) {
        this.delegate = delegate;
        this.authTypeCache = authTypeCache;
        
        AssertUtils.notNull(delegate, "delegate is null.");
        AssertUtils.notNull(authTypeCache, "authTypeCache is null.");
        
        //对象可能已经被代理，这里需要取到被代理的类
        this.beanClass = ClassUtils.getUserClass(delegate);
        this.authTypeCache.clear();
    }
    
    /**
     * @param authTypeId
     * @return
     */
    public AuthType findAuthTypeById(String authTypeId) {
        String cacheKey = CacheUtils.generateStringCacheKey(this.beanClass,
                "findAuthTypeById",
                FINDBYID_PARAMETER_TYPES,
                new Object[] { authTypeId });
        
        ValueWrapper vw = this.authTypeCache.get(cacheKey);
        if (vw != null && vw.get() != null
                && AuthType.class.isInstance(vw.get())) {
            AuthType authType = (AuthType) vw.get();
            return authType;
        }
        
        AuthType authType = this.delegate.findAuthTypeById(authTypeId);
        if (authType != null) {
            this.authTypeCache.put(cacheKey, authType);
        }
        return authType;
    }

    /**
     * @return
     */
    @Override
    public List<AuthType> queryAuthTypeList() {
        List<AuthType> resList = this.delegate.queryAuthTypeList();
        return resList;
    }
}
