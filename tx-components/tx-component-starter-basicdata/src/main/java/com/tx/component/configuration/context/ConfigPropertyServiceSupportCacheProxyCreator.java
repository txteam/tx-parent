/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月10日
 * <修改描述:>
 */
package com.tx.component.configuration.context;

import java.lang.reflect.Method;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.tx.component.configuration.ConfigContextConstants;
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
public class ConfigPropertyServiceSupportCacheProxyCreator
        extends AbstractAutoProxyCreator {
    
    /** 注释内容 */
    private static final long serialVersionUID = 2087861805320061268L;
    
    /** 缓存manager */
    private CacheManager cacheManager;
    
    /** <默认构造函数> */
    public ConfigPropertyServiceSupportCacheProxyCreator() {
        super();
        setProxyTargetClass(true);
    }
    
    /** <默认构造函数> */
    public ConfigPropertyServiceSupportCacheProxyCreator(
            CacheManager cacheManager) {
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
        if (ConfigPropertyItemService.class.isAssignableFrom(beanClass)) {
            String cacheName = ConfigContextConstants.CACHE_NAME;
            
            Cache cache = this.cacheManager.getCache(cacheName);
            Object[] interceptors = new Object[] {
                    new ServiceSupportCacheInterceptor(cache) {
                        /**/
                        @Override
                        protected boolean isUseCache(Method method,
                                Object[] args) {
                            String methodName = method.getName();
                            boolean isQueryMethod = methodName
                                    .startsWith("find");
                            return isQueryMethod;
                        }
                    } };
            return interceptors;
        } else {
            return DO_NOT_PROXY;
        }
    }
}
