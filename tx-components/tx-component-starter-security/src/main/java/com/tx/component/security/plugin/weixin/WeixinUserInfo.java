/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月25日
 * <修改描述:>
 */
package com.tx.component.security.plugin.weixin;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 微信用户信息<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月25日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class WeixinUserInfo {
    
    @JSONField(name="openid")
    private String openId;
    
    private String nickname;
    
    private int sex;
    
    private String province;
    
    private String city;
    
    private String country;
    
    @JSONField(name="headimgurl")
    private String headImageUrl;
    
    @JSONField(name="unionid")
    private String unionId;
    
    private List<String> privilege;

    /**
     * @return 返回 openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * @param 对openId进行赋值
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * @return 返回 nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param 对nickname进行赋值
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return 返回 sex
     */
    public int getSex() {
        return sex;
    }

    /**
     * @param 对sex进行赋值
     */
    public void setSex(int sex) {
        this.sex = sex;
    }

    /**
     * @return 返回 province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param 对province进行赋值
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return 返回 city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param 对city进行赋值
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return 返回 country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param 对country进行赋值
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return 返回 headImageUrl
     */
    public String getHeadImageUrl() {
        return headImageUrl;
    }

    /**
     * @param 对headImageUrl进行赋值
     */
    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    /**
     * @return 返回 unionId
     */
    public String getUnionId() {
        return unionId;
    }

    /**
     * @param 对unionId进行赋值
     */
    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    /**
     * @return 返回 privilege
     */
    public List<String> getPrivilege() {
        return privilege;
    }

    /**
     * @param 对privilege进行赋值
     */
    public void setPrivilege(List<String> privilege) {
        this.privilege = privilege;
    }
}
