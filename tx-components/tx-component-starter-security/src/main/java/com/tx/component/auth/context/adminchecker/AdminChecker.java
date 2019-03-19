/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-14
 * <修改描述:>
 */
package com.tx.component.auth.context.adminchecker;



 /**
  * 超级管理员检查器<br/>
  * 判断当前操作人员是否为超级管理员
  * 
  * @author  brady
  * @version  [版本号, 2012-12-14]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface AdminChecker {
    
    /**
      * 引用类型<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String refType();
    
    /**
      * 判断是否为超级管理员<br/>
      * 如果被判断认为是超级管理员<br/>
      * 系统将默认该人员具有所有权限<br/>
      * 可以通过扩展该方法支持管理员组的概念<br/>
      *<功能详细描述>
      * @param refType2RefIdMapping
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isSuperAdmin(String refId);
}
