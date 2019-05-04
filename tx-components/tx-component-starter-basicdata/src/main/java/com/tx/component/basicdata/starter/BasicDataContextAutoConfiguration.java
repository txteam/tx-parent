/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.basicdata.context.BasicDataContextFactory;
import com.tx.component.basicdata.context.BasicDataEntityRegistry;
import com.tx.component.basicdata.context.BasicDataServiceImportRegistrar;
import com.tx.component.basicdata.controller.BasicDataAPIController;
import com.tx.component.basicdata.registry.BasicDataAPIClientRegistry;
import com.tx.component.basicdata.service.DataDictService;

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
@EnableConfigurationProperties(BasicDataContextProperties.class)
@ConditionalOnClass({ BasicDataContextFactory.class })
@ConditionalOnSingleCandidate(PlatformTransactionManager.class)
@AutoConfigureAfter(TransactionAutoConfiguration.class)
@ConditionalOnProperty(prefix = "tx.basicdata", value = "enable", havingValue = "true")
@Import({ BasicDataPersisterConfiguration.class,
        BasicDataAPIClientConfiguration.class })
public class BasicDataContextAutoConfiguration
        implements InitializingBean, ApplicationContextAware {
    
    /** spring容器句柄　*/
    private ApplicationContext applicationContext;
    
    /** 属性文件 */
    private BasicDataContextProperties properties;
    
    /** 基础包 */
    private String basePackages;
    
    /** 容器所属模块：当该值为空时，使用spring.application.name的内容 */
    private String module;
    
    /** application.name */
    @Value(value = "${spring.application.name}")
    private String applicationName;
    
    /** <默认构造函数> */
    public BasicDataContextAutoConfiguration(
            BasicDataContextProperties properties) {
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
        if (!StringUtils.isEmpty(this.properties.getBasePackages())) {
            this.basePackages = this.properties.getBasePackages();
        }
        //初始化包名
        if (!StringUtils.isBlank(this.applicationName)) {
            this.module = this.applicationName;
        }
        if (!StringUtils.isEmpty(this.properties.getModule())) {
            this.module = this.properties.getModule();
        }
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
    @Bean(name = "basicdata.basicDataEntityRegistry")
    public BasicDataEntityRegistry basicDataServiceRegistry() {
        BasicDataEntityRegistry registry = new BasicDataEntityRegistry();
        
        return registry;
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
        context.setRegistry(basicDataServiceRegistry());
        
        return context;
    }
    
    /**
     * 基础数据业务层ImportRegistrar
     * <功能详细描述>
     * @param dataDictService
     * @param basicDataAPIClientRegistry
     * @return [参数说明]
     * 
     * @return BasicDataServiceImportRegistrar [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "basicDataServiceImportRegistrar")
    public BasicDataServiceImportRegistrar basicDataServiceImportRegistrar(
            DataDictService dataDictService) {
        BasicDataAPIClientRegistry basicDataAPIClientRegistry = null;
        if (this.applicationContext
                .containsBean("basicDataAPIClientRegistry")) {
            basicDataAPIClientRegistry = this.applicationContext.getBean(
                    "basicDataAPIClientRegistry",
                    BasicDataAPIClientRegistry.class);
        }
        BasicDataServiceImportRegistrar registrar = new BasicDataServiceImportRegistrar(
                this.basePackages, this.module, dataDictService,
                basicDataAPIClientRegistry, basicDataServiceRegistry());
        return registrar;
    }
    
    /**
     * basicDataAPIController<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return BasicDataAPIController [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "basicDataAPIController")
    public BasicDataAPIController basicDataAPIController() {
        BasicDataAPIController controller = new BasicDataAPIController();
        return controller;
    }
    
    /**
     * 加载controller<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2019年5月4日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    @Configuration
    @ComponentScan(basePackages = "com.tx.component.basicdata.controller")
    public static class ControllerLoader {
        
        @Bean
        @ConditionalOnMissingBean
        public ControllerLoader controllerLoader() {
            ControllerLoader loader = new ControllerLoader();
            return loader;
        }
    }
}
