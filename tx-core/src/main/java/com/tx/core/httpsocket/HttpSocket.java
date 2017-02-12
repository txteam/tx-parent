package com.tx.core.httpsocket;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.core.exceptions.remote.HttpSocketException;
import com.tx.core.httpsocket.context.Cookie;
import com.tx.core.httpsocket.context.HttpPost;
import com.tx.core.httpsocket.context.HttpProxy;
import com.tx.core.httpsocket.context.HttpUrl;
import com.tx.core.httpsocket.context.HttpWebUrl;
import com.tx.core.httpsocket.context.Request;
import com.tx.core.httpsocket.context.Response;
import com.tx.core.httpsocket.context.responseheader.ResponseStatus;

/**
 * 提供对Http协议的发送和接收<br />
 * 只支持http代理模式
 * 
 * @author Rain
 * 
 */
public class HttpSocket implements Serializable {
    private static final long serialVersionUID = -9216646912368723831L;
    
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(HttpSocket.class);
    
    /** http发送头信息 */
    private Request request = null;
    
    /** 连接超时时间,默认5秒 */
    private int timeout = 5 * 1000; //
    
    /** 链接是否空闲中 */
    private boolean isConnectionFree = true;
    
    /** cookies */
    private Map<String, Cookie> cookies = new HashMap<String, Cookie>();
    
    /** 是否持有返回的cookies,如果持有,则每次返回时自动获取,发送时自动发送 */
    private boolean isHoldCookies = true;
    
    public HttpSocket() {
        this.request = Request.newRequestByEmpty();
    }
    
    public HttpSocket(boolean isHoldCookies) {
        this.isHoldCookies = isHoldCookies;
        this.request = Request.newRequestByEmpty();
    }
    
    // **-----------about new HttpSocket functions-----------**//
    
    /**
     * 根据发送头信息创建HttpSocket
     * 
     * @param holdCookies 是否保持cookies
     */
    public static HttpSocket newHttpSocket(boolean holdCookies,
            HttpProxy httpProxy) {
        return new HttpSocket(holdCookies).setProxy(httpProxy);
    }
    
    // **-----------about send functions-----------**//
    
    /**
     * 发送请求
     * 
     * @param httpUrl
     * @return
     */
    public Response send(String httpUrl) throws HttpSocketException {
        return send(Request.newRequestByGet(HttpUrl.newInstance(httpUrl),
                request.getHttpProxy()));
    }
    
    /**
     * 发送请求
     * 
     * @param httpWebUrl
     * @return
     */
    public Response send(HttpWebUrl httpWebUrl)
            throws HttpSocketException {
        return send(Request.newRequestByGet(HttpUrl.newInstance(httpWebUrl),
                request.getHttpProxy()));
    }
    
    /**
     * 发送请求
     * 
     * @param httpUrl
     * @param cookies
     * @param headers
     * @return
     */
    public Response send(String httpUrl, Map<String, Cookie> cookies,
            Map<String, String> headers) throws HttpSocketException {
        return send(Request.newRequestByGet(HttpUrl.newInstance(httpUrl),
                request.getHttpProxy())
                .putCookies(cookies)
                .putAllHeader(headers));
    }
    
    /**
     * 发送请求
     * 
     * @param httpUrl
     * @param post
     * @return
     */
    public Response send(String httpUrl, HttpPost post)
            throws HttpSocketException {
        return send(Request.newRequestByPost(HttpUrl.newInstance(httpUrl),
                post,
                request.getHttpProxy()));
    }
    
    /**
     * 发送请求
     * 
     * @param httpWebUrl
     * @param post
     * @return
     */
    public Response send(HttpWebUrl httpWebUrl, HttpPost post)
            throws HttpSocketException {
        return send(Request.newRequestByPost(HttpUrl.newInstance(httpWebUrl),
                post,
                request.getHttpProxy()));
    }
    
    /**
     * 发送请求
     * 
     * @param httpUrl
     * @param post
     * @param cookies
     * @param headers
     * @return
     * @throws NetworkIOReadErrorException 网络IO读取错误
     */
    public Response send(String httpUrl, HttpPost post,
            Map<String, Cookie> cookies, Map<String, String> headers)
            throws HttpSocketException {
        return send(Request.newRequestByPost(HttpUrl.newInstance(httpUrl),
                post,
                request.getHttpProxy())
                .putCookies(cookies)
                .putAllHeader(headers));
    }
    
