/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月7日
 * <修改描述:>
 */
package com.tx.component.security.auth.registry;

import com.tx.component.security.auth.model.Auth;

/**
 * 权限注册表<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthRegistry {
    
    public static final AuthRegistry instance = new AuthRegistry();
    
    public static AuthRegistry getInstance(){
        return null;
    }
    
    
    /** 根据权限ID获取对应的权限项 */
    public static Auth getAuth(String authId) {
        return null;
    }
}
