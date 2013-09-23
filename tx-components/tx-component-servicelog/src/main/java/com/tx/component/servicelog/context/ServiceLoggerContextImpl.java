/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-22
 * <修改描述:>
 */
package com.tx.component.servicelog.context;

import java.util.Date;

import com.tx.component.servicelog.logger.ServiceLogDecorate;
import com.tx.component.servicelog.logger.ServiceLogQuerier;
import com.tx.component.servicelog.logger.ServiceLogPersister;
import com.tx.component.servicelog.logger.ServiceLoggerContext;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * 业务日志容器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ServiceLoggerContextImpl<T> extends ServiceLoggerContextFactory
        implements ServiceLoggerContext<T> {
    
    /** 业务日志实例装饰器 */
    private ServiceLogDecorate serviceLogDecorate;
    
    /** 业务日志查询器 */
    private ServiceLogQuerier<T> serviceLogQuerier;
    
    /** 业务日志记录器 */
    private ServiceLogPersister serviceLogPersister;
    
    /** <默认构造函数> */
    protected ServiceLoggerContextImpl() {
    }
    
    /** <默认构造函数> */
    ServiceLoggerContextImpl(ServiceLogDecorate serviceLogDecorate,
            ServiceLogQuerier<T> serviceLogQuerier,
            ServiceLogPersister serviceLogPersister) {
        super();
        this.serviceLogDecorate = serviceLogDecorate;
        this.serviceLogQuerier = serviceLogQuerier;
        this.serviceLogPersister = serviceLogPersister;
    }
    
    /**
     * @param key
     * @param value
     */
    @Override
    public void setAttribute(String key, Object value) {
        ServiceLoggerSessionContext.getContext().setAttribute(key, value);
    }
    
    /**
     * @param key
     * @return
     */
    @Override
    public Object getAttribute(String key) {
        Object res = ServiceLoggerSessionContext.getContext().getAttribute(key);
        return res;
    }
    
    /**
     * @param serviceLogInstance
     */
    @Override
    public void log(Object serviceLogInstance) {
        AssertUtils.notNull(serviceLogInstance, "serviceLogInstance is null");
        
        //利用业务日志装饰器，装饰日志实例
        Object logInstance = serviceLogDecorate.decorate(serviceLogInstance);
        //将业务日志实例进行持久
        serviceLogPersister.persist(logInstance);
    }
    
    /**
     * @param minCreateDate
     * @param maxCreateDate
     * @return
     */
    @Override
    public PagedList<T> query(Date minCreateDate, Date maxCreateDate) {
        PagedList<T> res = serviceLogQuerier.queryPagedList(minCreateDate,
                maxCreateDate);
        return res;
    }
    
}
