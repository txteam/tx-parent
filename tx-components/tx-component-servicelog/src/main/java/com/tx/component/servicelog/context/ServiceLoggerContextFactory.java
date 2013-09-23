/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-23
 * <修改描述:>
 */
package com.tx.component.servicelog.context;

import org.springframework.beans.factory.FactoryBean;

import com.tx.component.servicelog.logger.ServiceLoggerContext;

/**
 * 业务日志容器句柄
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ServiceLoggerContextFactory extends ServiceLoggerConfigurator
        implements FactoryBean<ServiceLoggerContext<?>> {
    
    /** 业务日志实例工厂 */
    protected ServiceLoggerContextBuilder serviceLoggerContextBuilder;
    
    public static <T> ServiceLoggerContext<T> getContext(Class<T> srcObjType) {
        if(srcObjType == null){
            
            return null;
        }
        
        //根据类型构建
        
        return null;
    }
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public ServiceLoggerContext<?> getObject() throws Exception {
        return ServiceLoggerContextImpl.getContext(null);
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
        return false;
    }
}
