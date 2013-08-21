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
import org.springframework.util.CollectionUtils;

import com.tx.core.dbscript.exception.UnsupportAutoUpdateException;
import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.util.SqlUtils;

/**
 * TableResource基础类<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-8-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class BaseTableResource implements TableResource {
    
    /** 日志记录器 */
    private Logger logger = LoggerFactory.getLogger(TableResource.class);
    
    /** 数据操作句柄 */
    private JdbcTemplate jdbcTemplate;
    
    /**
     * <默认构造函数>
     */
    public BaseTableResource(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * <默认构造函数>
     */
    public BaseTableResource(DataSource dataSource) {
        super();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
        }
        catch (DataAccessException e) {
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
        String createTableSqlScript = doGetCreateTableSqlScript(dataSourceType,
                tableName,
                params);
        logger.info("准备执行创建表：{}.创建表脚本：{}.", tableName, createTableSqlScript);
        List<String> createTableSqlList = SqlUtils.splitSqlScript(createTableSqlScript);
        if (CollectionUtils.isEmpty(createTableSqlList)) {
            return;
        }
        for (String createSqlTemp : createTableSqlList) {
            logger.info("  创建表：{}.执行语句：{}.", tableName, createSqlTemp);
            jdbcTemplate.execute(createSqlTemp);
        }
        logger.info("  表创建成功：{}.", tableName);
    }
    
    /**
      * 获取创建表Sql
      *<功能详细描述>
      * @param dataSourceType
      * @param tableName [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract String doGetCreateTableSqlScript(
            DataSourceTypeEnum dataSourceType, String tableName,
            Map<String, String> params);
    
    /**
     * @param dataSourceType
     * @param params
     */
    @Override
    public void initTableData(DataSourceTypeEnum dataSourceType,
            Map<String, String> params) {
        String tableName = getTableName(params);
        String initTableSqlScript = doGetInitDataSqlScript(dataSourceType,
                tableName,
                params);
        
        logger.info("准备执行初始化表：{}.初始化脚本：{}.", tableName, initTableSqlScript);
        final List<String> initTableSqlList = SqlUtils.splitSqlScript(initTableSqlScript);
        if (CollectionUtils.isEmpty(initTableSqlList)) {
            return;
        }
        for (String initSqlTemp : initTableSqlList) {
            logger.info("  初始化表：{}.执行语句：{}.", tableName, initSqlTemp);
            jdbcTemplate.execute(initSqlTemp);
        }
        logger.info("  表初始化成功：{}.", tableName);
    }
    
    protected abstract String doGetInitDataSqlScript(
            DataSourceTypeEnum dataSourceType, String tableName,
            Map<String, String> params);
    
    /**
     * @param dataSourceType
     * @param params
     */
    @Override
    public void backupTable(DataSourceTypeEnum dataSourceType,
            Map<String, String> params) {
        String tableName = this.getTableName(params);
        throw new UnsupportAutoUpdateException(tableName, tableName
                + "暂不支持自动备份.");
    }
    
    /**
     * @param dataSourceType
     * @param params
     */
    @Override
    public void updateTable(DataSourceTypeEnum dataSourceType, String version,
            Map<String, String> params) {
        String tableName = this.getTableName(params);
        throw new UnsupportAutoUpdateException(tableName, tableName
                + "暂不支持表自动升级.");
    }
    
    /**
     * @param dataSourceType
     * @param params
     */
    @Override
    public void updateTableData(DataSourceTypeEnum dataSourceType,
            String version, Map<String, String> params) {
        String tableName = this.getTableName(params);
        throw new UnsupportAutoUpdateException(tableName, tableName
                + "暂不支持数据自动升级.");
    }
}
