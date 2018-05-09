/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月2日
 * <修改描述:>
 */
package com.tx.component.statistical.context;

import com.tx.component.statistical.dao.StatisticalReportDao;
import com.tx.component.statistical.dao.impl.StatisticalReportDaoImpl;
import com.tx.component.statistical.mybatismapping.StatisticalMapperAssistantRepository;
import com.tx.component.statistical.service.StatisticalReportService;
import com.tx.component.statistical.service.impl.StatisticalReportServiceImpl;
import com.tx.core.exceptions.util.AssertUtils;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.Map;

/**
 * 模板引擎容器配置器<br/>
 * <功能详细描述>
 *
 * @author Administrator
 * @version [版本号, 2016年10月2日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@org.springframework.context.annotation.Configuration
public class StatisticalReportConfigurator implements ApplicationContextAware,
        InitializingBean, ResourceLoaderAware, BeanFactoryAware {

    /**
     * spring容器
     */
    protected static ApplicationContext applicationContext;

      /* *** 系统启动注入 start *** */
    /**
     * resourceLoader资源加载器
     */
    protected static ResourceLoader resourceLoader;
    protected static Resource[] reportResources = null;
    /**
     * 单例对象注册器
     */
    protected SingletonBeanRegistry singletonBeanRegistry;
    /**
     * bean定义注册器
     */
    protected BeanDefinitionRegistry beanDefinitionRegistry;

    /* *** 系统启动注入 end   *** */
    /**
     * mybatis配置文件
     */
//    protected String[] mybatisMapperLocations = new String[]{};
//    protected MyBatisDaoSupport myBatisDaoSupport;
    protected Configuration configuration;
    /* 依赖注入对象创建的 */
    /* *** 容器固定配置 start *** */
//    protected TypeHandlerRegistry typeHandlerRegistry;

    /* *** 容器固定配置 end   *** */
    /**
     * mybatis容器配置
     */
//    protected String mybatisConfigLocation = "classpath:context/mybatis-config.xml";
//    @javax.annotation.Resource
    private SqlSessionTemplate sqlSessionTemplate;
    /**
     * 数据库类型
     */
//    protected DataSourceTypeEnum dataSourceType = DataSourceTypeEnum.MySQL5InnoDBDialect;

//    protected TableDDLExecutor tableDDLExecutor;

    /* -------------- 待注入 ---------------- */
//    /**
//     * 数据源
//     */
//    protected DataSource dataSource;
//    /**
//     * jdbcTemplate句柄
//     */
//    protected JdbcTemplate jdbcTemplate;
//    /**
//     * transactionManager
//     */
//    protected PlatformTransactionManager transactionManager;
//    /**
//     * transactionTemplate: 如果存在事务则在当前事务中执行
//     */
//    protected TransactionTemplate transactionTemplate;
//    /**
//     * 缓存管理器
//     */
//    protected CacheManager cacheManager;
    private String reportConfig = "classpath:statistical/*/*Report.xml";

    public static Resource[] getReportResources() {
        return reportResources;
    }

    @Bean(name = "StatisticalReport.StatisticalMapperAssistantRepository")
    public StatisticalMapperAssistantRepository statisticalMapperAssistantRepository() {
        //获取容器中注册的mapper助手工厂类
        StatisticalMapperAssistantRepository reposity = new StatisticalMapperAssistantRepository(
                this.configuration);
        return reposity;
    }

    @Bean(name = "StatisticalReport.StatisticalReportService")
    public StatisticalReportService statisticalReportService() {
        StatisticalReportService statisticalReportService = new StatisticalReportServiceImpl();
        return statisticalReportService;
    }

    @Bean(name = "StatisticalReport.statisticalReportDao")
    public StatisticalReportDao statisticalReportDao() {
        StatisticalReportDao statisticalReportDao = new StatisticalReportDaoImpl();
        return statisticalReportDao;
    }

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
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        StatisticalReportConfigurator.applicationContext = applicationContext;
    }

    /**
     * @param resourceLoader
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        StatisticalReportConfigurator.resourceLoader = resourceLoader;
    }

    /**
     * @param beanName
     * @param bean
     * @desc 向spring容器注册bean
     */
    protected void registerSingletonBean(String beanName, Object bean) {
        if (!this.singletonBeanRegistry.containsSingleton(beanName)) {
            this.singletonBeanRegistry.registerSingleton(beanName, bean);
        }
    }

    /**
     * @throws Exception
     */
    @Override
    public final void afterPropertiesSet() throws Exception {

        Map<String, SqlSessionTemplate> SqlSessionTemplateMap = applicationContext.getBeansOfType(SqlSessionTemplate.class);
        this.sqlSessionTemplate = (SqlSessionTemplate) SqlSessionTemplateMap.values().toArray()[0];
        this.configuration = sqlSessionTemplate.getSqlSessionFactory().getConfiguration();
//        AssertUtils.notNull(this.dataSource == null
//                        || this.jdbcTemplate == null,
//                "dataSource and jdbcTemplate is all null.");
//
//        if (this.dataSource == null) {
//            this.dataSource = this.jdbcTemplate.getDataSource();
//        }
//        if (this.jdbcTemplate == null) {
//            this.jdbcTemplate = new JdbcTemplate(this.dataSource);
//        }
//        if (this.transactionManager == null) {
//            this.transactionManager = new DataSourceTransactionManager(
//                    this.dataSource);
//        }
//        if (this.transactionTemplate == null) {
//            this.transactionTemplate = new TransactionTemplate(
//                    this.transactionManager);
//        }
//        if (this.cacheManager == null) {
//            this.cacheManager = new ConcurrentMapCacheManager();
//        }
//        if (this.myBatisDaoSupport == null) {
//            this.myBatisDaoSupport = MyBatisDaoSupportHelper.buildMyBatisDaoSupport(mybatisConfigLocation,
//                    mybatisMapperLocations,
//                    dataSourceType,
//                    dataSource);
//            this.configuration = this.myBatisDaoSupport.getSqlSessionFactory()
//                    .getConfiguration();
//            this.typeHandlerRegistry = this.configuration.getTypeHandlerRegistry();
//        }
//        if (this.tableDDLExecutor == null) {
//            this.tableDDLExecutor = TableDDLExecutorFactory.buildTableDDLExecutor(this.dataSourceType,
//                    this.dataSource);
//        }
//
//        registerSingletonBean("statisticalReport.dataSource", this.dataSource);
//        registerSingletonBean("statisticalReport.jdbcTemplate", this.jdbcTemplate);

//        registerSingletonBean("statisticalReport.myBatisDaoSupport",
//                this.myBatisDaoSupport);

//        registerSingletonBean("statisticalReport.cacheManager", this.cacheManager);

        loadReportResource();
    }

    private void loadReportResource() {
        try {
            reportResources = applicationContext.getResources(reportConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getReportConfig() {
        return reportConfig;
    }

    public void setReportConfig(String reportConfig) {
        this.reportConfig = reportConfig;
    }
}
