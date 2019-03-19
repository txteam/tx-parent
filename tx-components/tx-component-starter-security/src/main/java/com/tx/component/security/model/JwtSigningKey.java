/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月28日
 * <修改描述:>
 */
package com.tx.component.security.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Jwt签名Key对象<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月28日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
@Table(name = "sec_jwt_signing_key")
public class JwtSigningKey {
    
    /** 唯一键key */
    @Id
    private String id;
    
    /** 类型:type */
    private String subject;
    
    /** 类型对应的有效期时常： 单位秒 */
    private long expiration;
    
    /** 签名:code */
    private String signingKeyCode;
    
    /** 签名:key */
    private String signingKey;
    
    /** 创建时间 */
    private Date createDate;
    
    /** 有效时间：单位秒 */
    private long duration;
    
    /** 是否有效：代码尽量保证有且仅有一个有效，已经过期的在创建新的值以后，再去禁用已经超过有效期的 */
    private boolean valid = true;
    
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
     * @return 返回 subject
     */
    public String getSubject() {
        return subject;
    }
    
    /**
     * @param 对subject进行赋值
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    /**
     * @return 返回 expiration
     */
    public long getExpiration() {
        return expiration;
    }
    
    /**
     * @param 对expiration进行赋值
     */
    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
    
    /**
     * @return 返回 signingKeyCode
     */
    public String getSigningKeyCode() {
        return signingKeyCode;
    }
    
    /**
     * @param 对signingKeyCode进行赋值
     */
    public void setSigningKeyCode(String signingKeyCode) {
        this.signingKeyCode = signingKeyCode;
    }
    
    /**
     * @return 返回 signingKey
     */
    public String getSigningKey() {
        return signingKey;
    }
    
    /**
     * @param 对signingKey进行赋值
     */
    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }
    
    /**
     * @return 返回 createDate
     */
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
     * @return 返回 duration
     */
    public long getDuration() {
        return duration;
    }
    
    /**
     * @param 对duration进行赋值
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }
    
    /**
     * @return 返回 valid
     */
    public boolean isValid() {
        return valid;
    }
    
    /**
     * @param 对valid进行赋值
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
}
