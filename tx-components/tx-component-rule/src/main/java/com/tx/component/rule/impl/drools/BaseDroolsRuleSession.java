/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-28
 * <修改描述:>
 */
package com.tx.component.rule.impl.drools;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.drools.base.MapGlobalResolver;
import org.drools.runtime.Globals;
import org.drools.runtime.StatefulKnowledgeSession;

import com.tx.component.rule.RuleConstants;
import com.tx.component.rule.session.CallbackHandler;
import com.tx.component.rule.session.impl.BaseRuleSession;
import com.tx.component.rule.transation.RuleSessionContext;

/**
 * drools规则会话实例<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-28]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BaseDroolsRuleSession<RULE extends BaseDroolsRule> extends
        BaseRuleSession<RULE> {
    
    /**
     * drools规则<默认构造函数>
     */
    public BaseDroolsRuleSession(RULE rule) {
        super(rule);
    }
    
    /**
     * @param fact
     */
    @Override
    public <R> void execute(Map<String, Object> fact,
            CallbackHandler<R> callbackHandler) {
        StatefulKnowledgeSession session = this.rule.getKnowledgeBase()
                .newStatefulKnowledgeSession();
        try {
            beforeFireRule(callbackHandler, session);
            
            //插入事实
            if(!CollectionUtils.isEmpty(fact.values())){
                for(Object factTemp : fact.values()){
                    session.insert(factTemp);
                }
            }
            //触发规则调用
            session.fireAllRules();
            
            afterFireRule(session);
        } finally {
            if (session != null) {
                session.dispose();
            }
        }
    }
    
    /**
     * @param facts
     */
    @Override
    public <R> void executeAll(List<Map<String, Object>> facts,
            CallbackHandler<R> callbackHandler) {
        if (facts == null) {
            return;
        }
        for (Map<String, Object> fact : facts) {
            execute(fact, callbackHandler);
        }
    }
    
    /** 
     * 在规则执行后执行方法<br/>
     *<功能详细描述>
     * @param session [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void afterFireRule(StatefulKnowledgeSession session) {
        Globals globals = session.getGlobals();
        if (globals instanceof MapGlobalResolver) {
            MapGlobalResolver globalInstance = (MapGlobalResolver) globals;
            @SuppressWarnings("rawtypes")
            Entry[] entrys = globalInstance.getGlobals();
            for (@SuppressWarnings("rawtypes")
            Entry entryTemp : entrys) {
                if (RuleConstants.RULE_EVALUATE_RESULT_VALUE_WRAPPER.equals((String) entryTemp.getKey())) {
                    continue;
                }
                RuleSessionContext.getContext()
                        .setGlobal((String) entryTemp.getKey(),
                                entryTemp.getValue());
            }
        }
    }
    
    /** 
     * 在规则执行前执行方法<br/>
     *<功能详细描述>
     * @param callbackHandler
     * @param session [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private <R> void beforeFireRule(CallbackHandler<R> callbackHandler,
            StatefulKnowledgeSession session) {
        //获取全局对象
        Map<String, Object> globas = RuleSessionContext.getContext()
                .getGlobals();
        //设置全局对象
        for (Entry<String, Object> entryTemp : globas.entrySet()) {
            try {
                //drools在写入global期间，如果未在drl中定义对应的global这里设置会发生异常
                //该异常直接
                session.setGlobal(entryTemp.getKey(), entryTemp.getValue());
            } catch (RuntimeException e) {
                logger.warn("drools session set global error: global key:{} error:{}",
                        new Object[] { entryTemp.getKey(), e.getMessage() });
            }
        }
        if (callbackHandler != null) {
            try {
                session.setGlobal(RuleConstants.RULE_EVALUATE_RESULT_VALUE_WRAPPER,
                        callbackHandler);
            } catch (RuntimeException e) {
                logger.warn("drools session set global error: global key:{} error:{}",
                        new Object[] {
                                RuleConstants.RULE_EVALUATE_RESULT_VALUE_WRAPPER,
                                callbackHandler });
            }
        }
    }
    
}
