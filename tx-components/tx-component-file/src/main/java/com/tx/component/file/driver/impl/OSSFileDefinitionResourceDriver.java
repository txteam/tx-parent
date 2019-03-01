/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年12月21日
 * <修改描述:>
 */
package com.tx.component.file.driver.impl;

import java.io.IOException;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.tx.component.file.driver.FileDefinitionResourceDriver;
import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.resource.FileResource;
import com.tx.component.file.resource.impl.OSSFileResource;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.ObjectUtils;

/**
 * 默认文件定义资源驱动<br/>
 *     file://somehost/someshare/afile.txt 
 *     /home/someuser/somedir
 * @author Administrator
 * @version [版本号, 2014年12月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class OSSFileDefinitionResourceDriver implements
        FileDefinitionResourceDriver {
    
    /** 访问域名 */
    private String accessDomain = "http://oss-cn-shenzhen.aliyuncs.com";
    
    /** endpoint */
    private String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
    
    /** accessId */
    private String accessKeyId;
    
    /** accessKey */
    private String secretAccessKey;
    
    /** bucket */
    private String bucketName;
    
    /** ossClient */
    private OSSClient ossClient = null;
    
    /** 允许打开的最大HTTP连接数。默认为1024 */
    private int maxConnections = ClientConfiguration.DEFAULT_MAX_CONNECTIONS;
    
    /** Socket层传输数据的超时时间（单位：毫秒）。默认为50000毫秒 */
    private int socketTimeout = ClientConfiguration.DEFAULT_SOCKET_TIMEOUT;
    
    /** 建立连接的超时时间（单位：毫秒）。默认为50000毫秒 */
    private int connectionTimeout = ClientConfiguration.DEFAULT_CONNECTION_TIMEOUT;
    
    /** 从连接池中获取连接的超时时间（单位：毫秒）。默认不超时 */
    private int connectionRequestTimeout = ClientConfiguration.DEFAULT_CONNECTION_REQUEST_TIMEOUT;
    
    /** 如果空闲时间超过此参数的设定值，则关闭连接（单位：毫秒）。默认为60000毫秒 */
    private long idleConnectionTime = ClientConfiguration.DEFAULT_IDLE_CONNECTION_TIME;
    
    /** 请求失败后最大的重试次数。默认3次 */
    private int maxErrorRetry = 3;
    
    /** 是否支撑cname */
    private boolean supportCname = true;
    
    /** 是否开启二级域名（Second Level Domain）的访问方式，默认不开启 */
    private boolean sldEnabled = false;
    
    /** 连接OSS所采用的协议（HTTP/HTTPS），默认为HTTP */
    private Protocol protocol = Protocol.HTTP;
    
    /** 用户代理，指HTTP的User-Agent头。默认为”aliyun-sdk-java” */
    private String userAgent = "aliyun-sdk-java";
    
    /** 代理服务器主机地址 */
    private String proxyHost;
    
    /** 代理服务器端口 */
    private Integer proxyPort;
    
    /** 代理服务器验证的用户名 */
    private String proxyUsername;
    
    /** 代理服务器验证的密码 */
    private String proxyPassword;
    
    public static void main(String[] args) throws Exception {
        OSSFileDefinitionResourceDriver driver1 = new OSSFileDefinitionResourceDriver();
        driver1.setAccessDomain("http://txstaticresource.oss-cn-shenzhen.aliyuncs.com");
        driver1.setEndpoint("http://oss-cn-shenzhen.aliyuncs.com");
        driver1.setAccessKeyId("LTAIqiYuWr9WAkWa");
        driver1.setSecretAccessKey("e4tyMfOU9IGiiDwWlc6gidT8siQek1");
        driver1.setBucketName("txstaticresource");
        driver1.afterPropertiesSet();
    }
    
    /**
     * 系统文件定义资源驱动器<br/>
     * <构造功能简述>
     * 
     * @param path
     * @throws IOException
     * 
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public OSSFileDefinitionResourceDriver() {
        super();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(this.endpoint, "endpoint is empty.");
        AssertUtils.notEmpty(this.accessKeyId, "accessId is empty.");
        AssertUtils.notEmpty(this.secretAccessKey, "accessKey is empty.");
        AssertUtils.notEmpty(this.bucketName, "bucketName is empty.");
        
        OSSClient client = buildOSSClient();
        this.ossClient = client;
        
        if (!this.ossClient.doesBucketExist(this.bucketName)) {
            CreateBucketRequest cbRequest = new CreateBucketRequest(
                    this.bucketName);
            
            cbRequest.setCannedACL(CannedAccessControlList.PublicRead);//设置为公共读
            this.ossClient.createBucket(cbRequest);
        }
        //如果存在则读取bucket信息，如果已被占用，则此处会抛出异常
        BucketInfo bucketInfo = this.ossClient.getBucketInfo(this.bucketName);
        bucketInfo.getBucket().getLocation();
    }
    
    /** 
     * 构建OSSClient
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return OSSClient [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private OSSClient buildOSSClient() {
        // 创建ClientConfiguration实例
        ClientConfiguration conf = new ClientConfiguration();
        conf.setMaxConnections(this.maxConnections);// 设置OSSClient使用的最大连接数，默认1024
        conf.setSocketTimeout(this.socketTimeout);// 设置请求超时时间，默认50秒
        conf.setConnectionTimeout(this.connectionTimeout);//建立连接的超时时间（单位：毫秒）。默认为50000毫秒
        conf.setConnectionRequestTimeout(this.connectionRequestTimeout);//从连接池中获取连接的超时时间（单位：毫秒）。默认不超时
        conf.setIdleConnectionTime(this.idleConnectionTime);//如果空闲时间超过此参数的设定值，则关闭连接（单位：毫秒）。默认为60000毫秒
        conf.setMaxErrorRetry(this.maxErrorRetry);// 设置失败请求重试次数，默认3次
        conf.setSupportCname(this.supportCname);//是否支撑cname
        conf.setSLDEnabled(this.sldEnabled);//是否开启二级域名（Second Level Domain）的访问方式，默认不开启
        conf.setProtocol(this.protocol);//连接OSS所采用的协议（HTTP/HTTPS），默认为HTTP
        conf.setUserAgent(this.userAgent);//用户代理，指HTTP的User-Agent头。默认为”aliyun-sdk-java”
        if (!ObjectUtils.isEmpty(this.proxyHost)) {
            conf.setProxyHost(this.proxyHost);
        }
        if (!ObjectUtils.isEmpty(this.proxyPort)) {
            conf.setProxyPort(this.proxyPort);
        }
        if (!ObjectUtils.isEmpty(this.proxyUsername)) {
            conf.setProxyUsername(this.proxyHost);
        }
        if (!ObjectUtils.isEmpty(this.proxyPassword)) {
            conf.setProxyPassword(this.proxyPassword);
        }
        
        // 创建OSSClient实例
        OSSClient client = new OSSClient(this.endpoint, this.accessKeyId,
                this.secretAccessKey, conf);
        return client;
    }
    
    /**
     * 获取文件定义对应的资源
     * @param fileDefinition
     * @return
     */
    @Override
    public FileResource getResource(FileDefinition fileDefinition) {
        AssertUtils.notNull(fileDefinition, "fileDefinition is null.");
        AssertUtils.notEmpty(fileDefinition.getRelativePath(),
                "fileDefinition.relativePath is empty.");
        
        FileResource fdResource = new OSSFileResource(fileDefinition,
                this.bucketName, this.ossClient, this.accessDomain);
        fileDefinition.setResource(fdResource);
        
        return fdResource;
    }
    
    /**
     * @param 对endpoint进行赋值
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
    
    /**
     * @param 对accessKeyId进行赋值
     */
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }
    
    /**
     * @param 对secretAccessKey进行赋值
     */
    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }
    
    /**
     * @param 对bucketName进行赋值
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
    
    /**
     * @param 对ossClient进行赋值
     */
    public void setOssClient(OSSClient ossClient) {
        this.ossClient = ossClient;
    }
    
    /**
     * @param 对maxConnections进行赋值
     */
    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }
    
    /**
     * @param 对socketTimeout进行赋值
     */
    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }
    
    /**
     * @param 对connectionTimeout进行赋值
     */
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
    
    /**
     * @param 对connectionRequestTimeout进行赋值
     */
    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }
    
    /**
     * @param 对idleConnectionTime进行赋值
     */
    public void setIdleConnectionTime(long idleConnectionTime) {
        this.idleConnectionTime = idleConnectionTime;
    }
    
    /**
     * @param 对maxErrorRetry进行赋值
     */
    public void setMaxErrorRetry(int maxErrorRetry) {
        this.maxErrorRetry = maxErrorRetry;
    }
    
    /**
     * @param 对supportCname进行赋值
     */
    public void setSupportCname(boolean supportCname) {
        this.supportCname = supportCname;
    }
    
    /**
     * @param 对sldEnabled进行赋值
     */
    public void setSldEnabled(boolean sldEnabled) {
        this.sldEnabled = sldEnabled;
    }
    
    /**
     * @param 对protocol进行赋值
     */
    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }
    
    /**
     * @param 对userAgent进行赋值
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    /**
     * @param 对proxyHost进行赋值
     */
    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }
    
    /**
     * @param 对proxyPort进行赋值
     */
    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }
    
    /**
     * @param 对proxyUsername进行赋值
     */
    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }
    
    /**
     * @param 对proxyPassword进行赋值
     */
    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }
    
    /**
     * @param 对accessDomain进行赋值
     */
    public void setAccessDomain(String accessDomain) {
        this.accessDomain = accessDomain;
    }
}
