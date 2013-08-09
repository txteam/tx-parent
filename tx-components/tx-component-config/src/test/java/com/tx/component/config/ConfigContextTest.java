/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-8-8
 * <修改描述:>
 */
package com.tx.component.config;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  wanxin
  * @version  [版本号, 2013-8-8]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
        "classpath:spring/beans-ds.xml",
        "classpath:spring/beans-config.xml" })
@ActiveProfiles("production")
public class ConfigContextTest {
    
    @BeforeClass
    public static void setUp() {
        TestBase.bindDsToJNDI();
    }
    
    @Test
    public void testInitContext(){
        System.out.println("success");
    }
}
