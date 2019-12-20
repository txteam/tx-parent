/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年3月20日
 * <修改描述:>
 */
package com.tx.core.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 请求处理工具<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年3月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class WebRequestUtils {
    
    /**
     * 获取HttpServletRequest
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return HttpServletRequest [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder
                .getRequestAttributes();
        return requestAttributes != null
                && requestAttributes instanceof ServletRequestAttributes
                        ? ((ServletRequestAttributes) requestAttributes)
                                .getRequest()
                        : null;
    }
    
    /**
     * 获取HttpServletResponse
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return HttpServletResponse [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static HttpServletResponse getResponse() {
        RequestAttributes requestAttributes = RequestContextHolder
                .getRequestAttributes();
        return requestAttributes != null
                && requestAttributes instanceof ServletRequestAttributes
                        ? ((ServletRequestAttributes) requestAttributes)
                                .getResponse()
                        : null;
    }
    
    /**
     * 获取请求Url<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getUrl() {
        StringBuffer url = getRequest().getRequestURL();
        String tempContextUrl = url
                .delete(url.length() - getRequest().getRequestURI().length(),
                        url.length())
                .append("/")
                .toString();
        return tempContextUrl;
    }
    
    /**
     * 是否是ajax请求<br/>
     * <功能详细描述>
     * @param request
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        //Header
        String requestType = request.getHeader("X-Requested-With");
        if (StringUtils.equalsIgnoreCase("XMLHttpRequest", requestType)) {
            return true;
        }
        
        //accept
        String accept = request.getHeader(HttpHeaders.ACCEPT);
        if (StringUtils.isEmpty(accept)) {
            accept = request.getHeader("accept");
        }
        if (!StringUtils.isEmpty(accept) && StringUtils
                .indexOfIgnoreCase(accept, "application/json") != -1) {
            return true;
        }
        
        //contentType
        String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);
        if (StringUtils.isEmpty(contentType)) {
            contentType = request.getHeader("content-type");
        }
        if (!StringUtils.isEmpty(contentType) && StringUtils
                .indexOfIgnoreCase(contentType, "application/json") > -1) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 重定向<br/>
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param url URL
     * @param contextRelative 是否相对上下文路径
     * @param http10Compatible 是否兼容HTTP1.0
     */
    public static void sendRedirect(HttpServletRequest request,
            HttpServletResponse response, String url, boolean isContextRelative,
            boolean isHttp10Compatible) {
        AssertUtils.notNull(request, "request is null.");
        AssertUtils.notNull(response, "response is null.");
        AssertUtils.notEmpty(url, "url is empty.");
        
        StringBuilder targetUrl = new StringBuilder();
        if (isContextRelative && url.startsWith("/")) {
            targetUrl.append(request.getContextPath());
        }
        targetUrl.append(url);
        String encodedRedirectURL = response
                .encodeRedirectURL(targetUrl.toString());
        if (isHttp10Compatible) {
            try {
                response.sendRedirect(encodedRedirectURL);
            } catch (IOException e) {
                throw new SILException(
                        "sendRedirect exception." + e.getMessage(), e);
            }
        } else {
            response.setStatus(303);
            response.setHeader("Location", encodedRedirectURL);
        }
    }
    
    /**
     * 重定向
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param url URL
     */
    public static void sendRedirect(HttpServletRequest request,
            HttpServletResponse response, String url) {
        sendRedirect(request, response, url, true, true);
    }
    
    /**
     * 添加cookie
     * 
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param name Cookie名称
     * @param value Cookie值
     * @param maxAge 有效期(单位: 秒)
     * @param path 路径
     * @param domain 域
     * @param secure 是否启用加密
     */
    public static void addCookie(HttpServletRequest request,
            HttpServletResponse response, String name, String value,
            Integer maxAge, String path, String domain, Boolean secure) {
        AssertUtils.notNull(request, "request is null.");
        AssertUtils.notNull(response, "response is null.");
        AssertUtils.notEmpty(name, "name is empty.");
        AssertUtils.notEmpty(value, "value is empty.");
        try {
            name = URLEncoder.encode(name, "UTF-8");
            value = URLEncoder.encode(value, "UTF-8");
            Cookie cookie = new Cookie(name, value);
            if (maxAge != null) {
                cookie.setMaxAge(maxAge);
            }
            if (StringUtils.isNotEmpty(path)) {
                cookie.setPath(path);
            }
            if (StringUtils.isNotEmpty(domain)) {
                cookie.setDomain(domain);
            }
            if (secure != null) {
                cookie.setSecure(secure);
            }
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            throw new SILException("addCookie exception." + e.getMessage(), e);
        }
    }
    
    /**
     * 获取cookie<br/>
     * <功能详细描述>
     * @param request
     * @param name
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getCookie(HttpServletRequest request, String name) {
        AssertUtils.notNull(request, "request is null.");
        AssertUtils.notEmpty(name, "name is empty.");
        
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            try {
                name = URLEncoder.encode(name, "UTF-8");
                for (Cookie cookie : cookies) {
                    if (StringUtils.equalsAnyIgnoreCase(name,
                            cookie.getName())) {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    }
                }
            } catch (UnsupportedEncodingException e) {
                throw new SILException("getCookie exception." + e.getMessage(),
                        e);
            }
        }
        return null;
    }
    
    /**
     * 移除cookie
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @param name
     *            Cookie名称
     * @param path
     *            路径
     * @param domain
     *            域
     */
    public static void removeCookie(HttpServletRequest request,
            HttpServletResponse response, String name, String path,
            String domain) {
        AssertUtils.notNull(request, "request is null.");
        AssertUtils.notNull(response, "response is null.");
        AssertUtils.notEmpty(name, "name is empty.");
        try {
            name = URLEncoder.encode(name, "UTF-8");
            Cookie cookie = new Cookie(name, null);
            cookie.setMaxAge(0);
            if (StringUtils.isNotEmpty(path)) {
                cookie.setPath(path);
            }
            if (StringUtils.isNotEmpty(domain)) {
                cookie.setDomain(domain);
            }
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            throw new SILException("removeCookie exception." + e.getMessage(),
                    e);
        }
    }
    
    /**
     * 根据request获取请求客户端ip地址<br/>
     * <功能详细描述>
     * @param request
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            ip = StringUtils.splitByWholeSeparator(ip, ",")[0];
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    /**
     * 根据request获取请求客户端ip地址<br/>
     * <功能详细描述>
     * @param request
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getForwardedIpAddress(HttpServletRequest request) {
        String forwardedIpAddress = request.getHeader("x-forwarded-for");
        if (forwardedIpAddress == null || forwardedIpAddress.length() == 0
                || "unknown".equalsIgnoreCase(forwardedIpAddress)) {
            forwardedIpAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        //如果forwaredIpAddress长度超过512，则截断
        if (forwardedIpAddress != null && forwardedIpAddress.length() > 512) {
            forwardedIpAddress = forwardedIpAddress.substring(0, 512);
        }
        return forwardedIpAddress;
    }
    
    /**
     * 根据request获取请求客户端ip地址<br/>
     * <功能详细描述>
     * @param request
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getRealIpAddress(HttpServletRequest request) {
        String realIpAddress = request.getHeader("X-Real-IP");
        return realIpAddress;
    }
    
    /**
     * 根据request获取请求客户端ip地址<br/>
     * <功能详细描述>
     * @param request
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getRemoteIpAddress(HttpServletRequest request) {
        String remoteIpAddress = request.getRemoteAddr();
        return remoteIpAddress;
    }
    
    //  /**
    //     * 添加cookie
    //     * 
    //     * @param request HttpServletRequest
    //     * @param response HttpServletResponse
    //     * @param name Cookie名称
    //     * @param value Cookie值
    //     * @param maxAge 有效期(单位: 秒)
    //     */
    //    public static void addCookie(HttpServletRequest request,
    //            HttpServletResponse response, String name, String value,
    //            Integer maxAge) {
    //        AssertUtils.notNull(request, "request is null.");
    //        AssertUtils.notNull(response, "response is null.");
    //        AssertUtils.notEmpty(name, "name is empty.");
    //        AssertUtils.notEmpty(value, "value is empty.");
    //        
    //        addCookie(request,
    //                response,
    //                name,
    //                value,
    //                maxAge,
    //                setting.getCookiePath(),
    //                setting.getCookieDomain(),
    //                null);
    //    }
    //    
    //    /**
    //     * 添加cookie 
    //     * @param request HttpServletRequest
    //     * @param response HttpServletResponse
    //     * @param name Cookie名称
    //     * @param value Cookie值
    //     */
    //    public static void addCookie(HttpServletRequest request,
    //            HttpServletResponse response, String name, String value) {
    //        Assert.notNull(request);
    //        Assert.notNull(response);
    //        Assert.hasText(name);
    //        Assert.hasText(value);
    //        
    //        Setting setting = SystemUtils.getSetting();
    //        addCookie(request,
    //                response,
    //                name,
    //                value,
    //                null,
    //                setting.getCookiePath(),
    //                setting.getCookieDomain(),
    //                null);
    //    }
    //    /**
    //     * 根据request获取请求客户端ip地址<br/>
    //     *<功能详细描述>
    //     * @param request
    //     * @return [参数说明]
    //     * 
    //     * @return String [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //    */
    //   public static String getIpAddress(HttpServletRequest request) {
    //       String ip = request.getHeader("x-forwarded-for");
    //       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    //           ip = request.getHeader("Proxy-Client-IP");
    //       }
    //       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    //           ip = request.getHeader("WL-Proxy-Client-IP");
    //       }
    //       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    //           ip = request.getRemoteAddr();
    //       }
    //       return ip;
    //   }
}
