/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月20日
 * <修改描述:>
 */
package com.tx.core.ddlutil.executor;

import javax.sql.DataSource;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 表DDL处理器工厂类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TableDDLExecutorFactory implements FactoryBean<TableDDLExecutor>,
        InitializingBean {
    
    private DataSource dataSource;
    
    private JdbcTemplate jdbcTemplate;
    
    private TableDDLExecutor tableDDLExecutor;
    
    /** <默认构造函数> */
    public TableDDLExecutorFactory() {
        super();
    }
    
    /** <默认构造函数> */
    public TableDDLExecutorFactory(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }
    
    /** <默认构造函数> */
    public TableDDLExecutorFactory(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public TableDDLExecutor getObject() throws Exception {
        AssertUtils.notNull(this.tableDDLExecutor,"tableDDLExecutor is null.");
        return this.tableDDLExecutor;
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return TableDDLExecutor.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
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
     * @return 返回 jdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    
    /**
     * @param 对jdbcTemplate进行赋值
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
