/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.security.role.context;

import org.springframework.cache.Cache;

import com.tx.component.security.role.model.RoleType;

/**
 * 角色注册表<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RoleTypeRegistry {
    
    private static RoleTypeRegistry instance = null;
    
    private Cache roleCache;
    
    public static RoleTypeRegistry getInstance(){
        return null;
    }
    
    public RoleType findById(String id) {
        return null;
    }
}
