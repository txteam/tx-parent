/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年12月17日
 * 项目： tx-component-messagerouter
 */
package com.tx.component.messagerouter.context.alidayu;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tx.component.communication.context.MessageSenderContext;
import com.tx.component.communication.model.SendResult;

/**
 * 消息路由服务测试类
 * 
 * @author rain
 * @version [版本号, 2015年12月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/beans.xml",
        "classpath:spring/beans-ds.xml",
        "classpath:spring/beans-tx.xml",
        "classpath:com/tx/component/messagerouter/context/alidayu/beans-communication.xml" })
@ActiveProfiles("production")
public class SMSOfAlidayuContextTest {
    
    @Test
    public void test() throws InterruptedException {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("code", String.valueOf((int) (Math.random() * 1000)));
            params.put("product", "添馨网络科技有限公司");
            SendResult result = MessageSenderContext.getContext().send("sms_code",
                    "18983379637",
                    "企账通",
                    "SMS_7310912",
                    params);
            if (result.isSuccess()) {
                System.out.println("success.");
            } else {
                System.out.println("errorCode:" + result.getErrorCode()
                        + " errorMessage:" + result.getErrorMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
