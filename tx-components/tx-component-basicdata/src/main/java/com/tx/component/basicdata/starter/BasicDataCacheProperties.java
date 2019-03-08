/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

/**
 * 基础数据容器默认配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年4月27日]
 * @see  [相关类/方法]0
 * @since  [产品/模块版本]
 */
//tx.basicdata.cache
public class BasicDataCacheProperties {
    
    /** cacheManager */
    //tx.basicdata.cache.cacheManagerRef
    private String cacheManagerRef;
    
    /** redis缓存属性 */
    //tx.basicdata.cache.redis
    private RedisProperties redis;

    /**
     * @return 返回 cacheManagerRef
     */
    public String getCacheManagerRef() {
        return cacheManagerRef;
    }

    /**
     * @param 对cacheManagerRef进行赋值
     */
    public void setCacheManagerRef(String cacheManagerRef) {
        this.cacheManagerRef = cacheManagerRef;
    }

    /**
     * @return 返回 redis
     */
    public RedisProperties getRedis() {
        return redis;
    }

    /**
     * @param 对redis进行赋值
     */
    public void setRedis(RedisProperties redis) {
        this.redis = redis;
    }
}
