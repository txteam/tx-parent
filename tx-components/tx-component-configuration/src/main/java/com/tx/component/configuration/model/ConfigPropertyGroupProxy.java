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
import com.tx.core.util.ObjectUtils;

/**
 * 配置属性组实例代理<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2014-2-17]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
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
    
    /** <默认构造函数> */
    public ConfigPropertyGroupProxy(ConfigGroupParse parentConfigGroupParse,
            ConfigGroupParse configGroupParse) {
        super();
        this.parentConfigGroupParse = parentConfigGroupParse;
        this.configGroupParse = configGroupParse;
    }
    
    /**
     * @return
     */
    @Override
    public String getName() {
        return this.configGroupParse.getName();
    }
    
    /**
     * @return
     */
    @Override
    public String getParentName() {
        return this.parentConfigGroupParse == null ? "" : this.parentConfigGroupParse.getName();
    }
    
    /**
     * @return
     */
    @Override
    public List<ConfigPropertyGroup> getChilds() {
        return this.configPropertyGroupList;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isViewAble() {
        return this.configGroupParse.isViewAble();
    }
    
    /**
     * @return
     */
    @Override
    public boolean isValid() {
        return this.configGroupParse.isValid();
    }
    
    /**
     * @return
     */
    @Override
    public List<ConfigProperty> getConfigs() {
        return this.configPropertList;
    }
    
    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return ObjectUtils.equals(this, obj, "name");
    }
    
    /**
     * @return
     */
    @Override
    public int hashCode() {
        return ObjectUtils.generateHashCode(super.hashCode(), this, "name");
    }
}
