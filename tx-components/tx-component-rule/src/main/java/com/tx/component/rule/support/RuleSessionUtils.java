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

import com.tx.component.rule.collection.CollectionRule;
import com.tx.component.rule.drools.DroolsRule;
import com.tx.component.rule.exceptions.RuleAccessException;
import com.tx.component.rule.exceptions.RuleExceptionTranslator;
import com.tx.component.rule.exceptions.RuleNotExsitException;
import com.tx.component.rule.method.MethodRule;
import com.tx.component.rule.model.Rule;

/**
 * 规则会话工具类<br/>
 *     1、根据规则生成对应规则会话对象<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleSessionUtils {
    
    /** 规则异常翻译器，将规则执行期间的异常进行统一封装，让外部得知是执行哪个规则期间出现的异常 */
    private RuleExceptionTranslator ruleExceptionTranslator;
    
    /**
      * <功能简述>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return RuleSession [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public RuleSession createRuleSession(MethodRule rule) {
        return null;
    }
    
    public RuleSession createRuleSession(DroolsRule rule){
        
        return null;
    }
    
    public RuleSession createRuleSession(CollectionRule rule){
        
        return null;
    }
    
    /**
      * 代理规则回话，自动执行，初始化以及销毁
      * @param methodRule
      * @return [参数说明]
      * 
      * @return RuleSession [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
//    private <FACT_TYPE, RESULT_TYPE> RuleSession proxyRuleSession(
//            RuleSessionInstance<FACT_TYPE, RESULT_TYPE> methodRuleInstance) {
//        if (methodRuleInstance.getInitHandle() == null
//                || methodRuleInstance.getExecutor() == null
//                || methodRuleInstance.getResultHandle() == null) {
//            throw new RuleAccessException(methodRuleInstance.rule(), null,
//                    methodRuleInstance, "规则实体不合法，规则执行器，初始化器，或结果回调句柄未初始化");
//        }
//        
//        //Proxy.newProxyInstance(this.getClass().getClassLoader(), RuleSession.class, new RuleSessionInvocationHandler());
//        methodRuleInstance.getInitHandle().initContext();
//        
//        methodRuleInstance.getExecutor().execute();
//        
//        methodRuleInstance.getResultHandle().getValue();
//        
//        return null;
//    }
    
    /**
      * 规则回话代理句柄
      * 
      * @author  brady
      * @version  [版本号, 2013-1-24]
      * @see  [相关类/方法]
      * @since  [产品/模块版本]
     */
    private static class RuleSessionInvocationHandler implements InvocationHandler{
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
            // TODO Auto-generated method stub
            return null;
        }
    }
}
