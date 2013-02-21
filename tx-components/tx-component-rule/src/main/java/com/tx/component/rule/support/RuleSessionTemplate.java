/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-23
 * <修改描述:>
 */
package com.tx.component.rule.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.InitializingBean;

import com.tx.component.rule.exceptions.RuleExceptionTranslator;
import com.tx.component.rule.exceptions.impl.DefaultRuleExceptionTranslator;
import com.tx.component.rule.model.Rule;

/**
 * 规则运行执行器<br>
 *     类似hibernateDaoTemplate的封装设计<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleSessionTemplate implements RuleSessionSupport,
        InitializingBean {
    
    private RuleSessionSupport supportProxy;
    
    private RuleExceptionTranslator ruleExceptionTranslator;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.supportProxy = (RuleSessionSupport) Proxy.newProxyInstance(this.getClass()
                .getClassLoader(),
                new Class<?>[] { RuleSessionSupport.class },
                new RuleSessionSupportInvocationHandler());
        if(this.ruleExceptionTranslator == null){
            this.ruleExceptionTranslator = new DefaultRuleExceptionTranslator();
        }
    }
   
    /**
     * @param rule
     * @param facts
     * @param global
     * @return
     */
    @Override
    public boolean evaluateBoolean(String rule, List<Map<String, ?>> facts,
            Map<String, ?> global) {
        return supportProxy.evaluateBoolean(rule, facts, global);
    }
    
    /**
     * @param rule
     * @param facts
     * @param global
     * @return
     */
    @Override
    public <T> List<T> evaluateList(String rule, List<Map<String, ?>> facts,
            Map<String, ?> global) {
        return supportProxy.evaluateList(rule, facts, global);
    }
    
    /**
     * @param rule
     * @param facts
     * @param global
     * @return
     */
    @Override
    public <T> Map<String, T> evaluateMap(String rule,
            List<Map<String, ?>> facts, Map<String, ?> global) {
        return supportProxy.evaluateMap(rule, facts, global);
    }
    
    /**
     * @param rule
     * @param facts
     * @param global
     * @return
     */
    @Override
    public <T> T evaluateObject(String rule, List<Map<String, ?>> facts,
            Map<String, ?> global) {
        return supportProxy.evaluateObject(rule, facts, global);
    }

    /**
     * @param rule
     * @param facts
     * @param global
     */
    @Override
    public void evaluate(String rule, List<Map<String, ?>> facts,
            Map<String, ?> global) {
        supportProxy.evaluate(rule, facts, global);
    }

    /**
     * @param ruleSession
     * @param facts
     * @param global
     */
    @Override
    public void evaluate(RuleSession ruleSession, List<Map<String, ?>> facts,
            Map<String, ?> global) {
        supportProxy.evaluate(ruleSession, facts, global);
    }

    /**
     * @param rule
     * @param fact
     * @param global
     * @return
     */
    @Override
    public boolean evaluateBoolean(String rule, Map<String, ?> fact,
            Map<String, ?> global) {
        return this.supportProxy.evaluateBoolean(rule, fact, global);
    }

    
    /**
     * @param rule
     * @param fact
     * @param global
     * @return
     */
    @Override
    public <T> List<T> evaluateList(String rule, Map<String, ?> fact,
            Map<String, ?> global) {
        return this.supportProxy.evaluateList(rule, fact, global);
    }
    

    
    /**
     * @param rule
     * @param fact
     * @param global
     * @return
     */
    @Override
    public <T> Map<String, T> evaluateMap(String rule, Map<String, ?> fact,
            Map<String, ?> global) {
        return this.supportProxy.evaluateMap(rule, fact, global);
    }
    

    
    /**
     * @param rule
     * @param fact
     * @param global
     * @return
     */
    @Override
    public <T> T evaluateObject(String rule, Map<String, ?> fact,
            Map<String, ?> global) {
        return this.supportProxy.evaluateObject(rule, fact, global);
    }
    
    /**
     * @param rule
     * @param fact
     * @param global
     */
    @Override
    public void evaluate(String rule, Map<String, ?> fact, Map<String, ?> global) {
        this.supportProxy.evaluate(rule, fact, global);
    }
    
    /**
     * @param ruleSession
     * @param fact
     * @param global
     */
    @Override
    public void evaluate(RuleSession ruleSession, Map<String, ?> fact,
            Map<String, ?> global) {
        supportProxy.evaluate(ruleSession, fact, global);
    }
    
    /**
      * 规则会话实际代理执行句柄，将执行的方法转换为实际RuleSession的调用<br/>
      * <功能详细描述>
      * 
      * @author  brady
      * @version  [版本号, 2013-1-28]
      * @see  [相关类/方法]
      * @since  [产品/模块版本]
     */
    private class RuleSessionSupportInvocationHandler implements
            InvocationHandler {
        
        private Rule rule;
        
        private RuleSession ruleSession;
        
        
        /**
         * @param proxy
         * @param method
         * @param args
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            
            //开始一次会话
            RuleSessionContext.open();
            try {
                Object result = null;//method.invoke(sqlSession, args);
                return result;
            } catch (Throwable t) {
                Throwable unwrapped = t;
                if (ruleExceptionTranslator != null) {
                    Throwable translated = ruleExceptionTranslator.translate(rule, ruleSession, t);
                    if(translated != null){
                        unwrapped = translated;
                    }
                }
                throw unwrapped;
            } finally {
                RuleSessionContext.close();
            }
        }
    }
    
}
