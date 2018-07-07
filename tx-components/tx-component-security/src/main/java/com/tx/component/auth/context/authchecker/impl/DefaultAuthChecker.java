/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-4-2
 * <修改描述:>
 */
package com.tx.component.auth.context.authchecker.impl;

import java.util.List;

import com.tx.component.auth.AuthConstants;
import com.tx.component.auth.model.AuthRef;

/**
 * 默认的权限检测器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-4-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultAuthChecker extends AbstractAuthChecker {
    
    /**
     * @return
     */
    @Override
    public String getCheckAuthType() {
        return AuthConstants.AUTHTYPE_ABSTRACT_DEFAULT;
    }
    
    /**
     * 判断是否拥有权限
     * <功能详细描述>
     * @param authItemRefList
     * @param objects
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @Override
    public boolean judgeIsHasAuth(List<AuthRef> authItemRefList,
            Object... objects) {
        return true;
    }
}
