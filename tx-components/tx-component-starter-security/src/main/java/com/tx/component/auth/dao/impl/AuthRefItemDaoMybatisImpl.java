/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.auth.dao.impl;

import java.util.List;
import java.util.Map;

import com.tx.component.auth.dao.AuthRefItemDao;
import com.tx.component.auth.model.AuthRefItem;
import com.tx.core.mybatis.dao.impl.MybatisBaseDaoImpl;
import com.tx.core.mybatis.support.MyBatisDaoSupport;

/**
 * AuthRefItem持久层
 * <功能详细描述>
 * 
 * @author  
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AuthRefItemDaoMybatisImpl extends
        MybatisBaseDaoImpl<AuthRefItem, String> implements AuthRefItemDao {
    
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /** <默认构造函数> */
    public AuthRefItemDaoMybatisImpl() {
        super();
    }
    
    /** <默认构造函数> */
    public AuthRefItemDaoMybatisImpl(MyBatisDaoSupport myBatisDaoSupport) {
        super();
        this.myBatisDaoSupport = myBatisDaoSupport;
    }
    
    /**
     * @param authRef
     */
    @Override
    public void insertToHis(AuthRefItem authRef) {
        this.myBatisDaoSupport.insert("authRefItem.insertToHis", authRef);
    }
    
    /**
     * @param authRefs
     */
    @Override
    public void batchInsertToHis(List<AuthRefItem> authRefs) {
        this.myBatisDaoSupport.batchInsert("authRefItem.insertToHis",
                authRefs,
                false);
    }
    
    /**
     * @param entityList
     */
    @Override
    public void batchInsert(List<AuthRefItem> entityList) {
        this.myBatisDaoSupport.batchInsertUseUUID("authRefItem.insert",
                entityList,
                "id",
                false);
    }
    
    /**
     * @param entityList
     */
    @Override
    public void batchDelete(List<AuthRefItem> entityList) {
        this.myBatisDaoSupport.batchDelete("authRefItem.delete",
                entityList,
                false);
    }
    
    /**
     * @param updateEntityMapList
     */
    @Override
    public void batchUpdate(List<Map<String, Object>> updateEntityMapList) {
        this.myBatisDaoSupport.batchUpdate("authRefItem.update",
                updateEntityMapList,
                false);
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
