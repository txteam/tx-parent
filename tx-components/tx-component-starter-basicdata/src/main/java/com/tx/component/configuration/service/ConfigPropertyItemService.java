package com.tx.component.configuration.service;

import java.util.List;
import java.util.Map;

import com.tx.component.configuration.model.ConfigPropertyItem;

public interface ConfigPropertyItemService {
    
    /**
     * 插入配置属性项目<br/>
     * <功能详细描述>
     * @param configPropertyItem [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    void insert(ConfigPropertyItem configPropertyItem);
    
    /**
     * 插入配置属性项目<br/>
     * <功能详细描述>
     * @param configPropertyItem [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    boolean delete(ConfigPropertyItem configPropertyItem);
    
    /**
     * 更新配置属性项<br/>
     * <功能详细描述>
     * @param configPropertyItem [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    boolean patch(String module, String code, String value);
    
    /**
     * 更新配置属性项<br/>
     * <功能详细描述>
     * @param configPropertyItem [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    boolean update(ConfigPropertyItem configPropertyItem);
    
    /**
     * 根据编码查询配置项目<br/>
     * <功能详细描述>
     * @param code
     * @return [参数说明]
     * 
     * @return ConfigPropertyItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    ConfigPropertyItem findByCode(String module, String code);
    
    /**
     * 根据系统id查询配置属性项列表 
     *    配置属性项列表功能不考虑缓存<br/>
     * <功能详细描述>
     * @param systemId
     * @return [参数说明]
     * 
     * @return List<ConfigPropertyItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    List<ConfigPropertyItem> queryList(String module,
            Map<String, Object> params);
    
    /**
     * 根据系统id查询配置属性项列表 
     *    配置属性项列表功能不考虑缓存<br/>
     * <功能详细描述>
     * @param systemId
     * @return [参数说明]
     * 
     * @return List<ConfigPropertyItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    List<ConfigPropertyItem> queryNestedListByParentId(String module,
            String parentId, Map<String, Object> params);
    
}