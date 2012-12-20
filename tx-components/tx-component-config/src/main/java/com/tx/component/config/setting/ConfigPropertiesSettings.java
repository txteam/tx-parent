/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-17
 * <修改描述:>
 */
package com.tx.component.config.setting;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


 /**
  * <对应各个配置文件>
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2012-10-17]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@XStreamAlias("configProperties")
public class ConfigPropertiesSettings {
    
    @XStreamImplicit(itemFieldName="property")
    private List<ConfigPropertySetting> configPropertyList;

    /**
     * @return 返回 configPropertyList
     */
    public List<ConfigPropertySetting> getConfigPropertyList() {
        return configPropertyList;
    }

    /**
     * @param 对configPropertyList进行赋值
     */
    public void setConfigPropertyList(List<ConfigPropertySetting> configPropertyList) {
        this.configPropertyList = configPropertyList;
    }
}
