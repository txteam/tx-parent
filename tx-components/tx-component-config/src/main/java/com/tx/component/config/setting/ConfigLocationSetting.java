/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-16
 * <修改描述:>
 */
package com.tx.component.config.setting;

import java.util.Set;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * <配置资源设置>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-16]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@XStreamAlias("configLocations")
public class ConfigLocationSetting {
    
    @XStreamImplicit(itemFieldName = "configLocation")
    private Set<String> configLocationSet;
    
    /**
     * @return 返回 configLocationSet
     */
    public Set<String> getConfigLocationSet() {
        return configLocationSet;
    }
    
    /**
     * @param 对configLocationSet进行赋值
     */
    public void setConfigLocationSet(Set<String> configLocationSet) {
        this.configLocationSet = configLocationSet;
    }
}
