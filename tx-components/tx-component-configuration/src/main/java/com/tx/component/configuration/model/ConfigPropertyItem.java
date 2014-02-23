/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-8-2
 * <修改描述:>
 */
package com.tx.component.configuration.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

/**
 * 配置属性项<br/>
 * 对应配置数据数据库统表存储的其中一项<br/>
 * <功能详细描述>
 * 
 * @author  wanxin
 * @version  [版本号, 2013-8-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigPropertyItem implements Serializable{
    
    /** 注释内容 */
    private static final long serialVersionUID = 887600460642190895L;

    /** 数据库存储唯一键 */
    @Id
    private String id;
    
    /** 系统资源id: 唯一系统具有唯一的systemSourceId用于区分配置中不同系统的参数 */
    private String systemId;
    
    /** 配置属性名 */
    private String name;
    
    /** 配置属性key */
    private String key;
    
    /** 配置属性的值 */
    private String value;
    
    /** 配置属性描述 */
    private String description;
    
    /** 配置属性是否有效 */
    private boolean valid = true;
    
    /** 配置属性值验证合法表达式 */
    private String validateExpression;
    
    /** 创建时间 */
    private Date createDate;
    
    /** 最后更新时间 */
    private Date lastUpdateDate;

    /**
     * @return 返回 id
     */
    public String getId() {
        return id;
    }

    /**
     * @param 对id进行赋值
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 返回 systemId
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * @param 对systemId进行赋值
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

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
     * @return 返回 valid
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * @param 对valid进行赋值
     */
    public void setValid(boolean valid) {
        this.valid = valid;
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
     * @return 返回 createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param 对createDate进行赋值
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return 返回 lastUpdateDate
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * @param 对lastUpdateDate进行赋值
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
