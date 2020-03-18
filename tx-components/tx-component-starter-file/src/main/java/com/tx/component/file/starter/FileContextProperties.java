/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.file.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.tx.component.file.FileContextConstants;

/**
 * 基础数据容器默认配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年4月27日]
 * @see  [相关类/方法]0
 * @since  [产品/模块版本]
 */
@ConfigurationProperties(prefix = FileContextConstants.PROPERTIES_PREFIX)
public class FileContextProperties {
    
    /** 命令容器是否启动 */
    private boolean enable;
    
    /** 所属模块:可以为空，默认使用项目名作为模块名 */
    private String module;
    
    /** 基础包集合 */
    private String basePackages = "com.tx.local";
    
    /** 文件容器默认的存放路径 */
    private DefaultsFileCatalogProperties defaults;
    
    /**
     * @return 返回 enable
     */
    public boolean isEnable() {
        return enable;
    }
    
    /**
     * @param 对enable进行赋值
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    
    /**
     * @return 返回 basePackages
     */
    public String getBasePackages() {
        return basePackages;
    }
    
    /**
     * @param 对basePackages进行赋值
     */
    public void setBasePackages(String basePackages) {
        this.basePackages = basePackages;
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
     * @return 返回 defaults
     */
    public DefaultsFileCatalogProperties getDefaults() {
        return defaults;
    }
    
    /**
     * @param 对defaults进行赋值
     */
    public void setDefaults(DefaultsFileCatalogProperties defaults) {
        this.defaults = defaults;
    }
}
