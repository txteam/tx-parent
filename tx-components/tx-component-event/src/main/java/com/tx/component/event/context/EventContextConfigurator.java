/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月5日
 * <修改描述:>
 */
package com.tx.component.event.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.event.context.impl.DefaultEventListenerSupportFactory;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 事件调度容器配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EventContextConfigurator
        implements InitializingBean, ApplicationContextAware, BeanNameAware {
    
    /** 日志记录器 */
    protected Logger logger = LoggerFactory.getLogger(EventContext.class);
    
    /* 容器注入 */
    /** spring容器 */
    protected ApplicationContext applicationContext;
    
    /** beanName */
    protected String beanName;
    
    /* 如果未注入可自动生成 */
    /** 事务管理器 */
    private PlatformTransactionManager transactionManager;
    
    /** 事件监听器支持类工厂 */
    protected EventListenerSupportFactory eventListenerSupportFactory;
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        this.beanName = name;
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
        AssertUtils.notNull(this.transactionManager, "dataSource is null.");
        
        if (this.eventListenerSupportFactory == null) {
            this.eventListenerSupportFactory = new DefaultEventListenerSupportFactory(this.transactionManager);
        }
    }
    
    /**
     * @param 对transactionManager进行赋值
     */
    public void setTransactionManager(
            PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    
    /**
     * @param 对eventListenerSupportFactory进行赋值
     */
    public void setEventListenerSupportFactory(
            EventListenerSupportFactory eventListenerSupportFactory) {
        this.eventListenerSupportFactory = eventListenerSupportFactory;
    }
}
