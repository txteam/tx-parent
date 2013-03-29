/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-29
 * <修改描述:>
 */
package com.tx.component.rule.transation;




 /**
  * 规则会话事务回调方法
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-3-29]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface RuleSessionTransactionCallback {
    
    /**
      * 在事务中做的事情，一般在此方法中调用RuleSessionTemplate
      * <功能详细描述>
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    void doInTransaction() throws Throwable;
}
