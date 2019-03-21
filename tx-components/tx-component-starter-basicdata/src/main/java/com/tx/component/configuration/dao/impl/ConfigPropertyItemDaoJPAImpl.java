/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.configuration.dao.impl;

import java.util.List;
import java.util.Map;

import com.tx.component.configuration.dao.ConfigPropertyItemDao;
import com.tx.component.configuration.model.ConfigPropertyItem;

/**
 * DataDict持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigPropertyItemDaoJPAImpl implements ConfigPropertyItemDao {
    
    /** <默认构造函数> */
    public ConfigPropertyItemDaoJPAImpl() {
        super();
    }

    /**
     * @param configPropertyItem
     */
    @Override
    public void insert(ConfigPropertyItem configPropertyItem) {
        // TODO Auto-generated method stub
        
    }

    /**
     * @param rowMap
     * @return
     */
    @Override
    public int update(Map<String, Object> rowMap) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @param params
     * @return
     */
    @Override
    public List<ConfigPropertyItem> queryList(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return null;
    }
}
