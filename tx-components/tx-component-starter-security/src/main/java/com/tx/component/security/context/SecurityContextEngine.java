/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月17日
 * <修改描述:>
 */
package com.tx.component.security.context;

import com.tx.component.auth.context.AuthRegistry;
import com.tx.component.auth.context.AuthTypeRegistry;
import com.tx.component.auth.service.AuthItemService;
import com.tx.component.auth.service.AuthRefService;
import com.tx.component.auth.service.AuthTypeItemService;
import com.tx.component.role.context.RoleRegistry;
import com.tx.component.role.context.RoleTypeRegistry;
import com.tx.component.role.service.RoleItemService;
import com.tx.component.role.service.RoleRefService;
import com.tx.component.role.service.RoleTypeItemService;

/**
 * 权控容器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface SecurityContextEngine {
    
    /**
     * 获取角色引用业务层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleRefService [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public RoleRefService getRoleRefService();
    
    /**
     * 获取角色类型注册中心实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleTypeRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public RoleTypeRegistry getRoleTypeRegistry();
    
    /**
     * 获取角色注册中心<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public RoleRegistry getRoleRegistry();
    
    /**
     * 获取角色类型项业务层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleTypeItemService [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public RoleTypeItemService getRoleTypeItemService();
    
    /**
     * 获取角色项业务层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleItemService [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public RoleItemService getRoleItemService();
    
    /**
     * 获取权限引用业务层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return AuthRefService [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public AuthRefService getAuthRefService();
    
    /**
     * 获取权限类型注册中心实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return AuthTypeRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public AuthTypeRegistry getAuthTypeRegistry();
    
    /**
     * 获取权限注册中心<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return AuthRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public AuthRegistry getAuthRegistry();
    
    /**
     * 获取权限类型项业务层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleTypeItemService [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public AuthTypeItemService getAuthTypeItemService();
    
    /**
     * 获取权限项业务层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleItemService [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public AuthItemService getAuthItemService();
}
