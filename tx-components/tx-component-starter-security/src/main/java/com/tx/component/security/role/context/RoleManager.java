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

/**
 * 角色类型业务层实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RoleManager {
    
    /**
     * 根据id查询角色<br/>
     * <功能详细描述>
     * @param roleId
     * @return [参数说明]
     * 
     * @return Role [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Role findById(String roleId);
    
    /**
     * 根据条件查询角色列表<br/>
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return List<Role> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<Role> queryList(Map<String, Object> params);
}
