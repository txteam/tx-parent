/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月16日
 * <修改描述:>
 */
package com.tx.component.security.role.customize;

import java.util.List;
import java.util.Map;

import com.tx.component.security.role.model.RoleType;

/**
 * 角色类型业务层实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RoleTypeManager {
    
    /**
     * 根据id查询角色类型<br/>
     * <功能详细描述>
     * @param roleTypeId
     * @return [参数说明]
     * 
     * @return RoleType [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public RoleType findById(String roleTypeId);
    
    /**
     * 根据条件查询角色类型列表<br/>
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return List<RoleType> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<RoleType> queryList(Map<String, Object> params);
    
    /**
     * 根据条件查询角色类型映射<br/>
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return Map<String,RoleType> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Map<String, RoleType> queryMap(Map<String, Object> params);
}