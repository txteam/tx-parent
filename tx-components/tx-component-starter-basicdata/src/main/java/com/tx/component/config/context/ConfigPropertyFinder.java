/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年2月3日
 * <修改描述:>
 */
package com.tx.component.config.context;

import java.util.List;

import com.tx.component.config.model.ConfigProperty;

/**
 * 根据配置项code查询配置项<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年2月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ConfigPropertyFinder {
    
    /**
     * 根据配置项编码查询配置属性<br/>
     * <功能详细描述>
     * @param code
     * @return [参数说明]
     * 
     * @return ConfigProperty [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public ConfigProperty find(String code);
    
    /**
     * 遍历所有的配置属性<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<ConfigProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> list();
}
