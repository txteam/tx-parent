/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-1-27
 * <修改描述:>
 */
package com.tx.component.rule.exceptions;

import com.tx.component.rule.model.Rule;
import com.tx.component.rule.support.RuleSession;


 /**
  * 规则执行时异常
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2013-1-27]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class RuleExcuteException extends RuleAccessException{

    /** 注释内容 */
    private static final long serialVersionUID = -5558841123798884888L;

    public RuleExcuteException(String ruleName, Rule rule,
            RuleSession ruleSession, String message, String... parameters) {
        super(ruleName, rule, ruleSession, message, parameters);
    }

    public RuleExcuteException(String ruleName, Rule rule,
            RuleSession ruleSession, String message, Throwable cause,
            String... parameters) {
        super(ruleName, rule, ruleSession, message, cause, parameters);
    }
}
