/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-8-2
 * <修改描述:>
 */
package com.tx.component.configuration.model;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class ConfigPropertyItem implements ConfigProperty, Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = 887600460642190895L;
    
    /** 数据库存储唯一键 */
    private String id;
    
    /** 数据库存储唯一键 */
    private String parentId;
    
    /** 配置属性key */
    private String code;
    
    /** 配置属性名 */
    private String name;
    
    /** 配置属性的值 */
    private String value;
    
    /** 配置属性描述 */
    private String remark;
    
    /** 配置属性值验证合法表达式 */
    private String validateExpression;
    
    /** 是否可编辑 */
    private boolean modifyAble;
    
    /** 是否叶节点 */
    private boolean leaf;
    
    /** 创建时间 */
    private Date createDate;
    
    /** 最后更新时间 */
    private Date lastUpdateDate;
    
    /** 所属模块 */
    private String module;
    
    /** 版本 */
    private String version;
    
    /** 子配置项 */
    private List<ConfigProperty> childs;
    
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
     * @return 返回 parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param 对parentId进行赋值
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
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
     * @return 返回 code
     */
    public String getCode() {
        return code;
    }
    
    /**
     * @param 对code进行赋值
     */
    public void setCode(String code) {
        this.code = code;
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
     * @return 返回 remark
     */
    public String getRemark() {
        return remark;
    }
    
    /**
     * @param 对remark进行赋值
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
    
    /**
     * @return 返回 childs
     */
    public List<ConfigProperty> getChilds() {
        return childs;
    }
    
    /**
     * @param 对childs进行赋值
     */
    public void setChilds(List<ConfigProperty> childs) {
        this.childs = childs;
    }
    
    /**
     * @return 返回 leaf
     */
    public boolean isLeaf() {
        return leaf;
    }
    
    /**
     * @param 对leaf进行赋值
     */
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
    
    /**
     * @param 对modifyAble进行赋值
     */
    public void setModifyAble(boolean modifyAble) {
        this.modifyAble = modifyAble;
    }
    
    /**
     * @return 返回 modifyAble
     */
    public boolean isModifyAble() {
        return modifyAble;
    }

    /**
     * @return 返回 module
     */
    public String getModule() {
        return module;
    }

    /**
     * @param 对module进行赋值
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * @return 返回 version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param 对version进行赋值
     */
    public void setVersion(String version) {
        this.version = version;
    }
}
