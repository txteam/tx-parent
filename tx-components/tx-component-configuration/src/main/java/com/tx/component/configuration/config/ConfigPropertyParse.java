/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-20
 * <修改描述:>
 */
package com.tx.component.configuration.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 属性配置<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-12-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@XStreamAlias("config")
public class ConfigPropertyParse {
    
    /** 状态:true可见 false不可见 */
    @XStreamAsAttribute
    private Boolean viewAble = true;
    
    /** 是否支持动态配置 */
    @XStreamAsAttribute
    private Boolean editAble = false;
    
    /** 配置资源名 */
    @XStreamAsAttribute
    private String name;
    
    /** 关键字 */
    @XStreamAsAttribute
    private String key;
    
    /** 配置资源描述信息 */
    private String description;
    
    /** 
     * 实际值，如果在开发模式，则以该值为标准
     * 在开发模式中，value值优先加载该值
     */
    private String value;
    
    /** 属性值表达式 */
    private String validateExpression = "";
    
    /**
     * @return 返回 name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param 对name进行赋值
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return 返回 key
     */
    public String getKey() {
        return key;
    }
    
    /**
     * @param 对key进行赋值
     */
    public void setKey(String key) {
        this.key = key;
    }
    
    /**
     * @return 返回 description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * @param 对description进行赋值
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * @return 返回 value
     */
    public String getValue() {
        return value;
    }
    
    /**
     * @param 对value进行赋值
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    /**
     * @return 返回 validateExpression
     */
    public String getValidateExpression() {
        return validateExpression;
    }
    
    /**
     * @param 对validateExpression进行赋值
     */
    public void setValidateExpression(String validateExpression) {
        this.validateExpression = validateExpression;
    }

    /**
     * @return 返回 editAble
     */
    public boolean isEditAble() {
        return editAble == null ? false : editAble;
    }

    /**
     * @param 对editAble进行赋值
     */
    public void setEditAble(boolean editAble) {
        this.editAble = editAble;
    }
    
    /**
     * @return 返回 viewAble
     */
    public boolean isViewAble() {
        return viewAble == null ? true : viewAble;
    }
    
    /**
     * @param 对viewAble进行赋值
     */
    public void setViewAble(boolean viewAble) {
        this.viewAble = viewAble;
    }
}
