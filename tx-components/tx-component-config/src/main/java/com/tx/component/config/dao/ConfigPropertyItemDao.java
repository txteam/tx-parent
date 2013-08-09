/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.config.dao;

import java.util.List;
import java.util.Map;

import com.tx.component.config.model.ConfigPropertyItem;
import com.tx.core.mybatis.model.Order;

/**
 * ConfigPropertyItem持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ConfigPropertyItemDao {
    
    /**
      * 插入ConfigPropertyItem对象实体
      * 1、auto generate
      * <功能详细描述>
      * @param condition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void insertConfigPropertyItem(ConfigPropertyItem configPropertyItem);
    
    /**
      * 批量插入配置属性项<br/>
      *<功能详细描述>
      * @param condition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void batchInsertConfigPropertyItem(List<ConfigPropertyItem> configPropertyItemList);
    
    /**
      * 删除ConfigPropertyItem对象
      * 1、auto generate
      * 2、根据入参条件进行删除
      * <功能详细描述>
      * @param condition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int deleteConfigPropertyItem(ConfigPropertyItem condition);
    
    /**
      * 查询ConfigPropertyItem实体
      * auto generate
      * <功能详细描述>
      * @param condition
      * @return [参数说明]
      * 
      * @return ConfigPropertyItem [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public ConfigPropertyItem findConfigPropertyItem(
            ConfigPropertyItem condition);
    
    /**
      * 根据条件查询ConfigPropertyItem列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return List<ConfigPropertyItem> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<ConfigPropertyItem> queryConfigPropertyItemList(
            Map<String, Object> params);
    
    /**
      * 根据指定查询条件以及排序列查询ConfigPropertyItem列表
      * auto generate
      * <功能详细描述>
      * @param params
      * @param orderList
      * @return [参数说明]
      * 
      * @return List<ConfigPropertyItem> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //auto generate
    public List<ConfigPropertyItem> queryConfigPropertyItemList(
            Map<String, Object> params, List<Order> orderList);
    
    /**
      * 更新ConfigPropertyItem实体，
      * auto generate
      * 1、传入ConfigPropertyItem中主键不能为空
      * <功能详细描述>
      * @param updateConfigPropertyItemRowMap
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int updateConfigPropertyItem(Map<String, Object> updateRowMap);
}
