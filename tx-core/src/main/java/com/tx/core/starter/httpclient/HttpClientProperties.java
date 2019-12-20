/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月1日
 * <修改描述:>
 */
package com.tx.core.starter.httpclient;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Mybatis配置属性<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@ConfigurationProperties(prefix = HttpClientProperties.HTTP_CLIENT_PREFIX)
public class HttpClientProperties {
    
    /** 常量 */
    public static final String HTTP_CLIENT_PREFIX = "tx.core.httpclient";
    
    /** 是否启动期间直接初始化HttpClient */
    private boolean enable = false;
    
    /** 默认金融ssl验证 */
    public static final boolean DEFAULT_DISABLE_SSL_VALIDATION = false;
    
    /** 默认最大连接数 */
    public static final int DEFAULT_MAX_CONNECTIONS = 200;
    
    /** 默认最大连接数(每个实例) */
    public static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 50;
    
    /** 默认限制时间 */
    public static final long DEFAULT_TIME_TO_LIVE = 900L;
    
    /** 默认时间点为 */
    public static final TimeUnit DEFAULT_TIME_TO_LIVE_UNIT = TimeUnit.SECONDS;
    
    public static final boolean DEFAULT_FOLLOW_REDIRECTS = true;
    
    /** 默认的链接超时时间 */
    public static final int DEFAULT_CONNECTION_TIMEOUT = 2000;
    
    /** 默认的重试时间 */
    public static final int DEFAULT_CONNECTION_TIMER_REPEAT = 3000;
    
    private boolean disableSslValidation = DEFAULT_DISABLE_SSL_VALIDATION;
    
    //最大连接数 200
    private int maxConnections = DEFAULT_MAX_CONNECTIONS;
    
    //每主机最大连接数 50
    private int maxConnectionsPerRoute = DEFAULT_MAX_CONNECTIONS_PER_ROUTE;
    
    private long timeToLive = DEFAULT_TIME_TO_LIVE;
    
    private TimeUnit timeToLiveUnit = DEFAULT_TIME_TO_LIVE_UNIT;
    
    private boolean followRedirects = DEFAULT_FOLLOW_REDIRECTS;
    
    /** 连接超时时间 */
    //连接超时.定义了通过网络与服务器建立连接的超时时间。
    //Httpclient包中通过一个异步线程去创建与服务器的socket连接，这就是该socket连接的超时时间，此处设置为2秒
    private int connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
    
    /** 连接超时重试时间 */
    private int connectionTimerRepeat = DEFAULT_CONNECTION_TIMER_REPEAT;
    
    /**
     * @return 返回 enable
     */
    public boolean isEnable() {
        return enable;
    }
    
    /**
     * @param 对enable进行赋值
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    
    /**
     * @return 返回 disableSslValidation
     */
    public boolean isDisableSslValidation() {
        return disableSslValidation;
    }
    
    /**
     * @param 对disableSslValidation进行赋值
     */
    public void setDisableSslValidation(boolean disableSslValidation) {
        this.disableSslValidation = disableSslValidation;
    }
    
    /**
     * @return 返回 maxConnections
     */
    public int getMaxConnections() {
        return maxConnections;
    }
    
    /**
     * @param 对maxConnections进行赋值
     */
    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }
    
    /**
     * @return 返回 maxConnectionsPerRoute
     */
    public int getMaxConnectionsPerRoute() {
        return maxConnectionsPerRoute;
    }
    
    /**
     * @param 对maxConnectionsPerRoute进行赋值
     */
    public void setMaxConnectionsPerRoute(int maxConnectionsPerRoute) {
        this.maxConnectionsPerRoute = maxConnectionsPerRoute;
    }
    
    /**
     * @return 返回 timeToLive
     */
    public long getTimeToLive() {
        return timeToLive;
    }
    
    /**
     * @param 对timeToLive进行赋值
     */
    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }
    
    /**
     * @return 返回 timeToLiveUnit
     */
    public TimeUnit getTimeToLiveUnit() {
        return timeToLiveUnit;
    }
    
    /**
     * @param 对timeToLiveUnit进行赋值
     */
    public void setTimeToLiveUnit(TimeUnit timeToLiveUnit) {
        this.timeToLiveUnit = timeToLiveUnit;
    }
    
    /**
     * @return 返回 followRedirects
     */
    public boolean isFollowRedirects() {
        return followRedirects;
    }
    
    /**
     * @param 对followRedirects进行赋值
     */
    public void setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
    }
    
    /**
     * @return 返回 connectionTimeout
     */
    public int getConnectionTimeout() {
        return connectionTimeout;
    }
    
    /**
     * @param 对connectionTimeout进行赋值
     */
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
    
    /**
     * @return 返回 connectionTimerRepeat
     */
    public int getConnectionTimerRepeat() {
        return connectionTimerRepeat;
    }
    
    /**
     * @param 对connectionTimerRepeat进行赋值
     */
    public void setConnectionTimerRepeat(int connectionTimerRepeat) {
        this.connectionTimerRepeat = connectionTimerRepeat;
    }
}
