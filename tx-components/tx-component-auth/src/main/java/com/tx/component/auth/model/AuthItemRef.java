package com.tx.component.auth.model;

import java.util.Date;

/**
  * 权限项引用
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-3-21]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public interface AuthItemRef {
    
    /**
     * 权限引用项的类型<br/>
     * 利用该类型<br/>
     * 实现           人员权限   AUTHREFTYPE_OPERATOR          <br/>
     *         临时权限   AUTHREFTYPE_OPERATOR_TEMP     <br/>
     *         角色权限   AUTHREFTYPE_ROLE              <br/>
     *         职位权限   ...
     *         ...
     * 这里用String虽没有int查询快，但能让sql可读性增强
     * @return 返回 authRefType
     */
    String getAuthRefType();
    
    /**
     * @return 返回 authItem实例
     */
    AuthItem getAuthItem();
    
    /**
      * 设置权限引用项，对应的权限实体
      * <功能详细描述>
      * @param authItem [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    void setAuthItem(AuthItem authItem);
    
    /**
     * 权限关联项id 
     * 可以是角色的id,
     * 可以是职位的id
     * ....
     * @return 返回 refId
     */
    String getRefId();
    
    /**
     * @return 返回 createOperId
     */
    String getCreateOperId();
    
    /**
     * @return 返回 createDate
     */
    Date getCreateDate();
    
    /**
     * @return 返回 endDate
     */
    Date getEndDate();
    
    /**
     * @return 返回 isValidDependEndDate
     */
    boolean isValidDependEndDate();
}