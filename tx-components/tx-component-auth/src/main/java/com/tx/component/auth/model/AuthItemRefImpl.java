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

import org.apache.cxf.common.util.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import com.tx.component.auth.AuthConstant;

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
    
    /** 
     * 所属系统id:不能为空 
     * 具体的一个权限的引用必然属于一个系统<br/> 
     */
    private String systemId;
    
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
    
    /** 权限引用项的失效时间 */
    private Date endDate;
    
    /**
     * 是否支持根据权限引用的引用的结束时间<br/>
     * 判断权限是否需要根据结束时间验证其有效性
     */
    private boolean isValidDependEndDate = false;

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
    
    public void setAuthItem(AuthItem authItem){
        if(authItem instanceof AuthItemImpl){
            this.authItemImpl = (AuthItemImpl)authItem;
        }else{
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
     * @return 返回 isValidDependEndDate
     */
    public boolean isValidDependEndDate() {
        return isValidDependEndDate;
    }
    
    /**
     * @param 对isValidDependEndDate进行赋值
     */
    public void setValidDependEndDate(boolean isValidDependEndDate) {
        this.isValidDependEndDate = isValidDependEndDate;
    }
    
    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj instanceof AuthItemRef) {
            return false;
        } else {
            AuthItemRef otherAuthItemRef = (AuthItemRef) obj;
            if (this.authItemImpl == null || StringUtils.isEmpty(this.authRefType)
                    || StringUtils.isEmpty(this.refId)
                    || StringUtils.isEmpty(this.authItemImpl.getAuthType())
                    || StringUtils.isEmpty(this.authItemImpl.getId())) {
                return this == otherAuthItemRef;
            } else {
                return this.authItemImpl.equals(otherAuthItemRef.getAuthItem())
                        && this.authRefType.equals(otherAuthItemRef.getAuthRefType())
                        && this.refId.equals(otherAuthItemRef.getRefId());
            }
        }
    }
    
    /**
     * @return
     */
    @Override
    public int hashCode() {
        if (this.authItemImpl == null || StringUtils.isEmpty(this.authRefType)
                || StringUtils.isEmpty(this.refId)
                || StringUtils.isEmpty(this.authItemImpl.getAuthType())
                || StringUtils.isEmpty(this.authItemImpl.getId())) {
            return super.hashCode();
        } else {
            return this.refId.hashCode() + this.authRefType.hashCode()
                    + this.authItemImpl.hashCode();
        }
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
