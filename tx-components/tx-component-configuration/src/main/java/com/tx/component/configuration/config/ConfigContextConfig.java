/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-20
 * <修改描述:>
 */
package com.tx.component.configuration.config;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 配置容器配置<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-12-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@XStreamAlias("configContext")
public class ConfigContextConfig {
    
    /** 子级属性组 */
    @XStreamImplicit(itemFieldName = "configGroup")
    private List<ConfigGroupParse> configGroups;
    
    /** 配置属性 */
    @XStreamImplicit(itemFieldName = "config")
    private List<ConfigPropertyParse> configs;

    /**
     * @return 返回 configGroups
     */
    public List<ConfigGroupParse> getConfigGroups() {
        return configGroups;
    }

    /**
     * @param 对configGroups进行赋值
     */
    public void setConfigGroups(List<ConfigGroupParse> configGroups) {
        this.configGroups = configGroups;
    }

    /**
     * @return 返回 configs
     */
    public List<ConfigPropertyParse> getConfigs() {
        return configs;
    }

    /**
     * @param 对configs进行赋值
     */
    public void setConfigs(List<ConfigPropertyParse> configs) {
        this.configs = configs;
    }
}
