package com.tx.component.auth.model;

import java.util.Date;

public interface AuthItemRef {
    
    /**
     * 权限引用项的类型
     * 利用该类型
     * 实现            人员权限   AUTHREFTYPE_OPERATOR
     *         临时权限   AUTHREFTYPE_OPERATOR_TEMP
     *         角色权限   AUTHREFTYPE_ROLE
     *         职位权限   ...
     * 这里用String虽没有int查询快，但能让sql可读性增强
     * @return 返回 authRefType
     */
    String getAuthRefType();
    
    /**
     * 权限关联项id 
     * 可以是角色的id,
     * 可以是职位的id
     * ....
     * @return 返回 refId
     */
    String getRefId();
    
    /**
     * @return 返回 authId
     */
    String getAuthId();
    
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