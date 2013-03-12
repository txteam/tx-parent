/*
 * 描          述:  <描述>
 * 修  改   人:  pengqingyang
 * 修改时间:  2013-1-27
 * <修改描述:>
 */
package com.tx.component.rule.method;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.rule.context.RuleLoader;
import com.tx.component.rule.method.annotation.RuleMethod;
import com.tx.component.rule.method.annotation.RuleMethodClass;
import com.tx.component.rule.model.Rule;
import com.tx.component.rule.model.RuleStateEnum;
import com.tx.component.rule.model.RuleTypeEnum;
import com.tx.component.rule.model.SimplePersistenceRule;
import com.tx.component.rule.service.SimplePersistenceRuleService;
import com.tx.core.TxConstants;
import com.tx.core.exceptions.parameter.ParameterIsInvalidException;

/**
 * 方法类型的规则加载器<br/>
 * 
 * @author  pengqingyang
 * @version  [版本号, 2013-1-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("methodRuleLoader")
public class MethodRuleLoader implements RuleLoader, ApplicationContextAware {
    
    private ApplicationContext applicationContext;
    
    /** 规则存在的包 */
    private String rulePackage = "com.tx";
    
    /** 负责对规则进行持久  */
    @Resource(name = "simplePersistenceRuleService")
    private SimplePersistenceRuleService simplePersistenceRuleService;
    
    /** 
     * 是否同步移除不存在方法类型规则
     *     开发阶段如果打开该设置可能造成，当前新添加的规则，被其他机器意外移除
     */
    private boolean isSynchronizeRemoveNotExsitMethodRule = true;
    
    /**
     * 规则顺序：值大的规则加载器将会覆盖同名规则加载器
     */
    private int order = 0;
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /**
     * @return
     */
    @Transactional
    public List<Rule> load() {
        List<Rule> resList = new ArrayList<Rule>();
        
        //获取当前存在的方法规则
        List<MethodRule> currentRuleMethodList = scanCurrentSystemRuleMethod();
        //获取历史数据库中存储的规则
        Map<String, SimplePersistenceRule> dbRuleMap = queryDBMethodRuleMap();
        Map<String, MethodRule> currentRuleMap = new HashMap<String, MethodRule>();
        
        for (MethodRule mrTemp : currentRuleMethodList) {
            currentRuleMap.put(changeRuleToStringKey(mrTemp), mrTemp);
            if (!dbRuleMap.containsKey(changeRuleToStringKey(mrTemp))) {
                SimplePersistenceRule spr = new SimplePersistenceRule(mrTemp);
                this.simplePersistenceRuleService.insertSimplePersistenceRule(spr);
            }
            resList.add(mrTemp);
        }
        
        //处理db中存在，而现在已经不存在的规则
        if (isSynchronizeRemoveNotExsitMethodRule) {
            for (Entry<String, SimplePersistenceRule> mrTemp : dbRuleMap.entrySet()) {
                if (!currentRuleMap.containsKey(mrTemp.getKey())) {
                    this.simplePersistenceRuleService.changeRuleStateById(mrTemp.getValue()
                            .getId(),
                            RuleStateEnum.ERROR);
                }
            }
        }
        
        return resList;
    }
    
    /**
     * 
     */
    @Override
    public void validate(List<Rule> ruleList) {
        //doNothing方法级规则，由于在加载逻辑中已经实现了对应的校验，这里就不再进行二次校验了
    }
    
    /**
     * 方法级规则最先调用校验方法
     * @return
     */
    @Override
    public int getOrder() {
        return order;
    }
    
    /**
      *<功能简述>
      *<功能详细描述>
      * @param rule
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private String changeRuleToStringKey(Rule rule) {
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append(rule.rule())
                .append("|")
                .append(rule.getRuleType())
                .append("|")
                .append(rule.getServiceType());
        return sb.toString();
    }
    
    /**
      * 查询数据库中方法类型的规则映射
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<SimplePersistenceRule> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private Map<String, SimplePersistenceRule> queryDBMethodRuleMap() {
        List<SimplePersistenceRule> resList = this.simplePersistenceRuleService.querySimplePersistenceRuleListByRuleType(RuleTypeEnum.METHOD);
        Map<String, SimplePersistenceRule> res = new HashMap<String, SimplePersistenceRule>();
        if (resList == null) {
            return res;
        }
        for (SimplePersistenceRule simplePRuleTemp : resList) {
            res.put(changeRuleToStringKey(simplePRuleTemp), simplePRuleTemp);
        }
        return res;
    }
    
    /**
      * 扫描获取当前系统中存在的方法类型规则
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<MethodRule> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private List<MethodRule> scanCurrentSystemRuleMethod() {
        //this.applicationContext.
        Map<String, Object> beanMap = this.applicationContext.getBeansWithAnnotation(RuleMethodClass.class);
        List<MethodRule> resList = new ArrayList<MethodRule>();
        List<String> ruleNameList = new ArrayList<String>();
        
        for (Entry<String, Object> entryTemp : beanMap.entrySet()) {
            Object beanTemp = entryTemp.getValue();
            for (Method methodTemp : beanTemp.getClass().getMethods()) {
                //如果方法没有ruleMethod注解
                if (!methodTemp.isAnnotationPresent(RuleMethod.class)) {
                    continue;
                }
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
    
    /**
     * @return
     */
    @Override
    public String ruleLoaderKey() {
        return String.valueOf(this.getClass().hashCode()
                + RuleTypeEnum.METHOD.hashCode()
                + "DefaultMethodRuleLoader".hashCode());
    }
    
    /**
     * @return 返回 rulePackage
     */
    public String getRulePackage() {
        return rulePackage;
    }
    
    /**
     * @param 对rulePackage进行赋值
     */
    public void setRulePackage(String rulePackage) {
        this.rulePackage = rulePackage;
    }
    
    /**
     * @return 返回 isSynchronizeRemoveNotExsitMethodRule
     */
    public boolean isSynchronizeRemoveNotExsitMethodRule() {
        return isSynchronizeRemoveNotExsitMethodRule;
    }
    
    /**
     * @param 对isSynchronizeRemoveNotExsitMethodRule进行赋值
     */
    public void setSynchronizeRemoveNotExsitMethodRule(
            boolean isSynchronizeRemoveNotExsitMethodRule) {
        this.isSynchronizeRemoveNotExsitMethodRule = isSynchronizeRemoveNotExsitMethodRule;
    }
}
