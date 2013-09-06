/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-22
 * <修改描述:>
 */
package com.tx.core.exceptions.util;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.helpers.MessageFormatter;

import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.io.ResourceAccessException;
import com.tx.core.reflection.exception.ReflectionException;

/**
 * 异常包装工具方便异常捕获后包装为系统内部定义异常
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ExceptionWrapperUtils {
    
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
    public static <T extends SILException> SILException wrapperSILException(
            Class<T> exceptionType, String message, Object... parameters) {
        Constructor<T> cons;
        try {
            cons = exceptionType.getConstructor(String.class, Object[].class);
        } catch (SecurityException e) {
            throw new ReflectionException(
                    MessageFormatter.format("SILException:{} SecurityException:{}.",
                            new Object[] { exceptionType, e })
                            .getMessage(), e);
        } catch (NoSuchMethodException e) {
            throw new ReflectionException(
                    MessageFormatter.format("SILException:{} NoSuchMethodException:{}.",
                            new Object[] { exceptionType, e })
                            .getMessage(), e);
        }
        
        T exception = null;
        try {
            exception = cons.newInstance(message, (Object[])parameters);
        } catch (IllegalArgumentException e) {
            throw new ReflectionException(
                    MessageFormatter.format("SILException:{} IllegalArgumentException:{}.",
                            new Object[] { exceptionType, e })
                            .getMessage(), e);
        } catch (InstantiationException e) {
            throw new ReflectionException(
                    MessageFormatter.format("SILException:{} InstantiationException:{}.",
                            new Object[] { exceptionType, e })
                            .getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new ReflectionException(
                    MessageFormatter.format("SILException:{} IllegalAccessException:{}.",
                            new Object[] { exceptionType, e })
                            .getMessage(), e);
        } catch (InvocationTargetException e) {
            throw new ReflectionException(
                    MessageFormatter.format("SILException:{} InvocationTargetException:{}.",
                            new Object[] { exceptionType, e })
                            .getMessage(), e);
        }
        return exception;
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
    public static <T extends SILException> SILException wrapperSILException(
            Class<T> exceptionType, String message, Throwable sourceError,
            Object[]... parameters) {
        Constructor<T> cons;
        try {
            cons = exceptionType.getConstructor(String.class, Throwable.class);
        } catch (SecurityException e) {
            throw new ReflectionException(
                    MessageFormatter.format("SILException:{} SecurityException:{}.",
                            new Object[] { exceptionType, e })
                            .getMessage(), e);
        } catch (NoSuchMethodException e) {
            throw new ReflectionException(
                    MessageFormatter.format("SILException:{} NoSuchMethodException:{}.",
                            new Object[] { exceptionType, e })
                            .getMessage(), e);
        }
        
        T exception = null;
        try {
            exception = cons.newInstance(MessageFormatter.format(message,
                    (Object[]) parameters).getMessage(),
                    sourceError);
        } catch (IllegalArgumentException e) {
            throw new ReflectionException(
                    MessageFormatter.format("SILException:{} IllegalArgumentException:{}.",
                            new Object[] { exceptionType, e })
                            .getMessage(), e);
        } catch (InstantiationException e) {
            throw new ReflectionException(
                    MessageFormatter.format("SILException:{} InstantiationException:{}.",
                            new Object[] { exceptionType, e })
                            .getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new ReflectionException(
                    MessageFormatter.format("SILException:{} IllegalAccessException:{}.",
                            new Object[] { exceptionType, e })
                            .getMessage(), e);
        } catch (InvocationTargetException e) {
            throw new ReflectionException(
                    MessageFormatter.format("SILException:{} InvocationTargetException:{}.",
                            new Object[] { exceptionType, e })
                            .getMessage(), e);
        }
        return exception;
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
    public static SILException wrapperIOException(IOException ioException,
            String message, String... parameters) {
        return wrapperIOException(ioException, message, (Object[]) parameters);
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
    public static SILException wrapperIOException(IOException ioException,
            String message, Object[] parameters) {
        AssertUtils.notNull(ioException, "ioException not be null");
        return new ResourceAccessException(MessageFormatter.arrayFormat(message, parameters).getMessage(),ioException);
    }
}
