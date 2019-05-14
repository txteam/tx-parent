/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月14日
 * <修改描述:>
 */
package com.tx.component.security.role.service;

import java.util.List;
import java.util.Map;

import com.tx.component.security.role.model.Role;

/**
 * 权限项管理器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RoleManager {
    
    public List<Role> queryList(Map<String, Object> params);
    
    
}
