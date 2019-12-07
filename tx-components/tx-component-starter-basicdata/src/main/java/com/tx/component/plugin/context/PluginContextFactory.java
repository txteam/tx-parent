/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月4日
 * <修改描述:>
 */
package com.tx.component.plugin.context;

import org.springframework.beans.factory.FactoryBean;

/**
 * 插件容器工厂类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PluginContextFactory extends PluginContext
        implements FactoryBean<PluginContext> {
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public PluginContext getObject() throws Exception {
        if (PluginContextFactory.context == null) {
            return this;
        } else {
            return PluginContextFactory.context;
        }
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return PluginContext.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
    
}