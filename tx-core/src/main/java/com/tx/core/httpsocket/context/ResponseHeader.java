package com.tx.core.httpsocket.context;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import javax.imageio.ImageIO;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.tx.core.httpsocket.context.responseheader.ResponseContentEncoding;
import com.tx.core.httpsocket.context.responseheader.ResponseContentType;
import com.tx.core.httpsocket.context.responseheader.ResponseDataType;
import com.tx.core.httpsocket.context.responseheader.ResponseStatus;
import com.tx.core.httpsocket.exception.HttpSocketException;

/**
 * 返回的消息报头<br>
 * 由三部分组成，分别为：状态行、消息报头、响应正文<br>
 * 
 * @author Rain
 * 
 */
public class ResponseHeader implements Serializable {
    private static final long serialVersionUID = 2212952560121701011L;
    
    /** 返回http协议版本,默认为HTTP/1.1 */
    private HttpType httpType = HttpType.HTTP_1_1;
    
    /** 返回的状态 */
    private ResponseStatus status;
    
    /** 请求的headers */
    private Map<String, String> headers = new HashMap<String, String>();
    
    /** cookies */
    private Map<String, Cookie> cookies = new HashMap<String, Cookie>();
    
    /** 返回的数据 */
    private byte[] responseBody = null;
    
    private ResponseHeader() {
    }
    
    /** 新建一个使用空白信息的RequestHeader */
    public static ResponseHeader newResponseHeaderByEmpty() {
        return new ResponseHeader();
    }
    
    /** 设置返回的地址类型,如果传入空则设置为默认的HTTP/1.1 */
    private ResponseHeader setHttpType(String httpType) {
        if (StringUtils.isEmpty(httpType)) {
            this.httpType = HttpType.HTTP_1_1;
        } else {
            this.httpType = HttpType.getHttpType(httpType);
        }
        return this;
    }
    
    /** 获得返回的http协议版本 */
    public HttpType getHttpType() {
        return this.httpType;
    }
    
    /** 设置返回的状态 */
    private ResponseHeader setStatus(String status) {
        this.status = ResponseStatus.getResponseStatus(status);
        return this;
    }
    
    /** 获得返回的状态 */
    public ResponseStatus getStatus() {
        return this.status;
    }
    
    /** 设置返回Header,如果传入空则直接返回 */
    public ResponseHeader putHeader(String header) {
        if (StringUtils.isEmpty(header)) {
            return this;
        }
        
        if (header.toUpperCase().startsWith("HTTP")) { // 第一行
            setHttpType(header.substring(0, 8));
            setStatus(header.substring(9, 12));
            return this;
        }
        
        if (header.toUpperCase().startsWith("SET-COOKIE")) { // 如果是设置Cookie
            putCookie(Cookie.newCookieFromResponse(header));
            return this;
        }
        
        int indexOf = header.indexOf(':');
        if (indexOf > 0) { // 只有在大于0的情况下,才是正确的header头信息
            String key = header.substring(0, indexOf).trim();
            String value = header.substring(indexOf + 1).trim();
            headers.put(key, value);
        }
        return this;
    }
    
    /** 添加Cookie,如果传入的值为空则直接返回 */
    public ResponseHeader putCookie(Cookie cookie) {
        if (cookie == null) {
            return this;
        }
        this.cookies.put(cookie.getName(), cookie);
        return this;
    }
    
