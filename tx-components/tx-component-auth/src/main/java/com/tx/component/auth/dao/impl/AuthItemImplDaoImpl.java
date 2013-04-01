/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.auth.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tx.component.auth.dao.AuthItemImplDao;
import com.tx.component.auth.model.AuthItemImpl;
import com.tx.core.mybatis.model.Order;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * AuthItemImpl持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("authItemImplDao")
public class AuthItemImplDaoImpl implements AuthItemImplDao {
    
    @Resource(name = "myBatisDaoSupport")
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /**
     * @param condition
     */
    @Override
    public void insertAuthItemImpl(AuthItemImpl condition) {
        this.myBatisDaoSupport.insertUseUUID("authItemImpl.insertAuthItemImpl", condition, "id");
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int deleteAuthItemImpl(AuthItemImpl condition) {
        return this.myBatisDaoSupport.delete("authItemImpl.deleteAuthItemImpl", condition);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public AuthItemImpl findAuthItemImpl(AuthItemImpl condition) {
        return this.myBatisDaoSupport.<AuthItemImpl> find("authItemImpl.findAuthItemImpl", condition);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<AuthItemImpl> queryAuthItemImplList(Map<String, Object> params) {
        return this.myBatisDaoSupport.<AuthItemImpl> queryList("authItemImpl.queryAuthItemImpl",
                params);
    }
    
    /**
     * @param params
     * @param orderList
     * @return
     */
    @Override
    public List<AuthItemImpl> queryAuthItemImplList(Map<String, Object> params,
            List<Order> orderList) {
        return this.myBatisDaoSupport.<AuthItemImpl> queryList("authItemImpl.queryAuthItemImpl",
                params,
                orderList);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int countAuthItemImpl(Map<String, Object> params) {
        return this.myBatisDaoSupport.<Integer> find("authItemImpl.queryAuthItemImplCount",
                params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<AuthItemImpl> queryAuthItemImplPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<AuthItemImpl> queryPagedList("authItemImpl.queryAuthItemImpl",
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
    public PagedList<AuthItemImpl> queryAuthItemImplPagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList) {
        return this.myBatisDaoSupport.<AuthItemImpl> queryPagedList("authItemImpl.queryAuthItemImpl",
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
    public int updateAuthItemImpl(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport.update("authItemImpl.updateAuthItemImpl", updateRowMap);
    }
}
