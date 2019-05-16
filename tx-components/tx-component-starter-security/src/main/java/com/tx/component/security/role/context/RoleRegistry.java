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

import com.tx.component.security.role.model.Role;

/**
 * 角色注册表<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RoleRegistry {
    
    private static RoleRegistry instance = null;
    
    private Cache roleCache;
    
    public static RoleRegistry getInstance(){
        return null;
    }
    
    public Role findById(String id) {
        return null;
    }
    
    public List<Role> queryList(Map<String, Object> params) {
        return null;
    }
    
    public Map<String, Role> queryMap(Map<String, Object> params) {
        return null;
    }
}
