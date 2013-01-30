/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-1-31
 * <修改描述:>
 */
package com.tx.component.rule.context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 用以支持规则加载器，自扩展<br/>
 *     支持插拔式的规则加载器的加入<br/>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-1-31]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleLoaderSupportPostProcessor implements BeanPostProcessor {
    
    /**
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        if (bean instanceof RuleLoader) {
            bean = Proxy.newProxyInstance(this.getClass().getClassLoader(),
                    new Class[] { RuleLoader.class },
                    new RuleLoaderInvocationHandler((RuleLoader) bean));
        } else if(bean instanceof RulesLoader){
            bean = Proxy.newProxyInstance(this.getClass().getClassLoader(),
                    new Class[] { RuleLoader.class },
                    new RuleLoaderInvocationHandler((RuleLoader) bean));
        }
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
        return bean;
    }
    
    /**
      * 规则加载器注入句柄
      * <功能详细描述>
      * 
      * @author  PengQingyang
      * @version  [版本号, 2013-1-31]
      * @see  [相关类/方法]
      * @since  [产品/模块版本]
     */
    private static class RuleLoaderInvocationHandler implements
            InvocationHandler {
        
        /** ruleLoader原实例 */
        private RuleLoader ruleLoaderDelegate;
        
        /** 构造器 */
        public RuleLoaderInvocationHandler(RuleLoader ruleLoaderDelegate) {
            super();
            this.ruleLoaderDelegate = ruleLoaderDelegate;
        }
        
        /**
         * @param proxy
         * @param method
         * @param args
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            return null;
        }
    }
    
}
