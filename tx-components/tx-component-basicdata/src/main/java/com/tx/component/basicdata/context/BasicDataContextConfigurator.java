/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月3日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.basicdata.dao.BasicDataTypeDao;
import com.tx.component.basicdata.dao.DataDictDao;
import com.tx.component.basicdata.dao.impl.BasicDataTypeDaoImpl;
import com.tx.component.basicdata.dao.impl.DataDictDaoImpl;
import com.tx.component.basicdata.service.BasicDataTypeService;
import com.tx.component.basicdata.service.DataDictService;
import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.mybatis.support.MyBatisDaoSupportHelper;

/**
 * 基础数据容器配置器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataContextConfigurator implements ApplicationContextAware,
        InitializingBean, BeanFactoryAware {
    
    @Bean(name = "basicdata.myBatisDaoSupport")
    public MyBatisDaoSupport basicdata_myBatisDaoSupport() throws Exception {
        MyBatisDaoSupport support = MyBatisDaoSupportHelper.buildMyBatisDaoSupport(this.mybatisConfigLocation,
                this.mybatisMapperLocations,
                DataSourceTypeEnum.MYSQL,
                this.dataSource);
        
        return support;
    }
    
    @Bean(name = "basicdata.dataDictDao")
    public DataDictDao dataDictDao() {
        DataDictDao dao = new DataDictDaoImpl();
        return dao;
    }
    
    @Bean(name = "basicdata.basicDataTypeDao")
    public BasicDataTypeDao basicDataTypeDao() {
        BasicDataTypeDao basicDataTypeDao = new BasicDataTypeDaoImpl();
        return basicDataTypeDao;
    }
    
    @Bean(name = "basicdata.basicDataTypeService")
    public BasicDataTypeService basicDataTypeService() {
        BasicDataTypeService basicDataTypeService = new BasicDataTypeService();
        return basicDataTypeService;
    }
    
    @Bean(name = "basicdata.dataDictService")
    public DataDictService dataDictService() {
        DataDictService service = new DataDictService();
        return service;
    }
    
    @DependsOn(value = "basicDataContext")
    @Bean(name = "basicdata.basicDataServiceRegistry")
    public BasicDataServiceRegistry basicDataServiceRegistry() {
        BasicDataServiceRegistry serviceFactory = new BasicDataServiceRegistry(
                this.packages);
        return serviceFactory;
    }
    
    /**
     * 基础数据业务层代理创建器<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return BasicDataServiceProxyCreator [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @Bean(name = "basicdata.basicDataServiceProxyCreator")
    public BasicDataServiceProxyCreator basicDataServiceProxyCreator() {
        BasicDataServiceProxyCreator processor = new BasicDataServiceProxyCreator();
        
        return processor;
    }
    
    @Bean(name = "basicDataContext")
    public BasicDataContextFactory BasicDataContextFactory() {
        BasicDataContextFactory context = new BasicDataContextFactory();
        
        context.setPackages(this.packages);
        context.setMybatisConfigLocation(this.mybatisConfigLocation);
        context.setMybatisMapperLocations(this.mybatisMapperLocations);
        context.setDataSource(this.dataSource);
        context.setJdbcTemplate(this.jdbcTemplate);
        context.setTransactionManager(this.transactionManager);
        context.setTransactionTemplate(this.transactionTemplate);
        
        return context;
    }
    
    /** spring容器句柄 */
    protected static ApplicationContext applicationContext;
    
    /** 包名 */
    protected String packages = "com.tx";
    
    /** mybatis配置文件 */
    protected String mybatisConfigLocation = "classpath:context/mybatis-config.xml";
    
    /** mybatis配置文件 */
    protected String[] mybatisMapperLocations = new String[] { "classpath*:com/tx/component/basicdata/dao/impl/*SqlMap_BASICDATA.xml" };
    
    /** 数据源:dataSource */
    protected DataSource dataSource;
    
    /** jdbcTemplate句柄 */
    protected JdbcTemplate jdbcTemplate;
    
    /** transactionManager */
    protected PlatformTransactionManager transactionManager;
    
    /** transactionTemplate: 如果存在事务则在当前事务中执行 */
    protected TransactionTemplate transactionTemplate;
    
    /** cacheManager */
    protected CacheManager cacheManager;
    
    /** 单例对象注册方法 */
    protected SingletonBeanRegistry singletonBeanRegistry;
    
    protected BeanDefinitionRegistry beanDefinitionRegistry;
    
    /**
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        AssertUtils.isInstanceOf(BeanDefinitionRegistry.class,
                beanFactory,
                "beanFactory is not BeanDefinitionRegistry instance.");
        this.beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;
        
        AssertUtils.isInstanceOf(SingletonBeanRegistry.class,
                beanFactory,
                "beanFactory is not SingletonBeanRegistry instance.");
        this.singletonBeanRegistry = (SingletonBeanRegistry) beanFactory;
    }
    
    /**
     * @desc 向spring容器注册BeanDefinition
     * @param beanName
     * @param beanDefinition
     */
    protected void registerBeanDefinition(String beanName,
            BeanDefinition beanDefinition) {
        if (!this.beanDefinitionRegistry.containsBeanDefinition(beanName)) {
            this.beanDefinitionRegistry.registerBeanDefinition(beanName,
                    beanDefinition);
        }
    }
    
    /**
     * @desc 向spring容器注册bean
     * @param beanName
     * @param beanDefinition
     */
    protected void registerSingletonBean(String beanName, Object bean) {
        if (!this.singletonBeanRegistry.containsSingleton(beanName)) {
            this.singletonBeanRegistry.registerSingleton(beanName, bean);
        }
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public final void setApplicationContext(
            ApplicationContext applicationContext) throws BeansException {
        BasicDataContextConfigurator.applicationContext = applicationContext;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public final void afterPropertiesSet() throws Exception {
        AssertUtils.notTrue(dataSource == null && jdbcTemplate == null,
                "dataSource or jdbcTemplate all is null.");
        
        if (this.dataSource == null) {
            this.dataSource = this.jdbcTemplate.getDataSource();
        }
        if (this.jdbcTemplate == null) {
            this.jdbcTemplate = new JdbcTemplate(this.dataSource);
        }
        if (this.transactionManager == null) {
            this.transactionManager = new DataSourceTransactionManager(
                    this.dataSource);
        }
        if (this.transactionTemplate == null) {
            this.transactionTemplate = new TransactionTemplate(
                    this.transactionManager);
        }
        if (this.cacheManager == null) {
            this.cacheManager = new ConcurrentMapCacheManager();
        }
        
        //初始化包名
        if (StringUtils.isEmpty(packages)) {
            this.packages = "com.tx";
        }
        
        registerSingletonBean("basicdata.cacheManager", this.cacheManager);
        registerSingletonBean("basicdata.dataSource", this.dataSource);
        registerSingletonBean("basicdata.jdbcTemplate", this.jdbcTemplate);
        registerSingletonBean("basicdata.transactionManager",
                this.transactionManager);
        registerSingletonBean("basicdata.transactionTemplate",
                this.transactionTemplate);
        
        //进行容器构建
        doBuild();
        
        //初始化容器
        doInitContext();
    }
    
    /**
      * 基础数据容器构建
      * <功能详细描述>
      * @throws Exception [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void doBuild() throws Exception {
        
    }
    
    protected void doInitContext() throws Exception {
        
    }
    
    /**
     * @param 对packages进行赋值
     */
    public void setPackages(String packages) {
        this.packages = packages;
    }
    
    /**
     * @param 对mybatisConfigLocation进行赋值
     */
    public void setMybatisConfigLocation(String mybatisConfigLocation) {
        this.mybatisConfigLocation = mybatisConfigLocation;
    }
    
    /**
     * @param 对mybatisMapperLocations进行赋值
     */
    public void setMybatisMapperLocations(String[] mybatisMapperLocations) {
        this.mybatisMapperLocations = mybatisMapperLocations;
    }
    
    /**
     * @param 对dataSource进行赋值
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * @param 对jdbcTemplate进行赋值
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * @param 对transactionManager进行赋值
     */
    public void setTransactionManager(
            PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    
    /**
     * @param 对transactionTemplate进行赋值
     */
    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
    
    /**
     * @param 对cacheManager进行赋值
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
    
}
