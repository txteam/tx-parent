/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-24
 * <修改描述:>
 */
package com.tx.component.servicelogger.context;

import org.springframework.beans.factory.FactoryBean;

/**
 * 业务日志容器工厂类<br/>
 * 单例
 * 
 * @author brady
 * @version [版本号, 2013-9-24]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ServiceLoggerContextFactory extends ServiceLoggerContext
        implements FactoryBean<ServiceLoggerContext> {
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public ServiceLoggerContext getObject() throws Exception {
        if (ServiceLoggerContext.context == null) {
            return this;
        } else {
            return ServiceLoggerContext.context;
        }
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return ServiceLoggerContext.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
