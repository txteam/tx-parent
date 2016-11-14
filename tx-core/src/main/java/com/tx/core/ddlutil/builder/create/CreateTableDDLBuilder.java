package com.tx.core.ddlutil.builder.create;

import java.util.List;

import com.tx.core.ddlutil.builder.DDLBuilder;
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
public interface CreateTableDDLBuilder extends
        DDLBuilder<CreateTableDDLBuilder> {
    
    /**
      * 获取对应的表名<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract String tableName();
    
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
      * 设置表名<br/>
      * <功能详细描述>
      * @param tableName [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract void setTableName(String tableName);
    
    /**
      * 获取所有字段<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<? extends TableColumnDef> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<? extends TableColumnDef> getColumns();
    
    /**
      * 获取所有的索引<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<? extends TableIndexDef> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<? extends TableIndexDef> getIndexes();
}