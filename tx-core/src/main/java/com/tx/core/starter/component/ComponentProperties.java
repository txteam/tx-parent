/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.core.starter.component;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 基础数据容器默认配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年4月27日]
 * @see  [相关类/方法]0
 * @since  [产品/模块版本]
 */
@ConfigurationProperties(prefix = "tx.component")
public class ComponentProperties {
    
    /** 所属模块 */
    private String module;
    
    /** 持久化类型 */
    private ComponentPersisterProperties persister;
    
    /** 缓存配置 */
    private ComponentCacheProperties cache;

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
     * @return 返回 persister
     */
    public ComponentPersisterProperties getPersister() {
        return persister;
    }

    /**
     * @param 对persister进行赋值
     */
    public void setPersister(ComponentPersisterProperties persister) {
        this.persister = persister;
    }

    /**
     * @return 返回 cache
     */
    public ComponentCacheProperties getCache() {
        return cache;
    }

    /**
     * @param 对cache进行赋值
     */
    public void setCache(ComponentCacheProperties cache) {
        this.cache = cache;
    }
}
