/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-19
 * <修改描述:>
 */
package com.tx.component.rule.config;

import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.tx.component.rule.model.RuleTypeEnum;


 /**
  * 规则项配置
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-19]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@XStreamAlias("rule")
@XStreamConverter(RuleItemConfigConverter.class)
public class RuleItemConfig {
    
    /**
     * 规则名，不能为空
     */
    @XStreamAsAttribute
    @XStreamAlias("rule")
    private String rule;
    
    /**
     * 规则业务类型
     */
    @XStreamAsAttribute
    @XStreamAlias("serviceType")
    private String serviceType="";
    
    /** 
     * 规则类型 可以为drools规则，可以为其他规则的实现<br/>
     * 可以为ognl<br/>
     * 可以为
     */
    @XStreamAsAttribute
    @XStreamAlias("ruleType")
    private RuleTypeEnum ruleType;
    
    /**
     * 规则描述，
     * 可以为ongl表达式
     * 可以为groovy代码<需要再进行添加，暂不支持>
     * 可以为drools规则名
     */
    private String ruleExpression = "";
    
    /**
     * 属性
     */
    private Map<String, String> properties = new HashMap<String, String>();

    /**
     * @return 返回 ruleType
     */
    public RuleTypeEnum getRuleType() {
        return ruleType;
    }

    /**
     * @param 对ruleType进行赋值
     */
    public void setRuleType(RuleTypeEnum ruleType) {
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

    /**
     * @return 返回 serviceType
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * @param 对serviceType进行赋值
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * @return 返回 rule
     */
    public String getRule() {
        return rule;
    }

    /**
     * @param 对rule进行赋值
     */
    public void setRule(String rule) {
        this.rule = rule;
    }

    /**
     * @return 返回 properties
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * @param 对properties进行赋值
     */
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
