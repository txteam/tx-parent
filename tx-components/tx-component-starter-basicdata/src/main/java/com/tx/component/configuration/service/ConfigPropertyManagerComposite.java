/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月5日
 * <修改描述:>
 */
package com.tx.component.configuration.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.OrderComparator;

import com.tx.component.configuration.model.ConfigProperty;
import com.tx.component.configuration.registry.RemoteConfigPropertyManagerRegistry;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.querier.model.Querier;

/**
 * 配置属性manager
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigPropertyManagerComposite implements InitializingBean {
    
    /** 当前项目的module */
    private String module;
    
    /** 远端配置manager */
    private List<ConfigPropertyManager> configPropertyManagers;
    
    /** 远程配置属性业务层 */
    private RemoteConfigPropertyManagerRegistry remoteManagerRegistry;
    
    /** <默认构造函数> */
    public ConfigPropertyManagerComposite(String module,
            List<ConfigPropertyManager> configPropertyManagers) {
        super();
        this.module = module;
        this.configPropertyManagers = configPropertyManagers;
    }
    
    /** <默认构造函数> */
    public ConfigPropertyManagerComposite(String module,
            List<ConfigPropertyManager> configPropertyManagers,
            RemoteConfigPropertyManagerRegistry remoteConfigPropertyServiceRegistry) {
        super();
        this.module = module;
        this.configPropertyManagers = configPropertyManagers;
        
        this.remoteManagerRegistry = remoteConfigPropertyServiceRegistry;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(this.module, "module is empty.");
        AssertUtils.notEmpty(this.configPropertyManagers,
                "configPropertyManagers is null.");
        
        //将manager进行排序
        Collections.sort(this.configPropertyManagers, OrderComparator.INSTANCE);
    }
    
    /**
     * 根据方法参数对象获取对应的方法参数解析器对象<br/> 
     * <功能详细描述>
     * @param parameter
     * @return [参数说明]
     * 
     * @return HandlerMethodArgumentResolver [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private ConfigPropertyManager getConfigPropertyManager(String module) {
        ConfigPropertyManager manager = null;
        for (ConfigPropertyManager m : this.configPropertyManagers) {
            if (m.supports(module)) {
                manager = m;
                return manager;
            }
        }
        if (this.remoteManagerRegistry != null) {
            manager = this.remoteManagerRegistry
                    .buildRemoteConfigPropertyManager(module);
        }
        AssertUtils.notNull(manager,
                "manager is not exists. module:{}.",
                module);
        return manager;
    }
    
    /**
     * @param module
     * @param code
     * @return
     */
    public ConfigProperty findByCode(String module, String code) {
        ConfigPropertyManager persister = getConfigPropertyManager(module);
        
        ConfigProperty cp = persister.findByCode(code);
        return cp;
    }
    
    /**
     * @param module
     * @param querier
     * @return
     */
    public List<ConfigProperty> queryList(String module, Querier querier) {
        ConfigPropertyManager persister = getConfigPropertyManager(module);
        
        List<ConfigProperty> cpList = persister.queryList(querier);
        return cpList;
    }
    
    /**
     * @param module
     * @param parentId
     * @param querier
     * @return
     */
    public List<ConfigProperty> queryChildrenByParentId(String module,
            String parentId, Querier querier) {
        ConfigPropertyManager persister = getConfigPropertyManager(module);
        
        List<ConfigProperty> cpList = persister
                .queryChildrenByParentId(parentId, querier);
        return cpList;
    }
    
    /**
     * @param module
     * @param parentId
     * @param querier
     * @return
     */
    public List<ConfigProperty> queryDescendantsByParentId(String module,
            String parentId, Querier querier) {
        ConfigPropertyManager persister = getConfigPropertyManager(module);
        
        List<ConfigProperty> cpList = persister
                .queryDescendantsByParentId(parentId, querier);
        return cpList;
    }
    
    /**
     * @param module
     * @param code
     * @param value
     * @return
     */
    public boolean patch(String module, String code, String value) {
        ConfigPropertyManager persister = getConfigPropertyManager(module);
        
        boolean flag = persister.patch(code, value);
        return flag;
    }
    
}
