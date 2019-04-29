/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter.cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * 基础数据容器自动配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年4月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
@EnableConfigurationProperties(BasicDataCacheProperties.class)
public class BasicDataCacheConfiguration
        implements ApplicationContextAware, InitializingBean {
    
    /** spring 容器句柄 */
    private ApplicationContext applicationContext;
    
    /** 缓存属性 */
    private BasicDataCacheProperties properties;
    
    /** cacheManager */
    protected CacheManager cacheManager;
    
    /** <默认构造函数> */
    public BasicDataCacheConfiguration(BasicDataCacheProperties properties) {
        super();
        this.properties = properties;
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
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //设置cacheManager
        if (StringUtils.isNotBlank(this.properties.getCacheManagerRef())
                && this.applicationContext
                        .containsBean(this.properties.getCacheManagerRef())) {
            this.cacheManager = (CacheManager) this.applicationContext
                    .getBean(this.properties.getCacheManagerRef());
        } else {
            //RedisCacheManager rcm = RedisSupportHelper.buildRedisConnectionFactory(properties, sentinelConfiguration, clusterConfiguration);
            //rcm.setDefaultExpiration(defaultExpireTime);
        }
    }
    
}
