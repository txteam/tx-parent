/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月3日
 * <修改描述:>
 */
package com.tx.core.starter.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tx.core.exceptions.ErrorCodeRegistry;

/**
 * mybatisSupport自动配置类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
public class CoreAutoConfiguration {
    
    /**
     * 错误码注册机<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return ErrorCodeRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "errorCodeRegistry")
    public ErrorCodeRegistry errorCodeRegistry() {
        return new ErrorCodeRegistry();
    }
}
