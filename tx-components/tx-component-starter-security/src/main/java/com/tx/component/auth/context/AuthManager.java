/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.auth.context;

import java.util.List;

import org.springframework.cache.Cache;

import com.tx.component.auth.model.Auth;

/**
 * 权限类型业务层实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface AuthManager {
    
    /**
     * 根据id查询角色<br/>
     * <功能详细描述>
     * @param authId
     * @return [参数说明]
     * 
     * @return Auth [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    Auth findAuthById(String authId);
    
    /**
     * 根据条件查询角色列表<br/>
     * <功能详细描述>
     * @param authTypeId
     * @return [参数说明]
     * 
     * @return List<Auth> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    List<Auth> queryAuthList(String authTypeId);
    
    /**
     * 根父节点查询子级角色列表<br/>
     * <功能详细描述>
     * @param parentId
     * @param authTypeId
     * @return [参数说明]
     * 
     * @return List<Auth> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    List<Auth> queryChildrenAuthByParentId(String parentId, String authTypeId);
    
    /**
     * 嵌套查询子级角色列表<br/>
     * <功能详细描述>
     * @param parentId
     * @param authTypeId
     * @return [参数说明]
     * 
     * @return List<Auth> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    List<Auth> queryDescendantsAuthByParentId(String parentId, String authTypeId);
    
    /**
     * 获取权限类型对应的缓存<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Cache [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default Cache getAuthCache() {
        Cache cache = AuthRegistry.getInstance().getCache();
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
    default void flushAuthCache() {
        Cache cache = AuthRegistry.getInstance().getCache();
        if (cache != null) {
            cache.clear();
        }
    }
}
