package com.tx.core.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.tx.core.util.XstreamUtils;

/**
 * Xstream工具类封装测试
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-4]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class XstreamUtilsTest extends TestCase {
    private static XStream testBeanXstream = XstreamUtils.getXstream(XstreamUtilsTest.TestBean.class);
    
    private static String tt1 = "<TestBeanRoot testAttr=\"attr\">"
            + "<testEl1>el1</testEl1>"
            + "<test_Rename_El2>el2</test_Rename_El2>"
            + "<itemName>listItem1</itemName>"
            + "<itemName>listItem2</itemName>" + "<overEl2>listItem2</overEl2>"
            + "<overEl1>listItem2</overEl1>" + "</TestBeanRoot>";
    
    /**
     * 常用举例
     * @param args [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void main(String[] args) throws Exception {
        XstreamUtilsTest.TestBean tt = createTestBean();
        
        System.out.println(testBeanXstream.toXML(tt));
        
        System.out.println(((XstreamUtilsTest.TestBean) testBeanXstream.fromXML(testBeanXstream.toXML(tt))).getTestEl1());
        
        System.out.println("-------------------------------");
        
        System.out.println(((XstreamUtilsTest.TestBean) testBeanXstream.fromXML(tt1)).getTestEl1());
        
    }
    
    /** 
     *<功能简述>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return XstreamUtilsTest.TestBean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static XstreamUtilsTest.TestBean createTestBean() {
        XstreamUtilsTest.TestBean tt = new XstreamUtilsTest.TestBean();
        
        tt.setTestAttr("attr");
        tt.setTestEl1("el1");
        tt.setTestEl2("el2");
        tt.setTestList(new ArrayList<String>());
        tt.getTestList().add("listItem1");
        tt.getTestList().add("listItem2");
        tt.setTestSkip("skip");
        return tt;
    }
    
    @XStreamAlias("TestBeanRoot")
    static class TestBean {
        
        private String testEl1;
        
        @XStreamAlias("test_Rename_El2")
        private String testEl2;
        
        @XStreamAsAttribute
        private String testAttr;
        
        @XStreamOmitField
        private String testSkip;
        
        @XStreamImplicit(itemFieldName = "itemName", keyFieldName = "keyName")
        private List<String> testList;
        
        /**
         * @return 返回 testEl1
         */
        public String getTestEl1() {
            return testEl1;
        }
        
        /**
         * @param 对testEl1进行赋值
         */
        public void setTestEl1(String testEl1) {
            this.testEl1 = testEl1;
        }
        
        /**
         * @return 返回 testEl2
         */
        public String getTestEl2() {
            return testEl2;
        }
        
        /**
         * @param 对testEl2进行赋值
         */
        public void setTestEl2(String testEl2) {
            this.testEl2 = testEl2;
        }
        
        /**
         * @return 返回 testAttr
         */
        public String getTestAttr() {
            return testAttr;
        }
        
        /**
         * @param 对testAttr进行赋值
         */
        public void setTestAttr(String testAttr) {
            this.testAttr = testAttr;
        }
        
        /**
         * @return 返回 testSkip
         */
        public String getTestSkip() {
            return testSkip;
        }
        
        /**
         * @param 对testSkip进行赋值
         */
        public void setTestSkip(String testSkip) {
            this.testSkip = testSkip;
        }
        
        /**
         * @return 返回 testList
         */
        public List<String> getTestList() {
            return testList;
        }
        
        /**
         * @param 对testList进行赋值
         */
        public void setTestList(List<String> testList) {
            this.testList = testList;
        }
    }
    
}
