package com.tx.component.role.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.tx.component.role.context.RoleRegistry;

/**
 * 权限项引用<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-3-21]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RoleRef extends Serializable {
    
    /**
     * 权限引用项唯一键<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getId();
    
    /**
     * 角色引用项的类型<br/>
     * 利用该类型<br/>
     * 实现         操作人员角色   OPERATOR          <br/>
     *       客户角色          CLIENT            <br/>
     *         ...
     * 这里用String虽没有int查询快，但能让sql可读性增强
     * @return 返回 authRefType
     */
    String getRefType();
    
    /**
     * 权限关联项id 
     * 可以是角色的id,
     * 可以是职位的id
     * ....
     * @return 返回 refId
     */
    String getRefId();
    
    /**
     * 创建人员id<br/>
     * 
     * @return 返回 createOperId
     */
    String getCreateOperatorId();
    
    /**
     * 返回权限引用项生效时间
     * when isTemp() == true 有效
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return Date [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    Date getEffectiveDate();
    
    /**
    * 返回权限引用项生效时间
    * when isTemp() == true 有效
    *<功能详细描述>
    * @return [参数说明]
    * 
    * @return Date [返回类型说明]
    * @exception throws [异常类型] [异常说明]
    * @see [类、类#方法、类#成员]
    */
    Date getExpiryDate();
    
    /**
     * 获取权限id<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getRoleId();
    
    /**
     * 获取权限id<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default Role getRole() {
        String roleId = getRoleId();
        if(StringUtils.isEmpty(roleId)){
            return null;
        }
        Role role = RoleRegistry.getInstance().findById(roleId);
        return role;
    }
}