package com.tx.core.httpsocket.context;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Proxy;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.httpsocket.context.responseheader.RequestUrlType;
import com.tx.core.httpsocket.context.responseheader.ResponseContentTypeCode;
import com.tx.core.httpsocket.exception.HttpSocketException;
import com.tx.core.util.RandomUtils;

/**
 * 请求头信息<br />
 * 只支持http代理模式
 * 
 * @author Rain
 * 
 */
public class RequestHeader implements Serializable {
    private static final long serialVersionUID = -8999621270754679019L;
    
    private static final String BOUNDARY = "--hiyouyu--rain--"
            + RandomUtils.randomRangeString(12) + "--"; // 上传时的文件分隔符(7d是ie
                                                  // 71是firfox)
    
    /** 请求地址类型,默认为GET */
    private RequestUrlType urlType = RequestUrlType.GET;
    
    /** 请求路径(GET或者POST请求的路径) */
    private HttpUrl httpUrl = null;
    
    /** 代理设置 */
    private HttpProxy httpProxy = null;
    
    /** 请求类型,默认为HTTP/1.1 */
    private HttpType httpType = HttpType.HTTP_1_1;
    
    /** 请求的headers */
    private Map<String, String> headers = new HashMap<String, String>();
    
    /** cookies */
    private Map<String, Cookie> cookies = new HashMap<String, Cookie>();
    
    // / //POST数据
    /** ---POST--- 如果发送类型为POST,则此字段保存post数据 */
    private byte[] post = null;
    
    private RequestHeader() {
        headers.put("Accept", "*/*");
        headers.put("Accept-Language", "zh-cn");
        headers.put("UA-CPU", "x86");
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; .NET CLR 2.0.50727; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
        headers.put("Connection", "close");
    }
    
    /** 新建一个使用空白信息的RequestHeader */
    public static RequestHeader newRequestHeaderByEmpty() {
        return new RequestHeader();
    }
    
    /**
     * 新建一个使用GET提交的RequestHeader
     * 
     * @param httpUrl 请求路径
     * @param httpProxy 代理设置
     * 
     * @return
     */
    public static RequestHeader newRequestHeaderByGet(HttpUrl httpUrl,
            HttpProxy httpProxy) {
        return RequestHeader.newRequestHeaderByEmpty()
                .setGet(httpUrl)
                .setHttpProxy(httpProxy);
    }
    
    /**
     * 新建一个使用POST提交的RequestHeader<br>
     * 
     * @param httpUrl 请求路径
     * @param post 提交的数据
     * @param httpProxy 代理设置
     * 
     * @return
     */
    public static RequestHeader newRequestHeaderByPost(HttpUrl httpUrl,
            HttpPost post, HttpProxy httpProxy) {
        return RequestHeader.newRequestHeaderByEmpty()
                .setPost(httpUrl, post)
                .setHttpProxy(httpProxy);
    }
    
    /**
     * 新建一个发送(上传)文件的RequestHeader
     * 
     * @param httpUrl 请求路径
     * @param upFile 需要发送的文件
     * @param httpProxy 代理设置
     * @param upFilePars 参数
     * @param parName 上传时确定文件的参数名,如果为null,则自动取文件名
     * @param fileName 上传时确定文件的文件名,如果为null,则自动取文件路径
     * @return
     * @throws IOException 读取文件失败时抛出此异常
     */
    public static RequestHeader newRequestHeaderByUpFile(HttpUrl httpUrl,
            File upFile, HttpProxy httpProxy, Map<String, String> upFilePars,
            String parName, String fileName) {
        return RequestHeader.newRequestHeaderByEmpty()
                .setUpFile(httpUrl, upFile, upFilePars, parName, fileName)
                .setHttpProxy(httpProxy);
    }
    
