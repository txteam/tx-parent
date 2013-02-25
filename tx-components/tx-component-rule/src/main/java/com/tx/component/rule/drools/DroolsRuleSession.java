/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-28
 * <修改描述:>
 */
package com.tx.component.rule.drools;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.drools.runtime.StatefulKnowledgeSession;

import com.tx.component.rule.support.RuleSessionContext;
import com.tx.component.rule.support.impl.DefaultRuleSession;

/**
 * drools规则会话实例<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-28]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DroolsRuleSession extends DefaultRuleSession<DroolsRule> {
    
    public DroolsRuleSession(DroolsRule rule) {
        super(rule);
    }
    
    /**
     * @param fact
     */
    @Override
    public void execute(Map<String, Object> fact) {
        StatefulKnowledgeSession session = this.rule.getKnowledgeBase()
                .newStatefulKnowledgeSession();
        
        try {
            Map<String, Object> globas = RuleSessionContext.getGlobals();
            for (Entry<String, Object> entryTemp : globas.entrySet()) {
                session.setGlobal(entryTemp.getKey(), entryTemp.getValue());
            }
        }
        catch (Exception e) {
            
        }
        finally {
            if (session != null) {
                session.dispose();
            }
        }
    }
    
    /**
     * @param facts
     */
    @Override
    public void execute(List<Map<String, Object>> facts) {
        // TODO Auto-generated method stub
        
    }
    
}
