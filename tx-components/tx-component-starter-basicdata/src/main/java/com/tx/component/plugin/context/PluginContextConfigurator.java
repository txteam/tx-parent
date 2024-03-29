/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月3日
 * <修改描述:>
 */
package com.tx.component.plugin.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tx.component.plugin.service.PluginInstanceService;

/**
 * 插件容器配置器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class PluginContextConfigurator
        implements ApplicationContextAware, InitializingBean, BeanNameAware {
    
    /** 日志记录器 */
    protected Logger logger = LoggerFactory.getLogger(PluginContext.class);
    
    /** spring容器句柄 */
    protected static ApplicationContext applicationContext;
    
    /** beanName实例 */
    protected static String beanName;
    
    /** 所属项目（模块） */
    protected String module;
    
    /** 插件实例业务层 */
    protected PluginInstanceService pluginInstanceService;
    
    /** <默认构造函数> */
    public PluginContextConfigurator() {
        super();
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public final void setApplicationContext(
            ApplicationContext applicationContext) throws BeansException {
        PluginContextConfigurator.applicationContext = applicationContext;
    }
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        PluginContextConfigurator.beanName = name;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public final void afterPropertiesSet() throws Exception {
        logger.info("插件容器开始构建: ......");
        
        //进行容器构建
        logger.info("......插件容器开始构建... ");
        doBuild();
        
        //初始化容器
        logger.info("......插件容器开始初始化... ");
        doInitContext();
        
        logger.info("插件容器构建完成: ......");
    }
    
    /**
     * 基础数据容器构建<br/>
     */
    protected abstract void doBuild() throws Exception;
    
    /**
     * 容器初始化<br/>
     */
    protected abstract void doInitContext() throws Exception;
    
    /**
     * @return 返回 module
     */
    public String getModule() {
        return module;
    }
    
    /**
     * @param 对module进行赋值
     */
    public void setModule(String module) {
        this.module = module;
    }
    
    /**
     * @return 返回 pluginInstanceService
     */
    public PluginInstanceService getPluginInstanceService() {
        return pluginInstanceService;
    }
    
    /**
     * @param 对pluginInstanceService进行赋值
     */
    public void setPluginInstanceService(
            PluginInstanceService pluginInstanceService) {
        this.pluginInstanceService = pluginInstanceService;
    }
    
}
