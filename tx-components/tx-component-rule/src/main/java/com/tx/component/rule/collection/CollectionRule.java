/*
 * 描          述:  <描述>
 * 修  改   人:  pengqingyang
 * 修改时间:  2013-1-27
 * <修改描述:>
 */
package com.tx.component.rule.collection;

import java.util.ArrayList;
import java.util.List;

import com.tx.component.rule.model.Rule;
import com.tx.component.rule.model.RuleState;
import com.tx.component.rule.model.RuleType;
import com.tx.core.TxConstants;

/**
 * 集合类规则<br/>
 *     一组规则的集合<br/>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CollectionRule implements Rule {
    
    /** 注释内容 */
    private static final long serialVersionUID = -107185037749395048L;

    private String rule;
    
    private String name;
    
    private String serviceType;
    
    private RuleState state;
    
    private List<Rule> ruleList = new ArrayList<Rule>(
            TxConstants.INITIAL_CONLLECTION_SIZE);
    
    /**
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

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
    public RuleType getRuleType() {
        return RuleType.COLLECTION;
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

    /**
     * @return 返回 state
     */
    public RuleState getState() {
        return state;
    }

    /**
     * @param 对state进行赋值
     */
    public void setState(RuleState state) {
        this.state = state;
    }

    /**
     * @param 对name进行赋值
     */
    public void setName(String name) {
        this.name = name;
    }
}
