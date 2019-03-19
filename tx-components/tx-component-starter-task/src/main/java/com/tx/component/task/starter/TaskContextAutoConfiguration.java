/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月3日
 * <修改描述:>
 */
package com.tx.component.task.starter;

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
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.task.TaskConstants;
import com.tx.component.task.context.TaskContextRegistry;
import com.tx.component.task.dao.TaskDefDao;
import com.tx.component.task.dao.TaskDetailDao;
import com.tx.component.task.dao.TaskExecuteLogDao;
import com.tx.component.task.dao.TaskStatusDao;
import com.tx.component.task.dao.impl.TaskDefDaoImpl;
import com.tx.component.task.dao.impl.TaskDetailDaoImpl;
import com.tx.component.task.dao.impl.TaskExecuteLogDaoImpl;
import com.tx.component.task.dao.impl.TaskStatusDaoImpl;
import com.tx.component.task.interceptor.AnnotationTaskInterceptorProxyCreator;
import com.tx.component.task.interceptor.TaskExecuteInterceptorFactory;
import com.tx.component.task.script.TaskContextTableInitializer;
import com.tx.component.task.service.TaskDefService;
import com.tx.component.task.service.TaskDetailService;
import com.tx.component.task.service.TaskExecuteLogService;
import com.tx.component.task.service.TaskStatusService;
import com.tx.component.task.service.impl.TaskDefServiceImpl;
import com.tx.component.task.service.impl.TaskDetailServiceImpl;
import com.tx.component.task.service.impl.TaskExecuteLogServiceImpl;
import com.tx.component.task.service.impl.TaskStatusServiceImpl;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.mybatis.support.MyBatisDaoSupportHelper;
import com.tx.core.util.SignatureUtils;
import com.tx.core.util.dialect.DataSourceTypeEnum;

