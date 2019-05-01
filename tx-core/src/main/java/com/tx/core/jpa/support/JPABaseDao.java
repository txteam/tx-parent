/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月3日
 * <修改描述:>
 */
package com.tx.core.jpa.support;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;

import com.tx.core.mybatis.model.BatchResult;
import com.tx.core.paged.model.PagedList;

/**
 * 实体持久层实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface JPABaseDao<T, ID extends Serializable> {
    
    /**
     * 插入对象实体<br/>
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public void insert(T entity);
    
    /**
     * 批量插入对象实体
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchInsert(List<T> entityList);
    
    /**
     * 批量插入对象<br/>
     * <功能详细描述>
     * @param objectList
     * @param doFlushSize
     * @param isStopWhenException
     * @return [参数说明]
     * 
     * @return BatchResult [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public BatchResult batchInsert(List<T> entityList, int doFlushSize,
            boolean isStopWhenException);
    
    /**
     * 删除对象实体<br/>
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return int [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public int delete(T entity);
    
    /**
     * 批量删除对象<br/>
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchDelete(List<T> entityList);
    
    /**
     * 批量删除<br/>
     * <功能详细描述>
     * @param objectList
     * @param doFlushSize
     * @param isStopWhenException
     * @return [参数说明]
     * 
     * @return BatchResult [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public BatchResult batchDelete(List<T> objectList, int doFlushSize,
            boolean isStopWhenException);
    
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
    public List<T> queryList(Map<String, Object> params);
    
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
    public int count(Map<String, Object> params);
    
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
    public PagedList<T> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize);
    
    /**
     * 更新T实体，
     * auto generate
     * 1、传入T中主键不能为空
     * <功能详细描述>
     * @param updateTRowMap
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int update(Map<String, Object> updateRowMap);
    
    /**
     * 批量更新对象实体<br/>
     *    1、传入T中主键不能为空
     * 
     * <功能详细描述>
     * @param updateTRowMap
     * 
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchUpdate(List<Map<String, Object>> updateRowMapList);
    
    public FlushModeType getFlushMode();
    
    public void lock(Object entity, LockModeType lockMode);
    
    public LockModeType getLockMode(Object entity);
    
    public boolean contains(Object entity);
    
    public void clear();
    
    public void flush();
}
