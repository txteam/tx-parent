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
        
    }
    
    
}
