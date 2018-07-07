///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2018年5月9日
// * <修改描述:>
// */
//package com.tx.component.auth.starter;
//
//import javax.sql.DataSource;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import com.tx.component.auth.context.AuthContextFactory;
//import com.tx.component.auth.context.AuthItemImplDaoImpl;
//import com.tx.component.auth.context.AuthItemLoaderProcessor;
//import com.tx.component.auth.context.AuthItemPersister;
//import com.tx.component.auth.context.AuthItemRefImplDaoImpl;
//import com.tx.component.auth.context.loader.impl.XmlAuthLoader;
//import com.tx.component.auth.context.loaderprocessor.ChildAuthRegisterSupportLoaderProcessor;
//import com.tx.component.auth.context.loaderprocessor.ControllerAuthRegisterSupportLoaderProcessor;
//import com.tx.component.auth.dao.AuthItemDao;
//import com.tx.component.auth.dao.AuthItemRefDao;
//import com.tx.component.auth.script.AuthContextTableInitializer;
//import com.tx.component.auth.service.AuthItemRefImplService;
//import com.tx.component.auth.service.AuthItemService;
//import com.tx.component.auth.service.NotTempAuthItemRefImplService;
//import com.tx.core.ddlutil.executor.TableDDLExecutor;
//import com.tx.core.exceptions.util.AssertUtils;
//
///**
// * <功能简述>
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2018年5月9日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//@Configuration
//@EnableConfigurationProperties(value = AuthContextProperties.class)
//@ConditionalOnBean({ DataSource.class, PlatformTransactionManager.class })
//@AutoConfigureAfter({ DataSourceAutoConfiguration.class,
//        DataSourceTransactionManagerAutoConfiguration.class })
//@ConditionalOnProperty(prefix = "auth", value = "enable", havingValue = "true")
//public class AuthContextAutoConfiguration
//        implements InitializingBean, ApplicationContextAware {
//    
//    private ApplicationContext applicationContext;
//    
//    /** 任务容器属性 */
//    private AuthContextProperties properties;
//    
//    /** 数据源 */
//    private DataSource dataSource;
//    
//    /** 事务管理器 */
//    private PlatformTransactionManager transactionManager;
//    
//    /** <默认构造函数> */
//    public AuthContextAutoConfiguration(AuthContextProperties properties) {
//        super();
//        this.properties = properties;
//    }
//    
//    /**
//     * @param applicationContext
//     * @throws BeansException
//     */
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext)
//            throws BeansException {
//        this.applicationContext = applicationContext;
//    }
//    
//    /**
//     * @throws Exception
//     */
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        //设置dataSource
//        if (StringUtils.isNotBlank(this.properties.getDataSource())
//                && this.applicationContext
//                        .containsBean(this.properties.getDataSource())) {
//            this.dataSource = this.applicationContext
//                    .getBean(this.properties.getDataSource(), DataSource.class);
//        } else if (this.applicationContext.getBeansOfType(DataSource.class)
//                .size() == 1) {
//            this.dataSource = this.applicationContext.getBean(DataSource.class);
//        }
//        AssertUtils.notEmpty(this.dataSource,
//                "dataSource is null.存在多个数据源，需要通过basicdata.dataSource指定使用的数据源,或为数据源设置为Primary.");
//        
//        //设置transactionManager
//        if (StringUtils.isNotBlank(this.properties.getTransactionManager())
//                && this.applicationContext.containsBean(
//                        this.properties.getTransactionManager())) {
//            this.transactionManager = this.applicationContext.getBean(
//                    this.properties.getTransactionManager(),
//                    PlatformTransactionManager.class);
//        } else if (this.applicationContext
//                .getBeansOfType(PlatformTransactionManager.class).size() == 1) {
//            this.transactionManager = this.applicationContext
//                    .getBean(PlatformTransactionManager.class);
//        }
//        AssertUtils.notEmpty(this.transactionManager,
//                "transactionManager is null.存在多个事务管理器，需要通过basicdata.transactionManager指定使用的数据源,或为数据源设置为Primary.");
//    }
//    
//    /**
//     * 该类会优先加载:基础数据容器表初始化器<br/>
//     * <功能详细描述>
//     * 
//     * @author  Administrator
//     * @version  [版本号, 2018年5月5日]
//     * @see  [相关类/方法]
//     * @since  [产品/模块版本]
//     */
//    @Configuration
//    @ConditionalOnBean({ TableDDLExecutor.class })
//    @ConditionalOnSingleCandidate(TableDDLExecutor.class)
//    @ConditionalOnProperty(prefix = "auth", value = "table-auto-initialize", havingValue = "true")
//    @ConditionalOnMissingBean(AuthContextTableInitializer.class)
//    public static class AuthContextTableInitializerConfiguration {
//        
//        /** 表ddl自动执行器 */
//        private TableDDLExecutor tableDDLExecutor;
//        
//        /** <默认构造函数> */
//        public AuthContextTableInitializerConfiguration(
//                TableDDLExecutor tableDDLExecutor) {
//            this.tableDDLExecutor = tableDDLExecutor;
//        }
//        
//        /**
//         * 权限相关表初始化<br/>
//         * <功能详细描述>
//         * @return [参数说明]
//         * 
//         * @return AuthContextTableInitializer [返回类型说明]
//         * @exception throws [异常类型] [异常说明]
//         * @see [类、类#方法、类#成员]
//         */
//        @Bean("auth.tableInitializer")
//        public AuthContextTableInitializer tableInitializer() {
//            AuthContextTableInitializer initializer = new AuthContextTableInitializer(
//                    this.tableDDLExecutor);
//            return initializer;
//        }
//    }
//    
//    
//    
//    
//    /** 增加对Controller权限加载的支持 */
//    @Bean(name = "auth.controllerAuthRegisterSupportLoaderProcessor")
//    public AuthItemLoaderProcessor controllerAuthRegisterSupportLoaderProcessor(){
//        ControllerAuthRegisterSupportLoaderProcessor processor = new ControllerAuthRegisterSupportLoaderProcessor();
//        processor.setBasePackages(scanControllerAuthBasePackages);
//        return processor;
//    }
//    
//    /** 增加对子权限加载的支撑 */
//    @Bean(name = "auth.childAuthRegisterSupportLoaderProcessor")
//    public AuthItemLoaderProcessor childAuthRegisterSupportLoaderProcessor() {
//        ChildAuthRegisterSupportLoaderProcessor processor = new ChildAuthRegisterSupportLoaderProcessor();
//        return processor;
//    }
//    
//    @Bean(name = "authContext")
//    public AuthContextFactory authContext() {
//        AuthContextFactory authContextFactory = new AuthContextFactory();
//        authContextFactory.setDataSource(dataSource);
//        authContextFactory.setDefaultAuthChecker(defaultAuthChecker);
//        authContextFactory.setJdbcTemplate(jdbcTemplate);
//        authContextFactory.setPlatformTransactionManager(platformTransactionManager);
//        authContextFactory.setSystemId(systemId);
//        authContextFactory.setTableSuffix(tableSuffix);
//        return authContextFactory;
//    }
//    
//    @Bean(name = "authItemRefImplDao")
//    public AuthItemRefDao authItemRefImplDao() {
//        AuthItemRefDao authItemRefImplDao = new AuthItemRefImplDaoImpl(
//                this.jdbcTemplate, this.dataSource);
//        return authItemRefImplDao;
//    }
//    
//    @Bean(name = "authItemImplDao")
//    public AuthItemDao authItemImplDao() {
//        AuthItemDao authItemImplDao = new AuthItemImplDaoImpl(
//                this.jdbcTemplate, this.dataSource);
//        return authItemImplDao;
//    }
//    
//    @Bean(name = "authItemRefImplService")
//    public AuthItemRefImplService authItemRefImplService() {
//        AuthItemRefImplService authItemRefImplService = new AuthItemRefImplService(
//                this.platformTransactionManager);
//        return authItemRefImplService;
//    }
//    
//    @Bean(name = "authItemImplService")
//    public AuthItemService authItemImplService() {
//        AuthItemService authItemImplService = new AuthItemService(
//                this.platformTransactionManager);
//        return authItemImplService;
//    }
//    
//    @Bean(name = "authItemPersister")
//    public AuthItemPersister authItemPersister() {
//        AuthItemPersister authItemPersister = new AuthItemPersister(
//                this.tableSuffix, this.systemId);
//        return authItemPersister;
//    }
//    
//    @Bean(name = "xmlAuthLoader")
//    public XmlAuthLoader xmlAuthLoader() {
//        XmlAuthLoader xmlAuthLoader = new XmlAuthLoader(this.authConfigLocaions);
//        return xmlAuthLoader;
//    }
//    
//    @Bean(name = "notTempAuthItemRefImplService")
//    public NotTempAuthItemRefImplService notTempAuthItemRefImplService() {
//        NotTempAuthItemRefImplService notTempAuthItemRefImplService = new NotTempAuthItemRefImplService(
//                this.platformTransactionManager);
//        return notTempAuthItemRefImplService;
//    }
//    
//}
