/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-22
 * <修改描述:>
 */
package com.tx.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.core.io.Resource;

/**
 * <CXF工具类>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CXFUtils {
    
    /** 超时时间无穷大 */
    public static final long TIMEOUT_INFINITY = 0;
    
    /** 默认超时时间 */
    public static final long TIMEOUT_DEFAULT = 0;
    
    /**
      *<创建WebService的客户端代理>
      *<功能详细描述>
      * @param <T>客户端代理类型
      * @param clazz 客户端代理接口
      * @param url WebService地址
      * @param timeout 超时时间，单位为毫秒 超时时间为零表示无穷大超时。 
      * @return [参数说明] 对应的WebService客户端代理
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> T createService(Class<T> clazz, String url, long timeout) {
        return createService(clazz, url, timeout, null, null, null);
    }
    
    /**
     * 创建WebService的客户端代理
     * @param <T>客户端代理类型
     * @param clazz客户端代理接口
     * @param urlWebService地址
     * @param timeout超时时间，单位为毫秒 超时时间为零表示无穷大超时。 
     * @param bind一般用於指明是使用Soap1.1还是1.2標準， SOAPBinding.SOAP11HTTP_BINDING<br/>SOAPBinding.SOAP12HTTP_BINDING
     * @return 对应的WebService客户端代理
     */
    public static <T> T createService(Class<T> clazz, String url, long timeout,
            String bind) {
        return createService(clazz, url, timeout, bind, null, null);
    }
    
    /**
      *<创建WebService的客户端代理>
      *<功能详细描述>
     * @param <T>客户端代理类型
     * @param clazz客户端代理接口
     * @param urlWebService地址
     * @param timeout超时时间，单位为毫秒 超时时间为零表示无穷大超时。 
     * @param bind一般用於指明是使用Soap1.1还是1.2標準 SOAPBinding.SOAP11HTTP_BINDING<br/>SOAPBinding.SOAP12HTTP_BINDING
     * @param username用户名
     * @param password密码
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> T createService(Class<T> clazz, String url, long timeout,
            String bind, String username, String password) {
        
        T service = createService(clazz, url, bind, null, null);
        
        setTimeout(service, timeout);
        
        return service;
    }
    
    /**
     * 创建WebService的客户端代理
     * 
     * @param <T>客户端代理类型
     * @param clazz客户端代理接口
     * @param urlWebService地址
     * @param timeout超时时间，单位为毫秒 超时时间为零表示无穷大超时。 
     * @param bind一般用於指明是使用Soap1.1还是1.2標準 SOAPBinding.SOAP11HTTP_BINDING<br/>SOAPBinding.SOAP12HTTP_BINDING
     * @param httpClientPolicy客户端调用规则
     * @param username用户名
     * @param password密码
     * @return 对应的WebService客户端代理
     */
    @SuppressWarnings("unchecked")
    public static <T> T createService(Class<T> clazz, String url, String bind,
            String username, String password) {
        // 创建工廠
        JaxWsProxyFactoryBean soapFactoryBean = new JaxWsProxyFactoryBean();
        // 设置地址，不是wsdl的地址，而是Web Service地址
        soapFactoryBean.setAddress(url);
        // 设置接口类
        soapFactoryBean.setServiceClass(clazz);
        
        // 使用什么SOAP协议，是1.1还是1.2 SOAPBinding.SOAP12HTTP_BINDING
        if (StringUtils.isNotEmpty(bind)) {
            soapFactoryBean.setBindingId(bind);
        }
        
        // 如有需要认证的，则设置用户名和密码
        if (username != null && password != null) {
            soapFactoryBean.setUsername(username);
            soapFactoryBean.setPassword(password);
        }
        
        T service = (T) soapFactoryBean.create();
        
        return service;
    }
    
    /**
      *<添加调试日志记录拦截器>
      *<功能详细描述>
      * @param service [参数说明]
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> void addDebugLoggingInterceptor(T service) {
        Client client = ClientProxy.getClient(service);
        
        client.getInInterceptors().add(new LoggingInInterceptor());
        client.getOutInterceptors().add(new LoggingOutInterceptor());
    }
    
    /**
      *<设置接口调用超时时间>
      *<功能详细描述>
      * @param service
      * @param timeout [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> void setTimeout(T service, long timeout) {
        if (timeout < 0) {
            return;
        }
        Client client = ClientProxy.getClient(service);
        
        HTTPConduit http = (HTTPConduit) client.getConduit();
        
        HTTPClientPolicy httpClientPolicy = http.getClient();
        if (httpClientPolicy == null) {
            httpClientPolicy = new HTTPClientPolicy();
            http.setClient(httpClientPolicy);
        }
        
        if (timeout > 0) {
            httpClientPolicy.setConnectionTimeout(timeout);
            httpClientPolicy.setAllowChunking(false);
            httpClientPolicy.setReceiveTimeout(timeout);
        }
    }
    
    /**
     *<设置接口调用超时时间>
     *<功能详细描述>
     * @param service
     * @param timeout [参数说明]
     * 
     * @return void [返回类型说明]
     * @throws KeyStoreException 
     * @throws IOException 
     * @throws CertificateException 
     * @throws NoSuchAlgorithmException 
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static <T> void setTrustSSLKeyManangers(T service,
            Resource keystoreResource, String keystorePassowrd)
            throws KeyStoreException, IOException, NoSuchAlgorithmException,
            CertificateException {
        if (keystoreResource == null || !keystoreResource.exists()
                || StringUtils.isEmpty(keystorePassowrd)) {
            return;
        }
        Client proxy = ClientProxy.getClient(service);
        
        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
        
        TLSClientParameters tlsParams = conduit.getTlsClientParameters();
        if (tlsParams == null) {
            tlsParams = new TLSClientParameters();
            conduit.setTlsClientParameters(tlsParams);
        }
        tlsParams.setDisableCNCheck(true);
        tlsParams.setSecureSocketProtocol("SSL");
        KeyStore keyStore = KeyStore.getInstance("JKS");
        //String password = "123321qQ";
        String storePassword = "123321qQ";
        
        InputStream in = null;
        try {
            in = keystoreResource.getInputStream();
            keyStore.load(in, storePassword.toCharArray());
            TrustManagerFactory trustFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustFactory.init(keyStore);
            TrustManager[] trustManagers = trustFactory.getTrustManagers();
            tlsParams.setTrustManagers(trustManagers);
            
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
    
}
