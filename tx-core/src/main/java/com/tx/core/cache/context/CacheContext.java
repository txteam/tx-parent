/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月6日
 * <修改描述:>
 */
package com.tx.core.cache.context;

import java.util.Collection;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * 缓存容器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CacheContext extends CacheContextBuilder implements CacheManager {
    
    private static CacheContext cacheContext;
    
    public static CacheContext getContext() {
        return CacheContext.cacheContext;
    }
    
    /**
     * @throws Exception
     */
    @Override
    protected void initContext() throws Exception {
        cacheContext = this;
    }
    
    /**
     * @param name
     * @return
     */
    @Override
    public Cache getCache(String name) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @return
     */
    @Override
    public Collection<String> getCacheNames() {
        // TODO Auto-generated method stub
        return null;
    }
}
