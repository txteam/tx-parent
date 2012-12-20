/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  2012-11-30
 * <修改描述:>
 */
package com.tx.component.auth.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class DefaultAuthItem implements Serializable, AuthItem {
    
    /** 注释内容 */
    private static final long serialVersionUID = -5205952448154970380L;
    
    /** 
     * 权限项唯一键key 
     * 约定权限项目分割符为"_"
     * 如权限为"wd_"
     * 不同的权限项,id也不能重复
     */
    private String id;
    
    /** 父级权限id */
    private String parentId;
    
    /** 权限项名 */
    private String name;
    
    /** 权限项目描述 */
    private String description;
    
    /** 
     * 权限类型
     * 后续根据需要可以扩展相应的权限大类
     * 比如 
     *      产品权限
     *      流程环节权限
     *      通过多个纬度的权限交叉可以达到多纬度的授权体系
     */
    private String authType = "";
    
    /** 子权限列表 */
    private List<AuthItem> childs = new ArrayList<AuthItem>();
    
    /** 是否为抽象权限：默认为非抽象权限，抽象权限不建立权限列表映射 */
    private boolean isAbstract = false;
    
    /** 是否有效，默认为true,权限可停用 */
    private boolean isValid = true;
    
    /**
     * @return
     */
    @Override
    public String getId() {
        return id;
    }
    
    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
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
        return name;
    }
    
    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return
     */
    @Override
    public String getDescription() {
        return description;
    }
    
    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * @return
     */
    @Override
    public List<AuthItem> getChilds() {
        return childs;
    }
    
    /**
     * @param childs
     */
    @Override
    public void setChilds(List<AuthItem> childs) {
        this.childs = childs;
    }
    
    /**
     * @return
     */
    @Override
    public String getAuthType() {
        return authType;
    }
    
    /**
     * @param authType
     */
    public void setAuthType(String authType) {
        this.authType = authType;
    }
    
    /**
     * @param parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isAbstract() {
        return isAbstract;
    }
    
    /**
     * @param isAbstract
     */
    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isValid() {
        return isValid;
    }
    
    /**
     * @param isValid
     */
    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }
    
    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof AuthItem)) {
            return false;
        }
        else {
            AuthItem other = (AuthItem) obj;
            if (this.id == null) {
                //这里认为authType或id为空时，不能按照authItem的设置判断相等，仅仅能根据两者是否是同一个引用判断相等
                //仅以两者是否是同一个对象的链接进行判断
                return this == other;
            }
            else {
                return this.getId().equals(other.getId());
            }
        }
    }
    
    /**
     * @return
     */
    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return super.hashCode();
        }
        return this.getId().hashCode();
    }
    
}
