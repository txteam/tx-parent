/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-22
 * <修改描述:>
 */
package com.tx.core.exceptions.util;

import java.io.IOException;

import com.tx.core.dbscript.exception.UnexpectIOException;
import com.tx.core.exceptions.SILException;

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
        return new UnexpectIOException(ioException, message, parameters);
    }
}
