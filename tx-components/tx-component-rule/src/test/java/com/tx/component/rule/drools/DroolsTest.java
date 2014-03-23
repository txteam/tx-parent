/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-20
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
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DroolsTest {
    
    public static void main(String[] args) {
        KnowledgeBuilder kBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kBuilder.add(ResourceFactory.newClassPathResource("com/tx/component/rule/newDrl.drl"),
                ResourceType.DSL);
        
        if(kBuilder.hasErrors()){
            System.out.println("kBuilder hasErrors");
        }
        Collection<KnowledgePackage>  kpCollection = kBuilder.getKnowledgePackages();
        for(KnowledgePackage kp : kpCollection){
            kp.getName();
            kp.getGlobalVariables();
        }
        
        KnowledgeBase kb = KnowledgeBaseFactory.newKnowledgeBase();
        StatefulKnowledgeSession session = kb.newStatefulKnowledgeSession();
        try {
            session.setGlobal("xxx", new Object());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        session.insert("sss");
        session.fireAllRules();
        
        session.dispose();
        System.out.println("over.");
    }
}
