package com.tx.core.httpsocket.context.responseheader;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.httpsocket.exception.HttpSocketException;

/**
 * 返回时的状态
 * 
 * @author Rain
 * 
 */
public enum ResponseStatus {
    s200("200", "OK", "客户端请求成功，正常返回"), // 200
    s304("304", "Not Modified", "没有修改,缓存中"), // 304
    s302("302", "Moved Temporatily", "被请求的资源暂时转移"), // 302
    s400("400", "Bad Request", "客户端请求有语法错误，不能被服务器所理解"), // 400
    s401("401", "Unauthorized", "请求未经授权，这个状态代码必须和WWW-Authenticate报头域一起使用 "), // 401
    s403("403", "Forbidden", "服务器收到请求，但是拒绝提供服务"), // 403
    s404("404", "Not Found", "没有找到资源"), // 404
    s500("500", "Internal Server Error", "服务器发生不可预期的错误"), // 500
    s501("501", "Not implemented or not supported.",
            "Web 服务器不支持实现此请求所需的功能。请检查URL 中的错误，如果问题依然存在，请与 Web服务器的管理员联系。"), s502(
            "502", "Bad Gateway", "服务器作为网关或代理，从上游服务器收到无效响应。 "), // 502
    s503("503", "Server Unavailable", "服务器目前无法使用（由于超载或停机维护）。 通常，这只是暂时状态。"), // 503
    s504("504", "Gateway Timeout", "服务器作为网关或代理，但是没有及时从上游服务器收到请求。"), // 504
    ;
    
    /** 状态编码 */
    private String code;
    
    /** 状态内容 */
    private String context;
    
    /** 状态说明 */
    private String caption;
    
    public String getCode() {
        return this.code;
    }
    
    public String getContext() {
        return this.context;
    }
    
    public String getCaption() {
        return this.caption;
    }
    
    ResponseStatus(String code, String context, String caption) {
        this.code = code;
        this.context = context;
        this.caption = caption;
    }
    
    /** 根据状态字符串返回状态,如果找不到则返回空 */
    public static ResponseStatus getResponseStatus(String status) {
        AssertUtils.notEmpty(status, "status is empty.");
        for (ResponseStatus rs : values()) {
            if (rs.getCode().equals(status)) {
                return rs;
            }
        }
        throw new HttpSocketException("传入了未知的返回状态:[" + status + "]");
    }
    
    @Override
    public String toString() {
        return MessageFormatter.arrayFormat("{} : {}({})",
                new String[] { this.code, this.context,
                        StringUtils.abbreviate(this.caption, 10) })
                .getMessage();
    }
}
