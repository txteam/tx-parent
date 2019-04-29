/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.configuration.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import com.tx.component.configuration.dao.ConfigPropertyItemDao;
import com.tx.component.configuration.model.ConfigPropertyItem;
import com.tx.core.exceptions.util.AssertUtils;

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
    
    /** entityManager */
    @PersistenceContext
    protected EntityManager entityManager;
    
    /** <默认构造函数> */
    public ConfigPropertyItemDaoJPAImpl() {
        super();
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public ConfigPropertyItem find(ConfigPropertyItem condition) {
        ConfigPropertyItem cpi = entityManager.find(ConfigPropertyItem.class,
                condition.getId());
        return cpi;
    }
    
    /**
     * @param configPropertyItem
     */
    @Override
    public void insert(ConfigPropertyItem configPropertyItem) {
        entityManager.persist(configPropertyItem);
    }
    
    /**
     * @param rowMap
     * @return
     */
    @Override
    public void update(Map<String, Object> rowMap) {
        AssertUtils.notNull(rowMap, "rowMap is null.");
        AssertUtils.notEmpty(rowMap.get("id"), "id is empty.");
        
        ConfigPropertyItem cpi = entityManager.find(ConfigPropertyItem.class,
                rowMap.get("id"));
        if (cpi == null) {
            return ;
        }
        
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(cpi);
        for (Entry<String, Object> entryTemp : rowMap.entrySet()) {
            if (StringUtils.equalsAnyIgnoreCase("id", entryTemp.getKey())) {
                continue;
            }
            if(bw.isWritableProperty(entryTemp.getKey())){
                bw.setPropertyValue(entryTemp.getKey(), entryTemp.getValue());
            }
        }
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<ConfigPropertyItem> queryList(Map<String, Object> params) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ConfigPropertyItem> criteriaQuery = criteriaBuilder
                .createQuery(ConfigPropertyItem.class);
        criteriaQuery.select(criteriaQuery.from(ConfigPropertyItem.class));
        
        
        return null;
    }
}
