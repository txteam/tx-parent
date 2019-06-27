/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.role.context;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.Cache;

import com.tx.component.role.model.Role;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 角色类型业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RoleManagerComposite {
    
    /** 角色管理器实现 */
    private List<CachingRoleManager> delegates;
    
    /** <默认构造函数> */
    public RoleManagerComposite(List<RoleManager> roleManagers, Cache cache) {
        super();
        this.delegates = new ArrayList<>();
        AssertUtils.notNull(cache, "cache is null.");
        
        if (CollectionUtils.isEmpty(roleManagers)) {
            roleManagers.stream().forEach(rmTemp -> {
                if (rmTemp instanceof CachingRoleManager) {
                    this.delegates.add((CachingRoleManager) rmTemp);
                } else {
                    this.delegates.add(new CachingRoleManager(rmTemp, cache));
                }
            });
        }
    }
    
    /**
     * @param roleTypeId
     * @return
     */
    public Role findById(String roleId) {
        AssertUtils.notEmpty(roleId, "roleId is empty.");
        
        Role roleTemp = null;
        for (RoleManager rm : delegates) {
            roleTemp = rm.findRoleById(roleId);
            if (roleTemp != null) {
                return roleTemp;
            }
        }
        return roleTemp;
    }
    
    /**
     * @param roleTypeId
     * @return
     */
    public List<Role> queryList(String... roleTypeIds) {
        List<Role> resList = new ArrayList<>();
        for (RoleManager rm : delegates) {
            List<Role> tempList = rm.queryRoleList(roleTypeIds);
            if (!CollectionUtils.isEmpty(tempList)) {
                resList.addAll(tempList);
            }
        }
        return resList;
    }
    
    /**
     * @param roleTypeId
     * @return
     */
    public List<Role> queryChildrenByParentId(String parentId,
            String... roleTypeIds) {
        List<Role> resList = new ArrayList<>();
        for (RoleManager rm : delegates) {
            List<Role> tempList = rm.queryChildrenRoleByParentId(parentId,
                    roleTypeIds);
            if (!CollectionUtils.isEmpty(tempList)) {
                resList.addAll(tempList);
            }
        }
        return resList;
    }
    
    /**
     * @param roleTypeId
     * @return
     */
    public List<Role> queryDescendantsByParentId(String parentId,
            String... roleTypeIds) {
        List<Role> resList = new ArrayList<>();
        for (RoleManager rm : delegates) {
            List<Role> tempList = rm.queryDescendantsRoleByParentId(parentId,
                    roleTypeIds);
            if (!CollectionUtils.isEmpty(tempList)) {
                resList.addAll(tempList);
            }
        }
        return resList;
    }
    
}
