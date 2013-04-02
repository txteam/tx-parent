/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-4-2
 * <修改描述:>
 */
package com.tx.core.exceptions.util;

import java.util.Collection;
import java.util.Map;

import org.slf4j.helpers.MessageFormatter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.tx.core.exceptions.parameter.ParameterIsEmptyException;
import com.tx.core.exceptions.parameter.ParameterIsInvalidException;

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
     * 断言对应字符串不能为空<br/>
     * <功能详细描述>
     * @param str
     * @param message [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void notEmpty(String str, String message,
            String... parameters) {
        if (StringUtils.isEmpty(str)) {
            throw new ParameterIsEmptyException(
                    MessageFormatter.format(message, parameters).getMessage());
        }
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
    public static void notEmpty(String str, String message, Object[] parameters) {
        if (StringUtils.isEmpty(str)) {
            throw new ParameterIsEmptyException(
                    MessageFormatter.format(message, parameters).getMessage());
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
    public static void notNull(Object object, String message,
            String... parameters) {
        if (object == null) {
            throw new ParameterIsInvalidException(
                    MessageFormatter.format(message, parameters).getMessage());
        }
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
    public static void notNull(Object str, String message, Object[] parameters) {
        if (str == null) {
            throw new ParameterIsEmptyException(
                    MessageFormatter.format(message, parameters).getMessage());
        }
    }
    
    /**
      * 断言表达式是否为真<br/>
      *     如果不为真，抛出参数非法异常ParameterIsInvalidException<br/>
      * <功能详细描述>
      * @param expression
      * @param message [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new ParameterIsInvalidException(message);
        }
    }
    
    /**
      * 断言表达式是否为真
      * @param expression [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }
    
    /**
      * 断言表达式是否为空<br/>
      *     如果不为空，抛出参数非法异常<br/>
      *<功能详细描述>
      * @param object
      * @param message [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new ParameterIsInvalidException(message);
        }
    }
    
    /**
      * 断言表达式是否为空<br/>
      * <功能详细描述>
      * @param object [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }
    
    /**
     * Assert that the given String is not empty; that is,
     * it must not be {@code null} and not the empty String.
     * <pre class="code">Assert.hasLength(name, "Name must not be empty");</pre>
     * @param text the String to check
     * @param message the exception message to use if the assertion fails
     * @see StringUtils#hasLength
     */
    public static void hasLength(String text, String message) {
        if (!StringUtils.hasLength(text)) {
            throw new IllegalArgumentException(message);
        }
    }
    
    /**
     * Assert that the given String is not empty; that is,
     * it must not be {@code null} and not the empty String.
     * <pre class="code">Assert.hasLength(name);</pre>
     * @param text the String to check
     * @see StringUtils#hasLength
     */
    public static void hasLength(String text) {
        hasLength(text,
                "[Assertion failed] - this String argument must have length; it must not be null or empty");
    }
    
    /**
     * Assert that the given String has valid text content; that is, it must not
     * be {@code null} and must contain at least one non-whitespace character.
     * <pre class="code">Assert.hasText(name, "'name' must not be empty");</pre>
     * @param text the String to check
     * @param message the exception message to use if the assertion fails
     * @see StringUtils#hasText
     */
    public static void hasText(String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new IllegalArgumentException(message);
        }
    }
    
    /**
     * Assert that the given String has valid text content; that is, it must not
     * be {@code null} and must contain at least one non-whitespace character.
     * <pre class="code">Assert.hasText(name, "'name' must not be empty");</pre>
     * @param text the String to check
     * @see StringUtils#hasText
     */
    public static void hasText(String text) {
        hasText(text,
                "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
    }
    
    /**
     * Assert that the given text does not contain the given substring.
     * <pre class="code">Assert.doesNotContain(name, "rod", "Name must not contain 'rod'");</pre>
     * @param textToSearch the text to search
     * @param substring the substring to find within the text
     * @param message the exception message to use if the assertion fails
     */
    public static void doesNotContain(String textToSearch, String substring,
            String message) {
        if (StringUtils.hasLength(textToSearch)
                && StringUtils.hasLength(substring)
                && textToSearch.indexOf(substring) != -1) {
            throw new IllegalArgumentException(message);
        }
    }
    
    /**
     * Assert that the given text does not contain the given substring.
     * <pre class="code">Assert.doesNotContain(name, "rod");</pre>
     * @param textToSearch the text to search
     * @param substring the substring to find within the text
     */
    public static void doesNotContain(String textToSearch, String substring) {
        doesNotContain(textToSearch,
                substring,
                "[Assertion failed] - this String argument must not contain the substring ["
                        + substring + "]");
    }
    
    /**
     * Assert that an array has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <pre class="code">Assert.notEmpty(array, "The array must have elements");</pre>
     * @param array the array to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object array is {@code null} or has no elements
     */
    public static void notEmpty(Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new IllegalArgumentException(message);
        }
    }
    
    /**
     * Assert that an array has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <pre class="code">Assert.notEmpty(array);</pre>
     * @param array the array to check
     * @throws IllegalArgumentException if the object array is {@code null} or has no elements
     */
    public static void notEmpty(Object[] array) {
        notEmpty(array,
                "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }
    
    /**
     * Assert that an array has no null elements.
     * Note: Does not complain if the array is empty!
     * <pre class="code">Assert.noNullElements(array, "The array must have non-null elements");</pre>
     * @param array the array to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object array contains a {@code null} element
     */
    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null) {
                    throw new IllegalArgumentException(message);
                }
            }
        }
    }
    
    /**
     * Assert that an array has no null elements.
     * Note: Does not complain if the array is empty!
     * <pre class="code">Assert.noNullElements(array);</pre>
     * @param array the array to check
     * @throws IllegalArgumentException if the object array contains a {@code null} element
     */
    public static void noNullElements(Object[] array) {
        noNullElements(array,
                "[Assertion failed] - this array must not contain any null elements");
    }
    
    /**
     * Assert that a collection has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <pre class="code">Assert.notEmpty(collection, "Collection must have elements");</pre>
     * @param collection the collection to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the collection is {@code null} or has no elements
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(message);
        }
    }
    
    /**
     * Assert that a collection has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <pre class="code">Assert.notEmpty(collection, "Collection must have elements");</pre>
     * @param collection the collection to check
     * @throws IllegalArgumentException if the collection is {@code null} or has no elements
     */
    public static void notEmpty(Collection<?> collection) {
        notEmpty(collection,
                "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
    }
    
    /**
     * Assert that a Map has entries; that is, it must not be {@code null}
     * and must have at least one entry.
     * <pre class="code">Assert.notEmpty(map, "Map must have entries");</pre>
     * @param map the map to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the map is {@code null} or has no entries
     */
    public static void notEmpty(Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new IllegalArgumentException(message);
        }
    }
    
    /**
     * Assert that a Map has entries; that is, it must not be {@code null}
     * and must have at least one entry.
     * <pre class="code">Assert.notEmpty(map);</pre>
     * @param map the map to check
     * @throws IllegalArgumentException if the map is {@code null} or has no entries
     */
    public static void notEmpty(Map<?, ?> map) {
        notEmpty(map,
                "[Assertion failed] - this map must not be empty; it must contain at least one entry");
    }
    
    /**
     * Assert that the provided object is an instance of the provided class.
     * <pre class="code">Assert.instanceOf(Foo.class, foo);</pre>
     * @param clazz the required class
     * @param obj the object to check
     * @throws IllegalArgumentException if the object is not an instance of clazz
     * @see Class#isInstance
     */
    public static void isInstanceOf(Class<?> clazz, Object obj) {
        isInstanceOf(clazz, obj, "");
    }
    
    /**
     * Assert that the provided object is an instance of the provided class.
     * <pre class="code">Assert.instanceOf(Foo.class, foo);</pre>
     * @param type the type to check against
     * @param obj the object to check
     * @param message a message which will be prepended to the message produced by
     * the function itself, and which may be used to provide context. It should
     * normally end in a ": " or ". " so that the function generate message looks
     * ok when prepended to it.
     * @throws IllegalArgumentException if the object is not an instance of clazz
     * @see Class#isInstance
     */
    public static void isInstanceOf(Class<?> type, Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException(message + ". Object of class ["
                    + (obj != null ? obj.getClass().getName() : "null")
                    + "] must be an instance of " + type);
        }
    }
    
    /**
     * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
     * <pre class="code">Assert.isAssignable(Number.class, myClass);</pre>
     * @param superType the super type to check
     * @param subType the sub type to check
     * @throws IllegalArgumentException if the classes are not assignable
     */
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
     * Assert a boolean expression, throwing {@code IllegalStateException}
     * if the test result is {@code false}. Call isTrue if you wish to
     * throw IllegalArgumentException on an assertion failure.
     * <pre class="code">Assert.state(id == null, "The id property must not already be initialized");</pre>
     * @param expression a boolean expression
     * @param message the exception message to use if the assertion fails
     * @throws IllegalStateException if expression is {@code false}
     */
    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }
    
    /**
     * Assert a boolean expression, throwing {@link IllegalStateException}
     * if the test result is {@code false}.
     * <p>Call {@link #isTrue(boolean)} if you wish to
     * throw {@link IllegalArgumentException} on an assertion failure.
     * <pre class="code">Assert.state(id == null);</pre>
     * @param expression a boolean expression
     * @throws IllegalStateException if the supplied expression is {@code false}
     */
    public static void state(boolean expression) {
        state(expression,
                "[Assertion failed] - this state invariant must be true");
    }
}
