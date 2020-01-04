package com.tx.component.servicelogger.support;

import java.lang.reflect.Type;
import java.util.List;

import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;

/**
 * 业务日志接口
 * 
 * @author brady
 * @version [版本号, 2013-9-23]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ServiceLogger<T> {
    
    /**
     * 业务日志类型<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Class<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Type getLoggerType();
    
    /**
     * 批量插入对象实体
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchInsert(List<T> serviceLogList);
    
    /**
     * 记录业务日志<br/>
     * 
     * @param serviceLog [参数说明]
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract void insert(T serviceLog);
    
    /**
     * 查询对象实体<br/>
     * <功能详细描述>
     * @param condition
     * 
     * @return T [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public T find(T condition);
    
    /**
     * 根据条件查询T列表
     * auto generate
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return List<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<T> queryList(Querier querier);
    
    /**
     * 根据条件查询T列表总数
     * auto generated
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public int count(Querier querier);
    
    /**
     * 分页查询T列表
     * auto generate
     * <功能详细描述>
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return [参数说明]
     * 
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<T> queryPagedList(Querier querier,
            int pageIndex, int pageSize);
}