/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年4月30日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.tx.component.basicdata.registry.BasicDataAPIClientRegistry;
import com.tx.component.basicdata.registry.impl.BasicDataAPIClientRegistryImpl;

import feign.Client;
import feign.Feign;

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
public class BasicDataAPIClientConfiguration {
    
    /** <默认构造函数> */
    public BasicDataAPIClientConfiguration() {
        super();
    }
    
    /**
     * 基础数据APIClient配置容器<br/>
     *    基础数据属于高频但不常用功能，缓存考虑直接在调用处直接生成<br/>
     *    所以远端接口不会被高频调用,不用考虑接口的负载均衡，以及熔断<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2019年5月3日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    @Configuration
    @ConditionalOnClass({ Feign.class, FeignClientsConfiguration.class })
    @ConditionalOnBean({Client.class})
    @Import(FeignClientsConfiguration.class)
    public static class feignAPIClientConfiguration {
        
        /** <默认构造函数> */
        public feignAPIClientConfiguration() {
            super();
        }
        
        /**
         * 基础数据APIClient的注册表：basicDataAPIClientRegistry<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return BasicDataAPIClientRegistry [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @ConditionalOnMissingBean
        @Bean("basicDataAPIClientRegistry")
        public BasicDataAPIClientRegistry basicDataAPIClientRegistry() {
            BasicDataAPIClientRegistry registry = new BasicDataAPIClientRegistryImpl();
            return registry;
        }
    }
    
}
