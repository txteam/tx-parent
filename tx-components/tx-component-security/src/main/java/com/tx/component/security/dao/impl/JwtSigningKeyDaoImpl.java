/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.security.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tx.component.security.dao.JwtSigningKeyDao;
import com.tx.component.security.model.JwtSigningKey;
import com.tx.core.mybatis.model.Order;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * JwtSigningKey持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("jwtSigningKeyDao")
public class JwtSigningKeyDaoImpl implements JwtSigningKeyDao {
    
    @Resource(name = "myBatisDaoSupport")
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /**
     * @param condition
     */
    @Override
    public void batchInsert(List<JwtSigningKey> condition) {
        this.myBatisDaoSupport.batchInsertUseUUID(
                "jwtSigningKey.insertJwtSigningKey", condition, "id", true);
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchUpdate(List<Map<String, Object>> updateRowMapList) {
        this.myBatisDaoSupport.batchUpdate("jwtSigningKey.updateJwtSigningKey",
                updateRowMapList,
                true);
    }
    
    /**
     * @param condition
     */
    @Override
    public void insert(JwtSigningKey condition) {
        this.myBatisDaoSupport.insertUseUUID(
                "jwtSigningKey.insertJwtSigningKey", condition, "id");
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int delete(JwtSigningKey condition) {
        return this.myBatisDaoSupport
                .delete("jwtSigningKey.deleteJwtSigningKey", condition);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public JwtSigningKey find(JwtSigningKey condition) {
        return this.myBatisDaoSupport.<JwtSigningKey> find(
                "jwtSigningKey.findJwtSigningKey", condition);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<JwtSigningKey> queryList(Map<String, Object> params) {
        return this.myBatisDaoSupport.<JwtSigningKey> queryList(
                "jwtSigningKey.queryJwtSigningKey", params);
    }
    
    /**
     * @param params
     * @param orderList
     * @return
     */
    @Override
    public List<JwtSigningKey> queryList(Map<String, Object> params,
            List<Order> orderList) {
        return this.myBatisDaoSupport.<JwtSigningKey> queryList(
                "jwtSigningKey.queryJwtSigningKey", params, orderList);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int count(Map<String, Object> params) {
        return this.myBatisDaoSupport.<Integer> find(
                "jwtSigningKey.queryJwtSigningKeyCount", params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<JwtSigningKey> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<JwtSigningKey> queryPagedList(
                "jwtSigningKey.queryJwtSigningKey",
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
    public PagedList<JwtSigningKey> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList) {
        return this.myBatisDaoSupport.<JwtSigningKey> queryPagedList(
                "jwtSigningKey.queryJwtSigningKey",
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
    public int update(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport
                .update("jwtSigningKey.updateJwtSigningKey", updateRowMap);
    }
}
