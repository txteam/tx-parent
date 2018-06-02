/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-23
 * <修改描述:>
 */
package com.tx.component.servicelogger.context;

import java.util.HashMap;
import java.util.Map;

import com.tx.component.servicelogger.service.LoggerService;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 业务日志工厂
 * 
 * @author brady
 * @version [版本号, 2013-9-23]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ServiceLoggerFactory extends ServiceLoggerBuilderFactory {
    
    /** 日志对象类型和日志映射 */
    private Map<Class<?>, LoggerService<?>> serviceLoggerMapping = new HashMap<Class<?>, LoggerService<?>>();
    
    @SuppressWarnings("unchecked")
    public <T> LoggerService<T> getServiceLogger(Class<T> logObjectType) {
        AssertUtils.notNull(logObjectType, "init ServiceLogContext instance fail. type is null");
        
        //从如果已经存在实例化的logger则直接提取
        if (serviceLoggerMapping.containsKey(logObjectType)) {
            return (LoggerService<T>) serviceLoggerMapping.get(logObjectType);
        } else {
            ServiceLoggerBuilder serviceLoggerBuilder = buildServiceLoggerBuilder(logObjectType);
            LoggerService<T> resLogger = serviceLoggerBuilder.build(logObjectType);
            serviceLoggerMapping.put(logObjectType, resLogger);
            return resLogger;
        }
    }
}
