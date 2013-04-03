/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.tx.component.auth.context;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tx.component.auth.model.AuthItemRef;

/**
 * 权限会话容器
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-4-3]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthSessionContext implements FactoryBean<AuthSessionContext>,
        ApplicationContextAware, InitializingBean {
    
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
    
    /** 
     * 超级管理员开关，默认为关闭<br/>
     * 如果超级管理员开关为true<br/>
     * 并且当前人员被认定为超级管理员，
     * 则该人员默认拥有系统所有权限  
     */
    private boolean superAdministratorSwitch = true;
    
    /** 所有的权限项目的引用，如果超级管理员开关打开，将在系统加载权限项同时生成一份超级管理员权限引用 */
    private List<AuthItemRef> superAdminAllAuthItemRef;
    
    /** 超级管理员认证器 */
    private SuperAdminChecker superAdminChecker;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public AuthSessionContext getObject() throws Exception {
        return this;
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return AuthSessionContext.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
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
    public static void bindCurrentSessionToThread(HttpServletRequest request,
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
    public static void removeCurrentSessionFromThread() {
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
    public static CurrentSessionContext getCurrentSessionContext() {
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
        } else {
            //authItemRefList = authService.queryAuthItemRefSetByOperatorId(operatorId);
        }
        return authItemRefList;
    }
}
