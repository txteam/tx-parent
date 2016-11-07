/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.mybatis;

import java.util.List;

import javax.persistence.Table;

import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.annotations.common.reflection.java.JavaReflectionManager;
import org.hibernate.cfg.AccessType;

import com.tx.core.mybatis.data.Demo;
import com.tx.core.mybatis.data.DemoTreeNode;


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
    
    public static void main(String[] args) {
        XClass xclass = javaReflectionManager.toXClass(DemoTreeNode.class);
        
        DemoTreeNode.class.getAnnotation(Table.class);
        Table t = xclass.getAnnotation(Table.class);
        System.out.println(t);
        
        for(XProperty xproperty : xclass.getDeclaredProperties( AccessType.FIELD.getType())){
            System.out.println(xproperty);
        }
    }
}
