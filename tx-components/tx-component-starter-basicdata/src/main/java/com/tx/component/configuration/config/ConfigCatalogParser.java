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
 * 属性配置<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-12-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@XStreamAlias("catalog")
public class ConfigCatalogParser {
    
    /** 关键字 */
    @XStreamAsAttribute
    private String code;
    
    /** 配置资源名 */
    @XStreamAsAttribute
    private String name;
    
    /** 配置属性 */
    @XStreamImplicit(itemFieldName = "catalog")
    private List<ConfigCatalogParser> catalogs;
    
    /** 配置属性 */
    @XStreamImplicit(itemFieldName = "config")
    private List<ConfigPropertyParser> configs;
    
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
     * @return 返回 code
     */
    public String getCode() {
        return code;
    }
    
    /**
     * @param 对code进行赋值
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * @return 返回 catalogs
     */
    public List<ConfigCatalogParser> getCatalogs() {
        return catalogs;
    }
    
    /**
     * @param 对catalogs进行赋值
     */
    public void setCatalogs(List<ConfigCatalogParser> catalogs) {
        this.catalogs = catalogs;
    }
    
    /**
     * @return 返回 configs
     */
    public List<ConfigPropertyParser> getConfigs() {
        return configs;
    }
    
    /**
     * @param 对configs进行赋值
     */
    public void setConfigs(List<ConfigPropertyParser> configs) {
        this.configs = configs;
    }
    
}
