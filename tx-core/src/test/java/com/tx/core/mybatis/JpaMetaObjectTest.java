/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.mybatis;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.reflection.MetaObject;
import org.hibernate.annotations.common.reflection.java.JavaReflectionManager;

import com.tx.core.mybatis.data.Demo;
import com.tx.core.mybatis.data.TestDemo;
import com.tx.core.util.MetaObjectUtils;


/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-9]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JpaMetaObjectTest {
    
    protected static JavaReflectionManager javaReflectionManager = new JavaReflectionManager();
    
//    public static void main(String[] args) throws Exception{
//        JpaEntityFreeMarkerGenerator g = new JpaEntityFreeMarkerGenerator();
//        
//        g.generate(Demo.class, "d:/mybatis");
//    }
    
//    public static void main(String[] args) {
//        XClass xclass = javaReflectionManager.toXClass(DemoTreeNode.class);
//        
//        DemoTreeNode.class.getAnnotation(Table.class);
//        Table t = xclass.getAnnotation(Table.class);
//        System.out.println(t);
//        
//        for(XProperty xproperty : xclass.getDeclaredProperties(XClass.ACCESS_PROPERTY)){
//            System.out.println(xproperty);
//        }
//    }
    
    public static void main(String[] args) {
        TestDemo td1 = new TestDemo();
        td1.setDemo(new Demo());
        td1.getDemo().setId("testId1");
        
        MetaObject mo1 = MetaObjectUtils.forObject(td1);
        System.out.println(mo1.getValue("demo.id"));
        
        Map<String, Object> md = new HashMap<String, Object>();
        Demo mdd = new Demo();
        mdd.setId("testId2");
        md.put("demo", mdd);
        MetaObject mo2 = MetaObjectUtils.forObject(md);
        System.out.println(mo2.getValue("demo.id"));
        
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("demo.id", "testId3");
        hm.put("demo_id", "testId3");
        MetaObject mo3 = MetaObjectUtils.forObject(hm);
        System.out.println(mo3.getValue("demo.id"));
        System.out.println(mo3.getValue("demo_id"));
    }
}
