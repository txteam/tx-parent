/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年3月5日
 * <修改描述:>
 */
package com.tx.component.configuration.service.impl;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;

import com.tx.component.configuration.client.ConfigContextAPIClient;
import com.tx.component.configuration.model.ConfigProperty;
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
public class RemoteConfigPropertyManager
        implements ConfigPropertyManager, InitializingBean, Ordered {
    
    /** 所属模块 */
    private String module;
    
    /** 客户端 */
    private ConfigContextAPIClient client;
    
    /** <默认构造函数> */
    public RemoteConfigPropertyManager() {
        super();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(this.module, "module is empty.");
        AssertUtils.notNull(this.client, "client is null.");
    }
    
    @Override
    public boolean supports(String module) {
        if (this.module.equals(module)) {
            return true;
        }
        return false;
    }
    
    /**
     * @param module
     * @param code
     * @param value
     * @return
     */
    @Override
    public boolean patch(String code, String value) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(code, "code is empty.");
        
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
    public ConfigProperty findByCode(String code) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(code, "code is empty.");
        
        ConfigProperty res = client.findByCode(code);
        return res;
    }
    
    /**
     * @param module
     * @param querier
     * @return
     */
    @Override
    public List<ConfigProperty> queryList(Querier querier) {
        AssertUtils.notEmpty(module, "module is empty.");
        
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
    public List<ConfigProperty> queryChildrenByParentId(String parentId,
            Querier querier) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
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
    public List<ConfigProperty> queryDescendantsByParentId(String parentId,
            Querier querier) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        List<ConfigProperty> resList = client
                .queryDescendantsByParentId(parentId, querier);
        return resList;
    }
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
