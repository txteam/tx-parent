/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-22
 * <修改描述:>
 */
package com.tx.core.springmvc.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

/**
 * <请求进入后，根据配置过滤，不期望直接被访问的资源，跳转到默认页面>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RejectAccessJspFilter extends OncePerRequestFilter implements
        InitializingBean {
    
    private static Logger logger = LoggerFactory.getLogger(RejectAccessJspFilter.class);
    
    private UrlPathHelper urlPathHelper = new UrlPathHelper();
    
    private List<String> excludePath;
    
    private Set<Pattern> excludePathPatternSet = new HashSet<Pattern>();
    
    private String defaultExcludePath = "/";
    
    private String redirectPath = "/index.jsp";
    
    private Set<String> excludePathCache = new HashSet<String>();
    
    private Set<String> includePathCache = new HashSet<String>();
    
    /**
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        
        if (redirectPath == null) {
            redirectPath = "/";
        }
        else if (!redirectPath.startsWith("/")) {
            redirectPath = "/" + redirectPath;
        }
        
        if (excludePath == null) {
            return;
        }
        
        for (String excludePathTemp : this.excludePath) {
            excludePathPatternSet.add(Pattern.compile(excludePathTemp));
        }
    }
    
    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestServletPath = urlPathHelper.getPathWithinApplication(request);
        
        if (isForward(request, requestServletPath)) {
            if (logger.isDebugEnabled()) {
                logger.debug("access path {} be reject. and redirectPath to {}.",
                        requestServletPath,
                        redirectPath);
            }
            
            response.sendRedirect(request.getContextPath() + this.redirectPath);
        }
        else {
            filterChain.doFilter(request, response);
        }
        
    }
    
    /**
      *<是否进行跳转,跳转到配置页面>
      *<功能详细描述>
      * @param request
      * @param path
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private boolean isForward(HttpServletRequest request, String path) {
        //默认允许  空rest风格访问即允许 /appname/这样的访问
        if (StringUtils.isEmpty(path) || defaultExcludePath.equals(path)
                || redirectPath.equals(path)) {
            return false;
        }
        
        if (excludePathCache.contains(path)) {
            return false;
        }
        if (includePathCache.contains(path)) {
            return true;
        }
        
        if (isMatchExcludePath(path)) {
            this.excludePathCache.add(path);
            return false;
        }
        else {
            this.includePathCache.add(path);
            return true;
        }
    }
    
    /**
      *<是否匹配例外路径>
      *<功能详细描述>
      * @param path
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private boolean isMatchExcludePath(String path) {
        for (Pattern pTemp : this.excludePathPatternSet) {
            Matcher mTemp = pTemp.matcher(path);
            
            if (mTemp.matches()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @return 返回 excludePath
     */
    public List<String> getExcludePath() {
        return excludePath;
    }
    
    /**
     * @param 对excludePath进行赋值
     */
    public void setExcludePath(List<String> excludePath) {
        this.excludePath = excludePath;
    }
    
    /**
     * @return 返回 defaultExcludePath
     */
    public String getDefaultExcludePath() {
        return defaultExcludePath;
    }
    
    /**
     * @param 对defaultExcludePath进行赋值
     */
    public void setDefaultExcludePath(String defaultExcludePath) {
        this.defaultExcludePath = defaultExcludePath;
    }

    /**
     * @return 返回 redirectPath
     */
    public String getRedirectPath() {
        return redirectPath;
    }

    /**
     * @param 对redirectPath进行赋值
     */
    public void setRedirectPath(String redirectPath) {
        this.redirectPath = redirectPath;
    }
}
