/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-4-1
 * <修改描述:>
 */
package com.tx.component.rule.impl.drools.drlfile;

import java.util.Set;

import com.tx.component.rule.impl.drools.BaseDroolsRuleSession;
import com.tx.component.rule.loader.RuleItem;
import com.tx.component.rule.loader.RuleItemParam;
import com.tx.component.rule.loader.RuleItemParamHelper;
import com.tx.component.rule.loader.RuleRegister;
import com.tx.component.rule.loader.RuleTypeEnum;
import com.tx.component.rule.session.RuleSession;

/**
 * DROOLS_DRL_BYTE类型的规则注册器
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-4-1]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DRLFileDroolsRuleRegister implements
        RuleRegister<DRLFileDroolsRule> {
    
    /**
     * @return
     */
    @Override
    public RuleTypeEnum ruleType() {
        return RuleTypeEnum.DROOLS_DRL_FILE;
    }
    
    /**
     * @return
     */
    @Override
    public Set<RuleItemParam> ruleItemParam() {
        Set<RuleItemParam> resSet = RuleItemParamHelper.getRuleItemParamSet(DRLFileDroolsRuleParamEnum.class);
        return resSet;
    }
    
    /**
     * @param rule
     * @return
     */
    @Override
    public RuleSession buildRuleSession(DRLFileDroolsRule rule) {
        BaseDroolsRuleSession<DRLFileDroolsRule> ruleSession = new BaseDroolsRuleSession<DRLFileDroolsRule>(
                rule);
        return ruleSession;
    }
    
    /**
     * @param ruleItem
     * @return
     */
    @Override
    public DRLFileDroolsRule registe(RuleItem ruleItem) {
        DRLFileDroolsRule res = new DRLFileDroolsRule(ruleItem);
        return res;
    }
}
