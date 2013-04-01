/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-1-31
 * <修改描述:>
 */
package com.tx.component.rule.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 用以支持规则加载器，自扩展<br/>
 *     支持插拔式的规则加载器的加入<br/>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-1-31]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("ruleLoaderSupportPostProcessor")
public class RuleLoaderSupportPostProcessor implements BeanPostProcessor,
        ApplicationContextAware {
    
    @SuppressWarnings("unused")
    private ApplicationContext applicationContext;
    
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
     * 调用ruleLoader
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if (bean instanceof RuleLoader) {
            RuleLoader realRuleLoader = (RuleLoader) bean;
            RuleContext.registeRuleLoader(realRuleLoader);
        }
        if (bean instanceof RuleRegister) {
            RuleContext.registeRuleValidator((RuleRegister) bean);
        }
        return bean;
    }
    
}
