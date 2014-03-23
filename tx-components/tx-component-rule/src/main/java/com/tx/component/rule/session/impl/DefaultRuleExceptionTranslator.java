/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-1-27
 * <修改描述:>
 */
package com.tx.component.rule.session.impl;

import com.tx.component.rule.exceptions.RuleAccessException;
import com.tx.component.rule.session.RuleExceptionTranslator;
import com.tx.component.rule.session.RuleSession;

/**
 * 默认的规则异常转换器<br/>
 *     1、规则异常放翻译器，负责将规则会话内发生的异常，用RuleAccessException进行包装<br/>
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
    public RuleAccessException translate(RuleSession ruleSession,Throwable ex) {
        if (ex instanceof RuleAccessException) {
            return (RuleAccessException) ex;
        }
        return new RuleAccessException(ex.getMessage(), ex);
    }
    
}
