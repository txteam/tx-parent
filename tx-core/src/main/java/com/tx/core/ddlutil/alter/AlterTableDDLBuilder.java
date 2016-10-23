/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月20日
 * <修改描述:>
 */
package com.tx.core.ddlutil.alter;



 /**
  * 表DDL构建器<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2016年10月20日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface AlterTableDDLBuilder {
    
    /**
     * 创建编辑表构建器实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return CreateTableDDLBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    AlterTableDDLBuilder newInstance(String tableName);
   
   /**
     * 执行最终建表语句<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   String alterSql();
}
