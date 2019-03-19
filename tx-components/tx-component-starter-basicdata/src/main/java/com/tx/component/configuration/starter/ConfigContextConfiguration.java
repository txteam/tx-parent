/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.configuration.starter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tx.component.basicdata.starter.BasicDataPersisterConfig;
import com.tx.component.configuration.dao.ConfigPropertyItemDao;
import com.tx.component.configuration.dao.impl.ConfigPropertyItemDaoImpl;
import com.tx.component.configuration.script.ConfigContextTableInitializer;
import com.tx.component.configuration.service.ConfigPropertyItemService;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
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
@Configuration
public class ConfigContextConfiguration
        implements ApplicationContextAware, InitializingBean {
    
    /** spring 容器句柄 */
    protected ApplicationContext applicationContext;
    
    /** 容器所属模块：当该值为空时，使用spring.application.name的内容 */
    private String module;
    
    /** cacheManager */
    private CacheManager cacheManager;
    
    /** 属性文件 */
    private ConfigContextProperties properties;
    
    /** 持久层配置属性 */
    private BasicDataPersisterConfig persisterConfig;
    
    /** <默认构造函数> */
    public ConfigContextConfiguration(String module,
            ConfigContextProperties properties, CacheManager cacheManager,
            BasicDataPersisterConfig persisterConfig) {
        super();
        this.module = module;
        this.properties = properties;
        this.cacheManager = cacheManager;
        this.persisterConfig = persisterConfig;
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
        AssertUtils.notEmpty(this.module, "module is empty.");
        AssertUtils.notNull(this.cacheManager, "cacheManager is null.");
        AssertUtils.notNull(this.properties, "properties is null.");
        AssertUtils.notNull(this.persisterConfig, "persisterConfig is null.");
    }
    
    /**
     * 配置荣庆初始化配置<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2019年3月7日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    @Configuration
    @ConditionalOnMissingBean(ConfigPropertyItemService.class)
    @AutoConfigureAfter(ConfigContextTableInitializerConfiguration.class)
    public class ConfigContextPersisterConfiguration
            implements InitializingBean {
        
        /**
         * @throws Exception
         */
        @Override
        public void afterPropertiesSet() throws Exception {
            AssertUtils.notNull(persisterConfig, "persisterConfig is null.");
            AssertUtils.notNull(persisterConfig.getTransactionTemplate(),
                    "transactionTemplate is null.");
            AssertUtils.notNull(persisterConfig.getMyBatisDaoSupport(),
                    "persisterConfig is null.");
        }
        
        /**
         * 配置属性业务持久业务层<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return ConfigPropertyItemService [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean("basicdata.config.configPropertyItemService")
        public ConfigPropertyItemService configPropertyItemService() {
            ConfigPropertyItemService service = new ConfigPropertyItemService(
                    persisterConfig.getTransactionTemplate(),
                    configPropertyItemDao());
            return service;
        }
        
        /**
         * 基础数据持久层实现<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return ConfigPropertyItemDao [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean("basicdata.config.configPropertyItemDao")
        public ConfigPropertyItemDao configPropertyItemDao() {
            ConfigPropertyItemDao dao = new ConfigPropertyItemDaoImpl(
                    persisterConfig.getMyBatisDaoSupport());
            return dao;
        }
    }
    
    /**
     * 该类会优先加载:基础数据容器表初始化器<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2018年5月5日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    @Configuration
    @ConditionalOnBean({ TableDDLExecutor.class })
    @ConditionalOnSingleCandidate(TableDDLExecutor.class)
    @ConditionalOnProperty(prefix = "tx.basicdata", value = "table-auto-initialize", havingValue = "true")
    @ConditionalOnMissingBean(ConfigContextTableInitializer.class)
    public static class ConfigContextTableInitializerConfiguration {
        
        /** 表ddl自动执行器 */
        private TableDDLExecutor tableDDLExecutor;
        
        /** 基础数据容器初始化构造函数 */
        public ConfigContextTableInitializerConfiguration(
                TableDDLExecutor tableDDLExecutor) {
            this.tableDDLExecutor = tableDDLExecutor;
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
        @Bean("basicdata.config.tableInitializer")
        public ConfigContextTableInitializer tableInitializer() {
            ConfigContextTableInitializer initializer = new ConfigContextTableInitializer(
                    tableDDLExecutor, true);
            return initializer;
        }
    }
    
}
