/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-29
 * <修改描述:>
 */
package com.tx.component.rule.transation.impl;

import java.util.Map;

import com.tx.component.rule.context.RuleContext;
import com.tx.component.rule.transation.RuleSessionContext;
import com.tx.component.rule.transation.RuleSessionTransaction;
import com.tx.component.rule.transation.RuleSessionTransactionCallback;
import com.tx.component.rule.transation.RuleSessionTransactionOperations;
import com.tx.component.rule.transation.RuleSessionTransactionUtils;

/**
 * 规则会话事务句柄
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-3-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleSessionTransationTemplate implements
        RuleSessionTransactionOperations {
    
    
    private RuleContext ruleContext;
    
    /** <默认构造函数> */
    public RuleSessionTransationTemplate(RuleContext ruleContext) {
        super();
        this.ruleContext = ruleContext;
    }
    
    /**
     * @param action
     * @param global
     */
    @Override
    public void execute(RuleSessionTransactionCallback action,
            Map<? extends String,? extends Object> global) throws Exception {
        
        //开启规则会话事务
        RuleSessionTransaction rsTrans = RuleSessionTransactionUtils.openRuleSessionTransation(ruleContext.getRuleSessionTransactionFactory());
        
        try {
            //设置会话变量
            if (global != null) {
                RuleSessionContext.getContext().setGlobals(global);
            }
            
            //调用事务内执行方法
            if (action != null) {
                action.doInTransaction();
            }
        } finally {
            //关闭规则会话事务
            RuleSessionTransactionUtils.closeRuleSessionTransation(rsTrans,
                    ruleContext.getRuleSessionTransactionFactory());
        }
    }
    
    /**
     * @return 返回 ruleContext
     */
    public RuleContext getRuleContext() {
        return ruleContext;
    }
    
    /**
     * @param 对ruleContext进行赋值
     */
    public void setRuleContext(RuleContext ruleContext) {
        this.ruleContext = ruleContext;
    }
    
}
