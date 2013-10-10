/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-10
 * <修改描述:>
 */
package com.tx.component.basicdata.plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.OrderComparator;

import com.tx.core.util.ClassScanUtils;

/**
 * 基础数据执行器插件注册机<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-10-10]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataExecutorPluginRegistry {
    
    private Logger logger = LoggerFactory.getLogger(BasicDataExecutorPluginRegistry.class);
    
    private final List<BasicDataExecutorPlugin> plugins = new ArrayList<BasicDataExecutorPlugin>();
    
    /**
      * 获取类型对应的插件集
      *<功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return List<BasicDataExecutorPlugin> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public <TYPE> List<BasicDataExecutorPlugin> getSupportPlugins(
            Class<TYPE> type) {
        List<BasicDataExecutorPlugin> resList = new ArrayList<BasicDataExecutorPlugin>();
        for (BasicDataExecutorPlugin pluginTemp : plugins) {
            if (pluginTemp.isSupportType(type)) {
                resList.add(pluginTemp);
            }
        }
        return resList;
    }
    
    /**
      * 对插件进行排序
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void sort() {
        Collections.sort(plugins, OrderComparator.INSTANCE);
    }
    
    /**
      * 注册基础数据执行器插件<br/>
      *<功能详细描述>
      * @param plugin [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public <T> void register(BasicDataExecutorPlugin plugin) {
        plugins.add(plugin);
        sort();
    }
    
    /**
     * 注册基础数据执行器插件<br/>
     *<功能详细描述>
     * @param plugin [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public <T> void register(String basePackages) {
        Set<Class<? extends BasicDataExecutorPlugin>> basicDataExecutorPluginClassSet = ClassScanUtils.scanByParentClass(BasicDataExecutorPlugin.class,
                StringUtils.splitByWholeSeparator(basePackages, ","));
        
        if (basicDataExecutorPluginClassSet == null) {
            return;
        }
        
        for (Class<? extends BasicDataExecutorPlugin> pluginClassTemp : basicDataExecutorPluginClassSet) {
            if (Modifier.isInterface(pluginClassTemp.getModifiers())
                    || Modifier.isAbstract(pluginClassTemp.getModifiers())
                    || !Modifier.isPublic(pluginClassTemp.getModifiers())) {
                continue;
            }
            
            try {
                Constructor<? extends BasicDataExecutorPlugin> cons = pluginClassTemp.getConstructor();
                BasicDataExecutorPlugin basicDataExecutorPlugin = cons.newInstance();
                plugins.add(basicDataExecutorPlugin);
            } catch (Exception e) {
                logger.warn("根据包:{} 扫描注册基础数据执行器插件异常：{}", new Object[] {
                        basePackages, e.getMessage() });
                continue;
            }
        }
        
        sort();
    }
}
