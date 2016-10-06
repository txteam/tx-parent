/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月7日
 * <修改描述:>
 */
package com.tx.component.basicdata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tx.component.basicdata.context.BasicDataContext;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2016年10月7日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
        "classpath:spring/beans-basicdata.xml",
        "classpath:spring/beans-aop.xml",
        "classpath:spring/beans-cache.xml",
        "classpath:spring/beans-config.xml",
        "classpath:spring/beans-ds.xml",
        "classpath:spring/beans-tx.xml", 
        "classpath:com/tx/component/basicdata/beans.xml"})
public class BasicDataContextTest {
    
    @Test
    public void test(){
        BasicDataContext.getContext();
    }
}
