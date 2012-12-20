/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-21
 * <修改描述:>
 */
package com.tx.core.springmvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * <具有跳过interceptor拦截判断的拦截器>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-21]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface SkipHandlerInterceptor extends HandlerInterceptor {
    
    /**
      * <判断是否跳过后续的拦截器,该拦截器需要 preHandle返回true时才会生效>
      * <功能详细描述>
      * @param request
      * @param response
      * @param handler
      * @return
      * @throws Exception [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isSkipAfterInterceptor(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception;
}
