/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-4-5
 * <修改描述:>
 */
package com.tx.core.spring.processor;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tx.core.spring.event.BeansInitializedEvent;

/**
 * bean初始化事件支持类
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-4-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class BeansInitializedEventSupportPostProcessor<T> implements
        BeanPostProcessor, ApplicationContextAware {
    
    private ApplicationContext applicationContext;
    
    private Set<String> waitInitRuleLoaderNameSet = new HashSet<String>();

    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
        waitInitRuleLoaderNameSet.addAll(this.applicationContext.getBeansOfType(beanType()).keySet());
    }

    /**
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }
    
    /**
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if (waitInitRuleLoaderNameSet.contains(beanName)) {
            waitInitRuleLoaderNameSet.remove(beanName);
            if (CollectionUtils.isEmpty(waitInitRuleLoaderNameSet)) {
                this.applicationContext.publishEvent(new BeansInitializedEvent<T>(
                        this.applicationContext, beanType()));
            }
        }
        return bean;
    }
    
    public abstract Class<T> beanType();
    
}
