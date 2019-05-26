/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月10日
 * <修改描述:>
 */
package com.tx.component.security.auth.service;

import java.util.Map;

import com.tx.component.auth.model.Auth;

/**
 * 权限管理接口定义<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface AuthManager {
    
    /**
     * 加载系统的所有权限项<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Set<AuthItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Map<String, Auth> load(Map<String, Object> params);
}
