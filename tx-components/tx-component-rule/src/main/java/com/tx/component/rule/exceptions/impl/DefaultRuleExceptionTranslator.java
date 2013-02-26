/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-1-27
 * <修改描述:>
 */
package com.tx.component.rule.exceptions.impl;

import com.tx.component.rule.exceptions.RuleAccessException;
import com.tx.component.rule.exceptions.RuleExceptionTranslator;
import com.tx.component.rule.model.Rule;
import com.tx.component.rule.support.RuleSession;

/**
 * 默认的规则异常转换器<br/>
 *     1、
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-1-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultRuleExceptionTranslator implements RuleExceptionTranslator {
    
    /**
     * @param rule
     * @param ruleType
     * @param ex
     * @return
     */
    @Override
    public RuleAccessException translate(Rule rule, RuleSession ruleSession,
            Throwable ex) {
        if (ex instanceof RuleAccessException) {
            return (RuleAccessException) ex;
        }
        
        return new RuleAccessException(rule.rule(), rule, ruleSession,
                ex.getMessage());
    }
    
}
