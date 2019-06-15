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
        Builder<BasicDataAPIClient> builder = this.feignClientBuilder.forType(
                BasicDataAPIClient.class, module + "/basicdataAPIClient");
        BasicDataAPIClient newClient = (BasicDataAPIClient) builder
                .path("/api/basicdataAPIClient").build();
        
        this.basicDataAPIClientMap.put(module, newClient);
        return newClient;
    }
}