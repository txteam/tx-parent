/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-11-5
 * <修改描述:>
 */
package com.tx.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <反射工具类封装用于访问或改写类的私有属性值>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-11-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ReflectionUtils {
    
    private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);
    
    /**
      *<获取对应字段值>
      *<功能详细描述>
      * @param object
      * @param fieldName
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static Object getFieldValue(Object object, String fieldName) {
        //获取对象对应字段
        Field field = org.springframework.util.ReflectionUtils.findField(object.getClass(),
                fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + object + "]");
        }
        
        //如果该字段不可被访问，则修改其访问性
        makeAccessible(field);
        
        Object result = null;
        try {
            result = field.get(object);
        }
        catch (IllegalAccessException e) {
            logger.error("Could not access field [" + fieldName
                    + "] on target [" + object + "]", e);
            throw new IllegalArgumentException("Could not access field ["
                    + fieldName + "] on target [" + object + "]");
        }
        return result;
    }
    
    /**
      *<设置对应字段值>
      *<功能详细描述>
      * @param object
      * @param fieldName
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void setFieldValue(Object object, String fieldName,
            Object value) {
        //获取对象对应字段
        Field field = org.springframework.util.ReflectionUtils.findField(object.getClass(),
                fieldName);
        if (field == null)
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + object + "]");
        //如果该字段不可被访问，则修改其访问性
        makeAccessible(field);
        
        try {
            field.set(object, value);
        }
        catch (IllegalAccessException e) {
            logger.error("Could not access field [" + fieldName
                    + "] on target [" + object + "]", e);
            throw new IllegalArgumentException("Could not access field ["
                    + fieldName + "] on target [" + object + "]");
        }
    }
    
    /**
      *<使对应字段可访问>
      *<功能详细描述>
      * @param field [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true);
        }
    }
    
}
