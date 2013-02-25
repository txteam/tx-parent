/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-28
 * <修改描述:>
 */
package com.tx.component.rule.drools.test1;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatefulSession;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.compiler.PackageBuilder;
import org.drools.io.ResourceFactory;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


 /**
  * 规则引擎第一种使用场景：<br/>
  *     根据规则过滤事实，查找匹配规则的事实<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-28]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
        "classpath:spring/beans-ds.xml",
        "classpath:spring/beans-tx.xml",
        "classpath:spring/beans.xml" })
@ActiveProfiles("dev")
public class TestDrools1 {
    
    public static void main(String[] args) throws Exception {
        //加载KBuilder
        KnowledgeBuilder kBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kBuilder.add(ResourceFactory.newClassPathResource("com/tx/component/rule/test1/test1.drl"),
                ResourceType.DSL);
        //kBuilder.getKnowledgePackages()
       
        final PackageBuilder builder = new PackageBuilder();  
        builder.addPackageFromDrl(ResourceFactory.newClassPathResource("com/tx/component/rule/test1/test1.drl"));   
        
        //创建ruleBase
        final RuleBase ruleBase = RuleBaseFactory.newRuleBase();  
        //Collection<KnowledgePackage> pC = kBuilder.getKnowledgePackages();
        //ruleBase.addPackages(pkgs);
        ruleBase.addPackage(builder.getPackage());
        
        
        //创建会话
        final StatefulSession session = ruleBase.newStatefulSession();  
        for(int i = 0 ; i < 10;i++){  
//            session.insert(new Guess("A" , i));  
//            session.insert(new Guess("B" , i));  
//            session.insert(new Guess("C" , i));  
//            session.insert(new Guess("D" , i));  
        }  
        session.fireAllRules();  
        session.dispose();  
    }
}
