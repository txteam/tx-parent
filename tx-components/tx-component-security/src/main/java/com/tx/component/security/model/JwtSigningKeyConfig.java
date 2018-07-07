/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月28日
 * <修改描述:>
 */
package com.tx.component.security.model;

/**
 * Jwt签名Key对象<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月28日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JwtSigningKeyConfig {
    
    /** 类型:type */
    private String subject;
    
    /** 类型对应的有效期时常： 单位秒 */
    private long expiration;
    
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
}
