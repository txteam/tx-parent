/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-23
 * <修改描述:>
 */
package com.tx.component.servicelogger.context;

import org.springframework.beans.factory.BeanNameAware;

/**
 * 业务日志工厂
 * 
 * @author brady
 * @version [版本号, 2013-9-23]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ServiceLoggerBuilder extends ServiceLoggerConfigurator
        implements BeanNameAware {
    
    protected static String beanName;
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        ServiceLoggerBuilder.beanName = name;
    }
    
}
