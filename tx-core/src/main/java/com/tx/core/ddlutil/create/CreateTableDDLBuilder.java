package com.tx.core.ddlutil.create;

import java.math.BigDecimal;

import com.tx.core.ddlutil.model.TableColumnDef;
import com.tx.core.ddlutil.model.TableIndexDef;

/**
  * 建表DDL构建器<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2016年11月6日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public interface CreateTableDDLBuilder {
    
    /**
      * 新增字段<br/>
      * <功能详细描述>
      * @param ddlColumn
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract CreateTableDDLBuilder newColumn(TableColumnDef tableColumn);
    
    /**
      * 增加非主键Varchar字段<br/>
      * <功能详细描述>
      * @param columnName
      * @param size
      * @param required
      * @param defaultValue
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract CreateTableDDLBuilder newColumnOfVarchar(String columnName,
            int size, boolean required, String defaultValue);
    
    /**
      * 增加Varchar类型字段<br/>
      * <功能详细描述>
      * @param primaryKey
      * @param columnName
      * @param size
      * @param required
      * @param defaultValue
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract CreateTableDDLBuilder newColumnOfVarchar(
            boolean primaryKey, String columnName, int size, boolean required,
            String defaultValue);
    
    /**
      * 添加非主键Integer字段<br/>
      * <功能详细描述>
      * @param columnName
      * @param size
      * @param required
      * @param defaultValue
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract CreateTableDDLBuilder newColumnOfInteger(String columnName,
            int size, boolean required, Integer defaultValue);
    
    /**
      * 添加integer类字段<br/>
      * <功能详细描述>
      * @param primaryKey
      * @param columnName
      * @param size
      * @param required
      * @param defaultValue
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
      */
    public abstract CreateTableDDLBuilder newColumnOfInteger(
            boolean primaryKey, String columnName, int size, boolean required,
            Integer defaultValue);
    
    /**
      * 新增时间字段<br/>
      * <功能详细描述>
      * @param columnName
      * @param required
      * @param isDefaultNow
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract CreateTableDDLBuilder newColumnOfDate(String columnName,
            boolean required, boolean isDefaultNow);
    
    /**
      * 添加一个Boolean类型对应的字段：从兼容性考虑，统一使用Bit去表示一个boolean类型<br/>
      * <功能详细描述>
      * @param columnName
      * @param required
      * @param defaultValue
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract CreateTableDDLBuilder newColumnOfBoolean(String columnName,
            boolean required, Boolean defaultValue);
    
    /**
      * 新增一个decimal类型的字段<br/>   
      * <功能详细描述>
      * @param columnName
      * @param size
      * @param scale
      * @param required
      * @param defaultValue
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract CreateTableDDLBuilder newColumnOfBigDecimal(
            String columnName, int size, int scale, boolean required,
            BigDecimal defaultValue);
    
    /**
      * 添加索引<br/>
      * <功能详细描述>
      * @param ddlIndex
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract CreateTableDDLBuilder newIndex(TableIndexDef ddlIndex);
    
    /**
      * 新增非唯一键索引<br/>
      * <功能详细描述>
      * @param indexName
      * @param columnNames
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract CreateTableDDLBuilder newIndex(String indexName,
            String... columnNames);
    
    /**
      * 新增表索引<br/>
      * <功能详细描述>
      * @param unique
      * @param indexName
      * @param columnNames
      * @return [参数说明]
      * 
      * @return CreateTableDDLBuilder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract CreateTableDDLBuilder newIndex(boolean unique,
            String indexName, String... columnNames);
    
    /**
      * 获取对应的建表Sql
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract String createSql();
    
    /**
      * 获取对应的表名<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract String getTableName();
    
}