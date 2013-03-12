/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-12
 * <修改描述:>
 */
package com.tx.core.springmvc.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.LRUMap;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

/**
 * 重定向资源请求过滤器
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-3-12]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RedirectResourceAccessFilter extends OncePerRequestFilter {
    
    /**
     * 缓存常用路径的lrumap的size
     */
    private int maxCacheSize = 500;
    
    /**
     * 配置的重定向路径匹配模式映射
     */
    private Map<String, String> redirectPathPatternMap = new HashMap<String, String>();
    
    private LRUMap notNeedRedirectPathMapCache;
    
    private LRUMap needRedirectPathMapCache;
    
    /** 辅助处理类 */
    /**
     * 路径匹配提取
     */
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    
    /**
     * 路径辅助类
     */
    private UrlPathHelper urlPathHelper = new UrlPathHelper();
    
    /**
     * @throws ServletException
     */
    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
        
        //根据配置大小，生成缓存大小
        needRedirectPathMapCache = new LRUMap(maxCacheSize);
        notNeedRedirectPathMapCache = new LRUMap(maxCacheSize);
        
        //antPathMatcher.co
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
        //获取请求的servletPath
        String servletPath = getServletPath(request);
        
        //根据缓存判断是否应该被重定向
        if (isNeedRedirectByCache(servletPath)) {
            //获取重定向地址
            String redirectPath = getFromCache(needRedirectPathMapCache,servletPath);
            //由于存在缓存被刷新的情况，这里如果没有取到对应值，则认为应该被重新提取
            if (!StringUtils.isEmpty(redirectPath)) {
                //再次刷新缓存击中次数
                putInCache(needRedirectPathMapCache,servletPath,redirectPath);
                //如果不为空，则直接将请求转向
                request.getRequestDispatcher(redirectPath).forward(request,
                        response);
                return;
            }
        }
        //根据缓存判断是否不需要进行重定向
        if (isNotNeedRedirectByCache(servletPath)) {
            //再次舒心缓存非击中次数
            putInCache(notNeedRedirectPathMapCache,servletPath,null);
            //如果根据缓存判断出不需要进行重定向，则直接进行扭转
            filterChain.doFilter(request, response);
            return;
        }
        
        //根据缓存未能判断成功的情况
        for(Entry<String, String> entryTemp : redirectPathPatternMap.entrySet()){
            if(antPathMatcher.match(entryTemp.getKey(), servletPath)){
                String extractPath = antPathMatcher.extractPathWithinPattern(entryTemp.getKey(), servletPath);
                String redirectPath = entryTemp.getValue().endsWith("/") ? entryTemp.getValue() : entryTemp.getValue() + "/";
                //获得重定向路径
                redirectPath = StringUtils.applyRelativePath(redirectPath, extractPath);
                //再次刷新缓存击中次数
                putInCache(needRedirectPathMapCache,servletPath,redirectPath);
                //跳转向重定向路径
                request.getRequestDispatcher(redirectPath).forward(request,
                        response);
                return;
            }
        }
        //如果都不匹配则认为不应该被缓存
        //再次舒心缓存非击中次数
        putInCache(notNeedRedirectPathMapCache,servletPath,null);
        //如果根据缓存判断出不需要进行重定向，则直接进行扭转
        filterChain.doFilter(request, response);
        return;
    }
    
    /**
      * 将值压入map中
      * <功能详细描述>
      * @param map
      * @param key
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void putInCache(LRUMap map,String key,String value){
        synchronized (map) {
            map.put(key, value);
        }
    }
    
    /**
      * 从map中提取值
      * <功能详细描述>
      * @param map
      * @param key
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private synchronized String getFromCache(LRUMap map,String key){
        synchronized (map) {
            return (String)map.get(key);
        }
    }
    
    /**
      * 根据缓存判断是否不需要进行重定向
      * <功能详细描述>
      * @param servletPaht
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private boolean isNotNeedRedirectByCache(String servletPath) {
        if (notNeedRedirectPathMapCache.containsKey(servletPath)) {
            return true;
        }
        return false;
    }
    
    /**
      * 判断请求的资源是否需要重定向
      * <功能详细描述>
      * @param servletPath
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private boolean isNeedRedirectByCache(String servletPath) {
        //如果在缓存中的路径直接认为需要重定向访问路径
        if (needRedirectPathMapCache.containsKey(servletPath)) {
            return true;
        }
        return false;
    }
    
    /**
     * 获取访问资源的path
     * 
     * @param request
     * @return
     */
    private String getServletPath(HttpServletRequest request) {
        String path = urlPathHelper.getServletPath(request);
        return path;
    }
    
    /**
     * @return 返回 maxCacheSize
     */
    public int getMaxCacheSize() {
        return maxCacheSize;
    }
    
    /**
     * @param 对maxCacheSize进行赋值
     */
    public void setMaxCacheSize(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }
    
    /**
     * @return 返回 redirectPathPatternMap
     */
    public Map<String, String> getRedirectPathPatternMap() {
        return redirectPathPatternMap;
    }
    
    /**
     * @param 对redirectPathPatternMap进行赋值
     */
    public void setRedirectPathPatternMap(
            Map<String, String> redirectPathPatternMap) {
        this.redirectPathPatternMap = redirectPathPatternMap;
    }
}
