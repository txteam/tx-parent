/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月3日
 * <修改描述:>
 */
package com.tx.core.mybatis.mapper;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

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
public interface MapperEntityDao<T> {
    
    /**
     * 获取泛型类型<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Type [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Type getEntityType();
    
    /**
     * 批量插入对象实体
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchInsert(List<T> condition);
    
    /**
     * 插入对象实体<br/>
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public void insert(T condition);
    
    /**
     * 删除对象实体<br/>
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return int [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public int delete(T condition);
    
    /**
     * 批量删除对象<br/>
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchDelete(List<T> condition);
    
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
    //  /**
    //     * 根据指定查询条件以及排序列查询T列表
    //     * auto generate
    //     * <功能详细描述>
    //     * @param params
    //     * @param orderList
    //     * @return [参数说明]
    //     * 
    //     * @return List<T> [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public List<T> queryList(Map<String, Object> params, List<Order> orderList);
    //
    //    /**
    //     * 分页查询T列表，传入排序字段
    //     * auto generate
    //     * <功能详细描述>
    //     * @param params
    //     * @param pageIndex
    //     * @param pageSize
    //     * @param orderList
    //     * @return [参数说明]
    //     * 
    //     * @return PagedList<T> [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public PagedList<T> queryPagedList(Map<String, Object> params,
    //            int pageIndex, int pageSize, List<Order> orderList);
    
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
}
