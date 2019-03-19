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
     * 权限项唯一键key<br/>
     * 约定权限项目分割符为"_"
     * 如权限为"wd_"
     * <功能详细描述>
     * @param object
     * @param parentAuth
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getId(T object, Auth parentAuth);
    
    /**
     * 获取权限类型<br/>
     * <功能详细描述>
     * @param object
     * @param parentAuth
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getAuthType(T object, Auth parentAuth);
    
    /**
     * 父级权限id<br/>
     * <功能详细描述>
     * @param object
     * @param parentAuth
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getParentId(T object, Auth parentAuth);
    
    /**
     * 获取权限关联项id <br/>
     * <功能详细描述>
     * @param object
     * @param parentAuth
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getRefId(T object, Auth parentAuth);
    
    /**
     * 权限项目引用类型<br/>
     * <功能详细描述>
     * @param object
     * @param parentAuth
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getRefType(T object, Auth parentAuth);
    
    /**
     * 获取权限项名<br/>
     * <功能详细描述>
     * @param object
     * @param parentAuth
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getName(T object, Auth parentAuth);
    
    /**
     * 获取权限描述<br/>
     * <功能详细描述>
     * @param object
     * @param parentAuth
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getRemark(T object, Auth parentAuth);
    
    /**
     * 获取权限项所属模块<br/>
     * <功能详细描述>
     * @param object
     * @param parentAuth
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getModule(T object, Auth parentAuth);
}
