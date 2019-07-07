/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.role.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;

/**
 * 角色引用项<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
@Table(name = "sec_roleref")
@ApiModel("角色引用")
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
    //effective是有效的，起作用的意思，用于事物或人，一般是呈现出正确的结果或是解决了问题。
    //efficient是有效率的，高效能的；既可指人也可指事物，强调不浪费时间、金钱、能源等的。
    private Date effectiveDate;
    
    /** 系统自动判定的无效时间:系统在查询具体是否存在引用过程中将根据该时间动态计算 */
    private Date expiryDate;
    
    /** 权限授予人*/
    private String createOperatorId;
    
    /** 权限引用项的创建(授予)时间 */
    private Date createDate;
    
    /** 最后更新人 */
    private String lastUpdateOperatorId;
    
    /** 最后更新时间 */
    private Date lastUpdateDate;
    
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
    
    /**
     * @return 返回 lastUpdateOperatorId
     */
    public String getLastUpdateOperatorId() {
        return lastUpdateOperatorId;
    }
    
    /**
     * @param 对lastUpdateOperatorId进行赋值
     */
    public void setLastUpdateOperatorId(String lastUpdateOperatorId) {
        this.lastUpdateOperatorId = lastUpdateOperatorId;
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
