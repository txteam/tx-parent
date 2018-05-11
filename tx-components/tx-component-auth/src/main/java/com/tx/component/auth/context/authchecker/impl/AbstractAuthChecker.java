/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-15
 * <修改描述:>
 */
package com.tx.component.auth.context.authchecker.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.tx.component.auth.context.authchecker.AuthChecker;
import com.tx.component.auth.model.AuthRef;

/**
 * 基础权限核查器<br/>
 *     用以支持权限检测
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-15]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractAuthChecker implements AuthChecker {
    
    /**
     * 检查是否拥有某权限
     * 1、如果当前登录人员权限项引用，不存在对对应权限的引用<br/>
     * 
     * @param authKey
     * @param objects
     * @return
     */
    @Override
    public boolean isHasAuth(List<AuthRef> authItemRefList,
            Object... objects) {
        if (!CollectionUtils.isEmpty(authItemRefList)) {
            Date now = new Date();
            List<AuthRef> validAuthItemRefList = new ArrayList<AuthRef>();
            
            for (AuthRef authItemRefTemp : authItemRefList) {
                if (authItemRefTemp.getExpiryDate() == null) {
                    //没有到期时间表示非临时权限
                    validAuthItemRefList.add(authItemRefTemp);
                } else {
                    if (now.compareTo(authItemRefTemp.getEffectiveDate()) >= 0
                            && now.compareTo(authItemRefTemp.getExpiryDate()) <= 0) {
                        //生效时间到了，到期时间未到
                        validAuthItemRefList.add(authItemRefTemp);
                    } else {
                        //生效时间未到，或到期时间已到
                        continue;
                    }
                }
            }
            return judgeIsHasAuth(validAuthItemRefList, objects);
        } else {
            return false;
        }
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
    protected abstract boolean judgeIsHasAuth(
            List<AuthRef> authItemRefList, Object... objects);
}
