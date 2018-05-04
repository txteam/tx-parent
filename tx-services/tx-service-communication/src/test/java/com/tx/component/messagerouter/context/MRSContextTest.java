/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年12月17日
 * 项目： tx-component-messagerouter
 */
package com.tx.component.messagerouter.context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tx.component.communication.context.MessageSenderContext;

/**
 * 消息路由服务测试类
 * 
 * @author rain
 * @version [版本号, 2015年12月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/beans-servicelog.xml",
        "classpath:spring/beans-cache.xml", "classpath:spring/beans-ds.xml",
        "classpath:spring/beans-tx.xml",
        "classpath:spring/beans-communication.xml",
        "classpath:spring/beans.xml" })
@ActiveProfiles("production")
public class MRSContextTest {
    
    @Test
    public void test() throws InterruptedException {
        try {
            MessageSenderContext.getContext().send("sms",
                    "18983777587",
                    "企账通",
                    "您的验证码是2232。");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
