/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月20日
 * <修改描述:>
 */
package com.tx.component.role.dao.impl;

import java.util.List;
import java.util.Map;

import com.tx.component.role.dao.RoleRefItemDao;
import com.tx.component.role.model.RoleRefItem;
import com.tx.core.mybatis.dao.impl.MybatisBaseDaoImpl;
import com.tx.core.mybatis.support.MyBatisDaoSupport;

/**
 * 角色应用项持久层实现层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RoleRefItemDaoMybatisImpl extends
        MybatisBaseDaoImpl<RoleRefItem, String> implements RoleRefItemDao {
    
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /** <默认构造函数> */
    public RoleRefItemDaoMybatisImpl() {
        super();
    }
    
    /** <默认构造函数> */
    public RoleRefItemDaoMybatisImpl(MyBatisDaoSupport myBatisDaoSupport) {
        super();
        this.myBatisDaoSupport = myBatisDaoSupport;
    }
    
    /**
     * @param roleRef
     */
    @Override
    public void insertToHis(RoleRefItem roleRef) {
        this.myBatisDaoSupport.insert("roleRefItem.insertToHis", roleRef);
    }
    
    /**
     * @param roleRefs
     */
    @Override
    public void batchInsertToHis(List<RoleRefItem> roleRefs) {
        this.myBatisDaoSupport.batchInsert("roleRefItem.insertToHis",
                roleRefs,
                false);
    }
    
    /**
     * @param entityList
     */
    @Override
    public void batchInsert(List<RoleRefItem> entityList) {
        this.myBatisDaoSupport.batchInsertUseUUID("roleRefItem.insert",
                entityList,
                "id",
                false);
    }
    
    /**
     * @param entityList
     */
    @Override
    public void batchDelete(List<RoleRefItem> entityList) {
        this.myBatisDaoSupport.batchInsert("roleRefItem.delete",
                entityList,
                false);
    }
    
    /**
     * @param updateEntityMapList
     */
    @Override
    public void batchUpdate(List<Map<String, Object>> updateEntityMapList) {
        this.myBatisDaoSupport.batchInsert("roleRefItem.update",
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
