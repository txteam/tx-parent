/*
 * 描          述:  <描述>
 * 修  改   人:  grace
 * 修改时间:  2012-12-10
 * <修改描述:>
 */
package com.tx.component.auth.context.impl;

import com.tx.component.auth.AuthConstant;
import com.tx.component.auth.model.AuthItem;
import com.tx.component.auth.model.AuthItemRef;


 /**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  grace
 * @version  [版本号, 2012-12-10]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DataRowAuthChecker extends BaseAuthChecker{

	/**
	 * @return
	 */
	@Override
	public String getCheckAuthType() {
		return AuthConstant.TYPE_DATA_ROW;
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
        return true;
    }
}
