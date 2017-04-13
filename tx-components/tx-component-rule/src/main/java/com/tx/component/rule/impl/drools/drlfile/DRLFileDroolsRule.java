///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2014年4月17日
// * <修改描述:>
// */
//package com.tx.component.rule.impl.drools.drlfile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.io.UnsupportedEncodingException;
//
//import org.drools.builder.ResourceType;
//import org.drools.io.ResourceFactory;
//
//import com.tx.component.rule.impl.drools.BaseDroolsRule;
//import com.tx.component.rule.impl.drools.DroolsRuleHelper;
//import com.tx.component.rule.impl.drools.exception.DroolsKnowledgeBaseInitException;
//import com.tx.component.rule.loader.RuleItem;
//import com.tx.component.rule.loader.RuleStateEnum;
//import com.tx.component.rule.loader.RuleTypeEnum;
//import com.tx.core.exceptions.resource.ResourceParseException;
//import com.tx.core.exceptions.util.ExceptionWrapperUtils;
//
///**
// * drl文件类型的drools规则
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2014年4月17日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class DRLFileDroolsRule extends BaseDroolsRule {
//    
//    /** 注释内容 */
//    private static final long serialVersionUID = -6289296692733571973L;
//    
//    /**
//     * 构造函数,根据二进制流构造drools规则
//     * @throws  
//     */
//    public DRLFileDroolsRule(RuleItem ruleItem) {
//        super(ruleItem);
//        
//        Object fileObj = ruleItem.getObjectParam(DRLFileDroolsRuleParamEnum.DRL_FILE.toString());
//        if (fileObj == null) {
//            logger.warn("rule:{} fileObj is null.", ruleItem.getKey());
//            state = RuleStateEnum.ERROR;
//            ruleItem.setRemark("DRL_BYTE:RuleItemByteParam is null.");
//            return;
//        }
//        if (!(fileObj instanceof File)) {
//            logger.warn("rule:{} fileObj is not File.", ruleItem.getKey());
//            state = RuleStateEnum.ERROR;
//            ruleItem.setRemark("DRL_FILE is not file.");
//            return;
//        }
//        
//        File file = (File) fileObj;
//        try {
//            Reader fileReader = new InputStreamReader(new FileInputStream(file),"UTF-8");
//            this.knowledgeBase = DroolsRuleHelper.newKnowledgeBase(ResourceFactory.newReaderResource(fileReader,"UTF-8"),
//                    ResourceType.DRL);
//            this.state = RuleStateEnum.OPERATION;
//        } catch (DroolsKnowledgeBaseInitException e) {
//            logger.warn("knowledgeBuilderErrors: \n{}\n",
//                    e.getKnowledgeBuilderErrors());
//            this.state = RuleStateEnum.ERROR;
//            ruleItem.setRemark(e.getKnowledgeBuilderErrors().toString());
//        } catch (FileNotFoundException e) {
//            throw ExceptionWrapperUtils.wrapperIOException(e, "file not exist.");
//        } catch (UnsupportedEncodingException e) {
//            throw ExceptionWrapperUtils.wrapperSILException(ResourceParseException.class, "解析drools规则文件异常.filePaht:{}", new Object[]{});
//        }
//    }
//    
//    /**
//     * @return
//     */
//    @Override
//    public RuleTypeEnum getRuleType() {
//        return RuleTypeEnum.DROOLS_DRL_FILE;
//    }
//    
//}
