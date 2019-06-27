/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月9日
 * <修改描述:>
 */
package com.tx.component.role.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.tx.component.role.context.RoleTypeRegistry;

/**
 * 角色接口实现<br/>
 *    系统中无论客户角色，还是操作员角色都可定义为该角色接口定义的实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface Role extends Serializable {
    
    /**
     * 获取角色唯一键<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getId();
    
    /**
     * 获取角色名称<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getName();
    
    /**
     * 获取角色备注<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default String getRemark() {
        return "";
    }
    
    /**
     * 获取角色类型id<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getRoleTypeId();
    
    /**
     * 获取父级角色，用于角色集成体系<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default String getParentId() {
        return null;
    }
    
    /**
     * 获取父类角色类型<br/>
     * 角色的parent主要应用于角色继承的业务逻辑中<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleType [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default RoleType getParent() {
        String parentId = getParentId();
        if (StringUtils.isEmpty(parentId)) {
            return null;
        }
        RoleType parent = RoleTypeRegistry.getInstance().findById(parentId);
        return parent;
    }
    
    /**
     * 获取角色类型<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleType [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default RoleType getRoleType() {
        String roleTypeId = getRoleTypeId();
        if (StringUtils.isEmpty(roleTypeId)) {
            return null;
        }
        RoleType roleType = RoleTypeRegistry.getInstance().findById(roleTypeId);
        return roleType;
    }
}
