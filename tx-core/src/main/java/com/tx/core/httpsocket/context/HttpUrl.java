/**
 * 描          述:  描述
 * 修  改   人:  oky
 * 修改时间:  2014-1-6
 * 修改描述:
 */
package com.tx.core.httpsocket.context;

import org.slf4j.helpers.MessageFormatter;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * url
 * 
 * @author oky
 * @version 版本号, 2014-1-6
 */
public class HttpUrl {
    /** 主机 */
    private String host;
    
    /** 地址 */
    private String url;
    
    /** 端口 */
    private int port = 80;
    
    /** 默认构造函数 */
    private HttpUrl() {
        super();
    }
    
    /** 默认构造函数 */
    private HttpUrl(String httpUrl) {
        super();
        AssertUtils.notEmpty(httpUrl, "httpUrl is empty.");
        detachHttpUrl(httpUrl);
    }
    
    /** 根据传入的URL实例化一个HttpUrl */
    public static HttpUrl newInstance(String url) {
        return new HttpUrl(url);
    }
    
    /** 根据传入的URL实例化一个HttpUrl */
    public static HttpUrl newInstance(String url, int port) {
        HttpUrl hu = new HttpUrl(url);
        hu.port = port;
        return hu;
    }
    
    /** 根据传入的URL实例化一个HttpUrl */
    public static HttpUrl newInstance(HttpWebUrl url) {
        return new HttpUrl(url.getUrl());
    }
    
    /** 解析httpurl */
    private void detachHttpUrl(String httpUrl) {
        String tempHttpUrl = httpUrl.trim();
        if (tempHttpUrl.toUpperCase().startsWith("HTTPS://")) {
            // tempHttpUrl = tempHttpUrl.substring(8);
            throw new NullPointerException("暂时不支持https协议");
        }
        
        if (tempHttpUrl.toUpperCase().startsWith("HTTP://")) {
            tempHttpUrl = tempHttpUrl.substring(7);
        }
        
        int token = tempHttpUrl.indexOf("/");
        String host = tempHttpUrl;
        String url = "/";
        if (token > 0) {
            host = tempHttpUrl.substring(0, token);
            url = tempHttpUrl.substring(token);
        }
        
        token = host.indexOf(":");
        String port = "80";
        if (token > 0) {
            port = host.substring(token + 1, host.length());
            host = host.substring(0, token);
        }
        
        this.host = host;
        this.url = url;
        this.port = Integer.valueOf(port).intValue();
    }
    
    /** 返回主机地址 */
    public String getHost() {
        return host;
    }
    
    /** 返回url */
    public String getUrl() {
        return url;
    }
    
    /** 返回访问端口 */
    public int getPort() {
        return port;
    }
    
    /** 设置主机地址 */
    public void setHost(String host) {
        this.host = host;
    }
    
    /** 设置url */
    public void setUrl(String url) {
        this.url = url;
    }
    
    /** 设置访问端口 */
    public void setPort(int port) {
        this.port = port;
    }
    
    /** 返回http协议中的host值 */
    public String getHttpHost() {
        if (this.port == 80) {
            return this.host;
        } else {
            return this.host + ":" + this.port;
        }
    }
    
    @Override
    public String toString() {
        return MessageFormatter.arrayFormat("HttpUrl [{}:{}/{}]",
                new String[] { host, String.valueOf(port), url }).getMessage();
    }
    
}
