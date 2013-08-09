/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-8-8
 * <修改描述:>
 */
package com.tx.component.config.context;

import org.springframework.beans.factory.FactoryBean;

/**
 * 配置容器工厂类
 * <功能详细描述>
 * 
 * @author  wanxin
 * @version  [版本号, 2013-8-8]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigContextFactory extends ConfigContextConfigurer implements
        FactoryBean<ConfigContext> {
    
    /**
     * @return
     * @throws Exception
     */
    public ConfigContext getObject() throws Exception {
        return ConfigContext.getContext();
    }
    
    /**
     * @return
     */
    public Class<?> getObjectType() {
        return ConfigContext.class;
    }
    
    /**
     * @return
     */
    public boolean isSingleton() {
        return true;
    }
}
