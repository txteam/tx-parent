/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.security.role.context;

import java.util.List;
import java.util.Map;

import com.tx.component.security.role.model.Role;
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
public class RoleManagerComposite{
    
    private List<RoleManager> roleManagers;
    
    /** <默认构造函数> */
    public RoleManagerComposite(List<RoleManager> roleManagers) {
        super();
        this.roleManagers = roleManagers;
    }
    
    /**
     * @param roleTypeId
     * @return
     */
    public Role findById(String roleId) {
        AssertUtils.notEmpty(roleId, "roleId is empty.");
        
        Role roleTemp = null;
        for (RoleManager rm : roleManagers) {
            roleTemp = rm.findById(roleId);
            if (roleTemp != null) {
                return roleTemp;
            }
        }
        return roleTemp;
    }
    
    /**
     * @param params
     * @return
     */
    public List<Role> queryList(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
