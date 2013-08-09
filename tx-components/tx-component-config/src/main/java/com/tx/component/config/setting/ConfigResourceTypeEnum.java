/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-8-2
 * <修改描述:>
 */
package com.tx.component.config.setting;

import com.tx.component.config.support.ConfigResourcePropertiesPersister;


 /**
  * 配置资源类型枚举<br/>
  * <功能详细描述>
  * 
  * @author  wanxin
  * @version  [版本号, 2013-8-2]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public enum ConfigResourceTypeEnum {
    /** 基于配置容器总表*/
    DEFAULT("database",null),
    /** 基于数据表的配置资源 */
    DATABASE_TABLE("database",null),
    /** 基与属性文件的配置资源 */
    PROPERTY_FILE("property",null),
    /** 自定义的配置文件资源 */
    CUSTOMIZED("customized",null);
    
    /** 配置资源类型名 */
    private String name;
    
    /** 配置资源类型对应的解析器 */
    private ConfigResourcePropertiesPersister persister;

    private ConfigResourceTypeEnum(String name, ConfigResourcePropertiesPersister persister) {
        this.name = name;
        this.persister = persister;
    }

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
     * @return 返回 persister
     */
    public ConfigResourcePropertiesPersister getPersister() {
        return persister;
    }

    /**
     * @param 对persister进行赋值
     */
    public void setPersister(ConfigResourcePropertiesPersister persister) {
        this.persister = persister;
    }
}
