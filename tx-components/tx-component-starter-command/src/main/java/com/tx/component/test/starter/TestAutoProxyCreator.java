/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年6月19日
 * <修改描述:>
 */
package com.tx.component.test.starter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;

import com.tx.component.test.service.TestBeanService;

/**
 * 任务执行器工厂<br/>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年6月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TestAutoProxyCreator extends AbstractAutoProxyCreator {
    
    /** 注释内容 */
    private static final long serialVersionUID = 5717953769982845279L;
    
    /** 没有注解的类 */
    private final Map<Class<?>, Boolean> nonAnnotatedClasses = new ConcurrentHashMap<Class<?>, Boolean>(
            64);
    
    /** <默认构造函数> */
    public TestAutoProxyCreator() {
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
        if (this.nonAnnotatedClasses.containsKey(beanClass)) {
            return DO_NOT_PROXY;
        }
        if (!TestBeanService.class.isAssignableFrom(beanClass)) {
            return DO_NOT_PROXY;
        }
        
        if(customTargetSource != null){
            System.out.println("beanClass: " + beanClass.getName());
            System.out.println("customTargetSource: " + customTargetSource);
        }
        
        System.out.println("beanClass: " + beanClass.getName());
        System.out.println("customTargetSource: " + customTargetSource);
        
        return DO_NOT_PROXY;
    }
    
    /**
     * @param proxyFactory
     */
    @Override
    protected void customizeProxyFactory(ProxyFactory proxyFactory) {
        proxyFactory.setProxyTargetClass(true);
    }
    
}
