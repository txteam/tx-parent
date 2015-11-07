/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年11月8日
 * <修改描述:>
 */
package com.tx.component.auth.model;


/**
 * 权限项适配器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年11月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface AuthItemAdapter<T> {
    
    /**
     * 权限项唯一键key 
     * 约定权限项目分割符为"_"
     * 如权限为"wd_"
     * @return 返回 id
     */
    String getId(T object, AuthItem parentAuthItem);
    
    /**
      * 父级权限id
      * <功能详细描述>
      * @param object
      * @param parentAuthItem
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String getParentId(T object, AuthItem parentAuthItem);
    
    /**
     * 获取权限关联项id 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   String getRefId(T object, AuthItem parentAuthItem);
    
    /**
      * 权限项目引用类型
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String getRefType(T object, AuthItem parentAuthItem);
    
    /**
     * 权限名
     * @return 返回 name
     */
    String getName(T object, AuthItem parentAuthItem);
    
    /**
     * 获取权限描述
     * @return 返回 description
     */
    String getDescription(T object, AuthItem parentAuthItem);
    
    /**
     * 获取权限类型
     * @return 返回 authType
     */
    String getAuthType(T object, AuthItem parentAuthItem);
}
