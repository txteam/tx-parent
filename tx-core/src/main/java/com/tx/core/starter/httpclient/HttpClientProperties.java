/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月1日
 * <修改描述:>
 */
package com.tx.core.starter.httpclient;

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
public class HttpClientProperties implements Cloneable {
    
    /** 常量 */
    public static final String HTTP_CLIENT_PREFIX = "tx.core.httpclient";
    
    /** 是否启动期间直接初始化HttpClient */
    private boolean enable = false;
    
    
}
