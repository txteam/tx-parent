/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月4日
 * <修改描述:>
 */
package com.tx.component.configuration.config;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 配置内容解析<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@XStreamAlias("configs")
public class ConfigContentParser {
    
    /** 配置属性 */
    @XStreamImplicit(itemFieldName = "catalog")
    private List<ConfigCatalogParser> catalogs;
    
    /** 配置属性 */
    @XStreamImplicit(itemFieldName = "config")
    private List<ConfigPropertyParser> configs;

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
