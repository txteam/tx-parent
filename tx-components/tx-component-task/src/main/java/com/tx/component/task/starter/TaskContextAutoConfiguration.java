/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月3日
 * <修改描述:>
 */
package com.tx.component.task.starter;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.task.script.TaskContextTableInitializer;

/**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2018年5月3日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Configuration
@EnableConfigurationProperties(value = TaskContextProperties.class)
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter({ DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        TransactionAutoConfiguration.class })
@ConditionalOnProperty(prefix = "task", value = "enable", havingValue="true")
public class TaskContextAutoConfiguration {
    
    /** 任务容器属性 */
    private TaskContextProperties properties;
    
    /** 数据源 */
    private DataSource datasource;
    
    /** 事务管理器 */
    private PlatformTransactionManager txManager;
    
    /** <默认构造函数> */
    public TaskContextAutoConfiguration(TaskContextProperties properties,
            DataSource datasource, PlatformTransactionManager txManager) {
        super();
        this.properties = properties;
        this.datasource = datasource;
        this.txManager = txManager;
    }
    
    @Bean("taskContext.TableInitializer")
    public TaskContextTableInitializer tableInitializer(){
        TaskContextTableInitializer initializer = new TaskContextTableInitializer();
        return initializer;
    }
    

    /**
     * @return 返回 properties
     */
    public TaskContextProperties getProperties() {
        return properties;
    }

    /**
     * @param 对properties进行赋值
     */
    public void setProperties(TaskContextProperties properties) {
        this.properties = properties;
    }

    /**
     * @return 返回 datasource
     */
    public DataSource getDatasource() {
        return datasource;
    }

    /**
     * @param 对datasource进行赋值
     */
    public void setDatasource(DataSource datasource) {
        this.datasource = datasource;
    }

    /**
     * @return 返回 txManager
     */
    public PlatformTransactionManager getTxManager() {
        return txManager;
    }

    /**
     * @param 对txManager进行赋值
     */
    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }
}
