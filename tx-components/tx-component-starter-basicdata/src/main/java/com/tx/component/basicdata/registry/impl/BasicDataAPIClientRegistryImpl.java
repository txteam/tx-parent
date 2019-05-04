/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.basicdata.registry.impl;

import java.util.HashMap;
import java.util.Map;

import com.tx.component.basicdata.client.BasicDataAPIClient;
import com.tx.component.basicdata.registry.BasicDataAPIClientRegistry;

import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;

/**
 * BasicDataType的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataAPIClientRegistryImpl implements BasicDataAPIClientRegistry {
    
    private Decoder decoder;
    
    private Encoder encoder;
    
    private Client client;
    
    private Contract feignContract;
    
    private final Map<String, BasicDataAPIClient> basicDataAPIClientMap = new HashMap<>();
    
    /** <默认构造函数> */
    public BasicDataAPIClientRegistryImpl(Decoder decoder, Encoder encoder,
            Client client, Contract feignContract) {
        super();
        this.decoder = decoder;
        this.encoder = encoder;
        this.client = client;
        this.feignContract = feignContract;
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
        BasicDataAPIClient newClient = Feign.builder()
                .client(client)
                .encoder(encoder)
                .decoder(decoder)
                .contract(this.feignContract)
                //.requestInterceptor(new BasicAuthRequestInterceptor("user", "user"))
                .target(BasicDataAPIClient.class,
                        "http://" + module + "/api/basicdata");
        return newClient;
    }
}