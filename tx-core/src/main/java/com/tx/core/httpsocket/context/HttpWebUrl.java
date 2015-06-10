/**
 * 
 */
package com.tx.core.httpsocket.context;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;

/**
 * URL构装类
 * 
 * @author rain
 *
 */
public class HttpWebUrl {
    /** url */
    private StringBuilder url = new StringBuilder();
    /** 默认编码,如果为空,则不转码 */
    private String defualtEncode;

    /**
     * 构造url
     * 
     * @param urladdr
     *            url地址部分(不包括'?'后面的参数)
     */
    public HttpWebUrl(String urladdr) {
        super();
        this.url.append(urladdr);
        if (urladdr.charAt(urladdr.length() - 1) != '?') {
            this.url.append('?');
        }
    }

    /**
     * 构造WebUrl
     * 
     * @param urladdr
     *            url地址部分(不包括'?'后面的参数)
     * @param encode
     *            编码 如果encode为空,则不转码
     */
    public HttpWebUrl(String urladdr, String encode) {
        this(urladdr);
        this.defualtEncode = encode;
    }

    /**
     * 添加urlparam值,默认编码 会自动在 urlparam 后面增加一个字符'&'
     * 
     * @param key
     *            urlparam键
     * @param value
     *            urlparam值
     * @return HttpWebUrl 对本身的引用
     */
    public HttpWebUrl add(String key, String value) {
        add(key, value, defualtEncode);
        return this;
    }

    /**
     * 添加urlparam值,如果encode为空,则不转码 会自动在 urlparam 后面增加一个字符'&'
     * 
     * @param key
     *            urlparam键
     * @param value
     *            urlparam值
     * @param encode
     *            编码 如果encode为空,则不转码
     * @return HttpWebUrl 对本身的引用
     */
    public HttpWebUrl add(String key, String value, String encode) {
        this.url.append(key).append('=').append(encode(value, encode)).append('&');
        return this;
    }

    /**
     * 直接添加urlparam字符串<br/>
     * 会自动在 urlparam 后面增加一个字符'&'
     * 
     * @param urlparam
     *            urlparam键值对
     * @return HttpWebUrl 对本身的引用
     */
    public HttpWebUrl add(String urlparam) {
        this.url.append(urlparam).append('&');
        return this;
    }

    /** 获取url值 */
    public String getUrl() {
        if (StringUtils.isBlank(this.url)) {
            return "";
        }
        if (this.url.charAt(this.url.length() - 1) == '&') {
            this.url.deleteCharAt(this.url.length() - 1);
        }
        return this.url.toString();
    }

    /** 获取默认编码 */
    public String getDefualtEncode() {
        return defualtEncode;
    }

    /** 设置默认编码 */
    public void setDefualtEncode(String defualtEncode) {
        this.defualtEncode = defualtEncode;
    }

    /**
     * 转码<br />
     * 
     * @param value
     * @param encode
     *            如果为空,则不转码,直接返回
     * @return
     */
    private String encode(String value, String encode) {
        if (StringUtils.isBlank(value)) {
            return "";
        }
        if (StringUtils.isNotBlank(encode)) {
            try {
                value = URLEncoder.encode(value, encode);
            } catch (UnsupportedEncodingException e) {
            }
        }
        return value;
    }

    @Override
    public String toString() {
        return getUrl();
    }
}
