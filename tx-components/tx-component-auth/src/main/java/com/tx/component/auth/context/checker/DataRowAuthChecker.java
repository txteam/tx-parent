/*
 * 描          述:  <描述>
 * 修  改   人:  grace
 * 修改时间:  2012-12-10
 * <修改描述:>
 */
package com.tx.component.auth.context.checker;

import org.springframework.stereotype.Component;

import com.tx.component.auth.AuthConstant;
import com.tx.component.auth.model.AuthItem;
import com.tx.component.auth.model.AuthItemRef;


/**
 * 数据行权限检测器
 * <功能详细描述>
 * 
 * @author  grace
 * @version  [版本号, 2012-12-10]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("dataRowAuthChecker")
public class DataRowAuthChecker extends BaseAuthChecker{

	/**
	 * @return
	 */
	@Override
	public String getCheckAuthType() {
		return AuthConstant.AUTHTYPE_DATA_ROW;
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
