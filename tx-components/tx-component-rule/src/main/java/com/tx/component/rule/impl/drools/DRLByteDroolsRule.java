/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-24
 * <修改描述:>
 */
package com.tx.component.rule.impl.drools;

import org.drools.KnowledgeBase;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;

import com.tx.component.rule.context.BaseRule;
import com.tx.component.rule.impl.drools.exception.DroolsKnowledgeBaseInitException;
import com.tx.component.rule.loader.RuleItem;
import com.tx.component.rule.loader.RuleItemByteParam;
import com.tx.component.rule.loader.RuleStateEnum;
import com.tx.component.rule.loader.RuleTypeEnum;

/**
 * drools规则<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DRLByteDroolsRule extends BaseRule {
    
    /** 注释内容 */
    private static final long serialVersionUID = -6764646531144979549L;
    
    /** 规则状态枚举 */
    private RuleStateEnum state;
    
    /** drools规则knowlegeBase */
    private KnowledgeBase knowledgeBase;
    
    /**
     * 构造函数,根据二进制流构造drools规则
     */
    public DRLByteDroolsRule(RuleItem ruleItem) {
        super(ruleItem);
        this.ruleItem = ruleItem;
        
        RuleItemByteParam byteParam = ruleItem.getByteParam(DRLByteDroolsRuleParamEnum.DRL_BYTE.toString());
        if(byteParam == null){
            state = RuleStateEnum.ERROR;
            ruleItem.setRemark("DRL_BYTE:RuleItemByteParam is null.");
            return ;
        }
        
        byte[] bytes = byteParam.getParamValue();
        try {
            this.knowledgeBase = DroolsRuleHelper.newKnowledgeBase(ResourceFactory.newByteArrayResource(bytes),
                    ResourceType.DRL);
            this.state = RuleStateEnum.OPERATION;
        } catch (DroolsKnowledgeBaseInitException e) {
            logger.warn("knowledgeBuilderErrors: \n{}\n", e.getKnowledgeBuilderErrors());
            this.state = RuleStateEnum.ERROR;
            ruleItem.setRemark(e.getKnowledgeBuilderErrors().toString());
        }
    }
    
    /**
     * @return
     */
    @Override
    public RuleTypeEnum getRuleType() {
        return RuleTypeEnum.DROOLS_DRL_BYTE;
    }
    
    /**
     * @return 返回 knowledgeBase
     */
    public KnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }
    
    /**
     * @param 对knowledgeBase进行赋值
     */
    public void setKnowledgeBase(KnowledgeBase knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
    }
    
    /**
     * @return
     */
    @Override
    public RuleStateEnum getState() {
        return this.state;
    }
}
