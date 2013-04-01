/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-24
 * <修改描述:>
 */
package com.tx.component.rule.drools;

import org.drools.KnowledgeBase;

import com.tx.component.rule.model.Rule;
import com.tx.component.rule.model.RuleStateEnum;
import com.tx.component.rule.model.RuleTypeEnum;
import com.tx.component.rule.model.SimplePersistenceRule;

/**
 * drools规则<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DroolsRule implements Rule {
    
    /** 注释内容 */
    private static final long serialVersionUID = -6764646531144979549L;
    
    /** drools规则knowlegeBase */
    private KnowledgeBase knowledgeBase;
    
    /** 数据库中存储的规则实体 */
    private SimplePersistenceRule simplePersistenceRule;
        
    /**
     * 构造函数,根据二进制流构造drools规则
     */
    public DroolsRule(SimplePersistenceRule spRule, KnowledgeBase knowledgeBase) {
        super();
        this.simplePersistenceRule = spRule;
        
        this.knowledgeBase = knowledgeBase;
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
    public RuleTypeEnum getRuleType() {
        return RuleTypeEnum.DROOLS;
    }
    
    /**
     * @return 返回 knowledgeBase
     */
    public KnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }
    
    /**
     * @param 对knowledgeBase进行赋值
     */
    public void setKnowledgeBase(KnowledgeBase knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
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
    public void setSimplePersistenceRule(
            SimplePersistenceRule simplePersistenceRule) {
        this.simplePersistenceRule = simplePersistenceRule;
    }
    
    /**
     * @return
     */
    @Override
    public String rule() {
        return this.simplePersistenceRule.getRule();
    }
    
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
    public String getServiceType() {
        return this.simplePersistenceRule.getServiceType();
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
}
