/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.configuration.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 基础数据容器默认配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年4月27日]
 * @see  [相关类/方法]0
 * @since  [产品/模块版本]
 */
@ConfigurationProperties(prefix = "tx.basicdata.config")
public class ConfigContextProperties {
    
    /** 命令容器是否启动 */
    private boolean enable = true;
    
    /** 配置文件所在路径 */
    private String configLocation = "classpath:config/local/*.xml";
    
    /**
     * @return 返回 enable
     */
    public boolean isEnable() {
        return enable;
    }
    
    /**
     * @param 对enable进行赋值
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    
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
