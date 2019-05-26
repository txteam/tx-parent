/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年4月30日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.basicdata.dao.DataDictDao;
import com.tx.component.basicdata.dao.impl.DataDictDaoImpl;
import com.tx.component.basicdata.script.BasicDataContextTableInitializer;
import com.tx.component.basicdata.service.DataDictService;
import com.tx.component.configuration.dao.ConfigPropertyItemDao;
import com.tx.component.configuration.dao.impl.ConfigPropertyItemDaoImpl;
import com.tx.component.configuration.script.ConfigContextTableInitializer;
import com.tx.component.configuration.service.ConfigPropertyItemService;
import com.tx.component.configuration.service.impl.ConfigPropertyItemServiceImpl;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.support.MyBatisDaoSupport;

/**
 * 基础数据持久层配置逻辑<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年4月30日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
public class BasicDataPersisterConfiguration {
    
    /** <默认构造函数> */
    public BasicDataPersisterConfiguration() {
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
    @ConditionalOnSingleCandidate(TableDDLExecutor.class)
    @ConditionalOnProperty(prefix = "tx.basicdata", value = "table-auto-initialize", havingValue = "true")
    @ConditionalOnMissingBean(ConfigContextTableInitializer.class)
    public static class BasicDataContextTableInitializerConfiguration {
        
        /** 表ddl自动执行器 */
        private TableDDLExecutor tableDDLExecutor;
        
        /** 基础数据容器初始化构造函数 */
        public BasicDataContextTableInitializerConfiguration(
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
        @Bean("basicdata.tableInitializer")
        public BasicDataContextTableInitializer tableInitializer() {
            BasicDataContextTableInitializer initializer = new BasicDataContextTableInitializer(
                    tableDDLExecutor, true);
            return initializer;
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
        @Bean("config.tableInitializer")
        public ConfigContextTableInitializer tableInitializer() {
            ConfigContextTableInitializer initializer = new ConfigContextTableInitializer(
                    tableDDLExecutor, true);
            return initializer;
        }
    }
    
    /**
     * mybatis持久层逻辑实现<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2019年5月2日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    @Configuration
    @ConditionalOnProperty(prefix = "tx.component.persister", value = "type", havingValue = "mybatis")
    public static class MybatisBasicDataContextPersisterConfiguration
            implements InitializingBean {
        
        /** mybatis属性 */
        @Resource(name = "tx.component.myBatisDaoSupport")
        private MyBatisDaoSupport myBatisDaoSupport;
        
        /** 事务管理器 */
        private TransactionTemplate transactionTemplate;
        
        /** <默认构造函数> */
        public MybatisBasicDataContextPersisterConfiguration(
                PlatformTransactionManager transactionManager) {
            this.transactionTemplate = new TransactionTemplate(
                    transactionManager);
        }
        
        /**
         * @throws Exception
         */
        @Override
        public void afterPropertiesSet() throws Exception {
            AssertUtils.notNull(this.myBatisDaoSupport,
                    "myBatisDaoSupport is null.");
        }
        
        /**
         * 数据字典实例<br/>
         * <功能详细描述>
         * @return
         * @throws Exception [参数说明]
         * 
         * @return DataDictDao [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean("basicdata.dataDictDao")
        @ConditionalOnMissingBean(name = "basicdata.dataDictDao")
        public DataDictDao dataDictDao() throws Exception {
            DataDictDao dao = new DataDictDaoImpl(this.myBatisDaoSupport);
            return dao;
        }
        
        /**
         * 数据字典<br/>
         * <功能详细描述>
         * @return
         * @throws Exception [参数说明]
         * 
         * @return DataDictService [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean("basicdata.dataDictService")
        @ConditionalOnMissingBean(name = "basicdata.dataDictService")
        public DataDictService dataDictService() throws Exception {
            DataDictService service = new DataDictService(dataDictDao(),
                    this.transactionTemplate);
            return service;
        }
        
        /**
         * 数据字典实例<br/>
         * <功能详细描述>
         * @return
         * @throws Exception [参数说明]
         * 
         * @return DataDictDao [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean("basicdata.configPropertyItemDao")
        @ConditionalOnMissingBean(name = "basicdata.configPropertyItemDao")
        public ConfigPropertyItemDao configPropertyItemDao() throws Exception {
            ConfigPropertyItemDao dao = new ConfigPropertyItemDaoImpl(
                    this.myBatisDaoSupport);
            return dao;
        }
        
        /**
         * 数据字典实例<br/>
         * <功能详细描述>
         * @return
         * @throws Exception [参数说明]
         * 
         * @return DataDictDao [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean("basicdata.configPropertyItemService")
        @ConditionalOnMissingBean(name = "basicdata.configPropertyItemService")
        public ConfigPropertyItemService configPropertyItemService()
                throws Exception {
            ConfigPropertyItemService service = new ConfigPropertyItemServiceImpl(
                    configPropertyItemDao());
            return service;
        }
    }
    
    //    /**
    //     * mybatis持久层逻辑实现<br/>
    //     * <功能详细描述>
    //     * 
    //     * @author  Administrator
    //     * @version  [版本号, 2019年5月2日]
    //     * @see  [相关类/方法]
    //     * @since  [产品/模块版本]
    //     */
    //    @Configuration
    //    @ConditionalOnProperty(prefix = "tx.basicdata", value = "persister", havingValue = "mybatis")
    //    public static class MybatisBasicDataContextPersisterConfiguration
    //            extends AbstractMybatisConfiguration {
    //        
    //        /** mybatis属性 */
    //        private MybatisProperties properties;
    //        
    //        /** 数据源 */
    //        private DataSource dataSource;
    //        
    //        /** 事务管理器 */
    //        private TransactionTemplate transactionTemplate;
    //        
    //        /** <默认构造函数> */
    //        public MybatisBasicDataContextPersisterConfiguration(
    //                MybatisProperties properties,
    //                ObjectProvider<DatabaseIdProvider> databaseIdProvider,
    //                ObjectProvider<Interceptor[]> interceptorsProvider,
    //                ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider,
    //                DataSource dataSource,
    //                PlatformTransactionManager transactionManager) {
    //            super(databaseIdProvider, interceptorsProvider,
    //                    configurationCustomizersProvider);
    //            
    //            this.properties = (MybatisProperties) properties.clone();
    //            this.properties.setMapperLocations(new String[] {
    //                    "classpath*:com/tx/component/basicdata/dao/impl/*SqlMap_BASICDATA.xml",
    //                    "classpath*:com/tx/component/configuration/dao/impl/*SqlMap_BASICDATA.xml" });
    //            
    //            this.dataSource = dataSource;
    //            this.transactionTemplate = new TransactionTemplate(
    //                    transactionManager);
    //        }
    //        
    //        /**
    //         * 构造sqlSessionFactory
    //         * <功能详细描述>
    //         * @return
    //         * @throws Exception [参数说明]
    //         * 
    //         * @return SqlSessionFactory [返回类型说明]
    //         * @exception throws [异常类型] [异常说明]
    //         * @see [类、类#方法、类#成员]
    //         */
    //        @Bean(name = "basicdata.sqlSessionFactory")
    //        @ConditionalOnMissingBean(name = "basicdata.sqlSessionFactory")
    //        public SqlSessionFactory sqlSessionFactory() throws Exception {
    //            SqlSessionFactory factory = MyBatisDaoSupportHelper
    //                    .buildSqlSessionFactory(this.dataSource,
    //                            this.properties,
    //                            this.databaseIdProvider,
    //                            this.interceptors,
    //                            this.configurationCustomizers,
    //                            this.resourceLoader);
    //            return factory;
    //        }
    //        
    //        /**
    //         * 构造sqlSessionTemplate
    //         * <功能详细描述>
    //         * @return
    //         * @throws Exception [参数说明]
    //         * 
    //         * @return SqlSessionTemplate [返回类型说明]
    //         * @exception throws [异常类型] [异常说明]
    //         * @see [类、类#方法、类#成员]
    //         */
    //        @Bean(name = "basicdata.sqlSessionTemplate")
    //        @ConditionalOnMissingBean(name = "basicdata.sqlSessionTemplate")
    //        public SqlSessionTemplate sqlSessionTemplate() throws Exception {
    //            ExecutorType executorType = this.properties.getExecutorType();
    //            if (executorType != null) {
    //                return new SqlSessionTemplate(sqlSessionFactory(),
    //                        executorType);
    //            } else {
    //                return new SqlSessionTemplate(sqlSessionFactory());
    //            }
    //        }
    //        
    //        /**
    //         * 注册myBatisDaoSupport类<br/>
    //         * <功能详细描述>
    //         * @param sqlSessionFactory
    //         * @return [参数说明]
    //         * 
    //         * @return MyBatisDaoSupport [返回类型说明]
    //         * @exception throws [异常类型] [异常说明]
    //         * @see [类、类#方法、类#成员]
    //         */
    //        @Bean("basicdata.myBatisDaoSupport")
    //        @ConditionalOnMissingBean(name = "basicdata.myBatisDaoSupport")
    //        public MyBatisDaoSupport myBatisDaoSupport() throws Exception {
    //            MyBatisDaoSupport myBatisDaoSupport = new MyBatisDaoSupport(
    //                    sqlSessionTemplate());
    //            return myBatisDaoSupport;
    //        }
    //        
    //        /**
    //         * 数据字典实例<br/>
    //         * <功能详细描述>
    //         * @return
    //         * @throws Exception [参数说明]
    //         * 
    //         * @return DataDictDao [返回类型说明]
    //         * @exception throws [异常类型] [异常说明]
    //         * @see [类、类#方法、类#成员]
    //         */
    //        @Bean("basicdata.dataDictDao")
    //        @ConditionalOnMissingBean(name = "basicdata.dataDictDao")
    //        public DataDictDao dataDictDao() throws Exception {
    //            DataDictDao dao = new DataDictDaoImpl(myBatisDaoSupport());
    //            return dao;
    //        }
    //        
    //        /**
    //         * 数据字典<br/>
    //         * <功能详细描述>
    //         * @return
    //         * @throws Exception [参数说明]
    //         * 
    //         * @return DataDictService [返回类型说明]
    //         * @exception throws [异常类型] [异常说明]
    //         * @see [类、类#方法、类#成员]
    //         */
    //        @Bean("basicdata.dataDictService")
    //        @ConditionalOnMissingBean(name = "basicdata.dataDictService")
    //        public DataDictService dataDictService() throws Exception {
    //            DataDictService service = new DataDictService(dataDictDao(),
    //                    this.transactionTemplate);
    //            return service;
    //        }
    //        
    //        /**
    //         * 数据字典实例<br/>
    //         * <功能详细描述>
    //         * @return
    //         * @throws Exception [参数说明]
    //         * 
    //         * @return DataDictDao [返回类型说明]
    //         * @exception throws [异常类型] [异常说明]
    //         * @see [类、类#方法、类#成员]
    //         */
    //        @Bean("basicdata.configPropertyItemDao")
    //        @ConditionalOnMissingBean(name = "basicdata.configPropertyItemDao")
    //        public ConfigPropertyItemDao configPropertyItemDao() throws Exception {
    //            ConfigPropertyItemDao dao = new ConfigPropertyItemDaoImpl(
    //                    myBatisDaoSupport());
    //            return dao;
    //        }
    //        
    //        /**
    //         * 数据字典实例<br/>
    //         * <功能详细描述>
    //         * @return
    //         * @throws Exception [参数说明]
    //         * 
    //         * @return DataDictDao [返回类型说明]
    //         * @exception throws [异常类型] [异常说明]
    //         * @see [类、类#方法、类#成员]
    //         */
    //        @Bean("basicdata.configPropertyItemService")
    //        @ConditionalOnMissingBean(name = "basicdata.configPropertyItemService")
    //        public ConfigPropertyItemService configPropertyItemService()
    //                throws Exception {
    //            ConfigPropertyItemService service = new ConfigPropertyItemServiceImpl(
    //                    configPropertyItemDao());
    //            return service;
    //        }
    //    }
    //    
    //    @Configuration
    //    @ConditionalOnProperty(prefix = "tx.basicdata", value = "persister", havingValue = "jpa")
    //    public static class JPABasicDataContextPersisterConfiguration {
    //        
    //    }
}
