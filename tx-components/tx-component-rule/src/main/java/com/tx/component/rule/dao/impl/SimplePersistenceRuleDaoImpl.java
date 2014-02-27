/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.rule.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tx.component.rule.dao.SimplePersistenceRuleDao;
import com.tx.component.rule.model.SimplePersistenceRule;
import com.tx.core.mybatis.model.Order;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * SimplePersistenceRule持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("simplePersistenceRuleDao")
public class SimplePersistenceRuleDaoImpl implements SimplePersistenceRuleDao {
    
    @Resource(name = "myBatisDaoSupport")
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /**
     * @param condition
     */
    @Override
    public void insertSimplePersistenceRule(SimplePersistenceRule condition) {
        this.myBatisDaoSupport.insertUseUUID("simplePersistenceRule.insertSimplePersistenceRule",
                condition,
                "id");
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int deleteSimplePersistenceRule(SimplePersistenceRule condition) {
        return this.myBatisDaoSupport.delete("simplePersistenceRule.deleteSimplePersistenceRule",
                condition);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public SimplePersistenceRule findSimplePersistenceRule(
            SimplePersistenceRule condition) {
        return this.myBatisDaoSupport.<SimplePersistenceRule> find("simplePersistenceRule.findSimplePersistenceRule",
                condition);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<SimplePersistenceRule> querySimplePersistenceRuleList(
            Map<String, Object> params) {
        return this.myBatisDaoSupport.<SimplePersistenceRule> queryList("simplePersistenceRule.querySimplePersistenceRule",
                params);
    }
    
    /**
     * @param params
     * @param orderList
     * @return
     */
    @Override
    public List<SimplePersistenceRule> querySimplePersistenceRuleList(
            Map<String, Object> params, List<Order> orderList) {
        return this.myBatisDaoSupport.<SimplePersistenceRule> queryList("simplePersistenceRule.querySimplePersistenceRule",
                params,
                orderList);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int countSimplePersistenceRule(Map<String, Object> params) {
        return this.myBatisDaoSupport.<Integer> find("simplePersistenceRule.querySimplePersistenceRuleCount",
                params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<SimplePersistenceRule> querySimplePersistenceRulePagedList(
            Map<String, Object> params, int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<SimplePersistenceRule> queryPagedList("simplePersistenceRule.querySimplePersistenceRule",
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
    public PagedList<SimplePersistenceRule> querySimplePersistenceRulePagedList(
            Map<String, Object> params, int pageIndex, int pageSize,
            List<Order> orderList) {
        return this.myBatisDaoSupport.<SimplePersistenceRule> queryPagedList("simplePersistenceRule.querySimplePersistenceRule",
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
    public int updateSimplePersistenceRule(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport.update("simplePersistenceRule.updateSimplePersistenceRule",
                updateRowMap);
    }
}
