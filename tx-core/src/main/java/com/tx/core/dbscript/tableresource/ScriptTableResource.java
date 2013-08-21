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

import com.tx.core.dbscript.exception.DataSourceTypeUnsupportException;
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
public abstract class ScriptTableResource extends BaseTableResource {
    
    /** 日志记录器 */
    private Logger logger = LoggerFactory.getLogger(TableResource.class);

    /**
     * @param dataSourceType
     * @param tableName
     * @param params
     * @return
     */
    @Override
    protected String doGetCreateTableSqlScript(
            DataSourceTypeEnum dataSourceType, String tableName,
            Map<String, String> params) {
        // TODO Auto-generated method stub
        return null;
    }



    /**
     * @param dataSourceType
     * @param tableName
     * @param params
     * @return
     */
    @Override
    protected String doGetInitDataSqlScript(DataSourceTypeEnum dataSourceType,
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
                throw new DataSourceTypeUnsupportException(dataSourceType,"自动创建表异常，不支持的数据源类型");
        }
        return createTableSql;
        return null;
    }
}
