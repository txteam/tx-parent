/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-19
 * <修改描述:>
 */
package com.tx.core.springmvc.interceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * <向Request中注入部分常量>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RequestInjectAttributeInterceptor implements HandlerInterceptor {
    
    private static Logger logger = LoggerFactory.getLogger(RequestInjectAttributeInterceptor.class);
    
    private Map<String, String> injectAttributes = new HashMap<String, String>();
    
    public static final String CONTEXT_PATH_ATTR_NAME = "contextPath";
    
    private boolean isCover = true;
    
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
        
        request.setAttribute(CONTEXT_PATH_ATTR_NAME, request.getContextPath());
        
        if (injectAttributes == null || injectAttributes.size() == 0) {
            return true;
        }
        
        for (Entry<String, String> attrTemp : this.injectAttributes.entrySet()) {
            if (logger.isDebugEnabled()) {
                logger.info("request set Attrubute key: {} value: {} .",
                        attrTemp.getKey(),
                        attrTemp.getValue());
            }
            
            if (this.isCover) {
                request.setAttribute(attrTemp.getKey(), attrTemp.getValue());
                continue;
            }
            
            if (request.getAttribute(attrTemp.getKey()) == null) {
                request.setAttribute(attrTemp.getKey(), attrTemp.getValue());
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
    
    /**
     * @return 返回 injectAttributes
     */
    public Map<String, String> getInjectAttributes() {
        return injectAttributes;
    }
    
    /**
     * @param 对injectAttributes进行赋值
     */
    public void setInjectAttributes(Map<String, String> injectAttributes) {
        this.injectAttributes = injectAttributes;
    }
}
