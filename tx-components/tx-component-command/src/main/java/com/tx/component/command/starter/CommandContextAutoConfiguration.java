/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月1日
 * <修改描述:>
 */
package com.tx.component.command.starter;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.command.context.CommandContext;
import com.tx.component.command.context.CommandContextFactory;
import com.tx.component.command.context.HelperFactory;
import com.tx.component.strategy.context.StrategyContext;
import com.tx.component.strategy.context.StrategyContextFactory;

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
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter({ DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        TransactionAutoConfiguration.class })
@ConditionalOnProperty(prefix = "command", value = "enable", havingValue="true")
public class CommandContextAutoConfiguration {
    
    @SuppressWarnings("unused")
    private CommandContextProperties properties;
    
    private DataSource datasource;
    
    private PlatformTransactionManager txManager;
    
    /** <默认构造函数> */
    public CommandContextAutoConfiguration(CommandContextProperties properties,
            DataSource datasource, PlatformTransactionManager txManager) {
        super();
        this.properties = properties;
        this.datasource = datasource;
        this.txManager = txManager;
    }
    
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
        CommandContextFactory factory = new CommandContextFactory();
        factory.setDataSource(this.datasource);
        factory.setTxManager(txManager);
        
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
