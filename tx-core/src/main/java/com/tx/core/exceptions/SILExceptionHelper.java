/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年2月11日
 * <修改描述:>
 */
package com.tx.core.exceptions;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.reflect.ConstructorUtils;

/**
 * SILException的辅助类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年2月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class SILExceptionHelper {
    
    /** 错误编码注册表 */
    private static final ErrorCodeRegistry ERROR_CODE_REGISTRY = ErrorCodeRegistry.INSTANCE;
    
    /**
      * 生成SILException<br/>
      * <功能详细描述>
      * @param message
      * @param args
      * @return [参数说明]
      * 
      * @return SILException [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static SILException newSILException(String message, Class<? extends SILException> defaultType) {
        SILException e = doNewSILException(-1, message, null, defaultType);
        return e;
    }
    
    /**
      * 生成SILException异常<br/>
      * <功能详细描述>
      * @param message
      * @param cause
      * @return [参数说明]
      * 
      * @return SILException [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static SILException newSILException(String message, Throwable cause,
            Class<? extends SILException> defaultType) {
        SILException e = doNewSILException(-1, message, cause, defaultType);
        return e;
    }
    
    /**
      * 创建指定的异常实例<br/>
      * <功能详细描述>
      * @param errorCode
      * @param message
      * @return [参数说明]
      * 
      * @return SILException [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static SILException newSILException(int errorCode, String message,
            Class<? extends SILException> defaultType) {
        SILException e = doNewSILException(errorCode, message, null, defaultType);
        return e;
    }
    
    /**
      * 创建指定的异常实例<br/>
      * <功能详细描述>
      * @param errorCode
      * @param message
      * @param cause
      * @return [参数说明]
      * 
      * @return SILException [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static SILException newSILException(int errorCode, String message, Throwable cause,
            Class<? extends SILException> defaultType) {
        SILException e = doNewSILException(errorCode, message, cause, defaultType);
        return e;
    }
    
    /**
      * 创建SILException实例<br/>
      * <功能详细描述>
      * @param errorCode
      * @param message
      * @param cause
      * @return [参数说明]
      * 
      * @return SILException [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static SILException doNewSILException(int errorCode, String message, Throwable cause,
            Class<? extends SILException> defaultType) {
        Class<? extends SILException> type = ERROR_CODE_REGISTRY.getErrorType(errorCode);
        if (defaultType == null) {
            defaultType = SILException.class;
        }
        SILException exception = null;
        if (type == null && errorCode >= 0) {
            //errorCode >= 0时，直接使用SILException
            if (cause == null) {
                exception = new SILException(message);
            } else {
                exception = new SILException(message, cause);
            }
        } else if (type == null) {
            //errorCode < 0时，使用defaultType对应的类
            if (cause == null) {
                try {
                    exception = ConstructorUtils.invokeConstructor(defaultType, message);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
                        | InstantiationException e1) {
                    exception = new SILException(message);
                }
            } else {
                try {
                    exception = ConstructorUtils.invokeConstructor(defaultType, message, cause);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
                        | InstantiationException e1) {
                    exception = new SILException(message, cause);
                }
            }
        } else {
            if (cause == null) {
                try {
                    exception = ConstructorUtils.invokeConstructor(type, message);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
                        | InstantiationException e) {
                    try {
                        exception = ConstructorUtils.invokeConstructor(defaultType, message);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
                            | InstantiationException e1) {
                        exception = new SILException(message);
                    }
                }
            } else {
                try {
                    exception = ConstructorUtils.invokeConstructor(type, message, cause);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
                        | InstantiationException e) {
                    try {
                        exception = ConstructorUtils.invokeConstructor(defaultType, message);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
                            | InstantiationException e1) {
                        exception = new SILException(message);
                    }
                }
            }
        }
        exception.setErrorCode(errorCode);
        return exception;
    }
}
