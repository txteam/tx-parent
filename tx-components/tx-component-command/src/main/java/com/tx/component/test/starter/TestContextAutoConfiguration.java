/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月1日
 * <修改描述:>
 */
package com.tx.component.test.starter;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.command.context.CommandContext;
import com.tx.component.command.starter.CommandContextProperties;
import com.tx.component.test.bean.TestBean;
import com.tx.component.test.bean.TestBeanImport;
import com.tx.component.test.bean.TestBeanRegiste;
import com.tx.component.test.service.TestBeanService;

/**
 * 命令容器配置器<br/>
 * <功能详细描述>
 * org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.tx.component.test.starter.TestContextAutoConfiguration
 * @author  Administrator
 * @version  [版本号, 2018年5月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
//@Configuration
@ConditionalOnClass(CommandContext.class)
@Import({ TestBeanImport.class, TestContextImportConfiguration.class })
public class TestContextAutoConfiguration
        implements InitializingBean, ApplicationContextAware {
    
    @SuppressWarnings("unused")
    private CommandContextProperties properties;
    
    @SuppressWarnings("unused")
    private DataSource datasource;
    
    @SuppressWarnings("unused")
    private PlatformTransactionManager txManager;
    
    private ApplicationContext applicationContext;
    
    /** <默认构造函数> */
    public TestContextAutoConfiguration(DataSource datasource,
            PlatformTransactionManager txManager) {
        super();
        System.out.println("TestContextAutoConfiguration constrution. called");
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(
                "TestContextAutoConfiguration afterPropertiesSet. called");
    }
    
    @PostConstruct
    public void postConstruct() {
        System.out
                .println("TestContextAutoConfiguration @PostConstruct. called");
        
        Map<String, DataSource> datasourceMap = this.applicationContext
                .getBeansOfType(DataSource.class);
        for (String datasourceName : datasourceMap.keySet()) {
            System.out.println("datasouce name:" + datasourceName);
        }
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
    
    @Bean
    public TestBean testBean1() {
        TestBean bean = new TestBean("1");
        return bean;
    }
    
    @Configuration
    public static class TestContextAutoInnerConfiguration1 {
        
        /** <默认构造函数> */
        public TestContextAutoInnerConfiguration1() {
            super();
            System.out.println(
                    "TestContextAutoInnerConfiguration1 constrution. called");
        }
        
        @Bean
        public TestBean testBean3() {
            TestBean bean = new TestBean("3");
            return bean;
        }
        
        @PostConstruct
        public void afterPropertiesSet() {
            System.out.println(
                    "TestContextAutoInnerConfiguration1 afterPropertiesSet. called");
        }
    }
    
    //public TestContextAutoInnerConfiguration1 testContextAutoInnerConfiguration1(){}
    public static class TestContextAutoInnerImportRegistrar
            implements BeanFactoryAware, ImportBeanDefinitionRegistrar,
            ResourceLoaderAware,InitializingBean {
        
        @SuppressWarnings("unused")
        private BeanFactory beanFactory;
        
        @SuppressWarnings("unused")
        private ResourceLoader resourceLoader;
        
        /** <默认构造函数> */
        //第一个被调用
        public TestContextAutoInnerImportRegistrar() {
            super();
            System.out.println(
                    "TestContextAutoInnerImportRegistrar constrution. called");
        }
        
        @PostConstruct
        public void afterPropertiesSet() {
            System.out.println(
                    "TestContextAutoInnerImportRegistrar afterPropertiesSet. called");
        }
        
        @PostConstruct
        public void postConstruct() {
            System.out.println(
                    "TestContextAutoInnerImportRegistrar postConstruct. called");
        }
        
        //低而个被调用
        @Override
        public void registerBeanDefinitions(
                AnnotationMetadata importingClassMetadata,
                BeanDefinitionRegistry registry) {
            BeanDefinition bd = BeanDefinitionBuilder
                    .genericBeanDefinition(TestBeanRegiste.class)
                    .getBeanDefinition();
            
            System.out.println(
                    "TestContextAutoInnerImportRegistrar registerBeanDefinitions. called");
            registry.registerBeanDefinition("testRegiste", bd);
        }
        
        @Override
        public void setBeanFactory(BeanFactory beanFactory)
                throws BeansException {
            this.beanFactory = beanFactory;
        }
        
        @Override
        public void setResourceLoader(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader;
        }
    }
    
    @Configuration
    @Import({ TestContextAutoInnerImportRegistrar.class })
    public static class TestContextAutoInnerConfiguration2 {
        
        /** <默认构造函数> */
        //第一个被调用
        public TestContextAutoInnerConfiguration2() {
            super();
            System.out.println(
                    "TestContextAutoInnerConfiguration2 constrution. called");
        }
        
        @PostConstruct
        public void afterPropertiesSet() {
            System.out.println(
                    "TestContextAutoInnerConfiguration2 afterPropertiesSet. called");
        }
    }
    
    @Bean
    public TestBean testBean2() {
        TestBean bean = new TestBean("2");
        return bean;
    }
    
    @Bean
    public TestAutoProxyCreator testAutoProxyCreator() {
        TestAutoProxyCreator proxyCreator = new TestAutoProxyCreator();
        return proxyCreator;
    }
    
    @Bean
    public TestBeanService testBeanService1() {
        TestBeanService bean = new TestBeanService("testBeanService1");
        return bean;
    }
    
    @Bean
    public TestBeanService testBeanService2() {
        TestBeanService bean = new TestBeanService("testBeanService2");
        return bean;
    }
    
}
//TestContextAutoInnerImportRegistrar constrution. called
//TestContextAutoInnerImportRegistrar registerBeanDefinitions. called
//TestContextAutoConfiguration constrution. called
//TestContextAutoConfiguration @PostConstruct. called
//datasouce name:dataSource
//TestContextAutoConfiguration afterPropertiesSet. called
//TestContextAutoInnerConfiguration1 constrution. called
//TestContextAutoInnerConfiguration1 afterPropertiesSet. called
//test bean cons.3
//test bean afterPropertiesSet.3
//TestContextAutoInnerConfiguration2 constrution. called
//TestContextAutoInnerConfiguration2 afterPropertiesSet. called
//test bean registe cons.
//test bean registe afterPropertiesSet.
//test bean import cons.
//test bean import afterPropertiesSet.
//TestContextImportInnerConfiguration constrution. called
//test bean cons.testBeanImport3
//test bean afterPropertiesSet.testBeanImport3
//TestContextImportConfiguration constrution. called
//TestContextImportConfiguration @PostConstruct. called
//TestContextImportConfiguration afterPropertiesSet. called
//test bean cons.testBeanImport1
//test bean afterPropertiesSet.testBeanImport1
//test bean cons.testBeanImport2
//test bean afterPropertiesSet.testBeanImport2
//test bean cons.1
//test bean afterPropertiesSet.1
//test bean cons.2
//test bean afterPropertiesSet.2
//test service cons.testBeanService1
//beanClass: com.tx.component.test.service.TestBeanService$$EnhancerBySpringCGLIB$$d874d61e
//customTargetSource: null
//test service cons.testBeanService2
//beanClass: com.tx.component.test.service.TestBeanService$$EnhancerBySpringCGLIB$$d874d61e
//customTargetSource: null
