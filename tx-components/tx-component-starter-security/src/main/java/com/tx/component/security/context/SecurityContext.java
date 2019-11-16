/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-11-30
 * <修改描述:>
 */
package com.tx.component.security.context;

import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.tx.component.auth.model.Auth;
import com.tx.component.role.model.Role;
import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 权限容器<br/>
 * 功能详细描述<br/>
 *     下一版本，将把权限注册的相关逻辑抽取接口AuthRegister从AuthContext中移出<br/>
 * 
 * @author PengQingyang
 * @version [版本号, 2012-12-1]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@SuppressWarnings("unused")
public class SecurityContext extends SecurityContextBuilder {
    /* 不需要进行注入部分属性 */
    
    /** 单子模式权限容器唯一实例 */
    protected static SecurityContext context;
    
    /**
     * <默认构造函数>
     * 构造函数级别为子类可见<br/>
     */
    protected SecurityContext() {
    }
    
    /**
      * 获取权限容器实例
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return AuthContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static SecurityContext getContext() {
        if (SecurityContext.context != null) {
            return SecurityContext.context;
        }
        synchronized (SecurityContext.class) {
            SecurityContext.context = applicationContext.getBean(beanName,
                    SecurityContext.class);
        }
        AssertUtils.notNull(SecurityContext.context, "context is null.");
        return SecurityContext.context;
    }
    
    /**
     * @throws Exception
     */
    @Override
    protected void doInitContext() throws Exception {
        
    }
    
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
    public Role getRoleById(String roleId) {
        if (StringUtils.isBlank(roleId)) {
            return null;
        }
        Role role = getRoleRegistry().findById(roleId);
        return role;
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
    public Auth getAuthById(String authId) {
        if (StringUtils.isBlank(authId)) {
            return null;
        }
        Auth auth = getAuthRegistry().findById(authId);
        return auth;
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
    public boolean hasAuth(String authsExpression) {
        if (StringUtils.isBlank(authsExpression)) {
            return true;
        }
        String[] authIds = StringUtils.splitByWholeSeparator(authsExpression,
                ",");
        for (String authIdTemp : authIds) {
            if (StringUtils.isBlank(authIdTemp)) {
                continue;
            }
            
            Auth auth = getAuthById(authIdTemp);
            if (auth == null) {
                continue;
            }
            //判断是否有对应权限
        }
        return true;
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
    public boolean hasAnyAuth(String authsExpression) {
        if (StringUtils.isBlank(authsExpression)) {
            return true;
        }
        String[] authIds = StringUtils.splitByWholeSeparator(authsExpression,
                ",");
        for (String authIdTemp : authIds) {
            if (StringUtils.isBlank(authIdTemp)) {
                continue;
            }
            
            Auth auth = getAuthById(authIdTemp);
            if (auth == null) {
                continue;
            }
            //判断是否有对应权限
        }
        return true;
    }
    
    /**
     * @return
     */
    public boolean hasRole(String rolesExpression) {
        if (StringUtils.isBlank(rolesExpression)) {
            return true;
        }
        String[] roleIds = StringUtils.splitByWholeSeparator(rolesExpression,
                ",");
        for (String roleIdTemp : roleIds) {
            if (StringUtils.isBlank(roleIdTemp)) {
                continue;
            }
            
            Role role = getRoleById(roleIdTemp);
            if (role == null) {
                continue;
            }
            //判断是否有对应角色
        }
        return true;
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
    public boolean hasAnyRole(String rolesExpression) {
        if (StringUtils.isBlank(rolesExpression)) {
            return true;
        }
        String[] roleIds = StringUtils.splitByWholeSeparator(rolesExpression,
                ",");
        for (String roleIdTemp : roleIds) {
            if (StringUtils.isBlank(roleIdTemp)) {
                continue;
            }
            
            Role role = getRoleById(roleIdTemp);
            if (role == null) {
                continue;
            }
            //判断是否有对应角色
        }
        return true;
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
    public boolean hasAuthority(String authoritiesExpression) {
        if (StringUtils.isBlank(authoritiesExpression)) {
            return true;
        }
        String[] authorities = StringUtils
                .splitByWholeSeparator(authoritiesExpression, ",");
        for (String authority : authorities) {
            if (StringUtils.isBlank(authority)) {
                continue;
            }
            
            Auth auth = null;
            Role role = getRoleById(authority);
            if (role == null) {
                auth = getAuthById(authority);
            }
            if (role == null && auth == null) {
                continue;
            }
            //判断是否有对应角色或权限
        }
        return true;
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
    public boolean hasAnyAuthority(String authoritiesExpression) {
        if (StringUtils.isBlank(authoritiesExpression)) {
            return true;
        }
        String[] authorities = StringUtils
                .splitByWholeSeparator(authoritiesExpression, ",");
        for (String authority : authorities) {
            if (StringUtils.isBlank(authority)) {
                continue;
            }
            
            Auth auth = null;
            Role role = getRoleById(authority);
            if (role == null) {
                auth = getAuthById(authority);
            }
            if (role == null && auth == null) {
                continue;
            }
            //判断是否有对应角色或权限
        }
        return true;
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
    public boolean isAnonymous() {
        SecurityContextHolder.getContext().getAuthentication();
        return true;
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
    public boolean isRememberMe() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication == null) {
            return false;
        }
        
        boolean flag = RememberMeAuthenticationToken.class
                .isAssignableFrom(authentication.getClass());
        return true;
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
    public boolean isAuthenticated() {
        boolean flag = SecurityContextHolder.getContext()
                .getAuthentication()
                .isAuthenticated();
        return flag;
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
    public boolean isFullyAuthenticated() {
        boolean flag = isAuthenticated() && !isRememberMe();
        return flag;
    }
    
    /**
     * 总是返回true，表示允许所有的
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean permitAll() {
        return true;
    }
    
    public boolean denyAll() {
        return false;
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
    public Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        return authentication;
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
    public Object getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        return principal;
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
     *    anonymous 当前用户是否是一个匿名用户
     *    rememberMe 表示当前用户是否是通过Remember-Me自动登录的
     *    authenticated 表示当前用户是否已经登录认证成功了
     *    fullyAuthenticated 如果当前用户既不是一个匿名用户，同时又不是通过Remember-Me自动登录的，则返回true
     *    permitAll 总是返回true，表示允许所有的
     *    denyAll 总是返回false，表示拒绝所有的
     *    
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
    public boolean access(String accessExpression) {
        if (StringUtils.isBlank(accessExpression)) {
            return true;
        }
        String[] expressions = StringUtils
                .splitByWholeSeparator(accessExpression, ",");
        for (String expressionTemp : expressions) {
            if (StringUtils.isBlank(expressionTemp)) {
                continue;
            }
            
            ExpressionParser parser = new SpelExpressionParser();
            //判断是否有对应角色或权限
            StandardEvaluationContext ctx = new StandardEvaluationContext();
            //root
            SecurityContext context = SecurityContext.getContext();
            
            parser.parseExpression(expressionTemp).getValue(ctx, context);
        }
        return true;
    }
    
    /**
     * hasAuth([role])
     * hasAnyAuth([role1,role2])
     * hasRole([role])
     * hasAnyRole([role1,role2])
     * hasAuthority([auth])
     * hasAnyAuthority([auth1,auth2])
     * <功能详细描述>
     * @param expression
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static String preprocess(String expression) {
        AssertUtils.notEmpty(expression, "expression is empty.");
        
        return null;
    }
    
}
