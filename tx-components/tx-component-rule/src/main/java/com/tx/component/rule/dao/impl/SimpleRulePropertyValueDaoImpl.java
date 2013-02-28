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

import com.tx.component.rule.dao.SimpleRulePropertyValueDao;
import com.tx.component.rule.model.SimpleRulePropertyValue;
import com.tx.core.mybatis.model.Order;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * SimpleRulePropertyValue持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("simpleRulePropertyValueDao")
public class SimpleRulePropertyValueDaoImpl implements SimpleRulePropertyValueDao {
    
    @Resource(name = "myBatisDaoSupport")
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /**
     * @param condition
     */
    @Override
    public void insertSimpleRulePropertyValue(SimpleRulePropertyValue condition) {
        this.myBatisDaoSupport.insertUseUUID("simpleRulePropertyValue.insertSimpleRulePropertyValue", condition, "id");
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int deleteSimpleRulePropertyValue(SimpleRulePropertyValue condition) {
        return this.myBatisDaoSupport.delete("simpleRulePropertyValue.deleteSimpleRulePropertyValue", condition);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public SimpleRulePropertyValue findSimpleRulePropertyValue(SimpleRulePropertyValue condition) {
        return this.myBatisDaoSupport.<SimpleRulePropertyValue> find("simpleRulePropertyValue.findSimpleRulePropertyValue", condition);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<SimpleRulePropertyValue> querySimpleRulePropertyValueList(Map<String, Object> params) {
        return this.myBatisDaoSupport.<SimpleRulePropertyValue> queryList("simpleRulePropertyValue.querySimpleRulePropertyValue",
                params);
    }
    
    /**
     * @param params
     * @param orderList
     * @return
     */
    @Override
    public List<SimpleRulePropertyValue> querySimpleRulePropertyValueList(Map<String, Object> params,
            List<Order> orderList) {
        return this.myBatisDaoSupport.<SimpleRulePropertyValue> queryList("simpleRulePropertyValue.querySimpleRulePropertyValue",
                params,
                orderList);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int countSimpleRulePropertyValue(Map<String, Object> params) {
        return this.myBatisDaoSupport.<Integer> find("simpleRulePropertyValue.querySimpleRulePropertyValueCount",
                params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<SimpleRulePropertyValue> querySimpleRulePropertyValuePagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<SimpleRulePropertyValue> queryPagedList("simpleRulePropertyValue.querySimpleRulePropertyValue",
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
    public PagedList<SimpleRulePropertyValue> querySimpleRulePropertyValuePagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList) {
        return this.myBatisDaoSupport.<SimpleRulePropertyValue> queryPagedList("simpleRulePropertyValue.querySimpleRulePropertyValue",
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
    public int updateSimpleRulePropertyValue(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport.update("simpleRulePropertyValue.updateSimpleRulePropertyValue", updateRowMap);
    }
}
