/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年11月18日
 * <修改描述:>
 */
package com.tx.component.security.model;

import org.springframework.security.core.GrantedAuthority;

import com.tx.component.auth.model.Auth;
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
public interface AuthAuthority extends GrantedAuthority {
    
    /**
     * @return
     */
    @Override
    default String getAuthority() {
        AssertUtils.notNull(getAuth(), "auth is null.");
        AssertUtils.notEmpty(getAuth().getId(), "auth.id is empty.");
        
        String authority = getAuth().getId();
        return authority;
    }
    
    /**
     * 获取对应的权限<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Auth [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Auth getAuth();
}
