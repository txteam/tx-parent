/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-23
 * <修改描述:>
 */
package com.tx.component.servicelog;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.MetaObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tx.component.servicelog.context.ServiceLoggerContext;
import com.tx.component.servicelog.defaultimpl.TXServiceLogDBScriptHelper;
import com.tx.component.servicelog.testmodel.LoginLog;
import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.reflection.ReflectionUtils;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/beans-aop.xml",
        "classpath:spring/beans-cache.xml", "classpath:spring/beans-ds.xml",
        "classpath:spring/beans-tx.xml",
        "classpath:spring/beans-servicelog.xml" })
public class ServiceLoggerContextTest {
    
    @Test
    public void testServiceLoggerContextInit() {
    }
    
    @Test
    public void testRecordServiceLog(){
        ServiceLoggerContext.getLogger(LoginLog.class).log(new LoginLog("0"));
    }
    
    @Test
    public void testQueryServiceLog(){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("minCreateDate", DateUtils.addHours(new Date(), -1));
        params.put("maxCreateDate", DateUtils.addHours(new Date(), 1));
        PagedList<LoginLog> res = ServiceLoggerContext.getLogger(LoginLog.class).queryPagedList(params, 1, 10);
        
        
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
