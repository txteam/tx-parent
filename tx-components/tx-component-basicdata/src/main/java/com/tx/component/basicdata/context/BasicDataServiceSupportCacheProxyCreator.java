/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月10日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.cache.CacheManager;

import com.tx.core.spring.interceptor.ServiceSupportCacheInterceptor;

/**
 * 基础数据业务层环绕
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataServiceSupportCacheProxyCreator extends
        AbstractAutoProxyCreator {
    
    //private Logger logger = LoggerFactory.getLogger(BasicDataServiceSupportCacheProxyCreator.class);
    
    /** 注释内容 */
    private static final long serialVersionUID = 2087861805320061268L;
    
    @Resource(name = "basicdata.cacheManager")
    private CacheManager cacheManager;
    
    /** <默认构造函数> */
    public BasicDataServiceSupportCacheProxyCreator() {
        super();
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
        if (!BasicDataService.class.isAssignableFrom(beanClass)) {
            return DO_NOT_PROXY;
        }
        
        String cacheNameOfService = beanName;
        if (!beanName.startsWith("basicdata.")) {
            cacheNameOfService = (new StringBuilder("basicdata.service.")).append(beanName)
                    .toString();
        } else {
            cacheNameOfService = (new StringBuilder("basicdata.service.")).append(StringUtils.substringAfter(beanName,
                    "basicdata."))
                    .toString();
        }
        org.springframework.cache.Cache cache = this.cacheManager.getCache(cacheNameOfService);
        if (BasicDataService.class.isAssignableFrom(beanClass)) {
            Object[] interceptors = new Object[] { new ServiceSupportCacheInterceptor(
                    cache) };
            return interceptors;
        }
        return DO_NOT_PROXY;
    }
    
    //    public static void main(String[] args) {
    //        String beanName = "basicdata.testService";
    //        String cacheNameOfService = "";
    //        if (!beanName.startsWith("basicdata.")) {
    //            cacheNameOfService = (new StringBuilder("basicdata.service.")).append(beanName)
    //                    .toString();
    //        } else {
    //            cacheNameOfService = (new StringBuilder("basicdata.service.")).append(StringUtils.substringAfter(beanName,
    //                    "basicdata."))
    //                    .toString();
    //        }
    //        System.out.println(cacheNameOfService);
    //    }
    //    /**
    //     * 在Bean初始化前进行代理类生成<br/>
    //     * @param bean
    //     * @param beanName
    //     * @return
    //     */
    //    public Object postProcessBeforeInitialization(Object bean, String beanName) {
    //        if (bean != null && bean instanceof BasicDataService) {
    //            Object cacheKey = getCacheKey(bean.getClass(), beanName);
    //            Object proxy = wrapIfNecessary(bean, beanName, cacheKey);
    //            
    //            logger.debug("beanName:{} bean:{} bean.getClass:{}", new Object[] {
    //                    beanName, bean, bean.getClass() });
    //            return proxy;
    //        }
    //        return bean;
    //    }
    //    
    //    /**
    //     * Create a proxy with the configured interceptors if the bean is
    //     * identified as one to proxy by the subclass.
    //     * @see #getAdvicesAndAdvisorsForBean
    //     */
    //    @Override
    //    public Object postProcessAfterInitialization(Object bean, String beanName)
    //            throws BeansException {
    //        return bean;
    //    }
    
    /**
     * @param proxyFactory
     */
    @Override
    protected void customizeProxyFactory(ProxyFactory proxyFactory) {
        proxyFactory.setProxyTargetClass(true);
    }
}
