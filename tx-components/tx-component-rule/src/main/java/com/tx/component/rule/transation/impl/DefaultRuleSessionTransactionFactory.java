/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-27
 * <修改描述:>
 */
package com.tx.component.rule.transation.impl;

import com.tx.component.rule.transation.RuleSessionTransaction;
import com.tx.component.rule.transation.RuleSessionTransactionFactory;

/**
 * 规则会话工厂类<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-3-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultRuleSessionTransactionFactory implements
        RuleSessionTransactionFactory {
    
    /**
      * 创建规则回话事务
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return RuleSessionTransaction [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public RuleSessionTransaction openRuleSessionTransaction() {
        RuleSessionTransaction ruleSessionTransaction = new RuleSessionTransaction();
        ruleSessionTransaction.open();
        return ruleSessionTransaction;
    }
}
