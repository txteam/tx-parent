package com.tx.component.configuration.service;

import java.util.List;

import com.tx.component.configuration.model.ConfigPropertyItem;
import com.tx.core.querier.model.Querier;

/**
 * 配置项业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
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
     * 根据id删除配置属性项<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    boolean deleteById(String id);
    
    /**
     * 根据code删除配置属性项<br/>
     * <功能详细描述>
     * @param code
     * @param module
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    boolean deleteByCode(String module, String code);
    
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
    boolean updateById(ConfigPropertyItem configPropertyItem);
    
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
    ConfigPropertyItem findById(String id);
    
    /**
     * 根据编码查询配置项目<br/>
     * <功能详细描述>
     * @param code
     * @param module
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
    List<ConfigPropertyItem> queryList(String module, Querier querier);
    
    /**
     * 根父节点查询子节点<br/>
     * <功能详细描述>
     * @param module
     * @param parentId
     * @param params
     * @return [参数说明]
     * 
     * @return List<ConfigPropertyItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    List<ConfigPropertyItem> queryChildrenByParentId(String module,
            String parentId, Querier querier);
    
    /**
     * 嵌套查询子级配置项<br/>
     * <功能详细描述>
     * @param module
     * @param parentId
     * @param params
     * @return [参数说明]
     * 
     * @return List<ConfigPropertyItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    List<ConfigPropertyItem> queryDescendantsByParentId(String module,
            String parentId, Querier querier);
    
}