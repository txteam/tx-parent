/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-20
 * <修改描述:>
 */
package com.tx.component.configuration.dao;

import java.util.List;
import java.util.Map;

import com.tx.component.configuration.model.ConfigPropertyItem;

/**
 * 配置属性持久层<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-12-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ConfigPropertyItemDao {
    
    /**
     * 插入配置属性项目<br/>
     * <功能详细描述>
     * @param configPropertyItem [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void insert(ConfigPropertyItem configPropertyItem);
    
    /**
     * 删除DataDict对象
     * 1、auto generate
     * 2、根据入参条件进行删除
     * <功能详细描述>
     * @param condition [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int delete(ConfigPropertyItem configPropertyItem);
    
    /**
     * 查询配置属性项<br/>
     * <功能详细描述>
     * @param condition
     * @return [参数说明]
     * 
     * @return ConfigPropertyItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public ConfigPropertyItem find(ConfigPropertyItem condition);
    
    /**
     * 根据系统id查询配置属性项列表 
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return List<ConfigPropertyItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigPropertyItem> queryList(Map<String, Object> params);
    
    /**
     * 根据条件查询DataDict列表总数
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
     * 更新配置属性项<br/>
     * <功能详细描述>
     * @param configPropertyItem [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int update(Map<String, Object> rowMap);
}
