/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.basicdata.context.BasicDataContextFactory;
import com.tx.component.basicdata.registry.BasicDataEntityRegistry;

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
public class BasicDataContextAutoConfiguration implements InitializingBean {
    
    /** 属性文件 */
    private BasicDataContextProperties properties;
    
    /** 基础数据扫表包路径 */
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
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化包名
        if (!StringUtils.isBlank(this.properties.getBasePackages())) {
            this.basePackages = this.properties.getBasePackages();
        }
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
        BasicDataEntityRegistry registry = new BasicDataEntityRegistry(
                this.module);
        
        return registry;
    }
    
    //    /**
    //     * 基础数据业务层代理创建器<br/>
    //     * <功能详细描述>
    //     * @return [参数说明]
    //     * 
    //     * @return BasicDataServiceProxyCreator [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //    */
    //    @Bean(name = "basicdata.basicDataServiceSupportCacheProxyCreator")
    //    public BasicDataServiceSupportCacheProxyCreator basicDataServiceSupportCacheProxyCreator() {
    //        BasicDataServiceSupportCacheProxyCreator creator = new BasicDataServiceSupportCacheProxyCreator(
    //                this.cacheManager);
    //        
    //        return creator;
    //    }
    //    
    //    /**
    //     * 基础数据容器<br/>
    //     * <功能详细描述>
    //     * @return [参数说明]
    //     * 
    //     * @return BasicDataContextFactory [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    @Bean(name = "basicDataContext")
    //    public BasicDataContextFactory BasicDataContextFactory() {
    //        BasicDataContextFactory context = new BasicDataContextFactory();
    //        
    //        return context;
    //    }
    //    
    
    
    
}
