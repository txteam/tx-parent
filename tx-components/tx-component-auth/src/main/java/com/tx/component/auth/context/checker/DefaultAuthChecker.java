/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-4-2
 * <修改描述:>
 */
package com.tx.component.auth.context.checker;

import com.tx.component.auth.AuthConstant;
import com.tx.component.auth.model.AuthItem;
import com.tx.component.auth.model.AuthItemRef;


 /**
  * 默认的权限检测器<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-4-2]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class DefaultAuthChecker extends BaseAuthChecker{

    /**
     * @return
     */
    @Override
    public String getCheckAuthType() {
        return AuthConstant.AUTHTYPE_ABSTRACT_DEFAULT;
    }

    /**
     * @param authItem
     * @param authItemRef
     * @param objects
     * @return
     */
    @Override
    public boolean isHasAuth(AuthItem authItem, AuthItemRef authItemRef,
            Object... objects) {
        if(authItemRef != null && authItem != null){
            return true;
        }else{
            return false;
        }
    }
}
