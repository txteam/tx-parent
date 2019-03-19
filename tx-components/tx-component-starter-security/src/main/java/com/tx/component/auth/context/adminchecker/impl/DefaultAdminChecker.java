/*
 * 描          述:  <描述>
 * 修  改   人:  pengqingyang
 * 修改时间:  2012-12-14
 * <修改描述:>
 */
package com.tx.component.auth.context.adminchecker.impl;

import com.tx.component.auth.AuthConstants;
import com.tx.component.auth.context.adminchecker.AdminChecker;

/**
 * 默认的超级管理员检查器
 * 
 * @author  pengqingyang
 * @version  [版本号, 2012-12-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultAdminChecker implements AdminChecker {

    /**
     * @return
     */
    @Override
    public String refType() {
        return AuthConstants.AUTHREFTYPE_DEFAULT;
    }

    /**
     * @param refId
     * @return
     */
    @Override
    public boolean isSuperAdmin(String refId) {
        return false;
    }
    
    
}
