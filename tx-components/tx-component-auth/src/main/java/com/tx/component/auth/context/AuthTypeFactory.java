package com.tx.component.auth.context;

import java.util.List;
import java.util.Map;

import com.tx.component.auth.model.AuthTypeItem;

/**
  * 权限类型工厂
  *     1、如果对应authType不存在，则将创建的新的权限类型添加进缓存
  *     2、如果对应authType已经存在，则更新相关字段后，返回对应权限类型
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-4-3]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public interface AuthTypeFactory {
    
    /**
      * 创建权限类型实例<br/>
      * <功能详细描述>
      * @param authType
      * @param name
      * @param description
      * @param isViewAble
      * @param isConfigAble
      * @return [参数说明]
      * 
      * @return AuthTypeItem [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    abstract AuthTypeItem registeNewOrGetAuthTypeItem(String authType,
            String name, String description, boolean isViewAble,
            boolean isConfigAble);
    
    /**
      * 获取权限类型实例
      * <功能详细描述>
      * @param authType
      * @return [参数说明]
      * 
      * @return AuthTypeItem [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    abstract AuthTypeItem registeNewOrGetAuthTypeItem(String authType);
    
    /**
      * 获取所有注册过的权限类型
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Set<AuthTypeItem> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    abstract List<AuthTypeItem> getAllRegistedAuthTypeItemList();
    
    /**
      * 获取系统中已经注册过的权限项映射
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Map<String,AuthTypeItem> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    abstract Map<String, AuthTypeItem> getAllRegistedAuthTypeItemMap();
    
}