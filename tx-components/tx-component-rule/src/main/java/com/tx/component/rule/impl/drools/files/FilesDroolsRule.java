///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2014年4月17日
// * <修改描述:>
// */
//package com.tx.component.rule.impl.drools.files;
//
//import org.drools.builder.ResourceType;
//import org.drools.io.ResourceFactory;
//
//import com.tx.component.rule.impl.drools.BaseDroolsRule;
//import com.tx.component.rule.impl.drools.DroolsRuleHelper;
//import com.tx.component.rule.impl.drools.drlbyte.DRLByteDroolsRuleParamEnum;
//import com.tx.component.rule.impl.drools.exception.DroolsKnowledgeBaseInitException;
//import com.tx.component.rule.loader.RuleItem;
//import com.tx.component.rule.loader.RuleItemByteParam;
//import com.tx.component.rule.loader.RuleStateEnum;
//import com.tx.component.rule.loader.RuleTypeEnum;
//
//
// /**
//  * <功能简述>
//  * <功能详细描述>
//  * 
//  * @author  Administrator
//  * @version  [版本号, 2014年4月17日]
//  * @see  [相关类/方法]
//  * @since  [产品/模块版本]
//  */
//public class FilesDroolsRule extends BaseDroolsRule {
//    
//    /** 注释内容 */
//    private static final long serialVersionUID = 8641988563506819160L;
//
//    /** <默认构造函数> */
//    public FilesDroolsRule(RuleItem ruleItem) {
//        super(ruleItem);
//        
//        RuleItemByteParam byteParam = ruleItem.getByteParam(DRLByteDroolsRuleParamEnum.DRL_BYTE.toString());
//        if (byteParam == null) {
//            state = RuleStateEnum.ERROR;
//            ruleItem.setRemark("DRL_BYTE:RuleItemByteParam is null.");
//            return;
//        }
//        
//        byte[] bytes = byteParam.getParamValue();
//        try {
//            this.knowledgeBase = DroolsRuleHelper.newKnowledgeBase(ResourceFactory.newByteArrayResource(bytes),
//                    ResourceType.DRL);
//            this.state = RuleStateEnum.OPERATION;
//        } catch (DroolsKnowledgeBaseInitException e) {
//            logger.warn("knowledgeBuilderErrors: \n{}\n",
//                    e.getKnowledgeBuilderErrors());
//            this.state = RuleStateEnum.ERROR;
//            ruleItem.setRemark(e.getKnowledgeBuilderErrors().toString());
//        }
//    }
//    
//    /**
//     * @return
//     */
//    @Override
//    public RuleTypeEnum getRuleType() {
//        return RuleTypeEnum.DROOLS_DRL_BYTE;
//    }
//}
