/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年2月11日
 * <修改描述:>
 */
package com.tx.core.exceptions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.tx.core.util.ClassScanUtils;

/**
 * 错误编码注册表<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年2月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ErrorCodeRegistry implements InitializingBean {
    
    private static Logger logger = LoggerFactory
            .getLogger(ErrorCodeRegistry.class);
    
    /** 错误编码注册表 */
    public static final ErrorCodeRegistry INSTANCE = new ErrorCodeRegistry();
    
    /** 错误编码映射 */
    private final Map<Integer, String> code2messageMap;
    
    /** 错误编码映射 */
    private final Map<Integer, Class<? extends SILException>> code2typeMap;
    
    /** <默认构造函数> */
    public ErrorCodeRegistry() {
        super();
        this.code2messageMap = new HashMap<Integer, String>();
        this.code2typeMap = new HashMap<Integer, Class<? extends SILException>>();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //根据异常编码枚举类初始化异常错误信息<br/>
        initByErrorCodeEnumTypes();
        
        //根据定义的异常进行异常初始化<br/>
        initBySILExceptionTypes();
    }
    
    /** 启动根据封装的错误编码与错误消息之间的映射 */
    public final void initByErrorCodeEnumTypes() {
        Set<Class<? extends ErrorCode>> errorCodeClassSet = ClassScanUtils
                .scanByParentClass(ErrorCode.class, "com.tx");
        for (Class<? extends ErrorCode> classTemp : errorCodeClassSet) {
            if (!classTemp.isEnum()) {
                continue;
            }
            ErrorCode[] errorCodes = classTemp.getEnumConstants();
            for (ErrorCode errorCodeTemp : errorCodes) {
                registeErrorCode(errorCodeTemp);
            }
        }
    }
    
    /** 启动根据封装的异常类进行初始化 */
    public final void initBySILExceptionTypes() {
        Set<Class<? extends SILException>> silExceptionClassSet = ClassScanUtils
                .scanByParentClass(SILException.class, "com.tx");
        for (Class<? extends SILException> classTemp : silExceptionClassSet) {
            if (SILException.class.equals(classTemp)
                    || Modifier.isAbstract(classTemp.getModifiers())) {
                continue;
            }
            
            //如果有无参构造函数，则读取对应的错误编码，以及错误信息进行注册
            Constructor<? extends SILException> constructor = ConstructorUtils
                    .getMatchingAccessibleConstructor(classTemp);
            if (constructor == null) {
                continue;
            }
            try {
                SILException exceptionInstance = ConstructorUtils
                        .invokeConstructor(classTemp);
                //注册错误编码
                registeErrorCode(exceptionInstance.errorCode(),
                        exceptionInstance.errorMessage(),
                        classTemp);
            } catch (NoSuchMethodException | IllegalAccessException
                    | InvocationTargetException | InstantiationException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
    
    /**
     * 注册错误编码<br/>
     * <功能详细描述>
     * @param code
     * @param errorCode [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void registeErrorCode(ErrorCode errorCode) {
        if (errorCode == null || errorCode.getCode() < 0) {
            return;
        }
        this.code2messageMap.put(errorCode.getCode(), errorCode.getMessage());
    }
    
    public static void main(String[] args) {
        Map<String, String> test = new HashMap<String, String>();
        test.put(null, null);
    }
    
    /**
     * 注册错误编码<br/>
     * <功能详细描述>
     * @param code
     * @param errorCode [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void registeErrorCode(Integer errorCode, String errorMessage,
            Class<? extends SILException> exceptionType) {
        if (errorCode == null || errorCode.intValue() < 0) {
            return;
        }
        this.code2messageMap.put(errorCode, errorMessage);
        if (exceptionType != null) {
            this.code2typeMap.put(errorCode, exceptionType);
        }
    }
    
    /**
     * 注册错误编码<br/>
     * <功能详细描述>
     * @param code
     * @param errorCode [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void registeErrorCode(int errorCode, String errorMessage) {
        if (errorCode < 0) {
            return;
        }
        this.code2messageMap.put(errorCode, errorMessage);
    }
    
    /**
      * 根据错误编码获取对应的错误消息<br/>
      * <功能详细描述>
      * @param errorCode
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getErrorMessage(int errorCode) {
        if (errorCode < 0) {
            return null;
        }
        String errorMessage = this.code2messageMap.get(errorCode);
        return errorMessage;
    }
    
    /**
      * 根据错误消息获取错误类型<br/>
      * <功能详细描述>
      * @param errorCode
      * @return [参数说明]
      * 
      * @return Class<? extends SILException> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Class<? extends SILException> getErrorType(int errorCode) {
        if (errorCode < 0) {
            return null;
        }
        Class<? extends SILException> type = this.code2typeMap.get(errorCode);
        return type;
    }
}
