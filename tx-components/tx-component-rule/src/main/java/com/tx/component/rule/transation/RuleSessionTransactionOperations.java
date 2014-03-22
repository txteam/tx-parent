/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-29
 * <修改描述:>
 */
package com.tx.component.rule.transation;

import java.util.Map;

/**
 * 规则会话事务执行器
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-3-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RuleSessionTransactionOperations {
    
    /**
      * 规则会话事务执行器<br/>
      *     执行的主体方法，主要用于开启事务，与关闭事务所用，该事务非db事务<br/>
      *<功能详细描述>
      * @param action
      * @param global [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    void execute(RuleSessionTransactionCallback action,
            Map<? extends String, ? extends Object> global) throws Throwable;
}
