/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-20
 * <修改描述:>
 */
package com.tx.component.configuration.config;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 配置属性组配置<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-12-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@XStreamAlias("configGroup")
public class ConfigGroupParse {
    
    /** 配置属性组的名字 */
    @XStreamAsAttribute
    private String name;
    
    /** 配置属性 */
    @XStreamImplicit(itemFieldName = "config")
    private List<ConfigPropertyParse> configs;
    
    /** 子级属性组 */
    @XStreamImplicit(itemFieldName = "configGroup")
    private List<ConfigGroupParse> configGroups;
    
    /** 配置属性组是否可见:默认为可见的 */
    @XStreamAsAttribute
    private Boolean viewAble = true;
    
    /** 是否有效，如果valid = false viewAble无论为何值均当做viewAble = false处理  默认为有效的*/
    @XStreamAsAttribute
    private Boolean valid = true;

    /**
     * @return 返回 name
     */
    public String getName() {
        return name;
    }

    /**
     * @param 对name进行赋值
     */
    public void setName(String name) {
        this.name = name;
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
     * @return 返回 viewAble
     */
    public boolean isViewAble() {
        return viewAble == null ? true : viewAble;
    }

    /**
     * @param 对viewAble进行赋值
     */
    public void setViewAble(boolean viewAble) {
        this.viewAble = viewAble;
    }

    /**
     * @return 返回 valid
     */
    public boolean isValid() {
        return valid == null ? true : valid;
    }

    /**
     * @param 对valid进行赋值
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
