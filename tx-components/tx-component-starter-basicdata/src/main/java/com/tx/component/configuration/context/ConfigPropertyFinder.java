/*
  * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-8-2
 * <修改描述:>
 */
package com.tx.component.configuration.context;

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
public interface ConfigPropertyFinder {
    
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
    public List<ConfigProperty> queryList(Map<String, Object> params);
    
    /**
     * 根据关键字key获取配置属性实例<br/>
     * <功能详细描述>
     * @param key
     * @return [参数说明]
     * 
     * @return ConfigProperty [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public ConfigProperty findByCode(String key);
    
    /**
     * 更新配置容器中配置属性值<br/> 
     *<功能详细描述>
     * @param key
     * @param value [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void update(ConfigProperty configProperty);
}
