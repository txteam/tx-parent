/*
  * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-8-2
 * <修改描述:>
 */
package com.tx.component.configuration.persister;

import java.util.List;
import java.util.Map;

import com.tx.component.configuration.model.ConfigProperty;

/**
 * 配置属性持久接口<br/>
 * <功能详细描述>
 * 
 * @author  wanxin
 * @version  [版本号, 2013-8-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ConfigPropertyPersister {
    
    /**
     * 根据数据所属模块判断从何获取数据,自动匹配<br/>
     * <功能详细描述>
     * @param module
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean supportsModule(String module);
    
    /**
     * 根据关键字code获取配置属性实例<br/>
     * <功能详细描述>
     * @param module
     * @param code
     * @return [参数说明]
     * 
     * @return ConfigProperty [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public ConfigProperty findByCode(String module, String code);
    
    /**
     * 查询配置属性<br/>
     * <功能详细描述>
     * @param module
     * @param params
     * @return [参数说明]
     * 
     * @return List<ConfigProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> queryList(String module,
            Map<String, Object> params);
    
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
    List<ConfigProperty> queryChildrenByParentId(String module, String parentId,
            Map<String, Object> params);
    
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
    List<ConfigProperty> queryDescendantsByParentId(String module,
            String parentId, Map<String, Object> params);
    
    /**
     * 更新配置项<br/>
     * <功能详细描述>
     * @param module
     * @param code
     * @param value
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean patch(String module, String code, String value);
}
