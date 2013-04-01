/*
 * 描          述:  <描述>
 * 修  改   人:  pengqingyang
 * 修改时间:  2012-12-14
 * <修改描述:>
 */
package com.tx.component.auth.context.impl;

import org.apache.commons.lang.StringUtils;

import com.tx.component.auth.context.SuperAdminChecker;

/**
 * 默认的超级管理员检查器
 * 
 * @author  pengqingyang
 * @version  [版本号, 2012-12-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultSuperAdminChecker implements SuperAdminChecker {
    
    private String superAdminId;
    
    /**
     * 判断是否为超级管理员<br/>
     * 如果被判断认为是超级管理员<br/>
     * 系统将默认该人员具有所有权限<br/>
     * 可以通过扩展该方法支持管理员组的概念<br/>
     * @param operatorId
     * @return
     */
    @Override
    public boolean isSuperAdmin(String operatorId) {
        if (StringUtils.isBlank(superAdminId)) {
            return false;
        }
        
        return superAdminId.equals(operatorId);
    }
    
    /**
     * @return 返回 superAdminId
     */
    public String getSuperAdminId() {
        return superAdminId;
    }
    
    /**
     * @param 对superAdminId进行赋值
     */
    public void setSuperAdminId(String superAdminId) {
        this.superAdminId = superAdminId;
    }
}
