/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.tx.component.basicdata.BasicDataContextConstants;

/**
 * 基础数据容器默认配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年4月27日]
 * @see  [相关类/方法]0
 * @since  [产品/模块版本]
 */
@ConfigurationProperties(prefix = BasicDataContextConstants.PROPERTIES_PREFIX)
public class BasicDataContextProperties {
    
    /** 命令容器是否启动 */
    private boolean enable;
    
    /** 基础包集合 */
    private String basePackages = "com.tx.local";
    
    /** 所属模块 */
    private String module;
    
    /** 缓存manager的引用 */
    private String cacheManagerRef;
    
    /** 缓存的有效期:默认缓存一天 */
    /*
     * <pre>
     *    "PT20.345S" -- parses as "20.345 seconds"
     *    "PT15M"     -- parses as "15 minutes" (where a minute is 60 seconds)
     *    "PT10H"     -- parses as "10 hours" (where an hour is 3600 seconds)
     *    "P2D"       -- parses as "2 days" (where a day is 24 hours or 86400 seconds)
     *    "P2DT3H4M"  -- parses as "2 days, 3 hours and 4 minutes"
     *    "P-6H3M"    -- parses as "-6 hours and +3 minutes"
     *    "-P6H3M"    -- parses as "-6 hours and -3 minutes"
     *    "-P-6H+3M"  -- parses as "+6 hours and -3 minutes"
     * </pre>
     */
    private Duration duration = Duration.ofDays(1);
    
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
}
