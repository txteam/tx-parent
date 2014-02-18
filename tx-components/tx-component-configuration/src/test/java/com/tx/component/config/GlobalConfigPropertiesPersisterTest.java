/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-2-17
 * <修改描述:>
 */
package com.tx.component.config;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tx.component.configuration.context.ConfigContext;
import com.tx.component.configuration.context.ConfigContextFactory;
import com.tx.component.configuration.persister.ConfigPropertiesPersister;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2014-2-17]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
        "classpath:spring/beans.xml",
        "classpath:spring/beans-ds.xml",
        "classpath:spring/beans-config.xml" })
public class GlobalConfigPropertiesPersisterTest {
    
    @Resource(name="configContext")
    private ConfigContext configContext;
    
    @Test
    public void testInitContext() throws Exception{
        //
    }
}
