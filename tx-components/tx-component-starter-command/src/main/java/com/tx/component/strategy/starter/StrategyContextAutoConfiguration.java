/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月1日
 * <修改描述:>
 */
package com.tx.component.strategy.starter;


import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.command.context.CommandContext;
import com.tx.component.strategy.context.StrategyContext;
import com.tx.component.strategy.context.StrategyContextFactory;
import com.tx.core.starter.component.ComponentSupportAutoConfiguration;

/**
 * 命令容器配置器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
@EnableConfigurationProperties(value = StrategyContextProperties.class)
@ConditionalOnClass({ CommandContext.class })
@ConditionalOnSingleCandidate(DataSource.class)
@ConditionalOnBean(PlatformTransactionManager.class)
@AutoConfigureAfter({ComponentSupportAutoConfiguration.class})
@ConditionalOnProperty(prefix = "tx.component.strategy", value = "enable", havingValue = "true")
public class StrategyContextAutoConfiguration
        implements ApplicationContextAware, InitializingBean {
    
    protected ApplicationContext applicationContext;
    
    protected final StrategyContextProperties properties;
    
    @SuppressWarnings("unused")
    private final PlatformTransactionManager transactionManager;
    
    /** <默认构造函数> */
    public StrategyContextAutoConfiguration(StrategyContextProperties properties,
            PlatformTransactionManager transactionManager) {
        super();
        this.properties = properties;
        this.transactionManager = transactionManager;
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
    }
    
    /**
     * 自动加载策略容器<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return StrategyContextFactory [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean("strategyContext")
    @ConditionalOnMissingBean(StrategyContext.class)
    public StrategyContextFactory strategyContext() {
        StrategyContextFactory factory = new StrategyContextFactory();
        
        return factory;
    }
}
