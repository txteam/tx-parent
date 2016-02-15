/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年12月17日
 * 项目： tx-component-messagerouter
 */
package com.tx.component.messagerouter.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.tx.component.communication.context.MRSResponse;
import com.tx.component.communication.context.MessageSenderContext;
import com.tx.component.communication.enums.MRSRequestSourceEnum;
import com.tx.component.communication.senddialect.sms.alidayu.AlidayuSMSRequest;
import com.tx.component.communication.senddialect.sms.alidayu.AlidayuSMSTemplate;

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
        "classpath:spring/beans.xml" })
@ActiveProfiles("production")
public class MRSContextTest {

    @Test
    public void test() throws InterruptedException {
        String smsFreeSignName = "登录验证";
        Map<String, String> smsParams = new HashMap<String, String>();
        smsParams.put("code", "'5201322140'");
        smsParams.put("product", "'亚馨信贷'");
        List<String> smsRecNums = new ArrayList<String>();
        smsRecNums.add("15223152423");
        
//        AlidayuSMSRequest request = new AlidayuSMSRequest(smsFreeSignName, smsParams, smsRecNums, AlidayuSMSTemplate.测试, MRSRequestSourceEnum.调试);
//        MRSResponse response = MessageSenderContext.getContext().post(request);
//        Object body = response.getBody();
//        AlibabaAliqinFcSmsNumSendResponse aliResponse = (AlibabaAliqinFcSmsNumSendResponse) body;
//        System.out.println(aliResponse.getBody());
        
        //        for (int i = 0; i < 10; i++) {
        //            new Thread(new Runnable() {
        //                public void run() {
        //                    TxLoaclFileServiceLog log = new TxLoaclFileServiceLog();
        //                    log.setId(UUIDUtils.generateUUID16());
        //                    log.setClientIpAddress("192.168.1.1");
        //                    log.setCreateDate(new Date());
        //                    log.setId(UUIDUtils.generateUUID());
        //                    log.setMessage("test!");
        //                    log.setModule("test");
        //                    log.setResponseCode("200");
        //                    for(;;){
        //                        ServiceLoggerContext.getLogger(TxLoaclFileServiceLog.class).log(log);
        //                        System.out.println("执行线程!!!"); 
        //                    }
        //                }
        //            }).start();
        //        }
        //        
        //        System.out.println("线程结束");
        //        Thread.sleep(1000000000l);
    }
    
    
}
