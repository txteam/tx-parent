package com.tx.component.auth.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tx.component.auth.context.AuthRegistry;
import com.tx.component.auth.context.AuthTypeRegistry;
import com.tx.core.support.json.JSONAttributesSupport;
import com.tx.core.tree.model.TreeAble;

/**
  * 权限项接口<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2012-12-14]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public interface Auth extends Serializable, JSONAttributesSupport,
        TreeAble<List<Auth>, Auth> {
    
    /**
     * 权限项唯一键key 
     * 约定权限项目分割符为"_"
     * 如权限为"wd_"<br/>
     * <功能详细描述>
     * 
     * @return 返回 id
     */
    String getId();
    
    /**
     * 父级权限id<br/>
     * <功能详细描述>
     * 
     * @return [参数说明]
     */
    String getParentId();
    
    /**
     * 获取权限类型<br/>
     * <功能详细描述>
     * 
     * @return [参数说明]
     */
    String getAuthTypeId();
    
    /**
     * 权限名<br/>
     * <功能详细描述>
     * 
     * @return [参数说明]
     */
    String getName();
    
    /**
     * 获取权限描述<br/>
     * <功能详细描述>
     * 
     * @return [参数说明]
     */
    String getRemark();
    
    /**
     * 是否能进行配置<br/>
     * <功能详细描述>
     * 
     * @return boolean 
     */
    boolean isConfigAble();
    
    /**
     * 权限项目引用类型  <br/>
     * <功能详细描述>
     * 
     * @return [参数说明]
     */
    default String getResourceType() {
        return "";
    }
    
    /**
     * 获取权限关联项id<br/>
     * <功能详细描述>
     * 
     * @return String [返回类型说明]
     */
    default String getResourceId() {
        return "";
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
    @JsonIgnore
    default Auth getParent() {
        String parentId = getParentId();
        if (StringUtils.isEmpty(parentId)) {
            return null;
        }
        Auth parent = AuthRegistry.getInstance().findById(parentId);
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
    @JsonIgnore
    default AuthType getAuthType() {
        String authTypeId = getAuthTypeId();
        if (StringUtils.isEmpty(authTypeId)) {
            return null;
        }
        AuthType authType = AuthTypeRegistry.getInstance().findById(authTypeId);
        return authType;
    }
}