/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月15日
 * <修改描述:>
 */
package com.tx.component.security;

/**
 * 安全模块常量类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface SecurityContextConstants {
    
    /** 角色开头字母 */
    String AUTHORITY_ROLE_STARTWITH = "ROLE_";
    
    String PROPERTIES_PREFIX = "tx.component.security";
    
    /** 刷新token */
    String REFRESH_TOKEN_NAME = "refresh_token";
    
    /** 访问token */
    String ACCESS_TOKEN_NAME = "access_token";
}
