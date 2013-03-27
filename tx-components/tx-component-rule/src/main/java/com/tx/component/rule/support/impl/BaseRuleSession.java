/*
 * 描          述:  <描述>
 * 修  改   人:  pengqingyang
 * 修改时间:  2013-1-27
 * <修改描述:>
 */
package com.tx.component.rule.support.impl;

import com.tx.component.rule.RuleConstants;
import com.tx.component.rule.model.Rule;
import com.tx.component.rule.model.RuleSessionResultHandle;
import com.tx.component.rule.support.RuleSession;
import com.tx.component.rule.transation.RuleSessionContext;


 /**
  * 默认的规则会话对象，用以代替ruleSesion的公共部分逻辑实现<br/>
  *     使得继承于该类的规则会话实体
  * 
  * @author  pengqingyang
  * @version  [版本号, 2013-1-27]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public abstract class BaseRuleSession<R extends Rule> implements RuleSession {
    
    /** 规则会话对应规则实体 */
    protected R rule;
    
    /**
     * 规则会话构造函数
     */
    public BaseRuleSession(R rule) {
        super();
        this.rule = rule;
    }
    
    /**
     * @return 返回 rule
     */
    public Rule rule() {
        return rule;
    }

    /**
     * @param resultHandle
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> void callback(RuleSessionResultHandle<T> resultHandle) {
        Object resultObject = RuleSessionContext.getContext().getGlobal(RuleConstants.RULE_PROMISE_CONSTANT_RESULT);
        resultHandle.setValue((T)resultObject);
    }
}