/**
 * 任务容器自动配置类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
@EnableConfigurationProperties(value = TaskContextProperties.class)
@ConditionalOnBean({ DataSource.class, PlatformTransactionManager.class })
@AutoConfigureAfter({ DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class })
@ConditionalOnProperty(prefix = "tx.task", value = "enable", havingValue = "true")
public class TaskContextAutoConfiguration
        implements InitializingBean, ApplicationContextAware {
    
    /** mybatis配置文件 */
    protected String[] mybatisMapperLocations = new String[] {
            "classpath*:com/tx/component/basicdata/dao/impl/*SqlMap_BASICDATA.xml" };
    
    /** mybatis配置文件 */
    protected String mybatisConfigLocation = "classpath:context/mybatis-config.xml";
    
    private ApplicationContext applicationContext;
    
    /** 任务容器属性 */
    private TaskContextProperties properties;
    
    /** 当前容器签名 */
    private String signature;
    
    /** 容器所属模块：当该值为空时，使用spring.application.name的内容 */
    private String module;
    
    /** application.name */
    @Value(value = "${spring.application.name}")
    private String applicationName;
    
    /** 数据源 */
    private DataSource dataSource;
    
    /** transactionTemplate: 如果存在事务则在当前事务中执行 */
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /** 事务管理器 */
    private PlatformTransactionManager transactionManager;
    
    /** <默认构造函数> */
    public TaskContextAutoConfiguration(TaskContextProperties properties) {
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
        
        if (!StringUtils.isEmpty(this.properties.getMybatisConfigLocation())) {
            this.mybatisConfigLocation = this.properties
                    .getMybatisConfigLocation();
        }
        if (!StringUtils.isBlank(this.properties.getModule())) {
            this.module = this.properties.getModule();
        }
        if (!StringUtils.isBlank(this.applicationName)) {
            this.module = this.applicationName;
        }
        
        // 生成服务签名（ip信息 + classpath信息）
        this.signature = String.valueOf(
                Math.abs(SignatureUtils.generateSignature().hashCode()));
        
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
    @ConditionalOnProperty(prefix = "tx.task", value = "table-auto-initialize", havingValue = "true")
    @ConditionalOnMissingBean(TaskContextTableInitializer.class)
    public static class TaskContextTableInitializerConfiguration {
        
        /** 表ddl自动执行器 */
        private TableDDLExecutor tableDDLExecutor;
        
        public TaskContextTableInitializerConfiguration(
                TableDDLExecutor tableDDLExecutor) {
            this.tableDDLExecutor = tableDDLExecutor;
        }
        
        @Bean("taskContext.tableInitializer")
        public TaskContextTableInitializer tableInitializer() {
            TaskContextTableInitializer initializer = new TaskContextTableInitializer(
                    this.tableDDLExecutor, true);
            return initializer;
        }
    }
    
    /**
     * 表初始化器<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TaskContextTableInitializer [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = TaskConstants.BEAN_NAME_TASK_DEF_DAO)
    public TaskDefDao taskDefDao() {
        TaskDefDao bean = new TaskDefDaoImpl(this.myBatisDaoSupport);
        return bean;
    }
    
    /**
     * 表初始化器<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TaskContextTableInitializer [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = TaskConstants.BEAN_NAME_TASK_DEF_SERVICE)
    public TaskDefService taskDefService(TaskDefDao taskDefDao) {
        TaskDefService bean = new TaskDefServiceImpl(taskDefDao);
        return bean;
    }
    
    /**
     * 表初始化器<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TaskContextTableInitializer [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = TaskConstants.BEAN_NAME_TASK_STATUS_DAO)
    public TaskStatusDao taskStatusDao() {
        TaskStatusDao bean = new TaskStatusDaoImpl(this.myBatisDaoSupport);
        return bean;
    }
    
    /**
     * 表初始化器<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TaskContextTableInitializer [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = TaskConstants.BEAN_NAME_TASK_STATUS_SERVICE)
    public TaskStatusService taskStatusService(TaskStatusDao taskStatusDao) {
        TaskStatusService bean = new TaskStatusServiceImpl(taskStatusDao);
        return bean;
    }
    
    /**
     * 表初始化器<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TaskContextTableInitializer [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = TaskConstants.BEAN_NAME_TASK_DETAIL_DAO)
    public TaskDetailDao taskDetailDao() {
        TaskDetailDao bean = new TaskDetailDaoImpl(this.myBatisDaoSupport);
        return bean;
    }
    
    /**
     * 表初始化器<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TaskContextTableInitializer [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = TaskConstants.BEAN_NAME_TASK_DETAIL_SERVICE)
    public TaskDetailService taskDetailService(TaskDetailDao taskDetailDao) {
        TaskDetailService bean = new TaskDetailServiceImpl(taskDetailDao);
        return bean;
    }
    
    /**
     * 任务执行日志<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TaskExecuteLogDao [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = TaskConstants.BEAN_NAME_TASK_EXECUTE_LOG_DAO)
    public TaskExecuteLogDao taskExecuteLogDao() {
        TaskExecuteLogDao bean = new TaskExecuteLogDaoImpl(
                this.myBatisDaoSupport);
        return bean;
    }
    
    /**
     * 任务执行日志业务层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TaskExecuteLogService [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = TaskConstants.BEAN_NAME_TASK_EXECUTE_LOG_SERVICE)
    public TaskExecuteLogService taskExecuteLogService(
            TaskExecuteLogDao taskExecuteLogDao) {
        TaskExecuteLogService bean = new TaskExecuteLogServiceImpl(
                taskExecuteLogDao);
        return bean;
    }
    
    /**
     * 任务容器注册表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TaskContextRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = TaskConstants.BEAN_NAME_TASK_CONTEXT_REGISTRY)
    public TaskContextRegistry taskContextRegistry() {
        TaskContextRegistry bean = TaskContextRegistry.INSTANCE;
        return bean;
    }
    
    //    /**
    //     * 任务拦截器<br/>
    //     * <功能详细描述>
    //     * @return [参数说明]
    //     * 
    //     * @return TaskContextTableInitializer [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    @Scope("prototype")
    //    @Bean(name = "taskContext.taskExecuteInterceptor")
    //    public TaskExecuteInterceptor taskExecuteInterceptor() {
    //        TaskExecuteInterceptor bean = new TaskExecuteInterceptor();
    //        return bean;
    //    }
    
    /**
     * 任务拦截器工厂<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TaskContextTableInitializer [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "taskContext.taskExecuteInterceptorFactory")
    public TaskExecuteInterceptorFactory taskExecuteInterceptorFactory(
            TaskDefService taskDefService, TaskStatusService taskStatusService,
            TaskExecuteLogService taskExecuteLogService) {
        TaskExecuteInterceptorFactory bean = new TaskExecuteInterceptorFactory(
                this.signature, taskDefService, taskStatusService,
                taskExecuteLogService, this.transactionManager);
        return bean;
    }
    
    /**
     * 注解任务拦截器代理创建器<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TaskContextTableInitializer [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "taskContext.annotationTaskInterceptorProxyCreator")
    public AnnotationTaskInterceptorProxyCreator annotationTaskInterceptorProxyCreator(
            TaskExecuteInterceptorFactory taskExecuteInterceptorFactory) {
        AnnotationTaskInterceptorProxyCreator bean = new AnnotationTaskInterceptorProxyCreator(
                this.module, taskExecuteInterceptorFactory);
        return bean;
    }
    
    //    /**
    //     * 初始化定时任务执行器工厂<br/>
    //     * <功能详细描述>
    //     * @return [参数说明]
    //     * 
    //     * @return TimedTaskExecutorFactory [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    @Bean(name = "taskContext.timedTaskExecutorFactory")
    //    public TimedTaskExecutorFactory timedTaskExecutorFactory() {
    //        TimedTaskExecutorFactory bean = new TimedTaskExecutorFactory();
    //        return bean;
    //    }
    
}
