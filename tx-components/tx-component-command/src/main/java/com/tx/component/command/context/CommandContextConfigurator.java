/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年11月28日
 * <修改描述:>
 */
package com.tx.component.command.context;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 投资项目容器配置器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年11月28日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CommandContextConfigurator
        implements ApplicationContextAware, InitializingBean, BeanNameAware {
    
    /** 日志记录器 */
    protected Logger logger = LoggerFactory.getLogger(CommandContext.class);
    
    /** spring容器 */
    protected static ApplicationContext applicationContext;
    
    /** beanName */
    protected static String beanName;
    
    /** 数据源 */
    private DataSource dataSource;
    
    /** 事务管理器 */
    protected PlatformTransactionManager txManager;
    
    /** 事务template */
    protected TransactionTemplate transactionTemplate;
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        CommandContextConfigurator.beanName = name;
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        CommandContextConfigurator.applicationContext = applicationContext;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notNull(dataSource, "dataSource is null.");
        if (this.txManager == null) {
            this.txManager = new DataSourceTransactionManager(this.dataSource);
        }
        this.transactionTemplate = new TransactionTemplate(this.txManager,
                new DefaultTransactionDefinition(
                        TransactionDefinition.PROPAGATION_REQUIRED));
        
        //构建
        doBuild();
        
        //初始化容器
        doInitContext();
    }
    
    /**
     * 执行构建<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected void doBuild() throws Exception {
        
    }
    
    /**
     * 初始化容器<br/>
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
     * @return 返回 dataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }
    
    /**
     * @param 对dataSource进行赋值
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
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
