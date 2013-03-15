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
    
    /** 规则名 */
    private String rule;
    
    /** 规则名 */
    private String name;
    
    /** 规则的业务类型属性 */
    private String serviceType;
    
    /** drools规则knowlegeBase */
    private KnowledgeBase knowledgeBase;
    
    private RuleStateEnum state;
    
    /**
     * 构造函数,根据二进制流构造drools规则
     */
    public DroolsRule(SimplePersistenceRule spRule, KnowledgeBase knowledgeBase) {
        super();
        this.rule = spRule.rule();
        this.name = spRule.getName();
        this.serviceType = spRule.getServiceType();
        this.state = spRule.getState();
        
        this.knowledgeBase = knowledgeBase;
    }
    
    /**
     * @return
     */
    @Override
    public String getName() {
        return this.name;
    }
    
    /**
     * @return
     */
    @Override
    public String rule() {
        return this.rule;
    }
    
    /**
     * @return
     */
    @Override
    public RuleTypeEnum getRuleType() {
        return RuleTypeEnum.DROOLS;
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
     * @param 对serviceType进行赋值
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
    
    /**
     * @return 返回 state
     */
    public RuleStateEnum getState() {
        return state;
    }
    
    /**
     * @param 对state进行赋值
     */
    public void setState(RuleStateEnum state) {
        this.state = state;
    }
}
