/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月27日
 * <修改描述:>
 */
package com.tx.component.auth.configuration;

import com.tx.component.auth.AuthConstants;

/**
 * 角色容器属性<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthContextProperties {
    
    /** 配置文件所在路径 */
    private String configLocation = AuthConstants.DEFAULT_AUTH_CONFIG_PATH;
    
    /**
     * @return 返回 configLocation
     */
    public String getConfigLocation() {
        return configLocation;
    }
    
    /**
     * @param 对configLocation进行赋值
     */
    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }
}
