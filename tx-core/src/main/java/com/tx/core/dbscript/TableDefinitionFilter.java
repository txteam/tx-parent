/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-18
 * <修改描述:>
 */
package com.tx.core.dbscript;


 /**
  * 用于过滤表定义的加载<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-12-18]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface TableDefinitionFilter {
    
    /**
      * 是否加载对应表定义<br/>
      *<功能详细描述>
      * @param tableDefinition
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean accept(TableDefinition tableDefinition);
}
