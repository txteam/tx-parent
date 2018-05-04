/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月4日
 * <修改描述:>
 */
package com.tx.component.communication.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2018年5月4日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@SpringBootApplication
@EnableEurekaClient
public class CommunicationBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunicationBootApplication.class, args);
    }
}
