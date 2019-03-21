/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.configuration.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tx.component.configuration.dao.ConfigPropertyItemDao;
import com.tx.component.configuration.model.ConfigPropertyItem;
import com.tx.core.mybatis.support.MyBatisDaoSupport;

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
    
    @PersistenceContext
    private EntityManager entityManager;
    
    /** <默认构造函数> */
    public ConfigPropertyItemDaoJPAImpl() {
        super();
    }
    
    /** <默认构造函数> */
    public ConfigPropertyItemDaoJPAImpl(MyBatisDaoSupport myBatisDaoSupport) {
        super();
        this.myBatisDaoSupport = myBatisDaoSupport;
    }
    
    /**
     * @param condition
     */
    @Override
    public void insert(ConfigPropertyItem condition) {
        this.myBatisDaoSupport.insertUseUUID("configPropertyItem.insert",
                condition,
                "id");
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<ConfigPropertyItem> queryList(Map<String, Object> params) {
        return this.myBatisDaoSupport.<ConfigPropertyItem> queryList(
                "configPropertyItem.query", params);
    }
    
    /**
     * @param updateRowMap
     * @return
     */
    @Override
    public int update(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport.update("configPropertyItem.update",
                updateRowMap);
    }
}
