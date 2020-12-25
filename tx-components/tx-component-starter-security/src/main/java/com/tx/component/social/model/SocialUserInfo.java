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
public class SocialUserInfo implements JSONAttributesSupport, Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = -9083339648693769127L;
    
    /** 唯一键 */
    private String id;
    
    /** 第三方账户类型 */
    private SocialAccountTypeEnum type;
    
    /** 用户的唯一键id==openId */
    private String uniqueId;
    
    /** 昵称 */
    private String username;
    
    /** 性别 */
    private SexEnum sex;
    
    /** 头像图片url */
    private String headImgUrl;
    
    /** 额外参数 */
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
     * @return 返回 type
     */
    public SocialAccountTypeEnum getType() {
        return type;
    }
    
    /**
     * @param 对type进行赋值
     */
    public void setType(SocialAccountTypeEnum type) {
        this.type = type;
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
     * @return 返回 username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * @param 对username进行赋值
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * @return 返回 sex
     */
    public SexEnum getSex() {
        return sex;
    }
    
    /**
     * @param 对sex进行赋值
     */
    public void setSex(SexEnum sex) {
        this.sex = sex;
    }
    
    /**
     * @return 返回 headImgUrl
     */
    public String getHeadImgUrl() {
        return headImgUrl;
    }
    
    /**
     * @param 对headImgUrl进行赋值
     */
    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }
    
    /**
     * @return 返回 attributes
     */
    @Override
    public String getAttributes() {
        return attributes;
    }
    
    /**
     * @param 对attributes进行赋值
     */
    @Override
    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }
    
    /**
     * 性别枚举<br/>
     * <功能详细描述>
     * 
     * @author  PengQingyang
     * @version  [版本号, 2020年12月18日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public static enum SexEnum {
        
        MALE("MALE", "男"),
        
        FEMALE("FEMALE", "女");
        
        private final String code;
        
        private final String name;
        
        /** <默认构造函数> */
        private SexEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }
        
        /**
         * @return 返回 code
         */
        public String getCode() {
            return code;
        }
        
        /**
         * @return 返回 name
         */
        public String getName() {
            return name;
        }
    }
}
