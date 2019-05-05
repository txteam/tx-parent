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
import com.tx.component.configuration.context.ConfigCacheInterceptor;
import com.tx.component.configuration.service.ConfigPropertyItemService;

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
        if (BasicDataService.class.isAssignableFrom(beanClass)) {
            String cacheName = beanName;
            if (!beanName.startsWith("basicdata.")) {
                cacheName = (new StringBuilder(beanName)).append(".cache")
                        .toString();
            } else {
                cacheName = (new StringBuilder(
                        StringUtils.substringAfter(beanName, "basicdata.")))
                                .append(".cache").toString();
            }
            
            Cache cache = this.cacheManager.getCache(cacheName);
            Object[] interceptors = new Object[] {
                    new BasicDataCacheInterceptor(cache) };
            return interceptors;
        } else if (ConfigPropertyItemService.class
                .isAssignableFrom(beanClass)) {
            String cacheName = "configPropertyItemService.cache";
            
            Cache cache = this.cacheManager.getCache(cacheName);
            Object[] interceptors = new Object[] {
                    new ConfigCacheInterceptor(cache) };
            return interceptors;
        } else {
            return DO_NOT_PROXY;
        }
    }
}