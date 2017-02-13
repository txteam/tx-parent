/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-4-2
 * <修改描述:>
 */
package com.tx.core.exceptions.util;

import java.io.File;

import org.springframework.core.io.Resource;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.SILExceptionHelper;
import com.tx.core.exceptions.argument.ArgEmptyException;
import com.tx.core.exceptions.argument.ArgIllegalException;
import com.tx.core.exceptions.argument.ArgNotEmptyException;
import com.tx.core.exceptions.argument.ArgNotNullException;
import com.tx.core.exceptions.argument.ArgNullException;
import com.tx.core.exceptions.argument.ArgTypeIllegalException;
import com.tx.core.exceptions.resource.ResourceNotExistException;
import com.tx.core.util.MessageUtils;
import com.tx.core.util.ObjectUtils;

/**
 * 断言工具类<br/>
 * <功能详细描述>
 * 
 * @author brady
 * @version [版本号, 2013-4-2]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AssertUtils {
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notEmpty(Object obj, String messagePattern,
            String... parameters) {
        notEmpty(obj, -1, null, messagePattern, (Object[]) parameters);
    }
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notEmpty(Object obj, String messagePattern,
            Object[] parameters) {
        notEmpty(obj, -1, null, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param error 传入的ErrorCode对象实例，如果在错误信息注册表中存在对应的错误实现类，则自动根据参数实例化对应的类实例
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notEmpty(Object obj, ErrorCode error,
            String messagePattern, String... parameters) {
        int errorCode = error == null ? -1 : error.getCode();
        notEmpty(obj, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param error 传入的ErrorCode对象实例，如果在错误信息注册表中存在对应的错误实现类，则自动根据参数实例化对应的类实例
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notEmpty(Object obj, ErrorCode error,
            String messagePattern, Object[] parameters) {
        int errorCode = error == null ? -1 : error.getCode();
        notEmpty(obj, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notEmpty(Object obj, int errorCode,
            String messagePattern, String... parameters) {
        notEmpty(obj, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notEmpty(Object obj, int errorCode,
            String messagePattern, Object[] parameters) {
        notEmpty(obj, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notEmpty(Object obj, Class<? extends SILException> type,
            String messagePattern, String... parameters) {
        notEmpty(obj, -1, type, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notEmpty(Object obj, Class<? extends SILException> type,
            String messagePattern, Object[] parameters) {
        notEmpty(obj, -1, type, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notEmpty(Object obj, int errorCode,
            Class<? extends SILException> type, String messagePattern,
            Object[] parameters) {
        if (ObjectUtils.isEmpty(obj)) {
            //如果为空则抛出异常
            String message = MessageUtils.format(messagePattern, parameters);
            throw SILExceptionHelper.newSILException(errorCode,
                    message,
                    null,
                    type == null ? ArgEmptyException.class : type);
        }
    }
    
    /**
     * 断言对应对象为空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isEmpty(Object obj, String messagePattern,
            String... parameters) {
        isEmpty(obj, -1, null, messagePattern, (Object[]) parameters);
    }
    
    /**
     * 断言对应对象为空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isEmpty(Object obj, String messagePattern,
            Object[] parameters) {
        isEmpty(obj, -1, null, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象为空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param error 传入的ErrorCode对象实例，如果在错误信息注册表中存在对应的错误实现类，则自动根据参数实例化对应的类实例
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isEmpty(Object obj, ErrorCode error,
            String messagePattern, String... parameters) {
        int errorCode = error == null ? -1 : error.getCode();
        isEmpty(obj, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象为空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param error 传入的ErrorCode对象实例，如果在错误信息注册表中存在对应的错误实现类，则自动根据参数实例化对应的类实例
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isEmpty(Object obj, ErrorCode error,
            String messagePattern, Object[] parameters) {
        int errorCode = error == null ? -1 : error.getCode();
        isEmpty(obj, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象为空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isEmpty(Object obj, int errorCode,
            String messagePattern, String... parameters) {
        isEmpty(obj, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象为空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isEmpty(Object obj, int errorCode,
            String messagePattern, Object[] parameters) {
        isEmpty(obj, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象为空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isEmpty(Object obj, Class<? extends SILException> type,
            String messagePattern, String... parameters) {
        isEmpty(obj, -1, type, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象为空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isEmpty(Object obj, Class<? extends SILException> type,
            String messagePattern, Object[] parameters) {
        isEmpty(obj, -1, type, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象为空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isEmpty(Object obj, int errorCode,
            Class<? extends SILException> type, String messagePattern,
            Object[] parameters) {
        if (ObjectUtils.isEmpty(obj)) {
            //如果为空则抛出异常
            String message = MessageUtils.format(messagePattern, parameters);
            throw SILExceptionHelper.newSILException(errorCode,
                    message,
                    null,
                    type == null ? ArgNotEmptyException.class : type);
        }
    }
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notNull(Object obj, String messagePattern,
            String... parameters) {
        notNull(obj, -1, null, messagePattern, (Object[]) parameters);
    }
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notNull(Object obj, String messagePattern,
            Object[] parameters) {
        notNull(obj, -1, null, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param error 传入的ErrorCode对象实例，如果在错误信息注册表中存在对应的错误实现类，则自动根据参数实例化对应的类实例
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notNull(Object obj, ErrorCode error,
            String messagePattern, String... parameters) {
        int errorCode = error == null ? -1 : error.getCode();
        notNull(obj, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param error 传入的ErrorCode对象实例，如果在错误信息注册表中存在对应的错误实现类，则自动根据参数实例化对应的类实例
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notNull(Object obj, ErrorCode error,
            String messagePattern, Object[] parameters) {
        int errorCode = error == null ? -1 : error.getCode();
        notNull(obj, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notNull(Object obj, int errorCode,
            String messagePattern, String... parameters) {
        notNull(obj, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notNull(Object obj, int errorCode,
            String messagePattern, Object[] parameters) {
        notNull(obj, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notNull(Object obj, Class<? extends SILException> type,
            String messagePattern, String... parameters) {
        notNull(obj, -1, type, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notNull(Object obj, Class<? extends SILException> type,
            String messagePattern, Object[] parameters) {
        notNull(obj, -1, type, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象非空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notNull(Object obj, int errorCode,
            Class<? extends SILException> type, String messagePattern,
            Object[] parameters) {
        if (obj == null) {
            //如果为空则抛出异常
            String message = MessageUtils.format(messagePattern, parameters);
            throw SILExceptionHelper.newSILException(errorCode,
                    message,
                    null,
                    type == null ? ArgNullException.class : type);
        }
    }
    
    /**
     * 断言对应对象为空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isNull(Object obj, String messagePattern,
            String... parameters) {
        isNull(obj, -1, null, messagePattern, (Object[]) parameters);
    }
    
    /**
     * 断言对应对象为空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isNull(Object obj, String messagePattern,
            Object[] parameters) {
        isNull(obj, -1, null, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象为空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param error 传入的ErrorCode对象实例，如果在错误信息注册表中存在对应的错误实现类，则自动根据参数实例化对应的类实例
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isNull(Object obj, ErrorCode error,
            String messagePattern, String... parameters) {
        int errorCode = error == null ? -1 : error.getCode();
        isNull(obj, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象为空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param error 传入的ErrorCode对象实例，如果在错误信息注册表中存在对应的错误实现类，则自动根据参数实例化对应的类实例
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isNull(Object obj, ErrorCode error,
            String messagePattern, Object[] parameters) {
        int errorCode = error == null ? -1 : error.getCode();
        isNull(obj, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象为空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isNull(Object obj, int errorCode, String messagePattern,
            String... parameters) {
        isNull(obj, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象为空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isNull(Object obj, int errorCode, String messagePattern,
            Object[] parameters) {
        isNull(obj, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象为空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isNull(Object obj, Class<? extends SILException> type,
            String messagePattern, String... parameters) {
        isNull(obj, -1, type, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象为空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isNull(Object obj, Class<? extends SILException> type,
            String messagePattern, Object[] parameters) {
        isNull(obj, -1, type, messagePattern, parameters);
    }
    
    /**
     * 断言对应对象为空(支持：字符串，数组，集合，Map)<br/>
     * <功能详细描述>
     * @param obj 传入的检查是否为空的对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isNull(Object obj, int errorCode,
            Class<? extends SILException> type, String messagePattern,
            Object[] parameters) {
        if (obj != null) {
            //如果为空则抛出异常
            String message = MessageUtils.format(messagePattern, parameters);
            throw SILExceptionHelper.newSILException(errorCode,
                    message,
                    null,
                    type == null ? ArgNotNullException.class : type);
        }
    }
    
    /**
     * 断言表达式非真<br/>
     * <功能详细描述>
     * @param expression 传入的检查是否为空的对象
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notTrue(boolean expression, String messagePattern,
            String... parameters) {
        notTrue(expression, -1, null, messagePattern, (Object[]) parameters);
    }
    
    /**
     * 断言表达式非真<br/>
     * <功能详细描述>
     * @param expression 传入的检查是否为空的对象
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notTrue(boolean expression, String messagePattern,
            Object[] parameters) {
        notTrue(expression, -1, null, messagePattern, parameters);
    }
    
    /**
     * 断言表达式非真<br/>
     * <功能详细描述>
     * @param expression 传入的检查是否为空的对象
     * @param error 传入的ErrorCode对象实例，如果在错误信息注册表中存在对应的错误实现类，则自动根据参数实例化对应的类实例
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notTrue(boolean expression, ErrorCode error,
            String messagePattern, String... parameters) {
        int errorCode = error == null ? -1 : error.getCode();
        notTrue(expression, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言表达式非真<br/>
     * <功能详细描述>
     * @param expression 传入的检查是否为空的对象
     * @param error 传入的ErrorCode对象实例，如果在错误信息注册表中存在对应的错误实现类，则自动根据参数实例化对应的类实例
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notTrue(boolean expression, ErrorCode error,
            String messagePattern, Object[] parameters) {
        int errorCode = error == null ? -1 : error.getCode();
        notTrue(expression, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言表达式非真<br/>
     * <功能详细描述>
     * @param expression 传入的检查是否为空的对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notTrue(boolean expression, int errorCode,
            String messagePattern, String... parameters) {
        notTrue(expression, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言表达式非真<br/>
     * <功能详细描述>
     * @param expression 传入的检查是否为空的对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notTrue(boolean expression, int errorCode,
            String messagePattern, Object[] parameters) {
        notTrue(expression, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言表达式非真<br/>
     * <功能详细描述>
     * @param expression 传入的检查是否为空的对象
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notTrue(boolean expression,
            Class<? extends SILException> type, String messagePattern,
            String... parameters) {
        notTrue(expression, -1, type, messagePattern, parameters);
    }
    
    /**
     * 断言表达式非真<br/>
     * <功能详细描述>
     * @param expression 传入的检查是否为空的对象
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notTrue(boolean expression,
            Class<? extends SILException> type, String messagePattern,
            Object[] parameters) {
        notTrue(expression, -1, type, messagePattern, parameters);
    }
    
    /**
     * 断言表达式非真<br/>
     * <功能详细描述>
     * @param expression 传入的检查是否为空的对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void notTrue(boolean expression, int errorCode,
            Class<? extends SILException> type, String messagePattern,
            Object[] parameters) {
        if (expression) {
            //如果为空则抛出异常
            String message = MessageUtils.format(messagePattern, parameters);
            throw SILExceptionHelper.newSILException(errorCode,
                    message,
                    null,
                    type == null ? ArgIllegalException.class : type);
        }
    }
    
    /**
     * 断言表达式为真<br/>
     * <功能详细描述>
     * @param expression 传入的表达式boolean值
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isTrue(boolean expression, String messagePattern,
            String... parameters) {
        isTrue(expression, -1, null, messagePattern, (Object[]) parameters);
    }
    
    /**
     * 断言表达式为真<br/>
     * <功能详细描述>
     * @param expression 传入的表达式boolean值
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isTrue(boolean expression, String messagePattern,
            Object[] parameters) {
        isTrue(expression, -1, null, messagePattern, parameters);
    }
    
    /**
     * 断言表达式为真<br/>
     * <功能详细描述>
     * @param expression 传入的表达式boolean值
     * @param error 传入的ErrorCode对象实例，如果在错误信息注册表中存在对应的错误实现类，则自动根据参数实例化对应的类实例
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isTrue(boolean expression, ErrorCode error,
            String messagePattern, String... parameters) {
        int errorCode = error == null ? -1 : error.getCode();
        isTrue(expression, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言表达式为真<br/>
     * <功能详细描述>
     * @param expression 传入的表达式boolean值
     * @param error 传入的ErrorCode对象实例，如果在错误信息注册表中存在对应的错误实现类，则自动根据参数实例化对应的类实例
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isTrue(boolean expression, ErrorCode error,
            String messagePattern, Object[] parameters) {
        int errorCode = error == null ? -1 : error.getCode();
        isTrue(expression, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言表达式为真<br/>
     * <功能详细描述>
     * @param expression 传入的表达式boolean值
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isTrue(boolean expression, int errorCode,
            String messagePattern, String... parameters) {
        isTrue(expression, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言表达式为真<br/>
     * <功能详细描述>
     * @param expression 传入的表达式boolean值
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isTrue(boolean expression, int errorCode,
            String messagePattern, Object[] parameters) {
        isTrue(expression, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言表达式为真<br/>
     * <功能详细描述>
     * @param expression 传入的表达式boolean值
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isTrue(boolean expression,
            Class<? extends SILException> type, String messagePattern,
            String... parameters) {
        isTrue(expression, -1, type, messagePattern, parameters);
    }
    
    /**
     * 断言表达式为真<br/>
     * <功能详细描述>
     * @param expression 传入的表达式boolean值
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isTrue(boolean expression,
            Class<? extends SILException> type, String messagePattern,
            Object[] parameters) {
        isTrue(expression, -1, type, messagePattern, parameters);
    }
    
    /**
     * 断言表达式为真<br/>
     * <功能详细描述>
     * @param expression 传入的表达式boolean值
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isTrue(boolean expression, int errorCode,
            Class<? extends SILException> type, String messagePattern,
            Object[] parameters) {
        if (!expression) {
            //如果为空则抛出异常
            String message = MessageUtils.format(messagePattern, parameters);
            throw SILExceptionHelper.newSILException(errorCode,
                    message,
                    null,
                    type == null ? ArgIllegalException.class : type);
        }
    }
    
    /**
     * 断言资源存在<br/>
     * <功能详细描述>
     * @param resource 传入的检查是否存在资源
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isExist(Resource resource, String messagePattern,
            String... parameters) {
        isExist(resource, -1, null, messagePattern, (Object[]) parameters);
    }
    
    /**
     * 断言资源存在<br/>
     * <功能详细描述>
     * @param resource 传入的检查是否存在资源
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isExist(Resource resource, String messagePattern,
            Object[] parameters) {
        isExist(resource, -1, null, messagePattern, parameters);
    }
    
    /**
     * 断言资源存在<br/>
     * <功能详细描述>
     * @param resource 传入的检查是否存在资源
     * @param error 传入的ErrorCode对象实例，如果在错误信息注册表中存在对应的错误实现类，则自动根据参数实例化对应的类实例
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isExist(Resource resource, ErrorCode error,
            String messagePattern, String... parameters) {
        int errorCode = error == null ? -1 : error.getCode();
        isExist(resource, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言资源存在<br/>
     * <功能详细描述>
     * @param resource 传入的检查是否存在资源
     * @param error 传入的ErrorCode对象实例，如果在错误信息注册表中存在对应的错误实现类，则自动根据参数实例化对应的类实例
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isExist(Resource resource, ErrorCode error,
            String messagePattern, Object[] parameters) {
        int errorCode = error == null ? -1 : error.getCode();
        isExist(resource, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言资源存在<br/>
     * <功能详细描述>
     * @param resource 传入的检查是否存在资源
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isExist(Resource resource, int errorCode,
            String messagePattern, String... parameters) {
        isExist(resource, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言资源存在<br/>
     * <功能详细描述>
     * @param resource 传入的检查是否存在资源
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isExist(Resource resource, int errorCode,
            String messagePattern, Object[] parameters) {
        isExist(resource, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言资源存在<br/>
     * <功能详细描述>
     * @param resource 传入的检查是否存在资源
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isExist(Resource resource,
            Class<? extends SILException> type, String messagePattern,
            String... parameters) {
        isExist(resource, -1, type, messagePattern, parameters);
    }
    
    /**
     * 断言资源存在<br/>
     * <功能详细描述>
     * @param resource 传入的检查是否存在资源
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isExist(Resource resource,
            Class<? extends SILException> type, String messagePattern,
            Object[] parameters) {
        isExist(resource, -1, type, messagePattern, parameters);
    }
    
    /**
     * 断言资源存在<br/>
     * <功能详细描述>
     * @param resource 传入的检查是否存在资源
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isExist(Resource resource, int errorCode,
            Class<? extends SILException> type, String messagePattern,
            Object[] parameters) {
        if (resource == null || !resource.exists()) {
            //如果为空则抛出异常
            String message = MessageUtils.format(messagePattern, parameters);
            throw SILExceptionHelper.newSILException(errorCode,
                    message,
                    null,
                    type == null ? ResourceNotExistException.class : type);
        }
    }
    
    /**
     * 断言文件存在<br/>
     * <功能详细描述>
     * @param file 传入的检查是否存在文件
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isExist(File file, String messagePattern,
            String... parameters) {
        isExist(file, -1, null, messagePattern, (Object[]) parameters);
    }
    
    /**
     * 断言文件存在<br/>
     * <功能详细描述>
     * @param file 传入的检查是否存在文件
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isExist(File file, String messagePattern,
            Object[] parameters) {
        isExist(file, -1, null, messagePattern, parameters);
    }
    
    /**
     * 断言文件存在<br/>
     * <功能详细描述>
     * @param file 传入的检查是否存在文件
     * @param error 传入的ErrorCode对象实例，如果在错误信息注册表中存在对应的错误实现类，则自动根据参数实例化对应的类实例
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isExist(File file, ErrorCode error,
            String messagePattern, String... parameters) {
        int errorCode = error == null ? -1 : error.getCode();
        isExist(file, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言文件存在<br/>
     * <功能详细描述>
     * @param file 传入的检查是否存在文件
     * @param error 传入的ErrorCode对象实例，如果在错误信息注册表中存在对应的错误实现类，则自动根据参数实例化对应的类实例
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isExist(File file, ErrorCode error,
            String messagePattern, Object[] parameters) {
        int errorCode = error == null ? -1 : error.getCode();
        isExist(file, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言文件存在<br/>
     * <功能详细描述>
     * @param file 传入的检查是否存在文件
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isExist(File file, int errorCode, String messagePattern,
            String... parameters) {
        isExist(file, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言文件存在<br/>
     * <功能详细描述>
     * @param file 传入的检查是否存在文件
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isExist(File file, int errorCode, String messagePattern,
            Object[] parameters) {
        isExist(file, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言文件存在<br/>
     * <功能详细描述>
     * @param file 传入的检查是否存在文件
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isExist(File file, Class<? extends SILException> type,
            String messagePattern, String... parameters) {
        isExist(file, -1, type, messagePattern, parameters);
    }
    
    /**
     * 断言文件存在<br/>
     * <功能详细描述>
     * @param file 传入的检查是否存在文件
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isExist(File file, Class<? extends SILException> type,
            String messagePattern, Object[] parameters) {
        isExist(file, -1, type, messagePattern, parameters);
    }
    
    /**
     * 断言文件存在<br/>
     * <功能详细描述>
     * @param file 传入的检查是否存在文件
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isExist(File file, int errorCode,
            Class<? extends SILException> type, String messagePattern,
            Object[] parameters) {
        if (file == null || !file.exists()) {
            //如果为空则抛出异常
            String message = MessageUtils.format(messagePattern, parameters);
            throw SILExceptionHelper.newSILException(errorCode,
                    message,
                    null,
                    type == null ? ResourceNotExistException.class : type);
        }
    }
    
    /**
     * 断言对象为指定类的实现<br/>
     * <功能详细描述>
     * @param clazz 验证类型（不能为空）
     * @param obj 待验证对象
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isInstanceOf(Class<?> clazz, Object obj,
            String messagePattern, String... parameters) {
        isInstanceOf(clazz,
                obj,
                -1,
                null,
                messagePattern,
                (Object[]) parameters);
    }
    
    /**
     * 断言对象为指定类的实现<br/>
     * <功能详细描述>
     * @param clazz 验证类型（不能为空）
     * @param obj 待验证对象
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isInstanceOf(Class<?> clazz, Object obj,
            String messagePattern, Object[] parameters) {
        isInstanceOf(clazz, obj, -1, null, messagePattern, parameters);
    }
    
    /**
     * 断言对象为指定类的实现<br/>
     * <功能详细描述>
     * @param clazz 验证类型（不能为空）
     * @param obj 待验证对象
     * @param error 传入的ErrorCode对象实例，如果在错误信息注册表中存在对应的错误实现类，则自动根据参数实例化对应的类实例
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isInstanceOf(Class<?> clazz, Object obj,
            ErrorCode error, String messagePattern, String... parameters) {
        int errorCode = error == null ? -1 : error.getCode();
        isInstanceOf(clazz, obj, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言对象为指定类的实现<br/>
     * <功能详细描述>
     * @param clazz 验证类型（不能为空）
     * @param obj 待验证对象
     * @param error 传入的ErrorCode对象实例，如果在错误信息注册表中存在对应的错误实现类，则自动根据参数实例化对应的类实例
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isInstanceOf(Class<?> clazz, Object obj,
            ErrorCode error, String messagePattern, Object[] parameters) {
        int errorCode = error == null ? -1 : error.getCode();
        isInstanceOf(clazz, obj, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言对象为指定类的实现<br/>
     * <功能详细描述>
     * @param clazz 验证类型（不能为空）
     * @param obj 待验证对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isInstanceOf(Class<?> clazz, Object obj, int errorCode,
            String messagePattern, String... parameters) {
        isInstanceOf(clazz, obj, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言对象为指定类的实现<br/>
     * <功能详细描述>
     * @param clazz 验证类型（不能为空）
     * @param obj 待验证对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isInstanceOf(Class<?> clazz, Object obj, int errorCode,
            String messagePattern, Object[] parameters) {
        isInstanceOf(clazz, obj, errorCode, null, messagePattern, parameters);
    }
    
    /**
     * 断言对象为指定类的实现<br/>
     * <功能详细描述>
     * @param clazz 验证类型（不能为空）
     * @param obj 待验证对象
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isInstanceOf(Class<?> clazz, Object obj,
            Class<? extends SILException> type, String messagePattern,
            String... parameters) {
        isInstanceOf(clazz, obj, -1, type, messagePattern, parameters);
    }
    
    /**
     * 断言对象为指定类的实现<br/>
     * <功能详细描述>
     * @param clazz 验证类型（不能为空）
     * @param obj 待验证对象
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isInstanceOf(Class<?> clazz, Object obj,
            Class<? extends SILException> type, String messagePattern,
            Object[] parameters) {
        isInstanceOf(clazz, obj, -1, type, messagePattern, parameters);
    }
    
    /**
     * 断言对象为指定类的实现<br/>
     * <功能详细描述>
     * @param clazz 验证类型（不能为空）
     * @param obj 待验证对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isInstanceOf(Class<?> clazz, Object obj, int errorCode,
            Class<? extends SILException> type, String messagePattern,
            Object[] parameters) {
        notNull(clazz, "clazz is null.");//判断是否是类型的实例，首先类型不能为空
        if (!clazz.isInstance(obj)) {
            //如果为空则抛出异常
            String message = MessageUtils.format(messagePattern, parameters);
            throw SILExceptionHelper.newSILException(errorCode,
                    message,
                    null,
                    type == null ? ArgTypeIllegalException.class : type);
        }
    }
    
    /**
     * 断言对象为指定类的实现<br/>
     * <功能详细描述>
     * @param clazz 验证类型（不能为空）
     * @param obj 待验证对象
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isAssignable(Class<?> superType, Class<?> subType,
            String messagePattern, String... parameters) {
        isAssignable(superType,
                subType,
                -1,
                null,
                messagePattern,
                (Object[]) parameters);
    }
    
    /**
     * 断言对象为指定类的实现<br/>
     * <功能详细描述>
     * @param clazz 验证类型（不能为空）
     * @param obj 待验证对象
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isAssignable(Class<?> superType, Class<?> subType,
            String messagePattern, Object[] parameters) {
        isAssignable(superType, subType, -1, null, messagePattern, parameters);
    }
    
    /**
     * 断言对象为指定类的实现<br/>
     * <功能详细描述>
     * @param clazz 验证类型（不能为空）
     * @param obj 待验证对象
     * @param error 传入的ErrorCode对象实例，如果在错误信息注册表中存在对应的错误实现类，则自动根据参数实例化对应的类实例
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isAssignable(Class<?> superType, Class<?> subType,
            ErrorCode error, String messagePattern, String... parameters) {
        int errorCode = error == null ? -1 : error.getCode();
        isAssignable(superType,
                subType,
                errorCode,
                null,
                messagePattern,
                parameters);
    }
    
    /**
     * 断言对象为指定类的实现<br/>
     * <功能详细描述>
     * @param clazz 验证类型（不能为空）
     * @param obj 待验证对象
     * @param error 传入的ErrorCode对象实例，如果在错误信息注册表中存在对应的错误实现类，则自动根据参数实例化对应的类实例
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isAssignable(Class<?> superType, Class<?> subType,
            ErrorCode error, String messagePattern, Object[] parameters) {
        int errorCode = error == null ? -1 : error.getCode();
        isAssignable(superType,
                subType,
                errorCode,
                null,
                messagePattern,
                parameters);
    }
    
    /**
     * 断言对象为指定类的实现<br/>
     * <功能详细描述>
     * @param clazz 验证类型（不能为空）
     * @param obj 待验证对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isAssignable(Class<?> superType, Class<?> subType,
            int errorCode, String messagePattern, String... parameters) {
        isAssignable(superType,
                subType,
                errorCode,
                null,
                messagePattern,
                parameters);
    }
    
    /**
     * 断言对象为指定类的实现<br/>
     * <功能详细描述>
     * @param clazz 验证类型（不能为空）
     * @param obj 待验证对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isAssignable(Class<?> superType, Class<?> subType,
            int errorCode, String messagePattern, Object[] parameters) {
        isAssignable(superType,
                subType,
                errorCode,
                null,
                messagePattern,
                parameters);
    }
    
    /**
     * 断言对象为指定类的实现<br/>
     * <功能详细描述>
     * @param clazz 验证类型（不能为空）
     * @param obj 待验证对象
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isAssignable(Class<?> superType, Class<?> subType,
            Class<? extends SILException> type, String messagePattern,
            String... parameters) {
        isAssignable(superType, subType, -1, type, messagePattern, parameters);
    }
    
    /**
     * 断言对象为指定类的实现<br/>
     * <功能详细描述>
     * @param clazz 验证类型（不能为空）
     * @param obj 待验证对象
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isAssignable(Class<?> superType, Class<?> subType,
            Class<? extends SILException> type, String messagePattern,
            Object[] parameters) {
        isAssignable(superType, subType, -1, type, messagePattern, parameters);
    }
    
    /**
     * 断言对象为指定类的实现<br/>
     * <功能详细描述>
     * @param superType 验证类型（不能为空）
     * @param subType 待验证对象
     * @param errorCode 传入的errorCode,如果对应的ErrorCode的实例类存在，则生成的异常为实际对应的类，否则为SILException.并且其中errorCode为传入的值（注：errorCode应>=0）
     * @param type 默认额异常实现类，如果为空，则系统自动选择SILException进行替代
     * @param messagePattern 异常信息Pattern支持占位符
     * @param parameters 信息中占位符的值
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isAssignable(Class<?> superType, Class<?> subType,
            int errorCode, Class<? extends SILException> type,
            String messagePattern, Object[] parameters) {
        notNull(superType, "superType is null.");//首先类型不能为空
        notNull(subType, "subType is null.");//首先类型不能为空
        if (!superType.isAssignableFrom(subType)) {
            //如果为空则抛出异常
            String message = MessageUtils.format(messagePattern, parameters);
            throw SILExceptionHelper.newSILException(errorCode,
                    message,
                    null,
                    type == null ? ArgTypeIllegalException.class : type);
        }
    }
    
    /**
     * 
     * 断言两个实体是否相等
     * 
     * @param srcObj
     * @param tagObj
     * @param message
     * @param parameters
     * 
     * @return void [返回类型说明]
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isEq(Object srcObj, Object tagObj, String message,
            Object[] parameters) {
        if (!((srcObj == null && tagObj == null) || (srcObj != null
                && tagObj != null && srcObj.equals(tagObj)))) {
            throw new ArgIllegalException(
                    MessageUtils.format("srcObj not equal tagObj : " + message,
                            parameters));
        }
    }
    
    /**
     * 
     * 断言两个实体是否相等
     * 
     * @param srcObj
     * @param tagObj
     * @param message
     * @param parameters
     * 
     * @return void [返回类型说明]
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isEq(Object srcObj, Object tagObj, String message,
            String... parameters) {
        isEq(srcObj, tagObj, message, (Object[]) parameters);
    }
    
    /**
     * 
     * 断言两个实体不相等
     * 
     * @param srcObj
     * @param tagObj
     * @param message
     * @param parameters
     * 
     * @return void [返回类型说明]
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isNotEq(Object srcObj, Object tagObj, String message,
            Object[] parameters) {
        if ((srcObj == null && tagObj == null)
                || (srcObj != null && tagObj != null && srcObj.equals(tagObj))) {
            throw new ArgIllegalException(
                    MessageUtils.format("srcObj equal tagObj : " + message,
                            parameters));
        }
    }
    
    /**
     * 
     * 断言两个实体不相等
     * 
     * @param srcObj
     * @param tagObj
     * @param message
     * @param parameters
     * 
     * @return void [返回类型说明]
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void isNotEq(Object srcObj, Object tagObj, String message,
            String... parameters) {
        isNotEq(srcObj, tagObj, message, (Object[]) parameters);
    }
}
