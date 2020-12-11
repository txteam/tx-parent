import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;

import com.tx.component.auth.model.Auth;
import com.tx.component.role.model.Role;
import com.tx.component.security.context.SecurityContext;

/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年11月8日
 * <修改描述:>
 */

/**
 * 服务于Mybatis的SqlMap中时减少类及方法的调用长度<br/>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年11月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SecurityUtils {
    
    /**
     * 根据角色id获取角色实例<br/>
     * <功能详细描述>
     * @param roleId
     * @return [参数说明]
     * 
     * @return Role [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static Role getRoleById(String roleId) {
        if (StringUtils.isBlank(roleId)) {
            return null;
        }
        return SecurityContext.getContext().getRoleById(roleId);
    }
    
    /**
     * 根据角色id获取权限实例<br/>
     * <功能详细描述>
     * @param authId
     * @return [参数说明]
     * 
     * @return Role [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static Auth getAuthById(String authId) {
        if (StringUtils.isBlank(authId)) {
            return null;
        }
        return SecurityContext.getContext().getAuthById(authId);
    }
    
    /**
     * 是否拥有指定权限<br/>
     * <功能详细描述>
     * @param authsExpression
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean hasAuth(String authsExpression) {
        if (StringUtils.isBlank(authsExpression)) {
            return true;
        }
        return SecurityContext.getContext().hasAuth(authsExpression);
    }
    
    /**
     * 多个角色是一个以逗号进行分隔的字符串。如果当前用户拥有指定权限中的任意一个则返回true<br/>
     * <功能详细描述>
     * @param authsExpression
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean hasAnyAuth(String authsExpression) {
        if (StringUtils.isBlank(authsExpression)) {
            return true;
        }
        return SecurityContext.getContext().hasAnyAuth(authsExpression);
    }
    
    /**
     * 是否拥有指定角色<br/>
     * <功能详细描述>
     * @param rolesExpression
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean hasRole(String rolesExpression) {
        if (StringUtils.isBlank(rolesExpression)) {
            return true;
        }
        return SecurityContext.getContext().hasRole(rolesExpression);
    }
    
    /**
     * 多个角色是一个以逗号进行分隔的字符串。如果当前用户拥有指定角色中的任意一个则返回true<br/>
     * <功能详细描述>
     * @param rolesExpression
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean hasAnyRole(String rolesExpression) {
        if (StringUtils.isBlank(rolesExpression)) {
            return true;
        }
        return SecurityContext.getContext().hasAnyRole(rolesExpression);
    }
    
    /**
     * 是否拥有对应的权限或角色<br/>
     * <功能详细描述>
     * @param authoritiesExpression
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean hasAuthority(String authoritiesExpression) {
        if (StringUtils.isBlank(authoritiesExpression)) {
            return true;
        }
        return SecurityContext.getContext().hasAuthority(authoritiesExpression);
    }
    
    /**
     * 
     * <功能详细描述>
     * @param authoritiesExpression
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean hasAnyAuthority(String authoritiesExpression) {
        if (StringUtils.isBlank(authoritiesExpression)) {
            return true;
        }
        return SecurityContext.getContext()
                .hasAnyAuthority(authoritiesExpression);
    }
    
    /**
     * 当前用户是否是一个匿名用户<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isAnonymous() {
        return SecurityContext.getContext().isAnonymous();
    }
    
    /**
     * 是否通过记住我进行登录<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isRememberMe() {
        return SecurityContext.getContext().isRememberMe();
    }
    
    /**
     * 表示当前用户是否已经登录认证成功了<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isAuthenticated() {
        return SecurityContext.getContext().isAuthenticated();
    }
    
    /**
     * 如果当前用户既不是一个匿名用户，同时又不是通过Remember-Me自动登录的，则返回true<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isFullyAuthenticated() {
        return SecurityContext.getContext().isFullyAuthenticated();
    }
    
    /**
     * 直接从SecurityContext获取的当前Authentication对象<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Authentication [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static Authentication getAuthentication() {
        return SecurityContext.getContext().getAuthentication();
    }
    
    /**
     * 代表当前用户的principle对象<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Object [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static Object getPrincipal() {
        return SecurityContext.getContext().getPrincipal();
    }
    
    /**
     * 判断是否可访问<br/>
     * <pre>
     *    support 'and' 'or'
     *    
     *    hasAuth([auth]) 当前用户是否拥有指定角色
     *    hasAnyAuth([auth1,auth2])  多个角色是一个以逗号进行分隔的字符串。如果当前用户拥有指定角色中的任意一个则返回true
     *    hasRole([role]) 当前用户是否拥有指定角色
     *    hasAnyRole([role1,role2])  多个角色是一个以逗号进行分隔的字符串。如果当前用户拥有指定角色中的任意一个则返回true
     *    hasAuthority([authority]) 等同于hasRole or hasAuth
     *    hasAnyAuthority([authority1,authority2]) 等同于hasAnyRole or hasAuth
     *    
     *    isAnonymous() 当前用户是否是一个匿名用户
     *    isRememberMe() 表示当前用户是否是通过Remember-Me自动登录的
     *    isAuthenticated() 表示当前用户是否已经登录认证成功了
     *    isFullyAuthenticated() 如果当前用户既不是一个匿名用户，同时又不是通过Remember-Me自动登录的，则返回true
     *    
     *    permitAll 总是返回true，表示允许所有的
     *    denyAll 总是返回false，表示拒绝所有的
     *    principle 代表当前用户的principle对象 
     *    authentication 直接从SecurityContext获取的当前Authentication对象
     * </pre>
     * @param accessExpression
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean access(String accessExpression) {
        if (StringUtils.isBlank(accessExpression)) {
            return true;
        }
        return SecurityContext.getContext().access(accessExpression);
    }
}