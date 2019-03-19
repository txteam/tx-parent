/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月16日
 * <修改描述:>
 */
package com.tx.component.strategy.context;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tx.component.strategy.annotations.StrategyMapping;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 规则容器构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年3月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class StrategyContextBuilder extends StrategyContextConfigurator
        implements BeanNameAware, ApplicationContextAware, BeanFactoryAware {
    
    /** spring容器句柄 */
    protected static ApplicationContext applicationContext;
    
    /** bean名  */
    protected static String beanName;
    
    /** 单例对象注册方法 */
    protected SingletonBeanRegistry singletonBeanRegistry;
    
    /** Bean定义注册机 */
    protected BeanDefinitionRegistry beanDefinitionRegistry;
    
    /** 策略映射 */
    protected Map<Class<?>, Map<String, Strategy>> strategyMap = new HashMap<>();
    
    /**
     * @param name
     */
    @Override
    public final void setBeanName(String name) {
        StrategyContextBuilder.beanName = name;
    }
    
    /**
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public final void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        AssertUtils.isInstanceOf(BeanDefinitionRegistry.class,
                beanFactory,
                "beanFactory is not BeanDefinitionRegistry instance.");
        this.beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;
        
        AssertUtils.isInstanceOf(SingletonBeanRegistry.class,
                beanFactory,
                "beanFactory is not SingletonBeanRegistry instance.");
        this.singletonBeanRegistry = (SingletonBeanRegistry) beanFactory;
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public final void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        StrategyContextBuilder.applicationContext = applicationContext;
    }
    
    /**
     * @desc 向spring容器注册BeanDefinition
     * @param beanName
     * @param beanDefinition
     */
    protected void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        if (!this.beanDefinitionRegistry.containsBeanDefinition(beanName)) {
            this.beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
        }
    }
    
    /**
     * @desc 向spring容器注册bean
     * @param beanName
     * @param beanDefinition
     */
    protected void registerSingletonBean(String beanName, Object bean) {
        if (!this.singletonBeanRegistry.containsSingleton(beanName)) {
            this.singletonBeanRegistry.registerSingleton(beanName, bean);
        }
    }
    
    /**
     * @throws Exception
     */
    @Override
    protected void doBuild() throws Exception {
        logger.info("策略容器初始化开始......");
        
        Map<String, Strategy> strategyMap = applicationContext.getBeansOfType(Strategy.class);
        parseStrategies(strategyMap);
        
        logger.info("策略容器初始化完毕......");
    }
    
    /**
      * 解析策略集合<br/>
      * <功能详细描述>
      * @param strategies [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void parseStrategies(Map<String, Strategy> strategyMap) {
        if (MapUtils.isEmpty(strategyMap)) {
            return;
        }
        for (Entry<String, Strategy> entryTemp : strategyMap.entrySet()) {
            parseStrategy(entryTemp.getKey(), entryTemp.getValue());
        }
    }
    
    /**
      * 解析策略实现<br/>
      * <功能详细描述>
      * @param beanName
      * @param strategy [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void parseStrategy(String beanName, Strategy strategyObject) {
        List<Class<?>> interfaces = null;
        if(AopUtils.isAopProxy(strategyObject)){
            interfaces = ClassUtils.getAllInterfaces(AopUtils.getTargetClass(strategyObject));
        }else{
            interfaces = ClassUtils.getAllInterfaces(strategyObject.getClass());
        }
        for (Class<?> type : interfaces) {
            if (!Strategy.class.isAssignableFrom(type)) {
                continue;
            }
            Method[] methods = type.getMethods();
            if (ArrayUtils.isEmpty(methods)) {
                continue;
            }
            String strategyName = beanName;
            if (strategyObject.getClass().isAnnotationPresent(StrategyMapping.class)) {
                StrategyMapping sma = strategyObject.getClass().getAnnotation(StrategyMapping.class);
                strategyName = sma.value();
            }
            if (!strategyMap.containsKey(type)) {
                strategyMap.put(type, new HashMap<>());
            }
            strategyMap.get(type).put(strategyName, strategyObject);
        }
    }
}
