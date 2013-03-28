/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-28
 * <修改描述:>
 */
package com.tx.component.rule.support.impl;

import com.tx.component.rule.drools.DroolsRule;
import com.tx.component.rule.drools.DroolsRuleSession;
import com.tx.component.rule.exceptions.RuleAccessException;
import com.tx.component.rule.method.MethodRule;
import com.tx.component.rule.method.MethodRuleSession;
import com.tx.component.rule.model.Rule;
import com.tx.component.rule.support.RuleSession;
import com.tx.component.rule.support.RuleSessionFactory;


 /**
  * 默认的规则会话工厂<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-28]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class DefaultRuleSessionFactory implements RuleSessionFactory{
    
    
    /**
     * 创建规则会话<br/>
     * 
     * @return [参数说明]
     * 
     * @return RuleSession [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public RuleSession createRuleSession(Rule rule) {
        if(rule instanceof MethodRule){
            return newRuleSession((MethodRule)rule);
        }else if(rule instanceof DroolsRule){
            return newRuleSession((DroolsRule)rule);
        }
        throw new RuleAccessException(rule.rule(), rule, null,
                "创建规则会话异常：对应规则类型:{}暂不支持", rule.getRuleType().toString());
    }
    
    /**
      * 创建方法类型的规则会话<br/>
      * 
      * @return [参数说明]
      * 
      * @return RuleSession [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private RuleSession newRuleSession(MethodRule rule) {
        MethodRuleSession ruleSession = new MethodRuleSession(rule);
        return ruleSession;
    }
    
    /**
      * <功能简述>
      * <功能详细描述>
      * @param rule
      * @return [参数说明]
      * 
      * @return RuleSession [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private RuleSession newRuleSession(DroolsRule rule) {
        DroolsRuleSession ruleSession = new DroolsRuleSession(rule);
        return ruleSession;
    }
}
