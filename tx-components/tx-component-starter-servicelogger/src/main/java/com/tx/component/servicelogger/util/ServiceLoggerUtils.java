/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年1月7日
 * <修改描述:>
 */
package com.tx.component.servicelogger.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import com.tx.component.servicelogger.context.ServiceLoggerContext;
import com.tx.component.servicelogger.context.ServiceLoggerSessionUtils;
import com.tx.component.servicelogger.support.ServiceLogger;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 业务日志工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class ServiceLoggerUtils {
    
    /**
     * 记录日志<br>
     * @param log 日志
     * @param logObjectType 日志类型
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年12月17日]
     * @author rain
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> void log(T log) {
        AssertUtils.notNull(log, "log is null.");
        
        ServiceLogger logger = ServiceLoggerContext.getContext()
                .getLogger(log.getClass());
        AssertUtils.notNull(logger,
                "logger is null.logClass:{}",
                log.getClass().getName());
        logger.insert(log);
    }
    
    /**
     * 记录日志<br>
     * @param log 日志
     * @param logObjectType 日志类型
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年12月17日]
     * @author rain
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> void logs(List<T> logs) {
        if (CollectionUtils.isEmpty(logs)) {
            return;
        }
        Class<T> type = (Class<T>) logs.get(0).getClass();
        AssertUtils.notNull(type, "type is null.");
        ServiceLogger logger = ServiceLoggerContext.getContext()
                .getLogger(type);
        AssertUtils.notNull(logger,
                "logger is null.logClass:{}",
                type.getName());
        
        logger.batchInsert(logs);
    }
    
    /**
     * 设置在当前事务中的会话参数<br/>
     * <功能详细描述>
     * @param key
     * @param value [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static void setAttribute(String key, Object value) {
        AssertUtils.notEmpty(key, "key is empty.");
        AssertUtils.notEmpty(value, "value is empty.");
        
        ServiceLoggerSessionUtils.getLoggerSession().setAttribute(key, value);
    }
    
    /**
     * 设置在当前事务中的会话参数<br/>
     * <功能详细描述>
     * @param key
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static Object getAttribute(String key) {
        AssertUtils.notEmpty(key, "key is empty.");
        
        Object value = ServiceLoggerSessionUtils.getLoggerSession()
                .getAttribute(key);
        return value;
    }
    
    /**
     * 设置在当前事务中的会话参数<br/>
     * <功能详细描述>
     * @param key
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static Map<String, Object> getAttributes() {
        Map<String, Object> map = ServiceLoggerSessionUtils.getLoggerSession()
                .getAttributes();
        return map;
    }
}
