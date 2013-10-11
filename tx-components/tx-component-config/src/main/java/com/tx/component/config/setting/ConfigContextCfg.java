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
 * 配置容器设置<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@XStreamAlias("configContext")
public class ConfigContextCfg {
    
    /** 是否处于开发模式  开发模式中 getValue 将优先获取 developValue */
    @XStreamAlias("isDevelop")
    private boolean isDevelop = false;
    
    /** 配置是否可重复 */
    @XStreamAlias("repeatAble")
    private boolean repeatAble = false;
    
    /** 配置是否可重复 */
    @XStreamAlias("configResourcePersisterPackage")
    private String configResourcePersisterPackage;
    
    @XStreamAlias("configResources")
    private Set<ConfigResource> configResources = new HashSet<ConfigResource>();
    
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
     * @return 返回 configResources
     */
    public Set<ConfigResource> getConfigResources() {
        return configResources;
    }
    
    /**
     * @param 对configResources进行赋值
     */
    public void setConfigResources(Set<ConfigResource> configResources) {
        this.configResources = configResources;
    }
    
    /**
     * @return 返回 configResourcePersisterPackage
     */
    public String getConfigResourcePersisterPackage() {
        return configResourcePersisterPackage;
    }
    
    /**
     * @param 对configResourcePersisterPackage进行赋值
     */
    public void setConfigResourcePersisterPackage(
            String configResourcePersisterPackage) {
        this.configResourcePersisterPackage = configResourcePersisterPackage;
    }
}
