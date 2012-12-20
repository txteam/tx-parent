/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-2
 * 修改描述:  
 */
package com.tx.component.auth.model;

import com.tx.component.auth.AuthConstant;

/**
 * 角色权限项引用<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RoleAuthRef extends DefaultAuthItemRef {
    
    /** 注释内容 */
    private static final long serialVersionUID = -5225480864798103809L;
    
    /** 操作员id */
    private String roleId;
    
    /**
     * @return 返回 roleId
     */
    public String getRoleId() {
        return roleId;
    }
    
    /**
     * @param 对roleId进行赋值
     */
    public void setRoleId(String roleId) {
        setRefId(roleId);
        this.roleId = roleId;
    }
    
    /**
     * @return
     */
    @Override
    public String getAuthRefType() {
        return AuthConstant.AUTHREFTYPE_ROLE;
    }
    
    /**
     * @return
     */
    @Override
    public String getRefId() {
        return super.getRefId();
    }
    
    /**
     * @return
     */
    @Override
    public String getAuthId() {
        return super.getAuthId();
    }
    
    /**
     * @param authRefType
     */
    @Override
    public void setAuthRefType(String authRefType) {
        super.setAuthRefType(AuthConstant.AUTHREFTYPE_ROLE);
    }
    
}
