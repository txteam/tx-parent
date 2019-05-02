/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月3日
 * <修改描述:>
 */
package com.tx.core.starter.mybatis;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.mybatis.support.MyBatisDaoSupportHelper;
import com.tx.core.starter.persister.PersisterProperties;

/**
 * mybatisSupport自动配置类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@org.springframework.context.annotation.Configuration
@ConditionalOnClass({ SqlSessionFactory.class, SqlSessionFactoryBean.class })
@ConditionalOnSingleCandidate(DataSource.class)
@EnableConfigurationProperties(PersisterProperties.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@Import({ MybatisPluginConfiguration.class })
public class MybatisAutoConfiguration extends AbstractMybatisConfiguration
        implements InitializingBean, ApplicationContextAware {
    
    /** 持久层配置属性 */
    protected final PersisterProperties properties;
    
    /** mybatis配置 */
    protected final MybatisProperties mybatisProperties;
    
    /** 数据源 */
    protected DataSource dataSource;
    
    /** <默认构造函数> */
    public MybatisAutoConfiguration(PersisterProperties properties,
            ObjectProvider<DatabaseIdProvider> databaseIdProvider,
            ObjectProvider<Interceptor[]> interceptorsProvider,
            ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
        super(databaseIdProvider, interceptorsProvider,
                configurationCustomizersProvider);
        
        this.properties = properties;
        this.mybatisProperties = this.properties.getMybatis();
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
        checkConfigFileExists();
        
        if (!StringUtils.isEmpty(this.properties.getDataSourceRef())
                && this.applicationContext
                        .containsBean(this.properties.getDataSourceRef())) {
            this.dataSource = this.applicationContext.getBean(
                    this.properties.getDataSourceRef(), DataSource.class);
        } else {
            //如果不是唯一的数据源则跑出异常
            this.dataSource = this.applicationContext.getBean(DataSource.class);
            //"dataSource is null.存在多个数据源，需要通过basicdata.dataSource指定使用的数据源,或为数据源设置为Primary."
        }
    }
    
    /**
     * 检查配置文件<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void checkConfigFileExists() {
        if (this.mybatisProperties.isCheckConfigLocation() && StringUtils
                .hasText(this.mybatisProperties.getConfigLocation())) {
            Resource resource = this.resourceLoader
                    .getResource(this.mybatisProperties.getConfigLocation());
            Assert.state(resource.exists(),
                    "Cannot find config location: " + resource
                            + " (please add config file or check your Mybatis configuration)");
        }
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
    @Bean(name = "sqlSessionFactory")
    @ConditionalOnMissingBean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactory factory = MyBatisDaoSupportHelper
                .buildSqlSessionFactory(dataSource,
                        mybatisProperties,
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
    @Bean(name = "sqlSessionTemplate")
    @ConditionalOnMissingBean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        ExecutorType executorType = this.mybatisProperties.getExecutorType();
        if (executorType != null) {
            return new SqlSessionTemplate(sqlSessionFactory(), executorType);
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
    @Bean("myBatisDaoSupport")
    @ConditionalOnMissingBean(name = "myBatisDaoSupport")
    public MyBatisDaoSupport myBatisDaoSupport() throws Exception {
        MyBatisDaoSupport myBatisDaoSupport = new MyBatisDaoSupport(
                sqlSessionTemplate());
        return myBatisDaoSupport;
    }
    
    //    /**
    //     * 实体自动持久化注册机<br/>
    //     * <功能详细描述>
    //     * @return [参数说明]
    //     * 
    //     * @return EntityDaoRegistrar [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    @ConditionalOnMissingBean
    //    @Bean("entityDaoRegistrar")
    //    public EntityDaoRegistry entityDaoRegistrar() {
    //        EntityDaoRegistry bean = new EntityDaoRegistry(
    //                this.mybatisProperties.getBasePackages(),
    //                myBatisDaoSupport());
    //        return bean;
    //    }
    //    
    //  /**
    //     * This will just scan the same base package as Spring Boot does. If you want
    //     * more power, you can explicitly use
    //     * {@link org.mybatis.spring.annotation.MapperScan} but this will get typed
    //     * mappers working correctly, out-of-the-box, similar to using Spring Data JPA
    //     * repositories.
    //     */
    //    public static class AutoConfiguredMapperScannerRegistrar
    //        implements BeanFactoryAware, ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    //
    //      private BeanFactory beanFactory;
    //
    //      private ResourceLoader resourceLoader;
    //
    //      @Override
    //      public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
    //
    //        if (!AutoConfigurationPackages.has(this.beanFactory)) {
    //          logger.debug("Could not determine auto-configuration package, automatic mapper scanning disabled.");
    //          return;
    //        }
    //
    //        logger.debug("Searching for mappers annotated with @Mapper");
    //
    //        List<String> packages = AutoConfigurationPackages.get(this.beanFactory);
    //        if (logger.isDebugEnabled()) {
    //          packages.forEach(pkg -> logger.debug("Using auto-configuration base package '{}'", pkg));
    //        }
    //
    //        ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);
    //        if (this.resourceLoader != null) {
    //          scanner.setResourceLoader(this.resourceLoader);
    //        }
    //        scanner.setAnnotationClass(Mapper.class);
    //        scanner.registerFilters();
    //        scanner.doScan(StringUtils.toStringArray(packages));
    //
    //      }
    //
    //      @Override
    //      public void setBeanFactory(BeanFactory beanFactory) {
    //        this.beanFactory = beanFactory;
    //      }
    //
    //      @Override
    //      public void setResourceLoader(ResourceLoader resourceLoader) {
    //        this.resourceLoader = resourceLoader;
    //      }
    //    }
    //
    //    /**
    //     * {@link org.mybatis.spring.annotation.MapperScan} ultimately ends up
    //     * creating instances of {@link MapperFactoryBean}. If
    //     * {@link org.mybatis.spring.annotation.MapperScan} is used then this
    //     * auto-configuration is not needed. If it is _not_ used, however, then this
    //     * will bring in a bean registrar and automatically register components based
    //     * on the same component-scanning path as Spring Boot itself.
    //     */
    //    @org.springframework.context.annotation.Configuration
    //    @Import({ AutoConfiguredMapperScannerRegistrar.class })
    //    @ConditionalOnMissingBean(MapperFactoryBean.class)
    //    public static class MapperScannerRegistrarNotFoundConfiguration implements InitializingBean {
    //
    //      @Override
    //      public void afterPropertiesSet() {
    //        logger.debug("No {} found.", MapperFactoryBean.class.getName());
    //      }
    //    }
}
