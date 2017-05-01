/*
 * 描          述:  <描述>
 * 修  改   人:  pengqingyang
 * 修改时间:  2013-1-27
 * <修改描述:>
 */
package com.tx.component.rule.impl.java;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.method.HandlerMethod;

import com.tx.component.rule.loader.java.annotation.RuleRequestParam;
import com.tx.component.rule.loader.java.annotation.RuleResultBody;
import com.tx.component.rule.session.CallbackHandler;
import com.tx.component.rule.session.ValueWrapper;
import com.tx.component.rule.session.impl.BaseRuleSession;
import com.tx.component.rule.transation.RuleSessionContext;
import com.tx.core.exceptions.argument.ArgTypeNotMatchedException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.MessageUtils;
import com.tx.core.util.ObjectUtils;

/**
 * 方法规则会话
 * 
 * @author  pengqingyang
 * @version  [版本号, 2013-1-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JavaMethodRuleSession extends BaseRuleSession<JavaMethodRule> {
    
    /**
     * 方法型规则会话构造函数
     */
    public JavaMethodRuleSession(JavaMethodRule rule) {
        super(rule);
    }
    
    /**
     * @param fact
     * @param resultHandle
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> void execute(Map<String, Object> fact,
            CallbackHandler<T> resultHandle) throws Exception {
        //获取规则对应的hanlderMethod
        HandlerMethod methodHandle = rule.getHandlerMethod();
        
        //从环境中获取可能已经存在的返回结果
        Map<String, Object> global = RuleSessionContext.getContext()
                .getGlobals();
        
        //解析方法执行时所需的参数
        Object[] args = resolveHandlerArguments(methodHandle,
                resultHandle,
                fact,
                global);
        Object returnObj = methodHandle.getMethod()
                .invoke(methodHandle.getBean(), args);
        
        //判断返回值类型
        //MethodParameter returnType = methodHandle.getReturnType();
        if (!methodHandle.isVoid()
                && methodHandle.getMethodAnnotation(RuleResultBody.class) != null) {
            AssertUtils.notNull(resultHandle, "resultHandle is null.");
            AssertUtils.isTrue(resultHandle.getRowType().isInstance(returnObj),
                    "result is not instanceOf type:{}",
                    new Object[] { resultHandle.getRowType() });
            
            resultHandle.setValue((T) returnObj);
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
    private <T> Object[] resolveHandlerArguments(HandlerMethod handlerMethod,
            CallbackHandler<T> callbackHandler, Map<String, Object> fact,
            Map<String, Object> global) {
        MethodParameter[] methodParamters = handlerMethod.getMethodParameters();
        //生成方法解析器
        Object[] args = new Object[methodParamters.length];
        //如果执行的方法没有参数，则直接返回
        if (methodParamters.length == 0) {
            return args;
        }
        MultiValueMap<Class<?>, Object> factTypeMap = new LinkedMultiValueMap<Class<?>, Object>();
        Map<String, Object> newFactMap = new HashMap<String, Object>();
        for (Entry<? extends String, ? extends Object> entryTemp : fact.entrySet()) {
            factTypeMap.add(entryTemp.getValue().getClass(),
                    entryTemp.getValue());
            //重新生成一个对原事实集合的map,这样可以保证原事实的引用不被交出去，对事实产生影响
            newFactMap.put(entryTemp.getKey(), entryTemp.getValue());
        }
        
        //解析参数
        int index = 0;
        for (MethodParameter methodParameterTemp : methodParamters) {
            Class<?> parameterTypeTemp = methodParameterTemp.getParameterType();
            int parameterIndex = methodParameterTemp.getParameterIndex();
            //如果对应参数存在RuleRequestParam注解
            Object argTemp = null;
            if (methodParameterTemp.getParameterAnnotation(RuleRequestParam.class) != null) {
                //优先注解匹配
                argTemp = parseRuleRequestParam(fact,
                        newFactMap,
                        methodParameterTemp,
                        parameterTypeTemp,
                        parameterIndex,
                        argTemp);
            } else if (factTypeMap.containsKey(parameterTypeTemp)) {
                AssertUtils.isTrue(factTypeMap.get(parameterTypeTemp).size() == 1,
                        "parameterIndex:{} multip type:{} value.",
                        new Object[] { parameterIndex, parameterTypeTemp });
                argTemp = factTypeMap.getFirst(parameterTypeTemp);
            } else {
                if (ValueWrapper.class.isAssignableFrom(parameterTypeTemp)) {
                    argTemp = callbackHandler;
                } else if (Map.class.isAssignableFrom(parameterTypeTemp)) {
                    argTemp = global;
                } else {
                    argTemp = ObjectUtils.newInstance(parameterTypeTemp);
                    ObjectUtils.populate(argTemp, fact);
                }
            }
            args[index++] = argTemp;
            continue;
        }
        return args;
    }
    
    /** 
     * 解析存在RuleRequestParam注解的参数
     *<功能详细描述>
     * @param fact
     * @param newFactMap
     * @param methodParameterTemp
     * @param parameterTypeTemp
     * @param argTemp
     * @return [参数说明]
     * 
     * @return Object [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private Object parseRuleRequestParam(
            Map<? extends String, ? extends Object> fact,
            Map<String, Object> newFactMap,
            MethodParameter methodParameterTemp, Class<?> parameterTypeTemp,
            int parameterIndex, Object argTemp) {
        RuleRequestParam anno = methodParameterTemp.getParameterAnnotation(RuleRequestParam.class);
        String paramName = anno.value();
        boolean required = anno.required();
        if (!StringUtils.isEmpty(paramName)) {
            //如果指定了参数在事实中的参数名
            Object factObj = fact.get(paramName);
            //如果配置参数不能为空，判断是否存在一个非空的事实数据
            AssertUtils.isTrue(required && factObj != null,
                    "paramName:{} is null.",
                    paramName);
            //判断参数类型是否匹配
            AssertUtils.isTrue(parameterTypeTemp.isAssignableFrom(factObj.getClass()),
                    "paramName:{} type is mismatch.Expected:{} Actual:{}",
                    new Object[] { paramName, parameterTypeTemp,
                            factObj.getClass() });
            //存在值，并且类型匹配时
            argTemp = factObj;
        } else {
            //如果未指定了参数在事实中的参数名
            if (Map.class.isAssignableFrom(methodParameterTemp.getParameterType())) {
                argTemp = newFactMap;
            } else {
                throw new ArgTypeNotMatchedException(
                        MessageUtils.format("Incorrect @RuleRequestParam.paramName is empty and type is not Map.parameterIndex:{}",
                                new Object[] { parameterIndex }));
            }
        }
        return argTemp;
    }
}
