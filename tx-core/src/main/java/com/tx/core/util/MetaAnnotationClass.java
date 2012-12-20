/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-15
 * <修改描述:>
 */
package com.tx.core.util;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.ibatis.reflection.MetaClass;
import org.springframework.beans.BeanUtils;

import com.tx.core.exceptions.parameter.ParameterIsEmptyException;

/**
 * 类声明属性工具类
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-15]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MetaAnnotationClass {
    
    /**
     * 缓存
     */
    private static Map<Class<?>, MetaAnnotationClass> cacheMap = new WeakHashMap<Class<?>, MetaAnnotationClass>();
    
    /**
      * 类属性工具类
      * <功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return MetaAnnotationClass [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static MetaAnnotationClass forClass(Class<?> type) {
        if (type == null) {
            throw new ParameterIsEmptyException(
                    "MetaAnnotationClass forClass parameter type is empty.");
        }
        if (cacheMap.containsKey(type)) {
            return cacheMap.get(type);
        }
        MetaAnnotationClass res = parseClass(type);
        
        cacheMap.put(type, res);
        return res;
    }
    
    /**
      * 解析类
      * <功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return MetaAnnotationClass [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static MetaAnnotationClass parseClass(Class<?> type) {
        MetaAnnotationClass res = new MetaAnnotationClass();
        
        MetaClass metaClass = MetaClass.forClass(type);
        res.setMetaClass(metaClass);
        
        String[] setterNames = metaClass.getGetterNames();
        //能够编辑的属性名
        //将具有set和get的属性名放一起
        List<String> canModifyPropertyNames = new ArrayList<String>();
        
        Map<String, Field> canModifyPropertyField = new HashMap<String, Field>();
        Map<String, Class<?>> canModifyPropertyFieldType = new HashMap<String, Class<?>>();
        Map<String, PropertyDescriptor> canModifyPropertyDescriptor = new HashMap<String, PropertyDescriptor>();
        
        Map<String, Method> canModifySetterMethod = new HashMap<String, Method>();
        Map<String, Method> canModifyGetterMethod = new HashMap<String, Method>();
        for (String setterNameTemp : setterNames) {
            if (metaClass.hasGetter(setterNameTemp)) {
                Class<?> setterType = metaClass.getSetterType(setterNameTemp);
                PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(type,
                        setterNameTemp);
                canModifyPropertyDescriptor.put(setterNameTemp,
                        propertyDescriptor);
                
                canModifyPropertyNames.add(setterNameTemp);
                canModifyPropertyField.put(setterNameTemp,
                        FieldUtils.getField(type, setterNameTemp, true));
                canModifyPropertyFieldType.put(setterNameTemp, setterType);
                
                canModifySetterMethod.put(setterNameTemp,
                        propertyDescriptor.getWriteMethod());
                canModifyGetterMethod.put(setterNameTemp,
                        propertyDescriptor.getReadMethod());
            }
        }
        res.setCanModifyPropertyNames(canModifyPropertyNames);
        res.setCanModifyGetterMethod(canModifyGetterMethod);
        res.setCanModifyPropertyField(canModifyPropertyField);
        res.setCanModifyPropertyDescriptor(canModifyPropertyDescriptor);
        res.setCanModifySetterMethod(canModifySetterMethod);
        
        return res;
    }
    
    /**
     * 能够标记的属性名
     */
    private List<String> canModifyPropertyNames;
    
    /**
     * 能够编辑的属性字段
     */
    private Map<String, Field> canModifyPropertyField;
    
    /**
     * 可编辑属性setter对应的属性类型
     */
    private Map<String, Class<?>> canModifyPropertyFieldType;
    
    /**
     * 能够编辑的属性descriptor
     */
    private Map<String, PropertyDescriptor> canModifyPropertyDescriptor;
    
    /**
     * 能够编辑的属性的setter方法
     */
    private Map<String, Method> canModifySetterMethod;
    
    /**
     * 能够编辑的属性的getter方法
     */
    private Map<String, Method> canModifyGetterMethod;
    
    private MetaClass metaClass;
    
    /**
     * <默认构造函数>
     */
    private MetaAnnotationClass() {
    }
    
    /**
      * 获取拥有指定注解的可编辑属性的集合
      * <功能详细描述>
      * @param annotationClass
      * @return [参数说明]
      * 
      * @return List<String> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<String> getHasAnnotationCanModifyPropertyNames(
            Class<? extends Annotation> annotationClass) {
        List<String> res = new ArrayList<String>();
        for (String canModifyPropertyNameTemp : this.canModifyPropertyNames) {
            if (this.getCanModifyPropertyField()
                    .get(canModifyPropertyNameTemp)
                    .isAnnotationPresent(annotationClass)
                    || this.getCanModifyGetterMethod()
                            .get(canModifyPropertyNameTemp)
                            .isAnnotationPresent(annotationClass)
                    || this.getCanModifySetterMethod()
                            .get(canModifyPropertyNameTemp)
                            .isAnnotationPresent(annotationClass)) {
                res.add(canModifyPropertyNameTemp);
            }
        }
        
        return res;
    }
    
    /**
      * 获取拥有指定注解,并且为指定类型的，的可编辑属性的集合<br/>
      * 
      *<功能详细描述>
      * @param annotationClass
      * @param type
      * @return [参数说明]
      * 
      * @return List<String> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<String> getHasAnnotationCanModifyPropertyNames(
            Class<? extends Annotation> annotationClass, Class<?> type) {
        List<String> res = new ArrayList<String>();
        for (String canModifyPropertyNameTemp : this.canModifyPropertyNames) {
            if (this.getCanModifyPropertyField()
                    .get(canModifyPropertyNameTemp)
                    .isAnnotationPresent(annotationClass)
                    || this.getCanModifyGetterMethod()
                            .get(canModifyPropertyNameTemp)
                            .isAnnotationPresent(annotationClass)
                    || this.getCanModifySetterMethod()
                            .get(canModifyPropertyNameTemp)
                            .isAnnotationPresent(annotationClass)
                    || this.getCanModifyPropertyFieldType()
                            .get(canModifyPropertyNameTemp)
                            .equals(type)) {
                res.add(canModifyPropertyNameTemp);
            }
        }
        
        return res;
    }
    
    /**
     * @return 返回 canModifyPropertyNames
     */
    public List<String> getCanModifyPropertyNames() {
        return canModifyPropertyNames;
    }
    
    /**
     * @param 对canModifyPropertyNames进行赋值
     */
    public void setCanModifyPropertyNames(List<String> canModifyPropertyNames) {
        this.canModifyPropertyNames = canModifyPropertyNames;
    }
    
    /**
     * @return 返回 metaClass
     */
    public MetaClass getMetaClass() {
        return metaClass;
    }
    
    /**
     * @param 对metaClass进行赋值
     */
    public void setMetaClass(MetaClass metaClass) {
        this.metaClass = metaClass;
    }
    
    /**
     * @return 返回 canModifyPropertyField
     */
    public Map<String, Field> getCanModifyPropertyField() {
        return canModifyPropertyField;
    }
    
    /**
     * @param 对canModifyPropertyField进行赋值
     */
    public void setCanModifyPropertyField(
            Map<String, Field> canModifyPropertyField) {
        this.canModifyPropertyField = canModifyPropertyField;
    }
    
    /**
     * @return 返回 canModifyPropertyDescriptor
     */
    public Map<String, PropertyDescriptor> getCanModifyPropertyDescriptor() {
        return canModifyPropertyDescriptor;
    }
    
    /**
     * @param 对canModifyPropertyDescriptor进行赋值
     */
    public void setCanModifyPropertyDescriptor(
            Map<String, PropertyDescriptor> canModifyPropertyDescriptor) {
        this.canModifyPropertyDescriptor = canModifyPropertyDescriptor;
    }
    
    /**
     * @return 返回 canModifySetterMethod
     */
    public Map<String, Method> getCanModifySetterMethod() {
        return canModifySetterMethod;
    }
    
    /**
     * @param 对canModifySetterMethod进行赋值
     */
    public void setCanModifySetterMethod(
            Map<String, Method> canModifySetterMethod) {
        this.canModifySetterMethod = canModifySetterMethod;
    }
    
    /**
     * @return 返回 canModifyGetterMethod
     */
    public Map<String, Method> getCanModifyGetterMethod() {
        return canModifyGetterMethod;
    }
    
    /**
     * @param 对canModifyGetterMethod进行赋值
     */
    public void setCanModifyGetterMethod(
            Map<String, Method> canModifyGetterMethod) {
        this.canModifyGetterMethod = canModifyGetterMethod;
    }
    
    /**
     * @return 返回 canModifyPropertyFieldType
     */
    public Map<String, Class<?>> getCanModifyPropertyFieldType() {
        return canModifyPropertyFieldType;
    }
    
    /**
     * @param 对canModifyPropertyFieldType进行赋值
     */
    public void setCanModifyPropertyFieldType(
            Map<String, Class<?>> canModifyPropertyFieldType) {
        this.canModifyPropertyFieldType = canModifyPropertyFieldType;
    }
}
