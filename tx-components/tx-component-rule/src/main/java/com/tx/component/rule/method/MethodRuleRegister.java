/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-14
 * <修改描述:>
 */
package com.tx.component.rule.method;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.tx.component.rule.context.RuleRegister;
import com.tx.component.rule.exceptions.RuleExceptionTranslator;
import com.tx.component.rule.model.Rule;
import com.tx.component.rule.model.RuleStateEnum;
import com.tx.component.rule.model.RuleTypeEnum;
import com.tx.component.rule.model.SimplePersistenceRule;
import com.tx.component.rule.service.SimplePersistenceRuleService;
import com.tx.core.TxConstants;
import com.tx.core.exceptions.SILException;

/**
 * 方法规则注册器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-3-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("methodRuleRegister")
public class MethodRuleRegister implements RuleRegister<MethodRule>,
        InitializingBean, ApplicationContextAware {
    
    @Resource(name = "simplePersistenceRuleService")
    private SimplePersistenceRuleService simplePersistenceRuleService;
    
    private Map<String, MethodRule> currentMethodRuleMap;
    
    private int order = 0;
    
    private ApplicationContext applicationContext;
    
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
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        currentMethodRuleMap = new HashMap<String, MethodRule>();
        //当前系统中的方法
        List<MethodRule> currentMethodRuleList = MethodRuleHelper.scanCurrentSystemRuleMethod(this.applicationContext);
        
        //当前方法规则列表映射
        if (currentMethodRuleList != null) {
            for (MethodRule mrTemp : currentMethodRuleList) {
                currentMethodRuleMap.put(getRuleKeyFromRule(mrTemp), mrTemp);
            }
        }
    }
    
    /**
     * @return
     */
    @Override
    public RuleTypeEnum ruleType() {
        return RuleTypeEnum.METHOD;
    }
    
    /**
     * @param rule
     * @return
     */
    @Override
    public Rule registe(SimplePersistenceRule rule) {
        throw new SILException(RuleExceptionTranslator.RULE_ERROR_CODE,
                new Object[] { "method rule register not support.rule:{}",
                        rule != null ? rule.rule() : "null" });
    }
    
    /**
     * @param rule
     */
    @Override
    public void validate(Rule rule) {
        //规则不为空，且状态为运营态的规则才需要进行校验
        if (rule != null && RuleStateEnum.OPERATION.equals(rule.getState())) {
            if (rule instanceof MethodRule) {
                MethodRule methodRule = (MethodRule) rule;
                if (methodRule.getObject() == null
                        || methodRule.getMethod() == null
                        || methodRule.getRuleAnnotation() == null
                        || !currentMethodRuleMap.containsKey(getRuleKeyFromRule(rule))) {
                    methodRule.setState(RuleStateEnum.ERROR);
                    SimplePersistenceRule spRule = methodRule.getSimplePersistenceRule();
                    this.simplePersistenceRuleService.changeRuleStateById(spRule.getId(),
                            RuleStateEnum.ERROR);
                }
            } else {
                rule.setState(RuleStateEnum.ERROR);
                this.simplePersistenceRuleService.changeRuleStateByRule(rule.rule(),
                        rule.getServiceType(),
                        rule.getRuleType(),
                        RuleStateEnum.ERROR);
            }
        }
    }
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return this.order;
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
    private String getRuleKeyFromRule(Rule rule) {
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append(rule.getRuleType())
                .append(".")
                .append(rule.getServiceType())
                .append(".")
                .append(rule.rule());
        return sb.toString();
    }
}
