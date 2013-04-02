/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-11-30
 * <修改描述:>
 */
package com.tx.component.auth.dao;

import java.util.List;

import com.tx.component.auth.model.AuthItemRef;


 /**
  * <权限的持久层>
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2012-11-30]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface AuthDao {
    
    /**
      * 根据用户id查询用户权限引用项列表
      * <功能详细描述>
      * @param operator
      * @return [参数说明]
      * 
      * @return List<AuthItemRef> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<AuthItemRef> queryItemAuthRefListByOperId(String opeId);
}
