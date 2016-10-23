/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月20日
 * <修改描述:>
 */
package com.tx.core.ddlutil.executor;

import java.util.List;

import com.tx.core.ddlutil.alter.AlterTableDDLBuilder;
import com.tx.core.ddlutil.create.CreateTableDDLBuilder;
import com.tx.core.ddlutil.model.DDLColumn;
import com.tx.core.ddlutil.model.DDLIndex;
import com.tx.core.ddlutil.model.Table;

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
    public Table findDDLTableDetailByTableName(String tableName);
    
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
    public Table findDDLTableByTableName(String tableName);
    
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
    public List<DDLColumn> queryDDLColumnsByTableName(String tableName);
    
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
    public List<DDLIndex> queryDDLIndexesByTableName(String tableName);
}