    /**
     * 设置上传文件
     * 
     * 
     * @param httpUrl 请求路径
     * @param upFile 需要发送的文件
     * @param upFilePars 参数
     * @param parName 上传时确定文件的参数名,如果为null,则自动取文件名
     * @param fileName 上传时确定文件的文件名,如果为null,则自动取文件路径
     * @return
     * @throws IOException 读取文件失败时抛出此异常
     */
    public RequestHeader setUpFile(HttpUrl httpUrl, File upFile,
            Map<String, String> upFilePars, String parName, String fileName) {
        this.httpUrl = httpUrl;
        this.urlType = RequestUrlType.POST;
        this.post = createContentDisposition(upFile,
                upFilePars,
                parName,
                fileName);
        
        headers.put("Content-Length", String.valueOf(post.length));
        headers.put("Content-Type", "multipart/form-data; boundary="
                + RequestHeader.BOUNDARY); // post必须添加的标识-上传
        return this;
    }
    
    /** 设置RequestHeader中GET值 */
    public RequestHeader setGet(HttpUrl httpUrl) {
        this.httpUrl = httpUrl;
        this.urlType = RequestUrlType.GET;
        headers.remove("Content-Type");
        return this;
    }
    
    /**
     * 设置Post数据
     * 
     * @param httpUrl 请求路径,如果为null,则自动设置为"/"
     * @param post post数据,0字节长度不算空
     * @param postSize post长度,如果此值为空,则直接取post数据的长度
     */
    public RequestHeader setPost(HttpUrl httpUrl, byte[] post, Integer postSize) {
        AssertUtils.notNull(post, "post is null.");
        if (postSize != null && postSize.intValue() < 0) {
            throw new HttpSocketException("不能传入负的post数据长度");
        }
        this.httpUrl = httpUrl;
        this.urlType = RequestUrlType.POST;
        this.post = post;
        headers.put("Content-Length",
                String.valueOf(postSize == null ? post.length
                        : postSize.intValue()));
        headers.put("Content-Type", "application/x-www-form-urlencoded"); // post必须添加的标识
        return this;
    }
    
    /**
     * 设置Post数据
     * 
     * @param httpUrl 请求路径
     * @param post post数据
     */
    public RequestHeader setPost(HttpUrl httpUrl, HttpPost post) {
        setPost(httpUrl, post.getPost().getBytes(), null);
        return this;
    }
    
