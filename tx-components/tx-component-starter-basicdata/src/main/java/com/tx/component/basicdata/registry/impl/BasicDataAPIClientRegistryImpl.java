/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.basicdata.registry.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.cloud.openfeign.FeignClientBuilder.Builder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tx.component.basicdata.client.BasicDataAPIClient;
import com.tx.component.basicdata.registry.BasicDataAPIClientRegistry;

/**
 * BasicDataType的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataAPIClientRegistryImpl implements
        BasicDataAPIClientRegistry, ApplicationContextAware, InitializingBean {
    
    /** 基础数据APIClientMap */
    private final Map<String, BasicDataAPIClient> basicDataAPIClientMap = new HashMap<>();
    
    /** spring容器 */
    private ApplicationContext applicationContext;
    
    /** builder */
    private FeignClientBuilder feignClientBuilder;
    
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
        this.feignClientBuilder = new FeignClientBuilder(applicationContext);
    }
    
    /**
     * @param module
     * @return
     */
    @Override
    public BasicDataAPIClient getBasicDataAPIClient(String module) {
        if (this.basicDataAPIClientMap.containsKey(module)) {
            return this.basicDataAPIClientMap.get(module);
        }
        Builder<BasicDataAPIClient> builder = this.feignClientBuilder
                .forType(BasicDataAPIClient.class, "basicDataAPIClient");
        BasicDataAPIClient newClient = (BasicDataAPIClient) builder
                .url("http://" + module + "/api/basicdata").build();
        
        this.basicDataAPIClientMap.put(module, newClient);
        return newClient;
    }
    
    //    private Decoder decoder;
    //    
    //    private Encoder encoder;
    //    
    //    private Client client;
    //    
    //    private Contract feignContract;
    //
    //    
    //    /** <默认构造函数> */
    //    public BasicDataAPIClientRegistryImpl(Decoder decoder, Encoder encoder,
    //            Client client, Contract feignContract) {
    //        super();
    //        this.decoder = decoder;
    //        this.encoder = encoder;
    //        this.client = client;
    //        this.feignContract = feignContract;
    //    }
    //    
    //    /**
    //     * @param module
    //     * @return
    //     */
    //    @Override
    //    public BasicDataAPIClient getBasicDataAPIClient(String module) {
    //        if (this.basicDataAPIClientMap.containsKey(module)) {
    //            return this.basicDataAPIClientMap.get(module);
    //        }
    //        BasicDataAPIClient newClient = Feign.builder()
    //                .client(client)
    //                .encoder(encoder)
    //                .decoder(decoder)
    //                .contract(this.feignContract)
    //                //.requestInterceptor(new BasicAuthRequestInterceptor("user", "user"))
    //                .target(BasicDataAPIClient.class,
    //                        "http://" + module + "/api/basicdata");
    //        this.basicDataAPIClientMap.put(module, newClient);
    //        return newClient;
    //    }
}