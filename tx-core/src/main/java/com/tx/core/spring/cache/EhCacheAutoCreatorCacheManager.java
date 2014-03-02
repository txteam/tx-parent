/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月1日
 * <修改描述:>
 */
package com.tx.core.spring.cache;

import net.sf.ehcache.Ehcache;

import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;


 /**
  * 缓存自动创建器<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年3月1日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class EhCacheAutoCreatorCacheManager extends EhCacheCacheManager {

    /**
     * @param name
     * @return
     */
    @Override
    public Cache getCache(String name) {
        Cache res = super.getCache(name);
        if(res == null){
            getCacheManager().addCache(name);
            Ehcache ehcache = getCacheManager().getCache(name);
            res = new EhCacheCache(ehcache);
            addCache(res);
        }
        return res;
    }
}
