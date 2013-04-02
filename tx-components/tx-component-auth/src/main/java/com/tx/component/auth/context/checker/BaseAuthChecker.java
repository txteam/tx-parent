/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-15
 * <修改描述:>
 */
package com.tx.component.auth.context.checker;

import java.util.Date;
import java.util.Map;

import com.tx.component.auth.context.AuthChecker;
import com.tx.component.auth.context.AuthContext;
import com.tx.component.auth.model.AuthItem;
import com.tx.component.auth.model.AuthItemRef;

/**
 * 基础权限核查器<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-15]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class BaseAuthChecker implements AuthChecker {
    
    /**
     * @param authKey
     * @param objects
     * @return
     */
    @Override
    public boolean isHasAuth(AuthItem authItem, Object... objects) {
        //如果当前不在请求回话中认为没有权限        
        //获取当前操作人员权限map
        Map<String, AuthItemRef> currentOperatorAuthRefMap = AuthContext.getContext()
                .getCurrentSessionContext()
                .getCurrentOperatorAuthMapFromSession();
        
        //如果当前会话中不存在操作人员权限集合，或
        if (currentOperatorAuthRefMap == null
                || currentOperatorAuthRefMap.get(authItem.getId()) == null) {
            return false;
        }
        
        //如果当前权限引用依赖有效时间，则判断该权限引用是否还有效
        AuthItemRef authItemRef = currentOperatorAuthRefMap.get(authItem.getId());
        if (authItemRef.isValidDependEndDate()
                && (new Date()).compareTo(authItemRef.getEndDate()) > 0) {
            return false;
        }
        
        return isHasAuth(authItem, authItemRef, objects);
    }
    
    public abstract boolean isHasAuth(AuthItem authItem,
            AuthItemRef authItemRef, Object... objects);
}
