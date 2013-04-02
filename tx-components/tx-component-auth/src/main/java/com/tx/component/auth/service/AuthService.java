/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-4-2
 * <修改描述:>
 */
package com.tx.component.auth.service;

import java.util.List;

import com.tx.component.auth.model.AuthItemRef;


 /**
  * 权限业务层接口<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-4-2]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface AuthService {
    
    public List<AuthItemRef> queryAuthItemRefSetByOperatorId(String operatorId);
}
