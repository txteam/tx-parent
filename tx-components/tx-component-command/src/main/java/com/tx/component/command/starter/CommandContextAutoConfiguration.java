/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月1日
 * <修改描述:>
 */
package com.tx.component.command.starter;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.command.context.CommandContext;
import com.tx.component.command.context.CommandContextFactory;
import com.tx.component.command.context.HelperFactory;
import com.tx.component.strategy.context.StrategyContext;
import com.tx.component.strategy.context.StrategyContextFactory;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.starter.util.CoreUtilAutoConfiguration;

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
@EnableConfigurationProperties(value = CommandContextProperties.class)
@ConditionalOnBean({ DataSource.class, PlatformTransactionManager.class })
@AutoConfigureAfter({ CoreUtilAutoConfiguration.class,DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class })
@ConditionalOnProperty(prefix = "tx.command", value = "enable", havingValue = "true")
public class CommandContextAutoConfiguration
        implements ApplicationContextAware, InitializingBean {
    
    private ApplicationContext applicationContext;
    
    private CommandContextProperties properties;
    
    private PlatformTransactionManager transactionManager;
    
    /** <默认构造函数> */
    public CommandContextAutoConfiguration(
            CommandContextProperties properties) {
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
        if (StringUtils.isNotBlank(properties.getTransactionManager())
                && this.applicationContext
                        .containsBean(properties.getTransactionManager())) {
            this.transactionManager = this.applicationContext.getBean(
                    properties.getTransactionManager(),
                    PlatformTransactionManager.class);
        } else {
            this.transactionManager = this.applicationContext
                    .getBean(PlatformTransactionManager.class);
        }
    }
    
    //    /**
    //     * 该类会优先加载<br/>
    //     * <功能详细描述>
    //     * 
    //     * @author  Administrator
    //     * @version  [版本号, 2018年5月5日]
    //     * @see  [相关类/方法]
    //     * @since  [产品/模块版本]
    //     */
    //    @Configuration
    //    @ConditionalOnSingleCandidate(PlatformTransactionManager.class)
    //    public static class CommandContextOnSingleConfiguration {
    //        
    //        private final PlatformTransactionManager txManager;
    //        
    //        public CommandContextOnSingleConfiguration(
    //                PlatformTransactionManager transactionManager) {
    //            this.txManager = transactionManager;
    //        }
    //        
    //        /**
    //         * 当命令容器不存在时<br/>
    //         * <功能详细描述>
    //         * @return [参数说明]
    //         * 
    //         * @return CommandContextFactory [返回类型说明]
    //         * @exception throws [异常类型] [异常说明]
    //         * @see [类、类#方法、类#成员]
    //         */
    //        @Bean("commandContext")
    //        @ConditionalOnMissingBean(CommandContext.class)
    //        public CommandContextFactory commandContext() {
    //            CommandContextFactory factory = new CommandContextFactory();
    //            factory.setTxManager(this.txManager);
    //            
    //            return factory;
    //        }
    //    }
    
    /**
     * 当命令容器不存在时<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return CommandContextFactory [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean("commandContext")
    @ConditionalOnMissingBean(CommandContext.class)
    public CommandContextFactory commandContext() {
        AssertUtils.notNull(this.transactionManager,
                "transactionManager在系统中非唯一.需要手工通过command.transactionManagerBeanName指定transactionManager的BeanName.");
        
        CommandContextFactory factory = new CommandContextFactory();
        factory.setTxManager(this.transactionManager);
        
        return factory;
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
    
    /**
     * 帮助类工厂类<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return HelperFactory [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean("helperFactory")
    public HelperFactory helperFactory() {
        HelperFactory helperFactory = new HelperFactory();
        
        return helperFactory;
    }
}
