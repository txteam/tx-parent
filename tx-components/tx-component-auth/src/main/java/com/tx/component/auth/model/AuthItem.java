package com.tx.component.auth.model;

import java.io.Serializable;
import java.util.List;

import com.tx.core.tree.model.TreeAble;

/**
  * 权限项接口
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2012-12-14]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public interface AuthItem extends TreeAble<List<AuthItem>, AuthItem>,
        Serializable {
    
    /**
     * 权限项唯一键key 
     * 约定权限项目分割符为"_"
     * 如权限为"wd_"
     * @return 返回 id
     */
    String getId();
    
    /**
      * 获取权限项系统id
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String getSystemId();
    
    /**
      * 社区权限所属系统<br/>
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    void setSystemId(String systemId);
    
    /**
     * 父级权限id
     * @return 返回 parentId 
     */
    String getParentId();
    
    /**
     * 权限名
     * @return 返回 name
     */
    String getName();
    
    /**
     * 获取权限描述
     * @return 返回 description
     */
    String getDescription();
    
    /**
     * 返回子权限
     * @return 返回 childs
     */
    List<AuthItem> getChilds();
    
    /**
     * 获取权限类型
     * @return 返回 authType
     */
    String getAuthType();
    
    /**
     * 判断是否可见
     * @return 返回 isAbstract
     */
    boolean isViewAble();
    
    /**
      * 是否可编辑
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    boolean isEditAble();
    
    /**
      * 是否能进行配置
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    boolean isConfigAble();
    
    /**
     * 该权限是否有效
     * @return 返回 isValid
     */
    boolean isValid();
    
}