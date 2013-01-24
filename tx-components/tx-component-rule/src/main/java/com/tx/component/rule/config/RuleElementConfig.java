/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-19
 * <修改描述:>
 */
package com.tx.component.rule.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;


 /**
  * 规则项配置
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-19]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@XStreamAlias("rule_element")
@XStreamConverter(RuleItemConfigConverter.class)
public class RuleElementConfig {
    
    /** 
     * 规则类型 可以为drools规则，可以为其他规则的实现<br/>
     * 可以为ognl<br/>
     * 可以为
     */
    @XStreamAlias("rule_type")
    private String ruleType="";
    
    /**
     * 规则描述，
     * 可以为ongl表达式
     * 可以为groovy代码<需要再进行添加，暂不支持>
     * 可以为drools规则名
     */
    private String ruleExpression = "";

    /**
     * @return 返回 ruleType
     */
    public String getRuleType() {
        return ruleType;
    }

    /**
     * @param 对ruleType进行赋值
     */
    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    /**
     * @return 返回 ruleExpression
     */
    public String getRuleExpression() {
        return ruleExpression;
    }

    /**
     * @param 对ruleExpression进行赋值
     */
    public void setRuleExpression(String ruleExpression) {
        this.ruleExpression = ruleExpression;
    }
}
