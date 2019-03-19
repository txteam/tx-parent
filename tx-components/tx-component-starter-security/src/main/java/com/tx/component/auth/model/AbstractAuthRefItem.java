/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-1
 * <修改描述:>
 */
package com.tx.component.auth.model;

import java.util.Date;

import javax.persistence.Id;

import org.slf4j.helpers.MessageFormatter;

import com.tx.core.exceptions.util.AssertUtils;
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
public abstract class AbstractAuthRefItem implements AuthRef {
    
    /** 注释内容 */
    private static final long serialVersionUID = 7403823613592297657L;
    
    /** 权限引用唯一键盘，全局唯一 */
    @Id
    private String id;
    
    /** 权限引用类型 */
    private String refType;
    
    /** 权限引用唯一键 */
    private String refId;
    
    /** 权限引用对应的权限id */
    private Auth auth;
    
    /** 权限授予人*/
    private String createOperatorId;
    
    /** 权限引用项的创建(授予)时间 */
    private Date createDate;
    
    /** 生效时间 */
    private Date effectiveDate;
    
    /** 系统自动判定的无效时间:系统在查询具体是否存在引用过程中将根据该时间动态计算 */
    private Date expiryDate;
    
    /** <默认构造函数> */
    public AbstractAuthRefItem() {
        super();
    }
    
    /** <默认构造函数> */
    public AbstractAuthRefItem(Auth auth) {
        super();
        AssertUtils.notNull(auth, "auth is null.");
        this.auth = auth;
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
    public Auth getAuth() {
        return this.auth;
    }
    
    /**
     * @return
     */
    @Override
    public String getAuthId() {
        return this.auth == null ? null : this.auth.getId();
    }
    
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
     * @param 对auth进行赋值
     */
    public void setAuth(Auth auth) {
        this.auth = auth;
    }
    
    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        boolean flag = ObjectUtils.equals(this, obj, "id");
        return flag;
    }
    
    /**
     * @return
     */
    @Override
    public int hashCode() {
        int hashCode = ObjectUtils.generateHashCode(super.hashCode(),
                this,
                "id");
        return hashCode;
    }
    
    /**
     * @return
     */
    @Override
    public String toString() {
        return MessageFormatter.arrayFormat(
                "authItemRef: {refId{},auth:{},refId:{},refType:{},id:{},}",
                new Object[] { this.refId, this.auth, this.refId, this.refType,
                        this.id })
                .getMessage();
    }
}
