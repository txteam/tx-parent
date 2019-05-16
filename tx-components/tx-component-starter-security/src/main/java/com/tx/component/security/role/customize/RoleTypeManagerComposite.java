/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.security.role.customize;

import java.util.List;
import java.util.Map;

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
public class RoleTypeManagerComposite implements RoleTypeManager {
    
    private List<RoleTypeManager> roleTypeManagers;
    
    /** <默认构造函数> */
    public RoleTypeManagerComposite(List<RoleTypeManager> roleTypeManagers) {
        super();
        this.roleTypeManagers = roleTypeManagers;
    }
    
    /**
     * @param roleTypeId
     * @return
     */
    @Override
    public RoleType findById(String roleTypeId) {
        AssertUtils.notEmpty(roleTypeId, "roleTypeId is empty.");
        return null;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<RoleType> queryList(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public Map<String, RoleType> queryMap(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
