package com.tx.core.ddlutil;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.tx.core.util.JdbcUtils;

/*
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2015年7月1日
 * <修改描述:>
 */

/**
 * <功能简述> <功能详细描述>
 * 
 * @author rain
 * @version [版本号, 2015年7月1日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Main {
    
    
    public static void main(String[] args) {
        System.out.println("Object : " + JdbcUtils.isSupportedSimpleType(Object.class));
        System.out.println("Class : " + JdbcUtils.isSupportedSimpleType(Class.class));
        
        System.out.println("short : " + JdbcUtils.isSupportedSimpleType(short.class));
        System.out.println("int : " + JdbcUtils.isSupportedSimpleType(int.class));
        System.out.println("long : " + JdbcUtils.isSupportedSimpleType(long.class));
        System.out.println("float : " + JdbcUtils.isSupportedSimpleType(float.class));
        System.out.println("double : " + JdbcUtils.isSupportedSimpleType(double.class));
        System.out.println("boolean : " + JdbcUtils.isSupportedSimpleType(boolean.class));
        System.out.println("char : " + JdbcUtils.isSupportedSimpleType(char.class));
        
        System.out.println("Short : " + JdbcUtils.isSupportedSimpleType(Short.class));
        System.out.println("Integer : " + JdbcUtils.isSupportedSimpleType(Integer.class));
        System.out.println("Long : " + JdbcUtils.isSupportedSimpleType(Long.class));
        System.out.println("Float : " + JdbcUtils.isSupportedSimpleType(Float.class));
        System.out.println("Double : " + JdbcUtils.isSupportedSimpleType(Double.class));
        System.out.println("Boolean : " + JdbcUtils.isSupportedSimpleType(Boolean.class));
        System.out.println("Character : " + JdbcUtils.isSupportedSimpleType(Character.class));
        
        System.out.println("java.sql.Date : " + JdbcUtils.isSupportedSimpleType(java.sql.Date.class));
        System.out.println("java.util.Date : " + JdbcUtils.isSupportedSimpleType(java.util.Date.class));
        System.out.println("Timestamp : " + JdbcUtils.isSupportedSimpleType(Timestamp.class));
        System.out.println("Time : " + JdbcUtils.isSupportedSimpleType(Time.class));
        
        System.out.println("BigDecimal : " + JdbcUtils.isSupportedSimpleType(BigDecimal.class));
        System.out.println("String : " + JdbcUtils.isSupportedSimpleType(String.class));
        
        System.out.println("Map : " + JdbcUtils.isSupportedSimpleType(Map.class));
        System.out.println("Set : " + JdbcUtils.isSupportedSimpleType(Set.class));
        System.out.println("List : " + JdbcUtils.isSupportedSimpleType(List.class));
        System.out.println("ArrayList : " + JdbcUtils.isSupportedSimpleType(ArrayList.class));
        System.out.println("HashMap : " + JdbcUtils.isSupportedSimpleType(HashMap.class));
        System.out.println("HashSet : " + JdbcUtils.isSupportedSimpleType(HashSet.class));
        
        TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();
        
        Field field1 = FieldUtils.getDeclaredField(DDLTestDemo.class, "test_String", true);
        System.out.println(field1);
        Field field11 = FieldUtils.getField(DDLTestDemo.class, "test_String", true);
        System.out.println(field11);
        
        Field field2 = FieldUtils.getDeclaredField(DDLTestDemo.class, "parentString", true);
        System.out.println(field2);
        Field field21 = FieldUtils.getField(DDLTestDemo.class, "parentString", true);
        System.out.println(field21);
    }
}
