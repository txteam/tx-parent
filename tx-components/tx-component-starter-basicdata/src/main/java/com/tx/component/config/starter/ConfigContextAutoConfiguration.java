/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.config.starter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * 基础数据容器自动配置<br/>
 *    允许ConfigPropertyService 不存在实例，如果不存在实例，则配置属性不可修改<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年4月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
@EnableConfigurationProperties(ConfigContextProperties.class)
@ConditionalOnProperty(prefix = "tx.basicdata.config", value = "configLocation", havingValue = "true")
public class ConfigContextAutoConfiguration
        implements ApplicationContextAware, InitializingBean {
    
    /** application.name */
    @Value(value = "${spring.application.name}")
    private String applicationName;
    
    /** 属性文件 */
    protected ConfigContextProperties properties;
    
    /** spring 容器句柄 */
    protected ApplicationContext applicationContext;
    
    /** 容器所属模块：当该值为空时，使用spring.application.name的内容 */
    protected String module;
    
    /** cacheManager */
    protected CacheManager cacheManager;
    
    /** <默认构造函数> */
    public ConfigContextAutoConfiguration(ConfigContextProperties properties) {
        super();
        this.properties = properties;
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (!StringUtils.isBlank(this.properties.getModule())) {
            this.module = this.properties.getModule();
        }
        if (!StringUtils.isBlank(this.applicationName)) {
            this.module = this.applicationName;
        }
    }
}
