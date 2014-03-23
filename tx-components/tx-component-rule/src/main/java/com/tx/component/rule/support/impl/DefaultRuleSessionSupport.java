/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月20日
 * <修改描述:>
 */
package com.tx.component.rule.support.impl;

import java.util.List;
import java.util.Map;

import com.tx.component.rule.loader.RuleStateEnum;
import com.tx.component.rule.session.RuleExceptionTranslator;
import com.tx.component.rule.session.RuleSession;
import com.tx.component.rule.session.CallbackHandler;
import com.tx.component.rule.support.RuleSessionSupport;
import com.tx.component.rule.transation.RuleSessionTransactionCallback;
import com.tx.component.rule.transation.impl.RuleSessionTransationTemplate;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 默认的规则会话执行器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年3月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultRuleSessionSupport implements RuleSessionSupport {
    
    private RuleSessionTransationTemplate ruleSessionTransationTemplate;
    
    /** 规则运行异常翻译器 */
    private RuleExceptionTranslator ruleExceptionTranslator;
    
    /** <默认构造函数> */
    public DefaultRuleSessionSupport(
            RuleSessionTransationTemplate ruleSessionTransationTemplate,
            RuleExceptionTranslator ruleExceptionTranslator) {
        super();
        AssertUtils.notNull(ruleSessionTransationTemplate,
                "ruleSessionTransationTemplate is null.");
        AssertUtils.notNull(ruleExceptionTranslator,
                "ruleExceptionTranslator is null.");
        this.ruleSessionTransationTemplate = ruleSessionTransationTemplate;
        this.ruleExceptionTranslator = ruleExceptionTranslator;
    }
    
    /**
     * @param ruleSession
     * @param fact
     * @param global
     */
    @Override
    public <R> void evaluate(final RuleSession ruleSession,
            final Map<String, Object> fact, Map<String, Object> global,
            final CallbackHandler<R> callbackHandler) {
        //参数验证
        AssertUtils.notNull(ruleSession, "ruleSession is null.");
        AssertUtils.isTrue(RuleStateEnum.OPERATION.equals(ruleSession.getRule()
                .getState()),
                "ruleSession.rule.state must be operation.ruleKey:{} state:{}",
                new Object[] { ruleSession.getRule().getKey(),
                        ruleSession.getRule().getState() });
        try {
            ruleSessionTransationTemplate.execute(new RuleSessionTransactionCallback() {
                @Override
                public void doInTransaction() throws Exception {
                    ruleSession.execute(fact, callbackHandler);
                }
            },
                    global);
        } catch (Exception e) {
            throw this.ruleExceptionTranslator.translate(ruleSession, e);
        }
    }
    
    /**
     * @param ruleSession
     * @param facts
     * @param global
     */
    @Override
    public <R> void evaluateAll(final RuleSession ruleSession,
            final List<Map<String, Object>> facts, Map<String, Object> global,
            final CallbackHandler<R> callbackHandler) {
        //参数验证
        AssertUtils.notNull(ruleSession, "ruleSession is null.");
        AssertUtils.isTrue(RuleStateEnum.OPERATION.equals(ruleSession.getRule()
                .getState()),
                "ruleSession.rule.state must be operation.ruleKey:{} state:{}",
                new Object[] { ruleSession.getRule().getKey(),
                        ruleSession.getRule().getState() });
        
        try {
            ruleSessionTransationTemplate.execute(new RuleSessionTransactionCallback() {
                @Override
                public void doInTransaction() throws Exception {
                    ruleSession.executeAll(facts, callbackHandler);
                }
            },
                    global);
        } catch (Exception e) {
            throw this.ruleExceptionTranslator.translate(ruleSession, e);
        }
    }
}
