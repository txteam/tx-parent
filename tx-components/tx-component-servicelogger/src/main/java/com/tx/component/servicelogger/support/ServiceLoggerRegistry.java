/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月9日
 * <修改描述:>
 */
package com.tx.component.servicelogger.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.AliasRegistry;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.servicelogger.annotation.ServiceLog;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.annotation.AutoPersistEntitySupport;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.util.ClassScanUtils;

/**
 * 实体自动持久注册机<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ServiceLoggerRegistry implements ApplicationContextAware,
        InitializingBean, BeanFactoryAware, BeanNameAware {
    
    private Logger logger = LoggerFactory
            .getLogger(ServiceLoggerRegistry.class);
    
    private static String beanName;
    
    private static ApplicationContext applicationContext;
    
    private static ServiceLoggerRegistry instance;
    
    private AliasRegistry aliasRegistry;
    
    private BeanDefinitionRegistry beanDefinitionRegistry;
    
    private SingletonBeanRegistry singletonBeanRegistry;
    
    /** 实体持久层实现映射 */
    private static Map<Class<?>, String> type2nameMap = new HashMap<Class<?>, String>();
    
    /** 实体持久层实现映射 */
    private static Map<String, ServiceLogger<?>> name2serviceMap = new HashMap<String, ServiceLogger<?>>();
    
    /** 扫描包范围 */
    private String basePackages;
    
    /** mybatisDaoSupport句柄 */
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /** transactionTemplate句柄 */
    private TransactionTemplate transactionTemplate;
    
    /** <默认构造函数> */
    public ServiceLoggerRegistry() {
        super();
    }
    
    /** <默认构造函数> */
    public ServiceLoggerRegistry(String basePackages,
            MyBatisDaoSupport myBatisDaoSupport,
            TransactionTemplate transactionTemplate) {
        super();
        this.basePackages = basePackages;
        this.myBatisDaoSupport = myBatisDaoSupport;
        this.transactionTemplate = transactionTemplate;
    }
    
    /**
     * 单例基础数据工厂类<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return BasicDataServiceFactory [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static ServiceLoggerRegistry getInstance() {
        AssertUtils.notEmpty(ServiceLoggerRegistry.beanName,
                "beanName is empty.");
        
        if (ServiceLoggerRegistry.instance == null) {
            ServiceLoggerRegistry.instance = applicationContext.getBean(
                    ServiceLoggerRegistry.beanName,
                    ServiceLoggerRegistry.class);
        }
        
        AssertUtils.notNull(ServiceLoggerRegistry.instance,
                "factory not inited.");
        
        return instance;
    }
    
    /**
     * 单例基础数据工厂类<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return BasicDataServiceFactory [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @SuppressWarnings("unchecked")
    public static <T> ServiceLogger<T> getLoggerService(Class<T> modelType) {
        AssertUtils.notNull(modelType, "modelType is null.");
        AssertUtils.isTrue(type2nameMap.containsKey(modelType),
                "type2nameMap is not contains:{}",
                new Object[] { modelType });
        
        AssertUtils.notNull(ServiceLoggerRegistry.applicationContext,
                "applicationContext is null.");
        
        //实体持久层Bean名称
        String entityDaoName = type2nameMap.get(modelType);
        ServiceLogger<T> loggerService = null;
        if (name2serviceMap.containsKey(entityDaoName)) {
            loggerService = (ServiceLogger<T>) name2serviceMap
                    .get(entityDaoName);
        } else {
            loggerService = (ServiceLogger<T>) applicationContext
                    .getBean(entityDaoName, Logger.class);
            name2serviceMap.put(entityDaoName, loggerService);
        }
        
        return loggerService;
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ServiceLoggerRegistry.applicationContext = applicationContext;
    }
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String beanName) {
        ServiceLoggerRegistry.beanName = beanName;
    }
    
    /**
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        AssertUtils.isInstanceOf(BeanDefinitionRegistry.class,
                beanFactory,
                "beanFactory is not BeanDefinitionRegistry instance.");
        this.beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;
        
        AssertUtils.isInstanceOf(AliasRegistry.class,
                beanFactory,
                "beanFactory is not SingletonBeanRegistry instance.");
        this.aliasRegistry = (AliasRegistry) beanFactory;
        
        AssertUtils.isInstanceOf(SingletonBeanRegistry.class,
                beanFactory,
                "beanFactory is not SingletonBeanRegistry instance.");
        this.singletonBeanRegistry = (SingletonBeanRegistry) beanFactory;
    }
    
    /**
     * @desc 向spring容器注册BeanDefinition
     * @param beanName
     * @param beanDefinition
     */
    protected void registerBeanDefinition(String beanName,
            BeanDefinition beanDefinition) {
        if (!this.beanDefinitionRegistry.containsBeanDefinition(beanName)) {
            logger.debug(
                    "动态注入基础数据业务层定义: beanName:{} Type:com.tx.component.basicdata.context.DefaultBasicDataService",
                    beanName);
            this.beanDefinitionRegistry.registerBeanDefinition(beanName,
                    beanDefinition);
        }
    }
    
    /**
     * @desc 向spring容器注册bean
     * @param beanName
     * @param beanDefinition
     */
    protected void registerSingletonBean(String beanName, Object bean) {
        if (!this.singletonBeanRegistry.containsSingleton(beanName)) {
            logger.debug("注入基础数据业务层实例: beanName:{} Type:{}",
                    beanName,
                    bean.getClass().getName());
            this.singletonBeanRegistry.registerSingleton(beanName, bean);
        }
    }
    
    /**
     * @desc 向spring容器注册bean
     * @param beanName
     * @param beanDefinition
     */
    protected void registerAlise(String beanName, String alias) {
        if (!this.aliasRegistry.isAlias(beanName)
                && !this.aliasRegistry.isAlias(alias)) {
            this.aliasRegistry.registerAlias(beanName, alias);
        }
    }
    
    /**
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void afterPropertiesSet() throws Exception {
        //查找spring容器中已经存在的业务层
        Map<String, ServiceLogger> loggerServiceMap = ServiceLoggerRegistry.applicationContext
                .getBeansOfType(ServiceLogger.class);
        
        for (Entry<String, ServiceLogger> entry : loggerServiceMap.entrySet()) {
            ServiceLogger dao = entry.getValue();
            String beanName = entry.getKey();
            if (dao.getLoggerType() == null
                    || !Class.class.isInstance(dao.getLoggerType())
                    || Object.class.equals(dao.getLoggerType())) {
                continue;
            }
            Class<?> beanType = (Class<?>) dao.getLoggerType();
            if (!beanType.isAnnotationPresent(ServiceLog.class)) {
                continue;
            }
            
            String generateDaoName = generateServiceLoggerNameByType(beanType);
            //注册单例Bean进入Spring容器
            //registerSingletonBean(beanName, service);
            if (!beanName.equals(generateDaoName)) {
                registerAlise(beanName, generateDaoName);
            }
            
            //注册处理的业务类型
            ServiceLoggerRegistry.type2nameMap.put(beanType, generateDaoName);
        }
        
        //扫描遍历，如果已经存在持久层的实体类，则不再添加
        String[] basePackageArray = StringUtils
                .splitByWholeSeparator(basePackages, ",");
        Set<Class<?>> types = ClassScanUtils.scanByAnnotation(
                AutoPersistEntitySupport.class, basePackageArray);
        
        for (Class<?> beanType : types) {
            //注册实体持久层
            BeanDefinition daoBeanDefinition = generateServiceLoggerBeanDefinition(
                    beanType, this.myBatisDaoSupport, this.transactionTemplate);
            String loggerServiceName = generateServiceLoggerNameByType(
                    beanType);
            
            registerBeanDefinition(loggerServiceName, daoBeanDefinition);
            
            ServiceLoggerRegistry.type2nameMap.put(beanType, loggerServiceName);
        }
    }
    
    /**
     * 注册实体的持久层实现<br/>
     * <功能详细描述>
     * @param registry
     * @param beanType
     * @param myBatisDaoSupport [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private BeanDefinition generateServiceLoggerBeanDefinition(
            Class<?> beanType, MyBatisDaoSupport myBatisDaoSupport,
            TransactionTemplate transactionTemplate) {
        AssertUtils.notNull(beanType, "beanType is null.");
        AssertUtils.notNull(myBatisDaoSupport, "myBatisDaoSupport is null.");
        
        BeanDefinitionBuilder builder = BeanDefinitionBuilder
                .genericBeanDefinition(ServiceLoggerFactory.class);
        builder.addConstructorArgValue(beanType);
        builder.addConstructorArgValue(myBatisDaoSupport);
        builder.addConstructorArgValue(transactionTemplate);
        
        return builder.getBeanDefinition();
    }
    
    /**
     * 根据类型获取bean名称<br/>
     * <功能详细描述>
     * @param beanType
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String generateServiceLoggerNameByType(Class<?> beanType) {
        AssertUtils.notNull(beanType, "beanType is null.");
        
        String beanName = beanType.getName() + "Dao";
        return beanName;
    }
    
    /**
     * @return 返回 basePackages
     */
    public String getBasePackages() {
        return basePackages;
    }
    
    /**
     * @param 对basePackages进行赋值
     */
    public void setBasePackages(String basePackages) {
        this.basePackages = basePackages;
    }
    
    /**
     * @return 返回 myBatisDaoSupport
     */
    public MyBatisDaoSupport getMyBatisDaoSupport() {
        return this.myBatisDaoSupport;
    }
    
    /**
     * @param 对myBatisDaoSupport进行赋值
     */
    public void setMyBatisDaoSupport(MyBatisDaoSupport myBatisDaoSupport) {
        this.myBatisDaoSupport = myBatisDaoSupport;
    }
}
