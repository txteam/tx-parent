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
    
    //----------------session 中获取权限的key --------------    
    
    //start-----------------------权限引用类型----------------------
    /** 操作员权限引用项：操作员权限引用 */
    public static final String AUTHREFTYPE_DEFAULT = "AUTHREFTYPE_DEFAULT";
    
    /** 操作员权限引用项：操作员权限引用 */
    public static final String AUTHREFTYPE_OPERATOR = "AUTHREFTYPE_OPERATOR";
    
    /** 操作员权限引用项：操作员临时权限引用 */
    public static final String AUTHREFTYPE_OPERATOR_TEMPORARY = "AUTHREFTYPE_OPERATOR_TEMPORARY";
    
    /** 操作员权限引用项：角色权限引用 */
    public static final String AUTHREFTYPE_ROLE = "AUTHREFTYPE_ROLE";
    
    /** 操作员权限引用项：职位权限引用 */
    public static final String AUTHREFTYPE_POST = "AUTHREFTYPE_POST";
    
    /** 操作员权限引用项：组织权限引用 */
    public static final String AUTHREFTYPE_ORGANIZATION = "AUTHREFTYPE_ORGANIZATION";
    //end-----------------------权限引用类型----------------------
    
    //start-----------------------权限类型----------------------
    /** 权限类型：默认的权限类型:为抽象类型，不能直接作为权限的权限类型，用以支持AuthCheck的功能 */
    public final static String AUTHTYPE_ABSTRACT_DEFAULT = "AUTHTYPE_ABSTRACT_DEFAULT";
    
    /** 权限类型：操作权限 */
    public final static String AUTHTYPE_OPERATE = "AUTHTYPE_OPERATE";
    
    /** 权限类型：数据权限 */
    public final static String AUTHTYPE_DATA = "AUTHTYPE_DATA";
    //end-----------------------权限类型----------------------
    
    /** 常用的数据权限对应的属性名: vcid 虚中心 */
    public final static String QUERY_AUTH_PROPERTY_NAME_VCID = "vcid";
    
    public final static String QUERY_AUTH_PROPERTY_NAME_ORGANIZATIONID = "organizationId";
    
    public final static String QUERY_AUTH_PROPERTY_NAME_PROVINCEID = "provinceId";
    
}
