/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-31
 * <修改描述:>
 */
package com.tx.component.rule.drools;

import java.util.Collection;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.io.Resource;

import com.tx.component.rule.exceptions.DroolsKnowledgeBaseInitException;

/**
 * drools辅助类<br/>
 *     在该类中负责生成knowledgeBase
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-3-31]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class DroolsRuleHelper {
    
    /**
      * 生成konwledgeBase
      * @param rule
      * @param droolsResource
      * @param resourceType
      * @return [参数说明]
      * 
      * @return KnowledgeBase [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static KnowledgeBase newKnowledgeBase(Resource droolsResource,
            ResourceType resourceType) throws DroolsKnowledgeBaseInitException {
        KnowledgeBuilder kBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kBuilder.add(droolsResource, resourceType);
        
        if (kBuilder.hasErrors()) {
            throw new DroolsKnowledgeBaseInitException(kBuilder.getErrors());
        }
        
        Collection<KnowledgePackage> kpCollection = kBuilder.getKnowledgePackages();
        KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
        knowledgeBase.addKnowledgePackages(kpCollection);
        
        return knowledgeBase;
    }
}
