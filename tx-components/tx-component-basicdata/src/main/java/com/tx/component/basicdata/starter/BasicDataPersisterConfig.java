/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年3月7日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter;

import javax.sql.DataSource;

import org.springframework.cache.CacheManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.core.mybatis.support.MyBatisDaoSupport;

/**
 * 基础数据持久化配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年3月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataPersisterConfig {
    
    /** 数据源:dataSource */
    protected DataSource dataSource;
    
    /** transactionManager */
    protected PlatformTransactionManager transactionManager;
    
    /** transactionTemplate: 如果存在事务则在当前事务中执行 */
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /** transactionTemplate: 如果存在事务则在当前事务中执行 */
    private TransactionTemplate transactionTemplate;
    
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
     * @return 返回 transactionManager
     */
    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }
    
    /**
     * @param 对transactionManager进行赋值
     */
    public void setTransactionManager(
            PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    
    /**
     * @return 返回 myBatisDaoSupport
     */
    public MyBatisDaoSupport getMyBatisDaoSupport() {
        return myBatisDaoSupport;
    }
    
    /**
     * @param 对myBatisDaoSupport进行赋值
     */
    public void setMyBatisDaoSupport(MyBatisDaoSupport myBatisDaoSupport) {
        this.myBatisDaoSupport = myBatisDaoSupport;
    }
    
    /**
     * @return 返回 transactionTemplate
     */
    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }
    
    /**
     * @param 对transactionTemplate进行赋值
     */
    public void setTransactionTemplate(
            TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
}
