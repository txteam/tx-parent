/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.config.starter;

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
    
    /** 容器所属模块：当该值为空时，使用spring.application.name的内容 */
    private String module;
    
    /** 配置路径 */
    private String configLocation;
    
    /** 服务器端url:可以为eureka重的应用名 */
    private String serverUrl;
    
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
    
    /**
     * @return 返回 module
     */
    public String getModule() {
        return module;
    }
    
    /**
     * @param 对module进行赋值
     */
    public void setModule(String module) {
        this.module = module;
    }
    
    /**
     * @return 返回 serverUrl
     */
    public String getServerUrl() {
        return serverUrl;
    }
    
    /**
     * @param 对serverUrl进行赋值
     */
    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}
