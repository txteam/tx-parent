/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年2月12日
 * <修改描述:>
 */
package com.tx.component.config.dao.impl;

import java.util.List;
import java.util.Map;

import com.tx.component.config.dao.ConfigPropertyDao;
import com.tx.component.config.model.ConfigProperty;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年2月12日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultConfigPropertyDaoImpl implements ConfigPropertyDao {
    
    /**
     * @param configProperty
     */
    @Override
    public void save(ConfigProperty configProperty) {
        
    }
    
    /**
     * @param configProperties
     */
    @Override
    public void batchSave(List<ConfigProperty> configProperties) {
    }
    
    /**
     * @param configProperty
     * @return
     */
    @Override
    public ConfigProperty find(ConfigProperty configProperty) {
        return null;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<ConfigProperty> query(Map<String, Object> params) {
        return null;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int count(Map<String, Object> params) {
        return 0;
    }
    
    /**
     * @param configProperty
     * @return
     */
    @Override
    public int delete(ConfigProperty configProperty) {
        return 0;
    }
}
