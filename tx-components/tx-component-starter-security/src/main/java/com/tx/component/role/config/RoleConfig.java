/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年8月6日
 * <修改描述:>
 */
package com.tx.component.role.config;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 内容信息存放文件<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年8月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@XStreamAlias("role")
public class RoleConfig {
    
    /** 分类编码 */
    @XStreamOmitField
    private RoleTypeConfig type;
    
    /** 分类编码 */
    @XStreamOmitField
    private RoleConfig parent;
    
    /** 对应枚举关键字：该字段可以为空 */
    @XStreamAsAttribute
    private String id;
    
    /** 内容信息类型名 */
    @XStreamAsAttribute
    private String name;
    
    /** 内容信息类型备注 */
    @XStreamAsAttribute
    private String remark;
    
    /** 内容分类配置 */
    @XStreamImplicit(itemFieldName = "role")
    private List<RoleConfig> children;
    
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
     * @return 返回 children
     */
    public List<RoleConfig> getChildren() {
        return children;
    }
    
    /**
     * @param 对children进行赋值
     */
    public void setChildren(List<RoleConfig> children) {
        this.children = children;
    }

    /**
     * @return 返回 type
     */
    public RoleTypeConfig getType() {
        return type;
    }

    /**
     * @param 对type进行赋值
     */
    public void setType(RoleTypeConfig type) {
        this.type = type;
    }

    /**
     * @return 返回 parent
     */
    public RoleConfig getParent() {
        return parent;
    }

    /**
     * @param 对parent进行赋值
     */
    public void setParent(RoleConfig parent) {
        this.parent = parent;
    }
}
