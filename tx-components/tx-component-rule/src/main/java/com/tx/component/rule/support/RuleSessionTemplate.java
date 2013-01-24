/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-23
 * <修改描述:>
 */
package com.tx.component.rule.support;

import static org.apache.ibatis.reflection.ExceptionUtil.unwrapThrowable;
import static org.mybatis.spring.SqlSessionUtils.closeSqlSession;
import static org.mybatis.spring.SqlSessionUtils.getSqlSession;
import static org.mybatis.spring.SqlSessionUtils.isSqlSessionTransactional;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.InitializingBean;

import com.tx.component.rule.exceptions.RuleExceptionTranslator;

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
    }
    
    /**
     * @param rule
     * @param fact
     * @return
     */
    @Override
    public boolean evaluateBoolean(String rule, Map<String, ?> fact) {
        return this.supportProxy.evaluateBoolean(rule, fact);
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
     * @return
     */
    @Override
    public <T> List<T> evaluateList(String rule, Map<String, ?> fact) {
        return this.supportProxy.evaluateList(rule, fact);
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
     * @return
     */
    @Override
    public <T> Map<String, T> evaluateMap(String rule, Map<String, ?> fact) {
        return this.supportProxy.evaluateMap(rule, fact);
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
     * @return
     */
    @Override
    public <T> T evaluateObject(String rule, Map<String, ?> fact) {
        return this.supportProxy.evaluateObject(rule, fact);
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
     */
    @Override
    public void evalute(String rule, Map<String, Object> fact) {
        this.supportProxy.evalute(rule, fact);
    }
    
    /**
     * @param rule
     * @param fact
     * @param global
     */
    @Override
    public void evalute(String rule, Map<String, ?> fact, Map<String, ?> global) {
        this.supportProxy.evalute(rule, fact, global);
    }
    
    private class RuleSessionSupportInvocationHandler implements
            InvocationHandler {
        
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
            final SqlSession sqlSession = null;
            try {
                Object result = method.invoke(sqlSession, args);
                return result;
            } catch (Throwable t) {
                Throwable unwrapped = t;
                if (ruleExceptionTranslator != null) {
                    Throwable translated = ruleExceptionTranslator.translate(rule,ruleType,ruleExpression, ex);
                    if (translated != null) {
                        unwrapped = translated;
                    }
                }
                throw unwrapped;
            } finally {
                //close rule session 
                //如果规则尚未执行完成
            }
        }
    }
    
}
