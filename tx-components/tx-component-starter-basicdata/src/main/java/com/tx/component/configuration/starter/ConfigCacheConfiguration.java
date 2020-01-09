/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年4月30日
 * <修改描述:>
 */
package com.tx.component.configuration.starter;

import java.time.Duration;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.transaction.TransactionAwareCacheManagerProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tx.component.configuration.ConfigContextConstants;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 基础数据持久层配置逻辑<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年4月30日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
public class ConfigCacheConfiguration implements ApplicationContextAware {
    
    /** spring容器句柄 */
    private ApplicationContext applicationContext;
    
    /** 基础数据容器属性 */
    private ConfigContextProperties properties;
    
    /** 缓存的有效期 */
    /*
     * <pre>
     *    "PT20.345S" -- parses as "20.345 seconds"
     *    "PT15M"     -- parses as "15 minutes" (where a minute is 60 seconds)
     *    "PT10H"     -- parses as "10 hours" (where an hour is 3600 seconds)
     *    "P2D"       -- parses as "2 days" (where a day is 24 hours or 86400 seconds)
     *    "P2DT3H4M"  -- parses as "2 days, 3 hours and 4 minutes"
     *    "P-6H3M"    -- parses as "-6 hours and +3 minutes"
     *    "-P6H3M"    -- parses as "-6 hours and -3 minutes"
     *    "-P-6H+3M"  -- parses as "+6 hours and -3 minutes"
     * </pre>
     */
    private Duration duration;
    
    /** <默认构造函数> */
    public ConfigCacheConfiguration(ConfigContextProperties properties) {
        super();
        this.properties = properties;
        
        if (this.properties.getDuration() == null
                || this.properties.getDuration().toMillis() <= 0) {
            this.duration = Duration.ofDays(1);
        } else {
            this.duration = this.properties.getDuration();
        }
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /**
     * 基础数据缓存定义<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return BasicDataCacheCustomizer [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean
    @ConditionalOnMissingBean(ConfigCacheCustomizer.class)
    @ConditionalOnProperty(prefix = ConfigContextConstants.PROPERTIES_PREFIX, value = "cache-manager-ref", matchIfMissing = false) 
    public ConfigCacheCustomizer configRefCacheCustomizer() {
        CacheManager cacheManager = null;
        if (this.applicationContext
                .containsBean(this.properties.getCacheManagerRef())) {
            cacheManager = this.applicationContext.getBean(
                    this.properties.getCacheManagerRef(), CacheManager.class);
        }
        
        AssertUtils.notNull(cacheManager, "cacheManager is null.");
        ConfigCacheCustomizer customizer = new ConfigCacheCustomizer();
        customizer.setCacheManager(cacheManager);
        return customizer;
    }
    
    /**
     * 该类会优先加载:基础数据容器表初始化器<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2018年5月5日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    @Bean
    @ConditionalOnMissingBean(ConfigCacheCustomizer.class)
    @ConditionalOnClass(RedisOperations.class)
    @ConditionalOnProperty(prefix = ConfigContextConstants.PROPERTIES_PREFIX, value = "cache-manager-ref", matchIfMissing = true)
    public ConfigCacheCustomizer configRedisCacheCustomizer(
            RedisConnectionFactory factory) {
        CacheManager cacheManager = configContextRedisCacheManager(factory);
        
        AssertUtils.notNull(cacheManager, "cacheManager is null.");
        ConfigCacheCustomizer customizer = new ConfigCacheCustomizer();
        customizer.setCacheManager(cacheManager);
        return customizer;
    }
    
    /**
     * 生成RedisCacheManager<br/>
     * <功能详细描述>
     * @param factory
     * @return [参数说明]
     * 
     * @return CacheManager [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private CacheManager configContextRedisCacheManager(
            RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(
                Object.class);
        
        //解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        
        // 配置序列化（解决乱码的问题）
        RedisCacheConfiguration config = RedisCacheConfiguration
                .defaultCacheConfig()
                //prefix?
                .entryTtl(duration)
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(jackson2JsonRedisSerializer))
                .disableCachingNullValues();
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
        return cacheManager;
    }
    
    /**
     * 当没有Redis时，构建内存缓存<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2019年5月4日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    @Bean
    @ConditionalOnMissingBean(ConfigCacheCustomizer.class)
    @ConditionalOnMissingClass({
            "org.springframework.data.redis.core.RedisOperations" })
    @ConditionalOnProperty(prefix = ConfigContextConstants.PROPERTIES_PREFIX, value = "cache-manager-ref", matchIfMissing = true)
    public ConfigCacheCustomizer configLocalCacheCustomizer() {
        CacheManager local = new ConcurrentMapCacheManager();
        CacheManager cacheManager = new TransactionAwareCacheManagerProxy(
                local);
        
        AssertUtils.notNull(cacheManager, "cacheManager is null.");
        ConfigCacheCustomizer customizer = new ConfigCacheCustomizer();
        customizer.setCacheManager(cacheManager);
        return customizer;
    }
    
    /**
     * 缓存定制器<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2019年5月4日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public static class ConfigCacheCustomizer {
        
        /** 缓存manager */
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
}
