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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.InitializingBean;

import com.tx.component.rule.context.Rule;
import com.tx.component.rule.context.RuleContext;
import com.tx.component.rule.exceptions.impl.RuleNotExistException;
import com.tx.component.rule.exceptions.impl.RuleStateNotOperationException;
import com.tx.component.rule.loader.RuleStateEnum;
import com.tx.component.rule.session.CallbackHandler;
import com.tx.component.rule.session.RuleExceptionTranslator;
import com.tx.component.rule.session.RuleSession;
import com.tx.component.rule.session.impl.DefaultRuleExceptionTranslator;
import com.tx.component.rule.session.impl.SimpleCallbackHandler;
import com.tx.component.rule.support.impl.DefaultRuleSessionSupport;
import com.tx.component.rule.transation.impl.RuleSessionTransationTemplate;
import com.tx.core.TxConstants;
import com.tx.core.exceptions.util.AssertUtils;

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
public class RuleSessionTemplate implements InitializingBean {
    
    private Logger logger = LoggerFactory.getLogger(RuleSessionTemplate.class);
    
    /** 规则容器实例 */
    private RuleContext ruleContext;
    
    /** 规则运行异常翻译器 */
    private RuleExceptionTranslator ruleExceptionTranslator;
    
    private RuleSessionTransationTemplate ruleSessionTransationTemplate;
    
    /** 实际的ruleSessionSupport动态代理实现 */
    private RuleSessionSupport ruleSessionSupportProxy;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notNull(ruleContext, "ruleContext is null.");
        
        if (ruleSessionTransationTemplate == null) {
            this.ruleSessionTransationTemplate = new RuleSessionTransationTemplate(
                    this.ruleContext);
        }
        if (this.ruleExceptionTranslator == null) {
            this.ruleExceptionTranslator = new DefaultRuleExceptionTranslator();
        }
        
