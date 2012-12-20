/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-2
 * <修改描述:>
 */
package com.tx.component.auth.dao.impl;

import java.util.List;
import java.util.Map;

import com.tx.component.auth.dao.AuthDao;
import com.tx.component.auth.model.AuthItemRef;
import com.tx.core.mybatis.support.MyBatisDaoSupport;

/**
 * 权限持久层实现 <功能详细描述>
 * 
 * @author PengQingyang
 * @version [版本号, 2012-12-2]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AuthDaoImpl implements AuthDao {

	private MyBatisDaoSupport myBatisDaoSupport;

	/**
	 * @param userId
	 * @return
	 */
	@Override
	public List<AuthItemRef> queryItemAuthRefListByOperId(String operatorId) {
		return this.myBatisDaoSupport.<AuthItemRef>queryList(
				"queryItemAuthRefListByOperId", operatorId);
	}

	/**
	 * @param operId
	 * @return
	 */
	@Override
	public List<AuthItemRef> queryItemAuthRefList(Map<String, Object> params) {
		return this.myBatisDaoSupport.<AuthItemRef>queryList(
				"queryItemAuthRefList", params);
	}

	/**
	 * @param authItemRef
	 */
	@Override
	public void addAuthItemRefList(List<AuthItemRef> authItemRefList) {
		this.myBatisDaoSupport.batchInsert("addAuthItemRef", authItemRefList, true);
	}

	/**
	 * @param authItemRef
	 */
	@Override
	public void delAuthItemRefList(List<AuthItemRef> authItemRefList) {
		this.myBatisDaoSupport.batchDelete("delAuthItemRef", authItemRefList, true);
	}

    /**
     * @return 返回 myBatisDaoSupport
     */
    public MyBatisDaoSupport getMyBatisDaoSupport() {
        return myBatisDaoSupport;
    }

    /**
     * @param 对myBatisDaoSupport进行赋值
     */
    public void setMyBatisDaoSupport(MyBatisDaoSupport myBatisDaoSupport) {
        this.myBatisDaoSupport = myBatisDaoSupport;
    }
}
