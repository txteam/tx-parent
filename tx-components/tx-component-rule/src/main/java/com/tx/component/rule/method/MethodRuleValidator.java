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

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.tx.component.rule.context.RuleValidator;
import com.tx.component.rule.model.Rule;
import com.tx.component.rule.model.RuleStateEnum;
import com.tx.component.rule.service.SimplePersistenceRuleService;
import com.tx.core.TxConstants;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-3-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("methodRuleValidator")
public class MethodRuleValidator implements RuleValidator<MethodRule>,
        InitializingBean {
    
    @Resource(name = "methodRuleLoader")
    private MethodRuleLoader methodRuleLoader;
    
    @Resource(name = "simplePersistenceRuleService")
    private SimplePersistenceRuleService simplePersistenceRuleService;
    
    private Map<String, MethodRule> currentMethodRuleMap;
    
    private int order = 0;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        currentMethodRuleMap = new HashMap<String, MethodRule>();
        //当前系统中的方法
        List<MethodRule> currentMethodRuleList = this.methodRuleLoader.scanCurrentSystemRuleMethod();
        
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
    public Class<MethodRule> validateType() {
        return MethodRule.class;
    }
    
    /**
     * @param rule
     */
    @Override
    public void validate(Rule rule) {
        if (!currentMethodRuleMap.containsKey(getRuleKeyFromRule(rule))) {
            if (RuleStateEnum.OPERATION.equals(rule.getState())) {
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
