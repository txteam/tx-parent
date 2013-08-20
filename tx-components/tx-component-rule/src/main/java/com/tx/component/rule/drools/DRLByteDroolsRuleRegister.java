/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-4-1
 * <修改描述:>
 */
package com.tx.component.rule.drools;

import javax.annotation.Resource;

import org.drools.KnowledgeBase;
import org.drools.builder.ResourceType;
import org.drools.core.util.StringUtils;
import org.drools.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tx.component.rule.context.RuleRegister;
import com.tx.component.rule.exceptions.DroolsKnowledgeBaseInitException;
import com.tx.component.rule.exceptions.RuleRegisteException;
import com.tx.component.rule.model.Rule;
import com.tx.component.rule.model.RuleStateEnum;
import com.tx.component.rule.model.RuleTypeEnum;
import com.tx.component.rule.model.SimplePersistenceRule;
import com.tx.component.rule.model.SimpleRuleParamEnum;
import com.tx.component.rule.model.SimpleRulePropertyByte;
import com.tx.component.rule.service.SimplePersistenceRuleService;
import com.tx.core.exceptions.argument.NullArgException;

/**
 * DROOLS_DRL_BYTE类型的规则注册器
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-4-1]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("drlByteDroolsRuleRegister")
public class DRLByteDroolsRuleRegister implements RuleRegister<DroolsRule> {
    
    /** 日志记录器 */
    private Logger logger = LoggerFactory.getLogger(DRLByteDroolsRuleRegister.class);
    
    private int order = 0;
    
    @Resource(name = "simplePersistenceRuleService")
    private SimplePersistenceRuleService simplePersistenceRuleService;
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return this.order;
    }
    
    /**
     * @return
     */
    @Override
    public RuleTypeEnum ruleType() {
        return RuleTypeEnum.DROOLS_DRL_BYTE;
    }
    
    /**
     * @param rule
     */
    @Override
    public void validate(Rule rule) {
        //如果传入额规则不为空，且状态为运营态
        if (rule != null && RuleStateEnum.OPERATION.equals(rule.getState())) {
            if (rule instanceof DroolsRule) {
                DroolsRule droolsRule = (DroolsRule) rule;
                if (droolsRule.getKnowledgeBase() == null) {
                    droolsRule.setState(RuleStateEnum.ERROR);
                    SimplePersistenceRule spRule = droolsRule.getSimplePersistenceRule();
                    this.simplePersistenceRuleService.changeRuleStateById(spRule.getId(),
                            RuleStateEnum.ERROR);
                }
            }
            else {
                rule.setState(RuleStateEnum.ERROR);
                this.simplePersistenceRuleService.changeRuleStateByRule(rule.rule(),
                        rule.getServiceType(),
                        rule.getRuleType(),
                        RuleStateEnum.ERROR);
            }
        }
    }
    
    /**
     * @param rule
     * @return
     */
    @Override
    public Rule registe(SimplePersistenceRule rule) {
        if (rule == null) {
            logger.warn("registe rule fail. rule is empty.");
            throw new NullArgException(
                    "registe rule fail.rule is null.");
        }
        if (rule.getByteProperty(SimpleRuleParamEnum.DROOLS_DRL_RESOURCE_BYTE) != null
                && !StringUtils.isEmpty(rule.rule())
                && !StringUtils.isEmpty(rule.getServiceType())
                && rule.getRuleType() != null) {
            SimpleRulePropertyByte srpb = rule.getByteProperty(SimpleRuleParamEnum.DROOLS_DRL_RESOURCE_BYTE);
            KnowledgeBase knowledgeBase = null;
            try {
                knowledgeBase = DroolsRuleHelper.newKnowledgeBase(ResourceFactory.newByteArrayResource(srpb.getParamValue()),
                        ResourceType.DRL);
                rule.setState(RuleStateEnum.OPERATION);
                rule.setRemark("success.");
            } catch (DroolsKnowledgeBaseInitException e) {
                logger.warn("rule:{} build has error.load skip."
                        + rule.rule());
                logger.warn("error:{}", e.getKnowledgeBuilderErrors());
                
                rule.setState(RuleStateEnum.ERROR);
                rule.setRemark(e.getKnowledgeBuilderErrors().toString());
            }
            //持久化该规则
            this.simplePersistenceRuleService.saveSimplePersistenceRule(rule);
            
            //生成并返回DroolsRule
            DroolsRule resRule = new DroolsRule(rule, knowledgeBase);
            return resRule;
        }
        else {
            //注册的规则非法
            throw new RuleRegisteException(
                    "registe rule:{} fail. rule:{} serviceType:{} ruleType:{} byte{} is empty",
                    new Object[] {
                            rule,
                            rule.getRule(),
                            rule.getServiceType(),
                            rule.getByteProperty(SimpleRuleParamEnum.DROOLS_DRL_RESOURCE_BYTE) });
        }
    }
}
