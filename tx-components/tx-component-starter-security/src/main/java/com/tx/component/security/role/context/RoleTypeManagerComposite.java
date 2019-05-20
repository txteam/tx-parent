/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.security.role.context;

import java.util.List;
import java.util.Map;

import org.springframework.cache.Cache;

import com.tx.component.security.role.model.RoleType;
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
public class RoleTypeManagerComposite{
    
    private List<RoleTypeManager> roleTypeManagers;
    
    /** <默认构造函数> */
    public RoleTypeManagerComposite(List<RoleTypeManager> roleTypeManagers,
            Cache roleTypeCache) {
        super();
        this.roleTypeManagers = roleTypeManagers;
    }
    
    /**
     * @param roleTypeId
     * @return
     */
    public RoleType findById(String roleTypeId) {
        AssertUtils.notEmpty(roleTypeId, "roleTypeId is empty.");
        return null;
    }
    
    /**
     * @param params
     * @return
     */
    public List<RoleType> queryList(Map<String, Object> params) {
        return null;
    }
    
}
