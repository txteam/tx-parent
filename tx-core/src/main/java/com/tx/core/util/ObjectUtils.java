/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-22
 * <修改描述:>
 */
package com.tx.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import com.tx.core.exceptions.io.ResourceAccessException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.reflection.JpaMetaClass;
import com.tx.core.reflection.exception.ReflectionException;

/**
 * 对象工具类<br />
 * 主要补充commonslang包中方法不足<br />
 * 利用该类补足对应方法<br />
 * 
 * @author brady
 * @version [版本号, 2013-8-22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ObjectUtils {
    
    /**
     * 
     * 打印调试信息<br />
     * 打印出一个 bean 中的 get 方法返回值
     *
     * @param out 输出流,如果为 null, 则自动设置成System.out
     * @param label 标签
     * @param object bean
     * @param deep 深度遍历(暂未实现)
     * @param ignoreNull 是否忽略返回的 null 的项
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月25日]
     * @author rain
     */
    public static void debugPrintPropertyValue(PrintStream out, String label,
            Object object, boolean deep, boolean ignoreNull) {
        if (out == null) {
            out = System.out;
        }
        if (object == null) {
            out.println(label + " : null");
        }
        out.println();
        out.println(label + " : " + object.getClass().getName());
        JpaMetaClass<?> jpaMetaClass = JpaMetaClass.forClass(object.getClass());
        Set<String> getterNames = jpaMetaClass.getGetterNames();
        for (String getterMethod : getterNames) {
            try {
                Object invokeMethod = MethodUtils.invokeMethod(object, "get"
                        + StringUtils.capitalize(getterMethod));
                if (ignoreNull && invokeMethod == null) {
                    continue;
                }
                out.println(getterMethod + " : " + invokeMethod);
            } catch (Exception e) {
            }
        }
    }
    
    /**
     * 对一个Serializable的对象进行深度Clone 基于序列化与反序列化实现， 该方法通用性强，但性能反而不如clone或BeanUtils的应用<br/>
     * 如果深度克隆的对象未实现Serializable将会抛出异常 <功能详细描述>
     * 
     * @param srcObj
     * @return [参数说明]
     *         
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public static <T> T deepClone(T srcObj) {
        T resObject = null;
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(srcObj);
            ByteArrayInputStream bais = new ByteArrayInputStream(
                    baos.toByteArray());
            ois = new ObjectInputStream(bais);
            resObject = (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new ResourceAccessException("deepClone error.", e);
        } finally {
            IOUtils.closeQuietly(oos);
            IOUtils.closeQuietly(ois);
            IOUtils.closeQuietly(baos);
        }
        return resObject;
    }
    
    /**
     * 根据依赖的属性名，判断对象是否相等 当指定字段都为空时判断两对象引用是否相同 <功能详细描述>
     * 
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
        if (thisObj == otherObj) {
            //两者均为空，则相等
            return true;
        }
        if (thisObj == null || otherObj == null) {
            //两者其中之一为空，则不等
            return false;
        }
        
        //两者均不为空
        if (!thisObj.getClass().isAssignableFrom(otherObj.getClass())) {
            return false;
        } else {
            BeanWrapper thisMetaObject = PropertyAccessorFactory.forBeanPropertyAccess(thisObj);
            BeanWrapper otherMetaObject = PropertyAccessorFactory.forBeanPropertyAccess(otherObj);
            for (String propertyNameTemp : dependPropertyName) {
                Object thisPropertyValue = thisMetaObject.getPropertyValue(propertyNameTemp);
                Object otherPropertyValue = otherMetaObject.getPropertyValue(propertyNameTemp);
                if (thisPropertyValue == otherPropertyValue) {
                    continue;
                }
                if (thisPropertyValue == null || otherPropertyValue == null) {
                    return false;
                }
                if (!org.apache.commons.lang.ObjectUtils.equals(thisPropertyValue,
                        otherPropertyValue)) {
                    return false;
                }
            }
            return true;
        }
    }
    
    /**
     * 根据依赖的属性名生成对象的HashCode <功能详细描述>
     * 
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
        
        int resHashCode = thisObj.getClass().hashCode();
        BeanWrapper metaObject = PropertyAccessorFactory.forBeanPropertyAccess(thisObj);
        for (String propertyNameTemp : dependPropertyName) {
            Object value = metaObject.getPropertyValue(propertyNameTemp);
            resHashCode += (value == null ? propertyNameTemp.hashCode()
                    : value.hashCode());
        }
        return resHashCode;
    }
    
    /**
     * 判断入参数是否为空<br/>
     * " "不会被判定为false<br/>
     * <功能详细描述>
     * 
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
        } else if (TypeUtils.isArrayType(obj.getClass())) {
            return ArrayUtils.isEmpty((Object[]) obj);
        } else {
            return false;
        }
    }
    
    /**
     * 根据传入的参数生成对象实例 <功能详细描述>
     * 
     * @param type
     * @param objects
     * @return [参数说明]
     *         
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static <T> T newInstance(Class<T> type, Object... objects) {
        T res = null;
        try {
            res = (T) ConstructorUtils.invokeConstructor(type, objects);
            return res;
        } catch (NoSuchMethodException e) {
            throw new ReflectionException(
                    "invokeConstructor create newInstance error.", e);
        } catch (IllegalAccessException e) {
            throw new ReflectionException(
                    "invokeConstructor create newInstance error.", e);
        } catch (InvocationTargetException e) {
            throw new ReflectionException(
                    "invokeConstructor create newInstance error.", e);
        } catch (InstantiationException e) {
            throw new ReflectionException(
                    "invokeConstructor create newInstance error.", e);
        }
    }
    
    public static <T> void populate(T obj, Map<String, Object> properties) {
        try {
            BeanUtils.populate(obj, properties);
        } catch (IllegalAccessException e) {
            throw new ReflectionException("invoke populate error.", e);
        } catch (InvocationTargetException e) {
            throw new ReflectionException("invoke populate error.", e);
        }
    }
    
    /**
     * 
     * 转换成 boolean
     *
     * @param value
     *            
     * @return Boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月23日]
     * @author rain
     */
    public static Boolean toBoolean(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if ("true".equals(value) || "1".equals(value) || "yes".equals(value)
                || "on".equals(value)) {
            return Boolean.TRUE;
        }
        return BooleanUtils.toBooleanObject(String.valueOf(value));
    }
    
    /**
     * 
     * 转换成 boolean
     *
     * @param value
     * @param defaultBoolean 如果不为boolean或者为 null, 则返回默认值
     *            
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月23日]
     * @author rain
     */
    public static boolean toBoolean(Object value, boolean defaultBoolean) {
        try {
            Boolean bool = toBoolean(value);
            return bool == null ? defaultBoolean : bool.booleanValue();
        } catch (Exception e) {
            return defaultBoolean;
        }
    }
    
    /**
     * 
     * 转换成数字
     *
     * @param value
     *            
     * @return Number [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月23日]
     * @author rain
     */
    public static Number toNumber(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return (Number) value;
        }
        return NumberUtils.createNumber(String.valueOf(value));
    }
    
    /**
     * 
     * 转换成数字
     *
     * @param value
     * @param defaultNumber 如果不为数字或者为 null, 则返回默认值
     *            
     * @return Number [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月23日]
     * @author rain
     */
    public static Number toNumber(Object value, Number defaultNumber) {
        try {
            Number number = toNumber(value);
            return number == null ? defaultNumber : number;
        } catch (Exception e) {
            return defaultNumber;
        }
    }
}
