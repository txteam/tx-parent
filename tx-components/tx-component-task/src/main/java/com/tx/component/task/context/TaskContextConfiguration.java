/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月24日
 * <修改描述:>
 */
package com.tx.component.task.context;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.task.TaskConstants;
import com.tx.component.task.dao.TaskDefDao;
import com.tx.component.task.dao.TaskDetailDao;
import com.tx.component.task.dao.TaskExecuteLogDao;
import com.tx.component.task.dao.TaskStatusDao;
import com.tx.component.task.dao.impl.TaskDefDaoImpl;
import com.tx.component.task.dao.impl.TaskDetailDaoImpl;
import com.tx.component.task.dao.impl.TaskExecuteLogDaoImpl;
import com.tx.component.task.dao.impl.TaskStatusDaoImpl;
import com.tx.component.task.interceptor.AnnotationTaskInterceptorProxyCreator;
import com.tx.component.task.interceptor.TaskContextRegistry;
import com.tx.component.task.interceptor.TaskExecuteInterceptor;
import com.tx.component.task.interceptor.TaskExecuteInterceptorFactory;
import com.tx.component.task.interceptor.TimedTaskExecutorProxyCreator;
import com.tx.component.task.service.TaskDefService;
import com.tx.component.task.service.TaskDetailService;
import com.tx.component.task.service.TaskExecuteLogService;
import com.tx.component.task.service.TaskStatusService;
import com.tx.component.task.service.impl.TaskDefServiceImpl;
import com.tx.component.task.service.impl.TaskDetailServiceImpl;
import com.tx.component.task.service.impl.TaskExecuteLogServiceImpl;
import com.tx.component.task.service.impl.TaskStatusServiceImpl;
import com.tx.component.task.timedtask.TimedTaskExecutorFactory;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.ddlutil.executor.TableDDLExecutorFactory;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.mybatis.support.MyBatisDaoSupportHelper;
import com.tx.core.util.dialect.DataSourceTypeEnum;

/**
 * 事务容器配置器<br/>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TaskContextConfiguration implements ApplicationContextAware, InitializingBean, BeanFactoryAware {
    
    
    
    /** 日志容器 */
    protected Logger logger = LoggerFactory.getLogger(TaskContextConfiguration.class);
    
    /** spring容器句柄 */
    protected static ApplicationContext applicationContext;
    
    /** 单例对象注册方法 */
    protected SingletonBeanRegistry singletonBeanRegistry;
    
    /** Bean定义注册机 */
    protected BeanDefinitionRegistry beanDefinitionRegistry;
    
    /** mybatis的配置文件所在目录 */
    private String mybatisConfigLocation = "classpath:context/mybatis-config.xml";
    
    /** 数据源类型 */
    protected DataSourceTypeEnum dataSourceType = DataSourceTypeEnum.MYSQL;
    
    /** 数据源 */
    protected DataSource dataSource;
    
    /** transactionManager */
    private PlatformTransactionManager transactionManager;
    
    /** 缓存Manager */
    protected CacheManager cacheManager;
    
    /** 表定义执行器 */
    protected TableDDLExecutor tableDDLExecutor;
    
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
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
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
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        TaskContextConfiguration.applicationContext = applicationContext;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public final void afterPropertiesSet() throws Exception {
        logger.info("开始初始化事务执行容器...");
        
        //表定义执行器
        
        AssertUtils.notNull(this.dataSource, "dataSource is null.");
        registerSingletonBean("taskContext.dataSource", this.dataSource);
        
        if (this.tableDDLExecutor == null) {
            this.tableDDLExecutor = TableDDLExecutorFactory.buildTableDDLExecutor(this.dataSourceType, this.dataSource);
        }
        registerSingletonBean("taskContext.tableDDLExecutor", this.tableDDLExecutor);
        
        //缓存容器
        if (this.cacheManager == null) {
            this.cacheManager = new ConcurrentMapCacheManager();
        }
        registerSingletonBean("taskContext.cacheManager", this.cacheManager);
        
        //缓存容器
        if (this.transactionManager == null) {
            this.transactionManager = new DataSourceTransactionManager(this.dataSource);
        }
        registerSingletonBean("taskContext.transactionManager", this.transactionManager);
        
        //执行构建
        doBuild();
        
        logger.info("事务执行容器初始化完毕...");
    }
    
    /**
      * 任务执行容器构建<br/>
      * <功能详细描述>
      * @throws Exception [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void doBuild() throws Exception {
    }
    
    /**
     * 任务执行容器构建<br/>
     * <功能详细描述>
     * @throws Exception [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected void doInitContext() throws Exception {
    }
    
    /**
     * @param 对dataSourceType进行赋值
     */
    public void setDataSourceType(DataSourceTypeEnum dataSourceType) {
        this.dataSourceType = dataSourceType;
    }
    
    /**
     * @param 对dataSource进行赋值
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * @param 对cacheManager进行赋值
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
    
    /**
     * @param 对tableDDLExecutor进行赋值
     */
    public void setTableDDLExecutor(TableDDLExecutor tableDDLExecutor) {
        this.tableDDLExecutor = tableDDLExecutor;
    }
    
    /**
     * @param 对mybatisConfigLocation进行赋值
     */
    public void setMybatisConfigLocation(String mybatisConfigLocation) {
        this.mybatisConfigLocation = mybatisConfigLocation;
    }
    
}
