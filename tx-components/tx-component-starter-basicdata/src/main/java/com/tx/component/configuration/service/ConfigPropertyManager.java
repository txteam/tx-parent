/*
  * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-8-2
 * <修改描述:>
 */
package com.tx.component.configuration.service;

import java.util.List;

import org.springframework.core.Ordered;

import com.tx.component.configuration.model.ConfigProperty;
import com.tx.core.querier.model.Querier;

/**
 * 配置属性持久接口<br/>
 * <功能详细描述>
 * 
 * @author  wanxin
 * @version  [版本号, 2013-8-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ConfigPropertyManager extends Ordered{
    
    /**
     * 是否匹配当前配置属性业务层<br/>
     * <功能详细描述>
     * @param module
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean supports(String module);
    
    /**
     * 根据关键字code获取配置属性实例<br/>
     * <功能详细描述>
     * @param code
     * @return [参数说明]
     * 
     * @return ConfigProperty [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public ConfigProperty findByCode(String code);
    
    /**
     * 查询配置属性<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return List<ConfigProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> queryList(Querier querier);
    
    /**
     * 根父节点查询子节点<br/>
     * <功能详细描述>
     * @param parentId
     * @param querier
     * @return [参数说明]
     * 
     * @return List<ConfigPropertyItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    List<ConfigProperty> queryChildrenByParentId(String parentId,
            Querier querier);
    
    /**
     * 嵌套查询子级配置项<br/>
     * <功能详细描述>
     * @param parentId
     * @param querier
     * @return [参数说明]
     * 
     * @return List<ConfigPropertyItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    List<ConfigProperty> queryDescendantsByParentId(String parentId,
            Querier querier);
    
    /**
     * 更新配置项<br/>
     * <功能详细描述>
     * @param code
     * @param value
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean patch(String code, String value);
}
