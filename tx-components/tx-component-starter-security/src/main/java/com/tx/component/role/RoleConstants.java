/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月21日
 * <修改描述:>
 */
package com.tx.component.role;

/**
 * 角色常量<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RoleConstants {
    
    String DEFAULT_ROLE_CONFIG_PATH = "classpath:context/role/*.xml";
    
    String BEAN_NAME_ROLE_ITEM_DAO = "security.roleItemDao";
    
    String BEAN_NAME_ROLE_ITEM_SERVICE = "security.roleItemService";
    
    String BEAN_NAME_ROLE_TYPE_ITEM_DAO = "security.roleTypeItemDao";
    
    String BEAN_NAME_ROLE_TYPE_ITEM_SERVICE = "security.roleTypeItemService";
    
    String BEAN_NAME_ROLE_REF_ITEM_DAO = "security.roleRefItemDao";
    
    String BEAN_NAME_ROLE_REF_ITEM_SERVICE = "security.roleRefItemService";
    
    String BEAN_NAME_ROLE_TYPE_ENUM_SERVICE = "security.roleTypeEnumService";
    
    String BEAN_NAME_ROLE_ENUM_SERVICE = "security.roleEnumService";
    
    String BEAN_NAME_ROLE_CONFIG_SERVICE = "security.roleConfigService";
    
    String BEAN_NAME_ROLE_REF_SERVICE = "security.roleRefService";
    
    String BEAN_NAME_ROLE_REGISTRY = "security.roleRegistry";
    
    String BEAN_NAME_ROLE_TYPE_REGISTRY = "security.roleTypeRegistry";
    
    String CACHE_KEY_ROLE_TYPE = "security.role_type.cache";
    
    String CACHE_KEY_ROLE = "security.role.cache";
    
    //start-----------------------权限引用类型----------------------
    /** 操作员权限引用项：操作员权限引用 */
    public static final String ROLEREFTYPE_DEFAULT = "DEFAULT";
    
    /** 操作员权限引用项：操作员权限引用 */
    public static final String ROLEREFTYPE_OPERATOR = "OPERATOR";
    
    /** 操作员权限引用项：职位权限引用 */
    public static final String ROLEREFTYPE_POST = "POST";
    
    /** 操作员权限引用项：组织权限引用 */
    public static final String ROLEREFTYPE_ORGANIZATION = "ORGANIZATION";
    //end-----------------------权限引用类型----------------------
}
