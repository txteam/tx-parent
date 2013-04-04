/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-2
 * <修改描述:>
 */
package com.tx.component.auth.springmvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tx.component.auth.context.AuthContext;
import com.tx.component.auth.context.AuthSessionContext;

/**
 * 权限容器拦截器支持器<br/>
 * 1、用以提供在请求进入后，将当前会话压入请求线程中<br/>
 * 2、在请求完成后或发生异常后，将现成中的会话移除
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthContextSupportInterceptor implements HandlerInterceptor {
    
    private String authContextKey = "authContext";
    
    @Resource(name = "authContext")
    private AuthContext authContext;
    
    /**
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        AuthSessionContext.bindCurrentSessionToThread(request, response);
        return true;
    }
    
    /**
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        request.setAttribute(authContextKey, authContext);
    }
    
    /**
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        AuthSessionContext.removeCurrentSessionFromThread();
        request.setAttribute(authContextKey, authContext);
    }

    /**
     * @return 返回 authContextKey
     */
    public String getAuthContextKey() {
        return authContextKey;
    }

    /**
     * @param 对authContextKey进行赋值
     */
    public void setAuthContextKey(String authContextKey) {
        this.authContextKey = authContextKey;
    }
}
