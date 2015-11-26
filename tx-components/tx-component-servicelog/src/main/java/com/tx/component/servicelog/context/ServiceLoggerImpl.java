/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-22
 * <修改描述:>
 */
package com.tx.component.servicelog.context;

import java.util.Map;

import com.tx.component.servicelog.context.logger.ServiceLogDecorate;
import com.tx.component.servicelog.context.logger.ServiceLogPersister;
import com.tx.component.servicelog.context.logger.ServiceLogQuerier;
import com.tx.component.servicelog.context.logger.ServiceLogger;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * 业务日志<br/>
 * 
 * @author brady
 * @version [版本号, 2013-9-22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ServiceLoggerImpl<T> extends ServiceLoggerFactory implements ServiceLogger<T> {
    
    /** 业务日志实例装饰器 */
    private ServiceLogDecorate<T> serviceLogDecorate;
    
    /** 业务日志查询器 */
    private ServiceLogQuerier<T> serviceLogQuerier;
    
    /** 业务日志记录器 */
    private ServiceLogPersister<T> serviceLogPersister;
    
    protected ServiceLoggerImpl() {
    }
    
    ServiceLoggerImpl(
            ServiceLogDecorate<T> serviceLogDecorate,
            ServiceLogQuerier<T> serviceLogQuerier,
            ServiceLogPersister<T> serviceLogPersister) {
        super();
        this.serviceLogDecorate = serviceLogDecorate;
        this.serviceLogQuerier = serviceLogQuerier;
        this.serviceLogPersister = serviceLogPersister;
    }
    
    @Override
    public T find(String id) {
        return serviceLogQuerier.find(id);
    }
    
    @Override
    public Object getAttribute(String key) {
        return ServiceLoggerSessionContext.getContext().getAttribute(key);
    }
    
    @Override
    public void log(T serviceLogInstance) {
        AssertUtils.notNull(serviceLogInstance, "serviceLogInstance is null");
        
        //利用业务日志装饰器，装饰日志实例
        T logInstance = serviceLogDecorate.decorate(serviceLogInstance);
        
        //将业务日志实例进行持久
        serviceLogPersister.persist(logInstance);
    }
    
    @Override
    public PagedList<T> queryPagedList(Map<String, Object> params, int pageIndex, int pageSize) {
        PagedList<T> res = serviceLogQuerier.queryPagedList(params, pageIndex, pageSize);
        return res;
    }
    
    @Override
    public void setAttribute(String key, Object value) {
        ServiceLoggerSessionContext.getContext().setAttribute(key, value);
    }
    
}
