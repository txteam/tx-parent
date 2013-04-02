/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-4-2
 * <修改描述:>
 */
package com.tx.component.auth.transaction;


 /**
  * 权限容器事务<br/>
  *     在向权限容器注册权限时，需要将注册的过程与事务统一起来<br/>
  *     如果事务发生回滚，需要将AuthContext中的新注册的权限移除，
  *     或在commit时才向容器写入<br/>
  *     需要依赖spring的事务管理功能，需要实现相关功能<br/>
  *     //TODO:XXXX
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-4-2]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class AuthContextTransaction {
    
}
