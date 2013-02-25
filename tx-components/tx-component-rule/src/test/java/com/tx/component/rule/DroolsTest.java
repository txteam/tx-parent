/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-20
 * <修改描述:>
 */
package com.tx.component.rule;

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
        //kb.addKnowledgePackages(arg0);
        //kb.
        //kb.getRule(packageName, ruleName)
        
        //start
        StatefulKnowledgeSession session = kb.newStatefulKnowledgeSession();
        
        //.args.session.
        session.setGlobal("xxx", new Object());
        session.setGlobal("xxx", new Object());
        
        session.insert("sss");
        //session.update(, object);
        //session.retract(handle);
        
        //session.execute(;);   
        //session.execute(command)
        
        //session.insert(arg0);
    }
}
