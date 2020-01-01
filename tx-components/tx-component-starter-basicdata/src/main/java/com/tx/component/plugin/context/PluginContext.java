/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月4日
 * <修改描述:>
 */
package com.tx.component.plugin.context;

import com.tx.component.basicdata.context.BasicDataContext;
import com.tx.component.plugin.service.PluginInstanceService;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 插件容器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PluginContext extends PluginContextBuilder {
    
    /** 插件容器实现 */
    protected static PluginContext context;
    
    /**
     * 获取插件容器实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return BasicDataContext [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static PluginContext getContext() {
        if (PluginContext.context != null) {
            return PluginContext.context;
        }
        synchronized (BasicDataContext.class) {
            PluginContext.context = applicationContext.getBean(beanName,
                    PluginContext.class);
        }
        AssertUtils.notNull(PluginContext.context, "context is null.");
        return PluginContext.context;
    }
    
    /**
     * @throws Exception
     */
    @Override
    protected void doInitContext() throws Exception {
    }
    
    /**
     * 获取插件配置<br/>
     *    如果插件尚未执行安装，将默认根据配置类返回其实例对象，其中enable=false
     * <功能详细描述>
     * @param pluginType
     * @return [参数说明]
     * 
     * @return PLUGIN [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public <CONFIG extends PluginConfig> CONFIG getConfig(
            Class<? extends Plugin<CONFIG>> pluginType) {
        AssertUtils.notNull(pluginType, "pluginType is null.");
        
        Plugin<CONFIG> plugin = (Plugin<CONFIG>) this.pluginTypeMap
                .get(pluginType);
        CONFIG config = plugin.getConfig();
        return config;
    }
    
    /**
     * 获取插件<br/>
     *   如果有多个同一类型的插件，则抛出异常
     * <功能详细描述>
     * @param pluginType
     * @return [参数说明]
     * 
     * @return PLUGIN [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public <PLUGIN extends Plugin<?>> PLUGIN getPlugin(
            Class<PLUGIN> pluginType) {
        AssertUtils.notNull(pluginType, "pluginType is null.");
        PLUGIN plugin = (PLUGIN) this.pluginTypeMap.get(pluginType);
        return plugin;
    }
    
    /**
     * 获取插件<br/>
     * <功能详细描述>
     * @param id
     * @param pluginType
     * @return [参数说明]
     * 
     * @return PLUGIN [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public <PLUGIN extends Plugin<?>> PLUGIN getPlugin(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        PLUGIN plugin = (PLUGIN) this.pluginMap.get(id);
        return plugin;
    }
    
    /**
     * 获取插件<br/>
     * <功能详细描述>
     * @param id
     * @param pluginType
     * @return [参数说明]
     * 
     * @return PLUGIN [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public <PLUGIN extends Plugin<?>> PLUGIN getPlugin(String id,
            Class<PLUGIN> pluginType) {
        AssertUtils.notEmpty(id, "id is empty.");
        PLUGIN plugin = (PLUGIN) this.pluginMap.get(id);
        return plugin;
    }
    
    /**
     * 获取插件实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return PLUGIN [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PluginInstanceService getPluginInstanceService() {
        return this.pluginInstanceService;
    }
}
