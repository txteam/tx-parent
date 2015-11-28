/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年5月12日
 * <修改描述:>
 */
package com.tx.core.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.codehaus.jettison.mapped.Configuration;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

/**
 * <功能简述> <功能详细描述>
 * 
 * @author Administrator
 * @version [版本号, 2015年5月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class JsonXstreamUtilsTest {
    
    public static class Test1 {
        private String t1;
        
        private List<Test2> t2;
        
        /**
         * @return 返回 t1
         */
        public String getT1() {
            return t1;
        }
        
        /**
         * @param 对t1进行赋值
         */
        public void setT1(String t1) {
            this.t1 = t1;
        }
        
        /**
         * @return 返回 t2
         */
        public List<Test2> getT2() {
            return t2;
        }
        
        /**
         * @param 对t2进行赋值
         */
        public void setT2(List<Test2> t2) {
            this.t2 = t2;
        }
        
    }
    
    public static class Test2 {
        private String t1;
        
        public Test2(String t1) {
            super();
            this.t1 = t1;
        }
        
        public Test2() {
            super();
            // TODO Auto-generated constructor stub
        }
        
        /**
         * @return 返回 t1
         */
        public String getT1() {
            return t1;
        }
        
        /**
         * @param 对t1进行赋值
         */
        public void setT1(String t1) {
            this.t1 = t1;
        }
    }
    
    public static void main(String[] args) {
        Configuration c = new Configuration();
        c.setDropRootElement(true);
        c.setPrimitiveArrayKeys(new HashSet<String>());
        //c.getPrimitiveArrayKeys().add("t2");
        //c.setEscapeForwardSlashAlways(false);
        XStream xsJson = new XStream(new JettisonMappedXmlDriver(c, true));
        //xsJson.setMode(XStream.);
        //        XStream xsJson = new XStream(new JsonHierarchicalStreamDriver() {
        //            public HierarchicalStreamWriter createWriter(Writer out) {
        //                return new JsonWriter(out, JsonWriter.DROP_ROOT_MODE);
        //            }
        //        });
        xsJson.processAnnotations(Test1.class);
        xsJson.processAnnotations(Test2.class);
        xsJson.addImplicitCollection(Test1.class, "t2");
        //        xsJson.alias("t1", String.class);
        Test1 t1 = new Test1();
        t1.setT1("t1test");
        t1.setT2(new ArrayList<Test2>());
        t1.getT2().add(new Test2("t1.t2-1"));
        t1.getT2().add(new Test2("t1.t2-2"));
        //t1.getT2().add(new Test2("t1.t2-2"));
        
        //        System.out.println(xsJson.toXML(t1));
        String xml = xsJson.toXML(t1);
        System.out.println(xml);
        
//        Object fromXML = xsJson.fromXML(xml);
//        ObjectUtils.debugPrintPropertyValue(System.err, "fromXML", fromXML, true, true);
    }
}
