/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月1日
 * <修改描述:>
 */
package com.tx.core.starter.mybatis;

import org.apache.ibatis.session.Configuration;

/**
 * Mybatis自定义扩展配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@FunctionalInterface
public interface ConfigurationCustomizer {
    
    /**
     * 自定义配置<br/>
     * <功能详细描述>
     * @param configuration [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    void customize(Configuration configuration);
}
