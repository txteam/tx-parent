/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年6月19日
 * <修改描述:>
 */
package com.tx.component.task.interceptor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyProcessorSupport;
import org.springframework.aop.framework.adapter.AdvisorAdapterRegistry;
import org.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;
import org.springframework.aop.framework.autoproxy.AutoProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.tx.component.task.timedtask.TimedTask;
import com.tx.component.task.timedtask.TimedTaskExecutor;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.AopTargetUtils;

/**
 * 任务执行器工厂<br/>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年6月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TimedTaskExecutorProxyCreator extends ProxyProcessorSupport
        implements BeanPostProcessor, BeanFactoryAware, Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = -980047641854419952L;
    
    /** 任务执行器拦截器工厂 */
    @Resource(name = "taskContext.taskExecuteInterceptorFactory")
    private TaskExecuteInterceptorFactory taskExecuteInterceptorFactory;
    
    /** beanFactory instance */
    private BeanFactory beanFactory;
    
    /** Default is global AdvisorAdapterRegistry */
    private AdvisorAdapterRegistry advisorAdapterRegistry = GlobalAdvisorAdapterRegistry.getInstance();
    
    /** <默认构造函数> */
    public TimedTaskExecutorProxyCreator() {
        super();
        //默认支持类代理 setProxyTargetClass(false);TimedTask都是基于接口的实现，这里无需
    }
    
    /**
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
    
    /**
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
    
    /**
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof TimedTaskExecutor && !AopTargetUtils.isProxy(bean)) {
            Object proxy = createProxy(beanName, (TimedTaskExecutor) bean);
            return proxy;
        } else {
            return bean;
        }
    }
    
    /**
     * Create an AOP proxy for the given bean.
     * @param beanClass the class of the bean
     * @param beanName the name of the bean
     * @param specificInterceptors the set of interceptors that is
     * specific to this bean (may be empty, but not null)
     * @param targetSource the TargetSource for the proxy,
     * already pre-configured to access the bean
     * @return the AOP proxy for the bean
     * @see #buildAdvisors
     */
    protected Object createProxy(String beanName, TimedTaskExecutor timedTaskExecutor) {
        AssertUtils.notTrue(AopTargetUtils.isProxy(timedTaskExecutor), "timedTaskExecutor is proxyed.");
        
        Class<?> beanClass = timedTaskExecutor.getClass();
        TimedTask timedTask = timedTaskExecutor.getTask();
        if (AopTargetUtils.isProxy(timedTask)) {
            timedTask = (TimedTask) AopTargetUtils.getTarget(timedTask);
        }
        
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.copyFrom(this);
        if (!proxyFactory.isProxyTargetClass()) {
            if (shouldProxyTargetClass(beanClass, beanName)) {
                proxyFactory.setProxyTargetClass(true);
            } else {
                evaluateProxyInterfaces(beanClass, proxyFactory);
            }
        }
        
        Method method = MethodUtils.getAccessibleMethod(beanClass, "execute", Date.class);
        Advisor[] advisors = getAdvisors(beanName, timedTask.getClass().getName(), method, timedTask);
        for (Advisor advisor : advisors) {
            proxyFactory.addAdvisor(advisor);
        }
        
        proxyFactory.setTarget(timedTaskExecutor);
        proxyFactory.setFrozen(false);
        proxyFactory.setPreFiltered(false);
        
        return proxyFactory.getProxy(getProxyClassLoader());
    }
    
    /**
     * Determine whether the given bean should be proxied with its target class rather than its interfaces.
     * <p>Checks the {@link AutoProxyUtils#PRESERVE_TARGET_CLASS_ATTRIBUTE "preserveTargetClass" attribute}
     * of the corresponding bean definition.
     * @param beanClass the class of the bean
     * @param beanName the name of the bean
     * @return whether the given bean should be proxied with its target class
     * @see AutoProxyUtils#shouldProxyTargetClass
     */
    protected boolean shouldProxyTargetClass(Class<?> beanClass, String beanName) {
        return (this.beanFactory instanceof ConfigurableListableBeanFactory
                && AutoProxyUtils.shouldProxyTargetClass((ConfigurableListableBeanFactory) this.beanFactory, beanName));
    }
    
    /**
     * Determine the advisors for the given bean, including the specific interceptors
     * as well as the common interceptor, all adapted to the Advisor interface.
     * @param beanName the name of the bean
     * @param specificInterceptors the set of interceptors that is
     * specific to this bean (may be empty, but not null)
     * @return the list of Advisors for the given bean
     */
    protected Advisor[] getAdvisors(String beanName, String className, Method method, TimedTask timedTask) {
        Advisor[] advisors = new Advisor[1];
        TaskExecuteInterceptor interceptor = this.taskExecuteInterceptorFactory.newInterceptor(beanName,
                className,
                method,
                timedTask);
        advisors[0] = this.advisorAdapterRegistry.wrap(interceptor);
        return advisors;
    }
    
}
