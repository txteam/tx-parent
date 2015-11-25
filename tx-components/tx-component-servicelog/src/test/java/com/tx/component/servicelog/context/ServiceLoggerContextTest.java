/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月25日
 * 项目： tx-component-servicelog
 */
package com.tx.component.servicelog.context;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tx.component.servicelog.template.TXServiceLogDBScriptHelper;
import com.tx.component.servicelog.testmodel.LoginLog;
import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.paged.model.PagedList;
import com.tx.core.util.ObjectUtils;

/**
 * <功能简述><br />
 * <功能详细描述>
 * 
 * @author rain
 * @version [版本号, 2015年11月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/beans-aop.xml",
        "classpath:spring/beans-cache.xml",
        "classpath:spring/beans-ds.xml",
        "classpath:spring/beans-tx.xml",
        "classpath:spring/beans-servicelog.xml" })
public class ServiceLoggerContextTest {
    
    @Test
    public void testServiceLoggerContextInit() {
        System.out.println(1);
    }
    
    @Test
    public void testRecordServiceLog() {
        System.out.println(2);
        ServiceLoggerContext.getLogger(LoginLog.class).log(new LoginLog("0"));
        ServiceLoggerContext.getContext().getServiceLogger(LoginLog.class).log(new LoginLog("0"));
        
    }
    
    @Test
    public void testQueryServiceLog() {
        System.out.println(3);
        
        ServiceLoggerContext.getLogger(LoginLog.class).log(new LoginLog("3"));
        ServiceLoggerContext.getContext().getServiceLogger(LoginLog.class).log(new LoginLog("3"));
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("minCreateDate", DateUtils.addHours(new Date(), -1));
        params.put("maxCreateDate", DateUtils.addHours(new Date(), 1));
        PagedList<LoginLog> res = ServiceLoggerContext.getContext().getServiceLogger(LoginLog.class).queryPagedList(params, 1, 10);
        
        Assert.assertTrue(res != null && res.getList().size() > 0);
        
        LoginLog loginLog = ServiceLoggerContext.getLogger(LoginLog.class).find(res.getList().get(0).getId());
        
        ObjectUtils.debugPrintPropertyValue(System.err, "loginlog01", loginLog, true, true);
        
        int index = 3;
        for (LoginLog log : res.getList()) {
            ObjectUtils.debugPrintPropertyValue(System.err, "log0" + index++, log, true, true);
        }
        
        Assert.assertNotNull(loginLog);
    }
    
    public static void main(String[] args) {
        //        for(String getterNameTemp : ReflectionUtils.getGetterNames(LoginLog.class,
        //                false)){
        //            System.out.println(getterNameTemp);
        //        }
        
        String script = TXServiceLogDBScriptHelper.generateDBScriptContent(LoginLog.class,
                DataSourceTypeEnum.ORACLE,
                "GBK");
        System.out.println(script);
    }
    
}
