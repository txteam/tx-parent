/*

 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月20日
 * <修改描述:>
 */
package com.tx.core.ddlutil.executor.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.PropertyPlaceholderHelper;

import com.tx.core.TxConstants;
import com.tx.core.ddlutil.builder.alter.AlterTableDDLBuilder;
import com.tx.core.ddlutil.builder.alter.impl.AlterTableDDLBuilderFactoryRegistry;
import com.tx.core.ddlutil.builder.create.CreateTableDDLBuilder;
import com.tx.core.ddlutil.builder.create.impl.CreateTableDDLBuilderFactoryRegistry;
import com.tx.core.ddlutil.dialect.DDLDialect;
import com.tx.core.ddlutil.dialect.MysqlDDLDialect;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.ddlutil.model.DBColumnDef;
import com.tx.core.ddlutil.model.DBIndexDef;
import com.tx.core.ddlutil.model.DBTableDef;
import com.tx.core.ddlutil.model.JdbcTypeEnum;
import com.tx.core.ddlutil.model.TableDef;
import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.MessageUtils;
import com.tx.core.util.SqlUtils;
import com.tx.core.util.dialect.DataSourceTypeEnum;

/**
 * Mysql 表相关 DDL执行器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MysqlTableDDLExecutor
        implements TableDDLExecutor, InitializingBean {
    
    //日志记录器
    private Logger logger = LoggerFactory.getLogger(TableDDLExecutor.class);
    
    //根据表名判断表是否存在.
    private static final String SQL_TABLE_IS_EXISTS = "SELECT COUNT(1) FROM INFORMATION_SCHEMA.`TABLES` TAB "
            + "WHERE TAB.TABLE_NAME = ? AND TAB.TABLE_SCHEMA = ? AND TAB.TABLE_TYPE='BASE TABLE'";
    
    //查询table数据中的实际定义
    private static final String SQL_FIND_TABLE_BY_TABLENAME = "SELECT "
            + "TABLE_CATALOG as 'catalog',TABLE_SCHEMA as 'schema',"
            + "TABLE_NAME as 'tableName',TABLE_COMMENT as 'comment' "
            + "FROM INFORMATION_SCHEMA.`TABLES` TAB "
            + "WHERE TAB.TABLE_NAME = ? AND TAB.TABLE_SCHEMA = ? AND TAB.TABLE_TYPE='BASE TABLE'";
    
    //查询出的table定义的RowMapper
    private static final RowMapper<DBTableDef> ddlTableRowMapper = new BeanPropertyRowMapper<DBTableDef>(
            DBTableDef.class);
    
    //查询table中column定义
    private static final String SQL_QUERY_COLUMN_BY_TABLENAME = "SELECT "
            + "TCOL.COLUMN_NAME AS 'columnName', TCOL.COLUMN_TYPE AS 'columnType', "
            + "TCOL.TABLE_NAME AS 'tableName', "
            + "( CASE WHEN TCOL.COLUMN_KEY = 'PRI' THEN 'Y' ELSE 'N' END ) AS 'primaryKey', "
            + "( CASE WHEN TCOL.IS_NULLABLE = 'NO' THEN 'Y' ELSE 'N' END ) AS 'required', "
            + "( CASE WHEN TCOL.DATA_TYPE = 'int' THEN 'integer' ELSE TCOL.DATA_TYPE END ) AS 'jdbcType', "
            + "( CASE WHEN TCOL.CHARACTER_MAXIMUM_LENGTH IS NULL THEN TCOL.NUMERIC_PRECISION ELSE TCOL.CHARACTER_MAXIMUM_LENGTH END ) AS size, "
            + "TCOL.NUMERIC_SCALE AS 'scale', " + "( CASE "
            + "     WHEN TCOL.DATA_TYPE = 'bit' AND TCOL.COLUMN_DEFAULT = 'b''1''' THEN 1 "
            + "     WHEN TCOL.DATA_TYPE = 'bit' AND TCOL.COLUMN_DEFAULT = 'b''0''' THEN 0 "
            + "     WHEN TCOL.DATA_TYPE = 'datetime' AND TCOL.COLUMN_DEFAULT = 'CURRENT_TIMESTAMP' THEN 'now()' "
            + "     WHEN TCOL.DATA_TYPE = 'varchar' THEN CONCAT( '''', TCOL.COLUMN_DEFAULT, '''' ) "
            + "     ELSE TCOL.COLUMN_DEFAULT END " + ") AS 'defaultValue' "
            + "FROM INFORMATION_SCHEMA.`COLUMNS` TCOL "
            + "WHERE TCOL.TABLE_NAME = ? AND TCOL.TABLE_SCHEMA = ?";
    
    //查询出的column定义的RowMapper
    private static final RowMapper<DBColumnDef> ddlColumnRowMapper = new RowMapper<DBColumnDef>() {
        @Override
        public DBColumnDef mapRow(ResultSet rs, int rowNum)
                throws SQLException {
            DBColumnDef col = new DBColumnDef();
            col.setColumnName(rs.getString("columnName"));
            col.setTableName(rs.getString("tableName"));
            
            col.setPrimaryKey(rs.getBoolean("primaryKey"));
            col.setRequired(rs.getBoolean("required"));
            col.setSize(rs.getInt("size"));
            col.setScale(rs.getInt("scale"));
            
            String jdbcTypeString = rs.getString("jdbcType");
            JdbcTypeEnum jdbcType = JdbcTypeEnum.forTypeKey(jdbcTypeString);
            AssertUtils.notNull(jdbcType,
                    "jdbcType is null.:{}",
                    jdbcTypeString);
            col.setJdbcType(jdbcType);
            
            String defaultValue = rs.getString("defaultValue");
            switch (jdbcType) {
                case DATE:
                    if (StringUtils.equals("CURRENT_TIMESTAMP", defaultValue)) {
                        defaultValue = "now()";
                    }
                    break;
                default:
                    if (StringUtils.equals("b'1'", defaultValue)) {
                        defaultValue = "1";//"true";
                    } else if (StringUtils.equals("b'0'", defaultValue)) {
                        defaultValue = "0";//"false";
                    } else if (StringUtils.equals("''", defaultValue)) {
                        defaultValue = "";
                    }
                    break;
            }
            col.setDefaultValue(defaultValue);
            return col;
        }
    };
    
    //查询索引SQL
    private static final String SQL_QUERY_INDEX_BY_TABLENAME = " SELECT T.indexName,T.tableName,T.uniqueKey,GROUP_CONCAT(T.columnName) as columnNames from ( "
            + "SELECT " + "TIDX.INDEX_NAME AS 'indexName', "
            + "TIDX.COLUMN_NAME AS 'columnName', "
            + "TIDX.TABLE_NAME AS 'tableName', "
            + "(CASE WHEN TIDX.NON_UNIQUE = 1 THEN 0 ELSE 1 END ) AS 'uniqueKey', "
            + "TIDX.SEQ_IN_INDEX as 'orderPriority' "
            + "FROM information_schema.`STATISTICS` TIDX "
            + "LEFT JOIN information_schema.`TABLE_CONSTRAINTS` TCONS ON (TIDX.TABLE_SCHEMA = TCONS.CONSTRAINT_SCHEMA AND TIDX.TABLE_NAME = TCONS.TABLE_NAME AND TIDX.INDEX_NAME = TCONS.CONSTRAINT_NAME) "
            + "WHERE TIDX.TABLE_NAME = ? AND TIDX.TABLE_SCHEMA = ? "
            + "AND (TCONS.CONSTRAINT_TYPE is null or 'PRIMARY KEY' <> TCONS.CONSTRAINT_TYPE) "
            + "ORDER BY TIDX.INDEX_NAME,TIDX.SEQ_IN_INDEX "
            + ") T GROUP BY indexName,tableName,uniqueKey ";
    
    //DDLindexRowMap
    private static final RowMapper<DBIndexDef> ddlIndexRowMapper = new RowMapper<DBIndexDef>() {
        @Override
        public DBIndexDef mapRow(ResultSet rs, int rowNum) throws SQLException {
            DBIndexDef ddlIndex = new DBIndexDef();
            ddlIndex.setIndexName(rs.getString("indexName"));
            ddlIndex.setTableName(rs.getString("tableName"));
            ddlIndex.setUniqueKey(rs.getBoolean("uniqueKey"));
            ddlIndex.setColumnNames(rs.getString("columnNames"));
            return ddlIndex;
        }
    };
    
    //DROP表
    private static final String SQL_DROP_TABLE = "DROP TABLE ${tableName}";
    
    //BACKUP表
    private static final String SQL_BACKUP_TABLE = "CREATE TABLE ${backupTableName} AS SELECT * FROM ${tableName}";
    
    private static PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper(
            "${", "}");
    
    /** jdbcTemplate句柄 */
    private JdbcTemplate jdbcTemplate;
    
    /** dataSource句柄 */
    private DataSource dataSource;
    
    /** schema */
    private String schema;
    
    /** <默认构造函数> */
    public MysqlTableDDLExecutor(DataSource dataSource,
            JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
        init();
    }
    
    /** <默认构造函数> */
    public MysqlTableDDLExecutor(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
        init();
    }
    
    /** <默认构造函数> */
    public MysqlTableDDLExecutor(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        init();
    }
    
    /** <默认构造函数> */
    public MysqlTableDDLExecutor() {
        super();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
    
    /**
      * 初始化表<br/>
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void init() {
        AssertUtils.isTrue(this.dataSource != null || this.jdbcTemplate != null,
                "dataSource and jdbcTemplate all is null.");
        if (this.dataSource == null) {
            this.dataSource = this.jdbcTemplate.getDataSource();
        }
        if (this.jdbcTemplate == null) {
            this.jdbcTemplate = new JdbcTemplate(this.dataSource);
        }
        Connection conn = null;
        try {
            conn = this.dataSource.getConnection();
            try {
                this.schema = conn.getSchema();
            } catch (Throwable e) {
                this.schema = conn.getCatalog();
            }
            if (StringUtils.isEmpty(this.schema)) {
                this.schema = conn.getCatalog();
            }
            AssertUtils.notEmpty(this.schema, "schema is empty.");
        } catch (SQLException e) {
            throw new SILException("dataSource.getConnection exception.", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new SILException("connnection close exception.", e);
            }
        }
        
    }
    
    /**
     * 根据表名判断表是否存在<br/>
     * <功能详细描述>
     * @param tableName
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean exists(String tableName) {
        AssertUtils.notEmpty(tableName, "tableName is empty.");
        
        int count = this.jdbcTemplate.queryForObject(SQL_TABLE_IS_EXISTS,
                new Object[] { tableName, this.schema },
                Integer.class);
        
        boolean flag = count > 0;
        return flag;
    }
    
    /**
      * Drop指定表名的数据库表<br/>
      * <功能详细描述>
      * @param tableName [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Override
    public void drop(String tableName) {
        AssertUtils.notEmpty(tableName, "tableName is empty.");
        AssertUtils.isTrue(exists(tableName),
                "table is not exist.tableName:{}",
                tableName);
        
        Properties props = new Properties();
        props.put("tableName", tableName);
        String dropSql = placeholderHelper.replacePlaceholders(SQL_DROP_TABLE,
                props);
        
        //打印删除表日志:
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("删除数据库表:").append(tableName).append("\t\n");
        sb.append("------删除表:")
                .append(tableName)
                .append("----------------------------start")
                .append("\t\n");
        sb.append(dropSql).append("\t\n");
        sb.append("------删除表:")
                .append(tableName)
                .append("------------------------------end")
                .append("\t\n");
        logger.info(sb.toString());
        
        this.jdbcTemplate.execute(dropSql);
        
        logger.info("删除数据库表:{}成功.", tableName);
    }
    
    /**
      * 备份表<br/>
      * <功能详细描述>
      * @param tableName
      * @param backupTableName [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Override
    public void backup(String tableName, String backupTableName) {
        AssertUtils.notEmpty(tableName, "tableName is empty.");
        AssertUtils.notEmpty(backupTableName, "backupTableName is empty.");
        
        AssertUtils.isTrue(exists(tableName),
                "table is not exist.tableName:{}",
                tableName);
        AssertUtils.isTrue(!exists(backupTableName),
                "backupTableName is exist.backupTableName:{}",
                backupTableName);
        
        Properties props = new Properties();
        props.put("tableName", tableName);
        props.put("backupTableName", backupTableName);
        String backupSql = placeholderHelper
                .replacePlaceholders(SQL_BACKUP_TABLE, props);
        
        //打印备份表日志:
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("备份数据库表:")
                .append(tableName)
                .append(" to ")
                .append(backupTableName)
                .append("\t\n");
        sb.append("------备份表:")
                .append(tableName)
                .append(" to ")
                .append(backupTableName)
                .append("----------------------------start")
                .append("\t\n");
        sb.append(backupSql).append("\t\n");
        sb.append("------备份表:")
                .append(tableName)
                .append(" to ")
                .append(backupTableName)
                .append("------------------------------end")
                .append("\t\n");
        logger.info(sb.toString());
        
        this.jdbcTemplate.execute(backupSql);
        
        logger.info("备份数据库表:{} to {} 成功.", tableName, backupTableName);
    }
    
    /**
     * 创建表<br/>
     * <功能详细描述>
     * @param builder [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @Override
    public void create(CreateTableDDLBuilder builder) {
        AssertUtils.notNull(builder, "builder is null.");
        AssertUtils.isTrue(!exists(builder.tableName()),
                "table is exist.tableName:{}",
                builder.tableName());
        AssertUtils.notEmpty(builder.getColumns(), "table.columns is empty.");
        
        String createSql = builder.createSql();
        
        //打印创建表日志:
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("创建数据库表:").append(builder.tableName()).append("\t\n");
        sb.append("------创建表:")
                .append(builder.tableName())
                .append("----------------------------start")
                .append("\t\n");
        sb.append(createSql).append("\t\n");
        sb.append("------创建表:")
                .append(builder.tableName())
                .append("------------------------------end")
                .append("\t\n");
        logger.info(sb.toString());
        
        createSql = SqlUtils.format(builder.createSql());
        String[] createSqls = StringUtils.splitByWholeSeparator(createSql, ";");
        for (String createSqlTemp : createSqls) {
            if (!StringUtils.isBlank(createSqlTemp)) {
                this.jdbcTemplate.execute(createSqlTemp);
            }
        }
        
        logger.info("创建数据库表: {} 成功.", builder.tableName());
    }
    
    /**
     * 修改表<br/>
     * <功能详细描述>
     * @param builder [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @Override
    public void alter(AlterTableDDLBuilder builder) {
        AssertUtils.notNull(builder, "builder is null.");
        AssertUtils.isTrue(exists(builder.tableName()),
                "table is not exist.tableName:{}",
                builder.tableName());
        
        String alterSql = builder.alterSql();
        if (StringUtils.isBlank(alterSql)) {
            return;
        }
        
        //打印修改表日志:
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("修改数据库表:").append(builder.tableName()).append("\t\n");
        sb.append("------修改表:")
                .append(builder.tableName())
                .append("----------------------------start")
                .append("\t\n");
        sb.append(alterSql).append("\t\n");
        sb.append("------修改表:")
                .append(builder.tableName())
                .append("------------------------------end")
                .append("\t\n");
        logger.info(sb.toString());
        
        alterSql = SqlUtils.format(alterSql);
        try {
            String[] alterSqls = StringUtils.splitByWholeSeparator(alterSql,
                    ";");
            for (String alterSqlTemp : alterSqls) {
                if (!StringUtils.isBlank(alterSqlTemp)) {
                    this.jdbcTemplate.execute(alterSqlTemp);
                }
            }
            
            logger.info("修改数据库表: {} 成功.", builder.tableName());
        } catch (DataAccessException e) {
            logger.error(MessageUtils.format("修改数据库表: {} 异常.异常信息:{}.",
                    builder.tableName(),
                    e.getMessage()), e);
            
            throw new SILException(MessageUtils.format("修改数据库表: {} 异常.异常信息:{}.",
                    builder.tableName(),
                    e.getMessage()), e);
        } catch (SILException e) {
            logger.error(MessageUtils.format("修改数据库表: {} 异常.异常信息:{}.",
                    builder.tableName(),
                    e.getMessage()), e);
            throw e;
        }
    }
    
    /**
     * 从当前数据库中获取当前表定义<br/>
     * <功能详细描述>
     * @param tableName
     * @return [参数说明]
     * 
     * @return DDLTable [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @Override
    public DBTableDef findDBTableDetailByTableName(String tableName) {
        AssertUtils.notEmpty(tableName, "tableName is empty.");
        
        List<DBTableDef> ddlTableList = this.jdbcTemplate.query(
                SQL_FIND_TABLE_BY_TABLENAME,
                new Object[] { tableName, this.schema },
                ddlTableRowMapper);
        if (CollectionUtils.isEmpty(ddlTableList)) {
            return null;
        }
        
        DBTableDef ddlTable = ddlTableList.get(0);
        List<DBColumnDef> columns = queryDBColumnsByTableName(tableName);
        List<DBIndexDef> indexes = queryDBIndexesByTableName(tableName);
        ddlTable.setColumns(columns);
        ddlTable.setIndexes(indexes);
        
        return ddlTable;
    }
    
    /**
      * 从当前数据库中获取当前表定义<br/>
      * <功能详细描述>
      * @param tableName
      * @return [参数说明]
      * 
      * @return DDLTable [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Override
    public DBTableDef findDBTableByTableName(String tableName) {
        AssertUtils.notEmpty(tableName, "tableName is empty.");
        
        List<DBTableDef> ddlTableList = this.jdbcTemplate.query(
                SQL_FIND_TABLE_BY_TABLENAME,
                new Object[] { tableName, this.schema },
                ddlTableRowMapper);
        if (CollectionUtils.isEmpty(ddlTableList)) {
            return null;
        }
        DBTableDef ddlTable = ddlTableList.get(0);
        return ddlTable;
    }
    
    /**
      * 根据表名查询DDL的字段集合<br/>
      * <功能详细描述>
      * @param tableName
      * @return [参数说明]
      * 
      * @return List<DDLColumn> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Override
    public List<DBColumnDef> queryDBColumnsByTableName(String tableName) {
        AssertUtils.notEmpty(tableName, "tableName is empty.");
        
        List<DBColumnDef> ddlColumnList = this.jdbcTemplate.query(
                SQL_QUERY_COLUMN_BY_TABLENAME,
                new Object[] { tableName, this.schema },
                ddlColumnRowMapper);
        return ddlColumnList;
    }
    
    /**
      * 根据表名查询DDL的索引集合<br/>
      * <功能详细描述>
      * @param tableName
      * @return [参数说明]
      * 
      * @return List<DDLIndex> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Override
    public List<DBIndexDef> queryDBIndexesByTableName(String tableName) {
        AssertUtils.notEmpty(tableName, "tableName is empty.");
        
        List<DBIndexDef> ddlIndexList = this.jdbcTemplate.query(
                SQL_QUERY_INDEX_BY_TABLENAME,
                new Object[] { tableName, this.schema },
                ddlIndexRowMapper);
        return ddlIndexList;
    }
    
    /**
     * 生成创建表的Builder对象<br/>
     * <功能详细描述>
     * @param tableName
     * @return [参数说明]
     * 
     * @return CreateTableDDLBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @Override
    public CreateTableDDLBuilder generateCreateTableDDLBuilder(
            String tableName) {
        AssertUtils.notEmpty(tableName, "tableName is empty.");
        
        CreateTableDDLBuilder builder = CreateTableDDLBuilderFactoryRegistry
                .getFactory(DataSourceTypeEnum.MYSQL).newInstance(tableName);
        return builder;
    }
    
    /**
     * @param table
     * @return
     */
    @Override
    public CreateTableDDLBuilder generateCreateTableDDLBuilder(TableDef table) {
        AssertUtils.notNull(table, "table is null.");
        AssertUtils.notEmpty(table.getTableName(), "table.tableName is empty.");
        
        CreateTableDDLBuilder builder = CreateTableDDLBuilderFactoryRegistry
                .getFactory(DataSourceTypeEnum.MYSQL).newInstance(table);
        return builder;
    }
    
    /**
     * 生成修改表的Builder对象<br/>
     * <功能详细描述>
     * @param tableName
     * @return [参数说明]
     * 
     * @return CreateTableDDLBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @Override
    public AlterTableDDLBuilder generateAlterTableDDLBuilder(String tableName) {
        AssertUtils.notEmpty(tableName, "tableName is empty.");
        AssertUtils.isTrue(exists(tableName),
                "table is not exist.tableName:{}",
                tableName);
        
        TableDef sourceTableDef = findDBTableDetailByTableName(tableName);
        AlterTableDDLBuilder builder = AlterTableDDLBuilderFactoryRegistry
                .getFactory(DataSourceTypeEnum.MYSQL)
                .newInstance(sourceTableDef);
        return builder;
    }
    
    /**
     * @param table
     * @return
     */
    @Override
    public AlterTableDDLBuilder generateAlterTableDDLBuilder(
            TableDef newTable) {
        AssertUtils.notNull(newTable, "table is null.");
        AssertUtils.notEmpty(newTable.getTableName(),
                "newTable.tableName is empty.");
        AssertUtils.isTrue(exists(newTable.getTableName()),
                "table is not exist.tableName:{}",
                newTable.getTableName());
        
        TableDef sourceTableDef = findDBTableDetailByTableName(
                newTable.getTableName());
        AlterTableDDLBuilder builder = AlterTableDDLBuilderFactoryRegistry
                .getFactory(DataSourceTypeEnum.MYSQL)
                .newInstance(newTable, sourceTableDef);
        return builder;
    }
    
    /**
     * @param newTable
     * @param sourceTable
     * @return
     */
    @Override
    public AlterTableDDLBuilder generateAlterTableDDLBuilder(TableDef newTable,
            TableDef sourceTable) {
        AssertUtils.notNull(newTable, "table is null.");
        AssertUtils.notEmpty(newTable.getTableName(),
                "newTable.tableName is empty.");
        
        AssertUtils.notNull(sourceTable, "table is null.");
        AssertUtils.notEmpty(sourceTable.getTableName(),
                "sourceTable.tableName is empty.");
        
        AssertUtils.isTrue(
                newTable.getTableName()
                        .equalsIgnoreCase(sourceTable.getTableName()),
                "newTable.tableName:{} should equalsIgnoreCase sourceTable.tableName:{}",
                new Object[] { newTable.getTableName(),
                        sourceTable.getTableName() });
        
        AlterTableDDLBuilder builder = AlterTableDDLBuilderFactoryRegistry
                .getFactory(DataSourceTypeEnum.MYSQL)
                .newInstance(newTable, sourceTable);
        return builder;
    }
    
    /**
     * @return
     */
    @Override
    public DDLDialect getDDLDialect() {
        return MysqlDDLDialect.INSTANCE;
    }
    
}
