/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-28
 * <修改描述:>
 */
package com.tx.component.rule.drools.test1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tx.component.rule.method.model.ProcessRule;
import com.tx.component.rule.method.model.TestPojo;
import com.tx.component.rule.method.model.TestPojoDao;
import com.tx.component.rule.support.RuleSessionTemplate;


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
        "classpath:spring/beans-aop.xml",
        "classpath:spring/beans-ds.xml",
        "classpath:spring/beans-tx.xml", 
        "classpath:spring/beans-cache.xml",
        "classpath:spring/beans-rule.xml",
        "classpath:spring/beans.xml"})
public class TestDrools1 {
    
    @Resource(name = "ruleSessionTemplate")
    private RuleSessionTemplate ruleSessionTemplate;
    
    @Test
    public void testRuleConstants() {
        try {
            //
            TestPojo testPojo = new TestPojo();
            testPojo.setTest("test:abc");
            TestPojoDao testPojoDao = new TestPojoDao();
            Map<String, Object> fact = new HashMap<String, Object>();
            fact.put("testPojo", testPojo);
            fact.put("testPojoDao", testPojoDao);
            
            //
            Map<String, Object> global = new HashMap<String, Object>();
            global.put("globalKey1", "globalValue1:abc");
            
            //
            List<ProcessRule> resList = ruleSessionTemplate.<ProcessRule> evaluateList("drools_test",
                    fact,
                    global);
            
            System.out.println(resList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
