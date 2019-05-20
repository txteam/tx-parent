/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月17日
 * <修改描述:>
 */
package com.tx.component.security.context;

import com.tx.component.security.auth.service.AuthRefService;
import com.tx.component.security.role.context.RoleRegistry;
import com.tx.component.security.role.context.RoleTypeRegistry;
import com.tx.component.security.role.service.RoleRefService;

/**
 * 权控容器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface SecurityContext {
    
    public RoleRefService getRoleRefService();
    
    public AuthRefService getAuthRefService();
    
    public RoleTypeRegistry getRoleTypeRegistry();
    
    public RoleRegistry getRoleRegistry();
}
