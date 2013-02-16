/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.workflow.dao;

import java.util.List;
import java.util.Map;

import com.tx.core.mybatis.model.Order;
import com.tx.core.paged.model.PagedList;
import com.tx.component.workflow.model.ProTaskDefinition;

/**
 * ProTaskDefinition持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ProTaskDefinitionDao {
    
    /**
      * 插入ProTaskDefinition对象实体
      * 1、auto generate
      * <功能详细描述>
      * @param condition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public void insertProTaskDefinition(ProTaskDefinition condition);
    
    /**
      * 删除ProTaskDefinition对象
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
    public int deleteProTaskDefinition(ProTaskDefinition condition);
    
    /**
      * 查询ProTaskDefinition实体
      * auto generate
      * <功能详细描述>
      * @param condition
      * @return [参数说明]
      * 
      * @return ProTaskDefinition [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public ProTaskDefinition findProTaskDefinition(ProTaskDefinition condition);
    
    /**
      * 根据条件查询ProTaskDefinition列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return List<ProTaskDefinition> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<ProTaskDefinition> queryProTaskDefinitionList(Map<String, Object> params);
    
    /**
      * 根据指定查询条件以及排序列查询ProTaskDefinition列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @param orderList
      * @return [参数说明]
      * 
      * @return List<ProTaskDefinition> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<ProTaskDefinition> queryProTaskDefinitionList(Map<String, Object> params,
            List<Order> orderList);
    
    /**
      * 根据条件查询ProTaskDefinition列表总数
      * auto generated
      * <功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int countProTaskDefinition(Map<String, Object> params);
    
    /**
      * 分页查询ProTaskDefinition列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @param pageIndex
      * @param pageSize
      * @return [参数说明]
      * 
      * @return PagedList<ProTaskDefinition> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public PagedList<ProTaskDefinition> queryProTaskDefinitionPagedList(Map<String, Object> params,
            int pageIndex, int pageSize);
    
    /**
      * 分页查询ProTaskDefinition列表，传入排序字段
      * auto generate
      * <功能详细描述>
      * @param params
      * @param pageIndex
      * @param pageSize
      * @param orderList
      * @return [参数说明]
      * 
      * @return PagedList<ProTaskDefinition> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public PagedList<ProTaskDefinition> queryProTaskDefinitionPagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList);
    
    
    /**
      * 更新ProTaskDefinition实体，
      * auto generate
      * 1、传入ProTaskDefinition中主键不能为空
      * <功能详细描述>
      * @param updateProTaskDefinitionRowMap
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int updateProTaskDefinition(Map<String, Object> updateRowMap);
}
