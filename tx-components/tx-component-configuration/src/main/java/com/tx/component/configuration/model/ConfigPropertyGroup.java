/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-2-10
 * <修改描述:>
 */
package com.tx.component.configuration.model;

import java.util.List;

/**
 * 配置属性分组
 * 
 * @author brady
 * @version [版本号, 2014-2-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ConfigPropertyGroup {
    
    /**
     * 获取配置属性类型
     * 
     * @return ConfigPropertyTypeEnum 配置属性类型
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public ConfigPropertyTypeEnum getConfigPropertyType();
    
    /**
     * 获取配置属性组id
     * 
     * @return String 配置属性组id
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getId();
    
    /**
     * 获取配置属性分组名
     * 
     * @return String 配置属性分组名
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getName();
    
    /**
     * 获取配置属性分组父级id
     * 
     * @return String 配置属性分组父级id
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getParentId();
    
    /**
     * 获取配置属性分组父级名称
     * 
     * @return String 配置属性分组父级名称
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getParentName();
    
    /**
     * 获取下级配置属性分组集合
     * 
     * @return List<ConfigPropertyGroupItem> 下级配置属性分组集合
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigPropertyGroup> getChilds();
    
    /**
     * 获取配置属性组是否可见
     * 
     * @return boolean 配置属性组是否可见
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isViewAble();
    
    /**
     * 获取配置属性组是否有效
     * 
     * @return boolean 配置属性组是否有效
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isValid();
    
    /**
     * 获取该配置属性组下属的配置属性
     * 
     * @return List<ConfigProperty> 配置属性组下属的配置属性
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> getConfigs();
}
