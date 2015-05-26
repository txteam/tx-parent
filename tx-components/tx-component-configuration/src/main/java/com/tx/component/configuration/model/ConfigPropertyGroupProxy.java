/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-2-17
 * <修改描述:>
 */
package com.tx.component.configuration.model;

import java.util.ArrayList;
import java.util.List;

import com.tx.component.configuration.config.ConfigGroupParse;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.ObjectUtils;

/**
 * 配置属性组实例代理<br/>
 * 
 * @author brady
 * @version [版本号, 2014-2-17]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ConfigPropertyGroupProxy implements ConfigPropertyGroup {
    
    /** 父级配置组解析器 */
    private ConfigGroupParse parentConfigGroupParse;
    
    /** 配置组解析实例 */
    private ConfigGroupParse configGroupParse;
    
    /** 子集配置属性列表 */
    private List<ConfigProperty> configPropertList = new ArrayList<ConfigProperty>();
    
    /** 子集配置属性组列表 */
    private List<ConfigPropertyGroup> configPropertyGroupList = new ArrayList<ConfigPropertyGroup>();
    
    /** 配置属性类型 */
    private ConfigPropertyTypeEnum configPropertyType;
    
    /** <默认构造函数> */
    public ConfigPropertyGroupProxy(ConfigPropertyTypeEnum configPropertyType,
            ConfigGroupParse parentConfigGroupParse,
            ConfigGroupParse configGroupParse) {
        super();
        //校验构建参数不能为空<br/>
        AssertUtils.notNull(configPropertyType,
                "build ConfigPropertyGroupProxy exception:this.configPropertyType is null.");
        AssertUtils.notNull(configGroupParse,
                "build ConfigPropertyProxy exception:this.configGroupParse is null.");
        this.parentConfigGroupParse = parentConfigGroupParse;
        this.configGroupParse = configGroupParse;
    }
    
    @Override
    public ConfigPropertyTypeEnum getConfigPropertyType() {
        return this.configPropertyType;
    }
    
    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String getName() {
        return this.configGroupParse.getName();
    }
    
    @Override
    public String getParentId() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String getParentName() {
        return this.parentConfigGroupParse == null ? ""
                : this.parentConfigGroupParse.getName();
    }
    
    @Override
    public List<ConfigPropertyGroup> getChilds() {
        return this.configPropertyGroupList;
    }
    
    @Override
    public boolean isViewAble() {
        return this.configGroupParse.isViewAble();
    }
    
    @Override
    public boolean isValid() {
        return this.configGroupParse.isValid();
    }
    
    @Override
    public List<ConfigProperty> getConfigs() {
        return this.configPropertList;
    }
    
    @Override
    public boolean equals(Object obj) {
        return ObjectUtils.equals(this, obj, "name");
    }
    
    @Override
    public int hashCode() {
        return ObjectUtils.generateHashCode(super.hashCode(), this, "name");
    }
}
