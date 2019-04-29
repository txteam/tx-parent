/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年3月7日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter.cache;

import org.springframework.cache.CacheManager;

/**
 * 基础数据持久化配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年3月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataContextCacheConfig {
    
    /** 缓存管理器 */
    private CacheManager cacheManager;
    
    /**
     * @return 返回 cacheManager
     */
    public CacheManager getCacheManager() {
        return cacheManager;
    }
    
    /**
     * @param 对cacheManager进行赋值
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
