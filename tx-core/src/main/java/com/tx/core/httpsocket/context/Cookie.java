package com.tx.core.httpsocket.context;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.httpsocket.exception.HttpSocketException;

/**
 * Cookie<br>
 * 在进行hash比较的时候,只取name属性来比较.
 * 
 * @author Rain
 * 
 */
public final class Cookie implements Serializable {
    private static final long serialVersionUID = 167353839345282434L;
    
    public static final String PATH = "PATH";
    
    public static final String DOMAIN = "DOMAIN";
    
    public static final String COOKIE_ = "COOKIE:";
    
    public static final String SET_COOKIE_ = "SET-COOKIE:";
    
    /** cookie的名称 */
    private final String name;
    
    /** cookie适用的路径 (域名后面跟的路径) */
    private final String path;
    
    /** cookie的作用域(域名) */
    private final String domain;
    
    /** cookie键对应的值 */
    private final String value;
    
    private Cookie(String name, String value, String path, String domain) {
        AssertUtils.notEmpty(name, "cookie'name is empty.");
        // AssertUtil.isNotBlank("不能传入空的cookie键对应的值", value);
        this.name = name;
        this.value = value;
        this.path = path;
        this.domain = domain;
    }
    
    private Cookie(String name, String value) {
        this.name = name;
        this.value = value;
        this.path = null;
        this.domain = null;
    }
    
    /**
     * 从发送response的header中的设置Cookie字符串来创建一个Cookie对象<br>
     * 此字符串是以"Set-Cookie:"开头的<br>
     * 如果传入的字符串为空,则返回null 如果传入的字符串不是以"Set-Cookie:"开头,则抛错<br>
     * 
     * @param cookie "Set-Cookie:"开头的Cookie的字符串
     * @return
     */
    public static Cookie newCookieFromResponse(String cookie) {
        if (StringUtils.isEmpty(cookie)) {
            return null;
        }
        cookie = cookie.trim();
        if (cookie.toUpperCase().startsWith(SET_COOKIE_)) {
            cookie = cookie.substring(11);
            String[] cookies = cookie.split(";");
            
            String cookieItem = cookies[0];
            int indexOf = cookieItem.indexOf('=');
            String name = cookieItem.substring(0, indexOf).trim();
            String value = cookieItem.substring(indexOf + 1).trim();
            String path = null;
            String domain = null;
            for (String cookiePar : cookies) {
                cookieItem = cookiePar.trim();
                indexOf = cookieItem.indexOf('=');
                String key = cookieItem.substring(0, indexOf);
                if (PATH.equals(key.toUpperCase())) {
                    path = cookieItem.substring(indexOf + 1).trim();
                    break;
                }
                if (DOMAIN.equals(key.toUpperCase())) {
                    domain = cookieItem.substring(indexOf + 1).trim();
                    break;
                }
            }
            return new Cookie(name, value, path, domain);
        } else {
            throw new HttpSocketException("不能传入错误的Cookie");
        }
    }
    
    /**
     * 从发送request的header中的设置Cookie字符串来创建一个Cookie对象<br>
     * 此字符串是以"Cookie:"开头的<br>
     * 如果传入的字符串为空,则返回null 如果传入的字符串不是以"Cookie:"开头,则抛错<br>
     * 
     * @param cookie "Cookie:"开头的Cookie的字符串
     * @return
     */
    public static Set<Cookie> newCookieFromRequest(String cookie) {
        if (StringUtils.isEmpty(cookie)) {
            return null;
        }
        
        if (cookie.toUpperCase().startsWith(COOKIE_)) {
            cookie = cookie.substring(7);
            String[] cookies = cookie.split(";");
            Set<Cookie> list = new HashSet<Cookie>();
            for (String cookieItem : cookies) {
                cookieItem = cookieItem.trim();
                if (!StringUtils.isEmpty(cookieItem)) {
                    int indexOf = cookieItem.indexOf('=');
                    String name = cookieItem.substring(0, indexOf).trim();
                    String value = cookieItem.substring(indexOf + 1).trim();
                    list.add(new Cookie(name, value));
                }
            }
            return list;
        } else {
            throw new HttpSocketException("不能传入错误的Cookie");
        }
    }
    
    /**
     * 从cookies字符串中创建cookie
     * 
     * @param name
     * @param value
     * @return
     */
    public static Cookie newCookieFromCookieStr(String name, String value) {
        return new Cookie(name, value);
    }
    
    /** 获得cookie的名字 */
    public String getName() {
        return name;
    }
    
    /** 获得cookie键对应的值 */
    public String getValue() {
        return value;
    }
    
    /** 获得cookie适用的路径 */
    public String getPath() {
        return path;
    }
    
    /** 获得cookie的作用域 */
    public String getDomain() {
        return domain;
    }
    
    /** 返回构造好的用来发送到服务器的Cookie字符串,包括最后的"\r\n",如果传入的值为空,则直接返回"" */
    public static String getCookie(Set<Cookie> cookies) {
        if (CollectionUtils.isEmpty(cookies)) {
            return "";
        }
        StringBuilder sb = new StringBuilder("Cookie: ");
        for (Cookie cookie : cookies) {
            sb.append(cookie.name)
                    .append('=')
                    .append(cookie.value)
                    .append("; ");
        }
        sb.append("\r\n");
        return sb.toString();
    }
    
    /** 返回cookie的key,value字符串 */
    public String toCookie() {
        return this.name + "=" + this.value + "; ";
    }
    
    @Override
    public String toString() {
        return toCookie();
    }
}
