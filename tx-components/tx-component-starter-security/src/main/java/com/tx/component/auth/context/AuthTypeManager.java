/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.auth.context;

import java.util.List;

import org.springframework.cache.Cache;

import com.tx.component.auth.model.AuthType;

/**
 * 权限类型业务层实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface AuthTypeManager {
    
    /**
     * 根据id查询权限类型<br/>
     * <功能详细描述>
     * @param authTypeId
     * @return [参数说明]
     * 
     * @return AuthType [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public AuthType findAuthTypeById(String authTypeId);
    
    /**
     * 查询权限类型<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<AuthType> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<AuthType> queryAuthTypeList();
    
    /**
     * 获取权限类型对应的缓存<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Cache [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default Cache getAuthTypeCache() {
        Cache cache = AuthTypeRegistry.getInstance().getCache();
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
    default void flushAuthTypeCache() {
        Cache cache = AuthTypeRegistry.getInstance().getCache();
        if (cache != null) {
            cache.clear();
        }
    }
}
