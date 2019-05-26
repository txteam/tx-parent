/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.tx.component.configuration.starter.ConfigContextProperties;

/**
 * 基础数据容器默认配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年4月27日]
 * @see  [相关类/方法]0
 * @since  [产品/模块版本]
 */
@ConfigurationProperties(prefix = "tx.component.basicdata")
public class BasicDataContextProperties {
    
    /** 命令容器是否启动 */
    private boolean enable;
    
    /** 表是否自动初始化 */
    private boolean tableAutoInitialize = false;
    
    /** 基础包集合 */
    private String basePackages = "com.tx.local";
    
    /** 持久化类型 */
    //private PersisterTypeEnum persister = PersisterTypeEnum.mybatis;
    
    /** 所属模块 */
    private String module;
    
    /** 缓存manager的引用 */
    private String cacheManagerRef;
    
    /** 缓存的有效期:默认缓存一天 */
    private Duration duration = Duration.ofDays(1);
    
    /** 配置属性 */
    private ConfigContextProperties config;
    
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
     * @return 返回 tableAutoInitialize
     */
    public boolean isTableAutoInitialize() {
        return tableAutoInitialize;
    }
    
    /**
     * @param 对tableAutoInitialize进行赋值
     */
    public void setTableAutoInitialize(boolean tableAutoInitialize) {
        this.tableAutoInitialize = tableAutoInitialize;
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
    
    //    /**
    //     * @return 返回 persister
    //     */
    //    public PersisterTypeEnum getPersister() {
    //        return persister;
    //    }
    //
    //    /**
    //     * @param 对persister进行赋值
    //     */
    //    public void setPersister(PersisterTypeEnum persister) {
    //        this.persister = persister;
    //    }
    
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
     * @return 返回 cacheManagerRef
     */
    public String getCacheManagerRef() {
        return cacheManagerRef;
    }
    
    /**
     * @param 对cacheManagerRef进行赋值
     */
    public void setCacheManagerRef(String cacheManagerRef) {
        this.cacheManagerRef = cacheManagerRef;
    }
    
    /**
     * @return 返回 duration
     */
    public Duration getDuration() {
        return duration;
    }
    
    /**
     * @param 对duration进行赋值
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }
    
    /**
     * @return 返回 config
     */
    public ConfigContextProperties getConfig() {
        return config;
    }
    
    /**
     * @param 对config进行赋值
     */
    public void setConfig(ConfigContextProperties config) {
        this.config = config;
    }
}
