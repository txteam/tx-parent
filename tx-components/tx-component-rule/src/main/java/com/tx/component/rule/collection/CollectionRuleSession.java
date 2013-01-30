/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-1-27
 * <修改描述:>
 */
package com.tx.component.rule.collection;

import java.util.List;
import java.util.Map;

import com.tx.component.rule.context.RuleContext;
import com.tx.component.rule.model.Rule;
import com.tx.component.rule.support.RuleSession;
import com.tx.component.rule.support.impl.DefaultRuleSession;

/**
 * 集合类规则会话<br/>
 *     适用于多个规则一起作用的场景<br/>
 *     在drools使用情况下，实际一个drools规则自身就可以是一个规则链，并且支持优先级，触发次数等高级属性<br/>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-1-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CollectionRuleSession extends DefaultRuleSession<CollectionRule> {
    
    /**
     * <默认构造函数>
     */
    public CollectionRuleSession(CollectionRule rule) {
        super(rule);
    }
    
    /**
     * @param fact
     */
    @Override
    public void execute(Map<String, Object> fact) {
        if (rule.getRuleList() == null) {
            return;
        }
        for (Rule ruleTemp : rule.getRuleList()) {
            RuleSession ruleSession = RuleContext.getRuleContext()
                    .newRuleSession(ruleTemp);
            ruleSession.execute(fact);
        }
    }
    
    /**
     * @param facts
     */
    @Override
    public void execute(List<Map<String, Object>> facts) {
        if (rule.getRuleList() == null) {
            return;
        }
        for (Rule ruleTemp : rule.getRuleList()) {
            RuleSession ruleSession = RuleContext.getRuleContext()
                    .newRuleSession(ruleTemp);
            ruleSession.execute(facts);
        }
    }
}
