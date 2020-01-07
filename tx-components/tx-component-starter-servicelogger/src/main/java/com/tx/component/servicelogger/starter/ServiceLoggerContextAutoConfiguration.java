/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.servicelogger.starter;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.servicelogger.ServiceLoggerConstants;
import com.tx.component.servicelogger.context.ServiceLoggerContextFactory;
import com.tx.component.servicelogger.support.ServiceLoggerRegistry;
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
@EnableConfigurationProperties(ServiceLoggerContextProperties.class)
@ConditionalOnClass({ ServiceLoggerContextFactory.class })
@ConditionalOnSingleCandidate(DataSource.class)
@ConditionalOnBean(PlatformTransactionManager.class)
@ConditionalOnProperty(prefix = ServiceLoggerConstants.PROPERTIES_PREFIX, value = "enable", havingValue = "true")
@Import({ ServiceLoggerPersisterConfiguration.class })
public class ServiceLoggerContextAutoConfiguration
        implements ApplicationContextAware, InitializingBean {
    
    /** 包名 */
    protected String basePackages = "com.tx";
    
    /** 属性文件 */
    protected ServiceLoggerContextProperties properties;
    
    /** spring 容器句柄 */
    protected ApplicationContext applicationContext;
    
    /** 容器所属模块：当该值为空时，使用spring.application.name的内容 */
    protected String module;
    
    /** application.name */
    @Value(value = "${spring.application.name}")
    private String applicationName;
    
    /** <默认构造函数> */
    public ServiceLoggerContextAutoConfiguration(
            ServiceLoggerContextProperties properties) {
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
        if (!StringUtils.isEmpty(this.properties.getBasePackages())) {
            this.basePackages = this.properties.getBasePackages();
        }
        
        if (!StringUtils.isBlank(this.properties.getModule())) {
            this.module = this.properties.getModule();
        }
        if (!StringUtils.isBlank(this.applicationName)) {
            this.module = this.applicationName;
        }
    }
    
    /**
     * 基础数据容器<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return BasicDataContextFactory [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "serviceLoggerContext")
    public ServiceLoggerContextFactory serviceLoggerContext(
            ServiceLoggerRegistry serviceLoggerRegistry) {
        ServiceLoggerContextFactory context = new ServiceLoggerContextFactory();
        context.setServiceLoggerRegistry(serviceLoggerRegistry);
        
        return context;
    }
    
}
