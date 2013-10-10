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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.apache.commons.lang.ClassUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
     * 默认是否包括不可访问的属性
     */
    public static boolean defaultIsIncludeInaccessible = false;
    
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
    public static <TYPE> ClassReflector<TYPE> forClass(Class<TYPE> type) {
        return forClass(type, defaultIsIncludeInaccessible);
    }
    
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
    public static <TYPE> ClassReflector<TYPE> forClass(Class<TYPE> type,
            boolean isIncludeInaccessible) {
        if (type == null) {
            throw new NullArgException(
                    "MetaAnnotationClass forClass parameter type is empty.");
        }
        synchronized (type) {
            if (cacheMap.containsKey(type)) {
                return (ClassReflector<TYPE>) cacheMap.get(type);
            }
            ClassReflector<TYPE> res = new ClassReflector<TYPE>(type,
                    isIncludeInaccessible);
            
            cacheMap.put(type, res);
            return res;
        }
    }
    
    /** <默认构造函数> 
     * isIncludeInaccessible 是否包括不可范文的字段
     * */
    private ClassReflector(Class<T> type, boolean isIncludeInaccessible) {
        super();
        this.type = type;
        
        Class<?> searchType = type;
        while (!Object.class.equals(searchType) && searchType != null) {
            
            Field[] fields = searchType.getDeclaredFields();
            for (Field fieldTemp : fields) {
                String fieldName = fieldTemp.getName();
                
                //如果子类已经存在的字段则认为该字段已经被覆写以子类为准
                if (this.fieldNames.contains(fieldName)) {
                    this.fieldMapping.add(fieldName, fieldTemp);
                    continue;
                }
                
                this.fieldMapping.add(fieldName, fieldTemp);
                this.fieldNames.add(fieldName);
                this.fieldTypeMapping.put(fieldName, fieldTemp.getType());
                
                if (isIncludeInaccessible) {
                    this.getterNames.add(fieldName);
                    this.getterTypeMapping.put(fieldName, fieldTemp.getType());
                    this.setterNames.add(fieldName);
                    this.setterTypeMapping.put(fieldName, fieldTemp.getType());
                }
            }
            
            Method[] methods = searchType.getMethods();
            for (Method methodTemp : methods) {
                if (ReflectionUtils.isGetterMethod(methodTemp)) {
                    String getterNameTemp = ReflectionUtils.getGetterNameByMethod(methodTemp);
                    //这里利用methodKey去判断避免isIncludeInaccessible为true时在field遍历时已经写入了getterType,getterName
                    if (!this.getterMethodMapping.containsKey(getterNameTemp)) {
                        this.getterNames.add(getterNameTemp);
                        this.getterMethodMapping.add(getterNameTemp, methodTemp);
                        this.getterTypeMapping.put(getterNameTemp,
                                methodTemp.getReturnType());
                    } else {
                        this.getterMethodMapping.add(getterNameTemp, methodTemp);
                    }
                }
                if (ReflectionUtils.isSetterMethod(methodTemp)) {
                    String setterNameTemp = ReflectionUtils.getSetterNameByMethod(methodTemp);
                    //这里利用methodKey去判断避免isIncludeInaccessible为true时在field遍历时已经写入了setterType,setterName
                    if (!this.setterMethodMapping.containsKey(setterNameTemp)) {
                        this.setterNames.add(setterNameTemp);
                        this.setterMethodMapping.add(setterNameTemp, methodTemp);
                        this.setterTypeMapping.put(setterNameTemp,
                                methodTemp.getParameterTypes()[0]);
                    } else {
                        this.setterMethodMapping.add(setterNameTemp, methodTemp);
                    }
                }
            }
            searchType = searchType.getSuperclass();
        }
        
        //将接口中Method写入
        @SuppressWarnings("unchecked")
        List<Class<?>> interfaceClasses = ClassUtils.getAllInterfaces(type);
        for(Class<?> interfaceClassTemp : interfaceClasses) {
            Method[] methods = interfaceClassTemp.getMethods();
            for (Method methodTemp : methods) {
                if (ReflectionUtils.isGetterMethod(methodTemp)) {
                    String getterNameTemp = ReflectionUtils.getGetterNameByMethod(methodTemp);
                    //这里利用methodKey去判断避免isIncludeInaccessible为true时在field遍历时已经写入了getterType,getterName
                    if (!this.getterMethodMapping.containsKey(getterNameTemp)) {
                        this.getterNames.add(getterNameTemp);
                        this.getterMethodMapping.add(getterNameTemp, methodTemp);
                        this.getterTypeMapping.put(getterNameTemp,
                                methodTemp.getReturnType());
                    } else {
                        this.getterMethodMapping.add(getterNameTemp, methodTemp);
                    }
                }
                if (ReflectionUtils.isSetterMethod(methodTemp)) {
                    String setterNameTemp = ReflectionUtils.getSetterNameByMethod(methodTemp);
                    //这里利用methodKey去判断避免isIncludeInaccessible为true时在field遍历时已经写入了setterType,setterName
                    if (!this.setterMethodMapping.containsKey(setterNameTemp)) {
                        this.setterNames.add(setterNameTemp);
                        this.setterMethodMapping.add(setterNameTemp, methodTemp);
                        this.setterTypeMapping.put(setterNameTemp,
                                methodTemp.getParameterTypes()[0]);
                    } else {
                        this.setterMethodMapping.add(setterNameTemp, methodTemp);
                    }
                }
            }
        }
    }
    
    private Class<T> type;
    
    private Set<String> setterNames = new HashSet<String>();
    
    private Set<String> getterNames = new HashSet<String>();
    
    private Set<String> fieldNames = new HashSet<String>();
    
    private MultiValueMap<String, Field> fieldMapping = new LinkedMultiValueMap<String, Field>();
    
    private MultiValueMap<String, Method> getterMethodMapping = new LinkedMultiValueMap<String, Method>();
    
    private Map<String, Class<?>> fieldTypeMapping = new HashMap<String, Class<?>>();
    
    private Map<String, Class<?>> getterTypeMapping = new HashMap<String, Class<?>>();
    
    private MultiValueMap<String, Method> setterMethodMapping = new LinkedMultiValueMap<String, Method>();
    
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
    public Field getFiled(String fieldName) {
        return this.fieldMapping.getFirst(fieldName);
    }
    
    /**
      * 获取字段列表
      *<功能详细描述>
      * @param fieldName
      * @return [参数说明]
      * 
      * @return List<Field> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<Field> getFiledList(String fieldName) {
        return this.fieldMapping.get(fieldName);
    }
    
    /**
      * 根据字段名获取字段类型<br/>
      *<功能详细描述>
      * @param fieldName
      * @return [参数说明]
      * 
      * @return Class<?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Class<?> getFiledType(String fieldName) {
        return this.fieldTypeMapping.get(fieldName);
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
        return this.getterMethodMapping.getFirst(getterName);
    }
    
    /**
      * 获取get方法集合
      *<功能详细描述>
      * @param getterName
      * @return [参数说明]
      * 
      * @return List<Method> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<Method> getGetterMethodList(String getterName){
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
        return this.setterMethodMapping.getFirst(setterName);
    }
    
    /**
      * 获取指定属性的setter方法集合
      *<功能详细描述>
      * @param setterName
      * @return [参数说明]
      * 
      * @return List<Method> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<Method> getSetterMethodList(String setterName){
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
    
    /**
     * @return 返回 fieldNames
     */
    public Set<String> getFieldNames() {
        return fieldNames;
    }
}
