/**
 * 
 */
package com.tx.core.httpsocket.context;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;

/**
 * Post构装类<br/>
 * 默认字符集不进行编码
 * 
 * @author oky
 * 
 */
public class HttpPost {
    private StringBuilder post = new StringBuilder();
    private String defualtEncode; // 默认编码,如果为空,则不转码

    public HttpPost() {
        super();
    }

    public HttpPost(String defualtEncode) {
        super();
        this.defualtEncode = defualtEncode;
    }

    /** 如果encode为空,则不转码 */
    public HttpPost(String post, String encode) {
        super();
        this.post.append('&').append(encode(post, encode));
    }

    /** 添加post值,默认编码 */
    public HttpPost add(String key, String value) {
        add(key, value, defualtEncode);
        return this;
    }

    /** 添加post值,如果encode为空,则不转码 */
    public HttpPost add(String key, String value, String encode) {
        post.append('&').append(key).append('=').append(encode(value, encode));
        return this;
    }

    /** 直接添加post字符串 */
    public HttpPost add(String post) {
        this.post.append(post);
        return this;
    }

    /** 获取post值 */
    public String getPost() {
        if (StringUtils.isBlank(post)) {
            return "";
        }

        return this.post.deleteCharAt(0).toString();
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
        return getPost();
    }
}