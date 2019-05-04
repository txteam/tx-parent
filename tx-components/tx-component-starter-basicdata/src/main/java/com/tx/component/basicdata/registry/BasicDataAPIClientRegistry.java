package com.tx.component.basicdata.registry;

import com.tx.component.basicdata.client.BasicDataAPIClient;

/**
 * 基础数据APIClient注册表<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface BasicDataAPIClientRegistry {
    
    /**
     * 获取基础数据API客户端<br/>
     * <功能详细描述>
     * @param module
     * @return [参数说明]
     * 
     * @return BasicDataAPIClient [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    BasicDataAPIClient getBasicDataAPIClient(String module);
    
}