/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-4-5
 * <修改描述:>
 */
package com.tx.core.spring.processor;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

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
        BeanPostProcessor, ApplicationListener<ContextRefreshedEvent> {
    
    private Logger logger = LoggerFactory.getLogger(BeansInitializedEventSupportPostProcessor.class);
    
    private Map<String, T> beansMapping = new HashMap<String, T>();
    
    private boolean isBeansInitializedEventPublished = false;
    
    /**
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("on ContextRefreshedEvent ...");
        if (!isBeansInitializedEventPublished) {
            isBeansInitializedEventPublished = true;
            event.getApplicationContext()
                    .publishEvent(new BeansInitializedEvent<T>(
                            event.getApplicationContext(), beanType(),
                            beansMapping));
            logger.info("publish BeansInitializedEvent beanType:{} beans.size:{} ...",beanType().getName(),beansMapping.size());
        }else{
            logger.info("publish BeansInitializedEvent not need republish. ...");
        }
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
        if (beanType().isAssignableFrom(bean.getClass())) {
            beansMapping.put(beanName, null);
        }
        return bean;
    }
    
    /**
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if (beansMapping.containsKey(beanName)) {
            beansMapping.put(beanName, (T) bean);
        }
        return bean;
    }
    
    public abstract Class<T> beanType();
    
}
