/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月5日
 * <修改描述:>
 */
package com.tx.component.configuration.persister;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import com.tx.component.configuration.model.ConfigProperty;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigPropertyPersisterComposite
        implements ConfigPropertyPersister, InitializingBean {
    
    /** 配置属性持久器 */
    private List<ConfigPropertyPersister> persisters;
    
    /** <默认构造函数> */
    public ConfigPropertyPersisterComposite(
            List<ConfigPropertyPersister> persisters) {
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
    private ConfigPropertyPersister getConfigPropertyPersister(String module) {
        for (ConfigPropertyPersister persister : persisters) {
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
    @Override
    public boolean supportsModule(String module) {
        return getConfigPropertyPersister(module) != null;
    }
    
    /**
     * @param module
     * @param code
     * @return
     */
    @Override
    public ConfigProperty findByCode(String module, String code) {
        ConfigPropertyPersister persister = getConfigPropertyPersister(module);
        
        ConfigProperty cp = persister.findByCode(module, code);
        return cp;
    }
    
    /**
     * @param module
     * @param params
     * @return
     */
    @Override
    public List<ConfigProperty> queryList(String module,
            Map<String, Object> params) {
        ConfigPropertyPersister persister = getConfigPropertyPersister(module);
        
        List<ConfigProperty> cpList = persister.queryList(module, params);
        return cpList;
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
        ConfigPropertyPersister persister = getConfigPropertyPersister(module);
        
        List<ConfigProperty> cpList = persister.queryChildsByParentId(module,
                parentId,
                params);
        return cpList;
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
        ConfigPropertyPersister persister = getConfigPropertyPersister(module);
        
        List<ConfigProperty> cpList = persister
                .queryNestedChildsByParentId(module, parentId, params);
        return cpList;
    }
    
}
