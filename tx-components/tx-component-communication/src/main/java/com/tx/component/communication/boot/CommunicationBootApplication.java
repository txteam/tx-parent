/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月4日
 * <修改描述:>
 */
package com.tx.component.communication.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2018年5月4日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */

@SpringBootApplication(scanBasePackages = {"com.tx.component.communication","com.tx.component.boot"})
//@EnableEurekaClient
//@EnableWebMvc
@MapperScan("com.tx.component.communication.dao")
@ImportResource(locations = {"classpath:spring/beans-*.xml"})
//@EnableSwagger2
public class CommunicationBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunicationBootApplication.class, args);
    }
}
