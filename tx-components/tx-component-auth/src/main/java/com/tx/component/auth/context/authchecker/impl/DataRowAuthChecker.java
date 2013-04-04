/*
 * 描          述:  <描述>
 * 修  改   人:  grace
 * 修改时间:  2012-12-10
 * <修改描述:>
 */
package com.tx.component.auth.context.authchecker.impl;

import java.util.List;

import com.tx.component.auth.AuthConstant;
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
public class DataRowAuthChecker extends BaseAuthChecker{

	/**
	 * @return
	 */
	@Override
	public String getCheckAuthType() {
		return AuthConstant.AUTHTYPE_DATA_ROW;
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
    public boolean judgeIsHasAuth(List<AuthItemRef> authItemRefList,
            Object... objects) {
        return true;
    }
}
