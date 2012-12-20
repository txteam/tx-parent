/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-14
 * <修改描述:>
 */
package com.tx.component.config.setting;

import java.util.HashSet;
import java.util.Set;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <配置容器设置>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@XStreamAlias("configContext")
public class ConfigContextSetting {
    
    /** 是否处于开发模式  开发模式中 getValue 将优先获取 developValue */
    @XStreamAlias("isDev")
    private boolean isDevelop = false;
    
    /** 配置是否可重复 */
    private boolean repeatAble = false;
    
    @XStreamAlias("configResources")
    private Set<ConfigResourceSetting> configResourceSettingSet = new HashSet<ConfigResourceSetting>();
    
    @XStreamAlias("ConfigLocations")
    private ConfigLocationSetting configLocationSetting = new ConfigLocationSetting();
    
    /**
     * @return 返回 isDevelop
     */
    public boolean isDevelop() {
        return isDevelop;
    }
    
    /**
     * @param 对isDevelop进行赋值
     */
    public void setDevelop(boolean isDevelop) {
        this.isDevelop = isDevelop;
    }
    
    /**
     * @return 返回 repeatAble
     */
    public boolean isRepeatAble() {
        return repeatAble;
    }
    
    /**
     * @param 对repeatAble进行赋值
     */
    public void setRepeatAble(boolean repeatAble) {
        this.repeatAble = repeatAble;
    }
    
    /**
     * @return 返回 configResourceSettingSet
     */
    public Set<ConfigResourceSetting> getConfigResourceSettingSet() {
        return configResourceSettingSet;
    }
    
    /**
     * @param 对configResourceSettingSet进行赋值
     */
    public void setConfigResourceSettingSet(
            Set<ConfigResourceSetting> configResourceSettingSet) {
        this.configResourceSettingSet = configResourceSettingSet;
    }

    /**
     * @return 返回 configLocationSetting
     */
    public ConfigLocationSetting getConfigLocationSetting() {
        return configLocationSetting;
    }

    /**
     * @param 对configLocationSetting进行赋值
     */
    public void setConfigLocationSetting(ConfigLocationSetting configLocationSetting) {
        this.configLocationSetting = configLocationSetting;
    }
    
}
