/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.auth.dao;

import java.util.List;
import java.util.Map;

import com.tx.core.mybatis.model.Order;
import com.tx.core.paged.model.PagedList;
import com.tx.component.auth.model.AuthItemRefImpl;

/**
 * AuthItemRefImpl持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface AuthItemRefImplDao {
    
    /**
      * 插入AuthItemRefImpl对象实体
      * 1、auto generate
      * <功能详细描述>
      * @param condition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public void insertAuthItemRefImpl(AuthItemRefImpl condition);
    
    /**
      * 批量插入权限项引用
      * <功能详细描述>
      * @param condition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void batchInsertAuthItemRefImpl(List<AuthItemRefImpl> condition);
    
    /**
      * 删除AuthItemRefImpl对象
      * 1、auto generate
      * 2、根据入参条件进行删除
      * <功能详细描述>
      * @param condition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public int deleteAuthItemRefImpl(AuthItemRefImpl condition);
    
    /**
      * 批量删除权限项目引用
      * <功能详细描述>
      * @param authItemRefImplList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void batchDeleteAuthItemRefImpl(List<AuthItemRefImpl> authItemRefImplList);
    
    /**
      * 查询AuthItemRefImpl实体
      * auto generate
      * <功能详细描述>
      * @param condition
      * @return [参数说明]
      * 
      * @return AuthItemRefImpl [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public AuthItemRefImpl findAuthItemRefImpl(AuthItemRefImpl condition);
    
    /**
      * 根据条件查询AuthItemRefImpl列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return List<AuthItemRefImpl> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<AuthItemRefImpl> queryAuthItemRefImplList(Map<String, Object> params);
    
    /**
      * 根据指定查询条件以及排序列查询AuthItemRefImpl列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @param orderList
      * @return [参数说明]
      * 
      * @return List<AuthItemRefImpl> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<AuthItemRefImpl> queryAuthItemRefImplList(Map<String, Object> params,
            List<Order> orderList);
    
    /**
      * 根据条件查询AuthItemRefImpl列表总数
      * auto generated
      * <功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int countAuthItemRefImpl(Map<String, Object> params);
    
    /**
      * 分页查询AuthItemRefImpl列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @param pageIndex
      * @param pageSize
      * @return [参数说明]
      * 
      * @return PagedList<AuthItemRefImpl> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public PagedList<AuthItemRefImpl> queryAuthItemRefImplPagedList(Map<String, Object> params,
            int pageIndex, int pageSize);
    
    /**
      * 分页查询AuthItemRefImpl列表，传入排序字段
      * auto generate
      * <功能详细描述>
      * @param params
      * @param pageIndex
      * @param pageSize
      * @param orderList
      * @return [参数说明]
      * 
      * @return PagedList<AuthItemRefImpl> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public PagedList<AuthItemRefImpl> queryAuthItemRefImplPagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList);
    
    
    /**
      * 更新AuthItemRefImpl实体，
      * auto generate
      * 1、传入AuthItemRefImpl中主键不能为空
      * <功能详细描述>
      * @param updateAuthItemRefImplRowMap
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int updateAuthItemRefImpl(Map<String, Object> updateRowMap);
    
    /**
      * 批量更新权限项引用
      * <功能详细描述>
      * @param updateRowMapList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void batchUpdateAuthItemRefImpl(List<Map<String, Object>> updateRowMapList);
}
