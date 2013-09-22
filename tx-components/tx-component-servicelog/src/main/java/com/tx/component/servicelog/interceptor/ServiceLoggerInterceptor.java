/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-17
 * <修改描述:>
 */
package com.tx.component.servicelog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tx.component.servicelog.context.ServiceLoggerSessionContext;

/**
 * 业务日志拦截器<br/>
 *     基于springMVC的实现<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-17]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ServiceLoggerInterceptor implements HandlerInterceptor {
    
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
        HttpSession session = request != null ? request.getSession(false)
                : null;
        ServiceLoggerSessionContext.init(request, response, session);
        return false;
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
        ServiceLoggerSessionContext.remove();
    }
}
