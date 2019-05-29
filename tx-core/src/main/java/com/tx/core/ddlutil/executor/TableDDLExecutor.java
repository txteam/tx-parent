/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月20日
 * <修改描述:>
 */
package com.tx.core.ddlutil.executor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.core.ddlutil.builder.alter.AlterTableDDLBuilder;
import com.tx.core.ddlutil.builder.create.CreateTableDDLBuilder;
import com.tx.core.ddlutil.dialect.Dialect4DDL;
import com.tx.core.ddlutil.model.DBColumnDef;
import com.tx.core.ddlutil.model.DBIndexDef;
import com.tx.core.ddlutil.model.DBTableDef;
import com.tx.core.ddlutil.model.TableDef;

/**
 * 表DDL处理器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface TableDDLExecutor {
    
    static final Logger logger = LoggerFactory
            .getLogger(TableDDLExecutor.class);
    
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
    public boolean exists(String tableName);
    
    /**
     * Drop指定表名的数据库表<br/>
     * <功能详细描述>
     * @param tableName [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void drop(String tableName);
    
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
    public void backup(String tableName, String backupTableName);
    
    /**
     * 创建表<br/>
     * <功能详细描述>
     * @param builder [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void create(CreateTableDDLBuilder builder);
    
    /**
     * 修改表<br/>
     * <功能详细描述>
     * @param builder [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void alter(AlterTableDDLBuilder builder);
    
    //    /**
    //      * 根据修改表的Builder执行修改表逻辑<br/>
    //      * <功能详细描述>
    //      * @param builder
    //      * @param isIncrementUpdate
    //      * @param isIgnoreIndexChange [参数说明]
    //      * 
    //      * @return void [返回类型说明]
    //      * @exception throws [异常类型] [异常说明]
    //      * @see [类、类#方法、类#成员]
    //     */
    //    public void alter(AlterTableDDLBuilder builder, boolean isIncrementUpdate);
    //    
    //    /**
    //      * 判断是否需要升级(仅考虑增量升级，减少字段，减少字段长度无需进行升级)<br/>
    //      * <功能详细描述>
    //      * @param newTableDef
    //      * @param oldTableDef [参数说明]
    //      * 
    //      * @return void [返回类型说明]
    //      * @exception throws [异常类型] [异常说明]
    //      * @see [类、类#方法、类#成员]
    //     */
    //    public boolean isNeedUpdate(TableDef newTableDef, TableDef oldTableDef);
    //    
    //    /**
    //      * 判断是否需要升级<br/>
    //      * <功能详细描述>
    //      * @param newTableDef
    //      * @param oldTableDef
    //      * @param isIncrementalUpgrade [参数说明]
    //      * 
    //      * @return void [返回类型说明]
    //      * @exception throws [异常类型] [异常说明]
    //      * @see [类、类#方法、类#成员]
    //     */
    //    public boolean isNeedUpdate(TableDef newTableDef, TableDef oldTableDef,
    //            boolean isIncrementalUpgrade);
    
    /**
      * 根据表名查询对应的DDLTable详情，查询期间将会查询对应的索引以及字段<br/>
      * <功能详细描述>
      * @param tableName
      * @return [参数说明]
      * 
      * @return DDLTable [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public DBTableDef findDBTableDetailByTableName(String tableName);
    
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
    public DBTableDef findDBTableByTableName(String tableName);
    
    /**
      * 根据表名查询DDL的字段集合<br/>
      *<功能详细描述>
      * @param tableName
      * @return [参数说明]
      * 
      * @return List<DDLColumn> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<DBColumnDef> queryDBColumnsByTableName(String tableName);
    
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
    public List<DBIndexDef> queryDBIndexesByTableName(String tableName);
    
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
    public CreateTableDDLBuilder generateCreateTableDDLBuilder(
            String tableName);
    
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
    public CreateTableDDLBuilder generateCreateTableDDLBuilder(TableDef table);
    
    /**
      * 生成修改表的Builder对象<br/>
      * <功能详细描述>
      * @param tableName
      * @return [参数说明]
      * 
      * @return AlterTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public AlterTableDDLBuilder generateAlterTableDDLBuilder(String tableName);
    
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
    public AlterTableDDLBuilder generateAlterTableDDLBuilder(TableDef newTable);
    
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
    public AlterTableDDLBuilder generateAlterTableDDLBuilder(TableDef newTable,
            TableDef sourceTable);
    
    /**
     * 获取实现的DDL方言实现<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return DDLDialect [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Dialect4DDL getDDLDialect();
}
