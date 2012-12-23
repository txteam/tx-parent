/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-1
 * <修改描述:>
 */
package com.tx.component.auth.model;

import java.io.Serializable;
import java.util.Date;

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
public class DefaultAuthItemRef implements Serializable, AuthItemRef {
    
    /** 注释内容 */
    private static final long serialVersionUID = -7928952142014599323L;
    
    /**
     * <默认构造函数>
     */
    public DefaultAuthItemRef() {
        super();
    }
    
    /**
     * <默认构造函数>
     */
    public DefaultAuthItemRef(AuthItem authItem) {
        super();
        this.authId = authItem.getId();
        this.authRefType = AuthConstant.AUTHREFTYPE_OPERATOR;
    }
    
    /** 
     * 权限引用对应的权限id
     */
    private String authId;
    
    /**
     * 权限授予人
     */
    private String createOperId;
    
    /** 权限引用项的创建(授予)时间 */
    private Date createDate;
    
    /** 权限引用项的失效时间 */
    private Date endDate;
    
    private String authRefType;
    
    private String refId;
    
    /**
     * 是否支持根据权限引用的引用的结束时间<br/>
     * 判断权限是否需要根据结束时间验证其有效性
     */
    private boolean isValidDependEndDate = false;
    
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
     * @return
     */
    @Override
    public String getAuthId() {
        return authId;
    }
    
    /**
     * @param 对authId进行赋值
     */
    public void setAuthId(String authId) {
        this.authId = authId;
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
        }
        else {
            AuthItemRef otherAuthItemRef = (AuthItemRef) obj;
            if (this.getAuthId() == null || this.getAuthRefType() == null) {
                return this == otherAuthItemRef;
            }
            else {
                return this.getAuthId().equals(otherAuthItemRef.getAuthId())
                        && this.getAuthRefType()
                                .equals(otherAuthItemRef.getAuthRefType());
            }
        }
    }
    
    /**
     * @return
     */
    @Override
    public int hashCode() {
        if (this.getAuthId() == null || this.getAuthRefType() == null) {
            return super.hashCode();
        }
        else {
            return this.getAuthId().hashCode()
                    + this.getAuthRefType().hashCode()
                    + this.getClass().hashCode();
        }
    }
    
    /**
     * @return
     */
    @Override
    public String toString() {
        return MessageFormatter.arrayFormat("authItem: {authId:{},authItemRef:{}}",
                new String[] { this.authId, this.authRefType })
                .getMessage();
    }
}
