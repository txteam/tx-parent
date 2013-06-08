/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-5-28
 * <修改描述:>
 */
package com.tx.core.support.quartz.spring;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.tx.core.support.quartz.event.SchedulerInitializationComplete;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-5-28]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SchedulerFactoryBean extends
        org.springframework.scheduling.quartz.SchedulerFactoryBean implements
        ApplicationEventPublisherAware, BeanNameAware, ApplicationContextAware {
    
    private ApplicationEventPublisher applicationEventPublisher;
    
    private ApplicationContext applicationContext;
    
    private String schedulerName;
    
    /**
     * @param applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        super.setApplicationContext(applicationContext);
        
        this.applicationContext = applicationContext;
    }
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        super.setBeanName(name);
        this.schedulerName = name;
    }
    
    /**
     * @param applicationEventPublisher
     */
    @Override
    public void setApplicationEventPublisher(
            ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        
        //初始化完成后发出调度器初始化完成事件
        this.applicationEventPublisher.publishEvent(new SchedulerInitializationComplete(
                this.schedulerName, super.getObject(), this.applicationContext));
    }
}
