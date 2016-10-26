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

import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.ddlutil.executor.impl.MysqlTableDDLExecutor;
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
    
    /**
      * 构建表TableDDLExecutor<br/>
      * <功能详细描述>
      * @param dataSourceType
      * @param jdbcTemplate
      * @return [参数说明]
      * 
      * @return TableDDLExecutor [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static TableDDLExecutor buildTableDDLExecutor(
            DataSourceTypeEnum dataSourceType, JdbcTemplate jdbcTemplate) {
        TableDDLExecutorFactory facotry = new TableDDLExecutorFactory();
        facotry.setDataSourceType(dataSourceType);
        facotry.setJdbcTemplate(jdbcTemplate);
        
        facotry.afterPropertiesSet();
        
        TableDDLExecutor tableDDLExecutor = facotry.getObject();
        return tableDDLExecutor;
    }
    
    /**
      * 构建表DDLExecutor<br/>
      * <功能详细描述>
      * @param dataSourceType
      * @param dataSource
      * @return [参数说明]
      * 
      * @return TableDDLExecutor [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static TableDDLExecutor buildTableDDLExecutor(
            DataSourceTypeEnum dataSourceType, DataSource dataSource) {
        TableDDLExecutorFactory facotry = new TableDDLExecutorFactory();
        facotry.setDataSourceType(dataSourceType);
        facotry.setDataSource(dataSource);
        
        facotry.afterPropertiesSet();
        
        TableDDLExecutor tableDDLExecutor = facotry.getObject();
        return tableDDLExecutor;
    }
    
    private DataSource dataSource;
    
    private JdbcTemplate jdbcTemplate;
    
    private TableDDLExecutor tableDDLExecutor;
    
    private DataSourceTypeEnum dataSourceType;
    
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
    public void afterPropertiesSet() {
        AssertUtils.notNull(this.dataSourceType, "dataSourceType is null.");
        AssertUtils.isTrue(this.jdbcTemplate != null || this.dataSource != null,
                "jdbcTemplate and dataSource is null.");
        
        if (this.dataSource == null) {
            this.dataSource = this.jdbcTemplate.getDataSource();
        }
        if (this.jdbcTemplate == null) {
            this.jdbcTemplate = new JdbcTemplate(this.dataSource);
        }
        
        //构建实际的表构建器
        this.tableDDLExecutor = buildTableDDLExecutor();
    }
    
    /**
      * 构建真实的TableDDLExecutor
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return TableDDLExecutor [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private TableDDLExecutor buildTableDDLExecutor() {
        TableDDLExecutor ddlExecutor = null;
        switch (this.dataSourceType) {
            case MYSQL:
            case MySQL5InnoDBDialect:
                ddlExecutor = new MysqlTableDDLExecutor(this.dataSource,
                        this.jdbcTemplate);
                break;
            default:
                break;
        }
        AssertUtils.notNull(ddlExecutor,
                "ddlExecutor not exist.dataSourceType:{}",
                new Object[] { this.dataSourceType });
        
        return ddlExecutor;
    }
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public TableDDLExecutor getObject() {
        AssertUtils.notNull(this.tableDDLExecutor, "tableDDLExecutor is null.");
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
    
    /**
     * @return 返回 dataSourceType
     */
    public DataSourceTypeEnum getDataSourceType() {
        return dataSourceType;
    }
    
    /**
     * @param 对dataSourceType进行赋值
     */
    public void setDataSourceType(DataSourceTypeEnum dataSourceType) {
        this.dataSourceType = dataSourceType;
    }
}
