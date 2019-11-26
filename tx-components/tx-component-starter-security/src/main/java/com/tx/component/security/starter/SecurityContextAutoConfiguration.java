/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月9日
 * <修改描述:>
 */
package com.tx.component.security.starter;

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

import com.tx.component.auth.configuration.AuthContextConfiguration;
import com.tx.component.role.configuration.RoleContextConfiguration;
import com.tx.component.security.SecurityContextConstants;
import com.tx.component.security.context.SecurityContextFactory;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.starter.component.ComponentSupportAutoConfiguration;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
@AutoConfigureAfter({ ComponentSupportAutoConfiguration.class })
@EnableConfigurationProperties(SecurityContextProperties.class)
@ConditionalOnProperty(prefix = SecurityContextConstants.PROPERTIES_PREFIX, value = "enable", havingValue = "true")
@ConditionalOnClass({ SecurityContextFactory.class })
@ConditionalOnSingleCandidate(DataSource.class)
@ConditionalOnBean(PlatformTransactionManager.class)
@Import({ SecurityContextCacheConfiguration.class,
        RoleContextConfiguration.class, AuthContextConfiguration.class })
public class SecurityContextAutoConfiguration
        implements InitializingBean, ApplicationContextAware {
    
    protected ApplicationContext applicationContext;
    
    /** 任务容器属性 */
    protected SecurityContextProperties properties;
    
    /** 容器所属模块：当该值为空时，使用spring.application.name的内容 */
    private String module;
    
    /** application.name */
    @Value(value = "${spring.application.name}")
    private String applicationName;
    
    /** <默认构造函数> */
    public SecurityContextAutoConfiguration(
            SecurityContextProperties properties) {
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
        if (!StringUtils.isBlank(this.applicationName)) {
            this.module = this.applicationName;
        }
        if (!StringUtils.isEmpty(this.properties.getModule())) {
            this.module = this.properties.getModule();
        }
        AssertUtils.notEmpty(this.module, "module is empty.");
    }
    
    @Bean("securityContext")
    public SecurityContextFactory securityContext() {
        SecurityContextFactory factory = new SecurityContextFactory();
        return factory;
    }
    
    //    @Bean("securityContext.webSecurityConfiguration")
    //    public SecurityContextWebConfiguration configuration(){
    //        SecurityContextWebConfiguration configuration = new SecurityContextWebConfiguration();
    //        return configuration;
    //    }
    
}
