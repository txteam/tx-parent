/*
 * 描          述:  <描述>
 * 修  改   人:  pengqingyang
 * 修改时间:  2013-1-27
 * <修改描述:>
 */
package com.tx.component.rule.method;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.drools.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tx.component.rule.RuleConstants;
import com.tx.component.rule.exceptions.RuleAccessException;
import com.tx.component.rule.method.annotation.RuleMethodParam;
import com.tx.component.rule.method.annotation.RuleMethodResult;
import com.tx.component.rule.support.RuleSessionContext;
import com.tx.component.rule.support.impl.DefaultRuleSession;
import com.tx.core.support.method.MethodResolver;
import com.tx.core.support.method.ParameterResolver;

/**
 * 方法规则会话
 * 
 * @author  pengqingyang
 * @version  [版本号, 2013-1-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MethodRuleSession extends DefaultRuleSession<MethodRule> {
    
    private static final Logger logger = LoggerFactory.getLogger(MethodRuleSession.class);
    
    /** 规则会话中是根据结果还是参数，获取规则结果 */
    private boolean isHasRuleMethodResultAnnotation = false;
    
    /**
     * 方法型规则会话构造函数
     */
    public MethodRuleSession(MethodRule rule) {
        super(rule);
    }
    
    /**
     * 
     */
    @Override
    public void execute(Map<String, Object> fact) {
        Object[] args = resolveHandlerArguments(rule.getMethod(), fact);
        try {
            Object returnObj = rule.getMethod().invoke(rule.getObject(), args);
            if (!isHasRuleMethodResultAnnotation) {
                //将结果对象，写入约定现成变量的key中
                RuleSessionContext.setGlobal(RuleConstants.RULE_PROMISE_CONSTANT_RESULT,
                        returnObj);
            }
        } catch (Exception e) {
            logger.error("MethodRuleSession execute exception: " + e.toString(),
                    e);
            throw new RuleAccessException(this.rule.rule(), this.rule, this,
                    "rule:{} execute exception.");
        }
    }
    
    /**
     * @param fact
     */
    @Override
    public void execute(List<Map<String, Object>> facts) {
        if(facts == null){
            return ;
        }
        for(Map<String, Object> fact : facts){
            execute(fact);
        }
    }

    /**
      * 解析生成规则会话方法调用参数数组<br/>
      * <功能详细描述>
      * @return
      * @throws Exception [参数说明]
      * 
      * @return Object[] [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private Object[] resolveHandlerArguments(Method method,
            Map<String, Object> fact) {
        //生成方法解析器
        MethodResolver methodResolver = MethodResolver.resolveMethodResolver(method);
        Object[] args = new Object[methodResolver.getParametersLength()];
        
        MultiValueMap<Class<?>, Object> typeMap = new LinkedMultiValueMap<Class<?>, Object>();
        for (Entry<String, Object> entryTemp : fact.entrySet()) {
            typeMap.add(entryTemp.getValue().getClass(), entryTemp.getValue());
        }
        
        for (int i = 0; i < methodResolver.getParametersLength(); i++) {
            ParameterResolver paramterResolver = methodResolver.getParameterResolvers()
                    .get(i);
            if (paramterResolver.isHasAnnotation(RuleMethodResult.class)) {
                //如果注解对应参数为结果，则不再识别其他注解
                //如果存在多个，ruleSession中只保留最后一个对象的句柄
                //resultHandle = 
                Object result = RuleSessionContext.getGlobal(RuleConstants.RULE_PROMISE_CONSTANT_RESULT);
                if (result != null) {
                    args[i] = result;
                    continue;
                }
                Class<?> type = paramterResolver.getParamterType();
                try {
                    Object resultObj = type.newInstance();
                    RuleSessionContext.setGlobal(RuleConstants.RULE_PROMISE_CONSTANT_RESULT,
                            resultObj);
                    isHasRuleMethodResultAnnotation = true;
                    continue;
                } catch (InstantiationException e) {
                    logger.error("resolveHandlerArguments exceptions: ", e);
                    throw new RuleAccessException(
                            this.rule.rule(),
                            this.rule,
                            this,
                            "rule:{} param[{}] @RuleMethodResult param must has default constructor.",
                            this.rule.rule(), String.valueOf(i));
                } catch (IllegalAccessException e) {
                    logger.error("resolveHandlerArguments exceptions: ", e);
                    throw new RuleAccessException(
                            this.rule.rule(),
                            this.rule,
                            this,
                            "rule:{} param[{}] @RuleMethodResult param must has default constructor.",
                            this.rule.rule(), String.valueOf(i));
                }
            } else if (paramterResolver.isHasAnnotation(RuleMethodParam.class)) {
                //如果有RuleMethodParam注解
                RuleMethodParam ruleMethodParamInstance = paramterResolver.getAnnotation(RuleMethodParam.class);
                if (StringUtils.isEmpty(ruleMethodParamInstance.value())) {
                    if (Map.class.isAssignableFrom(paramterResolver.getParamterType())
                            || Model.class.isAssignableFrom(paramterResolver.getParamterType())
                            || ModelMap.class.isAssignableFrom(paramterResolver.getParamterType())) {
                        args[i] = fact;
                        continue;
                    }
                } else if (!StringUtils.isEmpty(ruleMethodParamInstance.value())) {
                    String paramKey = ruleMethodParamInstance.value();
                    Object paramValue = fact.get(paramKey);
                    if (ruleMethodParamInstance.required()
                            && paramValue == null) {
                        paramValue = ruleMethodParamInstance.defaultValue();
                        if (paramValue == null) {
                            throw new RuleAccessException(
                                    this.rule.rule(),
                                    this.rule,
                                    this,
                                    "rule:{} param[{}] is required. must has not emptyValue",
                                    this.rule.rule(), String.valueOf(i));
                        }
                    }
                    args[i] = paramValue;
                    continue;
                }
            } else if (typeMap.containsKey(paramterResolver.getParamterType())) {
                //如果没有RuleMethodParam注解，但事实对象中存在同类型的对象，则将最后一个同类型对象压入参数
                args[i] = typeMap.getFirst(paramterResolver.getParamterType());
            } else {
                args[i] = null;
            }
        }
        
        return args;
    }
    
}
