/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年7月2日
 * <修改描述:>
 */
package com.tx.core.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.tx.core.exceptions.remote.AfterHttpExcuteException;
import com.tx.core.exceptions.remote.BeforeHttpExcuteException;
import com.tx.core.exceptions.remote.HttpExcutingException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.starter.httpclient.HttpClientProperties;

/**
 * HttpClient实现<br/>
 *    参考代码： org.springframework.cloud.openfeign.FeignAutoConfiguration<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年7月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class HttpClientUtils {
    
    /** PoolingHttpClientConnectionManager */
    private static final PoolingHttpClientConnectionManager HTTP_CLIENT_CONNECTION_MANAGER;
    
    /** http client 工具类中链接管理计时器 */
    private static final Timer HTTP_CLIENT_CONNECTION_MANAGER_TIMER = new Timer(
            "HttpClientUtils.connectionManagerTimer", true);
    
    /** CloseableHttpClient */
    private static final CloseableHttpClient HTTP_CLIENT;
    
    static {
        //构造httpclient
        HttpClientProperties props = new HttpClientProperties();
        props.setConnectionTimeout(6000);
        props.setConnectionTimerRepeat(9000);
        
        Registry<ConnectionSocketFactory> registry = RegistryBuilder
                .<ConnectionSocketFactory> create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https",
                        SSLConnectionSocketFactory.getSocketFactory())
                .build();
        HTTP_CLIENT_CONNECTION_MANAGER = new PoolingHttpClientConnectionManager(
                registry, null, null, null, props.getTimeToLive(),
                props.getTimeToLiveUnit());
        HTTP_CLIENT_CONNECTION_MANAGER.setMaxTotal(props.getMaxConnections());
        HTTP_CLIENT_CONNECTION_MANAGER
                .setDefaultMaxPerRoute(props.getMaxConnectionsPerRoute());
        HTTP_CLIENT_CONNECTION_MANAGER_TIMER.schedule(new TimerTask() {
            @Override
            public void run() {
                HTTP_CLIENT_CONNECTION_MANAGER.closeExpiredConnections();
            }
        }, 30000, props.getConnectionTimerRepeat());
        
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(props.getConnectionTimeout())
                .setRedirectsEnabled(props.isFollowRedirects())
                .setConnectionRequestTimeout(props.getConnectionTimeout())
                .setSocketTimeout(props.getConnectionTimeout())
                .build();
        HTTP_CLIENT = HttpClientBuilder.create()
                .setConnectionManager(HTTP_CLIENT_CONNECTION_MANAGER)
                .setConnectionManagerShared(true)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }
    
    /**
     * 返回httpclient对象<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return CloseableHttpClient [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpclient = HTTP_CLIENT;
        return httpclient;
    }
    
    /**
     * 获取通用报文头<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Map<String,String> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static Map<String, String> commonHeaders() {
        //httppost.setHeader("accept", "*/*");
        //httppost.setHeader("connection", "Keep-Alive");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("accept", "*/*");
        headers.put("connection", "Keep-Alive");
        return null;
    }
    
    /**
     * 通过post发送请求报文<br/>
     * <功能详细描述>
     * @param url
     * @param msg
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String post(String url, String msg) {
        AssertUtils.notEmpty(url, "url is empty.");
        
        String result = post(url, msg, "UTF-8", "UTF-8");
        return result;
    }
    
    /**
     * 通过post发送请求报文<br/>
     * <功能详细描述>
     * @param url
     * @param msg
     * @param requestEncoding
     * @param responseEncoding
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String post(String url, String msg, String requestEncoding,
            String responseEncoding) {
        return post(url, msg, requestEncoding, responseEncoding, null);
    }
    
    /**
     * 通过post发送请求报文<br/>
     * <功能详细描述>
     * @param url
     * @param msg
     * @param requestEncoding
     * @param responseEncoding
     * @param headerMap
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String post(String url, String msg, String requestEncoding,
            String responseEncoding, Map<String, String> headerMap) {
        AssertUtils.notEmpty(url, "url is empty.");
        String result = "";
        
        HttpEntity requestEntity = null;
        try {
            if (StringUtils.isEmpty(requestEncoding)) {
                requestEntity = new StringEntity(msg);
            } else {
                requestEntity = new StringEntity(msg, requestEncoding);
            }
        } catch (UnsupportedCharsetException e) {
            throw new BeforeHttpExcuteException("Http请求参数字符集转换异常.", e);
        } catch (UnsupportedEncodingException e) {
            throw new BeforeHttpExcuteException("Http请求参数字符集转换异常.", e);
        }
        
        //可关闭的response
        CloseableHttpResponse response = null;
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        try {
            if (!MapUtils.isEmpty(headerMap)) {
                //httppost.setHeader("accept", "*/*");
                //httppost.setHeader("connection", "Keep-Alive");
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    if (StringUtils.isEmpty(entry.getKey())
                            || StringUtils.isEmpty(entry.getValue())) {
                        continue;
                    }
                    httppost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            httppost.setEntity(requestEntity);
            
            response = HTTP_CLIENT.execute(httppost);
            int statusCode = response.getStatusLine().getStatusCode();
            String reasonPhrase = response.getStatusLine().getReasonPhrase();
            if (200 != statusCode) {
                if (503 == statusCode) {
                    throw new HttpExcutingException(false, "Http请求返回失败.",
                            statusCode, reasonPhrase);
                } else {
                    throw new HttpExcutingException(true, "Http请求返回失败.",
                            statusCode, reasonPhrase);
                }
            }
            
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try {
                    if (StringUtils.isEmpty(responseEncoding)) {
                        result = EntityUtils.toString(entity);
                    } else {
                        result = EntityUtils.toString(entity, responseEncoding);
                    }
                } catch (ParseException e) {
                    throw new AfterHttpExcuteException("Http请求返回解析异常", e);
                } catch (IOException e) {
                    throw new AfterHttpExcuteException("Http请求返回解析异常", e);
                } finally {
                    //会自动释放连接
                    EntityUtils.consume(response.getEntity());
                }
            }
        } catch (ClientProtocolException e1) {
            throw new HttpExcutingException(false, "Http请求协议异常.", e1);
        } catch (ConnectException e1) {
            throw new HttpExcutingException(false, "Http请求IO流异常.", e1);
        } catch (ConnectTimeoutException e1) {
            throw new HttpExcutingException(false, "Http请求IO流异常.", e1);
        } catch (NoRouteToHostException e1) {
            throw new HttpExcutingException(false, "Http请求IO流异常.", e1);
        } catch (IOException e1) {
            throw new HttpExcutingException(true, "Http请求IO流异常.", e1);
        } finally {
            if (response != null) {
                try {
                    //对response进行关闭
                    response.close();
                } catch (IOException e) {
                    //do nothing
                }
            }
        }
        return result;
    }
    
    /**
     * 根据请求参数map获取url请求后返回的数据<br/>
     * <功能详细描述>
     * @param url
     * @param params
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String post(String url, Map<String, String> params) {
        String result = post(url, params, "UTF-8", "UTF-8", null);
        return result;
    }
    
    /**
     * 根据请求参数map获取url请求后返回的数据<br/>
     * <功能详细描述>
     * @param url
     * @param params
     * @param requestEncoding
     * @param responseEncoding
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String post(String url, Map<String, String> params,
            String requestEncoding, String responseEncoding) {
        String result = post(url,
                params,
                requestEncoding,
                responseEncoding,
                null);
        return result;
    }
    
    /**
     * 根据请求参数map获取url请求后返回的数据<br/>
     * <功能详细描述>
     * @param url
     * @param params
     * @param requestEncoding
     * @param responseEncoding
     * @param headerMap
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String post(String url, Map<String, String> params,
            String requestEncoding, String responseEncoding,
            Map<String, String> headerMap) {
        AssertUtils.notEmpty(url, "url is empty.");
        
        String result = "";
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        if (!MapUtils.isEmpty(params)) {
            for (Entry<String, String> entryTemp : params.entrySet()) {
                if (StringUtils.isEmpty(entryTemp.getKey())) {
                    continue;
                }
                nameValuePairs.add(new BasicNameValuePair(entryTemp.getKey(),
                        entryTemp.getValue()));
            }
        }
        
        UrlEncodedFormEntity uefEntity = null;
        try {
            uefEntity = new UrlEncodedFormEntity(nameValuePairs,
                    requestEncoding);
        } catch (UnsupportedEncodingException e) {
            throw new BeforeHttpExcuteException("Http请求参数字符集转换异常.", e);
        }
        
        CloseableHttpResponse response = null;
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        try {
            if (!MapUtils.isEmpty(headerMap)) {
                //httppost.setHeader("accept", "*/*");
                //httppost.setHeader("connection", "Keep-Alive");
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    if (StringUtils.isEmpty(entry.getKey())
                            || StringUtils.isEmpty(entry.getValue())) {
                        continue;
                    }
                    httppost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            httppost.setEntity(uefEntity);
            
            response = HTTP_CLIENT.execute(httppost);
            int statusCode = response.getStatusLine().getStatusCode();
            String reasonPhrase = response.getStatusLine().getReasonPhrase();
            if (200 != statusCode) {
                if (503 == statusCode) {
                    throw new HttpExcutingException(false, "Http请求返回失败.",
                            statusCode, reasonPhrase);
                } else {
                    throw new HttpExcutingException(true, "Http请求返回失败.",
                            statusCode, reasonPhrase);
                }
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try {
                    if (StringUtils.isEmpty(responseEncoding)) {
                        result = EntityUtils.toString(entity);
                    } else {
                        result = EntityUtils.toString(entity, responseEncoding);
                    }
                } catch (ParseException e) {
                    throw new AfterHttpExcuteException("Http请求返回解析异常", e);
                } catch (IOException e) {
                    throw new AfterHttpExcuteException("Http请求返回解析异常", e);
                } finally {
                    //会自动释放连接
                    EntityUtils.consume(response.getEntity());
                }
            }
        } catch (ClientProtocolException e1) {
            throw new HttpExcutingException(false, "Http请求协议异常.", e1);
        } catch (ConnectException e1) {
            throw new HttpExcutingException(false, "Http请求IO流异常.", e1);
        } catch (ConnectTimeoutException e1) {
            throw new HttpExcutingException(false, "Http请求IO流异常.", e1);
        } catch (NoRouteToHostException e1) {
            throw new HttpExcutingException(false, "Http请求IO流异常.", e1);
        } catch (IOException e1) {
            throw new HttpExcutingException(true, "Http请求IO流异常.", e1);
        } finally {
            if (response != null) {
                try {
                    //会自动释放连接
                    response.close();
                } catch (IOException e) {
                    //do nothing
                }
            }
        }
        return result;
    }
    
    /**
     * 通过GET请求发起http请求至远端<br/>
     * <功能详细描述>
     * @param url
     * @param params
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String get(String url, Map<String, String> params) {
        String result = get(url, params, "UTF-8", "UTF-8", null);
        return result;
    }
    
    /**
     * 通过GET请求发起http请求至远端<br/>
     * <功能详细描述>
     * @param url
     * @param params
     * @param requestEncoding
     * @param responseEncoding
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String get(String url, Map<String, String> params,
            String requestEncoding, String responseEncoding) {
        String result = get(url,
                params,
                requestEncoding,
                responseEncoding,
                null);
        return result;
    }
    
    /**
     * 通过GET请求发起http请求至远端<br/>
     * <功能详细描述>
     * @param url
     * @param params
     * @param requestEncoding
     * @param responseEncoding
     * @param headerMap
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String get(String url, Map<String, String> params,
            String requestEncoding, String responseEncoding,
            Map<String, String> headerMap) {
        AssertUtils.notEmpty(url, "url is empty.");
        
        HttpGet httpget = null;
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        if (!MapUtils.isEmpty(params)) {
            for (Entry<String, String> entryTemp : params.entrySet()) {
                if (StringUtils.isEmpty(entryTemp.getKey())) {
                    continue;
                }
                nameValuePairs.add(new BasicNameValuePair(entryTemp.getKey(),
                        entryTemp.getValue()));
            }
            try {
                httpget = new HttpGet(
                        url + (StringUtils.contains(url, "?") ? "&" : "?")
                                + EntityUtils.toString(new UrlEncodedFormEntity(
                                        nameValuePairs, "UTF-8")));
            } catch (ParseException | IOException e) {
                throw new BeforeHttpExcuteException("Http请求参数字符集转换异常.", e);
            }
        } else {
            httpget = new HttpGet(url);
        }
        
        String result = "";
        CloseableHttpResponse response = null;
        try {
            // 创建httpget
            if (!MapUtils.isEmpty(headerMap)) {
                //httppost.setHeader("accept", "*/*");
                //httppost.setHeader("connection", "Keep-Alive");
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    if (StringUtils.isEmpty(entry.getKey())
                            || StringUtils.isEmpty(entry.getValue())) {
                        continue;
                    }
                    httpget.setHeader(entry.getKey(), entry.getValue());
                }
            }
            // 执行get请求.
            response = HTTP_CLIENT.execute(httpget);
            // 获取响应实体
            int statusCode = response.getStatusLine().getStatusCode();
            String reasonPhrase = response.getStatusLine().getReasonPhrase();
            if (200 != statusCode) {
                if (503 == statusCode) {
                    throw new HttpExcutingException(false, "Http请求返回失败.",
                            statusCode, reasonPhrase);
                } else {
                    throw new HttpExcutingException(true, "Http请求返回失败.",
                            statusCode, reasonPhrase);
                }
            }
            
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try {
                    if (StringUtils.isEmpty(responseEncoding)) {
                        result = EntityUtils.toString(entity);
                    } else {
                        result = EntityUtils.toString(entity, responseEncoding);
                    }
                } catch (ParseException e) {
                    throw new AfterHttpExcuteException("Http请求返回解析异常", e);
                } catch (IOException e) {
                    throw new AfterHttpExcuteException("Http请求返回解析异常", e);
                } finally {
                    //会自动释放连接
                    EntityUtils.consume(response.getEntity());
                }
            }
        } catch (ClientProtocolException e1) {
            throw new HttpExcutingException(false, "Http请求协议异常.", e1);
        } catch (IOException e1) {
            throw new HttpExcutingException(true, "Http请求IO流异常.", e1);
        } finally {
            try {
                //会自动释放连接
                HTTP_CLIENT.close();
            } catch (IOException e) {
                //do nothing
            }
        }
        return result;
    }
}
