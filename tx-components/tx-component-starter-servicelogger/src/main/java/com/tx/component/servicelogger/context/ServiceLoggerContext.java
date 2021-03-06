/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-24
 * <修改描述:>
 */
package com.tx.component.servicelogger.context;

import com.tx.component.servicelogger.support.ServiceLogger;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.MessageUtils;

/**
 * 业务日志容器<br/>
 * 
 * @author brady
 * @version [版本号, 2013-9-24]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ServiceLoggerContext extends ServiceLoggerBuilder {
    
    protected static ServiceLoggerContext context;
    
    /** 日业务日志容器构造方法 */
    protected ServiceLoggerContext() {
        super();
    }
    
    public static ServiceLoggerContext getContext() {
        if (ServiceLoggerContext.context != null) {
            return ServiceLoggerContext.context;
        }
        synchronized (ServiceLoggerContext.class) {
            ServiceLoggerContext.context = applicationContext.getBean(beanName,
                    ServiceLoggerContext.class);
        }
        AssertUtils.notNull(ServiceLoggerContext.context, "context is null.");
        return ServiceLoggerContext.context;
    }
    
    /**
     * 获取日志对象<br/>
     * @param logObjectType
     *            
     * @return ServiceLogger<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月24日]
     * @author rain
     */
    public <T> ServiceLogger<T> getLogger(Class<T> beanType) {
        ServiceLogger<T> logger = serviceLoggerRegistry
                .getServiceLogger(beanType);
        AssertUtils.notNull(logger,
                MessageUtils.format("logger:[{}] is not exists.",
                        new Object[] { beanType }));
        return logger;
    }
    
    
}
