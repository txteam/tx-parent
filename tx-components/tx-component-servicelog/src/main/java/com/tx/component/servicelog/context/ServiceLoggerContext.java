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
public class ServiceLoggerContext<T> {
    
    /** 业务日志实例装饰器 */
    private ServiceLogDecorate serviceLogDecorate;
    
    /** 业务日志查询器 */
    private ServiceLogQuerier<T> serviceLogQuerier;
    
    /** 业务日志记录器 */
    private ServiceLogPersister serviceLogPersister;
    
    /**
      * 向容器中设置属性值<br/>
      *<功能详细描述>
      * @param key
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setAttribute(String key, Object value) {
        ServiceLoggerSessionContext.getContext().setAttribute(key, value);
    }
    
    /**
      * 获取写入的业务日志属性值<br/>
      *<功能详细描述>
      * @param key
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Object getAttribute(String key) {
        Object res = ServiceLoggerSessionContext.getContext().getAttribute(key);
        return res;
    }
    
    /**
      * 记录业务日志<br/> 
      *<功能详细描述>
      * @param serviceLogInstance [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void log(Object serviceLogInstance) {
        AssertUtils.notNull(serviceLogInstance, "serviceLogInstance is null");
        
        //利用业务日志装饰器，装饰日志实例
        Object logInstance = serviceLogDecorate.decorate(serviceLogInstance);
        //将业务日志实例进行持久
        serviceLogPersister.persist(logInstance);
    }
    
    /**
      * 业务日志查询器<br/>
      *<功能详细描述>
      * @param minCreateDate
      * @param maxCreateDate
      * @return [参数说明]
      * 
      * @return PagedList<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public PagedList<T> query(Date minCreateDate, Date maxCreateDate) {
        PagedList<T> res = serviceLogQuerier.queryPagedList(minCreateDate,
                maxCreateDate);
        return res;
    }
    
}
