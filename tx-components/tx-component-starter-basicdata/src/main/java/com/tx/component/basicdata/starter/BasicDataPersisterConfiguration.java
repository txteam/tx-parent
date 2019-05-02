/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年4月30日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import com.tx.component.basicdata.dao.DataDictDao;
import com.tx.component.basicdata.dao.impl.DataDictDaoImpl;
import com.tx.component.basicdata.model.DataDict;
import com.tx.component.basicdata.script.BasicDataContextTableInitializer;
import com.tx.component.basicdata.service.DataDictService;
import com.tx.component.configuration.script.ConfigContextTableInitializer;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.mybatis.support.MyBatisDaoSupportHelper;
import com.tx.core.starter.mybatis.AbstractMybatisConfiguration;
import com.tx.core.starter.mybatis.ConfigurationCustomizer;

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
public class BasicDataPersisterConfiguration
        implements ApplicationContextAware, InitializingBean {
    
    /** spring 容器句柄 */
    private static ApplicationContext applicationContext;
    
    /** 基础数据容器属性 */
    private static BasicDataContextProperties properties;
    
    /** 数据源 */
    private static DataSource dataSource;
    
    /** 事务管理器 */
    private static PlatformTransactionManager transactionManager;
    
    /** 事务管理器 */
    private static TransactionTemplate transactionTemplate;
    
    /** <默认构造函数> */
    public BasicDataPersisterConfiguration(
            BasicDataContextProperties properties) {
        super();
        BasicDataPersisterConfiguration.properties = properties;
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        BasicDataPersisterConfiguration.applicationContext = applicationContext;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (!StringUtils.isEmpty(properties.getPersister().getDataSourceRef())
                && applicationContext.containsBean(
                        properties.getPersister().getDataSourceRef())) {
            dataSource = applicationContext.getBean(
                    properties.getPersister().getDataSourceRef(),
                    DataSource.class);
        } else {
            //如果不是唯一的数据源则跑出异常
            dataSource = applicationContext.getBean(DataSource.class);
            //"dataSource is null.存在多个数据源，需要通过basicdata.dataSource指定使用的数据源,或为数据源设置为Primary."
        }
        
        if (!StringUtils.isEmpty(properties.getPersister().getTransactionManagerRef())
                && applicationContext.containsBean(
                        properties.getPersister().getTransactionManagerRef())) {
            transactionManager = applicationContext.getBean(
                    properties.getPersister().getTransactionManagerRef(),
                    PlatformTransactionManager.class);
        } else {
            //如果不是唯一的数据源则跑出异常
            transactionManager = applicationContext.getBean(PlatformTransactionManager.class);
            //"dataSource is null.存在多个数据源，需要通过basicdata.dataSource指定使用的数据源,或为数据源设置为Primary."
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
        @Bean("basicdata.config.tableInitializer")
        public BasicDataContextTableInitializer tableInitializer() {
            BasicDataContextTableInitializer initializer = new BasicDataContextTableInitializer(
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
    @ConditionalOnExpression("'mybatis'.equals('${tx.basicdata.persist.type}')")
    public static class MybatisBasicDataContextPersisterConfiguration
            extends AbstractMybatisConfiguration {
        
        /** <默认构造函数> */
        public MybatisBasicDataContextPersisterConfiguration(
                ObjectProvider<DatabaseIdProvider> databaseIdProvider,
                ObjectProvider<Interceptor[]> interceptorsProvider,
                ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
            super(databaseIdProvider, interceptorsProvider,
                    configurationCustomizersProvider);
        }
        
        /**
         * 构造sqlSessionFactory
         * <功能详细描述>
         * @return
         * @throws Exception [参数说明]
         * 
         * @return SqlSessionFactory [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean(name = "basicdata.sqlSessionFactory")
        @ConditionalOnMissingBean(name = "basicdata.sqlSessionFactory")
        public SqlSessionFactory sqlSessionFactory() throws Exception {
            SqlSessionFactory factory = MyBatisDaoSupportHelper
                    .buildSqlSessionFactory(dataSource,
                            properties.getPersister().getMybatis(),
                            databaseIdProvider,
                            interceptors,
                            configurationCustomizers,
                            resourceLoader);
            return factory;
        }
        
        /**
         * 构造sqlSessionTemplate
         * <功能详细描述>
         * @return
         * @throws Exception [参数说明]
         * 
         * @return SqlSessionTemplate [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean(name = "basicdata.sqlSessionTemplate")
        @ConditionalOnMissingBean(name = "basicdata.sqlSessionTemplate")
        public SqlSessionTemplate sqlSessionTemplate() throws Exception {
            ExecutorType executorType = properties.getPersister()
                    .getMybatis()
                    .getExecutorType();
            if (executorType != null) {
                return new SqlSessionTemplate(sqlSessionFactory(),
                        executorType);
            } else {
                return new SqlSessionTemplate(sqlSessionFactory());
            }
        }
        
        /**
         * 注册myBatisDaoSupport类<br/>
         * <功能详细描述>
         * @param sqlSessionFactory
         * @return [参数说明]
         * 
         * @return MyBatisDaoSupport [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean("basicdata.myBatisDaoSupport")
        @ConditionalOnMissingBean(name = "basicdata.myBatisDaoSupport")
        public MyBatisDaoSupport myBatisDaoSupport() throws Exception {
            MyBatisDaoSupport myBatisDaoSupport = new MyBatisDaoSupport(
                    sqlSessionTemplate());
            return myBatisDaoSupport;
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
        @Bean("dataDictDao")
        public DataDictDao dataDictDao() throws Exception {
            DataDictDao dao = new DataDictDaoImpl(myBatisDaoSupport());
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
        @Bean("dataDictService")
        public DataDictService dataDictService() throws Exception{
            DataDictService service = new DataDictService(dataDictDao(), transactionTemplate);
            return service;
        }
    }
    
    @Configuration
    @ConditionalOnExpression("'jpa'.equals('${tx.basicdata.persist.type}')")
    public static class JPABasicDataContextPersisterConfiguration {
        
    }
}
