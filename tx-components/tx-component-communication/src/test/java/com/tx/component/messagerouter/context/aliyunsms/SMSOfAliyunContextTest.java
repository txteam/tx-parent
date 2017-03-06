/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年12月17日
 * 项目： tx-component-messagerouter
 */
package com.tx.component.messagerouter.context.aliyunsms;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
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
        "classpath:spring/beans-servicelog.xml",
        "classpath:spring/beans-cache.xml",
        "classpath:spring/beans-ds.xml",
        "classpath:spring/beans-tx.xml",
        "classpath:com/tx/component/messagerouter/context/aliyunsms/beans-communication.xml",
        "classpath:spring/beans.xml" })
@ActiveProfiles("production")
public class SMSOfAliyunContextTest {
    
    @Test
    public void test() throws InterruptedException {
        try {
            for (int i = 0; i < 1; i++) {
                SendResult sr = MessageSenderContext.getContext()
                        .send("sms",
                                "15998918907",
                                "企账通",
                                MessageUtils.format("您于{}发起的入金操作，金额为：{}元，已成功到账。",
                                        new Object[] {
                                                DateTime.now()
                                                        .toString("yyyy-MM-dd HH:mm:ss"),
                                                String.valueOf((int) (Math.random() * 1000)) }));
                if(!sr.isSuccess()){
                    System.out.println(sr.getErrorMessage());
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
