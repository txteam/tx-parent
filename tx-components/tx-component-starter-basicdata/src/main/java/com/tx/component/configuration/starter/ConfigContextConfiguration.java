/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.configuration.starter;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.tx.component.basicdata.starter.BasicDataContextProperties;
import com.tx.component.configuration.context.ConfigContextFactory;
import com.tx.component.configuration.persister.ConfigPropertyPersister;
import com.tx.component.configuration.persister.ConfigPropertyPersisterComposite;
import com.tx.component.configuration.persister.impl.LocalConfigPropertyPersister;
import com.tx.component.configuration.persister.impl.RemoteConfigPropertyPersister;
import com.tx.component.configuration.registry.ConfigAPIClientRegistry;
import com.tx.component.configuration.service.ConfigPropertyItemService;
import com.tx.core.exceptions.util.AssertUtils;

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
public class ConfigContextConfiguration
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
    public ConfigContextConfiguration(
            BasicDataContextProperties basicDataContextProperties,
            ConfigContextProperties properties) {
        super();
        
        this.properties = properties;
        this.module = basicDataContextProperties.getModule();
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
        if (StringUtils.isEmpty(this.module)) {
            this.module = this.applicationName;
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
    @Bean
    public LocalConfigPropertyPersister localConfigPropertyPersister(
            ConfigPropertyItemService configPropertyItemService) {
        LocalConfigPropertyPersister persister = new LocalConfigPropertyPersister(
                module, configLocation, configPropertyItemService);
        return persister;
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
    public RemoteConfigPropertyPersister remoteConfigPropertyPersister(
            ConfigAPIClientRegistry configAPIClientRegistry) {
        RemoteConfigPropertyPersister persister = new RemoteConfigPropertyPersister(
                module, configAPIClientRegistry);
        return persister;
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
    public ConfigPropertyPersisterComposite configPropertyPersisterComposite(
            List<ConfigPropertyPersister> persisters) {
        ConfigPropertyPersisterComposite composite = new ConfigPropertyPersisterComposite(
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
            ConfigPropertyPersisterComposite composite) {
        ConfigContextFactory factory = new ConfigContextFactory();
        factory.setComposite(composite);
        factory.setModule(module);
        
        return factory;
    }
}
