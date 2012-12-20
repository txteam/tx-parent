///*
// * 描          述:  <描述>
// * 修  改   人:  PengQingyang
// * 修改时间:  2012-12-9
// * <修改描述:>
// */
//package com.tx.core.mybatis;
//
//import java.lang.reflect.Method;
//
//import org.apache.commons.beanutils.PropertyUtils;
//import org.apache.ibatis.reflection.MetaClass;
//import org.springframework.beans.BeanUtils;
//import org.springframework.util.StringUtils;
//
//import com.tx.core.mybatis.data.Demo;
//import com.tx.core.mybatis.data.DemoTreeNode;
//import com.tx.core.mybatis.generator.JpaEntityFreeMarkerGenerator;
//
///**
// * <功能简述>
// * <功能详细描述>
// * 
// * @author  PengQingyang
// * @version  [版本号, 2012-12-9]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class JpaMetaObjectTest {
//    
//    public static void main(String[] args) throws Exception{
//        JpaEntityFreeMarkerGenerator g = new JpaEntityFreeMarkerGenerator();
//        
//        g.generate(Demo.class, "d:/mybatis");
//    }
//    
//}
