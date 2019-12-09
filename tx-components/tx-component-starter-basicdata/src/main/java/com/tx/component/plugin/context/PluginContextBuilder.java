/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月4日
 * <修改描述:>
 */
package com.tx.component.plugin.context;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.BeanNameAware;

/**
 * 插件容器构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class PluginContextBuilder extends PluginContextConfigurator
        implements BeanNameAware {
    
    /** 插件映射关联 */
    @SuppressWarnings("rawtypes")
    protected Map<String, Plugin> pluginMap = new HashMap<>();
    
    /**
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @Override
    protected void doBuild() throws Exception {
        //加载基础数据类<br/>
        Collection<Plugin> plugins = applicationContext
                .getBeansOfType(Plugin.class).values();
        
        for (Plugin pluginTemp : plugins) {
            pluginMap.put(pluginTemp.getId(), pluginTemp);
        }
    }
}
