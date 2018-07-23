package com.tx.component.file.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * <br/>
 *
 * @author XRX
 * @version [版本号, 2018/05/08]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

@SpringBootApplication(scanBasePackages = {"com.tx.component.file" })
@ImportResource(locations = { "classpath:spring/beans-*.xml" })
public class FileComponentBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileComponentBootApplication.class);
    }
    
}
