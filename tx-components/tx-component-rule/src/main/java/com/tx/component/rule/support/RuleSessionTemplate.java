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

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.tx.component.rule.context.RuleContext;
import com.tx.component.rule.exceptions.RuleAccessException;
import com.tx.component.rule.exceptions.RuleExceptionTranslator;
import com.tx.component.rule.exceptions.impl.DefaultRuleExceptionTranslator;
import com.tx.component.rule.model.RuleSessionResultHandle;
import com.tx.component.rule.model.impl.SimpleRuleSessionResultHandle;
import com.tx.core.exceptions.parameter.ParameterIsInvalidException;

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
@Component("ruleSessionTemplate")
public class RuleSessionTemplate implements RuleSessionSupport,
        InitializingBean {
    
    @Resource(name = "ruleContext")
    private RuleContext ruleContext;
    
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
        if (this.ruleExceptionTranslator == null) {
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
    public <T> List<T> evaluateList(String rule, List<Map<String, ?>> facts,
            Map<String, ?> global) {
        return supportProxy.<T> evaluateList(rule, facts, global);
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
        return supportProxy.<T> evaluateMap(rule, facts, global);
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
        return supportProxy.<T> evaluateObject(rule, facts, global);
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
    public <T> List<T> evaluateList(String rule, Map<String, ?> fact,
            Map<String, ?> global) {
        return this.supportProxy.<T> evaluateList(rule, fact, global);
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
        return this.supportProxy.<T> evaluateMap(rule, fact, global);
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
        return this.supportProxy.<T> evaluateObject(rule, fact, global);
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
        
        /**
         * @param proxy
         * @param method
         * @param args
         * @return
         * @throws Throwable
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            if (args.length != 3
                    && !(args[1] instanceof List || args[1] instanceof Map)
                    && !(args[2] instanceof Map)) {
                throw new ParameterIsInvalidException(
                        "RuleSessionSupportInvocationHandler.invoke args invalid");
            }
            
            //开始一次会话
            RuleSessionContext.open();
            if(args[2] != null){
                RuleSessionContext.setGlobals((Map)args[2]);
            }
            
            RuleSession ruleSession = getTargetRuleSession(method, args);
            try {
                if (args[1] instanceof List) {
                    ruleSession.execute((List) args[1]);
                }
                else if (args[1] instanceof Map) {
                    ruleSession.execute((Map) args[1]);
                }
                
                RuleSessionResultHandle<Object> resultHandle = new SimpleRuleSessionResultHandle<Object>();
                ruleSession.callback(resultHandle);
                Object result = resultHandle.getValue();
                return result;
            }
            catch (Throwable t) {
                Throwable unwrapped = t;
                if (ruleExceptionTranslator != null) {
                    Throwable translated = ruleExceptionTranslator.translate(ruleSession.rule(),
                            ruleSession,
                            t);
                    if (translated != null) {
                        unwrapped = translated;
                    }
                }
                throw unwrapped;
            }
            finally {
                RuleSessionContext.close();
            }
        }
        
        /**
         * <功能简述>
         * <功能详细描述>
         * @param method
         * @param args
         * @param ruleSession
         * @return
         * @throws RuleAccessException [参数说明]
         * 
         * @return RuleSession [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        private RuleSession getTargetRuleSession(Method method, Object[] args)
                throws RuleAccessException {
            Object arg0 = args[0];
            //ruleSessionTemp中第一个参数必然不能为空，如果为空抛出错误
            //如果不为空，如果判断为String则认为调用的是指定规则的规则会话
            //
            if (arg0 == null) {
                throw new RuleAccessException(
                        null,
                        null,
                        null,
                        "call ruleSession method:{} parameter rule or ruleSession is null.",
                        method.getName());
            }
            else if (arg0 instanceof RuleSession) {
                return (RuleSession) arg0;
            }
            else if (arg0 instanceof String) {
                String ruleKey = (String) arg0;
                if (!ruleContext.contains(ruleKey)) {
                    throw new RuleAccessException(
                            ruleKey,
                            null,
                            null,
                            "call ruleSession method:{} parameter rule:{} is not Exist",
                            method.getName(), ruleKey);
                }
                return ruleContext.newRuleSession(ruleContext.getRule(ruleKey));
            }
            else {
                throw new RuleAccessException(null, null, null,
                        "call ruleSession method:{} parameter is invalid.",
                        method.getName());
            }
        }
    }
    
}
