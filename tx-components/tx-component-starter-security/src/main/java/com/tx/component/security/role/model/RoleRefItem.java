/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.security.role.model;

import java.util.Date;

import javax.persistence.Id;

/**
 * 角色引用项<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RoleRefItem implements RoleRef {
    
    /** 注释内容 */
    private static final long serialVersionUID = 1988043649630154355L;
    
    /** 权限引用唯一键盘，全局唯一 */
    @Id
    private String id;
    
    /** 权限引用类型 */
    private String refType;
    
    /** 权限引用唯一键 */
    private String refId;
    
    /** 权限引用对应的权限id */
    private String roleId;
    
    /** 生效时间 */
    private Date effectiveDate;
    
    /** 系统自动判定的无效时间:系统在查询具体是否存在引用过程中将根据该时间动态计算 */
    private Date expiryDate;
    
    /** 权限授予人*/
    private String createOperatorId;
    
    /** 权限引用项的创建(授予)时间 */
    private Date createDate;
    
    /**
     * @param 对id进行赋值
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * @param 对refType进行赋值
     */
    public void setRefType(String refType) {
        this.refType = refType;
    }
    
    /**
     * @param 对refId进行赋值
     */
    public void setRefId(String refId) {
        this.refId = refId;
    }
    
    /**
     * @param 对roleId进行赋值
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    
    /**
     * @param 对effectiveDate进行赋值
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    
    /**
     * @param 对expiryDate进行赋值
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    /**
     * @param 对createOperatorId进行赋值
     */
    public void setCreateOperatorId(String createOperatorId) {
        this.createOperatorId = createOperatorId;
    }
    
    /**
     * @param 对createDate进行赋值
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    /**
     * @return
     */
    @Override
    public String getId() {
        return this.id;
    }
    
    /**
     * @return
     */
    @Override
    public String getRefType() {
        return this.refType;
    }
    
    /**
     * @return
     */
    @Override
    public String getRefId() {
        return this.refId;
    }
    
    /**
     * @return
     */
    @Override
    public String getCreateOperatorId() {
        return this.createOperatorId;
    }
    
    /**
     * @return
     */
    @Override
    public Date getCreateDate() {
        return this.createDate;
    }
    
    /**
     * @return
     */
    @Override
    public Date getEffectiveDate() {
        return this.effectiveDate;
    }
    
    /**
     * @return
     */
    @Override
    public Date getExpiryDate() {
        return this.expiryDate;
    }
    
    /**
     * @return
     */
    @Override
    public String getRoleId() {
        return this.roleId;
    }
    
}
