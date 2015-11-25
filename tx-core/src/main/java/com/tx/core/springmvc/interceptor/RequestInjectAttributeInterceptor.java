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
 * <向Request中注入部分常量> <功能详细描述>
 * 
 * @author PengQingyang
 * @version [版本号, 2012-10-19]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class RequestInjectAttributeInterceptor implements HandlerInterceptor {
    
    private static Logger logger = LoggerFactory.getLogger(RequestInjectAttributeInterceptor.class);
    
    public static final String CONTEXT_PATH_ATTR_NAME = "contextPath";
    
    public static final String CONTEXT_PATH_ATTR_NAME_PLACEHOLDER = "${contextPath}";
    
    private Map<String, Object> injectAttributes = new HashMap<String, Object>();
    
    /** 是否覆盖注入,当为false时,如果key存在且对应value不为null则注入,true则直接覆盖 */
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
        
        for (Entry<String, Object> attrTemp : this.injectAttributes.entrySet()) {
            if (logger.isDebugEnabled()) {
                logger.info("request set Attrubute key: {} value: {} .",
                        attrTemp.getKey(),
                        attrTemp.getValue());
            }
            
            if (this.isCover) {
                if (attrTemp.getValue() instanceof String) {
                    String objString = (String) attrTemp.getValue();
                    if (objString.contains(CONTEXT_PATH_ATTR_NAME_PLACEHOLDER)) {
                        request.setAttribute(attrTemp.getKey(),
                                objString.replace(CONTEXT_PATH_ATTR_NAME_PLACEHOLDER,
                                        request.getContextPath()));
                    } else {
                        request.setAttribute(attrTemp.getKey(), objString);
                    }
                } else {
                    request.setAttribute(attrTemp.getKey(), attrTemp.getValue());
                }
                continue;
            } else {
                //对应的属性值不存在时才进行写入<br/>
                if (request.getAttribute(attrTemp.getKey()) == null) {
                    request.setAttribute(attrTemp.getKey(), attrTemp.getValue());
                }
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
     * @return 返回 isCover
     */
    public boolean isCover() {
        return isCover;
    }
    
    /**
     * @param isCover 是否覆盖注入,当为false时,如果key存在且对应value不为null则注入,true则直接覆盖
     */
    public void setCover(boolean isCover) {
        this.isCover = isCover;
    }
    
    /**
     * @return 返回 injectAttributes
     */
    public Map<String, Object> getInjectAttributes() {
        return injectAttributes;
    }
    
    /**
     * @param 对injectAttributes进行赋值
     */
    public void setInjectAttributes(Map<String, Object> injectAttributes) {
        this.injectAttributes = injectAttributes;
    }
}
