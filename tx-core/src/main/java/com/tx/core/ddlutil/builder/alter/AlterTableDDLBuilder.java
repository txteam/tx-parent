/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月20日
 * <修改描述:>
 */
package com.tx.core.ddlutil.builder.alter;

import com.tx.core.ddlutil.builder.DDLBuilder;
import com.tx.core.ddlutil.model.TableDef;

/**
 * 修改表DDL构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface AlterTableDDLBuilder extends DDLBuilder<AlterTableDDLBuilder> {
    
    /**
      * 原表定义<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return TableDef [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract TableDef sourceTable();
    
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
     * 获取对应的修改表Sql
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public abstract String alterSql();
    
    /**
      * 获取对应的修改表Sql
      * <功能详细描述>
      * @param isIncrementUpdate
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String alterSql(boolean isIncrementUpdate);
    
    /**
     * 是否需要更新修改表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isNeedAlter();
    
    /**
     * 是否需要更新修改表<br/>
     * <功能详细描述>
     * @param isIncrementUpdate
     * @param isIgnoreIndexChange
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isNeedAlter(boolean isIncrementUpdate);
}
