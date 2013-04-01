/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-14
 * <修改描述:>
 */
package com.tx.component.rule.drools;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.drools.KnowledgeBase;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.component.rule.context.RuleLoader;
import com.tx.component.rule.exceptions.DroolsKnowledgeBaseInitException;
import com.tx.component.rule.model.Rule;
import com.tx.component.rule.model.RuleStateEnum;
import com.tx.component.rule.model.RuleTypeEnum;
import com.tx.component.rule.model.SimplePersistenceRule;
import com.tx.component.rule.model.SimpleRuleParamEnum;
import com.tx.component.rule.model.SimpleRulePropertyByte;
import com.tx.component.rule.service.SimplePersistenceRuleService;

/**
 * drools drl 类型的加载
 *      1、从数据库中读取drl资源加载
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-3-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DRLByteDroolsRuleLoader implements RuleLoader {
    
    /** 日志记录器 */
    private Logger logger = LoggerFactory.getLogger(DRLByteDroolsRuleLoader.class);
    
    /** 负责对规则进行持久  */
    @Resource(name = "simplePersistenceRuleService")
    private SimplePersistenceRuleService simplePersistenceRuleService;
    
    /** 规则加载器排序值 */
    private int order = 0;
    
    /**
     * @return
     */
    @Override
    public List<Rule> load() {
        List<Rule> resList = new ArrayList<Rule>();
        
        //获取到drools_drl_byte的资源列表
        List<SimplePersistenceRule> dbRuleList = this.simplePersistenceRuleService.querySimplePersistenceRuleListByRuleType(RuleTypeEnum.DROOLS_DRL_BYTE);
        //如果查询出的资源列表为空
        if (dbRuleList == null) {
            return resList;
        }
        
        //加载资源类规则
        for (SimplePersistenceRule spRuleTemp : dbRuleList) {
            if (!RuleStateEnum.OPERATION.equals(spRuleTemp.getState())) {
                logger.warn("rule:{} state is not operation.load skip.",
                        spRuleTemp.rule());
                
                //如果非运营态的规则
                resList.add(new DroolsRule(spRuleTemp, null));
                continue;
            }
            //获取属性
            SimpleRulePropertyByte srpb = spRuleTemp.getByteProperty(SimpleRuleParamEnum.DROOLS_DRL_RESOURCE_BYTE);
            if (srpb == null || srpb.getParamValue() == null) {
                spRuleTemp.setState(RuleStateEnum.ERROR);
                this.simplePersistenceRuleService.changeRuleStateById(spRuleTemp.getId(),
                        RuleStateEnum.ERROR);
                
                //改变状态后添加到返回的droolsRule中去
                resList.add(new DroolsRule(spRuleTemp, null));
                continue;
            } else {
                KnowledgeBase knowledgeBase = null;
                try {
                    knowledgeBase = DroolsRuleHelper.newKnowledgeBase(ResourceFactory.newByteArrayResource(srpb.getParamValue()),
                            ResourceType.DRL);
                } catch (DroolsKnowledgeBaseInitException e) {
                    logger.warn("rule:{} build has error.load skip."
                            + spRuleTemp.rule());
                    logger.warn("error:{}", e.getKnowledgeBuilderErrors());
                    
                    spRuleTemp.setState(RuleStateEnum.ERROR);
                    this.simplePersistenceRuleService.changeRuleStateById(spRuleTemp.getId(),
                            RuleStateEnum.ERROR);
                }
                
                resList.add(new DroolsRule(spRuleTemp, knowledgeBase));
            }
        }
        
        return resList;
    }
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return this.order;
    }
    
    /**
     * @return 返回 simplePersistenceRuleService
     */
    public SimplePersistenceRuleService getSimplePersistenceRuleService() {
        return simplePersistenceRuleService;
    }
    
    /**
     * @param 对simplePersistenceRuleService进行赋值
     */
    public void setSimplePersistenceRuleService(
            SimplePersistenceRuleService simplePersistenceRuleService) {
        this.simplePersistenceRuleService = simplePersistenceRuleService;
    }
    
    /**
     * @param 对order进行赋值
     */
    public void setOrder(int order) {
        this.order = order;
    }
}
