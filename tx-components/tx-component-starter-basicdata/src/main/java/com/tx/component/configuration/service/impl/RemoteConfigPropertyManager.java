/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年3月5日
 * <修改描述:>
 */
package com.tx.component.configuration.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.tx.component.configuration.client.ConfigContextAPIClient;
import com.tx.component.configuration.model.ConfigProperty;
import com.tx.component.configuration.registry.ConfigAPIClientRegistry;
import com.tx.component.configuration.service.ConfigPropertyManager;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.querier.model.Querier;

/**
 * 本地配置属性查询器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年3月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RemoteConfigPropertyManager implements ConfigPropertyManager {
    
    /** 所属模块 */
    private String module;
    
    /** 配置客户端注册表 */
    private ConfigAPIClientRegistry configAPIClientRegistry;
    
    /** <默认构造函数> */
    public RemoteConfigPropertyManager(String module,
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
     * @param value
     * @return
     */
    @Override
    public boolean patch(String module, String code, String value) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(code, "code is empty.");
        
        ConfigContextAPIClient client = configAPIClientRegistry
                .getConfigAPIClient(module);
        value = value == null ? "" : value;
        
        boolean res = client.patch(code, value);
        return res;
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
        
        ConfigContextAPIClient client = configAPIClientRegistry
                .getConfigAPIClient(module);
        
        ConfigProperty res = client.findByCode(code);
        return res;
    }
    
    /**
     * @param module
     * @param querier
     * @return
     */
    @Override
    public List<ConfigProperty> queryList(String module, Querier querier) {
        AssertUtils.notEmpty(module, "module is empty.");
        
        ConfigContextAPIClient client = configAPIClientRegistry
                .getConfigAPIClient(module);
        
        List<ConfigProperty> resList = client.queryList(querier);
        return resList;
    }
    
    /**
     * @param module
     * @param parentId
     * @param querier
     * @return
     */
    @Override
    public List<ConfigProperty> queryChildrenByParentId(String module,
            String parentId, Querier querier) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        ConfigContextAPIClient client = configAPIClientRegistry
                .getConfigAPIClient(module);
        
        List<ConfigProperty> resList = client.queryChildrenByParentId(parentId,
                querier);
        return resList;
    }
    
    /**
     * @param module
     * @param parentId
     * @param querier
     * @return
     */
    @Override
    public List<ConfigProperty> queryDescendantsByParentId(String module,
            String parentId, Querier querier) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        ConfigContextAPIClient client = configAPIClientRegistry
                .getConfigAPIClient(module);
        
        List<ConfigProperty> resList = client
                .queryDescendantsByParentId(parentId, querier);
        return resList;
    }
    
}
