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

import com.tx.component.auth.dao.AuthItemRefImplDao;
import com.tx.component.auth.model.AuthItemRefImpl;
import com.tx.core.mybatis.model.Order;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * AuthItemRefImpl持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("authItemRefImplDao")
public class AuthItemRefImplDaoImpl implements AuthItemRefImplDao {
    
    @Resource(name = "myBatisDaoSupport")
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /**
     * @param condition
     */
    @Override
    public void insertAuthItemRefImpl(AuthItemRefImpl condition) {
        this.myBatisDaoSupport.insertUseUUID("authItemRefImpl.insertAuthItemRefImpl", condition, "id");
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int deleteAuthItemRefImpl(AuthItemRefImpl condition) {
        return this.myBatisDaoSupport.delete("authItemRefImpl.deleteAuthItemRefImpl", condition);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public AuthItemRefImpl findAuthItemRefImpl(AuthItemRefImpl condition) {
        return this.myBatisDaoSupport.<AuthItemRefImpl> find("authItemRefImpl.findAuthItemRefImpl", condition);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<AuthItemRefImpl> queryAuthItemRefImplList(Map<String, Object> params) {
        return this.myBatisDaoSupport.<AuthItemRefImpl> queryList("authItemRefImpl.queryAuthItemRefImpl",
                params);
    }
    
    /**
     * @param params
     * @param orderList
     * @return
     */
    @Override
    public List<AuthItemRefImpl> queryAuthItemRefImplList(Map<String, Object> params,
            List<Order> orderList) {
        return this.myBatisDaoSupport.<AuthItemRefImpl> queryList("authItemRefImpl.queryAuthItemRefImpl",
                params,
                orderList);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int countAuthItemRefImpl(Map<String, Object> params) {
        return this.myBatisDaoSupport.<Integer> find("authItemRefImpl.queryAuthItemRefImplCount",
                params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<AuthItemRefImpl> queryAuthItemRefImplPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<AuthItemRefImpl> queryPagedList("authItemRefImpl.queryAuthItemRefImpl",
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
    public PagedList<AuthItemRefImpl> queryAuthItemRefImplPagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList) {
        return this.myBatisDaoSupport.<AuthItemRefImpl> queryPagedList("authItemRefImpl.queryAuthItemRefImpl",
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
    public int updateAuthItemRefImpl(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport.update("authItemRefImpl.updateAuthItemRefImpl", updateRowMap);
    }
}
