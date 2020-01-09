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
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import com.tx.component.configuration.ConfigContextConstants;
import com.tx.component.configuration.context.ConfigContextFactory;
import com.tx.component.configuration.context.ConfigEntityFactory;
import com.tx.component.configuration.context.ConfigPropertyServiceSupportCacheProxyCreator;
import com.tx.component.configuration.controller.ConfigContextAPIController;
import com.tx.component.configuration.registry.ConfigAPIClientRegistry;
import com.tx.component.configuration.registry.RemoteConfigPropertyManagerRegistry;
import com.tx.component.configuration.service.ConfigPropertyItemService;
import com.tx.component.configuration.service.ConfigPropertyManager;
import com.tx.component.configuration.service.ConfigPropertyManagerComposite;
import com.tx.component.configuration.service.impl.LocalConfigPropertyManager;
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
@Configuration
@AutoConfigureAfter({ ComponentSupportAutoConfiguration.class })
@EnableConfigurationProperties(ConfigContextProperties.class)
@ConditionalOnClass({ ConfigContextFactory.class })
@ConditionalOnSingleCandidate(DataSource.class)
@ConditionalOnBean(PlatformTransactionManager.class)
@ConditionalOnProperty(prefix = ConfigContextConstants.PROPERTIES_PREFIX, value = "enable", havingValue = "true")
@Import({ ConfigCacheConfiguration.class, ConfigPersisterConfiguration.class,
        ConfigAPIClientConfiguration.class })
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
    
    private TransactionTemplate transactionTemplate;
    
    /** <默认构造函数> */
    public ConfigContextAutoConfiguration(ConfigContextProperties properties,
            PlatformTransactionManager transactionManager) {
        super();
        
        this.properties = properties;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
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
     * 本地配置属性持久层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return LocalConfigPropertyPersister [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "localConfigPropertyManager")
    public LocalConfigPropertyManager localConfigPropertyManager(
            ConfigPropertyItemService configPropertyItemService) {
        LocalConfigPropertyManager manager = new LocalConfigPropertyManager(
                this.module, configLocation, configPropertyItemService);
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
    @Bean(name = "remoteConfigPropertyManagerRegistry")
    @ConditionalOnBean(ConfigAPIClientRegistry.class)
    public RemoteConfigPropertyManagerRegistry remoteConfigPropertyManagerRegistry(
            ConfigAPIClientRegistry configAPIClientRegistry) {
        RemoteConfigPropertyManagerRegistry registry = new RemoteConfigPropertyManagerRegistry(
                this.module, configAPIClientRegistry);
        return registry;
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
    @Bean(name = "configPropertyManagerComposite")
    @ConditionalOnMissingBean({ ConfigPropertyManagerComposite.class })
    @ConditionalOnBean(RemoteConfigPropertyManagerRegistry.class)
    public ConfigPropertyManagerComposite configPropertyManagerComposite1(
            List<ConfigPropertyManager> managers,
            RemoteConfigPropertyManagerRegistry registry) {
        ConfigPropertyManagerComposite composite = new ConfigPropertyManagerComposite(
                this.module, managers, registry);
        return composite;
    }
    
    /**
     * 当远程的rigsitry不存在，composite又未能成功注册时<br/>
     * <功能详细描述>
     * @param managers
     * @param registry
     * @return [参数说明]
     * 
     * @return ConfigPropertyManagerComposite [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "configPropertyManagerComposite")
    @ConditionalOnMissingBean({ RemoteConfigPropertyManagerRegistry.class,
            ConfigPropertyManagerComposite.class })
    public ConfigPropertyManagerComposite configPropertyManagerComposite2(
            List<ConfigPropertyManager> managers) {
        ConfigPropertyManagerComposite composite = new ConfigPropertyManagerComposite(
                this.module, managers);
        return composite;
    }
    
    /**
     * 配置实体代理工厂<br/>
     * <功能详细描述>
     * @param localConfigPropertyManager
     * @return [参数说明]
     * 
     * @return ConfigEntityProxyFactory [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "configEntityFactory")
    public ConfigEntityFactory configEntityFactory(
            LocalConfigPropertyManager localConfigPropertyManager) {
        ConfigEntityFactory factory = new ConfigEntityFactory(
                this.transactionTemplate, localConfigPropertyManager);
        return factory;
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
            ConfigPropertyManagerComposite composite,
            ConfigEntityFactory configEntityFactory) {
        ConfigContextFactory factory = new ConfigContextFactory();
        factory.setModule(module);
        factory.setComposite(composite);
        factory.setConfigEntityFactory(configEntityFactory);
        
        return factory;
    }
    
    /**
     * 控制层自动配置层
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2019年10月24日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    @Configuration
    @AutoConfigureAfter({ DispatcherServletAutoConfiguration.class })
    public class ControllerAutoConfiguration {
        
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
            ConfigContextAPIController controller = new ConfigContextAPIController(
                    module, configPropertyItemService);
            return controller;
        }
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
}
