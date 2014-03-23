/*
 * 描          述:  <描述>
 * 修  改   人:  pengqingyang
 * 修改时间:  2013-1-27
 * <修改描述:>
 */
package com.tx.component.rule.session.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.component.rule.context.Rule;
import com.tx.component.rule.session.RuleSession;

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
    
    protected static Logger logger = LoggerFactory.getLogger(RuleSession.class);
    
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
    public Rule getRule() {
        return rule;
    }
}
