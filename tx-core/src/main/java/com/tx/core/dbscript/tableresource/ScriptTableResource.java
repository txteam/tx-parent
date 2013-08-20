/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-8-19
 * <修改描述:>
 */
package com.tx.core.dbscript.tableresource;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.core.dbscript.exception.DataSourceTypeUnSupportException;
import com.tx.core.dbscript.model.DataSourceTypeEnum;

/**
 * TableResource基础类<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-8-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class ScriptTableResource implements TableResource {
    
    /** 日志记录器 */
    private Logger logger = LoggerFactory.getLogger(TableResource.class);
    
    /** 数据操作句柄 */
    private JdbcTemplate jdbcTemplate;
    
    private TransactionTemplate transactionTemplate;
    
    /**
     * <默认构造函数>
     */
    public ScriptTableResource(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = new TransactionTemplate(
                new DataSourceTransactionManager(
                        this.jdbcTemplate.getDataSource()));
    }
    
    /**
     * <默认构造函数>
     */
    public ScriptTableResource(DataSource dataSource) {
        super();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.transactionTemplate = new TransactionTemplate(
                new DataSourceTransactionManager(dataSource));
    }
    
    /**
     * @param dataSourceType
     * @param params
     * @return
     */
    @Override
    public boolean isExsit(DataSourceTypeEnum dataSourceType,
            Map<String, String> params) {
        String tableName = getTableName(params);
        boolean isExist = isExist(dataSourceType, tableName);
        logger.info("判断表：{}是否存在：{}.", tableName, isExist ? "存在" : "不存在");
        return isExist;
    }
    
    /**
      * 判断表是否存在<br/>
      *<功能详细描述>
      * @param dataSourceType
      * @param tableName
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected boolean isExist(DataSourceTypeEnum dataSourceType,
            String tableName) {
        try {
            this.jdbcTemplate.execute("select 1 from " + tableName);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
    
    /**
     * @param dataSourceType
     * @param params
     * @return
     */
    @Override
    public boolean isNeedUpdate(DataSourceTypeEnum dataSourceType,
            String version, Map<String, String> params) {
        String tableName = getTableName(params);
        boolean isNeedUpdate = isNeedUpdate(dataSourceType, version, tableName);
        logger.info("判断表：{}是否存在：{}.", tableName, isNeedUpdate ? "存在" : "不存在");
        return false;
    }
    
    /**
     * 判断表是否需要升级<br/>
     *<功能详细描述>
     * @param dataSourceType
     * @param tableName
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected boolean isNeedUpdate(DataSourceTypeEnum dataSourceType,
            String version, String tableName) {
        return false;
    }
    
    /**
     * @param dataSourceType
     * @param params
     */
    @Override
    public void createTable(DataSourceTypeEnum dataSourceType,
            Map<String, String> params) {
        String tableName = getTableName(params);
        String createTableSql = getCreateTableSql(dataSourceType,
                tableName,
                params);
        this.jdbcTemplate.execute(createTableSql);
    }
    
    /**
      * 创建表Sql
      *<功能详细描述>
      * @param dataSourceType
      * @param tableName [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected String getCreateTableSql(DataSourceTypeEnum dataSourceType,
            String tableName, Map<String, String> params) {
        String createTableSql = "";
        switch (dataSourceType) {
            case ORACLE:
            case ORACLE9I:
            case ORACLE10G:
                createTableSql = getCreateSqlForOracle(tableName, params);
                break;
            case H2:
                createTableSql = getCreateSqlForH2(tableName, params);
            default:
                throw new DataSourceTypeUnSupportException(dataSourceType);
        }
        return createTableSql;
    }
    
    protected abstract String getCreateSqlForOracle(String tableName,
            Map<String, String> params);
    
    protected abstract String getCreateSqlForH2(String tableName,
            Map<String, String> params);
    
    /**
     * @param dataSourceType
     * @param params
     */
    @Override
    public void initTableData(DataSourceTypeEnum dataSourceType,
            Map<String, String> params) {
        //this.transactionTemplate.execute(action);
        String tableName = getTableName(params);
        String createTableSql = getCreateTableSql(dataSourceType,
                tableName,
                params);
        this.jdbcTemplate.execute(createTableSql);
    }
    
    protected abstract List<String> doGetInitDataSql();
    
    /**
     * @param dataSourceType
     * @param params
     */
    @Override
    public void backupTable(DataSourceTypeEnum dataSourceType,
            Map<String, String> params) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @param dataSourceType
     * @param params
     */
    @Override
    public void updateTable(DataSourceTypeEnum dataSourceType, String version,
            Map<String, String> params) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @param dataSourceType
     * @param params
     */
    @Override
    public void updateTableData(DataSourceTypeEnum dataSourceType,
            String version, Map<String, String> params) {
        // TODO Auto-generated method stub
        
    }
}
