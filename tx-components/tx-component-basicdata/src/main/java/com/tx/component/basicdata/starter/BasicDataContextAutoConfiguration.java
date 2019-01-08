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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.basicdata.context.BasicDataContextFactory;
import com.tx.component.basicdata.context.BasicDataServiceRegistry;
import com.tx.component.basicdata.context.BasicDataServiceSupportCacheProxyCreator;
import com.tx.component.basicdata.controller.BasicDataRemoteController;
import com.tx.component.basicdata.controller.BasicDataTypeController;
import com.tx.component.basicdata.dao.DataDictDao;
import com.tx.component.basicdata.dao.impl.DataDictDaoImpl;
import com.tx.component.basicdata.script.BasicDataContextTableInitializer;
import com.tx.component.basicdata.service.BasicDataTypeService;
import com.tx.component.basicdata.service.DataDictService;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
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
public class BasicDataContextAutoConfiguration
        implements ApplicationContextAware, InitializingBean {
    
    /** mybatis配置文件 */
    protected String[] mybatisMapperLocations = new String[] {
            "classpath*:com/tx/component/basicdata/dao/impl/*SqlMap_BASICDATA.xml" };
    
    /** 包名 */
    protected String basePackages = "com.tx";
    
    /** mybatis配置文件 */
    protected String mybatisConfigLocation = "classpath:context/mybatis-config.xml";
    
    /** 属性文件 */
    private BasicDataContextProperties properties;
    
    /** spring 容器句柄 */
    private ApplicationContext applicationContext;
    
    /** 数据源:dataSource */
    protected DataSource dataSource;
    
    /** transactionManager */
    protected PlatformTransactionManager transactionManager;
    
    /** cacheManager */
    protected CacheManager cacheManager;
    
    /** transactionTemplate: 如果存在事务则在当前事务中执行 */
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /** transactionTemplate: 如果存在事务则在当前事务中执行 */
    private TransactionTemplate transactionTemplate;
    
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
    	BasicDataContextServerProperties server = this.properties.getServer();
    	
        //设置dataSource
        if (server!=null && StringUtils.isNotBlank(server.getDataSourceRef())
                && this.applicationContext
                        .containsBean(server.getDataSourceRef())) {
            this.dataSource = this.applicationContext.getBean(
            		server.getDataSourceRef(), DataSource.class);
        } else if (this.applicationContext.getBeansOfType(DataSource.class)
                .size() == 1) {
            this.dataSource = this.applicationContext.getBean(DataSource.class);
        }
        AssertUtils.notEmpty(this.dataSource,
                "dataSource is null.存在多个数据源，需要通过basicdata.dataSource指定使用的数据源,或为数据源设置为Primary.");
        
        //设置transactionManager
        if (server!=null && StringUtils.isNotBlank(server.getTransactionManagerRef())
                && this.applicationContext.containsBean(
                		server.getTransactionManagerRef())) {
            this.transactionManager = this.applicationContext.getBean(
            		server.getTransactionManagerRef(),
                    PlatformTransactionManager.class);
        } else if (this.applicationContext
                .getBeansOfType(PlatformTransactionManager.class).size() == 1) {
            this.transactionManager = this.applicationContext
                    .getBean(PlatformTransactionManager.class);
        } else {
            this.transactionManager = new DataSourceTransactionManager(
                    this.dataSource);
        }
        AssertUtils.notEmpty(this.transactionManager,
                "transactionManager is null.存在多个事务管理器，需要通过basicdata.transactionManager指定使用的数据源,或为数据源设置为Primary.");
        
        //设置cacheManager
        if (StringUtils.isNotBlank(this.properties.getCacheManagerRef())
                && this.applicationContext
                        .containsBean(this.properties.getCacheManagerRef())) {
            this.cacheManager = new ConcurrentMapCacheManager();
        } else if (this.applicationContext.getBeansOfType(CacheManager.class)
                .size() == 1) {
            this.cacheManager = this.applicationContext
                    .getBean(CacheManager.class);
        } else {
            this.cacheManager = new ConcurrentMapCacheManager();
        }
        
        //初始化包名
        if (!StringUtils.isEmpty(this.properties.getBasePackages())) {
            this.basePackages = this.properties.getBasePackages();
        }
        if (server!=null && !StringUtils.isEmpty(server.getMybatisConfigLocation())) {
            this.mybatisConfigLocation = server.getMybatisConfigLocation();
        }
        
        if (!StringUtils.isBlank(this.properties.getModule())) {
            this.module = this.properties.getModule();
        }
        if (!StringUtils.isBlank(this.applicationName)) {
            this.module = this.applicationName;
        }
        
        this.transactionTemplate = new TransactionTemplate(
                this.transactionManager);
        this.myBatisDaoSupport = MyBatisDaoSupportHelper.buildMyBatisDaoSupport(
                this.mybatisConfigLocation,
                this.mybatisMapperLocations,
                DataSourceTypeEnum.MYSQL,
                this.dataSource);
    }
    
    /**
     * 该类会优先加载:基础数据容器表初始化器<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2018年5月5日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    @Configuration
    @ConditionalOnBean({ TableDDLExecutor.class })
    @ConditionalOnSingleCandidate(TableDDLExecutor.class)
    @ConditionalOnProperty(prefix = "tx.basicdata", value = "table-auto-initialize", havingValue = "true")
    @ConditionalOnMissingBean(BasicDataContextTableInitializer.class)
    public static class BasicDataContextTableInitializerConfiguration {
        
        /** 表ddl自动执行器 */
        private TableDDLExecutor tableDDLExecutor;
        
        public BasicDataContextTableInitializerConfiguration(
                TableDDLExecutor tableDDLExecutor) {
            this.tableDDLExecutor = tableDDLExecutor;
        }
        
        /**
         * 当命令容器不存在时<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return CommandContextFactory [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean("basicdata.tableInitializer")
        public BasicDataContextTableInitializer tableInitializer() {
            BasicDataContextTableInitializer initializer = new BasicDataContextTableInitializer(
                    tableDDLExecutor, true);
            
            return initializer;
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
        DataDictDao dao = new DataDictDaoImpl(this.myBatisDaoSupport);
        return dao;
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
    public DataDictService dataDictService(DataDictDao dataDictDao) {
        DataDictService service = new DataDictService(this.dataSource,
                this.transactionTemplate, dataDictDao);
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
        
        return context;
    }
    
    /**
     * 基础数据业务层注册机<br/>
     * <功能详细描述>
     * @param basicDataTypeService
     * @param dataDictService
     * @return [参数说明]
     * 
     * @return BasicDataServiceRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @DependsOn(value = "basicDataContext")
    @Bean(name = "basicdata.basicDataServiceRegistry")
    public BasicDataServiceRegistry basicDataServiceRegistry(
            BasicDataTypeService basicDataTypeService,
            DataDictService dataDictService) {
        BasicDataServiceRegistry serviceFactory = new BasicDataServiceRegistry(
                module, basePackages, basicDataTypeService, dataDictService,
                null);
        
        return serviceFactory;
    }
    
    @Bean(name = "basicDataTypeController")
    public BasicDataTypeController basicDataTypeController(
            BasicDataTypeService basicDataTypeService) {
        BasicDataTypeController controller = new BasicDataTypeController(
                basicDataTypeService);
        return controller;
    }
    
    @Bean(name = "basicDataRemoteController")
    public BasicDataRemoteController basicDataRemoteController(
            BasicDataTypeService basicDataTypeService) {
        BasicDataRemoteController controller = new BasicDataRemoteController();
        return controller;
    }
}
