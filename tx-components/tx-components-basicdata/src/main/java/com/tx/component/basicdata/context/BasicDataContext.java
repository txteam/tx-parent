/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-14
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import com.tx.component.basicdata.annotation.BasicData;
import com.tx.component.basicdata.executor.BasicDataExecutor;
import com.tx.component.basicdata.plugin.BasicDataExecutorPlugin;
import com.tx.component.basicdata.plugin.BasicDataExecutorPluginRegistry;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.reflection.exception.ReflectionException;
import com.tx.core.util.ClassScanUtils;

/**
 * 基础数据容器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataContext extends BasicDataContextConfigurator implements
        InitializingBean {
    
    private static BasicDataContext context = null;
    
    /** 基础数据执行器工厂  */
    private static BasicDataExecutorFactory basicDataExecutorFactory;
    
    /** 基础数据执行器本次缓存 */
    private static Map<Class<?>, BasicDataExecutor<?>> basicDataExecutorMapping = new HashMap<Class<?>, BasicDataExecutor<?>>();
    
    protected BasicDataContext() {
        super();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        
        BasicDataContext.context = this;
        
        if (basicDataExecutorFactory == null) {
            basicDataExecutorFactory = new DefaultBasicDataExecutorFactory(this);
        }
        
        //启动时加载生成基础数据执行器
        loadExecutorOnStartup();
    }
    
    /**
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <TYPE> BasicDataExecutor<TYPE> getExecutor(Class<TYPE> type) {
        synchronized (type) {
            if (basicDataExecutorMapping.containsKey(type)) {
                return (BasicDataExecutor<TYPE>) basicDataExecutorMapping.get(type);
            } else {
                BasicDataExecutor<TYPE> resExecutor = buildBasicDataExecutor(type);
                basicDataExecutorMapping.put(type, resExecutor);
                return resExecutor;
            }
        }
    }
    
    /**
      * 构建基础数据执行器,并为执行器添加插件
      *<功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return BasicDataExecutor<TYPE> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static <TYPE> BasicDataExecutor<TYPE> buildBasicDataExecutor(
            Class<TYPE> type) {
        BasicDataExecutor<TYPE> executorTemp = basicDataExecutorFactory.getExecutor(type);
        
        //插件化基础数据
        BasicDataExecutor<TYPE> resExecutor = plugin(type, executorTemp);
        
        return resExecutor;
    }
    
    /**
      * 为基础数据执行器添加插件<br/>
      *<功能详细描述>
      * @param type
      * @param executor
      * @return [参数说明]
      * 
      * @return BasicDataExecutor<TYPE> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    private static <TYPE> BasicDataExecutor<TYPE> plugin(Class<TYPE> type,
            BasicDataExecutor<TYPE> executor) {
        BasicDataExecutorPluginRegistry registry = BasicDataContext.context.getBasicDataExecutorPluginRegistry();
        List<BasicDataExecutorPlugin> plugins = registry.getSupportPlugins(type);
        
        BasicDataExecutor<TYPE> resExecutor = executor;
        if (!CollectionUtils.isEmpty(plugins)) {
            for (BasicDataExecutorPlugin pluginTemp : plugins) {
                resExecutor = (BasicDataExecutor<TYPE>) Proxy.newProxyInstance(BasicDataContext.class.getClassLoader(),
                        new Class<?>[] { BasicDataExecutor.class },
                        pluginTemp.plugin(resExecutor,type));
            }
        }
        return resExecutor;
    }
    
    /**
      * 基础数据容器单例<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return BasicDataContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static BasicDataContext getContext() {
        AssertUtils.notNull(BasicDataContext.context,
                "BasicDataContext.context is null.");
        
        return BasicDataContext.context;
    }
    
    /**
     * 在启动期间加载执行器
     *<功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected void loadExecutorOnStartup() {
        Set<Class<?>> basicDataClassSet = ClassScanUtils.scanByAnnotation(BasicData.class,
                StringUtils.splitByWholeSeparator(this.getBasePackages(), ","));
        
        if (CollectionUtils.isEmpty(basicDataClassSet)) {
            return;
        }
        
        for (Class<?> basicDataClassTemp : basicDataClassSet) {
            //如果为接口，抽象类，或不含有BasicData注解则不进行解析
            if (basicDataClassTemp.isInterface()
                    || Modifier.isAbstract(basicDataClassTemp.getModifiers())
                    || !basicDataClassTemp.isAnnotationPresent(BasicData.class)) {
                continue;
            }
            
            //如果不含有无参构造函数也不进行解析
            try {
                BasicDataExecutor<?> basicDataExecutorTemp = buildBasicDataExecutor(basicDataClassTemp);
                
                basicDataExecutorMapping.put(basicDataClassTemp,
                        basicDataExecutorTemp);
            } catch (ReflectionException e) {
                if (this.isStopOnBuildBasicDataExecutorWhenStartup()) {
                    throw e;
                } else {
                    logger.warn("构造基础数据查询器异常:{}", e);
                    continue;
                }
            }
        }
    }
}
