/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-4-1
 * <修改描述:>
 */
package com.tx.component.rule.method;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

import com.tx.component.rule.method.annotation.RuleMethod;
import com.tx.component.rule.method.annotation.RuleMethodClass;
import com.tx.core.exceptions.parameter.ParameterIsInvalidException;

/**
 * 方法规则帮助类<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-4-1]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class MethodRuleHelper {
    
    /**
     * 扫描获取当前系统中存在的方法类型规则
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<MethodRule> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static List<MethodRule> scanCurrentSystemRuleMethod(
            ApplicationContext applicationContext) {
        //在spring容器中扫描含有RuleMethodClass注解的类
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(RuleMethodClass.class);
        List<MethodRule> resList = new ArrayList<MethodRule>();
        List<String> ruleNameList = new ArrayList<String>();
        
        //遍历类中所有方法，如果含有RuleMethod注解
        for (Entry<String, Object> entryTemp : beanMap.entrySet()) {
            Object beanTemp = entryTemp.getValue();
            for (Method methodTemp : beanTemp.getClass().getMethods()) {
                //如果方法没有ruleMethod注解
                if (!methodTemp.isAnnotationPresent(RuleMethod.class)) {
                    continue;
                }
                
                //如果含有：提取，并生成MethodRule
                RuleMethod ruleAnnotation = methodTemp.getAnnotation(RuleMethod.class);
                if (StringUtils.isEmpty(ruleAnnotation.rule())
                        || StringUtils.isEmpty(ruleAnnotation.serviceType())) {
                    throw new ParameterIsInvalidException(
                            "MethodRuleLoader.scanCurrentSystemRuleMethod exception class:{} method{} RuleMethod invalid",
                            beanTemp.getClass().toString(),
                            methodTemp.getName());
                }
                MethodRule newMR = new MethodRule(methodTemp, beanTemp,
                        ruleAnnotation);
                if (ruleNameList.contains(newMR.rule())) {
                    throw new ParameterIsInvalidException(
                            "MethodRuleLoader.scanCurrentSystemRuleMethod exception class:{} method{} RuleMethod rule{} duplicate",
                            beanTemp.getClass().toString(),
                            methodTemp.getName(), newMR.rule());
                }
                ruleNameList.add(newMR.rule());
                resList.add(newMR);
            }
        }
        return resList;
    }
}
