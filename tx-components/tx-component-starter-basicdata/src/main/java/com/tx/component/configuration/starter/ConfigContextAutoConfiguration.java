/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.configuration.starter;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

import com.tx.component.basicdata.context.BasicDataContextFactory;
import com.tx.component.configuration.ConfigContextConstants;
import com.tx.component.configuration.context.ConfigContextFactory;
import com.tx.component.configuration.context.ConfigPropertyServiceSupportCacheProxyCreator;
import com.tx.component.configuration.controller.ConfigContextAPIController;
import com.tx.component.configuration.registry.ConfigAPIClientRegistry;
import com.tx.component.configuration.service.ConfigPropertyItemService;
import com.tx.component.configuration.service.ConfigPropertyManager;
import com.tx.component.configuration.service.ConfigPropertyManagerComposite;
import com.tx.component.configuration.service.impl.LocalConfigPropertyManager;
import com.tx.component.configuration.service.impl.RemoteConfigPropertyManager;
import com.tx.component.configuration.starter.ConfigCacheConfiguration.ConfigCacheCustomizer;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.starter.component.ComponentSupportAutoConfiguration;

/**
 * 基础数据容器自动配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年4月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@EnableConfigurationProperties(ConfigContextProperties.class)
@Configuration
@AutoConfigureAfter({ ComponentSupportAutoConfiguration.class })
@ConditionalOnClass({ BasicDataContextFactory.class })
@ConditionalOnSingleCandidate(DataSource.class)
@ConditionalOnBean(PlatformTransactionManager.class)
@ConditionalOnProperty(prefix = ConfigContextConstants.PROPERTIES_PREFIX, value = "enable", havingValue = "true")
@Import({ ConfigPersisterConfiguration.class,
        ConfigAPIClientConfiguration.class, ConfigCacheConfiguration.class })
public class ConfigContextAutoConfiguration
        implements ApplicationContextAware, InitializingBean {
    
    /** spring 容器句柄 */
    protected ApplicationContext applicationContext;
    
    /** 容器所属模块：当该值为空时，使用spring.application.name的内容 */
    private String module;
    
    /** application.name */
    @Value(value = "${spring.application.name}")
    private String applicationName;
    
    /** 属性文件 */
    private ConfigContextProperties properties;
    
    /** 配置文件所在路径 */
    private String configLocation = "classpath:config/*.xml";
    
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
        if (this.properties != null
                && !StringUtils.isEmpty(this.properties.getConfigLocation())) {
            this.configLocation = this.properties.getConfigLocation();
        }
        
        //初始化包名
        if (!StringUtils.isEmpty(this.applicationName)) {
            this.module = this.applicationName;
        }
        if (!StringUtils.isEmpty(this.properties.getModule())) {
            this.module = this.properties.getModule();
        }
        AssertUtils.notEmpty(this.module, "module is empty.");
    }
    
    /**
     * 基础数据业务层缓存代理<br/>
     * <功能详细描述>
     * @param customizer
     * @return [参数说明]
     * 
     * @return BasicDataServiceSupportCacheProxyCreator [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "configPropertyServiceSupportCacheProxyCreator")
    public ConfigPropertyServiceSupportCacheProxyCreator configPropertyServiceSupportCacheProxyCreator(
            ConfigCacheCustomizer customizer) {
        CacheManager cacheManager = customizer.getCacheManager();
        AssertUtils.notNull(cacheManager, "cacheManager is null.");
        
        ConfigPropertyServiceSupportCacheProxyCreator creator = new ConfigPropertyServiceSupportCacheProxyCreator(
                cacheManager);
        return creator;
    }
    
    /**
     * 本地配置属性持久层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return LocalConfigPropertyPersister [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean
    public LocalConfigPropertyManager localConfigPropertyManager(
            ConfigPropertyItemService configPropertyItemService) {
        LocalConfigPropertyManager manager = new LocalConfigPropertyManager(
                module, configLocation, configPropertyItemService);
        return manager;
    }
    
    /**
     * 远程调用Bean<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RemoteConfigPropertyPersister [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean
    @ConditionalOnBean(ConfigAPIClientRegistry.class)
    public RemoteConfigPropertyManager remoteConfigPropertyManager(
            ConfigAPIClientRegistry configAPIClientRegistry) {
        RemoteConfigPropertyManager manager = new RemoteConfigPropertyManager(
                module, configAPIClientRegistry);
        return manager;
    }
    
    /**
     * 配置项查询器<br/>
     * <功能详细描述>
     * @param persisters
     * @return [参数说明]
     * 
     * @return ConfigPropertyPersisterComposite [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean
    public ConfigPropertyManagerComposite configPropertyManagerComposite(
            List<ConfigPropertyManager> persisters) {
        ConfigPropertyManagerComposite composite = new ConfigPropertyManagerComposite(
                persisters);
        return composite;
    }
    
    /**
     * 配置容器<br/>
     * <功能详细描述>
     * @param composite
     * @return [参数说明]
     * 
     * @return ConfigContextFactory [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean
    public ConfigContextFactory configContext(
            ConfigPropertyManagerComposite composite) {
        ConfigContextFactory factory = new ConfigContextFactory();
        factory.setComposite(composite);
        factory.setModule(module);
        
        return factory;
    }
    
    /**
     * 配置容器apicontroller实现
     * <功能详细描述>
     * @param configPropertyItemService
     * @return [参数说明]
     * 
     * @return ConfigAPIController [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean
    public ConfigContextAPIController configAPIController(
            ConfigPropertyItemService configPropertyItemService) {
        ConfigContextAPIController controller = new ConfigContextAPIController(module,
                configPropertyItemService);
        return controller;
    }
}
