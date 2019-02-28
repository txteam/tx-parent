/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年2月11日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter;

import java.net.UnknownHostException;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;

import redis.clients.jedis.Jedis;

/**
 * 基础数据缓存自动配置类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年2月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
@ConditionalOnClass({ JedisConnection.class, RedisOperations.class, Jedis.class,
        GenericObjectPool.class })
@EnableConfigurationProperties(BasicDataContextProperties.class)
public class BasicDataCacheAutoConfiguration extends RedisAutoConfiguration {
    
    /**
     * 
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2019年2月11日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    @Configuration
    public static class BasicDataRedisAutoConfiguration
            extends RedisAutoConfiguration {
        
        /**
         * 基础数据Redis连接配置<br/>
         * <功能详细描述>
         * 
         * @author  Administrator
         * @version  [版本号, 2019年2月11日]
         * @see  [相关类/方法]
         * @since  [产品/模块版本]
         */
        @Configuration
        protected static class BasicDataRedisConnectionConfiguration
                extends RedisConnectionConfiguration {
            
            /** <默认构造函数> */
            public BasicDataRedisConnectionConfiguration(
                    BasicDataContextProperties properties,
                    ObjectProvider<RedisSentinelConfiguration> sentinelConfiguration,
                    ObjectProvider<RedisClusterConfiguration> clusterConfiguration) {
                super(properties.getRedisProperties(), null, null);
            }
            
            /**
             * 
             * @return
             * @throws UnknownHostException
             */
            @Bean
            @ConditionalOnMissingBean(RedisConnectionFactory.class)
            public JedisConnectionFactory redisConnectionFactory()
                    throws UnknownHostException {
                return super.redisConnectionFactory();
            }
        }
    }
    
}
