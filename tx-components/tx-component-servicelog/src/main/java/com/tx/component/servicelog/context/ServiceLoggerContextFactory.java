/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-23
 * <修改描述:>
 */
package com.tx.component.servicelog.context;

import org.springframework.beans.factory.FactoryBean;

import com.tx.component.servicelog.logger.ServiceLoggerContext;
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
public class ServiceLoggerContextFactory extends ServiceLoggerConfigurator
        implements FactoryBean<ServiceLoggerContext<?>> {
    
    /** 业务日志实例工厂 */
    protected static ServiceLoggerContextBuilder serviceLoggerContextBuilder;
    
    private Class<?> type;
    
    public static <T> ServiceLoggerContext<T> getContext(Class<T> srcObjType) {
        //根据类型构建
        AssertUtils.notNull(serviceLoggerContextBuilder,
                "serviceLoggerContextBuilder is null");
        AssertUtils.notNull(srcObjType,
                "init ServiceLogContext instance fail. type is null");
        
        //
        serviceLoggerContextBuilder.build(srcObjType, dataSourceType, dataSource);
        return null;
    }
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public ServiceLoggerContext<?> getObject() throws Exception {
        AssertUtils.notNull(type,
                "init ServiceLogContext instance fail. type is null");
        return ServiceLoggerContextImpl.getContext(type);
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
