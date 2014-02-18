///*
// * 描          述:  <描述>
// * 修  改   人:  wanxin
// * 修改时间:  2013-11-20
// * <修改描述:>
// */
//package com.tx.core.support.jsoup;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.util.List;
//
//import org.jsoup.nodes.Document;
//import org.junit.Test;
//
//import com.tx.los.creditreport.entity.BasicPersonalInfo;
//import com.tx.los.creditreport.entity.CareerInfo;
//import com.tx.los.creditreport.entity.CreditReportInstance;
//import com.tx.los.creditreport.entity.CreditTips;
//import com.tx.los.creditreport.entity.LivingInfo;
//import com.tx.los.creditreport.entity.NotCanceledCreditCardInfo;
//import com.tx.los.creditreport.entity.OverdueInfo;
//import com.tx.los.creditreport.entity.PensionInsuranceRecord;
//import com.tx.los.creditreport.entity.SpouseInfo;
//import com.tx.los.creditreport.rowMapper.BasicPersonalInfoRowMapper;
//import com.tx.los.creditreport.rowMapper.CareerInfoRowMapper;
//import com.tx.los.creditreport.rowMapper.CreditReportInstanceRowMapper;
//import com.tx.los.creditreport.rowMapper.CreditTipsRowMapper;
//import com.tx.los.creditreport.rowMapper.LivingInfoRowMapper;
//import com.tx.los.creditreport.rowMapper.NotCanceledCreditCardInfoRowMapper;
//import com.tx.los.creditreport.rowMapper.OverdueInfoRowMapper;
//import com.tx.los.creditreport.rowMapper.PensionInsuranceRecordRowMapper;
//import com.tx.los.creditreport.rowMapper.SpouseInfoRowMapper;
//
///**
// * <功能简述>
// * <功能详细描述>
// * 
// * @author  wanxin
// * @version  [版本号, 2013-11-20]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class JsoupTemplateTest {
//    
//    private static JsoupSupport jsoupSupport = new DefaultJsoupSupport();
//    
//    static Document doc = null;
//    static {
//        try {
//            URL u = 
//                    Class.class.getResource("/com/tx/core/support/jsoup/file/data.html");
//            File f = new File(u.toURI());
//            doc = JsoupUtils.parse(f);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }
//    
//    @Test
//    public void test01() throws Exception {
//        //个人基本信息
//        BasicPersonalInfo bpi = jsoupSupport.evaluateForObject(doc,
//                new BasicPersonalInfoRowMapper());
//        
//        System.out.println(bpi);
//        
//        //持久化crtIns;
//    }
//    
//    @Test
//    public void test02() throws Exception {
//        //个人征信报告实例
//        CreditReportInstance bpi = jsoupSupport.evaluateForObject(doc,
//                new CreditReportInstanceRowMapper());
//        
//        System.out.println(bpi);
//        
//        //持久化crtIns;
//    }
//    
////    @Test
////    public void test03() throws Exception {
////        //配偶信息
////        List<SpouseInfo> list = jsoupSupport.evaluateForList(doc,
////                new SpouseInfoRowMapper());
////        
////        System.out.println(list.size());
////        //持久化crtIns;
////    }
////    
////    @Test
////    public void test04() throws Exception {
////        //居住信息实体
////        List<LivingInfo> list = jsoupSupport.evaluateForList(doc,
////                new LivingInfoRowMapper());
////        
////        System.out.println("列表数量" + list.size());
////        for (LivingInfo li : list) {
////            System.out.println(li);
////        }
////        ;
////        //持久化crtIns;
////    }
////    
////    @Test
////    public void test05() throws Exception {
////        //职业信息
////        List<CareerInfo> list = jsoupSupport.evaluateForList(doc,
////                new CareerInfoRowMapper());
////        
////        System.out.println("列表数量" + list.size());
////        for (CareerInfo li : list) {
////            System.out.println(li);
////        }
////        ;
////        //持久化crtIns;
////    }
//    
//    @Test
//    public void test06() throws Exception {
//        //信用提示
//        CreditTips res = jsoupSupport.evaluateForObject(doc,
//                new CreditTipsRowMapper());
//        
//        System.out.println(res);
//        //持久化crtIns;
//    }
//    
//    @Test
//    public void test07() throws Exception {
//        //逾期（透支）信息汇总
//        OverdueInfo res = jsoupSupport.evaluateForObject(doc,
//                new OverdueInfoRowMapper());
//        
//        System.out.println(res);
//        //持久化crtIns;
//    }
//    
//    @Test
//    public void test08() throws Exception {
//        //未销户贷记卡信息汇总 
//        NotCanceledCreditCardInfo res = jsoupSupport.evaluateForObject(doc,
//                new NotCanceledCreditCardInfoRowMapper());
//        
//        System.out.println(res);
//        //持久化crtIns;
//    }
//    
//    @Test
//    public void test09() throws Exception {
//        //养老保险金缴存记录
//        PensionInsuranceRecord res = jsoupSupport.evaluateForObject(doc,
//                new PensionInsuranceRecordRowMapper());
//        
//        System.out.println(res);
//        //持久化crtIns;
//    }
//    
//    
//}
