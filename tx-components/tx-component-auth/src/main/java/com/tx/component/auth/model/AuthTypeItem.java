/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-4-1
 * <修改描述:>
 */
package com.tx.component.auth.model;

import java.io.Serializable;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;

/**
 * 权限项类型<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-4-1]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthTypeItem implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = 7942093110803351685L;
    
    /** 权限类型  */
    private String authType;
    
    /** 权限类型名  */
    private String name = "";
    
    /** 权限类型描述 */
    private String description = "";
    
    /** 是否可见 */
    private boolean isViewAble = true;
    
    /** 是否可在统一权限管理界面进行编辑  */
    private boolean isConfigAble = true;
    
    /** 权限项列表 */
    private List<AuthItem> authItemList;
    
    /**
     * 使AuthType构造函数为包内可见，使外部不能通过new去创建AuthTypeItem
     * <默认构造函数>
     */
    public AuthTypeItem(String authType, String name, String description,
            boolean isViewAble, boolean isConfigAble) {
        super();
        this.authType = authType;
        this.name = name;
        this.description = description;
        this.isViewAble = isViewAble;
        this.isConfigAble = isConfigAble;
    }
    
    /**
     * 使AuthType构造函数为包内可见，使外部不能通过new去创建AuthTypeItem
     * <默认构造函数>
     */
    public AuthTypeItem(String authType) {
        super();
        this.authType = authType;
    }
    
    /**
     * @return 返回 authType
     */
    public String getAuthType() {
        return authType;
    }
    
    /**
     * @param 对authType进行赋值
     */
    public void setAuthType(String authType) {
        this.authType = authType;
    }
    
    /**
     * @return 返回 isViewAble
     */
    public boolean isViewAble() {
        return isViewAble;
    }
    
    /**
     * @param 对isViewAble进行赋值
     */
    public void setViewAble(boolean isViewAble) {
        this.isViewAble = isViewAble;
    }
    
    /**
     * @return 返回 isConfigAble
     */
    public boolean isConfigAble() {
        return isConfigAble;
    }

    /**
     * @param 对isConfigAble进行赋值
     */
    public void setConfigAble(boolean isConfigAble) {
        this.isConfigAble = isConfigAble;
    }

    /**
     * @return 返回 authItemList
     */
    public List<AuthItem> getAuthItemList() {
        return authItemList;
    }
    
    /**
     * @param 对authItemList进行赋值
     */
    public void setAuthItemList(List<AuthItem> authItemList) {
        this.authItemList = authItemList;
    }
    
    /**
     * @return 返回 name
     */
    public String getName() {
        if (StringUtils.isEmpty(name)) {
            return authType;
        } else {
            return name;
        }
    }
    
    /**
     * @param 对name进行赋值
     */
    public void setName(String name) {
        this.name = name;
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
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof AuthTypeItem)) {
            return false;
        } else {
            AuthTypeItem other = (AuthTypeItem) obj;
            if (this.authType == null) {
                return this == other;
            } else {
                return this.authType.equals(other.getAuthType());
            }
        }
    }
    
    /**
     * @return
     */
    @Override
    public int hashCode() {
        if (this.authType == null) {
            return super.hashCode();
        }
        return this.authType.hashCode();
    }
}
