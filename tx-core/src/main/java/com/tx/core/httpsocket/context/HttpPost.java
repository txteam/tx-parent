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
    
    private String defualtEncode; // 默认编码,如果为空,则不转码-url转码格式
    
    private String postEncode; // 字符集编码- post 字符串本身编码格式
    
    public HttpPost() {
        super();
    }
    
    public HttpPost(String defualtEncode, String postEncode) {
        super();
        this.defualtEncode = defualtEncode;
        this.postEncode = postEncode;
    }
    
    /** 直接添加post字符串 */
    public HttpPost add(String post) {
        this.post.append(post);
        return this;
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
    
    /** 获取默认编码 */
    public String getDefualtEncode() {
        return defualtEncode;
    }
    
    /** 获取post值 */
    public String getPost() {
        if (StringUtils.isBlank(post)) {
            return "";
        }
        
        if (post.charAt(0) == '&') {
            return this.post.deleteCharAt(0).toString();
        }
        return this.post.toString();
    }
    
    /** @return 返回 postEncode */
    public String getPostEncode() {
        return postEncode;
    }
    
    /** 设置默认编码 */
    public void setDefualtEncode(String defualtEncode) {
        this.defualtEncode = defualtEncode;
    }
    
    /** @param 对 postEncode 进行赋值 */
    public void setPostEncode(String postEncode) {
        this.postEncode = postEncode;
    }
    
    @Override
    public String toString() {
        return getPost();
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
}