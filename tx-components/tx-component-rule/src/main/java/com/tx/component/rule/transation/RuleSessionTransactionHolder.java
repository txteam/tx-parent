/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-22
 * <修改描述:>
 */
package com.tx.component.rule.transation;

import org.springframework.transaction.support.ResourceHolderSupport;

/**
 * 规则回话事务支持
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-3-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleSessionTransactionHolder extends ResourceHolderSupport {
    
    /**
     * 规则会话事务
     */
    private RuleSessionTransaction ruleSessionTransaction;
    
    /** <默认构造函数> */
    public RuleSessionTransactionHolder(
            RuleSessionTransaction ruleSessionTransaction) {
        super();
        this.ruleSessionTransaction = ruleSessionTransaction;
    }
    
    /**
     * 返回当前是否存在规则会话事务
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public boolean isReleased() {
        return this.ruleSessionTransaction == null;
    }
    
    /**
     * 是否已发布
     */
    @Override
    public void released() {
        super.released();
        if (!isOpen() && this.ruleSessionTransaction != null) {
            this.ruleSessionTransaction.close();
            this.ruleSessionTransaction = null;
        }
    }
    
    /**
     * 事务清空,结束
     */
    @Override
    public void clear() {
        super.clear();
        this.ruleSessionTransaction = null;
    }

    /**
     * @return 返回 ruleSessionTransaction
     */
    public RuleSessionTransaction getRuleSessionTransaction() {
        return ruleSessionTransaction;
    }

    /**
     * @param 对ruleSessionTransaction进行赋值
     */
    public void setRuleSessionTransaction(
            RuleSessionTransaction ruleSessionTransaction) {
        this.ruleSessionTransaction = ruleSessionTransaction;
    }
}
