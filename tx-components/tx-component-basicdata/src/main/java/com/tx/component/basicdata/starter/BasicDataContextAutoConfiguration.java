/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 基础数据容器自动配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年4月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
@EnableConfigurationProperties(BasicDataContextProperties.class)
public class BasicDataContextAutoConfiguration {
    
}
