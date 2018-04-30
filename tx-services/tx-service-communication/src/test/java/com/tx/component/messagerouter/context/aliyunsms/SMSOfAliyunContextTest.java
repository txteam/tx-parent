/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年12月17日
 * 项目： tx-component-messagerouter
 */
package com.tx.component.messagerouter.context.aliyunsms;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tx.component.communication.context.MessageSenderContext;
import com.tx.component.communication.model.SendResult;
import com.tx.core.util.MessageUtils;

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
        "classpath:com/tx/component/messagerouter/context/aliyunsms/beans-communication.xml" })
@ActiveProfiles("production")
public class SMSOfAliyunContextTest {
    
    @Test
    public void test() throws InterruptedException {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("code", String.valueOf((int) (Math.random() * 1000)));
            SendResult result = MessageSenderContext.getContext().send("sms_code",
                    "15998918907",
                    "企账通",
                    "SMS_14246689",
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
        
                try {
                    SendResult result = MessageSenderContext.getContext()
                            .send("sms",
                                    "15998918907",
                                    "企账通",
                                    MessageUtils.format("您的验证码是{}。",
                                            new Object[] { String.valueOf((int) (Math.random() * 1000)) }));
                    
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
