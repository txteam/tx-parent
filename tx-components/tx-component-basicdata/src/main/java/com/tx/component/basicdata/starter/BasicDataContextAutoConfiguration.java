/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.basicdata.context.BasicDataContextFactory;
import com.tx.component.basicdata.context.BasicDataServiceRegistry;
import com.tx.component.basicdata.context.BasicDataServiceSupportCacheProxyCreator;
import com.tx.component.basicdata.dao.BasicDataTypeDao;
import com.tx.component.basicdata.dao.DataDictDao;
import com.tx.component.basicdata.dao.impl.BasicDataTypeDaoImpl;
import com.tx.component.basicdata.dao.impl.DataDictDaoImpl;
import com.tx.component.basicdata.service.BasicDataTypeService;
import com.tx.component.basicdata.service.DataDictService;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.mybatis.support.MyBatisDaoSupportHelper;
import com.tx.core.util.dialect.DataSourceTypeEnum;

/**
 * 基础数据容器自动配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年4月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
@EnableConfigurationProperties(BasicDataContextProperties.class)
@ConditionalOnBean({ DataSource.class, PlatformTransactionManager.class })
@AutoConfigureAfter({ DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class })
@ConditionalOnProperty(prefix = "basicdata", value = "enable", havingValue = "true")
public class BasicDataContextAutoConfiguration
        implements ApplicationContextAware, InitializingBean, BeanFactoryAware {
    
    /** mybatis配置文件 */
    protected String[] mybatisMapperLocations = new String[] {
            "classpath*:com/tx/component/basicdata/dao/impl/*SqlMap_BASICDATA.xml" };
    
    /** 属性文件 */
    private BasicDataContextProperties properties;
    
    /** spring 容器句柄 */
    private ApplicationContext applicationContext;
    
    /** 包名 */
    protected String packages = "com.tx";
    
    /** mybatis配置文件 */
    protected String mybatisConfigLocation = "classpath:context/mybatis-config.xml";
    
    /** 数据源:dataSource */
    protected DataSource dataSource;
    
    /** transactionManager */
    protected PlatformTransactionManager transactionManager;
    
    /** transactionTemplate: 如果存在事务则在当前事务中执行 */
    protected TransactionTemplate transactionTemplate;
    
    /** cacheManager */
    protected CacheManager cacheManager;
    
    /** <默认构造函数> */
    public BasicDataContextAutoConfiguration(
            BasicDataContextProperties properties) {
        super();
        this.properties = properties;
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
    
    //    /**
    //     * @param beanFactory
    //     * @throws BeansException
    //     */
    //    @Override
    //    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    //        AssertUtils.isInstanceOf(BeanDefinitionRegistry.class,
    //                beanFactory,
    //                "beanFactory is not BeanDefinitionRegistry instance.");
    //        this.beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;
    //        
    //        AssertUtils.isInstanceOf(SingletonBeanRegistry.class,
    //                beanFactory,
    //                "beanFactory is not SingletonBeanRegistry instance.");
    //        this.singletonBeanRegistry = (SingletonBeanRegistry) beanFactory;
    //    }
    //    
    //    /**
    //     * @desc 向spring容器注册BeanDefinition
    //     * @param beanName
    //     * @param beanDefinition
    //     */
    //    protected void registerBeanDefinition(String beanName,
    //            BeanDefinition beanDefinition) {
    //        if (!this.beanDefinitionRegistry.containsBeanDefinition(beanName)) {
    //            this.beanDefinitionRegistry.registerBeanDefinition(beanName,
    //                    beanDefinition);
    //        }
    //    }
    //    
    //    /**
    //     * @desc 向spring容器注册bean
    //     * @param beanName
    //     * @param beanDefinition
    //     */
    //    protected void registerSingletonBean(String beanName, Object bean) {
    //        if (!this.singletonBeanRegistry.containsSingleton(beanName)) {
    //            this.singletonBeanRegistry.registerSingleton(beanName, bean);
    //        }
    //    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(this.properties.getDataSource(),
                "properties.dataSource is empty");
        AssertUtils.isTrue(
                this.applicationContext
                        .containsBean(this.properties.getDataSource()),
                "applicationContext is not contains datasource:{}",
                new Object[] { this.properties.getDataSource() });
        
        AssertUtils.notEmpty(this.properties.getTransactionManager(),
                "properties.transactionManager is empty");
        AssertUtils.isTrue(
                this.applicationContext
                        .containsBean(this.properties.getTransactionManager()),
                "transactionManager is not contains transactionManager:{}",
                new Object[] { this.properties.getTransactionManager() });
        
        this.dataSource = this.applicationContext
                .getBean(this.properties.getDataSource(), DataSource.class);
        this.transactionManager = this.applicationContext.getBean(
                this.properties.getTransactionManager(),
                PlatformTransactionManager.class);
        
        this.transactionTemplate = new TransactionTemplate(
                this.transactionManager);
        
        //设置cacheManager
        if (StringUtils.isNotBlank(this.properties.getCacheManager())
                && this.applicationContext
                        .containsBean(this.properties.getCacheManager())) {
            this.cacheManager = new ConcurrentMapCacheManager();
        } else if (this.cacheManager == null) {
            this.cacheManager = new ConcurrentMapCacheManager();
        }
        
        //初始化包名
        if (StringUtils.isEmpty(this.properties.getBasePackages())) {
            this.packages = "com.tx";
        } else {
            this.packages = this.properties.getBasePackages();
        }
        
        //        registerSingletonBean("basicdata.dataSource", this.dataSource);
        //        registerSingletonBean("basicdata.transactionTemplate",
        //                this.transactionTemplate);
    }
    
    /**
     * mybatis句柄<br/>
     * <功能详细描述>
     * @return
     * @throws Exception [参数说明]
     * 
     * @return MyBatisDaoSupport [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "basicdata.myBatisDaoSupport")
    public MyBatisDaoSupport basicdata_myBatisDaoSupport() throws Exception {
        MyBatisDaoSupport support = MyBatisDaoSupportHelper
                .buildMyBatisDaoSupport(this.mybatisConfigLocation,
                        this.mybatisMapperLocations,
                        DataSourceTypeEnum.MYSQL,
                        this.dataSource);
        
        return support;
    }
    
    /**
     * 基础数据类型持久层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return BasicDataTypeDao [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "basicdata.basicDataTypeDao")
    public BasicDataTypeDao basicDataTypeDao() {
        BasicDataTypeDao basicDataTypeDao = new BasicDataTypeDaoImpl();
        return basicDataTypeDao;
    }
    
    /**
     * 数据字典类持久层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return DataDictDao [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "basicdata.dataDictDao")
    public DataDictDao dataDictDao() {
        DataDictDao dao = new DataDictDaoImpl();
        return dao;
    }
    
    /**
     * 基础数据类型业务层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return BasicDataTypeService [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "basicdata.basicDataTypeService")
    public BasicDataTypeService basicDataTypeService() {
        BasicDataTypeService basicDataTypeService = new BasicDataTypeService();
        return basicDataTypeService;
    }
    
    /**
     * 数据字典业务层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return DataDictService [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "basicdata.dataDictService")
    public DataDictService dataDictService() {
        DataDictService service = new DataDictService();
        return service;
    }
    
    /**
     * 基础数据业务层代理创建器<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return BasicDataServiceProxyCreator [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @Bean(name = "basicdata.basicDataServiceSupportCacheProxyCreator")
    public BasicDataServiceSupportCacheProxyCreator basicDataServiceSupportCacheProxyCreator() {
        BasicDataServiceSupportCacheProxyCreator creator = new BasicDataServiceSupportCacheProxyCreator(
                this.cacheManager);
        
        return creator;
    }
    
    /**
     * 基础数据容器<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return BasicDataContextFactory [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "basicDataContext")
    public BasicDataContextFactory BasicDataContextFactory() {
        BasicDataContextFactory context = new BasicDataContextFactory();
        
        context.setPackages(this.packages);
        context.setDataSource(this.dataSource);
        context.setTransactionManager(this.transactionManager);
        context.setCacheManager(this.cacheManager);
        
        return context;
    }
    
    @DependsOn(value = "basicDataContext")
    @Bean(name = "basicdata.basicDataServiceRegistry")
    public BasicDataServiceRegistry basicDataServiceRegistry() {
        BasicDataServiceRegistry serviceFactory = new BasicDataServiceRegistry(
                this.packages);
        
        return serviceFactory;
    }
}
