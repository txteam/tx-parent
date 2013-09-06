/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-2
 * <修改描述:>
 */
package com.tx.core.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import com.tx.core.exceptions.argument.NullArgException;
import com.tx.core.reflection.exception.ReflectionException;

/**
 * 类反射器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ClassReflector {
    
    /**
     * 缓存
     */
    private static WeakHashMap<Class<?>, ClassReflector> cacheMap = new WeakHashMap<Class<?>, ClassReflector>();
    
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
    public static ClassReflector forClass(Class<?> type) {
        if (type == null) {
            throw new NullArgException(
                    "MetaAnnotationClass forClass parameter type is empty.");
        }
        synchronized (type) {
            if (cacheMap.containsKey(type)) {
                return cacheMap.get(type);
            }
            ClassReflector res = new ClassReflector(type);
            
            cacheMap.put(type, res);
            
            return res;
        }
    }
    
    /**
      * 获取类型中指定名称的方法<br/>
      *<功能详细描述>
      * @param methodName
      * @param parameterTypes
      * @return [参数说明]
      * 
      * @return Method [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private Method getMethod(String methodName, Class<?>... parameterTypes) {
        try {
            return type.getMethod(methodName, parameterTypes);
        } catch (SecurityException e) {
            throw new ReflectionException(
                    MessageFormatter.arrayFormat("type:{} methodName:{} access error:SecurityException:{}",
                            new Object[] { type, methodName, e })
                            .getMessage(), e);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
    
    /** <默认构造函数> */
    private ClassReflector(Class<?> type) {
        super();
        this.type = type;
        
        Class<?> searchType = type;
        while (!Object.class.equals(searchType) && searchType != null) {
            
            Field[] fields = searchType.getDeclaredFields();
            for (Field fieldTemp : fields) {
                String fieldName = fieldTemp.getName();
                Class<?> fieldType = fieldTemp.getType();
                String capitalizeFieldName = StringUtils.capitalize(fieldName);
                
                //如果子类已经存在的字段则认为该字段已经被覆写以子类为准
                if(propertyFields.containsKey(fieldName)){
                    continue;
                }
                
                propertyFields.put(fieldName, fieldTemp);
                
                Method getterMethod = null;
                if (boolean.class.isAssignableFrom(fieldTemp.getType())) {
                    getterMethod = getMethod("is" + capitalizeFieldName);
                    if (getterMethod == null
                            && StringUtils.startsWithIgnoreCase(fieldName, "is")) {
                        getterMethod = getMethod(fieldName);
                    }
                } else {
                    getterMethod = getMethod("get" + capitalizeFieldName);
                }
                propertyGetterMethods.put(fieldName, getterMethod);
                
                Method setterMethod = null;
                if (boolean.class.isAssignableFrom(fieldTemp.getType())) {
                    getterMethod = getMethod("set" + capitalizeFieldName,
                            boolean.class);
                    if (getterMethod == null
                            && StringUtils.startsWithIgnoreCase(fieldName, "is")) {
                        getterMethod = getMethod("set" + StringUtils.substringAfter(fieldName, "is"),boolean.class);
                    }
                } else {
                    getterMethod = getMethod("set" + capitalizeFieldName,fieldType);
                }
                propertySetterMethods.put(fieldName, setterMethod);
            }
            searchType = searchType.getSuperclass();
        }
    }
    
    private Class<?> type;
    
    private Map<String, Field> propertyFields = new HashMap<String, Field>();
    
    private Map<String, Method> propertyGetterMethods = new HashMap<String, Method>();
    
    private Map<String, Method> propertySetterMethods = new HashMap<String, Method>();
    
    /**
      * 获取类反射器类型名<br/> 
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return Class<?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Class<?> getType() {
        return this.type;
    }
    
    /**
      * 获取指定属性名的Set方法<br/> 
      *<功能详细描述>
      * @param propertyName
      * @return [参数说明]
      * 
      * @return Invoker [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Field getFiled(String propertyName) {
        return this.propertyFields.get(propertyName);
    }
    
    /**
      * 获取指定属性的getter方法<br/>
      *     可能为空 
      *<功能详细描述>
      * @param propertyName
      * @return [参数说明]
      * 
      * @return Method [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Method getGetterMethod(String propertyName) {
        return this.propertyGetterMethods.get(propertyName);
    }
    
    /**
      * 获取指定属性的setter方法<br/>
      *     可能为空 
      *<功能详细描述>
      * @param propertyName
      * @return [参数说明]
      * 
      * @return Method [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Method getSetterMethod(String propertyName) {
        return this.propertySetterMethods.get(propertyName);
    }
}