        RuleSessionSupportInvocationHandler handler = null;
        if (ruleSessionSupportProxy == null) {
            handler = new RuleSessionSupportInvocationHandler(
                    new DefaultRuleSessionSupport(
                            this.ruleSessionTransationTemplate,
                            this.ruleExceptionTranslator));
        } else {
            handler = new RuleSessionSupportInvocationHandler(
                    ruleSessionSupportProxy);
        }
        //动态代理：负责对异常等集中进行处理<br/>
        this.ruleSessionSupportProxy = (RuleSessionSupport) Proxy.newProxyInstance(this.getClass()
                .getClassLoader(),
                new Class<?>[] { RuleSessionSupport.class },
                handler);
    }
    
    /**
      * 根据指定的规则key生成对应的规则会话实例<br/>
      *<功能详细描述>
      * @param ruleKey
      * @return [参数说明]
      * 
      * @return RuleSession [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private RuleSession buildRuleSessionByRuleKey(String ruleKey) {
        Rule rule = this.ruleContext.getRuleByRuleKey(ruleKey);
        if (rule == null) {
            throw new RuleNotExistException("rule not exist.ruleKey:{}",
                    new Object[] { ruleKey });
        }
        if (!RuleStateEnum.OPERATION.equals(rule.getState())) {
            throw new RuleStateNotOperationException(
                    "rule state is not operation.ruleKey:{}",
                    new Object[] { ruleKey });
        }
        RuleSession ruleSession = this.ruleContext.buildRuleSession(rule);
        return ruleSession;
    }
    
    /**
      * 根据传入的事实以及规则运行时环境，解析规则运行结果,结果类型为List<T><br/> 
      *<功能简述>
      *<功能详细描述>
      * @param ruleKey
      * @param fact
      * @param global
      * @return [参数说明]
      * 
      * @return List<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> evaluateList(String ruleKey, Map<String, ?> fact,
            Map<String, ?> global) {
        AssertUtils.notEmpty(ruleKey, "ruleKey is empty.");
        RuleSession ruleSession = buildRuleSessionByRuleKey(ruleKey);
        
        CallbackHandler<List<T>> listCallBackHandler = (CallbackHandler<List<T>>) SimpleCallbackHandler.newInstance(List.class,
                new ArrayList<T>(TxConstants.INITIAL_CONLLECTION_SIZE));
        this.ruleSessionSupportProxy.<List<T>> evaluate(ruleSession,
                (Map<String, Object>)fact,
                (Map<String, Object>)global,
                listCallBackHandler);
        List<T> resList = listCallBackHandler.getValue();
        return resList;
    }
    
//    /**
//      * 根据传入的事实以及规则运行时环境，解析规则运行结果,结果类型为List<T><br/> 
//      *<功能详细描述>
//      * @param ruleKey
//      * @param facts
//      * @param global
//      * @return [参数说明]
//      * 
//      * @return List<T> [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public <T> List<T> evaluateList(String ruleKey,
//            List<Map<String, Object>> facts, Map<String, Object> global) {
//        AssertUtils.notEmpty(ruleKey, "ruleKey is empty.");
//        RuleSession ruleSession = buildRuleSessionByRuleKey(ruleKey);
//        @SuppressWarnings("unchecked")
//        CallbackHandler<List<T>> listCallBackHandler = (CallbackHandler<List<T>>) SimpleCallbackHandler.newInstance(List.class,
//                new ArrayList<T>(TxConstants.INITIAL_CONLLECTION_SIZE));
//        this.ruleSessionSupportProxy.<List<T>> evaluateAll(ruleSession,
//                facts,
//                global,
//                listCallBackHandler);
//        List<T> resList = listCallBackHandler.getValue();
//        return resList;
//    }
    
    /**
     * @param rule
     * @param fact
     * @param global
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> Map<String, T> evaluateMap(String ruleKey,
            Map<String, ?> fact, Map<String, ?> global) {
        AssertUtils.notEmpty(ruleKey, "ruleKey is empty.");
        RuleSession ruleSession = buildRuleSessionByRuleKey(ruleKey);
        
        CallbackHandler<Map<String, T>> mapCallBackHandler = (CallbackHandler<Map<String, T>>) SimpleCallbackHandler.newInstance(Map.class,
                new HashMap<String, T>(TxConstants.INITIAL_MAP_SIZE));
        this.ruleSessionSupportProxy.<Map<String, T>> evaluate(ruleSession,
                (Map<String, Object>)fact,
                (Map<String, Object>)global,
                mapCallBackHandler);
        Map<String, T> resMap = mapCallBackHandler.getValue();
        return resMap;
    }
    
//    /**
//     * @param rule
//     * @param facts
//     * @param global
//     * @return
//     */
//    public <T> Map<String, T> evaluateMap(String ruleKey,
//            List<Map<String, Object>> facts, Map<String, Object> global) {
//        AssertUtils.notEmpty(ruleKey, "ruleKey is empty.");
//        RuleSession ruleSession = buildRuleSessionByRuleKey(ruleKey);
//        @SuppressWarnings("unchecked")
//        CallbackHandler<Map<String, T>> mapCallBackHandler = (CallbackHandler<Map<String, T>>) SimpleCallbackHandler.newInstance(Map.class,
//                new HashMap<String, T>(TxConstants.INITIAL_MAP_SIZE));
//        this.ruleSessionSupportProxy.<Map<String, T>> evaluateAll(ruleSession,
//                facts,
//                global,
//                mapCallBackHandler);
//        Map<String, T> resMap = mapCallBackHandler.getValue();
//        return resMap;
//    }
    
