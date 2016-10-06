/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月6日
 * <修改描述:>
 */
package com.tx.core.cache.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 缓存容器配置器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class CacheContextConfigurator implements ApplicationContextAware,
        InitializingBean {
    
    /** 是否在启动期间清空缓存 */
    protected boolean clearWhenStartup = true;
    
    /** connectionFactory */
    protected JedisConnectionFactory connectionFactory;
    
    /** redis的操作句柄：如果为空，则缓存部分停用Redis */
    protected RedisOperations redisOperations;
    
    /** spring applicationContext容器 */
    protected ApplicationContext applicationContext;
    
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
        RedisTemplate<?,?> rt = new RedisTemplate<>();
        if(connectionFactory != null){
            rt.setConnectionFactory(connectionFactory);
        }
        
        //进行容器构建初始化
        initBuild();
        
        //初始化容器
        initContext();
    }

    protected abstract void initBuild() throws Exception;
    
    protected abstract void initContext() throws Exception;
}
