/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月4日
 * <修改描述:>
 */
package com.tx.component.plugin.loginplugin;

import com.tx.component.plugin.context.PluginConfig;

/**
 * 登录插件配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class LoginPluginConfig extends PluginConfig {
    
    /** logo */
    private String logo;
    
    /** 登陆 action name */
    private String loginActionName;
    
    /** 登陆view name */
    private String loginViewName;
    
    /** 登陆备注 */
    private String remark;
    
    /**
     * @return 返回 logo
     */
    public String getLogo() {
        return logo;
    }
    
    /**
     * @param 对logo进行赋值
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }
    
    /**
     * @return 返回 loginActionName
     */
    public String getLoginActionName() {
        return loginActionName;
    }
    
    /**
     * @param 对loginActionName进行赋值
     */
    public void setLoginActionName(String loginActionName) {
        this.loginActionName = loginActionName;
    }
    
    /**
     * @return 返回 loginViewName
     */
    public String getLoginViewName() {
        return loginViewName;
    }
    
    /**
     * @param 对loginViewName进行赋值
     */
    public void setLoginViewName(String loginViewName) {
        this.loginViewName = loginViewName;
    }
    
    /**
     * @return 返回 remark
     */
    public String getRemark() {
        return remark;
    }
    
    /**
     * @param 对remark进行赋值
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
