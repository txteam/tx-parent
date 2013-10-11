/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-11
 * <修改描述:>
 */
package com.tx.component.auth.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tx.component.auth.annotation.CheckOperateAuth;
import com.tx.component.auth.context.AuthContext;
import com.tx.core.exceptions.logic.NoAuthorityAccessException;

/**
 * 控制器中，操作权限校验切面
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-10-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ControllerCheckOperateAuthInterceptor implements
        HandlerInterceptor {
    
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
        if (handler instanceof HandlerMethod
                && ((HandlerMethod) handler).getMethod()
                        .isAnnotationPresent(CheckOperateAuth.class)) {
            HandlerMethod handlerMethod = ((HandlerMethod) handler);
            CheckOperateAuth checkOperateAuthAnno = handlerMethod.getMethod()
                    .getAnnotation(CheckOperateAuth.class);
            String authKey = checkOperateAuthAnno.key();
            if (StringUtils.isEmpty(authKey)) {
                return true;
            }
            
            //如果无权限抛出异常
            if(!AuthContext.getContext().hasAuth(authKey)){
                throw new NoAuthorityAccessException("Controller class:{} method:{} needAuth:{}",
                        new Object[]{handlerMethod.getBean().getClass(),handlerMethod.getMethod(),authKey});
            }
        }
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
        
    }    
}
