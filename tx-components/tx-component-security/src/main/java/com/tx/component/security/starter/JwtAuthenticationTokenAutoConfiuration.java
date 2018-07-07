/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年7月3日
 * <修改描述:>
 */
package com.tx.component.security.starter;


/**
 * JwtAuthenticationToken配置<br/>
 *    在路由内部的系统，认为都不应该直接与当前的会话以及Authentication交互
 *    前端路由分为ms-zuul,ss-zuul,api-zuul
 *    ms-zuul 维持sessionId的机制，通过ms-zuul以后，将会话中的authentication转换为，authentication-jwt-token进行传递
 *    ss-zuul 采用refresh_token,access_token机制进行控制，路由根据access_token获取，authentication信息，然后通过jwt进行传递
 *    api-zuul 采用oath2机制，主要采用access_token,或根据根token生成临时的access_token的机制，获取authentication,然后通过jwt传递
 *    内部子系统，默认采用JwtAuthenticationToken获取当前的authentication
 *       通过jwt中的jwt-header.strategy获取策略,利用策略实现类实现内部认证信息的转译，内部系统默认禁用登录，登出，禁用session,禁用crsf,cros校验
 *       通过jwt-header.skcode获取对应的签名密码<密码本地缓存即可>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年7月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JwtAuthenticationTokenAutoConfiuration {
    
}
