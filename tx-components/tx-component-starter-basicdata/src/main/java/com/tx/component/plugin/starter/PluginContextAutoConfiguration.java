/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.plugin.starter;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

import com.tx.component.configuration.ConfigContextConstants;
import com.tx.component.configuration.context.ConfigContext;
import com.tx.component.configuration.starter.ConfigContextAutoConfiguration;
import com.tx.component.plugin.context.PluginContextFactory;
import com.tx.component.plugin.controller.PluginContextAPIController;
import com.tx.component.plugin.service.PluginInstanceService;
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
//只要配置容器启动该容器自动启动
//@ConditionalOnProperty(prefix = PluginContextConstants.PROPERTIES_PREFIX, value = "enable", havingValue = "true")
@ConditionalOnProperty(prefix = ConfigContextConstants.PROPERTIES_PREFIX, value = "enable", havingValue = "true")
@ConditionalOnBean({ PlatformTransactionManager.class, ConfigContext.class })
@EnableConfigurationProperties(PluginContextProperties.class)
@Configuration
@AutoConfigureAfter({ ComponentSupportAutoConfiguration.class,
        ConfigContextAutoConfiguration.class })
@ConditionalOnClass({ PluginContextFactory.class })
@ConditionalOnSingleCandidate(DataSource.class)
@Import({ PluginPersisterConfiguration.class })
public class PluginContextAutoConfiguration
        implements ApplicationContextAware, InitializingBean {
    
    /** spring 容器句柄 */
    protected ApplicationContext applicationContext;
    
    /** application.name */
    @Value(value = "${spring.application.name}")
    private String applicationName;
    
    /** 容器所属模块：当该值为空时，使用spring.application.name的内容 */
    private String module;
    
    /** 属性文件 */
    private PluginContextProperties properties;
    
    /** <默认构造函数> */
    public PluginContextAutoConfiguration(PluginContextProperties properties,
            PlatformTransactionManager transactionManager) {
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
     * 配置容器<br/>
     * <功能详细描述>
     * @param composite
     * @return [参数说明]
     * 
     * @return ConfigContextFactory [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean("pluginContext")
    @DependsOn("configContext")
    public PluginContextFactory pluginContext(
            PluginInstanceService pluginInstanceService) {
        PluginContextFactory factory = new PluginContextFactory();
        factory.setModule(module);
        factory.setPluginInstanceService(pluginInstanceService);
        
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
        public PluginContextAPIController pluginAPIController(
                PluginInstanceService pluginInstanceService) {
            PluginContextAPIController controller = new PluginContextAPIController(
                    pluginInstanceService);
            return controller;
        }
    }
}
