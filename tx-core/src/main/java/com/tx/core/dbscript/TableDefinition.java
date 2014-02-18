/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-17
 * <修改描述:>
 */
package com.tx.core.dbscript;

import com.tx.core.dbscript.model.DataSourceTypeEnum;

/**
 * 表定义<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-12-17]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface TableDefinition{
    
    /**
      * 获取表名 <br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String tableName();
    
    /**
      * 获取表版本号 <br/> 
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String tableVersion();
    
    /**
      * 获取创建表脚本<br/> 
      *<功能详细描述>
      * @param dataSourceType [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String createTableScript(DataSourceTypeEnum dataSourceType);
    
    /**
      * 获取更新表脚本<br/> 
      *<功能详细描述>
      * @param sourceTableVersion
      * @param dataSourceTyp [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String updateTableScript(String sourceTableVersion,
            DataSourceTypeEnum dataSourceTyp);
}
