/*
x * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  2012-11-30
 * <修改描述:>
 */
package com.tx.component.auth.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.util.ObjectUtils;

import io.swagger.annotations.ApiModel;

/**
 * 权限项
 * 如果两个权限项的 id与authType相同，则被认为是同一个authitem
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-11-30]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
@Table(name = "sec_authref")
@ApiModel("权限项")
public class AuthItem implements Auth {
    
    /** 注释内容 */
    private static final long serialVersionUID = 2870014183031122725L;
    
    /** 权限项唯一键key : 约定权限项目分割符为"_" 如权限为"wd_" 不同的权限项,id也不能重复 */
    @Id
    private String id;
    
    /** 父级权限id */
    private String parentId;
    
    /** 权限类型 后续根据需要可以扩展相应的权限大类   比如: 产品权限\流程环节权限\通过多个纬度的权限交叉可以达到多纬度的授权体系*/
    private String authTypeId;
    
    /** 引用id */
    private String refId;
    
    /** 引用类型 */
    private String refType;
    
    /** 权限项名 */
    private String name;
    
    /** 权限项目描述 */
    private String remark;
    
    /** 是否可配置给 */
    private boolean configAble = true;
    
    /** 属性值 */
    private String attributes;
    
    /** 子权限列表 */
    @OneToMany(fetch = FetchType.LAZY)
    private List<Auth> children = new ArrayList<Auth>();
    
    /** <默认构造函数> */
    public AuthItem() {
        super();
    }
    
    /** <默认构造函数> */
    public AuthItem(String id, String authTypeId) {
        super();
        this.id = id;
        this.authTypeId = authTypeId;
    }
    
    /** <默认构造函数> */
    public AuthItem(String id, String authTypeId, String refType,
            String refId) {
        super();
        this.id = id;
        this.authTypeId = authTypeId;
        this.refType = refType;
        this.refId = refId;
    }
    
    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * @param parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
    /**
     * @param name
     */
    
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @param 对remark进行赋值
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    /**
     * @param authType
     */
    public void setAuthTypeId(String authTypeId) {
        this.authTypeId = authTypeId;
    }
    
    /**
     * @param 对refId进行赋值
     */
    public void setRefId(String refId) {
        this.refId = refId;
    }
    
    /**
     * @param 对refType进行赋值
     */
    public void setRefType(String refType) {
        this.refType = refType;
    }
    
    /**
     * @return
     */
    @Override
    public String getId() {
        return id;
    }
    
    /**
     * @return
     */
    @Override
    public String getAuthTypeId() {
        return authTypeId;
    }
    
    /**
     * @return
     */
    @Override
    public String getParentId() {
        return parentId;
    }
    
    /**
     * @return
     */
    @Override
    public String getName() {
        if (StringUtils.isEmpty(this.name)) {
            return this.id;
        }
        return this.name;
    }
    
    /**
     * @return 返回 remark
     */
    @Override
    public String getRemark() {
        return remark;
    }
    
    /**
     * @return 返回 configAble
     */
    @Override
    public boolean isConfigAble() {
        return configAble;
    }
    
    /**
     * @return 返回 refId
     */
    @Override
    public String getRefId() {
        return refId;
    }
    
    /**
     * @return 返回 refType
     */
    @Override
    public String getRefType() {
        return refType;
    }
    
    /**
     * @return 返回 attributes
     */
    @Override
    public String getAttributes() {
        return attributes;
    }
    
    /**
     * @param 对attributes进行赋值
     */
    @Override
    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }
    
    /**
     * @return
     */
    @Override
    public List<Auth> getChildren() {
        return children;
    }
    
    /**
     * @param childs
     */
    @Override
    public void setChildren(List<Auth> children) {
        this.children = children;
    }
    
    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return ObjectUtils.equals(this, obj, "id", "authType", "module");
    }
    
    /**
     * @return
     */
    @Override
    public int hashCode() {
        int resHashCode = ObjectUtils.generateHashCode(super.hashCode(),
                this,
                "id",
                "authType",
                "module");
        return resHashCode;
    }
}
