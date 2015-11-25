/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-23
 * <修改描述:>
 */
package com.tx.component.servicelog.context;

import com.tx.component.servicelog.context.logger.ServiceLogDecorate;
import com.tx.component.servicelog.context.logger.ServiceLogPersister;
import com.tx.component.servicelog.context.logger.ServiceLogQuerier;
import com.tx.component.servicelog.context.logger.ServiceLogger;

/**
 * 基础业务日志容器构建器<br/>
 * 
 * @author bradyB
 * @version [版本号, 2013-9-23]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class BaseServiceLoggerBuilder implements ServiceLoggerBuilder {
    
    /** 排序 */
    protected int order = 0;
    
    @Override
    public <T> ServiceLogger<T> build(Class<T> logObjectType) {
        //构建业务日志持久器
        ServiceLogPersister serviceLogPersister = buildServiceLogPersister(logObjectType);
        
        //构建业务日志装饰器
        ServiceLogDecorate<T> serviceLogDecorate = buildServiceLogDecorate(logObjectType);
        
        //构建业务日志查询器
        ServiceLogQuerier<T> serviceLogQuerier = buildServiceLogQuerier(logObjectType);
        
        //构建业务日志容器
        ServiceLogger<T> serviceLogger = new ServiceLoggerImpl<T>(
                serviceLogDecorate,
                serviceLogQuerier,
                serviceLogPersister);
                
        return serviceLogger;
    }
    
    @Override
    public int order() {
        return order;
    }
    
    /** @param 对 order 进行赋值 */
    public void setOrder(int order) {
        this.order = order;
    }
    
    /**
     * 构建业务日志实例装饰器<br/>
     * 
     * @param logObjectType 日志对象类型
     *            
     * @return ServiceLogDecorate 日志实例装饰器
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected abstract <T> ServiceLogDecorate<T> buildServiceLogDecorate(Class<T> logObjectType);
    
    /**
     * 
     * 构建业务日志记录器<br/>
     *
     * @param logObjectType 日志对象类型
     *            
     * @return ServiceLogPersister 日志记录器
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月23日]
     * @author rain
     */
    protected abstract ServiceLogPersister buildServiceLogPersister(Class<?> logObjectType);
    
    /**
     * 
     * 构建业务日志查询器<br/>
     *
     * @param logObjectType 日志对象类型
     *            
     * @return ServiceLogQuerier<T> 业务日志查询器
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月23日]
     * @author rain
     */
    protected abstract <T> ServiceLogQuerier<T> buildServiceLogQuerier(Class<T> logObjectType);
}
