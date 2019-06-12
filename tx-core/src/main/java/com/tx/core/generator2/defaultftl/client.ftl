/*
 * 描       述:  <描述>
 * 修  改 人:  
 * 修改时间:
 * <修改描述:>
 */
package ${controller.basePackage}.client;

import org.springframework.cloud.openfeign.FeignClient;

import com.tx.local.boot.FeignConfiguration;
import ${controller.basePackage}.facade.${controller.entityTypeSimpleName}Facade;

/**
 * ${controller.entityComment}API客户端[${controller.entityTypeSimpleName}APIClient]<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@FeignClient(name = "WEBDEMO/clientSource", url = "http://localhost:8090/webdemo", path = "/api/clientSource", configuration = FeignConfiguration.class)
public class ${controller.entityTypeSimpleName}APIClient extends ${controller.entityTypeSimpleName}Facade {
    
}