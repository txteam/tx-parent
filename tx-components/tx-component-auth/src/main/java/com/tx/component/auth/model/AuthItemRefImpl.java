/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-1
 * <修改描述:>
 */
package com.tx.component.auth.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.slf4j.helpers.MessageFormatter;

import com.tx.component.auth.AuthConstant;
import com.tx.core.util.ObjectUtils;

/**
 * 权限引用项
 *     可以是： 角色权限
 *             职位权限
 *             ...可以赋予权限的主体
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-1]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
@Table(name = "auth_authref")
public class AuthItemRefImpl implements AuthItemRef {
    
    /** 注释内容 */
    private static final long serialVersionUID = -7928952142014599323L;
    
    /**
     * <默认构造函数>
     */
    public AuthItemRefImpl() {
        super();
    }
    
    /**
     * <默认构造函数>
     */
    public AuthItemRefImpl(AuthItemImpl authItem) {
        super();
        this.authItemImpl = authItem;
        this.authRefType = AuthConstant.AUTHREFTYPE_OPERATOR;
    }
    
    /** 权限引用唯一键盘，全局唯一 */
    private String id;
    
    /** 权限引用类型 */
    private String authRefType;
    
    /** 权限引用唯一键 */
    @Id
    private String refId;
    
    /** 
     * 权限引用对应的权限id
     */
    @ManyToOne
    @Column(name = "authId")
    private AuthItemImpl authItemImpl;
    
    /**
     * 权限授予人
     */
    private String createOperId;
    
    /** 权限引用项的创建(授予)时间 */
    private Date createDate;
    
    /** 权限引用项结束时间  */
    private Date endDate;
    
    /** 生效时间 */
    private Date effectiveDate;
    
    /** 系统自动判定的无效时间:系统在查询具体是否存在引用过程中将根据该时间动态计算 */
    private Date invalidDate;
    
    /** 是否是临时权限 */
    private Boolean temp;
    
    /**
     * @return
     */
    @Override
    public String getAuthRefType() {
        return this.authRefType;
    }
    
    /**
     * @return
     */
    @Override
    public String getRefId() {
        return this.refId;
    }
    
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
     * @return 返回 authItemImpl
     */
    public AuthItemImpl getAuthItemImpl() {
        return authItemImpl;
    }
    
    /**
     * @param 对authItemImpl进行赋值
     */
    public void setAuthItemImpl(AuthItemImpl authItemImpl) {
        this.authItemImpl = authItemImpl;
    }
    
    /**
     * @return
     */
    @Override
    public AuthItem getAuthItem() {
        return this.authItemImpl;
    }
    
    /**
      * 设置权限项
      *<功能详细描述>
      * @param authItem [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setAuthItem(AuthItem authItem) {
        if (authItem instanceof AuthItemImpl) {
            this.authItemImpl = (AuthItemImpl) authItem;
        } else {
            this.authItemImpl = new AuthItemImpl(authItem);
        }
    }
    
    /**
     * @return
     */
    @Override
    public String getCreateOperId() {
        return createOperId;
    }
    
    /**
     * @param 对createOperId进行赋值
     */
    public void setCreateOperId(String createOperId) {
        this.createOperId = createOperId;
    }
    
    /**
     * @return
     */
    @Override
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
     * @return
     */
    @Override
    public Date getEndDate() {
        return endDate;
    }
    
    /**
     * @param 对endDate进行赋值
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    /**
     * @param 对authRefType进行赋值
     */
    public void setAuthRefType(String authRefType) {
        this.authRefType = authRefType;
    }
    
    /**
     * @param 对refId进行赋值
     */
    public void setRefId(String refId) {
        this.refId = refId;
    }
    
    /**
     * @return 返回 effectiveDate
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }
    
    /**
     * @param 对effectiveDate进行赋值
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    
    /**
     * @return 返回 invalidDate
     */
    public Date getInvalidDate() {
        return invalidDate;
    }
    
    /**
     * @param 对invalidDate进行赋值
     */
    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }
    
    /**
     * @return 返回 temp
     */
    public Boolean isTemp() {
        return temp;
    }

    public Boolean getTemp() {
        return temp;
    }

    /**
     * @param 对temp进行赋值
     */
    public void setTemp(Boolean temp) {
        this.temp = temp;
    }
    
    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        boolean flag = ObjectUtils.equals(this,
                obj,
                "id",
                "authItem",
                "authRefType",
                "refId",
                "temp");
        return flag;
    }
    
    /**
     * @return
     */
    @Override
    public int hashCode() {
        int hashCode = ObjectUtils.generateHashCode(super.hashCode(),
                this,
                "id",
                "authItem",
                "authRefType",
                "refId",
                "temp");
        return hashCode;
    }
    
    /**
     * @return
     */
    @Override
    public String toString() {
        return MessageFormatter.arrayFormat("authItemRef: {refId{},authRefType:{},authItem:{},,}",
                new Object[] { this.refId, this.authRefType, this.authItemImpl })
                .getMessage();
    }
}
