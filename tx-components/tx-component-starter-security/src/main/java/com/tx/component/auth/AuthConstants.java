/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-11-30
 * <修改描述:>
 */
package com.tx.component.auth;

/**
 * 权限常量
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2012-11-30]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface AuthConstants {
    
    String DEFAULT_AUTH_CONFIG_PATH = "classpath:context/auth/*.xml";
    
    String BEAN_NAME_AUTH_ITEM_DAO = "security.authItemDao";
    
    String BEAN_NAME_AUTH_ITEM_SERVICE = "security.authItemService";
    
    String BEAN_NAME_AUTH_TYPE_ITEM_DAO = "security.authTypeItemDao";
    
    String BEAN_NAME_AUTH_TYPE_ITEM_SERVICE = "security.authTypeItemService";
    
    String BEAN_NAME_AUTH_REF_ITEM_DAO = "security.authRefItemDao";
    
    String BEAN_NAME_AUTH_REF_ITEM_SERVICE = "security.authRefItemService";
    
    String BEAN_NAME_AUTH_TYPE_ENUM_SERVICE = "security.authTypeEnumService";
    
    String BEAN_NAME_AUTH_ENUM_SERVICE = "security.authEnumService";
    
    String BEAN_NAME_AUTH_CONFIG_SERVICE = "security.authConfigService";
    
    String BEAN_NAME_AUTH_REF_SERVICE = "security.authRefService";
    
    String BEAN_NAME_AUTH_REGISTRY = "security.authRegistry";
    
    String BEAN_NAME_AUTH_TYPE_REGISTRY = "security.authTypeRegistry";
    
    String CACHE_KEY_AUTH_TYPE = "security.auth_type.cache";
    
    String CACHE_KEY_AUTH = "security.auth.cache";
    
    //----------------session 中获取权限的key --------------    
    
    //start-----------------------权限引用类型----------------------
    /** 操作员权限引用项：操作员权限引用 */
    public static final String AUTHREFTYPE_DEFAULT = "DEFAULT";
    
    /** 操作员权限引用项：操作员权限引用 */
    public static final String AUTHREFTYPE_OPERATOR = "OPERATOR";
    
    /** 操作员权限引用项：角色权限引用 */
    public static final String AUTHREFTYPE_ROLE = "ROLE";
    
    /** 操作员权限引用项：职位权限引用 */
    public static final String AUTHREFTYPE_POST = "POST";
    
    /** 操作员权限引用项：组织权限引用 */
    public static final String AUTHREFTYPE_ORGANIZATION = "ORGANIZATION";
    //end-----------------------权限引用类型----------------------
    
    //start-----------------------权限类型----------------------
    /** 权限类型：默认的权限类型:为抽象类型，不能直接作为权限的权限类型，用以支持AuthCheck的功能 */
    public final static String AUTHTYPE_ABSTRACT_DEFAULT = "AUTHTYPE_ABSTRACT_DEFAULT";
    
    /** 权限类型：操作权限 */
    public final static String AUTHTYPE_OPERATE = "AUTHTYPE_OPERATE";
    
    /** 权限类型：数据权限 */
    public final static String AUTHTYPE_DATA = "AUTHTYPE_DATA";
    //end-----------------------权限类型----------------------
}
