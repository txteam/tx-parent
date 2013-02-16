/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.workflow.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tx.component.workflow.dao.ProTaskDefinitionDao;
import com.tx.component.workflow.model.ProTaskDefinition;
import com.tx.core.mybatis.model.Order;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * ProTaskDefinition持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("proTaskDefinitionDao")
public class ProTaskDefinitionDaoImpl implements ProTaskDefinitionDao {
    
    @Resource(name = "myBatisDaoSupport")
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /**
     * @param condition
     */
    @Override
    public void insertProTaskDefinition(ProTaskDefinition condition) {
        this.myBatisDaoSupport.insertUseUUID("proTaskDefinition.insertProTaskDefinition", condition, "id");
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int deleteProTaskDefinition(ProTaskDefinition condition) {
        return this.myBatisDaoSupport.delete("proTaskDefinition.deleteProTaskDefinition", condition);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public ProTaskDefinition findProTaskDefinition(ProTaskDefinition condition) {
        return this.myBatisDaoSupport.<ProTaskDefinition> find("proTaskDefinition.findProTaskDefinition", condition);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<ProTaskDefinition> queryProTaskDefinitionList(Map<String, Object> params) {
        return this.myBatisDaoSupport.<ProTaskDefinition> queryList("proTaskDefinition.queryProTaskDefinition",
                params);
    }
    
    /**
     * @param params
     * @param orderList
     * @return
     */
    @Override
    public List<ProTaskDefinition> queryProTaskDefinitionList(Map<String, Object> params,
            List<Order> orderList) {
        return this.myBatisDaoSupport.<ProTaskDefinition> queryList("proTaskDefinition.queryProTaskDefinition",
                params,
                orderList);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int countProTaskDefinition(Map<String, Object> params) {
        return this.myBatisDaoSupport.<Integer> find("proTaskDefinition.queryProTaskDefinitionCount",
                params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<ProTaskDefinition> queryProTaskDefinitionPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<ProTaskDefinition> queryPagedList("proTaskDefinition.queryProTaskDefinition",
                params,
                pageIndex,
                pageSize);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @param orderList
     * @return
     */
    @Override
    public PagedList<ProTaskDefinition> queryProTaskDefinitionPagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList) {
        return this.myBatisDaoSupport.<ProTaskDefinition> queryPagedList("proTaskDefinition.queryProTaskDefinition",
                params,
                pageIndex,
                pageSize,
                orderList);
    }
    
    /**
     * @param updateRowMap
     * @return
     */
    @Override
    public int updateProTaskDefinition(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport.update("proTaskDefinition.updateProTaskDefinition", updateRowMap);
    }
}
