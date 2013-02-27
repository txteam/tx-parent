/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-27
 * <修改描述:>
 */
package com.tx.component.rule.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tx.component.rule.context.RuleContext;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-2-27]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
        "classpath:spring/beans-ds.xml",
        "classpath:spring/beans-tx.xml",
        "classpath:spring/beans-cache.xml",
        "classpath:spring/beans.xml" })
@ActiveProfiles("dev")
public class RuleContextTest {
    
    @Resource(name="ruleContext")
    private RuleContext ruleContext;
    
    @Test
    public void testRuleConstants(){
        try {
            int size = this.ruleContext.size();
            System.out.println(size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
