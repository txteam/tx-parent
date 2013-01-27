/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-1-27
 * <修改描述:>
 */
package com.tx.component.rule.collection;

import java.util.List;
import java.util.Map;

import com.tx.component.rule.model.Rule;
import com.tx.component.rule.support.RuleSession;
import com.tx.component.rule.support.RuleSessionFactory;
import com.tx.component.rule.support.impl.DefaultRuleSession;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2013-1-27]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class CollectionRuleSession extends DefaultRuleSession<CollectionRule> {

    private List<Rule> ruleList;
    
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
        if(ruleList == null){
            return ;
        }
        for(Rule rule : ruleList){
            RuleSession ruleSession = RuleSessionFactory.createRuleSession(rule);
            ruleSession.execute(fact);
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
}
