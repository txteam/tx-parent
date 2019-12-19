/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年4月30日
 * <修改描述:>
 */
package com.tx.core.starter.httpclient;

import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 基础数据持久层配置逻辑<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年4月30日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
//参考： org.springframework.cloud.commons.httpclient.HttpClientConfiguration.ApacheHttpClientFactory在其中已被创建
//参考: org.springframework.cloud.netflix.zuul.ZuulProxyAutoConfiguration
@Configuration

@ConditionalOnProperty(value = HttpClientProperties.HTTP_CLIENT_PREFIX
        + ".enable", matchIfMissing = true)
@ConditionalOnMissingClass({ "com.netflix.loadbalancer.ILoadBalancer",
        "org.springframework.cloud.openfeign.FeignAutoConfiguration" })
@ConditionalOnMissingBean(CloseableHttpClient.class)


@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientAutoConfiguration {
    
}
