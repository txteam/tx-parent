/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.servicelogger.starter;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.servicelogger.context.ServiceLoggerContextFactory;
import com.tx.component.servicelogger.dbscript.ServiceLoggerTableInitializer;
import com.tx.component.servicelogger.support.ServiceLoggerRegistry;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.support.MyBatisDaoSupport;

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
@EnableConfigurationProperties(ServiceLoggerContextProperties.class)
@ConditionalOnBean({ DataSource.class, PlatformTransactionManager.class })
@AutoConfigureAfter({ DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        MybatisAutoConfiguration.class })
@ConditionalOnProperty(prefix = "tx.servicelogger", value = "enable", havingValue = "true")
public class ServiceLoggerContextAutoConfiguration
        implements ApplicationContextAware, InitializingBean {
    
    /** mybatis配置文件 */
    protected String[] mybatisMapperLocations = new String[] {
            "classpath*:com/tx/component/basicdata/dao/impl/*SqlMap_BASICDATA.xml" };
    
    /** 包名 */
    protected String basePackages = "com.tx";
    
    /** 属性文件 */
    private ServiceLoggerContextProperties properties;
    
    /** spring 容器句柄 */
    private ApplicationContext applicationContext;
    
    /** 数据源:dataSource */
    protected DataSource dataSource;
    
    /** transactionManager */
    protected PlatformTransactionManager transactionManager;
    
    /** transactionTemplate: 如果存在事务则在当前事务中执行 */
    protected MyBatisDaoSupport myBatisDaoSupport;
    
    /** transactionTemplate: 如果存在事务则在当前事务中执行 */
    protected TransactionTemplate transactionTemplate;
    
    /** 容器所属模块：当该值为空时，使用spring.application.name的内容 */
    protected String module;
    
    /** application.name */
    @Value(value = "${spring.application.name}")
    private String applicationName;
    
    /** <默认构造函数> */
    public ServiceLoggerContextAutoConfiguration(
            ServiceLoggerContextProperties properties) {
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
        //设置dataSource
        if (StringUtils.isNotBlank(this.properties.getDataSourceRef())
                && this.applicationContext
                        .containsBean(this.properties.getDataSourceRef())) {
            this.dataSource = this.applicationContext.getBean(
                    this.properties.getDataSourceRef(), DataSource.class);
        } else if (this.applicationContext.getBeansOfType(DataSource.class)
                .size() == 1) {
            this.dataSource = this.applicationContext.getBean(DataSource.class);
        }
        AssertUtils.notEmpty(this.dataSource,
                "dataSource is null.存在多个数据源，需要通过basicdata.dataSource指定使用的数据源,或为数据源设置为Primary.");
        
        //设置dataSource
        if (StringUtils.isNotBlank(this.properties.getMybatisDaoSupportRef())
                && this.applicationContext.containsBean(
                        this.properties.getMybatisDaoSupportRef())) {
            this.myBatisDaoSupport = this.applicationContext.getBean(
                    this.properties.getMybatisDaoSupportRef(),
                    MyBatisDaoSupport.class);
        } else if (this.applicationContext
                .getBeansOfType(MyBatisDaoSupport.class).size() == 1) {
            this.myBatisDaoSupport = this.applicationContext
                    .getBean(MyBatisDaoSupport.class);
        }
        AssertUtils.notEmpty(this.myBatisDaoSupport,
                "myBatisDaoSupport is null.存在多个数据源，需要通过basicdata.myBatisDaoSupport指定使用的mybatis句柄,或为mybatis句柄设置为Primary.");
        
        //设置transactionManager
        if (StringUtils.isNotBlank(this.properties.getTransactionManagerRef())
                && this.applicationContext.containsBean(
                        this.properties.getTransactionManagerRef())) {
            this.transactionManager = this.applicationContext.getBean(
                    this.properties.getTransactionManagerRef(),
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
        
        //初始化包名
        if (!StringUtils.isEmpty(this.properties.getBasePackages())) {
            this.basePackages = this.properties.getBasePackages();
        }
        if (!StringUtils.isBlank(this.properties.getModule())) {
            this.module = this.properties.getModule();
        }
        if (!StringUtils.isBlank(this.applicationName)) {
            this.module = this.applicationName;
        }
        
        this.transactionTemplate = new TransactionTemplate(
                this.transactionManager);
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
    @ConditionalOnProperty(prefix = "tx.servicelogger", value = "table-auto-initialize", havingValue = "true")
    @ConditionalOnMissingBean(ServiceLoggerTableInitializer.class)
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
        @Bean("servicelogger.tableInitializer")
        public ServiceLoggerTableInitializer tableInitializer() {
            ServiceLoggerTableInitializer initializer = new ServiceLoggerTableInitializer(
                    tableDDLExecutor);
            
            return initializer;
        }
    }
    
    /**
     * 业务日志注册表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return ServiceLoggerRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "serviceLoggerRegistry")
    public ServiceLoggerRegistry serviceLoggerRegistry() {
        ServiceLoggerRegistry registry = new ServiceLoggerRegistry(
                this.basePackages, this.myBatisDaoSupport,
                this.transactionTemplate);
        
        return registry;
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
    @Bean(name = "serviceLoggerContext")
    public ServiceLoggerContextFactory serviceLoggerContext(
            ServiceLoggerRegistry serviceLoggerRegistry) {
        ServiceLoggerContextFactory context = new ServiceLoggerContextFactory();
        context.setServiceLoggerRegistry(serviceLoggerRegistry);
        
        return context;
    }
    
}
