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

import com.tx.component.rule.dao.SimpleRulePropertyByteDao;
import com.tx.component.rule.model.SimpleRulePropertyByte;
import com.tx.core.mybatis.model.Order;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * SimpleRulePropertyByte持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("simpleRulePropertyByteDao")
public class SimpleRulePropertyByteDaoImpl implements SimpleRulePropertyByteDao {
    
    @Resource(name = "myBatisDaoSupport")
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /**
     * @param condition
     */
    @Override
    public void insertSimpleRulePropertyByte(SimpleRulePropertyByte condition) {
        this.myBatisDaoSupport.insertUseUUID("simpleRulePropertyByte.insertSimpleRulePropertyByte", condition, "id");
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int deleteSimpleRulePropertyByte(SimpleRulePropertyByte condition) {
        return this.myBatisDaoSupport.delete("simpleRulePropertyByte.deleteSimpleRulePropertyByte", condition);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public SimpleRulePropertyByte findSimpleRulePropertyByte(SimpleRulePropertyByte condition) {
        return this.myBatisDaoSupport.<SimpleRulePropertyByte> find("simpleRulePropertyByte.findSimpleRulePropertyByte", condition);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<SimpleRulePropertyByte> querySimpleRulePropertyByteList(Map<String, Object> params) {
        return this.myBatisDaoSupport.<SimpleRulePropertyByte> queryList("simpleRulePropertyByte.querySimpleRulePropertyByte",
                params);
    }
    
    /**
     * @param params
     * @param orderList
     * @return
     */
    @Override
    public List<SimpleRulePropertyByte> querySimpleRulePropertyByteList(Map<String, Object> params,
            List<Order> orderList) {
        return this.myBatisDaoSupport.<SimpleRulePropertyByte> queryList("simpleRulePropertyByte.querySimpleRulePropertyByte",
                params,
                orderList);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int countSimpleRulePropertyByte(Map<String, Object> params) {
        return this.myBatisDaoSupport.<Integer> find("simpleRulePropertyByte.querySimpleRulePropertyByteCount",
                params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<SimpleRulePropertyByte> querySimpleRulePropertyBytePagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<SimpleRulePropertyByte> queryPagedList("simpleRulePropertyByte.querySimpleRulePropertyByte",
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
    public PagedList<SimpleRulePropertyByte> querySimpleRulePropertyBytePagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList) {
        return this.myBatisDaoSupport.<SimpleRulePropertyByte> queryPagedList("simpleRulePropertyByte.querySimpleRulePropertyByte",
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
    public int updateSimpleRulePropertyByte(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport.update("simpleRulePropertyByte.updateSimpleRulePropertyByte", updateRowMap);
    }
}
