/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.role.context;

import java.util.List;

import org.springframework.cache.Cache;

import com.tx.component.role.model.Role;
import com.tx.core.querier.model.Querier;

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
    public Role findRoleById(String roleId);
    
    /**
     * 根据条件查询角色列表<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return List<Role> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<Role> queryRoleList(Querier querier);
    
    /**
     * 根父节点查询子级角色列表<br/>
     * <功能详细描述>
     * @param module
     * @param parentId
     * @param params
     * @return [参数说明]
     * 
     * @return List<Role> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    List<Role> queryChildrenRoleByParentId(String parentId, Querier querier);
    
    /**
     * 嵌套查询子级角色列表<br/>
     * <功能详细描述>
     * @param module
     * @param parentId
     * @param params
     * @return [参数说明]
     * 
     * @return List<Role> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    List<Role> queryDescendantsRoleByParentId(String parentId, Querier querier);
    
    /**
     * 获取角色类型对应的缓存<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Cache [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default Cache getRoleCache() {
        Cache cache = RoleRegistry.getInstance().getCache();
        return cache;
    }
    
    /**
     * 刷新缓存<br/>
     * <功能详细描述>
     * @param ms [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default void flushRoleCache() {
        Cache cache = RoleRegistry.getInstance().getCache();
        if (cache != null) {
            cache.clear();
        }
    }
}
