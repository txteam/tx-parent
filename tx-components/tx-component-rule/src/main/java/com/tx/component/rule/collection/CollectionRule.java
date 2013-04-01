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
import com.tx.component.rule.model.RuleStateEnum;
import com.tx.component.rule.model.RuleTypeEnum;
import com.tx.component.rule.model.SimplePersistenceRule;
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
    
    private List<Rule> ruleList = new ArrayList<Rule>(
            TxConstants.INITIAL_CONLLECTION_SIZE);
    
    private SimplePersistenceRule simplePersistenceRule;
    
    /**
     * @return
     */
    @Override
    public String getName() {
        return this.simplePersistenceRule.getName();
    }

    /**
     * @return
     */
    @Override
    public String rule() {
        return this.simplePersistenceRule.rule();
    }
    
    /**
     * @return
     */
    @Override
    public RuleTypeEnum getRuleType() {
        return RuleTypeEnum.COLLECTION;
    }
    
    /**
     * @return
     */
    @Override
    public String getServiceType() {
        return this.simplePersistenceRule.getServiceType();
    }
    
    /**
     * @return 返回 rule
     */
    public String getRule() {
        return this.simplePersistenceRule.getRule();
    }

    /**
     * @return
     */
    @Override
    public String getId() {
        return this.simplePersistenceRule.getId();
    }

    /**
     * @return
     */
    @Override
    public RuleStateEnum getState() {
        if(this.simplePersistenceRule == null){
            return RuleStateEnum.ERROR;
        }else{
            return this.simplePersistenceRule.getState();
        }
    }

    /**
     * @param state
     */
    @Override
    public void setState(RuleStateEnum state) {
        if (state != null && this.simplePersistenceRule != null
                && !state.equals(this.simplePersistenceRule.getState())) {
            this.simplePersistenceRule.setState(state);
        }
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
     * @return 返回 simplePersistenceRule
     */
    public SimplePersistenceRule getSimplePersistenceRule() {
        return simplePersistenceRule;
    }

    /**
     * @param 对simplePersistenceRule进行赋值
     */
    public void setSimplePersistenceRule(SimplePersistenceRule simplePersistenceRule) {
        this.simplePersistenceRule = simplePersistenceRule;
    }
}
