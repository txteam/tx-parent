/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-17
 * <修改描述:>
 */
package com.tx.component.servicelog.interceptor;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tx.component.servicelog.context.ServiceLoggerSessionContext;

/**
 * 业务日志拦截器基础类<br/>
 *     基于springMVC的实现<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-17]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class BaseServiceLoggerInterceptor implements
        HandlerInterceptor {
    
    /**
      * 初始化向业务日志容器线程变量中写入的属性集合
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return Map<String,Object> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract Map<String, Object> initAttributes();
    
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
        ServiceLoggerSessionContext.init(request, response);
        
        //调用需要初始化写入业务日志容器的线程变量值
        Map<String, Object> attributes = initAttributes();
        if (!MapUtils.isEmpty(attributes)) {
            ServiceLoggerSessionContext localContext = ServiceLoggerSessionContext.getContext();
            
            for (Entry<String, Object> entryTemp : attributes.entrySet()) {
                localContext.setAttribute(entryTemp.getKey(),
                        entryTemp.getValue());
            }
        }
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
