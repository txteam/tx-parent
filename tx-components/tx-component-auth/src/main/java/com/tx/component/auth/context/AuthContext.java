/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-11-30
 * <修改描述:>
 */
package com.tx.component.auth.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tx.component.auth.exceptions.AuthContextInitException;
import com.tx.component.auth.model.AuthItem;
import com.tx.component.auth.model.AuthItemRef;
import com.tx.component.auth.model.AuthItemRefImpl;
import com.tx.component.auth.service.AuthService;

/**
 * 权限容器<br/>
 * 功能详细描述<br/>
 * 
 * @author PengQingyang
 * @version [版本号, 2012-12-1]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AuthContext implements FactoryBean<AuthContext>,
        ApplicationContextAware, InitializingBean {
    
    /** 日志记录器 */
    private static final Logger logger = LoggerFactory.getLogger(AuthContext.class);
    
    /**
     * 线程变量:当前会话容器<br/>
     * 获取到该容器后可以<br/>
     * 获取当前回话的session从而获取到相应的权限列表
     */
    private static ThreadLocal<CurrentSessionContext> currentSessionContext = new ThreadLocal<CurrentSessionContext>() {
        /**
         * @return
         */
        @Override
        protected CurrentSessionContext initialValue() {
            CurrentSessionContext csContext = new CurrentSessionContext();
            return csContext;
        }
    };
    
    /** 懒汉模式工厂实例  */
    private static AuthContext context;
    
    /** 所有的权限项目的引用，如果超级管理员开关打开，将在系统加载权限项同时生成一份超级管理员权限引用 */
    private List<AuthItemRef> superAdminAllAuthItemRef;
    
    /**
     * 权限检查器映射，以权限
     * 权限类型检查器默认会添加几个检查器 用户自定义添加的权限检查器会覆盖该检查器
     */
    private static Map<String, AuthChecker> authCheckerMapping;
    
    /**
     * 系统的权限项集合<br/>
     * key为权限项唯一键（key,id）<br/>
     * value为具体的权限项
     */
    private static Map<String, AuthItem> authItemMapping;
    
    
    /** 业务日志记录器：默认使用logback日志记录器  */
    private static Logger serviceLogger = LoggerFactory.getLogger(AuthContext.class);
    
    /** 权限业务逻辑层 */
    private AuthService authService;
    
    /** 权限检查器实现 */
    private List<AuthChecker> authCheckers;
    
    /** 权限加载器 : 默认为通过xml配置加载权限，*/
    private List<AuthLoader> authLoaders;
    
    /** 
     * 超级管理员开关，默认为关闭<br/>
     * 如果超级管理员开关为true<br/>
     * 并且当前人员被认定为超级管理员，
     * 则该人员默认拥有系统所有权限  
     */
    private boolean superAdministratorSwitch = true;
    
    /** 超级管理员认证器 */
    private SuperAdminChecker superAdminChecker;
    

    
    /** 当前spring容器 */
    @SuppressWarnings("unused")
    private ApplicationContext applicationContext;
    
    /**
     * ApplicationContextAware接口实现<br/>
     * 用以获取spring容器引用
     * @param context
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        this.applicationContext = context;
    }
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public AuthContext getObject() throws Exception {
        return AuthContext.context;
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return AuthContext.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
    
    /**
      * 获取权限容器唯一容器
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return AuthContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static AuthContext getContext() {
        return AuthContext.context;
    }
    
    /**
     * InitializingBean接口的实现，用以在容器参数设置完成后加载相关权限
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //加载系统的权限项
        loadAuthConfig();
        
        //使系统context指向实体本身
        context = this;
    }
    
    /**
     * 重新加载权限配置
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void reLoadAuthConfig() {
        loadAuthConfig();
    }
    
    /**
      * 加载权限配置
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void loadAuthConfig() {
        //自动加载容器中实现的权限检查器
        logger.info("初始化权限容器start...");
        logger.info("      加载权限检查器...");
        loadAuthChecker();
        logger.info("      加载权限项...");
        loadAuthItems();
        logger.info("初始化权限容器end...");
    }
    
    /**
      * 加载系统的权限项
      * 1、加载同时，检查对应的权限检查器是否存在，如果不存在，则抛出异常提示，对应权限检查器不存在
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void loadAuthItems() {
        if(this.authLoaders == null || this.authLoaders.size() == 0){
            logger.warn("AuthContext init.AuthLoader is empty.");
            return ;
        }
        
        //权限项映射
        Map<String, AuthItem> tempAuthItemMapping = new HashMap<String, AuthItem>();
        for(AuthLoader authLoaderTemp : this.authLoaders){
            //加载权限项
            Set<AuthItem> authItemSet = authLoaderTemp.loadAuthItems();
            
            for (AuthItem authItem : authItemSet) {
                tempAuthItemMapping.put(authItem.getId(), authItem);
            }
            
            //如果支持超级管理开关打开，这里将申城一份全权限的引用，以便后续人员获取权限
            if (superAdministratorSwitch) {
                List<AuthItemRef> tempAllAuthItemRef = new ArrayList<AuthItemRef>();
                for (AuthItem authItem : authItemSet) {
                    tempAllAuthItemRef.add(new AuthItemRefImpl(authItem));
                }
                superAdminAllAuthItemRef = tempAllAuthItemRef;
            }
        }
        
        authItemMapping = tempAuthItemMapping;
    }
    
    /**
     * 加载检查器<br/>
     *  <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void loadAuthChecker() {
        if (this.authCheckers == null || this.authCheckers.size() == 0) {
            throw new AuthContextInitException("权限容器初始化异常,未注入权限检查器");
        }
        
        Map<String, AuthChecker> tempAuthCheckerMapping = new HashMap<String, AuthChecker>();
        for (AuthChecker authCheckerTemp : this.authCheckers) {
            logger.info("加载权限检查器：权限类型:'{}':检查器类：'{}'",
                    authCheckerTemp.getCheckAuthType(),
                    authCheckerTemp.getClass().getName());
            tempAuthCheckerMapping.put(authCheckerTemp.getCheckAuthType(),
                    authCheckerTemp);
        }
        
        authCheckerMapping = tempAuthCheckerMapping;
    }
    
    /**
      * 获取当前权限项映射
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Map<String,AuthItem> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Map<String, AuthItem> getAuthItemMapping() {
        return authItemMapping;
    }
    
    /**
     * 将当前会话绑定到线程中
     * <功能详细描述>
     * @param request
     * @param response [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void bindCurrentSessionToThread(HttpServletRequest request,
            HttpServletResponse response) {
        //绑定线程前先remove一次，以保证不会残留上一次的会话，虽然不是特别需要，也不会占用太多资源
        currentSessionContext.remove();
        
        //将当前会话绑定到现成中
        currentSessionContext.get().install(request, response);
    }
    
    /**
      * 从当前线程中移除当前会话
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void removeCurrentSessionFromThread() {
        currentSessionContext.get().uninstall();
        currentSessionContext.remove();
    }
    
    /**
      * 从当前现成中获取到当前会话<br/>
      * 该会话可能为空
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return CurrentSessionContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public CurrentSessionContext getCurrentSessionContext() {
        return currentSessionContext.get();
    }
    
    /**
     * 登录时初始化当前登录人的权限容器，权限容器放入session中<br/>
     * 请求进入后将对应的权限容器放入线程中以备后续调用 <功能详细描述>
     * 
     * @param operatorId
     * @return [参数说明]
     * 
     * @return List<AuthItemRef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<AuthItemRef> initCurrentUserAuthContextWhenLogin(
            String operatorId) {
        List<AuthItemRef> authItemRefList = getAllAuthRefByOperatorId(operatorId);
        getCurrentSessionContext().setCurrentOperatorAuthToSession(authItemRefList);
        return authItemRefList;
    }
    
    /**
      * 根据操作员id查询操作员权限集合
      * <功能详细描述>
      * @param operatorId
      * @return [参数说明]
      * 
      * @return List<AuthItemRef> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<AuthItemRef> getAllAuthRefByOperatorId(String operatorId) {
        List<AuthItemRef> authItemRefList = null;
        if (superAdministratorSwitch
                && superAdminChecker.isSuperAdmin(operatorId)) {
            authItemRefList = superAdminAllAuthItemRef;
        }
        else {
            authItemRefList = authService.queryAuthItemRefSetByOperatorId(operatorId);
        }
        return authItemRefList;
    }
    
    /**
     * 判断是否具有某权限<br/>
     * authType:除定制的几类权限特性以外， 可以为 业务权限 产品权限
     * 这里权限验证会根据当前会话以及对应的权限验证器判断是否具有对应权限
     * 
     * @param authKey
     * @param authType
     * @param objects
     *            判断权限传入的参数，可以为业务ID，可以为等等。。。。 该参数会直接传入对应的authChecker中
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isHasAuth(String authKey, Object... objects) {
        //检查对应权限的权限类型是否正确
        AuthItem authItem = getAuthItemMapping().get(authKey);
        if (authItem == null || !authItem.isValid()) {
            return false;
        }
        if (authCheckerMapping.get(authItem.getAuthType()) == null) {
            throw new AuthContextInitException(
                    "The authType:{} authChecker is not exists!",
                    authItem.getAuthType());
        }
        return authCheckerMapping.get(authItem.getAuthType())
                .isHasAuth(authItem, objects);
    }

    /**
     * @return 返回 serviceLogger
     */
    public static Logger getServiceLogger() {
        return serviceLogger;
    }

    /**
     * @param 对serviceLogger进行赋值
     */
    public static void setServiceLogger(Logger serviceLogger) {
        AuthContext.serviceLogger = serviceLogger;
    }

    /**
     * @return 返回 authService
     */
    public AuthService getAuthService() {
        return authService;
    }

    /**
     * @param 对authService进行赋值
     */
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    /**
     * @return 返回 authCheckers
     */
    public List<AuthChecker> getAuthCheckers() {
        return authCheckers;
    }

    /**
     * @param 对authCheckers进行赋值
     */
    public void setAuthCheckers(List<AuthChecker> authCheckers) {
        this.authCheckers = authCheckers;
    }

    /**
     * @return 返回 authLoaders
     */
    public List<AuthLoader> getAuthLoaders() {
        return authLoaders;
    }

    /**
     * @param 对authLoaders进行赋值
     */
    public void setAuthLoaders(List<AuthLoader> authLoaders) {
        this.authLoaders = authLoaders;
    }

    /**
     * @return 返回 superAdministratorSwitch
     */
    public boolean isSuperAdministratorSwitch() {
        return superAdministratorSwitch;
    }

    /**
     * @param 对superAdministratorSwitch进行赋值
     */
    public void setSuperAdministratorSwitch(boolean superAdministratorSwitch) {
        this.superAdministratorSwitch = superAdministratorSwitch;
    }

    /**
     * @return 返回 superAdminChecker
     */
    public SuperAdminChecker getSuperAdminChecker() {
        return superAdminChecker;
    }

    /**
     * @param 对superAdminChecker进行赋值
     */
    public void setSuperAdminChecker(SuperAdminChecker superAdminChecker) {
        this.superAdminChecker = superAdminChecker;
    }

    /**
     * @return 返回 superAdminAllAuthItemRef
     */
    public List<AuthItemRef> getSuperAdminAllAuthItemRef() {
        return superAdminAllAuthItemRef;
    }

    /**
     * @param 对superAdminAllAuthItemRef进行赋值
     */
    public void setSuperAdminAllAuthItemRef(
            List<AuthItemRef> superAdminAllAuthItemRef) {
        this.superAdminAllAuthItemRef = superAdminAllAuthItemRef;
    }
}
