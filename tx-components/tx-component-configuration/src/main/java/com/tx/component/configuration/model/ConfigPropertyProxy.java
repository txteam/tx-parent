/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-2-10
 * <修改描述:>
 */
package com.tx.component.configuration.model;

import org.apache.commons.lang3.StringUtils;

import com.tx.component.configuration.config.ConfigGroupParse;
import com.tx.component.configuration.config.ConfigPropertyParse;
import com.tx.component.configuration.context.ConfigContext;
import com.tx.component.configuration.exceptions.UnModifyAbleException;
import com.tx.component.configuration.persister.ConfigPropertiesPersister;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 基础配置属性
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2014-2-10]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigPropertyProxy implements ConfigProperty {
    
    /** 是否为开发模式 */
    private ConfigContext configContext;
    
    /** 配置属性持久器 */
    protected ConfigPropertiesPersister configPropertiesPersister;
    
    /** 配置属性所属配置属性组 */
    protected ConfigGroupParse configGroupParse;
    
    /** 配置属性解析类 */
    protected ConfigPropertyParse configPropertyParse;
    
    /** 配置属性类型 */
    private ConfigPropertyTypeEnum configPropertyType;
    
    /** <默认构造函数> */
    public ConfigPropertyProxy(ConfigContext configContext,
            ConfigPropertiesPersister configPropertiesPersister,
            ConfigPropertyTypeEnum configPropertyType,
            ConfigGroupParse configGroupParse,
            ConfigPropertyParse configPropertyParse) {
        super();
        //校验构建参数不能为空<br/>
        AssertUtils.notNull(configPropertyType,
                "build ConfigPropertyProxy exception:this.configPropertyType is null.");
        AssertUtils.notNull(configContext,
                "build ConfigPropertyProxy exception:this.configContext is null.");
        AssertUtils.notNull(configPropertiesPersister,
                "build ConfigPropertyProxy exception:this.configPropertiesPersister is null.");
        AssertUtils.notNull(configPropertyParse,
                "build ConfigPropertyProxy exception:this.configPropertyParse is null.");
        
        this.configPropertyType = configPropertyType;
        this.configContext = configContext;
        this.configPropertiesPersister = configPropertiesPersister;
        this.configPropertyParse = configPropertyParse;
        
        this.configGroupParse = configGroupParse;
        
    }
    
    /**
     * @return
     */
    @Override
    public ConfigPropertyTypeEnum getConfigPropertyType() {
        return this.configPropertyType;
    }
    
    /**
     * 如果配置属性所属配置配置属性组有效为false,则属性为false
     * @return
     */
    @Override
    public boolean isValid() {
        //如果配置属性所属配置配置属性组有效为false,则属性为false
        if (configGroupParse != null && !configGroupParse.isValid()) {
            return false;
        }
        //如果在新的配置属性文件中不存在对应的配置属性，则显示该配置属性无效
        return true;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isModifyAble() {
        //如果配置属性无效，则也显示对应属性不可编辑
        if (!isValid()) {
            return false;
        }
        //如果配置属性组不可见，则显示该配置属性组不可编辑
        if (configGroupParse != null && !configGroupParse.isViewAble()) {
            return false;
        }
        //如果配置属性对应的配置属性持久器为不支持可编辑，则对应属性为不可编辑
        if (!configPropertiesPersister.isSupportModifyAble()) {
            return false;
        } else {
            return this.configPropertyParse.isEditAble();
        }
    }
    
    /**
     * @return
     */
    @Override
    public String getValidateExpression() {
        if (!isModifyAble()) {
            return "";
        }
        if (StringUtils.isBlank(this.configPropertyParse.getValidateExpression())) {
            return getName() + ":required";
        } else {
            String relValidateExpression = this.configPropertyParse.getValidateExpression();
            return relValidateExpression;
        }
    }
    
    /**
     * @return
     */
    @Override
    public boolean isViewAble() {
        //如果属性无效，则对应属性不可见
        if (!isValid()) {
            return false;
        }
        //如果所属组不可见，则属性不可见
        if (configGroupParse != null && !configGroupParse.isViewAble()) {
            return false;
        } else {
            //配置属性解析器
            if (configPropertyParse.isViewAble()) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    /**
     * @return
     */
    public String getConfigPropertyGroupName() {
        if (this.configGroupParse != null) {
            return this.configGroupParse.getName();
        } else {
            return "";
        }
    }
    
    /**
     * @return
     */
    @Override
    public String getName() {
        return this.configPropertyParse.getName();
    }
    
    /**
     * @return
     */
    @Override
    public String getKey() {
        return this.configPropertyParse.getKey();
    }
    
    /**
     * @return
     */
    @Override
    public String getDescription() {
        return this.configPropertyParse.getDescription();
    }
    
    /**
     * @param newValue
     */
    @Override
    public void update(String newValue) {
        if (!isModifyAble()) {
            throw new UnModifyAbleException("属性不可编辑.");
        }
        this.configPropertiesPersister.updateConfigProperty(getKey(), newValue);
    }
    
    /**
     * @return
     */
    @Override
    public String getValue() {
        if (this.configContext.isDevelop()) {
            return this.configPropertyParse.getValue();
        } else {
            String value = this.configPropertiesPersister.getConfigPropertyValueByKey(getKey());
            return value;
        }
    }
}
