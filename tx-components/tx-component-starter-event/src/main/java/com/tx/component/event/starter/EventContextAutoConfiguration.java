/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月1日
 * <修改描述:>
 */
package com.tx.component.event.starter;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.event.context.EventContextFactory;
import com.tx.component.event.context.EventListenerSupportFactory;
import com.tx.component.event.context.loader.AnnotationEventListenerLoader;
import com.tx.component.event.listener.resolver.impl.EventListenerMethodEventArgumentResolver;
import com.tx.component.event.listener.resolver.impl.EventListenerParamMapMethodArgumentResolver;
import com.tx.component.event.listener.resolver.impl.EventListenerParamMethodArgumentResolver;

/**
 * 命令容器配置器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
@EnableConfigurationProperties(value = EventContextProperties.class)
@ConditionalOnClass({ EventContextFactory.class })
@ConditionalOnSingleCandidate(DataSource.class)
@ConditionalOnBean(PlatformTransactionManager.class)
@AutoConfigureAfter(TransactionAutoConfiguration.class)
@ConditionalOnProperty(prefix = "tx.event", value = "enable", havingValue = "true")
public class EventContextAutoConfiguration
        implements ApplicationContextAware, InitializingBean {
    
    private ApplicationContext applicationContext;
    
    private final EventContextProperties properties;
    
    private final PlatformTransactionManager transactionManager;
    
    private EventListenerSupportFactory eventListenerSupportFactory;
    
    /** <默认构造函数> */
    public EventContextAutoConfiguration(EventContextProperties properties,
            PlatformTransactionManager transactionManager) {
        super();
        this.properties = properties;
        this.transactionManager = transactionManager;
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
        if (StringUtils.isNotBlank(properties.getEventListenerSupportFactory())
                && this.applicationContext.containsBean(
                        properties.getEventListenerSupportFactory())) {
            this.eventListenerSupportFactory = this.applicationContext.getBean(
                    properties.getEventListenerSupportFactory(),
                    EventListenerSupportFactory.class);
        }
    }
    
    /**
     * 该类会优先加载<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2018年5月5日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    @Configuration
    public static class EventContextArgumentResolverConfiguration {
        
        public EventContextArgumentResolverConfiguration() {
        }
        
        @Bean(name = "eventListenerMethodEventArgumentResolver")
        public EventListenerMethodEventArgumentResolver eventListenerMethodEventArgumentResolver() {
            EventListenerMethodEventArgumentResolver resolver = new EventListenerMethodEventArgumentResolver();
            return resolver;
        }
        
        @Bean(name = "eventListenerParamMapMethodArgumentResolver")
        public EventListenerParamMapMethodArgumentResolver eventListenerParamMapMethodArgumentResolver() {
            EventListenerParamMapMethodArgumentResolver resolver = new EventListenerParamMapMethodArgumentResolver();
            return resolver;
        }
        
        @Bean(name = "eventListenerParamMethodArgumentResolver")
        public EventListenerParamMethodArgumentResolver eventListenerParamMethodArgumentResolver() {
            EventListenerParamMethodArgumentResolver resolver = new EventListenerParamMethodArgumentResolver();
            return resolver;
        }
        
        @Bean(name = "annotationEventListenerLoader")
        public AnnotationEventListenerLoader annotationEventListenerLoader() {
            AnnotationEventListenerLoader loader = new AnnotationEventListenerLoader();
            return loader;
        }
    }
    
    @Bean(name = "eventContext")
    public EventContextFactory eventContext() {
        EventContextFactory eventContextFactory = new EventContextFactory();
        eventContextFactory.setEventListenerSupportFactory(
                this.eventListenerSupportFactory);
        eventContextFactory.setTransactionManager(this.transactionManager);
        
        return eventContextFactory;
    }
}
