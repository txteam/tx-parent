/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月17日
 * <修改描述:>
 */
package com.tx.component.security.role.context.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.cache.Cache;

import com.tx.component.security.role.context.RoleTypeManager;
import com.tx.component.security.role.context.RoleTypeRegistry;
import com.tx.component.security.role.model.RoleType;

/**
 * 支持缓存的角色类型管理器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface CachingRoleTypeManager extends RoleTypeManager {
    
    /**
     * @param roleTypeId
     * @return
     */
    @Override
    public RoleType findById(String roleTypeId) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<RoleType> queryList(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return null;
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
    default void flushCache() {
        Cache cache = getCache();
        if (cache != null) {
            cache.clear();
        }
    }
    
    /**
     * 获取对应的缓存对象<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Cache [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default Cache getCache() {
        Cache cache = RoleTypeRegistry.getInstance().getCache();
        return cache;
    }
}
