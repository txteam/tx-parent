/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月5日
 * <修改描述:>
 */
package com.tx.component.configuration.service;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.tx.component.configuration.model.ConfigProperty;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.querier.model.Querier;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigPropertyManagerComposite implements InitializingBean {
    
    /** 配置属性持久器 */
    private List<ConfigPropertyManager> persisters;
    
    /** <默认构造函数> */
    public ConfigPropertyManagerComposite(
            List<ConfigPropertyManager> persisters) {
        super();
        this.persisters = persisters;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(persisters, "persisters is empty.");
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
    private ConfigPropertyManager getConfigPropertyPersister(String module) {
        for (ConfigPropertyManager persister : persisters) {
            if (persister.supportsModule(module)) {
                return persister;
            }
        }
        return null;
    }
    
    /**
     * @param module
     * @return
     */
    public boolean supportsModule(String module) {
        return getConfigPropertyPersister(module) != null;
    }
    
    /**
     * @param module
     * @param code
     * @return
     */
    public ConfigProperty findByCode(String module, String code) {
        ConfigPropertyManager persister = getConfigPropertyPersister(module);
        
        ConfigProperty cp = persister.findByCode(module, code);
        return cp;
    }
    
    /**
     * @param module
     * @param querier
     * @return
     */
    public List<ConfigProperty> queryList(String module, Querier querier) {
        ConfigPropertyManager persister = getConfigPropertyPersister(module);
        
        List<ConfigProperty> cpList = persister.queryList(module, querier);
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
        ConfigPropertyManager persister = getConfigPropertyPersister(module);
        
        List<ConfigProperty> cpList = persister.queryChildrenByParentId(module,
                parentId,
                querier);
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
        ConfigPropertyManager persister = getConfigPropertyPersister(module);
        
        List<ConfigProperty> cpList = persister
                .queryDescendantsByParentId(module, parentId, querier);
        return cpList;
    }
    
    /**
     * @param module
     * @param code
     * @param value
     * @return
     */
    public boolean patch(String module, String code, String value) {
        ConfigPropertyManager persister = getConfigPropertyPersister(module);
        
        boolean flag = persister.patch(module, code, value);
        return flag;
    }
    
}