//    /**
//     * @param rule
//     * @param facts
//     * @param global
//     * @return
//     */
//    public <T> T evaluateObject(String ruleKey,
//            List<Map<String, Object>> facts, Map<String, Object> global,
//            Class<T> type) {
//        AssertUtils.notEmpty(ruleKey, "ruleKey is empty.");
//        RuleSession ruleSession = buildRuleSessionByRuleKey(ruleKey);
//        @SuppressWarnings("unchecked")
//        CallbackHandler<T> objectCallBackHandler = (CallbackHandler<T>) SimpleCallbackHandler.newInstance(type);
//        this.ruleSessionSupportProxy.<T> evaluateAll(ruleSession,
//                facts,
//                global,
//                objectCallBackHandler);
//        T resMap = objectCallBackHandler.getValue();
//        return resMap;
//    }
    
    /**
     * @param rule
     * @param fact
     * @param global
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T evaluateObject(String ruleKey, Map<String, ?> fact,
            Map<String, ?> global, Class<T> type) {
        AssertUtils.notEmpty(ruleKey, "ruleKey is empty.");
        RuleSession ruleSession = buildRuleSessionByRuleKey(ruleKey);
        
        CallbackHandler<T> objectCallBackHandler = (CallbackHandler<T>) SimpleCallbackHandler.newInstance(type);
        this.ruleSessionSupportProxy.<T> evaluate(ruleSession,
                (Map<String, Object>)fact,
                (Map<String, Object>)global,
                objectCallBackHandler);
        T resMap = objectCallBackHandler.getValue();
        return resMap;
    }
    
    /**
     * @param rule
     * @param fact
     * @param global
     */
    @SuppressWarnings("unchecked")
    public void evaluate(String ruleKey, Map<String, ?> fact,
            Map<String, ?> global, CallbackHandler<?> callbackHandler) {
        AssertUtils.notEmpty(ruleKey, "ruleKey is empty.");
        RuleSession ruleSession = buildRuleSessionByRuleKey(ruleKey);
        this.ruleSessionSupportProxy.evaluate(ruleSession,
                (Map<String, Object>)fact,
                (Map<String, Object>)global,
                callbackHandler);
    }
    
//    /**
//     * @param rule
//     * @param facts
//     * @param global
//     */
//    public void evaluate(String ruleKey, List<Map<String, Object>> facts,
//            Map<String, Object> global, CallbackHandler<?> callbackHandler) {
//        AssertUtils.notEmpty(ruleKey, "ruleKey is empty.");
//        RuleSession ruleSession = buildRuleSessionByRuleKey(ruleKey);
//        this.ruleSessionSupportProxy.evaluateAll(ruleSession,
//                facts,
//                global,
//                callbackHandler);
//    }
    
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
        
        private RuleSessionSupport ruleSessionSupport;
        
        /** <默认构造函数> */
        public RuleSessionSupportInvocationHandler(
                RuleSessionSupport ruleSessionSupport) {
            super();
            AssertUtils.notNull(ruleSessionSupport,
                    "ruleSessionSupport is null.");
            this.ruleSessionSupport = ruleSessionSupport;
        }
        
        /**
         * @param proxy
         * @param method
         * @param args
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(final Object proxy, final Method method,
                final Object[] args) throws Throwable {
            //参数合法性验证
            AssertUtils.notNull(args[0], "ruleSession is null.");
            AssertUtils.isTrue(args[0] instanceof RuleSession,
                    "ruleSession type error. expected:RuleSession actual:{}",
                    new Object[] { args[0].getClass() });
            
            Object result = null;
            try {
                result = method.invoke(this.ruleSessionSupport, args);
            } catch (Exception e) {
                logger.error(MessageFormatter.arrayFormat("rule evaluate error.ruleKey:{}",
                        new Object[] { ((RuleSession) args[0]).getRule()
                                .getKey() })
                        .getMessage(),
                        e);
                throw e;
            }
            return result;
        }
    }
    
    /**
     * @param 对ruleContext进行赋值
     */
    public void setRuleContext(RuleContext ruleContext) {
        this.ruleContext = ruleContext;
    }
    
    /**
     * @param 对ruleExceptionTranslator进行赋值
     */
    public void setRuleExceptionTranslator(
            RuleExceptionTranslator ruleExceptionTranslator) {
        this.ruleExceptionTranslator = ruleExceptionTranslator;
    }
    
    /**
     * @param 对ruleSessionTransationTemplate进行赋值
     */
    public void setRuleSessionTransationTemplate(
            RuleSessionTransationTemplate ruleSessionTransationTemplate) {
        this.ruleSessionTransationTemplate = ruleSessionTransationTemplate;
    }
}
