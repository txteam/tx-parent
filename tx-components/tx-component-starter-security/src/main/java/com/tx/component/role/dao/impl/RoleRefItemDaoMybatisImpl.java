/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月20日
 * <修改描述:>
 */
package com.tx.component.role.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.tx.component.role.dao.RoleRefItemDao;
import com.tx.component.role.model.RoleRefItem;
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
public class RoleRefItemDaoMybatisImpl implements RoleRefItemDao {
    
    @Resource(name = "myBatisDaoSupport")
    private MyBatisDaoSupport myBatisDaoSupport;
    
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
                true);
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchInsert(List<RoleRefItem> condition) {
        this.myBatisDaoSupport.batchInsertUseUUID(
                "roleRefItem.insertRoleRefItem", condition, "id", true);
    }
    
    /**
     * @param condition
     */
    @Override
    public void insert(RoleRefItem condition) {
        this.myBatisDaoSupport.insertUseUUID("roleRefItem.insertRoleRefItem",
                condition,
                "id");
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int delete(RoleRefItem condition) {
        return this.myBatisDaoSupport.delete("roleRefItem.deleteRoleRefItem",
                condition);
    }
    
    /**
     * @param roleRefs
     */
    @Override
    public void batchDelete(List<RoleRefItem> roleRefs) {
        this.myBatisDaoSupport.batchDelete("roleRefItem.deleteRoleRefItem",
                roleRefs,
                true);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<RoleRefItem> queryList(Map<String, Object> params) {
        return this.myBatisDaoSupport.<RoleRefItem> queryList(
                "roleRefItem.queryRoleRefItem", params);
    }
    
    /**
     * @param updateRowMap
     * @return
     */
    @Override
    public int update(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport.update("roleRefItem.updateRoleRefItem",
                updateRowMap);
    }
}
