/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.configuration.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tx.component.basicdata.starter.BasicDataContextProperties;
import com.tx.component.basicdata.starter.cache.BasicDataContextCacheConfig;
import com.tx.component.basicdata.starter.persister.BasicDataContextMybatisConfig;
import com.tx.component.configuration.dao.ConfigPropertyItemDao;
import com.tx.component.configuration.dao.impl.ConfigPropertyItemDaoImpl;
import com.tx.component.configuration.script.ConfigContextTableInitializer;
import com.tx.component.configuration.service.ConfigPropertyItemService;
import com.tx.core.ddlutil.executor.TableDDLExecutor;

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
public class ConfigContextPersisterConfiguration {
    
    /** <默认构造函数> */
    public ConfigContextPersisterConfiguration() {
        super();
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
    
    /**
     * 配置容器持久化配置<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2019年3月7日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    @Configuration
    @EnableConfigurationProperties(BasicDataContextProperties.class)
    public static class ConfigContextMybatisPersisterConfiguration {
        
        /** mybatis配置 */
        private BasicDataContextMybatisConfig mybatisConfig;
        
        /** 基础数据容器缓存配置 */
        private BasicDataContextCacheConfig cacheConfig;
        
        /** <默认构造函数> */
        public ConfigContextMybatisPersisterConfiguration(
                BasicDataContextMybatisConfig mybatisConfig,
                BasicDataContextCacheConfig cacheConfig) {
            super();
            this.mybatisConfig = mybatisConfig;
            this.cacheConfig = cacheConfig;
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
        @ConditionalOnMissingBean(ConfigPropertyItemService.class)
        @Bean("basicdata.config.configPropertyItemService")
        public ConfigPropertyItemService configPropertyItemService() {
            ConfigPropertyItemService service = new ConfigPropertyItemService(
                    mybatisConfig.getTransactionTemplate(),
                    configPropertyItemDao(), cacheConfig.getCacheManager());
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
                    mybatisConfig.getMyBatisDaoSupport());
            return dao;
        }
    }
}
