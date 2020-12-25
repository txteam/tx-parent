/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月17日
 * <修改描述:>
 */
package com.tx.component.social.model;

import java.io.Serializable;

import com.tx.core.support.json.JSONAttributesSupport;

/**
 * 微信用户信息<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SocialAccessToken implements JSONAttributesSupport,Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = -4649235640596483621L;

    /** 唯一键 */
    private String id;
    
    /** 用户的唯一键id==openId */
    private String uniqueId;
    
    /** refresgToken */
    private String refreshToken;
    
    /** 单次访问的accessToken */
    private String accessToken;
    
    /** token类型 */
    private String tokenType;
    
    /** 到期时间 */
    private Long expiresIn;
    
    /** 参数范围 */
    private String scope;
    
    /** 其他参数 */
    private String attributes;

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
     * @return 返回 uniqueId
     */
    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * @param 对uniqueId进行赋值
     */
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * @return 返回 refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param 对refreshToken进行赋值
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * @return 返回 accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param 对accessToken进行赋值
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return 返回 tokenType
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * @param 对tokenType进行赋值
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * @return 返回 expiresIn
     */
    public Long getExpiresIn() {
        return expiresIn;
    }

    /**
     * @param 对expiresIn进行赋值
     */
    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    /**
     * @return 返回 scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param 对scope进行赋值
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * @return 返回 attributes
     */
    public String getAttributes() {
        return attributes;
    }

    /**
     * @param 对attributes进行赋值
     */
    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }
}
