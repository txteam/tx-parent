/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-4-2
 * <修改描述:>
 */
package com.tx.core.exceptions.util;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.argument.IllegalArgException;
import com.tx.core.exceptions.argument.NullArgException;
import com.tx.core.exceptions.io.ResourceIsNullOrNotExistException;
import com.tx.core.util.ObjectUtils;

/**
 * 断言工具类<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-4-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AssertUtils {
    
    /**
     * 判断对象不为空则抛出指定异常<br/>
     * <功能详细描述>
     * @param obj
     * @param exception [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void isTrue(boolean flag, SILException exception) {
        //不为空
        if (flag) {
            throw exception;
        }
    }
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param str
     * @param message [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void notEmpty(Object obj) {
        //不为空
        notEmpty(obj, "");
    }
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param str
     * @param message [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void notEmpty(Object obj, String message,
            String... parameters) {
        //不为空
        notEmpty(obj, message, (Object[]) parameters);
    }
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param str
     * @param message [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void notEmpty(Object obj, String message, Object[] parameters) {
        //不为空
        notNull(obj, message, parameters);
        if (ObjectUtils.isEmpty(obj)) {
            throw new NullArgException(MessageFormatter.arrayFormat(message,
                    parameters).getMessage());
        }
    }
    
    /**
     * 断言对应对象为空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param str
     * @param message [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void isEmpty(Object obj) {
        //不为空
        isEmpty(obj, "");
    }
    
    /**
     * 断言对应对象为空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param str
     * @param message [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void isEmpty(Object obj, String message, String... parameters) {
        //不为空
        isEmpty(obj, message, (Object[]) parameters);
    }
    
    /**
     * 断言对应字符串不能为空<br/>
     * <功能详细描述>
     * @param str
     * @param message [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @SuppressWarnings("rawtypes")
    public static void isEmpty(Object obj, String message, Object[] parameters) {
        //不为空
        isNull(obj, message, parameters);
        if (obj instanceof String && !StringUtils.isBlank((String) obj)) {
            throw new NullArgException(MessageFormatter.arrayFormat(message,
                    parameters).getMessage());
        } else if (obj instanceof Collection
                && !CollectionUtils.isEmpty((Collection) obj)) {
            throw new NullArgException(MessageFormatter.arrayFormat(message,
                    parameters).getMessage());
        } else if (obj instanceof Map && !MapUtils.isEmpty((Map) obj)) {
            throw new NullArgException(MessageFormatter.arrayFormat(message,
                    parameters).getMessage());
        } else if (obj instanceof Object[]
                && !ArrayUtils.isEmpty((Object[]) obj)) {
            throw new NullArgException(MessageFormatter.arrayFormat(message,
                    parameters).getMessage());
        }
    }
    
    /**
     * 断言对象不为空<br/>
     * <功能详细描述>
     * @param object
     * @param message [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void notNull(Object object) {
        notNull(object, "");
    }
    
    /**
     * 断言对象不为空<br/>
     * <功能详细描述>
     * @param object
     * @param message [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void notNull(Object object, String message,
            String... parameters) {
        notNull(object, message, (Object[]) parameters);
    }
    
    /**
      * 断言对象不为空<br/>
      * <功能详细描述>
      * @param object [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void notNull(Object object, String message,
            Object[] parameters) {
        if (object == null) {
            throw new NullArgException(MessageFormatter.arrayFormat(message,
                    parameters).getMessage());
        }
    }
    
    /**
     * 断言对象为空<br/>
     * <功能详细描述>
     * @param object
     * @param message [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void isNull(Object object) {
        notNull(object, "");
    }
    
    /**
     * 断言对象为空<br/>
     * <功能详细描述>
     * @param object
     * @param message [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void isNull(Object object, String message,
            String... parameters) {
        notNull(object, message, (Object[]) parameters);
    }
    
    /**
      * 断言对象为空<br/>
      * <功能详细描述>
      * @param object [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void isNull(Object object, String message, Object[] parameters) {
        if (object != null) {
            throw new IllegalArgException(MessageFormatter.arrayFormat(message,
                    parameters).getMessage());
        }
    }
    
    /**
     * 断言表达式是为真<br/>
     *     如果不为真，抛出参数非法异常IllegalArgException<br/>
     * <功能详细描述>
     * @param expression
     * @param message [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public static void isTrue(boolean expression) {
       isTrue(expression, "");
   }
    
    /**
      * 断言表达式是为真<br/>
      *     如果不为真，抛出参数非法异常IllegalArgException<br/>
      * <功能详细描述>
      * @param expression
      * @param message [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void isTrue(boolean expression, String message,
            String... parameters) {
        isTrue(expression, message, (Object[]) parameters);
    }
    
    /**
      * 断言表达式是为真<br/>
      *     如果不为真，抛出参数非法异常IllegalArgException<br/>
      * <功能详细描述>
      * @param expression [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void isTrue(boolean expression, String message,
            Object[] parameters) {
        if (!expression) {
            throw new IllegalArgException(MessageFormatter.arrayFormat(message,
                    parameters).getMessage());
        }
    }
    
    /**
     * 断言表达式是不为真<br/>
     *     如果不为真，抛出参数非法异常IllegalArgException<br/>
     * <功能详细描述>
     * @param expression
     * @param message [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void notTrue(boolean expression) {
        notTrue(expression, "");
    }
    
    /**
     * 断言表达式是不为真<br/>
     *     如果不为真，抛出参数非法异常IllegalArgException<br/>
     * <功能详细描述>
     * @param expression
     * @param message [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void notTrue(boolean expression, String message,
            String... parameters) {
        notTrue(expression, message, (Object[]) parameters);
    }
    
    /**
      * 断言表达式是不为真<br/>
      *     如果不为真，抛出参数非法异常IllegalArgException<br/>
      * <功能详细描述>
      * @param expression [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void notTrue(boolean expression, String message,
            Object[] parameters) {
        if (expression) {
            throw new IllegalArgException(MessageFormatter.arrayFormat(message,
                    parameters).getMessage());
        }
    }
    
    /**
     * 断言是否为指定类型的实例
     *<功能详细描述>
     * @param clazz
     * @param obj [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void isInstanceOf(Class<?> clazz, Object obj) {
        isInstanceOf(clazz, obj, "");
    }
    
    /**
      * 断言是否为指定类型的实例
      *<功能详细描述>
      * @param clazz
      * @param obj [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void isInstanceOf(Class<?> clazz, Object obj, String message,
            String... parameters) {
        isInstanceOf(clazz, obj, message, (Object[]) parameters);
    }
    
    /**
      * 断言对象是否为指定类型的实例
      *<功能详细描述>
      * @param type
      * @param obj
      * @param message
      * @param parameters [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void isInstanceOf(Class<?> type, Object obj, String message,
            Object[] parameters) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException(message + ". Object of class ["
                    + (obj != null ? obj.getClass().getName() : "null")
                    + "] must be an instance of " + type);
        }
    }
    
    /**
      *<功能简述>
      *<功能详细描述>
      * @param superType
      * @param subType [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    public static void isAssignable(Class superType, Class subType) {
        isAssignable(superType, subType, "");
    }
    
    /**
     * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
     * <pre class="code">Assert.isAssignable(Number.class, myClass);</pre>
     * @param superType the super type to check against
     * @param subType the sub type to check
     * @param message a message which will be prepended to the message produced by
     * the function itself, and which may be used to provide context. It should
     * normally end in a ": " or ". " so that the function generate message looks
     * ok when prepended to it.
     * @throws IllegalArgumentException if the classes are not assignable
     */
    public static void isAssignable(Class<?> superType, Class<?> subType,
            String message) {
        notNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new IllegalArgumentException(message + subType
                    + " is not assignable to " + superType);
        }
    }
    
    /**
     * 断言资源是存在否则抛出资源不存在异常<br/>
     * <功能详细描述>
     * @param str
     * @param message [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void isExist(Resource resource, String message,
            String... parameters) {
        isExist(resource, message, (Object[]) parameters);
    }
    
    /**
     * 断言资源是存在否则抛出资源不存在异常<br/>
     * <功能详细描述>
     * @param str
     * @param message [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void isExist(Resource resource, String message,
            Object[] parameters) {
        if (resource == null || !resource.exists()) {
            throw new ResourceIsNullOrNotExistException(resource, message,
                    parameters);
        }
    }
    
    /**
     * 断言资源是存在否则抛出资源不存在异常<br/>
     * <功能详细描述>
     * @param str
     * @param message [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void isExist(File file, String message, String... parameters) {
        isExist(file, message, (Object[]) parameters);
    }
    
    /**
     * 断言资源是存在否则抛出资源不存在异常<br/>
     * <功能详细描述>
     * @param str
     * @param message [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void isExist(File file, String message, Object[] parameters) {
        if (file == null || !file.exists()) {
            throw new ResourceIsNullOrNotExistException(new FileSystemResource(
                    file), message, parameters);
        }
    }
}
