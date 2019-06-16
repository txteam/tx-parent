package com.tx.component.configuration.registry;

import com.tx.component.configuration.client.ConfigContextAPIClient;

/**
 * 基础数据APIClient注册表<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ConfigAPIClientRegistry {
    
    /**
     * 获取配置项API客户端
     * <功能详细描述>
     * @param module
     * @return [参数说明]
     * 
     * @return ConfigAPIClient [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    ConfigContextAPIClient getConfigAPIClient(String module);
    
}