    /** 增加请求的header信息,如果传入的键值为空,则直接返回 */
    public RequestHeader putHeader(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return this;
        }
        String keyUpper = key.toUpperCase();
        AssertUtils.isEq(keyUpper,
                "COOKIE",
                "这个方法不能设置Cookie的值,请调用addCookie(Cookie)方法来设置");
        if (RequestUrlType.GET.name().equals(keyUpper)
                || RequestUrlType.POST.name().equals(keyUpper)) {
            throw new HttpSocketException(
                    "这个方法不能设置GET和POST的值,请调用setAddress(String, AddressType)方法来设置");
        }
        headers.put(key, value);
        return this;
    }
    
    /** 增加请求的header信息,如果传入的值为空,则直接返回 */
    public RequestHeader putAllHeader(Map<String, String> headers) {
        if (MapUtils.isEmpty(headers)) {
            return this;
        }
        this.headers.putAll(headers);
        return this;
    }
    
    /**
     * 根据请求的键来获得对应的值
     * 
     * @param key 请求键,如果为空则返回""
     * @return
     */
    public String getHeader(String key) {
        if (StringUtils.isEmpty(key)) {
            return "";
        }
        if ("COOKIE".equals(key.toUpperCase())) {
            return getCookieValue();
        }
        return headers.get(key);
    }
    
    /** 判断某个header存不存在,如果存在则返回true,不存在返回false */
    public boolean containsKey(String key) {
        if (key != null && "COOKIE".equals(key.toUpperCase())) {
            return !MapUtils.isEmpty(cookies);
        }
        return headers.containsKey(key);
    }
    
    /** 设置请求数据的起止位置 */
    public RequestHeader setRange(Integer start, Integer end) {
        if (start != null && start.intValue() < 0) {
            throw new HttpSocketException("请求数据的起始位置不能小于0");
        }
        if (end != null && end.intValue() < 0) {
            throw new HttpSocketException("请求数据的结束位置不能小于0");
        }
        if (start != null && end != null && start.intValue() > end.intValue()) {
            throw new HttpSocketException("请求数据的起始位置不能大于结束位置");
        }
        headers.put("Range",
                "bytes=" + start == null ? ""
                        : String.valueOf(start.intValue()) + "-" + end == null ? ""
                                : String.valueOf(end.intValue()));
        return this;
    }
    
    /**
     * 设置请求的主机地址,可以是域名,也可以是ip<br>
     * 例如:<br>
     * http://www.baidu.com,截取出:www.baidu.com<br>
     * http://www.baidu.com:8080,截取出:www.baidu.com:8080<br>
     * http://www.baidu.com:80/index.html,截取出:www.baidu.com
     */
    public RequestHeader setHost(String host) {
        HttpUrl hu = HttpUrl.newInstance(host);
        httpUrl.setUrl(hu.getUrl());
        httpUrl.setHost(hu.getHost());
        httpUrl.setPort(hu.getPort());
        return this;
    }
    
    /** 获得主机地址 */
    public String getHost() {
        return this.httpUrl.getHost();
    }
    
    /** 获得主机端口号 */
    public int getPort() {
        return this.httpUrl.getPort();
    }
    
    /** 设置http发送协议的版本,如果为空则取默认的HTTP/1.1 */
    public RequestHeader setHttpType(HttpType httpType) {
        if (httpType == null) {
            this.httpType = HttpType.HTTP_1_1;
        } else {
            this.httpType = httpType;
        }
        return this;
    }
    
    /** 添加cookies,如果传入的值为空则直接返回 */
    public RequestHeader putCookies(Cookie cookie) {
        if (cookie == null) {
            return this;
        }
        this.cookies.put(cookie.getName(), cookie);
        return this;
    }
    
    /** 添加cookies,如果传入的参数为空,则直接返回 */
    public RequestHeader putCookies(Map<String, Cookie> cookies) {
        if (MapUtils.isEmpty(cookies)) {
            return this;
        }
        this.cookies.putAll(cookies);
        return this;
    }
    
    /** 返回Cookies */
    public Map<String, Cookie> getCookies() {
        return this.cookies;
    }
    
    /** 返回请求头信息,键值对形式 */
    public Map<String, String> getHeaders() {
        Map<String, String> copyHeaders = new HashMap<String, String>();
        copyHeaders.putAll(headers);
        copyHeaders.put("Cookie", getCookieValue());
        return copyHeaders;
    }
    
    /** 返回请求头信息,字符串形式 */
    public String getRequest() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.urlType.name())
                .append(' ')
                .append(this.httpUrl.getUrl())
                .append(' ')
                .append(httpType.getHttpType())
                .append("\r\n");
        for (String key : headers.keySet()) {
            sb.append(key).append(": ").append(headers.get(key)).append("\r\n");
        }
        if (containsKey("Cookie")) {
            sb.append("Cookie: ").append(getCookieValue()).append("\r\n");
        }
        sb.append("\r\n");
        
        return sb.toString();
    }
    
    /**
     * 发送报文,如果成功,则返回成功后的"返回报文"
     * 
     * @param timeout 超时时间
     * @return 返回报文的数据流
     * @throws IOException 链接失败,链接超时
     */
    public synchronized Socket send(int timeout) throws IOException {
        headers.put("Host", this.httpUrl.getHttpHost());
        
        checkRequest();
        Socket socket = new Socket(getConnectHost(), getConnectPort());
        socket.setSoTimeout(timeout); // 毫秒为单位
        
        // 这里不能关闭输出流.因为关闭输出流,就同时关闭输入流.后面就不能获取到返回的报文
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(getRequest().getBytes());
        
        if (ArrayUtils.isNotEmpty(this.post)) {
            outputStream.write(this.post);
        }
        
        outputStream.flush();
        return socket;
    }
    
    /** 返回代理设置 */
    public HttpProxy getHttpProxy() {
        return httpProxy;
    }
    
    /** 设置代理 */
    public RequestHeader setHttpProxy(HttpProxy httpProxy) {
        this.httpProxy = httpProxy;
        return this;
    }
    
    /** 清空代理 */
    public RequestHeader clearProxy() {
        this.httpProxy = null;
        return this;
    }
    
    @Override
    public String toString() {
        return "RequestHeader [urlType=" + urlType + ", url="
                + (httpUrl == null ? "" : httpUrl.getUrl()) + "]";
    }
    
    /**
     * 根据上传的参数创建Content-Disposition字符串
     * 
     * @throws IOException 读取文件失败时抛出此异常
     */
    private byte[] createContentDisposition(File file,
            Map<String, String> upFilePars, String parName, String fileName) {
        StringBuilder sb = new StringBuilder();
        if (MapUtils.isNotEmpty(upFilePars)) {
            for (Map.Entry<String, String> entry : upFilePars.entrySet()) {
                sb.append("--").append(RequestHeader.BOUNDARY).append("\r\n");
                sb.append("Content-Disposition: form-data; name=\"")
                        .append(entry.getKey())
                        .append("\"")
                        .append("\r\n");
                sb.append("\r\n");
                sb.append(entry.getValue()).append("\r\n");
            }
        }
        if (StringUtils.isBlank(parName)) {
            parName = file.getName();
        }
        if (StringUtils.isBlank(fileName)) {
            fileName = file.getPath();
        }
        sb.append("--").append(RequestHeader.BOUNDARY).append("\r\n");
        sb.append("Content-Disposition: form-data; name=\"")
                .append(parName)
                .append("\"; filename=\"")
                .append(fileName)
                .append("\"")
                .append("\r\n");
        sb.append("Content-Type: ")
                .append(ResponseContentTypeCode.application_octet_stream.getContentTypeCode())
                .append("\r\n");
        sb.append("\r\n");
        try {
            byte[] pre = sb.toString().getBytes();
            byte[] files = FileUtils.readFileToByteArray(file);
            byte[] last = ("\r\n--" + RequestHeader.BOUNDARY + "--\r\n").getBytes();
            return ArrayUtils.addAll(ArrayUtils.addAll(pre, files), last);
        } catch (IOException e) {
            throw new HttpSocketException("上传文件读取异常!", e);
        }
    }
    
    /**
     * 获取socket链接的主机名<br />
     * 如果有代理设置则链接代理
     */
    private String getConnectHost() {
        if (this.httpProxy == null) {
            return getHost();
        } else {
            return this.httpProxy.getProxyHostName();
        }
    }
    
    /**
     * 获取socket链接的端口<br />
     * 如果有代理设置则链接代理
     */
    private int getConnectPort() {
        if (this.httpProxy == null) {
            return getPort();
        } else {
            return this.httpProxy.getProxyPort();
        }
    }
    
    /** 返回cookies的字符串表达形式,"Cookie: "后面部分 */
    private String getCookieValue() {
        StringBuilder sb = new StringBuilder();
        for (Cookie cookie : cookies.values()) {
            sb.append(cookie.toCookie());
        }
        return sb.toString();
    }
    
    /** 校验headers的正确性,如果不能够进行Socket的发送则抛出错误 */
    private void checkRequest() {
        if (!containsKey("Host")) {
            throw new NullPointerException("headers里面没有包含Host,不能发送");
        }
        
        if (this.httpProxy != null
                && !Proxy.Type.HTTP.equals(this.httpProxy.getProxyType())) {
            throw new IllegalArgumentException("只支持http代理模式");
        }
        
        if (StringUtils.isEmpty(httpUrl.getUrl())) {
            throw new NullPointerException("headers里面没有包含能发送的路径,不能发送");
        }
    }
    
    /** 返回Post数据 */
    public String getPost() {
        return new String(this.post);
    }
}
