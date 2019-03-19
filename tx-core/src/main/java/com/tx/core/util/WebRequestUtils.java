/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年3月20日
 * <修改描述:>
 */
package com.tx.core.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

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
}
