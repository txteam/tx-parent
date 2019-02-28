/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年2月12日
 * <修改描述:>
 */
package com.tx.component.config.dao;

import java.util.List;
import java.util.Map;

import com.tx.component.config.model.ConfigProperty;

/**
 * 配置属性项持久层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年2月12日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ConfigPropertyDao {
    
    /**
     * 保存配置属性<br/>
     * <功能详细描述>
     * @param configProperty [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void save(ConfigProperty configProperty);
    
    /**
     * 批量保存配置属性<br/>
     * <功能详细描述>
     * @param configProperties [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void batchSave(List<ConfigProperty> configProperties);
    
    /**
     * 根据配置属性值查询配置属性<br/>
     * <功能详细描述>
     * @param configProperty
     * @return [参数说明]
     * 
     * @return ConfigProperty [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public ConfigProperty find(ConfigProperty configProperty);
    
    /**
     * 查询配置属性列表<br/>
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return List<ConfigProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> query(Map<String, Object> params);
    
    /**
     * 统计配置属性项数量<br/>
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
     * 删除配置属性<br/>
     * <功能详细描述>
     * @param configProperty
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int delete(ConfigProperty configProperty);
}
