/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.configuration.starter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tx.component.configuration.script.ConfigContextTableInitializer;
import com.tx.component.configuration.service.ConfigPropertyItemService;
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
public class ConfigContextConfiguration
        implements ApplicationContextAware, InitializingBean {
    
    /** spring 容器句柄 */
    private ApplicationContext applicationContext;
    
    /** 容器所属模块：当该值为空时，使用spring.application.name的内容 */
    private String module;
    
    /** cacheManager */
    private CacheManager cacheManager;
    
    /** 属性文件 */
    private ConfigContextProperties properties;
    
    /** 配置属性项持久层 */
    private ConfigPropertyItemService configPropertyItemService;
    
    /** <默认构造函数> */
    public ConfigContextConfiguration(String module, CacheManager cacheManager,
            ConfigContextProperties properties,
            ConfigPropertyItemService configPropertyItemService) {
        super();
        this.module = module;
        this.cacheManager = cacheManager;
        this.properties = properties;
        this.configPropertyItemService = configPropertyItemService;
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
        AssertUtils.notEmpty(this.module, "module is empty.");
        AssertUtils.notNull(this.cacheManager, "cacheManager is null.");
        AssertUtils.notNull(this.properties, "properties is null.");
        AssertUtils.notNull(this.configPropertyItemService, "configPropertyItemService is null.");
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
    @ConditionalOnProperty(prefix = "tx.basicdata.config", value = "table-auto-initialize", havingValue = "true")
    @ConditionalOnMissingBean(ConfigContextTableInitializer.class)
    public static class BasicDataContextTableInitializerConfiguration {
        
        /** 表ddl自动执行器 */
        private TableDDLExecutor tableDDLExecutor;
        
        /** 基础数据容器初始化构造函数 */
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
        @Bean("basicdata.config.tableInitializer")
        public ConfigContextTableInitializer tableInitializer() {
            ConfigContextTableInitializer initializer = new ConfigContextTableInitializer(
                    tableDDLExecutor, true);
            return initializer;
        }
    }
    
    //    /**
    //     * 配置属性项业务层<br/>
    //     * <功能详细描述>
    //     * 
    //     * @author  Administrator
    //     * @version  [版本号, 2019年3月6日]
    //     * @see  [相关类/方法]
    //     * @since  [产品/模块版本]
    //     */
    //    @Configuration
    //    @ConditionalOnMissingBean(ConfigPropertyItemService.class)
    //    public static class ConfigContextPersisterConfiguration {
    //        
    //        /**
    //         * 持久化类型方案:默认为mybatis<br/>
    //         * <功能详细描述>
    //         * 
    //         * @author  Administrator
    //         * @version  [版本号, 2019年3月6日]
    //         * @see  [相关类/方法]
    //         * @since  [产品/模块版本]
    //         */
    //        @Configuration
    //        @ConditionalOnMissingBean(ConfigPropertyItemDao.class)
    //        @ConditionalOnProperty(prefix = "tx.basicdata.config.persister", value = "type", havingValue = "jpa")
    //        public static class ConfigContextJPADaoConfiguration {
    //            
    //            @EntityScan(basePackages = {
    //                    "com.tx.component.configuration.model" })
    //            @EnableJpaRepositories(basePackages = {
    //                    "com.tx.component.configuration.dao.jpa" })
    //            public static class ConfigContextJPADaoImplConfiguration {
    //                
    //            }
    //        }
    //        
    //        @Configuration
    //        @ConditionalOnMissingBean(ConfigPropertyItemDao.class)
    //        public static class ConfigContextMybatisDaoConfiguration
    //                implements InitializingBean {
    //            
    //            /** 数据源:dataSource */
    //            protected DataSource dataSource;
    //            
    //            /** transactionManager */
    //            protected PlatformTransactionManager transactionManager;
    //            
    //            /** transactionTemplate: 如果存在事务则在当前事务中执行 */
    //            private MyBatisDaoSupport myBatisDaoSupport;
    //            
    //            /** transactionTemplate: 如果存在事务则在当前事务中执行 */
    //            private TransactionTemplate transactionTemplate;
    //            
    //            /**
    //             * @throws Exception
    //             */
    //            @Override
    //            public void afterPropertiesSet() throws Exception {
    //                //设置transactionManager
    //                //                if (StringUtils.isNotBlank(this.properties.getTransactionManagerRef())
    //                //                        && this.applicationContext.containsBean(
    //                //                                this.properties.getTransactionManagerRef())) {
    //                //                    this.transactionManager = this.applicationContext.getBean(
    //                //                            this.properties.getTransactionManagerRef(),
    //                //                            PlatformTransactionManager.class);
    //                //                } else if (this.applicationContext
    //                //                        .getBeansOfType(PlatformTransactionManager.class).size() == 1) {
    //                //                    this.transactionManager = this.applicationContext
    //                //                            .getBean(PlatformTransactionManager.class);
    //                //                } else {
    //                //                    this.transactionManager = new DataSourceTransactionManager(
    //                //                            this.dataSource);
    //                //                }
    //                //                AssertUtils.notEmpty(this.transactionManager,
    //                //                        "transactionManager is null.存在多个事务管理器，需要通过basicdata.transactionManager指定使用的数据源,或为数据源设置为Primary.");
    //                
    //                //设置cacheManager
    //                //                if (StringUtils.isNotBlank(this.properties.getCacheManagerRef())
    //                //                        && this.applicationContext
    //                //                                .containsBean(this.properties.getCacheManagerRef())) {
    //                //                    this.cacheManager = new ConcurrentMapCacheManager();
    //                //                } else if (this.applicationContext.getBeansOfType(CacheManager.class)
    //                //                        .size() == 1) {
    //                //                    this.cacheManager = this.applicationContext
    //                //                            .getBean(CacheManager.class);
    //                //                } else {
    //                //                    this.cacheManager = new ConcurrentMapCacheManager();
    //                //                }
    //                //                
    //                //                //初始化包名
    //                //                if (!StringUtils.isEmpty(this.properties.getBasePackages())) {
    //                //                    this.basePackages = this.properties.getBasePackages();
    //                //                }
    //                //                if (!StringUtils.isEmpty(this.properties.getMybatisConfigLocation())) {
    //                //                    this.mybatisConfigLocation = this.properties
    //                //                            .getMybatisConfigLocation();
    //                //                }
    //                //                
    //                //                if (!StringUtils.isBlank(this.properties.getModule())) {
    //                //                    this.module = this.properties.getModule();
    //                //                }
    //                //                if (!StringUtils.isBlank(this.applicationName)) {
    //                //                    this.module = this.applicationName;
    //                //                }
    //                
    //                //                this.transactionTemplate = new TransactionTemplate(
    //                //                        this.transactionManager);
    //                //                this.myBatisDaoSupport = MyBatisDaoSupportHelper.buildMyBatisDaoSupport(
    //                //                        this.mybatisConfigLocation,
    //                //                        this.mybatisMapperLocations,
    //                //                        DataSourceTypeEnum.MYSQL,
    //                //                        this.dataSource);
    //            }
    //            
    //            @Bean
    //            public ConfigPropertyItemDao configPropertyItemDao() {
    //                return null;
    //            }
    //        }
    //    }
}
