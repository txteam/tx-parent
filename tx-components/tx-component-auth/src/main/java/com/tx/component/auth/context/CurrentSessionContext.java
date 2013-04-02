/*
 * 描          述:  <描述>
 * 修  改   人:  pengqingyang
 * 修改时间:  2012-12-3
 * <修改描述:>
 */
package com.tx.component.auth.context;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tx.component.auth.AuthConstant;
import com.tx.component.auth.model.AuthItemRef;

/**
 * 当前会话容器<br/> 
 * <功能详细描述>
 * 
 * @author pengqingyang
 * @version [版本号, 2012-12-3]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CurrentSessionContext implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = -1543807551170666376L;
    
    /** 当前请求 */
    private HttpServletRequest request;
    
    /** 返回的response */
    private HttpServletResponse response;
    
    /** 当前请求的会话  */
    private HttpSession session;
    
    /**
      * 初始化(安装)会话容器<br/>
      * 1、提供给请求进入interceptor后调用该方法，将当前会话的request,response压入会话容器中以便后续判断权限
      * <功能详细描述>
      * @param request
      * @param response [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void install(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        if (this.request != null) {
            this.session = this.request.getSession();
        }
    }
    
    /**
      * 销毁容器中的数据，防止由于线程池造成的线程复用后，引用到不该引用的内容
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void uninstall() {
        this.request = null;
        this.response = null;
        this.session = null;
    }
    
    /**
      * 将当前操作人员的权限引用放入session中 <br/>
      * 1、如果当前不存在会话，者直接跳过该逻辑<br/>
      *<功能详细描述>
      * @param authItemRefList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setCurrentOperatorAuthToSession(List<AuthItemRef> authItemRefSet) {
        //如果当前不存在会话，者直接跳过该逻辑
        if (this.session == null) {
            return;
        }
        
        //如果当前人员不含有权限，也会压入一个空的权限引用map
        Map<String, AuthItemRef> authItemRefMap = new HashMap<String, AuthItemRef>();
        if (authItemRefSet != null) {
            for (AuthItemRef refTemp : authItemRefSet) {
                authItemRefMap.put(refTemp.getAuthItem().getId(), refTemp);
            }
        }
        this.session.setAttribute(AuthConstant.SESSION_KEY_CURRENT_USER_AUTHREF_MAP,
                authItemRefMap);
    }
    
    /**
      * 从session中获取当前操作人员的权限集合<br/>
      * 1、当前如果不存在会话，者返回null
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return List<AuthItemRef> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Map<String, AuthItemRef> getCurrentOperatorAuthMapFromSession() {
        if (this.session == null
                || this.session.getAttribute(AuthConstant.SESSION_KEY_CURRENT_USER_AUTHREF_MAP) == null
                || !(this.session.getAttribute(AuthConstant.SESSION_KEY_CURRENT_USER_AUTHREF_MAP) instanceof Map<?, ?>)) {
            return null;
        }
        
        @SuppressWarnings("unchecked")
        Map<String, AuthItemRef> authItemRefMap = (Map<String, AuthItemRef>) this.session.getAttribute(AuthConstant.SESSION_KEY_CURRENT_USER_AUTHREF_MAP);
        return authItemRefMap == null ? new HashMap<String, AuthItemRef>()
                : authItemRefMap;
    }
    
    /**
     * @return 返回 request
     */
    public HttpServletRequest getRequest() {
        return request;
    }
    
    /**
     * @param 对request进行赋值
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    /**
     * @return 返回 response
     */
    public HttpServletResponse getResponse() {
        return response;
    }
    
    /**
     * @param 对response进行赋值
     */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    
    /**
     * @return 返回 session
     */
    public HttpSession getSession() {
        return session;
    }
    
    /**
     * @param 对session进行赋值
     */
    public void setSession(HttpSession session) {
        this.session = session;
    }
}
