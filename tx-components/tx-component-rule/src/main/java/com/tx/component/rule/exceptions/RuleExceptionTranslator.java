/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-19
 * <修改描述:>
 */
package com.tx.component.rule.exceptions;

import com.tx.component.rule.model.Rule;
import com.tx.component.rule.support.RuleSession;

/**
 * 规则异常转换器
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RuleExceptionTranslator {
    
    public static final String RULE_ERROR_CODE = "RuleError_000_001";
    
    /**
      * 规则异常转换器接口
      * 1、将请求规则生命周期内发生的异常进行转换
      * @param ruleType
      * @param rule
      * @param ruleExpression
      * @param ex
      * @return [参数说明]
      * 
      * @return RuleAccessException [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    RuleAccessException translate(Rule rule, RuleSession ruleSession, Throwable ex);
}
