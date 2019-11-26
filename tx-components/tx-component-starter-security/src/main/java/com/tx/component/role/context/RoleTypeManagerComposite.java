/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.role.context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.Cache;

import com.tx.component.role.model.RoleType;
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
public class RoleTypeManagerComposite {
    
    private List<RoleTypeManager> delegates;
    
    /** <默认构造函数> */
    public RoleTypeManagerComposite(List<RoleTypeManager> roleTypeManagers,
            Cache cache) {
        super();
        this.delegates = new ArrayList<>();
        AssertUtils.notNull(cache, "cache is null.");
        
        if (CollectionUtils.isEmpty(roleTypeManagers)) {
            roleTypeManagers.stream().forEach(rmTemp -> {
                if (rmTemp instanceof CachingRoleTypeManager) {
                    this.delegates.add((CachingRoleTypeManager) rmTemp);
                } else {
                    this.delegates
                            .add(new CachingRoleTypeManager(rmTemp, cache));
                }
            });
        }
    }
    
    /**
     * @param roleTypeId
     * @return
     */
    public RoleType findById(String roleTypeId) {
        AssertUtils.notEmpty(roleTypeId, "roleTypeId is empty.");
        
        RoleType roleTypeTemp = null;
        for (RoleTypeManager rm : delegates) {
            roleTypeTemp = rm.findRoleTypeById(roleTypeId);
            if (roleTypeTemp != null) {
                return roleTypeTemp;
            }
        }
        return roleTypeTemp;
    }
    
    /**
     * 查询角色类型列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<AuthType> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<RoleType> queryList() {
        List<RoleType> resList = new ArrayList<>();
        
        Set<String> roleTypeIdSet = new HashSet<>();
        for (RoleTypeManager rm : delegates) {
            List<RoleType> tempList = rm.queryRoleTypeList();
            if (CollectionUtils.isEmpty(tempList)) {
                continue;
            }
            //过滤重复权限类型
            tempList.stream().forEach(atTemp -> {
                if (!roleTypeIdSet.contains(atTemp.getId())) {
                    resList.add(atTemp);
                    roleTypeIdSet.add(atTemp.getId());
                }
            });
        }
        return resList;
    }
    
}
