/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-22
 * <修改描述:>
 */
package com.tx.core.util;

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;

import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.lang.StringUtils;
import org.codehaus.xfire.XFire;
import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.transport.Channel;
import org.codehaus.xfire.transport.Transport;
import org.codehaus.xfire.transport.TransportManager;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;
import org.codehaus.xfire.transport.http.EasySSLProtocolSocketFactory;
import org.codehaus.xfire.util.dom.DOMOutHandler;

/**
 * <xfire客户端工具类>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class XfireUtils {
    
    /**
     * 创建WebService的客户端代理
     * @param <T> 客户端代理类型
     * @param clazz 客户端代理接口
     * @param url WebService地址
     * @param timeout 超时时间，单位为毫秒
     * @return 对应的WebService客户端代理
     * @throws MalformedURLException
     */
    public static <T> T createService(Class<T> clazz, String url, long timeout)
            throws MalformedURLException {
        return createService(clazz, url, timeout, null, null, null);
    }
    
    /**
     * 创建WebService的客户端代理
     * @param <T>客户端代理类型
     * @param clazz客户端代理接口
     * @param urlWebService地址
     * @param timeout超时时间，单位为毫秒
     * @param bind一般用於指明是使用Soap1.1还是1.2標準， SoapHttpTransport.SOAP11_HTTP_BINDING<br/>SoapHttpTransport.SOAP12_HTTP_BINDING
     * @return 对应的WebService客户端代理
     * @throws MalformedURLException
     */
    public static <T> T createService(Class<T> clazz, String url, long timeout,
            String bind) throws MalformedURLException {
        return createService(clazz, url, timeout, bind, null, null);
    }
    
    /**
     * 创建WebService的客户端代理
     * @param <T>客户端代理类型
     * @param clazz客户端代理接口
     * @param urlWebService地址
     * @param timeout超时时间，单位为毫秒
     * @param bind一般用於指明是使用Soap1.1还是1.2標準 SoapHttpTransport.SOAP11_HTTP_BINDING<br/> SoapHttpTransport.SOAP12_HTTP_BINDING
     * @param username 用户名
     * @param password密码
     * @return 对应的WebService客户端代理
     */
    @SuppressWarnings("unchecked")
    public static <T> T createService(Class<T> clazz, String url, long timeout,
            String bind, String username, String password)
            throws MalformedURLException {
        
        XFire xfire = XFireFactory.newInstance().getXFire();
        XFireProxyFactory factory = new XFireProxyFactory(xfire);
        
        // 创建Service实例
        Service serviceModel = new ObjectServiceFactory().create(clazz);
        Transport transport = null;
        
        // 如果指明了传输协议
        if (StringUtils.isNotEmpty(bind)) {
            // 获取传输协议
            TransportManager tm = xfire.getTransportManager();
            transport = tm.getTransport(bind);
        }
        
        // 创建Client Proxy实例
        T service = (transport == null ? (T) factory.create(serviceModel, url)
                : (T) factory.create(serviceModel, transport, url));
        
        // http 设置
        Client client = Client.getInstance(service);
        // 超时时间设置，单位是
        if (timeout >= 0) {
            client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT,
                    String.valueOf(timeout));
        }
        
        //设置用户名和密码
        if (username != null && password != null) {
            client.setProperty(Channel.USERNAME, username);
            client.setProperty(Channel.PASSWORD, password);
        }
        
        return service;
    }
    
    public static void setHttpsSupport(){
        ProtocolSocketFactory easy = new EasySSLProtocolSocketFactory();
        Protocol protocol = new Protocol("https", easy, 443);
        Protocol.registerProtocol("https", protocol);
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
   public static <T> void setGzipEnable(T service){
       
       Client client = ((XFireProxy)Proxy.getInvocationHandler(service)).getClient();
       
       client.addOutHandler(new DOMOutHandler());
       
       client.setProperty(CommonsHttpMessageSender.GZIP_ENABLED, Boolean.TRUE);
   }
    
}
