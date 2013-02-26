/*
 * 描          述:  <描述>
 * 修  改   人:  pengqingyang
 * 修改时间:  2013-1-27
 * <修改描述:>
 */
package com.tx.component.rule.method;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.tx.component.rule.context.RuleLoader;
import com.tx.component.rule.method.annotation.RuleMethod;
import com.tx.component.rule.method.annotation.RuleMethodClass;
import com.tx.component.rule.model.Rule;
import com.tx.component.rule.service.SimplePersistenceRuleService;
import com.tx.core.TxConstants;

/**
 * 方法类型的规则加载器<br/>
 * 
 * @author  pengqingyang
 * @version  [版本号, 2013-1-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("methodRuleLoader")
public class MethodRuleLoader implements RuleLoader,ApplicationContextAware {
    
    private ApplicationContext applicationContext;
    
    /** 规则存在的包 */
    private String rulePackage;
    
    /** 负责对规则进行持久  */
    @Resource(name = "simplePersistenceRuleService")
    private SimplePersistenceRuleService simplePersistenceRuleService;
    
    /** 
     * 是否同步移除不存在方法类型规则
     *     开发阶段如果打开该设置可能造成，当前新添加的规则，被其他机器意外移除
     */
    private boolean isSynchronizeRemoveNotExsitMethodRule = true;
    
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
    @PostConstruct
    @Override
    public List<Rule> load() {
        //获取当前存在的方法规则
        
        return null;
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
    private String ruleToStringKey(Rule rule) {
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append(rule.rule())
                .append("|")
                .append(rule.getRuleType())
                .append("|")
                .append(rule.getServiceType());
        return sb.toString();
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
    private List<MethodRule> scanCurrentSystemRuleMethod(){
        Map<String, Object> beanMap = this.applicationContext.getBeansWithAnnotation(RuleMethodClass.class);
        List<MethodRule> resList = new ArrayList<MethodRule>();
        for(Entry<String, Object> entryTemp: beanMap.entrySet()){
            Object beanTemp = entryTemp.getValue();
            for(Method methodTemp : beanTemp.getClass().getMethods()){
                //如果方法没有ruleMethod注解
                if(!methodTemp.isAnnotationPresent(RuleMethod.class)){
                    continue;
                }
                RuleMethod ruleAnnotation = methodTemp.getAnnotation(RuleMethod.class);
                MethodRule newMR = new MethodRule(methodTemp, beanTemp, ruleAnnotation);
                resList.add(newMR);
            }
        }
        return resList;
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
