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
import com.tx.component.workflow.model.ProcessDefinition;

/**
 * ProcessDefinition持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ProcessDefinitionDao {
    
    /**
      * 插入ProcessDefinition对象实体
      * 1、auto generate
      * <功能详细描述>
      * @param condition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public void insertProcessDefinition(ProcessDefinition condition);
    
    /**
      * 删除ProcessDefinition对象
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
    public int deleteProcessDefinition(ProcessDefinition condition);
    
    /**
      * 查询ProcessDefinition实体
      * auto generate
      * <功能详细描述>
      * @param condition
      * @return [参数说明]
      * 
      * @return ProcessDefinition [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public ProcessDefinition findProcessDefinition(ProcessDefinition condition);
    
    /**
      * 根据条件查询ProcessDefinition列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return List<ProcessDefinition> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<ProcessDefinition> queryProcessDefinitionList(Map<String, Object> params);
    
    /**
      * 根据指定查询条件以及排序列查询ProcessDefinition列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @param orderList
      * @return [参数说明]
      * 
      * @return List<ProcessDefinition> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<ProcessDefinition> queryProcessDefinitionList(Map<String, Object> params,
            List<Order> orderList);
    
    /**
      * 根据条件查询ProcessDefinition列表总数
      * auto generated
      * <功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int countProcessDefinition(Map<String, Object> params);
    
    /**
      * 分页查询ProcessDefinition列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @param pageIndex
      * @param pageSize
      * @return [参数说明]
      * 
      * @return PagedList<ProcessDefinition> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public PagedList<ProcessDefinition> queryProcessDefinitionPagedList(Map<String, Object> params,
            int pageIndex, int pageSize);
    
    /**
      * 分页查询ProcessDefinition列表，传入排序字段
      * auto generate
      * <功能详细描述>
      * @param params
      * @param pageIndex
      * @param pageSize
      * @param orderList
      * @return [参数说明]
      * 
      * @return PagedList<ProcessDefinition> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public PagedList<ProcessDefinition> queryProcessDefinitionPagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList);
    
    
    /**
      * 更新ProcessDefinition实体，
      * auto generate
      * 1、传入ProcessDefinition中主键不能为空
      * <功能详细描述>
      * @param updateProcessDefinitionRowMap
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int updateProcessDefinition(Map<String, Object> updateRowMap);
}
