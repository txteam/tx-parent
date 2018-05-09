package com.tx.component.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <br/>
 *
 * @author XRX
 * @version [版本号, 2018/05/08]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@SpringBootApplication(scanBasePackages = "com.tx.component.statistical")
public class ReportBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReportBootApplication.class);
    }
}
