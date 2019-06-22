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

import com.tx.component.role.model.RoleType;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.querier.model.Querier;

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
     * @param params
     * @return
     */
    public List<RoleType> queryList(Querier querier) {
        List<RoleType> resList = new ArrayList<>();
        for (RoleTypeManager rm : delegates) {
            List<RoleType> tempList = rm.queryRoleTypeList(querier);
            if (!CollectionUtils.isEmpty(tempList)) {
                resList.addAll(tempList);
            }
        }
        return resList;
    }
    
}
