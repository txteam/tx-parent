/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年2月20日
 * <修改描述:>
 */
package com.tx.component.config.context.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年2月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RemoteConfigPropertyFinderImpl {
    
    @Autowired
    private DiscoveryClient discoveryClient;
    
    @RequestMapping("/registered")
    public String getRegistered(){
        List<ServiceInstance> list = discoveryClient.getInstances("STORES");
        System.out.println(discoveryClient.getLocalServiceInstance());
        System.out.println("discoveryClient.getServices().size() = " + discoveryClient.getServices().size());
        
        for( String s :  discoveryClient.getServices()){
            System.out.println("services " + s);
            List<ServiceInstance> serviceInstances =  discoveryClient.getInstances(s);
            for(ServiceInstance si : serviceInstances){
                System.out.println("    services:" + s + ":getHost()=" + si.getHost());
                System.out.println("    services:" + s + ":getPort()=" + si.getPort());
                System.out.println("    services:" + s + ":getServiceId()=" + si.getServiceId());
                System.out.println("    services:" + s + ":getUri()=" + si.getUri());
                System.out.println("    services:" + s + ":getMetadata()=" + si.getMetadata());
            }
            
        }
        
        System.out.println(list.size());
        if (list != null && list.size() > 0 ) {
            System.out.println( list.get(0).getUri()  );
        }
        return null;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(SpringBootEurekaClientApplication.class, args);
    }

}
