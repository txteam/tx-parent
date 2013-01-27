/*
 * 描          述:  <描述>
 * 修  改   人:  pengqingyang
 * 修改时间:  2013-1-27
 * <修改描述:>
 */
package com.tx.component.rule.collection;

import java.util.ArrayList;
import java.util.List;

import com.tx.component.rule.RuleConstants;
import com.tx.component.rule.model.Rule;
import com.tx.core.TxConstants;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-24]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class CollectionRule implements Rule {

    private String rule;
    
    private String serviceType;
    
    private List<Rule> ruleList = new ArrayList<Rule>(TxConstants.INITIAL_CONLLECTION_SIZE);
    
    /**
     * @return
     */
    @Override
    public String rule() {
        return rule;
    }

    /**
     * @return
     */
    @Override
    public String getRuleType() {
        return RuleConstants.RULE_TYPE_COLLECTIONS;
    }

    /**
     * @return
     */
    @Override
    public String getServiceType() {
        return this.serviceType;
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
     * @return 返回 ruleList
     */
    public List<Rule> getRuleList() {
        return ruleList;
    }

    /**
     * @param 对ruleList进行赋值
     */
    public void setRuleList(List<Rule> ruleList) {
        this.ruleList = ruleList;
    }

    /**
     * @param 对serviceType进行赋值
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
