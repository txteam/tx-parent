/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-24
 * <修改描述:>
 */
package com.tx.component.rule.impl.drools.drlbyte;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;

import com.tx.component.rule.impl.drools.BaseDroolsRule;
import com.tx.component.rule.impl.drools.DroolsRuleHelper;
import com.tx.component.rule.impl.drools.exception.DroolsKnowledgeBaseInitException;
import com.tx.component.rule.loader.RuleItem;
import com.tx.component.rule.loader.RuleItemByteParam;
import com.tx.component.rule.loader.RuleStateEnum;
import com.tx.component.rule.loader.RuleTypeEnum;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;

/**
 * drools规则<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DRLByteDroolsRule extends BaseDroolsRule {
    
    /** 注释内容 */
    private static final long serialVersionUID = -6764646531144979549L;
    
    /**
     * 构造函数,根据二进制流构造drools规则
     * @throws  
     */
    public DRLByteDroolsRule(RuleItem ruleItem) {
        super(ruleItem);
        
        RuleItemByteParam byteParam = ruleItem.getByteParam(DRLByteDroolsRuleParamEnum.DRL_BYTE.toString());
        if (byteParam == null) {
            logger.warn("rule:{} fileObj is null.", ruleItem.getKey());
            state = RuleStateEnum.ERROR;
            ruleItem.setRemark("DRL_BYTE:RuleItemByteParam is null.");
            return;
        }
        
        byte[] bytes = byteParam.getParamValue();
        try {
            this.knowledgeBase = DroolsRuleHelper.newKnowledgeBase(ResourceFactory.newReaderResource(new InputStreamReader(
                    new ByteArrayInputStream(bytes), "UTF-8")),
                    ResourceType.DRL);
            this.knowledgeBase = DroolsRuleHelper.newKnowledgeBase(ResourceFactory.newByteArrayResource(bytes),
                    ResourceType.DRL);
            this.state = RuleStateEnum.OPERATION;
        } catch (DroolsKnowledgeBaseInitException e) {
            logger.warn("knowledgeBuilderErrors: \n{}\n",
                    e.getKnowledgeBuilderErrors());
            this.state = RuleStateEnum.ERROR;
            ruleItem.setRemark(e.getKnowledgeBuilderErrors().toString());
        } catch (UnsupportedEncodingException e) {
            throw ExceptionWrapperUtils.wrapperIOException(e, "解析drools文件异常.");
        }
    }
    
    /**
     * @return
     */
    @Override
    public RuleTypeEnum getRuleType() {
        return RuleTypeEnum.DROOLS_DRL_BYTE;
    }
    
}
