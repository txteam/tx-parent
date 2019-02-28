/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年2月8日
 * <修改描述:>
 */
package com.tx.component.config.context.impl;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.CacheManager;

import com.tx.component.config.context.ConfigPropertyFinder;
import com.tx.component.config.exception.ConfigContextInitException;
import com.tx.component.config.model.ConfigProperty;
import com.tx.component.config.model.ConfigPropertyItem;
import com.tx.component.config.service.ConfigPropertyRepository;
import com.tx.component.config.service.impl.DefaultConfigPropertyRepositoryImpl;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年2月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class LocalConfigPropertyFinderImpl
        implements ConfigPropertyFinder, InitializingBean {
    
    /** 本地化配置 */
    private String configLocation;
    
    /** 缓存管理器 */
    private CacheManager cacheManager;
    
    /** 配置属性仓库 */
    private ConfigPropertyRepository configPropertyRepository;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(configLocation,
                ConfigContextInitException.class,
                "configLocation is empty.");
        AssertUtils.notNull(cacheManager,
                ConfigContextInitException.class,
                "cacheManager is null.");
        
        if (this.configPropertyRepository == null) {
            this.configPropertyRepository = new DefaultConfigPropertyRepositoryImpl();
        }
    }
    
    /**
     * @param code
     * @return
     */
    @Override
    public ConfigProperty find(String code) {
        ConfigPropertyItem condition = new ConfigPropertyItem();
        condition.setCode(code);
        
        ConfigProperty res = this.configPropertyRepository.find(condition);
        return res;
    }
    
    /**
     * @return
     */
    @Override
    public List<ConfigProperty> list() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
