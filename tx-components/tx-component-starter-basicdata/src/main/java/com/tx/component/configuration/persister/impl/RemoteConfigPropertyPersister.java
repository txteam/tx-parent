/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年3月5日
 * <修改描述:>
 */
package com.tx.component.configuration.persister.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.tx.component.configuration.client.ConfigAPIClient;
import com.tx.component.configuration.model.ConfigProperty;
import com.tx.component.configuration.persister.ConfigPropertyPersister;
import com.tx.component.configuration.registry.ConfigAPIClientRegistry;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 本地配置属性查询器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年3月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RemoteConfigPropertyPersister implements ConfigPropertyPersister {
    
    /** 所属模块 */
    private String module;
    
    /** 配置客户端注册表 */
    private ConfigAPIClientRegistry configAPIClientRegistry;
    
    /** <默认构造函数> */
    public RemoteConfigPropertyPersister(String module,
            ConfigAPIClientRegistry configAPIClientRegistry) {
        super();
        this.module = module;
        this.configAPIClientRegistry = configAPIClientRegistry;
    }
    
    /**
     * @param module
     * @return
     */
    @Override
    public boolean supportsModule(String module) {
        if (StringUtils.isEmpty(module)
                || StringUtils.equalsAnyIgnoreCase(this.module, module)) {
            return false;
        }
        return true;
    }
    
    /**
     * @param module
     * @param code
     * @return
     */
    @Override
    public ConfigProperty findByCode(String module, String code) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(code, "code is empty.");
        
        ConfigAPIClient client = configAPIClientRegistry
                .getConfigAPIClient(module);
        
        ConfigProperty res = client.findByCode(code);
        return res;
    }
    
    /**
     * @param module
     * @param params
     * @return
     */
    @Override
    public List<ConfigProperty> queryList(String module,
            Map<String, Object> params) {
        AssertUtils.notEmpty(module, "module is empty.");
        
        ConfigAPIClient client = configAPIClientRegistry
                .getConfigAPIClient(module);
        
        List<ConfigProperty> resList = client.queryList(params);
        return resList;
    }
    
    /**
     * @param module
     * @param parentId
     * @param params
     * @return
     */
    @Override
    public List<ConfigProperty> queryChildsByParentId(String module,
            String parentId, Map<String, Object> params) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        ConfigAPIClient client = configAPIClientRegistry
                .getConfigAPIClient(module);
        
        List<ConfigProperty> resList = client.queryChildsByParentId(parentId,
                params);
        return resList;
    }
    
    /**
     * @param module
     * @param parentId
     * @param params
     * @return
     */
    @Override
    public List<ConfigProperty> queryNestedChildsByParentId(String module,
            String parentId, Map<String, Object> params) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        ConfigAPIClient client = configAPIClientRegistry
                .getConfigAPIClient(module);
        
        List<ConfigProperty> resList = client
                .queryNestedChildsByParentId(parentId, params);
        return resList;
    }
    
}