    /**
     * 上传文件<br />
     * 部分实现 RFC1867协议
     * 
     * @param httpUrl 请求地址
     * @param file 文件
     * @param pars 参数
     * @param parName 文件参数name
     * @param fileName 文件名
     * 
     * @return Response
     * @throws NetworkIOReadErrorException 网络IO读取错误
     * @throws IOException 文件IO读取错误
     */
    public Response send(String httpUrl, File file,
            Map<String, String> pars, String parName, String fileName)
            throws HttpSocketException {
        return send(Request.newRequestByUpFile(HttpUrl.newInstance(httpUrl),
                file,
                request.getHttpProxy(),
                pars,
                parName,
                fileName));
    }
    
    /**
     * 发送请求<br />
     * 如果调用地方法来发送请求,请自己处理代理设置
     * 
     * @throws NetworkIOReadErrorException 网络IO读取错误
     */
    public synchronized Response send(Request request)
            throws HttpSocketException {
        this.request = request;
        return send();
    }
    
    /**
     * 发送请求(此方法是阻塞式方法)
     * 
     * @return 返回报文头
     * @throws NetworkIOReadErrorException 网络IO读取错误
     */
    public synchronized Response send() throws HttpSocketException {
        this.isConnectionFree = false;
        Response response = Response.newResponseByEmpty();
        Socket socket = null;
        InputStream inputStream = null;
        try {
            // 开始发送数据
            // 如果保持cookies,则添加保持着的cookies
            if (this.isHoldCookies) {
                this.request.putCookies(this.cookies);
            }
            
            // 发送命令,同时返回"返回报文"的流通道
            socket = this.request.send(this.timeout);
            
            // 解析返回的报文
            inputStream = socket.getInputStream();
            response.resolveResponse(inputStream);
            
            // 如果保持cookies,则从返回头中提取出cookies保存起来
            if (this.isHoldCookies) {
                this.cookies.putAll(response.getCookies());
            }
            
            ResponseStatus status = response.getStatus();
            if (!ResponseStatus.s200.equals(status)) {
                logger.warn("返回非正常的报文{}", status.toString());
            }
        } catch (SocketTimeoutException ste) {
            throw new HttpSocketException("获取数据超时!");
        } catch (UnknownHostException uhe) {
            throw new HttpSocketException("不可识别的主机地址 : ");
        } catch (IOException e) {
            throw new HttpSocketException("未知的网络错误!  当前request请求头 : \r\n");
        } finally {
            // 已经发送完成
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(socket);
            this.isConnectionFree = true;
        }
        return response;
    }
    
    // **-----------setter and getter-----------**//
    
    /** 获得发送Request的头信息 */
    public Request getRequest() {
        return this.request;
    }
    
    /**
     * 设置Request的头信息<br />
     * 如果调用地方法来发送请求,请自己处理代理设置
     */
    public HttpSocket setRequest(Request request) {
        this.request = request;
        return this;
    }
    
    /**
     * @return 返回 是否保持cookies
     */
    public boolean isHoldCookies() {
        return isHoldCookies;
    }
    
    /**
     * @param 对holdCookies进行赋值
     */
    public HttpSocket setHoldCookies(boolean holdCookies) {
        this.isHoldCookies = holdCookies;
        return this;
    }
    
    /** 获取代理设置 */
    public HttpProxy getProxy() {
        return request.getHttpProxy();
    }
    
    /** 设置代理 */
    public HttpSocket setProxy(HttpProxy httpProxy) {
        request.setHttpProxy(httpProxy);
        return this;
    }
    
    /** 清空代理 */
    public HttpSocket clearProxy() {
        request.clearProxy();
        return this;
    }
    
    /**
     * @return the timeout
     */
    public int getTimeout() {
        return timeout;
    }
    
    /**
     * 默认5*1000毫秒
     * 
     * @param timeout the timeout to set(单位 毫秒)
     */
    public HttpSocket setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }
    
    /**
     * @return the cookies
     */
    public Map<String, Cookie> getCookies() {
        return cookies;
    }
    
    /**
     * @param cookies the cookies to set
     */
    public HttpSocket setCookies(Map<String, Cookie> cookies) {
        this.cookies = cookies;
        return this;
    }
    
    /** 获取指定的Cookie */
    public Cookie getCookie(String key) {
        return this.cookies.get(key);
    }
    
    /** 链接是否空闲 */
    public boolean isConnectionFree() {
        return isConnectionFree;
    }
    
    /** 清空cookies */
    public void clearCookies() {
        this.cookies.clear();
    }
    
    @Override
    public String toString() {
        return "HttpSocket [request=" + request
                + ", isHoldCookies=" + isHoldCookies + "]";
    }
}
