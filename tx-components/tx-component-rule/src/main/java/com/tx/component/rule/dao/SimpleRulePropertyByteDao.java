/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.rule.dao;

import java.util.List;
import java.util.Map;

import com.tx.core.mybatis.model.Order;
import com.tx.core.paged.model.PagedList;
import com.tx.component.rule.model.SimpleRulePropertyByte;

/**
 * SimpleRulePropertyByte持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface SimpleRulePropertyByteDao {
    
    /**
      * 插入SimpleRulePropertyByte对象实体
      * 1、auto generate
      * <功能详细描述>
      * @param condition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public void insertSimpleRulePropertyByte(SimpleRulePropertyByte condition);
    
    /**
      * 批量插入
      * <功能详细描述>
      * @param condition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void batchInsertSimpleRulePropertyByte(List<SimpleRulePropertyByte> condition);
    
    /**
      * 删除SimpleRulePropertyByte对象
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
    public int deleteSimpleRulePropertyByte(SimpleRulePropertyByte condition);
    
    /**
      * 查询SimpleRulePropertyByte实体
      * auto generate
      * <功能详细描述>
      * @param condition
      * @return [参数说明]
      * 
      * @return SimpleRulePropertyByte [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public SimpleRulePropertyByte findSimpleRulePropertyByte(SimpleRulePropertyByte condition);
    
    /**
      * 根据条件查询SimpleRulePropertyByte列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return List<SimpleRulePropertyByte> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<SimpleRulePropertyByte> querySimpleRulePropertyByteList(Map<String, Object> params);
    
    /**
      * 根据指定查询条件以及排序列查询SimpleRulePropertyByte列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @param orderList
      * @return [参数说明]
      * 
      * @return List<SimpleRulePropertyByte> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<SimpleRulePropertyByte> querySimpleRulePropertyByteList(Map<String, Object> params,
            List<Order> orderList);
    
    /**
      * 根据条件查询SimpleRulePropertyByte列表总数
      * auto generated
      * <功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int countSimpleRulePropertyByte(Map<String, Object> params);
    
    /**
      * 分页查询SimpleRulePropertyByte列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @param pageIndex
      * @param pageSize
      * @return [参数说明]
      * 
      * @return PagedList<SimpleRulePropertyByte> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public PagedList<SimpleRulePropertyByte> querySimpleRulePropertyBytePagedList(Map<String, Object> params,
            int pageIndex, int pageSize);
    
    /**
      * 分页查询SimpleRulePropertyByte列表，传入排序字段
      * auto generate
      * <功能详细描述>
      * @param params
      * @param pageIndex
      * @param pageSize
      * @param orderList
      * @return [参数说明]
      * 
      * @return PagedList<SimpleRulePropertyByte> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public PagedList<SimpleRulePropertyByte> querySimpleRulePropertyBytePagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList);
    
    
    /**
      * 更新SimpleRulePropertyByte实体，
      * auto generate
      * 1、传入SimpleRulePropertyByte中主键不能为空
      * <功能详细描述>
      * @param updateSimpleRulePropertyByteRowMap
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int updateSimpleRulePropertyByte(Map<String, Object> updateRowMap);
}
