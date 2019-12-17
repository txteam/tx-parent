///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2016年7月2日
// * <修改描述:>
// */
//package com.tx.core.util;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.ConnectException;
//import java.net.NoRouteToHostException;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.WeakHashMap;
//import java.util.concurrent.TimeUnit;
//
//import org.apache.commons.collections4.MapUtils;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.ParseException;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.params.ClientPNames;
//import org.apache.http.config.SocketConfig;
//import org.apache.http.conn.ConnectTimeoutException;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.CoreConnectionPNames;
//import org.apache.http.params.HttpParams;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//
//import com.tx.core.exceptions.remote.AfterHttpExcuteException;
//import com.tx.core.exceptions.remote.BeforeHttpExcuteException;
//import com.tx.core.exceptions.remote.HttpExcutingException;
//
///**
// * HttpClient实现<br/>
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2016年7月2日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class HttpClientUtils {
//    
//    //获取httpClient默认实现
//    private static Map<Class<?>, HttpClient> httpClientMap = new WeakHashMap<>();
//    
//    /**
//     * 构建HttpClient对象<br/>
//     * <功能详细描述>
//     * @param type
//     * @param connectionManagerTimeout
//     * @param connectionTimeout
//     * @param soTimeout
//     * @param maxTotalConnections
//     * @param maxConnectionsPerHost
//     * @return [参数说明]
//     * 
//     * @return HttpClient42x [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    public static HttpClient buildHttpClient(Class<?> type,
//            long connectionManagerTimeout, int connectionTimeout, int soTimeout,
//            int maxTotalConnections, int maxConnectionsPerHost,
//            boolean statleCheckingEnabled) {
//        HttpClient httpClient42x = buildHttpClient(type,
//                connectionManagerTimeout,
//                connectionTimeout,
//                soTimeout,
//                maxTotalConnections,
//                maxConnectionsPerHost,
//                statleCheckingEnabled,
//                true,
//                3,
//                3000);
//        return httpClient42x;
//    }
//    
//    /**
//      * 构建42x版本的HttpClient对象<br/>
//      * <功能详细描述>
//      * @param type
//      * @param connectionManagerTimeout
//      * @param connectionTimeout
//      * @param soTimeout
//      * @param maxTotalConnections
//      * @param maxConnectionsPerHost
//      * @param statleCheckingEnabled
//      * @param isRetry
//      * @param maxRetries
//      * @param retryInterval
//      * @return [参数说明]
//      * 
//      * @return HttpClient42x [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public static HttpClient buildHttpClient(Class<?> type,
//            long connectionManagerTimeout, int connectionTimeout, int soTimeout,
//            int maxTotalConnections, int maxConnectionsPerHost,
//            boolean statleCheckingEnabled, boolean isRetry, int maxRetries,
//            int retryInterval) {
//        if (httpClientMap.containsKey(type)) {
//            return httpClientMap.get(type);
//        }
//        
//        HttpClient httpClient42x = new HttpClient(connectionManagerTimeout,
//                connectionTimeout, soTimeout, maxTotalConnections,
//                maxConnectionsPerHost, statleCheckingEnabled, isRetry,
//                maxRetries, retryInterval);
//        httpClientMap.put(type, httpClient42x);
//        return httpClient42x;
//    }
//    
//    public static class HttpClient {
//        //定义了当从ClientConnectionManager中检索ManagedClientConnection实例时使用的毫秒级的超时时间
//        //这个参数期望得到一个java.lang.Long类型的值。如果这个参数没有被设置，默认等于CONNECTION_TIMEOUT，因此一定要设置
//        private long connectionManagerTimeout = 500L;
//        
//        //连接超时.定义了通过网络与服务器建立连接的超时时间。
//        //Httpclient包中通过一个异步线程去创建与服务器的socket连接，这就是该socket连接的超时时间，此处设置为5秒
//        private int connectionTimeout = 5000;
//        
//        //请求超时:这定义了Socket读数据的超时时间，即从服务器获取响应数据需要等待的时间，此处设置为60秒。
//        private int soTimeout = (60 * 1000);
//        
//        //在提交请求之前 测试连接是否可用
//        private boolean statleCheckingEnabled = true;
//        
//        //最大连接数
//        private int maxTotalConnections = 50;
//        
//        //每主机最大连接数
//        private int maxConnectionsPerHost = 10;
//        
//        //客户链接
//        private CloseableHttpClient httpClient;
//        
//        //是否重发
//        private boolean isRetry;
//        
//        //最大重发次数
//        private int maxRetries = 3;
//        
//        //重发频次
//        private int retryInterval = 3000;
//        
//        /** <默认构造函数> */
//        public HttpClient(long connectionManagerTimeout, int connectionTimeout,
//                int soTimeout, int maxTotalConnections,
//                int maxConnectionsPerHost, boolean statleCheckingEnabled,
//                boolean isRetry, int maxRetries, int retryInterval) {
//            super();
//            this.connectionManagerTimeout = connectionManagerTimeout;
//            this.connectionTimeout = connectionTimeout;
//            this.soTimeout = soTimeout;
//            this.maxTotalConnections = maxTotalConnections;
//            this.maxConnectionsPerHost = maxConnectionsPerHost;
//            this.statleCheckingEnabled = statleCheckingEnabled;
//            
//            this.isRetry = isRetry;
//            this.maxRetries = maxRetries;
//            this.retryInterval = retryInterval;
//            
//            buildHttpClient();
//        }
//        
//        /**
//         * 构建HttpParams
//         * <功能详细描述>
//         * @param connectionManagerTimeout //该值就是连接不够用的时候等待超时时间
//         * @param connectionTimeout //连接超时.定义了通过网络与服务器建立连接的超时时间
//         * @param soTimeout //从连接池中取连接的超时时间
//         * @param statleCheckingEnabled //在提交请求之前 测试连接是否可用
//         * @return [参数说明]
//         * 
//         * @return HttpParams [返回类型说明]
//         * @exception throws [异常类型] [异常说明]
//         * @see [类、类#方法、类#成员]
//        */
//        private HttpParams buildHttpParams() {
//            HttpParams params = new BasicHttpParams();
//            //该值就是连接不够用的时候等待超时时间
//            params.setLongParameter(ClientPNames.CONN_MANAGER_TIMEOUT,
//                    connectionManagerTimeout);
//            //连接超时.定义了通过网络与服务器建立连接的超时时间
//            params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
//                    connectionTimeout);
//            //在提交请求之前 测试连接是否可用
//            params.setBooleanParameter(
//                    CoreConnectionPNames.STALE_CONNECTION_CHECK,
//                    statleCheckingEnabled);
//            return params;
//        }
//        
//        private void buildHttpClient() {
//            HttpClientBuilder builder = HttpClientBuilder.create();
//            //是路由的默认最大连接（该值默认为2），限制数量实际使用DefaultMaxPerRoute并非MaxTotal。
//            //设置过小无法支持大并发(ConnectionPoolTimeoutException: Timeout waiting for connection from pool)，路由是对maxTotal的细分。
//            builder.setMaxConnTotal(maxTotalConnections);
//            //此处解释下MaxtTotal和DefaultMaxPerRoute的区别：
//            //1、MaxtTotal是整个池子的大小；
//            //2、DefaultMaxPerRoute是根据连接到的主机对MaxTotal的一个细分；比如：
//            //MaxtTotal=400 DefaultMaxPerRoute=200
//            //而我只连接到xx时，到这个主机的并发最多只有200；而不是400；
//            //而我连接到xx 和 xx时，到每个主机的并发最多只有200；即加起来是400（但不能超过400）；所以起作用的设置是DefaultMaxPerRout
//            builder.setMaxConnPerRoute(maxConnectionsPerHost);
//            
//            builder.setConnectionTimeToLive(this.connectionTimeout,
//                    TimeUnit.MILLISECONDS);
//            //从连接池中取连接的超时时间
//            SocketConfig socketConfig = SocketConfig.custom()
//                    .setSoTimeout(soTimeout)
//                    .setSoReuseAddress(true)
//                    .setSoKeepAlive(true)
//                    .build();
//            builder.setDefaultSocketConfig(socketConfig);
//            //ConnectionConfig connnectionConfig = ConnectionConfig.custom().setCharset(Charset.defaultCharset()).setBufferSize(bufferSize);
//            //builder.setDefaultConnectionConfig(config);
//            //设置自动重试
//            if (this.isRetry) {
//                DefaultHttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(
//                        this.maxRetries, this.isRetry);
//                builder.setRetryHandler(retryHandler);
//            } else {
//                builder.disableAutomaticRetries();
//            }
//            //是否自动重发
//            this.httpClient = builder.build();
//        }
//        
//        public CloseableHttpClient getHttpClient() {
//            return this.httpClient;
//        }
//        
//        public String post(String url, String requestMessage) {
//            String response = post(url, requestMessage, "UTF-8", "UTF-8");
//            return response;
//        }
//        
//        public String post(String url, String requestMessage,
//                String requestEncoding, String responseEncoding,
//                Map<String, String> headerMap) {
//            String resStr = "";
//            
//            HttpEntity requestEntity = null;
//            //            try {
//            requestEntity = new StringEntity(requestMessage, requestEncoding);
//            //            } catch (UnsupportedEncodingException e) {
//            //                throw new BeforeHttpExcuteException("Http请求参数字符集转换异常.", e);
//            //            }
//            
//            HttpResponse response = null;
//            // 创建httppost
//            HttpPost httppost = new HttpPost(url);
//            try {
//                httppost.setHeader("accept", "*/*");
//                //httppost.setHeader(name, value);"Charset", "UTF-8"
//                httppost.setHeader("connection", "Keep-Alive");
//                
//                if (headerMap != null && headerMap.size() > 0) {
//                    for (Map.Entry<String, String> entry : headerMap
//                            .entrySet()) {
//                        httppost.setHeader(entry.getKey(), entry.getValue());
//                    }
//                }
//                
//                httppost.setEntity(requestEntity);
//                
//                response = this.httpClient.execute(httppost);
//                
//                int statusCode = response.getStatusLine().getStatusCode();
//                String reasonPhrase = response.getStatusLine()
//                        .getReasonPhrase();
//                if (200 != statusCode) {
//                    if (503 == statusCode) {
//                        throw new HttpExcutingException(false, "Http请求返回失败.",
//                                statusCode, reasonPhrase);
//                    } else {
//                        throw new HttpExcutingException(true, "Http请求返回失败.",
//                                statusCode, reasonPhrase);
//                    }
//                }
//                HttpEntity entity = response.getEntity();
//                
//                if (entity != null) {
//                    try {
//                        Charset responseCharset = Charset
//                                .forName(responseEncoding);
//                        if (responseCharset == null) {
//                            ContentType contentType = ContentType
//                                    .getOrDefault(entity);
//                            Charset defaultCharset = contentType.getCharset();
//                            if (defaultCharset == null) {
//                                defaultCharset = HTTP.DEF_CONTENT_CHARSET;
//                            }
//                            resStr = new String(EntityUtils.toByteArray(entity),
//                                    defaultCharset);
//                        } else {
//                            resStr = new String(EntityUtils.toByteArray(entity),
//                                    responseCharset);
//                        }
//                    } catch (ParseException e) {
//                        throw new AfterHttpExcuteException("Http请求返回解析异常", e);
//                    } catch (IOException e) {
//                        throw new AfterHttpExcuteException("Http请求返回解析异常", e);
//                    }
//                }
//            } catch (ClientProtocolException e1) {
//                throw new HttpExcutingException(false, "Http请求协议异常.", e1);
//            } catch (ConnectException e1) {
//                throw new HttpExcutingException(false, "Http请求IO流异常.", e1);
//            } catch (ConnectTimeoutException e1) {
//                throw new HttpExcutingException(false, "Http请求IO流异常.", e1);
//            } catch (NoRouteToHostException e1) {
//                throw new HttpExcutingException(false, "Http请求IO流异常.", e1);
//            } catch (IOException e1) {
//                throw new HttpExcutingException(true, "Http请求IO流异常.", e1);
//            } finally {
//                if (response != null) {
//                    try {
//                        //会自动释放连接
//                        EntityUtils.consume(response.getEntity());
//                    } catch (IOException e) {
//                        //do nothing
//                    }
//                }
//            }
//            return resStr;
//        }
//        
//        public String post(String url, String requestMessage,
//                String requestEncoding, String responseEncoding) {
//            return post(url,
//                    requestMessage,
//                    requestEncoding,
//                    responseEncoding,
//                    null);
//        }
//        
//        public String post(String url, Map<String, String> params) {
//            String response = post(url, params, "UTF-8", "UTF-8");
//            return response;
//        }
//        
//        /**
//         * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
//         * @throws IOException 
//         * @throws ParseException 
//         */
//        public String post(String url, Map<String, String> params,
//                String requestEncoding, String responseEncoding) {
//            String resStr = "";
//            
//            // 创建参数队列
//            List<NameValuePair> requestParamList = new ArrayList<>();
//            if (!MapUtils.isEmpty(params)) {
//                for (Entry<String, String> entryTemp : params.entrySet()) {
//                    if (StringUtils.isEmpty(entryTemp.getKey())) {
//                        continue;
//                    }
//                    requestParamList.add(new BasicNameValuePair(
//                            entryTemp.getKey(), entryTemp.getValue()));
//                }
//            }
//            UrlEncodedFormEntity uefEntity;
//            try {
//                uefEntity = new UrlEncodedFormEntity(requestParamList,
//                        requestEncoding);
//            } catch (UnsupportedEncodingException e) {
//                throw new BeforeHttpExcuteException("Http请求参数字符集转换异常.", e);
//            }
//            
//            HttpResponse response = null;
//            // 创建httppost
//            HttpPost httppost = new HttpPost(url);
//            try {
//                httppost.setHeader("accept", "*/*");
//                httppost.setHeader("connection", "Keep-Alive");
//                httppost.setEntity(uefEntity);
//                
//                response = this.httpClient.execute(httppost);
//                int statusCode = response.getStatusLine().getStatusCode();
//                String reasonPhrase = response.getStatusLine()
//                        .getReasonPhrase();
//                if (200 != statusCode) {
//                    if (503 == statusCode) {
//                        throw new HttpExcutingException(false, "Http请求返回失败.",
//                                statusCode, reasonPhrase);
//                    } else {
//                        throw new HttpExcutingException(true, "Http请求返回失败.",
//                                statusCode, reasonPhrase);
//                    }
//                }
//                HttpEntity entity = response.getEntity();
//                
//                if (entity != null) {
//                    try {
//                        Charset responseCharset = Charset
//                                .forName(responseEncoding);
//                        if (responseCharset == null) {
//                            ContentType contentType = ContentType
//                                    .getOrDefault(entity);
//                            Charset defaultCharset = contentType.getCharset();
//                            if (defaultCharset == null) {
//                                defaultCharset = HTTP.DEF_CONTENT_CHARSET;
//                            }
//                            resStr = new String(EntityUtils.toByteArray(entity),
//                                    defaultCharset);
//                        } else {
//                            resStr = new String(EntityUtils.toByteArray(entity),
//                                    responseCharset);
//                        }
//                    } catch (ParseException e) {
//                        throw new AfterHttpExcuteException("Http请求返回解析异常", e);
//                    } catch (IOException e) {
//                        throw new AfterHttpExcuteException("Http请求返回解析异常", e);
//                    }
//                }
//            } catch (ClientProtocolException e1) {
//                throw new HttpExcutingException(false, "Http请求协议异常.", e1);
//            } catch (IOException e1) {
//                throw new HttpExcutingException(true, "Http请求IO流异常.", e1);
//            } finally {
//                try {
//                    //会自动释放连接
//                    this.httpClient.close();
//                } catch (IOException e) {
//                    //do nothing
//                }
//            }
//            return resStr;
//        }
//        
//        /**
//         * 发送 get请求
//         */
//        public String get(String url, String requestEncoding,
//                String responseEncoding) {
//            String resStr = "";
//            HttpResponse response = null;
//            try {
//                // 创建httpget.
//                HttpGet httpget = new HttpGet(url);
//                httpget.setHeader("accept", "*/*");
//                httpget.setHeader("connection", "Keep-Alive");
//                
//                // 执行get请求.
//                response = this.httpClient.execute(httpget);
//                
//                // 获取响应实体
//                int statusCode = response.getStatusLine().getStatusCode();
//                String reasonPhrase = response.getStatusLine()
//                        .getReasonPhrase();
//                if (200 != statusCode) {
//                    if (503 == statusCode) {
//                        throw new HttpExcutingException(false, "Http请求返回失败.",
//                                statusCode, reasonPhrase);
//                    } else {
//                        throw new HttpExcutingException(true, "Http请求返回失败.",
//                                statusCode, reasonPhrase);
//                    }
//                }
//                
//                HttpEntity entity = response.getEntity();
//                if (entity != null) {
//                    try {
//                        Charset responseCharset = Charset
//                                .forName(responseEncoding);
//                        if (responseCharset == null) {
//                            ContentType contentType = ContentType
//                                    .getOrDefault(entity);
//                            Charset defaultCharset = contentType.getCharset();
//                            if (defaultCharset == null) {
//                                defaultCharset = HTTP.DEF_CONTENT_CHARSET;
//                            }
//                            resStr = new String(EntityUtils.toByteArray(entity),
//                                    defaultCharset);
//                        } else {
//                            resStr = new String(EntityUtils.toByteArray(entity),
//                                    responseCharset);
//                        }
//                    } catch (ParseException e) {
//                        throw new AfterHttpExcuteException("Http请求返回解析异常", e);
//                    } catch (IOException e) {
//                        throw new AfterHttpExcuteException("Http请求返回解析异常", e);
//                    }
//                }
//            } catch (ClientProtocolException e1) {
//                throw new HttpExcutingException(false, "Http请求协议异常.", e1);
//            } catch (IOException e1) {
//                throw new HttpExcutingException(true, "Http请求IO流异常.", e1);
//            } finally {
//                try {
//                    //会自动释放连接
//                    this.httpClient.close();
//                } catch (IOException e) {
//                    //do nothing
//                }
//            }
//            return resStr;
//        }
//    }
//}
