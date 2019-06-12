/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年4月30日
 * <修改描述:>
 */
package com.tx.component.configuration.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.tx.component.configuration.registry.ConfigAPIClientRegistry;
import com.tx.component.configuration.registry.impl.ConfigAPIClientRegistryImpl;

import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;

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
public class ConfigAPIClientConfiguration {
    
    /** <默认构造函数> */
    public ConfigAPIClientConfiguration() {
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
        
        private Decoder decoder;
        
        private Encoder encoder;
        
        private Client client;
        
        private Contract feignContract;
        
        /** <默认构造函数> */
        public feignAPIClientConfiguration(Decoder decoder, Encoder encoder,
                Client client, Contract feignContract) {
            super();
            this.decoder = decoder;
            this.encoder = encoder;
            this.client = client;
            this.feignContract = feignContract;
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
        @Bean("configAPIClientRegistry")
        public ConfigAPIClientRegistry configAPIClientRegistry() {
            ConfigAPIClientRegistry registry = new ConfigAPIClientRegistryImpl(
                    decoder, encoder, client, feignContract);
            return registry;
        }
    }
    
}
