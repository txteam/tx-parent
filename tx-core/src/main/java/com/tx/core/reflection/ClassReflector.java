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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import com.tx.core.exceptions.argument.NullArgException;

/**
 * 类反射器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ClassReflector<T> {
    
    /**
     * 缓存
     */
    private static WeakHashMap<Class<?>, ClassReflector<?>> cacheMap = new WeakHashMap<Class<?>, ClassReflector<?>>();
    
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
    @SuppressWarnings("unchecked")
    public static <TYPE> ClassReflector<TYPE> forClass(Class<TYPE> type) {
        if (type == null) {
            throw new NullArgException(
                    "MetaAnnotationClass forClass parameter type is empty.");
        }
        synchronized (type) {
            if (cacheMap.containsKey(type)) {
                return (ClassReflector<TYPE>)cacheMap.get(type);
            }
            ClassReflector<TYPE> res = new ClassReflector<TYPE>(type);
            
            cacheMap.put(type, res);
            return res;
        }
    }
    
    /** <默认构造函数> */
    private ClassReflector(Class<T> type) {
        super();
        this.type = type;
        
        Class<?> searchType = type;
        while (!Object.class.equals(searchType) && searchType != null) {
            
            Field[] fields = searchType.getDeclaredFields();
            for (Field fieldTemp : fields) {
                String fieldName = fieldTemp.getName();
                
                //如果子类已经存在的字段则认为该字段已经被覆写以子类为准
                if (this.propertyFields.containsKey(fieldName)) {
                    continue;
                }
                
                this.propertyFields.put(fieldName, fieldTemp);
            }
            
            Method[] methods = searchType.getMethods();
            for (Method methodTemp : methods) {
                if (ReflectionUtils.isGetterMethod(methodTemp)) {
                    String getterNameTemp = ReflectionUtils.getGetterNameByMethod(methodTemp);
                    if (!this.getterNames.contains(getterNameTemp)) {
                        this.getterNames.add(getterNameTemp);
                        this.getterMethodMapping.put(getterNameTemp, methodTemp);
                        this.getterTypeMapping.put(getterNameTemp,
                                methodTemp.getReturnType());
                    }
                }
                if (ReflectionUtils.isSetterMethod(methodTemp)) {
                    String setterNameTemp = ReflectionUtils.getSetterNameByMethod(methodTemp);
                    if (!this.setterNames.contains(setterNameTemp)) {
                        this.setterNames.add(setterNameTemp);
                        this.setterMethodMapping.put(setterNameTemp, methodTemp);
                        this.setterTypeMapping.put(setterNameTemp,
                                methodTemp.getParameterTypes()[0]);
                    }
                }
            }
            searchType = searchType.getSuperclass();
        }
    }
    
    private Class<T> type;
    
    private Map<String, Field> propertyFields = new HashMap<String, Field>();
    
    private Set<String> setterNames = new HashSet<String>();
    
    private Set<String> getterNames = new HashSet<String>();
    
    private Map<String, Method> getterMethodMapping = new HashMap<String, Method>();
    
    private Map<String, Class<?>> getterTypeMapping = new HashMap<String, Class<?>>();
    
    private Map<String, Method> setterMethodMapping = new HashMap<String, Method>();
    
    private Map<String, Class<?>> setterTypeMapping = new HashMap<String, Class<?>>();
    
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
    public Method getGetterMethod(String getterName) {
        return this.getterMethodMapping.get(getterName);
    }
    
    /**
      * 获取指定属性的getter方法对应类型
      *<功能详细描述>
      * @param getterName
      * @return [参数说明]
      * 
      * @return Class<?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Class<?> getGetterType(String getterName) {
        return this.getterTypeMapping.get(getterName);
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
    public Method getSetterMethod(String setterName) {
        return this.setterMethodMapping.get(setterName);
    }
    
    /**
      * 获取指定属性的setter对应类型
      *<功能详细描述>
      * @param setterName
      * @return [参数说明]
      * 
      * @return Class<?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Class<?> getSetterType(String setterName) {
        return this.setterTypeMapping.get(setterName);
    }
    
    /**
      * 获取所有set方法映射到的属性<br/> 
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return Set<String> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Set<String> getSetterNames() {
        return setterNames;
    }
    
    /**
      * 获取所有get方法映射到的属性<br/> 
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return Set<String> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Set<String> getGetterNames() {
        return getterNames;
    }
    
}
