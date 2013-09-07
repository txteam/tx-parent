/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-22
 * <修改描述:>
 */
package com.tx.core.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.ConstructorUtils;
import org.apache.ibatis.reflection.MetaObject;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.reflection.exception.ReflectionException;

/**
 * 对象工具类<br/>
 *     主要补充commonslang包中方法不足
 *     利用该类补足对应方法<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ObjectUtils {
    
    /**
      * 根据传入的参数生成对象实例
      *<功能详细描述>
      * @param type
      * @param objects
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> type,Object... objects){
        T res = null;
        try {
            res = (T)ConstructorUtils.invokeConstructor(type, objects);
            return res;
        } catch (NoSuchMethodException e) {
            throw new ReflectionException("invokeConstructor create newInstance error.", e);
        } catch (IllegalAccessException e) {
            throw new ReflectionException("invokeConstructor create newInstance error.", e);
        } catch (InvocationTargetException e) {
            throw new ReflectionException("invokeConstructor create newInstance error.", e);
        } catch (InstantiationException e) {
            throw new ReflectionException("invokeConstructor create newInstance error.", e);
        }
    }
    
    /**
     * 判断入参数是否为空<br/>
     * " "不会被判定为false<br/>
     * <功能详细描述>
     * @param obj
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static boolean isEmpty(Object obj) {
        //为空时认为是empty的
        if (obj == null) {
            return true;
        } else if (obj instanceof String) {
            return StringUtils.isEmpty((String) obj);
        } else if (obj instanceof Collection<?>) {
            return CollectionUtils.isEmpty((Collection<?>) obj);
        } else if (obj instanceof Map<?, ?>) {
            return MapUtils.isEmpty((Map<?, ?>) obj);
        } else if (obj instanceof Object[]) {
            return ArrayUtils.isEmpty((Object[]) obj);
        } else {
            return false;
        }
    }
    
    /**
      * 根据依赖的属性名生成对象的HashCode
      *<功能详细描述>
      * @param thisObj
      * @param dependPropertyName
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static int generateHashCode(int superHashCode, Object thisObj,
            String... dependPropertyName) {
        AssertUtils.notNull(thisObj, "thisObj is null.");
        
        MetaObject metaObject = MetaObject.forObject(thisObj);
        int resHashCode = thisObj.getClass().hashCode();
        for (String propertyNameTemp : dependPropertyName) {
            Object value = metaObject.getValue(propertyNameTemp);
            resHashCode += value == null ? superHashCode : value.hashCode();
        }
        return resHashCode;
    }
    
    /**
      * 根据依赖的属性名，判断对象是否相等
      *     当指定字段都为空时判断两对象引用是否相同
      *<功能详细描述>
      * @param thisObj
      * @param otherObj
      * @param dependPropertyName
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean equals(Object thisObj, Object otherObj,
            String... dependPropertyName) {
        AssertUtils.notNull(thisObj, "thisObj is null.");
        
        if (thisObj == otherObj) {
            return true;
        } else if (!thisObj.getClass().isAssignableFrom(otherObj.getClass())) {
            return false;
        } else {
            MetaObject thisMetaObject = MetaObject.forObject(thisObj);
            MetaObject otherMetaObject = MetaObject.forObject(otherObj);
            
            for (String propertyNameTemp : dependPropertyName) {
                if (!org.apache.commons.lang.ObjectUtils.equals(thisMetaObject.getValue(propertyNameTemp),
                        otherMetaObject.getValue(propertyNameTemp))) {
                    return false;
                }
            }
            return true;
        }
    }
}
