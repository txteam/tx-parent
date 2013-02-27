/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-1-31
 * <修改描述:>
 */
package com.tx.component.rule.context;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.tx.component.rule.model.Rule;

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
        //        if (bean instanceof RuleLoader) {
        //            RuleContext.registerRuleLoader((RuleLoader) bean);
        //            bean = RuleLoaderInvocationHandler.
        //                    Proxy.newProxyInstance(this.getClass().getClassLoader(),
        //                    new Class[] { bean.getClass() },
        //                    new RuleLoaderInvocationHandler((RuleLoader) bean,
        //                            this.applicationContext));
        //        }
        return bean;
    }
    
    /**
     * 调用ruleLoader
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if (bean instanceof RuleLoader) {
            RuleContext.registerRuleLoader((RuleLoader) bean);
            RuleLoader realRuleLoader = (RuleLoader) bean;
            List<Rule> ruleList = realRuleLoader.load();
            this.applicationContext.publishEvent(new LoadRuleEvent(this,
                    realRuleLoader, ruleList));
            //            bean = RuleLoaderInvocationHandler.Proxy.newProxyInstance(this.getClass()
            //                    .getClassLoader(),
            //                    new Class[] { bean.getClass() },
            //                    new RuleLoaderInvocationHandler((RuleLoader) bean,
            //                            this.applicationContext));
        }
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
    @SuppressWarnings("unused")
    private static class RuleLoaderInvocationHandler implements
            MethodInterceptor {
        
        /** ruleLoader原实例 */
        private RuleLoader realRuleLoader;
        
        private ApplicationContext applicationContext;
        
        /** 构造器 */
        public RuleLoaderInvocationHandler(RuleLoader realRuleLoader,
                ApplicationContext applicationContext) {
            super();
            this.realRuleLoader = realRuleLoader;
        }
        
        /**
         * @param arg0
         * @param arg1
         * @param arg2
         * @param arg3
         * @return
         * @throws Throwable
         */
        @Override
        public Object intercept(Object arg0, Method method, Object[] args,
                MethodProxy methodProxy) throws Throwable {
            Object res = methodProxy.invoke(realRuleLoader, args);
            if ("load".equals(method.getName()) && res instanceof List) {
                @SuppressWarnings("unchecked")
                List<Rule> ruleList = (List<Rule>) res;
                this.applicationContext.publishEvent(new LoadRuleEvent(this,
                        realRuleLoader, ruleList));
            }
            return res;
        }
        
        //        /**
        //         * 如果执行了ruleLoader的load方法，通过规则加载事件，将规则发送给规则容器
        //         * @param proxy
        //         * @param method
        //         * @param args
        //         * @return
        //         * @throws Throwable
        //         */
        //        @Override
        //        public Object invoke(Object proxy, Method method, Object[] args)
        //                throws Throwable {
        //            Object res = method.invoke(realRuleLoader, args);
        //            if ("load".equals(method.getName()) && res instanceof List) {
        //                @SuppressWarnings("unchecked")
        //                List<Rule> ruleList = (List<Rule>) res;
        //                this.applicationContext.publishEvent(new LoadRuleEvent(this,
        //                        realRuleLoader, ruleList));
        //            }
        //            
        //            return res;
        //        }
    }
    
}
