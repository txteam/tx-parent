/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-28
 * <修改描述:>
 */
package com.tx.component.rule.model;

import java.io.Serializable;

import javax.persistence.Id;



 /**
  * 简单规则的属性值
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-2-28]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class SimpleRuleProperty implements Serializable{
    
    /** 注释内容 */
    private static final long serialVersionUID = -9023550864701139335L;

    /** 规则属性 */
    private SimpleRulePropertyParam simpleRulePropertyParam;
    
    /** 对应规则id */
    @Id
    private String ruleId;
    
    /** 对应属性key */
    private String paramKey;
    
    /** paramValueOrdered */
    private int paramValueOrdered;

    /**
     * @return 返回 simpleRulePropertyParam
     */
    public SimpleRulePropertyParam getSimpleRulePropertyParam() {
        return simpleRulePropertyParam;
    }

    /**
     * @param 对simpleRulePropertyParam进行赋值
     */
    public void setSimpleRulePropertyParam(
            SimpleRulePropertyParam simpleRulePropertyParam) {
        this.simpleRulePropertyParam = simpleRulePropertyParam;
    }

    /**
     * @return 返回 ruleId
     */
    public String getRuleId() {
        return ruleId;
    }

    /**
     * @param 对ruleId进行赋值
     */
    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    /**
     * @return 返回 paramKey
     */
    public String getParamKey() {
        return paramKey;
    }

    /**
     * @param 对paramKey进行赋值
     */
    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    /**
     * @return 返回 paramValueOrdered
     */
    public int getParamValueOrdered() {
        return paramValueOrdered;
    }

    /**
     * @param 对paramValueOrdered进行赋值
     */
    public void setParamValueOrdered(int paramValueOrdered) {
        this.paramValueOrdered = paramValueOrdered;
    }
}
