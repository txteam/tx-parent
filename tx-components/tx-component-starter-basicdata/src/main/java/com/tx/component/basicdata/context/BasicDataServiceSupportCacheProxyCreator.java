/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月10日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.tx.component.basicdata.service.BasicDataService;
import com.tx.component.configuration.service.ConfigPropertyItemService;
import com.tx.core.spring.interceptor.ServiceSupportCacheInterceptor;

/**
 * 基础数据业务层环绕<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataServiceSupportCacheProxyCreator
        extends AbstractAutoProxyCreator {
    
    /** 注释内容 */
    private static final long serialVersionUID = 2087861805320061268L;
    
    /** 缓存manager */
    private CacheManager cacheManager;
    
    /** <默认构造函数> */
    public BasicDataServiceSupportCacheProxyCreator() {
        super();
        setProxyTargetClass(true);
    }
    
    /** <默认构造函数> */
    public BasicDataServiceSupportCacheProxyCreator(CacheManager cacheManager) {
        super();
        this.cacheManager = cacheManager;
        setProxyTargetClass(true);
    }
    
    /**
     * @param beanClass
     * @param beanName
     * @param customTargetSource
     * @return
     * @throws BeansException
     */
    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass,
            String beanName, TargetSource customTargetSource)
            throws BeansException {
        if (!BasicDataService.class.isAssignableFrom(beanClass)
                && !ConfigPropertyItemService.class
                        .isAssignableFrom(beanClass)) {
            return DO_NOT_PROXY;
        }
        
        if (BasicDataService.class.isAssignableFrom(beanClass)) {
            String cacheName = beanName;
            
            if (!beanName.startsWith("basicdata.")) {
                cacheName = (new StringBuilder("basicdata.")).append(beanName)
                        .append("Cache")
                        .toString();
            } else {
                cacheName = (new StringBuilder("basicdata.")).append(
                        StringUtils.substringAfter(beanName, "basicdata."))
                        .append("Cache")
                        .toString();
            }
            
            Cache cache = this.cacheManager.getCache(cacheName);
            if (BasicDataService.class.isAssignableFrom(beanClass)) {
                Object[] interceptors = new Object[] {
                        new BasicDataCacheInterceptor(cache) };
                return interceptors;
            }
        } else if (ConfigPropertyItemService.class
                .isAssignableFrom(beanClass)) {
            String cacheName = beanName;
            
            if (!beanName.startsWith("config.")) {
                cacheName = (new StringBuilder("config.")).append(beanName)
                        .append("Cache")
                        .toString();
            } else {
                cacheName = (new StringBuilder("config."))
                        .append(StringUtils.substringAfter(beanName, "config."))
                        .append("Cache")
                        .toString();
            }
            
            Cache cache = this.cacheManager.getCache(cacheName);
            if (ConfigPropertyItemService.class.isAssignableFrom(beanClass)) {
                Object[] interceptors = new Object[] {
                        new BasicDataCacheInterceptor(cache) };
                return interceptors;
            }
        }
        
        return DO_NOT_PROXY;
    }
}
