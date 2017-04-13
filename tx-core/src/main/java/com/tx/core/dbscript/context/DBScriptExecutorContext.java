/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-17
 * <修改描述:>
 */
package com.tx.core.dbscript.context;

import java.util.HashMap;
import java.util.Map;

import com.tx.core.dbscript.TableCreator;
import com.tx.core.dbscript.TableDefinition;
import com.tx.core.dbscript.XMLTableDefinition;
import com.tx.core.dbscript.model.DBScriptContext;
import com.tx.core.jdbc.sqlsource.SqlSource;
import com.tx.core.jdbc.sqlsource.SqlSourceBuilder;
import com.tx.core.util.UUIDUtils;

/**
 * 数据库脚本自动执行容器<br/>
 * <功能详细描述>
 * 
 * @author brady
 * @version [版本号, 2013-12-17]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DBScriptExecutorContext extends DBScriptExecutorContextConfigurator {
    
    private TableDefinition dbScriptContextTableDefinition = new XMLTableDefinition(
            "classpath:com/tx/core/dbscript/script/dbscript_context_table.xml");
    
    /** 表创建器 */
    private TableCreator tableCreator;
    
    /** 数据脚本容器sqlSource */
    private SqlSource<DBScriptContext> dbScriptContextSqlSource;
    
    /** <默认构造函数> */
    public DBScriptExecutorContext() {
        logger.debug("实例化 数据库脚本自动执行容器 [DBScriptExecutorContext]  ");
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        SqlSourceBuilder ssb = new SqlSourceBuilder();
        dbScriptContextSqlSource = ssb.build(DBScriptContext.class, dataSourceType.getDialect());
        if (tableCreator == null) {
            tableCreator = new TableCreator(this);
        }
        
        createOrUpdateTable(dbScriptContextTableDefinition);
    }
    
    /**
     * 创建或更新表<br/>
     */
    public void createOrUpdateTable(TableDefinition tableDefinition) {
        this.tableCreator.createOrUpdateTable(tableDefinition);
    }
    
    /**
     * 获取创建表的语句<br/>
     * <功能详细描述>
     * 
     * @param tableDefinition [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isExistInContext(String tableName) {
        Map<String, Object> queryCondition = new HashMap<String, Object>();
        queryCondition.put("tableName", tableName);
        
        int resInt = this.jdbcTemplate.queryForObject(this.dbScriptContextSqlSource.countSql(queryCondition),
                new Object[] { tableName },
                Integer.class);
        return resInt > 0;
    }
    
    /**
     * 根据表名查询容器中对应DBScriptContext实例<br/>
     * <功能详细描述>
     * 
     * @param tableName
     * @return [参数说明]
     * 
     * @return DBScriptContext [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public DBScriptContext findDBScriptContextByTableName(String tableName) {
        Map<String, Object> queryCondition = new HashMap<String, Object>();
        queryCondition.put("tableName", tableName);
        
        DBScriptContext dbScriptContext = this.jdbcTemplate.queryForObject(
                this.dbScriptContextSqlSource.querySql(queryCondition),
                this.dbScriptContextSqlSource.getSelectRowMapper(),
                tableName);
        return dbScriptContext;
    }
    
    /**
     * 删除数据 <功能详细描述>
     * 
     * @param dbScriptContextId [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void deleteById(String dbScriptContextId) {
        this.jdbcTemplate.update(this.dbScriptContextSqlSource.deleteSql(), dbScriptContextId);
    }
    
    /**
     * 新插入数据<br/>
     * <功能详细描述>
     * 
     * @param dbScriptContext [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void insert(DBScriptContext dbScriptContext) {
        dbScriptContext.setId(UUIDUtils.generateUUID());
        this.jdbcTemplate.update(this.dbScriptContextSqlSource.insertSql(),
                this.dbScriptContextSqlSource.getInsertSetter(dbScriptContext));
    }
    
    /**
     * @param 对tableCreator进行赋值
     */
    public void setTableCreator(TableCreator tableCreator) {
        this.tableCreator = tableCreator;
    }
}
