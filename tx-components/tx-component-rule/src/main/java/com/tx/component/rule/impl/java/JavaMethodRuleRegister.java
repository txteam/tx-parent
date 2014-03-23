/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-14
 * <修改描述:>
 */
package com.tx.component.rule.impl.java;

import java.util.Set;

import com.tx.component.rule.loader.RuleItem;
import com.tx.component.rule.loader.RuleItemParam;
import com.tx.component.rule.loader.RuleItemParamHelper;
import com.tx.component.rule.loader.RuleRegister;
import com.tx.component.rule.loader.RuleTypeEnum;
import com.tx.component.rule.session.RuleSession;

/**
 * 方法规则注册器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-3-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JavaMethodRuleRegister implements RuleRegister<JavaMethodRule> {
    
    /**
     * @return
     */
    @Override
    public RuleTypeEnum ruleType() {
        return RuleTypeEnum.JAVA_METHOD;
    }
    
    /**
     * @return
     */
    @Override
    public Set<RuleItemParam> ruleItemParam() {
        Set<RuleItemParam> resSet = RuleItemParamHelper.getRuleItemParamSet(JavaMethodRuleParamEnum.class);
        return resSet;
    }
    
    /**
     * @param ruleItem
     * @return
     */
    @Override
    public JavaMethodRule registe(RuleItem ruleItem) {
        JavaMethodRule javaMethodRule = new JavaMethodRule(ruleItem);
        return javaMethodRule;
    }
    
    /**
     * @param rule
     * @return
     */
    @Override
    public RuleSession buildRuleSession(JavaMethodRule rule) {
        JavaMethodRuleSession javaMethodRuleSession = new JavaMethodRuleSession(
                rule);
        return javaMethodRuleSession;
    }
}
