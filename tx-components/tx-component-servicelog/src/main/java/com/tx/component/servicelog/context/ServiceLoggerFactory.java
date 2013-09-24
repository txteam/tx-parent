/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-23
 * <修改描述:>
 */
package com.tx.component.servicelog.context;

import java.util.HashMap;
import java.util.Map;

import com.tx.component.servicelog.logger.ServiceLogger;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 业务日志容器句柄
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ServiceLoggerFactory extends ServiceLoggerConfigurator {
    
    /** 业务日志实例工厂 */
    protected static ServiceLoggerBuilder serviceLoggerBuilder;
    
    private static Map<Class<?>, ServiceLogger<?>> serviceLoggerMapping = new HashMap<Class<?>, ServiceLogger<?>>();
    
    @SuppressWarnings("unchecked")
    public static <T> ServiceLogger<T> getLogger(Class<T> srcObjType) {
        //根据类型构建
        AssertUtils.notNull(serviceLoggerBuilder,
                "serviceLoggerContextBuilder is null");
        AssertUtils.notNull(srcObjType,
                "init ServiceLogContext instance fail. type is null");
        
        ServiceLogger<T> resLogger = null;
        //从如果已经存在实例化的logger则直接提取
        if (serviceLoggerMapping.containsKey(srcObjType)) {
            resLogger = (ServiceLogger<T>) serviceLoggerMapping.get(srcObjType);
        } else {
            resLogger = serviceLoggerBuilder.build(srcObjType,
                    dataSourceType,
                    jdbcTemplate);
            serviceLoggerMapping.put(srcObjType, resLogger);
        }
        
        return resLogger;
    }

    /**
     * @param 对serviceLoggerBuilder进行赋值
     */
    public void setServiceLoggerBuilder(
            ServiceLoggerBuilder serviceLoggerBuilder) {
        ServiceLoggerFactory.serviceLoggerBuilder = serviceLoggerBuilder;
    }
}
