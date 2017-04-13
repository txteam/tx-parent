///*
// * 描述： <描述>
// * 修改人： rain
// * 修改时间： 2015年11月25日
// * 项目： tx-component-servicelog
// */
//package com.tx.component.servicelog.context;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.lang.time.DateUtils;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.tx.component.servicelog.logger.TxLoaclFileServiceLog;
//import com.tx.component.servicelog.template.TXServiceLogDBScriptHelper;
//import com.tx.component.servicelog.testmodel.LoginLog;
//import com.tx.core.dbscript.model.DataSourceTypeEnum;
//import com.tx.core.paged.model.PagedList;
//import com.tx.core.util.ObjectUtils;
//import com.tx.core.util.RandomUtils;
//import com.tx.core.util.UUIDUtils;
//
///**
// * 
// * @author rain
// * @version [版本号, 2015年11月25日]
// * @see [相关类/方法]
// * @since [产品/模块版本]
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {
//        "classpath:spring/beans-aop.xml",
//        "classpath:spring/beans-cache.xml",
//        "classpath:spring/beans-ds.xml",
//        "classpath:spring/beans-tx.xml",
//        "classpath:spring/beans-servicelog.xml" })
//public class ServiceLoggerContextTest {
//    
//    class BB extends TxLoaclFileServiceLog {
//        private String abce;
//        
//        /** @return 返回 abce */
//        public String getAbce() {
//            return abce;
//        }
//        
//        /** @param 对 abce 进行赋值 */
//        public void setAbce(String abce) {
//            this.abce = abce;
//        }
//        
//    }
//    
//    @Test
//    public void testTxLocalFileServiceLog() {
//        System.out.println("testTxLocalFileServiceLog");
//        BB log = new BB();
//        log.setAbce("abce");
//        log.setClientIpAddress("clientIpAddress");
//        log.setCreateDate(new Date());
//        log.setId(UUIDUtils.generateUUID());
//        log.setMessage(RandomUtils.randomChineseCharacter(10));
//        log.setMessageid(UUIDUtils.generateUUID16());
//        log.setModule("servicelog-test");
//        log.setOperatorId("operatorId");
//        log.setOperatorLoginName("operatorLoginName");
//        log.setOperatorName("operatorName");
//        log.setOrganizationId("organizationId");
//        log.putOtherParam("other", "abcd1234");
//        log.setRemark("remark" + RandomUtils.randomRangeString(50));
//        log.setRequestBody("requestBody");
//        log.setResponseBody("responseBody");
//        log.setResponseCode("200");
//        log.setResponseCodeMessage("成功");
//        log.setUseTime(RandomUtils.randomRangeString("123456789", 10));
//        log.setVcid("vcid");
//        log.setVersion("version");
//        
//        ServiceLoggerContext.getLogger(BB.class).log(log);
//        ServiceLoggerContext.getContext().getServiceLogger(BB.class).log(log);
//        
//        //        Map<String, Object> params = new HashMap<String, Object>();
//        //        params.put("minCreateDate", DateUtils.addHours(new Date(), -1));
//        //        params.put("maxCreateDate", DateUtils.addHours(new Date(), 1));
//        //        PagedList<TxLoaclFileServiceLog> res = ServiceLoggerContext.getContext().getServiceLogger(TxLoaclFileServiceLog.class).queryPagedList(params, 1, 10);
//        //        
//        //        Assert.assertTrue(res != null && res.getList().size() > 0);
//    }
//    
//    @Test
//    public void testTXBaseServiceLog() {
//        System.out.println("testTXBaseServiceLog");
//        ServiceLoggerContext.getLogger(LoginLog.class).log(new LoginLog("3"));
//        ServiceLoggerContext.getContext().getServiceLogger(LoginLog.class).log(new LoginLog("3"));
//        
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("minCreateDate", DateUtils.addHours(new Date(), -1));
//        params.put("maxCreateDate", DateUtils.addHours(new Date(), 1));
//        PagedList<LoginLog> res = ServiceLoggerContext.getContext().getServiceLogger(LoginLog.class).queryPagedList(params, 1, 10);
//        
//        Assert.assertTrue(res != null && res.getList().size() > 0);
//        
//        LoginLog loginLog = ServiceLoggerContext.getLogger(LoginLog.class).find(res.getList().get(0).getId());
//        
//        ObjectUtils.debugPrintPropertyValue(System.err, "loginlog01", loginLog, true, true);
//        
//        int index = 0;
//        for (LoginLog log : res.getList()) {
//            ObjectUtils.debugPrintPropertyValue(System.err, "log0" + index++, log, true, true);
//        }
//        
//        Assert.assertNotNull(loginLog);
//    }
//    
//    public static void main(String[] args) {
//        //        for(String getterNameTemp : ReflectionUtils.getGetterNames(LoginLog.class,
//        //                false)){
//        //            System.out.println(getterNameTemp);
//        //        }
//        
//        String script = TXServiceLogDBScriptHelper.generateDBScriptContent(LoginLog.class,
//                DataSourceTypeEnum.ORACLE,
//                "GBK");
//        System.out.println(script);
//    }
//    
//}
