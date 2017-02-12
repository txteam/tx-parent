/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-4
 * <修改描述:>
 */
package com.tx.component.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tx.component.auth.context.AuthContext;

import com.tx.core.exceptions.util.AssertUtils;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-4]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
        "classpath:spring/beans-aop.xml",
        "classpath:spring/beans-cache.xml", 
        "classpath:spring/beans-ds.xml",
        "classpath:spring/beans-tx.xml", 
        "classpath:spring/beans-auth.xml" })
public class AuthContextInitTest {
    
    @Test
    public void test(){
        AssertUtils.notNull(AuthContext.getContext(),"");
    }
}
