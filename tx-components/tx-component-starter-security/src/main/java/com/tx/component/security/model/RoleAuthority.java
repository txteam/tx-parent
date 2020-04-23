/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年11月18日
 * <修改描述:>
 */
package com.tx.component.security.model;

import org.springframework.security.core.GrantedAuthority;

import com.tx.component.role.model.Role;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 操作人员角色权限<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年11月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RoleAuthority extends GrantedAuthority {
    
    /**
     * 角色如果没有前缀ROLE_则自动添加<br/>
     * @return
     */
    @Override
    default String getAuthority() {
        AssertUtils.notNull(getRole(), "role is null.");
        AssertUtils.notEmpty(getRole().getId(), "role.id is empty.");
        
        String authority = getRole().getId();
        if (!authority.startsWith("ROLE_")) {
            authority = "ROLE_" + authority;
        }
        return authority;
    }
    
    /**
     * 获取对应的角色<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Role [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Role getRole();
}