    /** 添加cookies,如果传入的参数为空,则直接返回 */
    public ResponseHeader addAllCookie(Map<String, Cookie> cookies) {
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
    
    /** 设置返回体 */
    public void setBody(byte[] bytes) {
        this.responseBody = bytes;
    }
    
    /** 获取返回的原始字符数组 */
    public byte[] getBody() {
        if (isChunked()) {
            return decodeChunked(this.responseBody);
        } else {
            return this.responseBody;
        }
    }
    
    /**
     * 获取返回的文本,自动根据返回的编码类型解码,如果没有设置编码类型,则自动按照gb2312来解码
     * 
     * @return Response 返回的字符串
     * @throws IOException 获取response字符串失败时 or 获取response字符串是突然断开链接时
     */
    public String bodyToString() {
        return bodyToString(null);
    }
    
    /**
     * 获得Response返回的字符串
     * 
     * @param encoding 返回内容的编码<br>
     *            如果为空且Content-Type中也没有指定编码则取默认编码:gb2312<br>
     *            如果为空且Content-Type中有编码则取Content-Type中的编码<br>
     *            如果不为空则强制指定传入的编码<br>
     * @return response 返回的字符串
     */
    public String bodyToString(String encoding) {
        ResponseContentType contentType = getContentType(encoding);
        ResponseDataType responseDataType = contentType.getResponseContentTypeCode()
                .getResponseDataType();
        if (!ResponseDataType.文本.equals(responseDataType)) {
            throw new HttpSocketException("返回状态 : " + getStatus().toString()
                    + "  Response返回的Body体不是[" + ResponseDataType.文本.name()
                    + "]类型,是[" + responseDataType.name() + "]类型");
        }
        
        InputStream in = bodyToInputStream();
        try {
            return IOUtils.toString(in, contentType.getEncoding());
        } catch (IOException e1) {
            throw new HttpSocketException("从Response返回的body流转换成文件时流IO转换错误", e1);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
    
    /**
     * 获得Response返回的图片
     * 
     * @return BufferedImage 图片
     * @throws IOException 获取图片失败时 or 获取图片是突然断开链接时
     */
    public BufferedImage bodyToImage() {
        ResponseContentType contentType = getContentType(null);
        ResponseDataType responseDataType = contentType.getResponseContentTypeCode()
                .getResponseDataType();
        if (!ResponseDataType.图片.equals(responseDataType)
                && !ResponseDataType.二进制流.equals(responseDataType)) {
            throw new HttpSocketException(" " + getStatus().toString()
                    + "  Response返回的Body体是[" + responseDataType.name() + "]类型");
        }
        
        InputStream in = bodyToInputStream();
        try {
            return ImageIO.read(in);
        } catch (IOException e1) {
            throw new HttpSocketException("从Response返回的body流转换成文件时流IO转换错误", e1);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
    
    /**
     * 获得Response返回的文件
     * 
     * @return File 文件
     * @throws IOException 获取文件失败时 or 获取文件是突然断开链接时
     */
    public File bodyToFile(String fileName) {
        File file = new File(fileName);
        ResponseContentType contentType = getContentType(null);
        ResponseDataType responseDataType = contentType.getResponseContentTypeCode()
                .getResponseDataType();
        if (!ResponseDataType.文件.equals(responseDataType)
                && !ResponseDataType.二进制流.equals(responseDataType)) {
            throw new HttpSocketException("返回状态 : " + getStatus().toString()
                    + "  Response的返回Body是[" + responseDataType.name() + "]");
        }
        InputStream in = bodyToInputStream();
        try {
            FileUtils.copyInputStreamToFile(in, file);
        } catch (IOException e1) {
            throw new HttpSocketException("从Response返回的body流转换成文件时流IO转换错误", e1);
        } finally {
            IOUtils.closeQuietly(in);
        }
        return file;
    }
    
    /**
     * 获得Response返回的文件
     * 
     * @return InputStream 文件流.请自己关闭此文件流
     * @throws IOException 获取文件失败时 or 获取文件是突然断开链接时
     */
    public InputStream bodyToInputStream() {
        ByteArrayInputStream in = null;
        ResponseContentEncoding contentEncoding = getContentEncoding();
        try {
            in = new ByteArrayInputStream(getBody());
            try {
                switch (contentEncoding) {
                    case gzip:
                        return new GZIPInputStream(in);
                    case deflate:
                        return in;
                    default:
                        throw new HttpSocketException("不能识别未知的压缩类型");
                }
            } catch (IOException e) {
                throw new HttpSocketException("从Response返回的body流转换成文件时流IO转换错误",
                        e);
            }
        } finally {
            // IOUtils.closeQuietly(in);
        }
    }
    
    /**
     * 返回内容的类型<br>
     * 此方法的返回值跟getContentType(String)方法传入null时一样<br>
     * Content-Type中有编码则取Content-Type中的编码,否则取默认编码gb2312<br>
     * 
     * @return
     */
    public ResponseContentType getContentType() {
        return getContentType(null);
    }
    
    /**
     * 返回内容的类型
     * 
     * @param encoding 返回类型的编码<br>
     *            如果为空且Content-Type中也没有指定编码则取默认编码:gb2312<br>
     *            如果为空且Content-Type中有编码则取Content-Type中的编码<br>
     *            如果不为空则强制指定传入的编码<br>
     * @return
     */
    public ResponseContentType getContentType(String encoding) {
        if (StringUtils.isEmpty(this.headers.get("Content-Type"))) {
            return ResponseContentType.newResponseContentType();
        }
        return ResponseContentType.newResponseContentTypeByHeader("Content-Type:"
                + this.headers.get("Content-Type"),
                encoding);
    }
    
    /** 返回内容的压缩类型,如果不存在则返回默认压缩类型ResponseContentEncoding.deflate */
    public ResponseContentEncoding getContentEncoding() {
        if (StringUtils.isEmpty(this.headers.get("Content-Encoding"))) {
            return ResponseContentEncoding.deflate;
        }
        return ResponseContentEncoding.getResponseContentEncoding(this.headers.get("Content-Encoding"));
    }
    
    /**
     * 返回内容的传输模式是否是分段传输
     * 
     * @return true:分段传输|false:完整传输
     */
    public boolean isChunked() {
        String transferEncoding = this.headers.get("Transfer-Encoding");
        if (StringUtils.isBlank(transferEncoding)) {
            return false;
        }
        return transferEncoding.equals("chunked");
    }
    
    /** 返回response头信息的字符串,如果还没有获得返回头,就直接返回"" */
    public String getResponse() {
        if (MapUtils.isEmpty(headers)) {
            return "";
        }
        StringBuilder header = new StringBuilder();
        header.append(httpType.getHttpType())
                .append(' ')
                .append(status.getCode())
                .append(' ')
                .append(status.getContext())
                .append("\r\n");
        for (Entry<String, String> entry : this.headers.entrySet()) {
            header.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("\r\n");
        }
        if (MapUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies.values()) {
                header.append("Set-Cookie: ")
                        .append(cookie.toCookie())
                        .append("\r\n");
            }
        }
        return header.toString();
    }
    
    /** 清空header信息 */
    public void clearResponseHeader() {
        this.cookies.clear();
        this.headers.clear();
        this.responseBody = null;
        this.httpType = HttpType.HTTP_1_1;
        this.status = null;
    }
    
    /**
     * 解析返回的报文.解析完毕会自动关闭传入的输入流
     * 
     * @param is 请求返回的输入流
     * @param isNeedBody 是否需要获取返回的body
     * @throws IOException 其它IO异常
     */
    public void resolveResponse(InputStream is) throws IOException {
        
        BufferedInputStream bis = null;
        ByteArrayOutputStream header = new ByteArrayOutputStream();
        ByteArrayOutputStream body = new ByteArrayOutputStream();
        
        try {
            // 接收返回的结果
            bis = new BufferedInputStream(is);
            byte[] byt = new byte[1];
            while (bis.read(byt) > 0) {
                header.write(byt);
                if (byt[0] == '\r') {
                    bis.read(byt);
                    header.write(byt);
                    if (byt[0] == '\n') {
                        bis.read(byt);
                        header.write(byt);
                        if (byt[0] == '\r') {
                            bis.read(byt);
                            header.write(byt);
                            if (byt[0] == '\n') {
                                break;
                            }
                        }
                    }
                }
            }
            header.flush();
            resolveResponseHeader(header.toByteArray());
            int length = 0;
            byte[] buf = new byte[512];
            while ((length = bis.read(buf)) > 0) {
                body.write(buf, 0, length);
            }
            body.flush();
            setBody(body.toByteArray());
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(header);
            IOUtils.closeQuietly(body);
        }
    }
    
    /** 解析返回头信息,如果传入数据为空,则直接返回 */
    private void resolveResponseHeader(byte[] bytes) throws IOException {
        if (ArrayUtils.isEmpty(bytes)) {
            return;
        }
        ByteArrayInputStream in = null;
        InputStreamReader ir = null;
        BufferedReader br = null;
        try {
            in = new ByteArrayInputStream(bytes);
            ir = new InputStreamReader(in, "UTF-8");
            br = new BufferedReader(ir);
            
            String line = "";
            while ((line = br.readLine()) != null) {
                putHeader(line + "\r\n");
            }
        } finally {
            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(ir);
            IOUtils.closeQuietly(in);
        }
    }
    
    /**
     * 解析分段传输字符串
     * 
     * @param responseBody
     * @return
     */
    private byte[] decodeChunked(byte[] bytes) {
        int size = bytes.length;
        ByteBuffer in = ByteBuffer.allocate(size);
        in.put(bytes);
        in.flip();
        int start = in.position();
        int end = in.limit();
        ByteBuffer content = ByteBuffer.allocate(size);
        while (true) { // 封包循环
            for (int i = start; i < end - 1; i++) {
                if (in.get(i) == '\r' && in.get(i + 1) == '\n') {
                    byte[] nums = new byte[i - start];
                    in.get(nums);
                    // 丢弃\r\n
                    in.get(new byte[2]);
                    int num = Integer.parseInt(new String(nums), 16);
                    byte[] strs = new byte[num];
                    in.get(strs);
                    content.put(strs);
                    // 丢弃\r\n
                    in.get(new byte[2]);
                    start = i + 4 + num;
                    break;
                }
            }
            
            if (in.get(start) == '0' && in.get(start + 1) == '\r'
                    && in.get(start + 2) == '\n' && in.get(start + 3) == '\r'
                    && in.get(start + 4) == '\n') {
                content.flip();
                in.get(new byte[5]);
                break;
            }
        }
        return content.array();
    }
}
