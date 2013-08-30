/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-23
 * <修改描述:>
 */
package com.tx.component.rule.exceptions;

import com.tx.component.rule.model.Rule;
import com.tx.component.rule.support.RuleSession;
import com.tx.core.exceptions.SILException;

/**
 * 规则访问异常
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleAccessException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 3113970360855922652L;
    
    private String ruleName;
    
    private Rule rule;
    
    private RuleSession ruleSession;
    
    /**
     * <默认构造函数>
     */
    public RuleAccessException(String ruleName, Rule rule,
            RuleSession ruleSession, String message, Object[] parameters) {
        super(message, parameters);
        this.ruleName = ruleName;
        this.rule = rule;
        this.ruleSession = ruleSession;
    }
    
    public RuleAccessException(String ruleName, Rule rule,
            RuleSession ruleSession, String message, Throwable cause) {
        super(message, cause);
        this.ruleName = ruleName;
        this.rule = rule;
        this.ruleSession = ruleSession;
    }
    
    /** <默认构造函数> */
    public RuleAccessException(String message, Object[] parameters) {
        super(message, parameters);
    }
    
    /** <默认构造函数> */
    public RuleAccessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public RuleAccessException(String message) {
        super(message);
    }
    
    /**
     * @return 返回 ruleName
     */
    public String getRuleName() {
        return ruleName;
    }
    
    /**
     * @param 对ruleName进行赋值
     */
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }
    
    /**
     * @return 返回 rule
     */
    public Rule getRule() {
        return rule;
    }
    
    /**
     * @param 对rule进行赋值
     */
    public void setRule(Rule rule) {
        this.rule = rule;
    }
    
    /**
     * @return 返回 ruleSession
     */
    public RuleSession getRuleSession() {
        return ruleSession;
    }
    
    /**
     * @param 对ruleSession进行赋值
     */
    public void setRuleSession(RuleSession ruleSession) {
        this.ruleSession = ruleSession;
    }
}
