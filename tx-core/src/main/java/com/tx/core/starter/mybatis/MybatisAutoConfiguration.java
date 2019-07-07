/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月3日
 * <修改描述:>
 */
package com.tx.core.starter.mybatis;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.mybatis.support.MyBatisDaoSupportHelper;
import com.tx.core.starter.util.CoreAutoConfiguration;

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
@EnableConfigurationProperties(MybatisProperties.class)
@AutoConfigureAfter({ CoreAutoConfiguration.class,
        TransactionAutoConfiguration.class })
@Import({ MybatisPluginConfiguration.class })
public class MybatisAutoConfiguration extends AbstractMybatisConfiguration
        implements InitializingBean, ApplicationContextAware {
    
    /** mybatis配置 */
    protected final MybatisProperties properties;
    
    /** 数据源 */
    protected final DataSource dataSource;
    
    /** <默认构造函数> */
    public MybatisAutoConfiguration(MybatisProperties properties,
            ObjectProvider<DatabaseIdProvider> databaseIdProvider,
            ObjectProvider<Interceptor[]> interceptorsProvider,
            ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider,
            DataSource dataSource) {
        super(databaseIdProvider, interceptorsProvider,
                configurationCustomizersProvider);
        
        this.properties = properties;
        this.dataSource = dataSource;
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
        if (this.properties.isCheckConfigLocation()
                && StringUtils.hasText(this.properties.getConfigLocation())) {
            Resource resource = this.resourceLoader
                    .getResource(this.properties.getConfigLocation());
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
    @Primary
    @Bean(name = "sqlSessionFactory")
    @ConditionalOnMissingBean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactory factory = MyBatisDaoSupportHelper
                .buildSqlSessionFactory(dataSource,
                        properties,
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
    @Primary
    @Bean(name = "sqlSessionTemplate")
    @ConditionalOnMissingBean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        ExecutorType executorType = this.properties.getExecutorType();
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
    @Primary
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
    
    /**
     * Spring的Mapper自动扫描实现<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2019年5月2日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    @org.springframework.context.annotation.Configuration
    @Import({ AutoConfiguredMapperScannerRegistrar.class })
    @ConditionalOnMissingBean(MapperFactoryBean.class)
    public static class MapperScannerRegistrarNotFoundConfiguration
            implements InitializingBean {
        
        @Override
        public void afterPropertiesSet() {
            logger.debug("No {} found.", MapperFactoryBean.class.getName());
        }
    }
    
    /**
     * 自动扫描Mapper注解生成MybatisMapper实现<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2019年5月2日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public static class AutoConfiguredMapperScannerRegistrar
            implements BeanFactoryAware, ImportBeanDefinitionRegistrar,
            ResourceLoaderAware {
        
        private BeanFactory beanFactory;
        
        private ResourceLoader resourceLoader;
        
        @Override
        public void registerBeanDefinitions(
                AnnotationMetadata importingClassMetadata,
                BeanDefinitionRegistry registry) {
            
            if (!AutoConfigurationPackages.has(this.beanFactory)) {
                logger.debug(
                        "Could not determine auto-configuration package, automatic mapper scanning disabled.");
                return;
            }
            
            logger.debug("Searching for mappers annotated with @Mapper");
            
            List<String> packages = AutoConfigurationPackages
                    .get(this.beanFactory);
            if (logger.isDebugEnabled()) {
                packages.forEach(pkg -> logger.debug(
                        "Using auto-configuration base package '{}'", pkg));
            }
            
            ClassPathMapperScanner scanner = new ClassPathMapperScanner(
                    registry);
            if (this.resourceLoader != null) {
                scanner.setResourceLoader(this.resourceLoader);
            }
            scanner.setAnnotationClass(Mapper.class);
            scanner.registerFilters();
            scanner.doScan(StringUtils.toStringArray(packages));
            
        }
        
        @Override
        public void setBeanFactory(BeanFactory beanFactory) {
            this.beanFactory = beanFactory;
        }
        
        @Override
        public void setResourceLoader(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader;
        }
    }
}
