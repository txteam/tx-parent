/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月17日
 * <修改描述:>
 */
package com.tx.component.rule.impl.drools;

import org.drools.KnowledgeBase;

import com.tx.component.rule.context.BaseRule;
import com.tx.component.rule.loader.RuleItem;
import com.tx.component.rule.loader.RuleStateEnum;

/**
 * drools规则的基础实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class BaseDroolsRule extends BaseRule {
    
    /** 注释内容 */
    private static final long serialVersionUID = 2518109664489815501L;
    
    /** drools规则knowlegeBase */
    protected KnowledgeBase knowledgeBase;
    
    /** 规则状态枚举 */
    protected RuleStateEnum state;
    
    public BaseDroolsRule(RuleItem ruleItem) {
        super(ruleItem);
    }
    
    /**
     * @return 返回 knowledgeBase
     */
    public KnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }
    
    /**
     * @return
     */
    @Override
    public RuleStateEnum getState() {
        return this.state;
    }
}
