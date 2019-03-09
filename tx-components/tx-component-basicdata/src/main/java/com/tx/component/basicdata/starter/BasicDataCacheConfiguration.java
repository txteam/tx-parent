/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.basicdata.context.BasicDataContextFactory;
import com.tx.component.basicdata.context.BasicDataServiceRegistry;
import com.tx.component.basicdata.context.BasicDataServiceSupportCacheProxyCreator;
import com.tx.component.basicdata.controller.BasicDataRemoteController;
import com.tx.component.basicdata.controller.BasicDataTypeController;
import com.tx.component.basicdata.dao.DataDictDao;
import com.tx.component.basicdata.dao.impl.DataDictDaoImpl;
import com.tx.component.basicdata.script.BasicDataContextTableInitializer;
import com.tx.component.basicdata.service.BasicDataTypeService;
import com.tx.component.basicdata.service.DataDictService;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.mybatis.support.MyBatisDaoSupportHelper;
import com.tx.core.util.dialect.DataSourceTypeEnum;

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
public class BasicDataCacheConfiguration
        implements ApplicationContextAware, InitializingBean {
    
    /** spring 容器句柄 */
    private ApplicationContext applicationContext;
    
    /** 缓存属性 */
    private BasicDataCacheProperties properties;
    
    /** cacheManager */
    protected CacheManager cacheManager;
    
    /** <默认构造函数> */
    public BasicDataCacheConfiguration(
           BasicDataCacheProperties  properties) {
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
      //设置cacheManager
        if (StringUtils.isNotBlank(this.properties.getCacheManagerRef())
                && this.applicationContext
                        .containsBean(this.properties.getCacheManagerRef())) {
            this.cacheManager = this.cacheManager = this.applicationContext
                    .getBean(CacheManager.class);
        }
        
    }
    
    /**
     * 基础数据类型业务层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return BasicDataTypeService [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "basicdata.basicDataTypeService")
    public BasicDataTypeService basicDataTypeService() {
        BasicDataTypeService basicDataTypeService = new BasicDataTypeService();
        return basicDataTypeService;
    }
    
    /**
     * 数据字典类持久层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return DataDictDao [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "basicdata.dataDictDao")
    public DataDictDao dataDictDao() {
        DataDictDao dao = new DataDictDaoImpl(this.myBatisDaoSupport);
        return dao;
    }
    
    /**
     * 数据字典业务层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return DataDictService [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "basicdata.dataDictService")
    public DataDictService dataDictService(DataDictDao dataDictDao) {
        DataDictService service = new DataDictService(this.dataSource,
                this.transactionTemplate, dataDictDao);
        return service;
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
    @Bean(name = "basicdata.basicDataServiceSupportCacheProxyCreator")
    public BasicDataServiceSupportCacheProxyCreator basicDataServiceSupportCacheProxyCreator() {
        BasicDataServiceSupportCacheProxyCreator creator = new BasicDataServiceSupportCacheProxyCreator(
                this.cacheManager);
        
        return creator;
    }
    
    /**
     * 基础数据容器<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return BasicDataContextFactory [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "basicDataContext")
    public BasicDataContextFactory BasicDataContextFactory() {
        BasicDataContextFactory context = new BasicDataContextFactory();
        
        return context;
    }
    
    /**
     * 基础数据业务层注册机<br/>
     * <功能详细描述>
     * @param basicDataTypeService
     * @param dataDictService
     * @return [参数说明]
     * 
     * @return BasicDataServiceRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @DependsOn(value = "basicDataContext")
    @Bean(name = "basicdata.basicDataServiceRegistry")
    public BasicDataServiceRegistry basicDataServiceRegistry(
            BasicDataTypeService basicDataTypeService,
            DataDictService dataDictService) {
        BasicDataServiceRegistry serviceFactory = new BasicDataServiceRegistry(
                module, basePackages, basicDataTypeService, dataDictService,
                null);
        
        return serviceFactory;
    }
    
    @Bean(name = "basicDataTypeController")
    public BasicDataTypeController basicDataTypeController(
            BasicDataTypeService basicDataTypeService) {
        BasicDataTypeController controller = new BasicDataTypeController(
                basicDataTypeService);
        return controller;
    }
    
    @Bean(name = "basicDataRemoteController")
    public BasicDataRemoteController basicDataRemoteController(
            BasicDataTypeService basicDataTypeService) {
        BasicDataRemoteController controller = new BasicDataRemoteController();
        return controller;
    }
}
