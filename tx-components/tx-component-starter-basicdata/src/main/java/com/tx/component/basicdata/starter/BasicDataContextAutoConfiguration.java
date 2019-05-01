/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.basicdata.script.BasicDataContextTableInitializer;
import com.tx.component.basicdata.starter.persister.BasicDataContextHibernateConfig;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.exceptions.util.AssertUtils;

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
@Import(BasicDataPersisterConfiguration.class)
public class BasicDataContextAutoConfiguration
        implements ApplicationContextAware, InitializingBean {
    
    /** spring 容器句柄 */
    private ApplicationContext applicationContext;
    
    /** 属性文件 */
    private BasicDataContextProperties properties;
    
    /** 基础数据扫表包路径 */
    private String basePackages;
    
    /** 容器所属模块：当该值为空时，使用spring.application.name的内容 */
    private String module;
    
    /** application.name */
    @Value(value = "${spring.application.name}")
    private String applicationName;
    
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
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化包名
        if (!StringUtils.isBlank(this.properties.getBasePackages())) {
            this.basePackages = this.properties.getBasePackages();
        }
        if (!StringUtils.isBlank(this.applicationName)) {
            this.module = this.applicationName;
        }
        if (!StringUtils.isEmpty(this.properties.getModule())) {
            this.module = this.properties.getModule();
        }
    }
    
    //    /**
    //     * 基础数据类型持久层<br/>
    //     * <功能详细描述>
    //     * @return [参数说明]
    //     * 
    //     * @return BasicDataTypeDao [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    @Bean(name = "basicdata.basicDataTypeDao")
    //    public BasicDataTypeDao basicDataTypeDao() {
    //        BasicDataTypeDao basicDataTypeDao = new BasicDataTypeDaoImpl(
    //                this.myBatisDaoSupport);
    //        return basicDataTypeDao;
    //    }
    //
    //    /**
    //     * 基础数据类型业务层<br/>
    //     * <功能详细描述>
    //     * @return [参数说明]
    //     * 
    //     * @return BasicDataTypeService [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    @Bean(name = "basicdata.basicDataTypeService")
    //    public BasicDataTypeService basicDataTypeService(
    //            BasicDataTypeDao basicDataTypeDao) {
    //        BasicDataTypeService basicDataTypeService = new BasicDataTypeService(
    //                basicDataTypeDao);
    //        return basicDataTypeService;
    //    }
    
    //    /**
    //     * 基础数据类型业务层<br/>
    //     * <功能详细描述>
    //     * @return [参数说明]
    //     * 
    //     * @return BasicDataTypeService [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    @Bean(name = "basicdata.basicDataTypeService")
    //    public BasicDataTypeService basicDataTypeService() {
    //        BasicDataTypeService basicDataTypeService = new BasicDataTypeService();
    //        return basicDataTypeService;
    //    }
    //    
    //    /**
    //     * 数据字典类持久层<br/>
    //     * <功能详细描述>
    //     * @return [参数说明]
    //     * 
    //     * @return DataDictDao [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    @Bean(name = "basicdata.dataDictDao")
    //    public DataDictDao dataDictDao() {
    //        DataDictDao dao = new DataDictDaoImpl(this.myBatisDaoSupport);
    //        return dao;
    //    }
    //    
    //    /**
    //     * 数据字典业务层<br/>
    //     * <功能详细描述>
    //     * @return [参数说明]
    //     * 
    //     * @return DataDictService [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    @Bean(name = "basicdata.dataDictService")
    //    public DataDictService dataDictService(DataDictDao dataDictDao) {
    //        DataDictService service = new DataDictService(this.dataSource,
    //                this.transactionTemplate, dataDictDao);
    //        return service;
    //    }
    
    //    /**
    //     * 基础数据业务层代理创建器<br/>
    //     * <功能详细描述>
    //     * @return [参数说明]
    //     * 
    //     * @return BasicDataServiceProxyCreator [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //    */
    //    @Bean(name = "basicdata.basicDataServiceSupportCacheProxyCreator")
    //    public BasicDataServiceSupportCacheProxyCreator basicDataServiceSupportCacheProxyCreator() {
    //        BasicDataServiceSupportCacheProxyCreator creator = new BasicDataServiceSupportCacheProxyCreator(
    //                this.cacheManager);
    //        
    //        return creator;
    //    }
    //    
    //    /**
    //     * 基础数据容器<br/>
    //     * <功能详细描述>
    //     * @return [参数说明]
    //     * 
    //     * @return BasicDataContextFactory [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    @Bean(name = "basicDataContext")
    //    public BasicDataContextFactory BasicDataContextFactory() {
    //        BasicDataContextFactory context = new BasicDataContextFactory();
    //        
    //        return context;
    //    }
    //    
    //    /**
    //     * 基础数据业务层注册机<br/>
    //     * <功能详细描述>
    //     * @param basicDataTypeService
    //     * @param dataDictService
    //     * @return [参数说明]
    //     * 
    //     * @return BasicDataServiceRegistry [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    @DependsOn(value = "basicDataContext")
    //    @Bean(name = "basicdata.basicDataServiceRegistry")
    //    public BasicDataServiceRegistry basicDataServiceRegistry(
    //            BasicDataTypeService basicDataTypeService,
    //            DataDictService dataDictService) {
    //        BasicDataServiceRegistry serviceFactory = new BasicDataServiceRegistry(
    //                module, basePackages, basicDataTypeService, dataDictService,
    //                null);
    //        
    //        return serviceFactory;
    //    }
    
    /**
     * 基础数据业务层注册<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2019年4月30日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    @Import(BasicDataPersisterConfiguration.class)
    public static class BasicDataServiceImportRegistrar
            implements BeanFactoryAware, ImportBeanDefinitionRegistrar,
            ResourceLoaderAware, InitializingBean {
        
        @SuppressWarnings("unused")
        private BeanFactory beanFactory;
        
        @SuppressWarnings("unused")
        private ResourceLoader resourceLoader;
        
        /** <默认构造函数> */
        //第一个被调用
        public BasicDataServiceImportRegistrar() {
            super();
        }
        
        @PostConstruct
        public void afterPropertiesSet() {
            System.out.println(
                    "TestContextAutoInnerImportRegistrar afterPropertiesSet. called");
        }
        
        @PostConstruct
        public void postConstruct() {
            System.out.println(
                    "TestContextAutoInnerImportRegistrar postConstruct. called");
        }
        
        //低而个被调用
        @Override
        public void registerBeanDefinitions(
                AnnotationMetadata importingClassMetadata,
                BeanDefinitionRegistry registry) {
            BeanDefinition bd = BeanDefinitionBuilder
                    .genericBeanDefinition(TestBeanRegiste.class)
                    .getBeanDefinition();
            
            System.out.println(
                    "TestContextAutoInnerImportRegistrar registerBeanDefinitions. called");
            registry.registerBeanDefinition("testRegiste", bd);
        }
        
        @Override
        public void setBeanFactory(BeanFactory beanFactory)
                throws BeansException {
            this.beanFactory = beanFactory;
        }
        
        @Override
        public void setResourceLoader(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader;
        }
    }
    
}
