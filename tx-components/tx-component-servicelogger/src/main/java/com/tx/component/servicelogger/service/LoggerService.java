package com.tx.component.servicelogger.service;

import java.util.Map;

import com.tx.core.paged.model.PagedList;

/**
 * 业务日志接口
 * 
 * @author brady
 * @version [版本号, 2013-9-23]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface LoggerService<T> {
    
    /**
     * 业务日志类型<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Class<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Class<T> type();
    
    /**
     * 记录业务日志<br/>
     * 
     * @param serviceLogInstance [参数说明]
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract void insert(T serviceLogInstance);
    
    /**
     * 分页查询业务日志<br/>
     * 
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return [参数说明]
     *         
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract PagedList<T> queryPagedList(Map<String, Object> params, int pageIndex, int pageSize);
    
    /**
     * 日志查询<br/>
     *
     * @param id 日志 id
     *            
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月23日]
     * @author rain
     */
    public abstract T find(String id);
    
}