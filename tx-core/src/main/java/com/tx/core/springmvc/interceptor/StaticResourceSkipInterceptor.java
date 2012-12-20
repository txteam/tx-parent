/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-21
 * <修改描述:>
 */
package com.tx.core.springmvc.interceptor;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ModelAndView;

/**
 * <用以静态资源跳过过滤器直接进入maphandle>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-21]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class StaticResourceSkipInterceptor implements SkipHandlerInterceptor,
        InitializingBean {
    
    private Logger logger = LoggerFactory.getLogger(StaticResourceSkipInterceptor.class);
    
    private String[] skipResourcePath;
    
    private Set<Pattern> skipResourcePatterns;
    
    private Set<String> otherResourcePathSetCache = new HashSet<String>();
    
    private Set<String> skipResourcePathSetCache = new HashSet<String>();
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        skipResourcePatterns = new HashSet<Pattern>();
        
        if (skipResourcePath == null) {
            return;
        }
        
        for (String pathTemp : skipResourcePath) {
            skipResourcePatterns.add(Pattern.compile(pathTemp));
        }
    }
    
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
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean isSkipAfterInterceptor(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        String serverPath = request.getServletPath();
        if (this.skipResourcePathSetCache.contains(serverPath)) {
            return true;
        }
        else if (otherResourcePathSetCache.contains(serverPath)) {
            return false;
        }
        
        for (Pattern pattern : this.skipResourcePatterns) {
            Matcher mTemp = pattern.matcher(serverPath);
            
            if (mTemp.matches()) {
                this.skipResourcePathSetCache.add(serverPath);
                if (logger.isDebugEnabled()) {
                    logger.debug("Set serverpath: {} can skip interceptor chain. the server path match: {}",
                            serverPath,
                            pattern.pattern());
                }
                return true;
            }
        }
        
        this.otherResourcePathSetCache.add(serverPath);
        if (logger.isDebugEnabled()) {
            logger.debug("Set serverpath: {} can not skip interceptor chain. ",
                    serverPath);
        }
        return false;
    }
    
    /**
     * @return 返回 skipResourcePath
     */
    public String[] getSkipResourcePath() {
        return skipResourcePath;
    }
    
    /**
     * @param 对skipResourcePath进行赋值
     */
    public void setSkipResourcePath(String[] skipResourcePath) {
        this.skipResourcePath = skipResourcePath;
    }
}
