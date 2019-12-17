/**
  * 文 件 名:  ConfigContext.java
 * 版    权:  TX Workgroup . Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  PengQingyang
 * 修改时间:  2012-10-5
 * <修改描述:>
 */
package com.tx.component.configuration.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tx.component.configuration.service.ConfigPropertyManagerComposite;

/**
 * 配置容器基础配置吃撑类<br/>
 * 用以加载系统配置，支持动态加载系统中各配置<br/>
 * 
 * @author PengQingyang
 * @version [版本号, 2012-10-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class ConfigContextConfigurator
        implements InitializingBean, ApplicationContextAware, BeanNameAware {
    
    /** 日志记录器 */
    protected static Logger logger = LoggerFactory
            .getLogger(ConfigContextConfigurator.class);
    
    /** spring容器句柄 */
    protected static ApplicationContext applicationContext;
    
    /** beanName实例 */
    protected static String beanName;
    
    /** 配置容器所属模块 */
    protected String module;
    
    /** 配置属性持久器集合 */
    protected ConfigPropertyManagerComposite composite;
    
    /** 代理工厂 */
    protected ConfigEntityFactory configEntityFactory;
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ConfigContextConfigurator.applicationContext = applicationContext;
    }
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        ConfigContextConfigurator.beanName = name;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public final void afterPropertiesSet() throws Exception {
        logger.info("配置容器开始构建. ");
        
        logger.info("......配置容器开始构建... ");
        doBuild();
        
        logger.info("......配置容器开始初始化... ");
        doInitContext();
        
        logger.info("配置容器构建完成. ");
    }
    
    /**
     * 开始构建配置容器<br/>
     * <功能详细描述>
     * @throws Exception [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected abstract void doBuild() throws Exception;
    
    /**
     * 初始化容器<br/>
     * <功能详细描述>
     * @throws Exception [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
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
     * @return 返回 composite
     */
    public ConfigPropertyManagerComposite getComposite() {
        return composite;
    }
    
    /**
     * @param 对composite进行赋值
     */
    public void setComposite(ConfigPropertyManagerComposite composite) {
        this.composite = composite;
    }
    
    /**
     * @return 返回 configEntityFactory
     */
    public ConfigEntityFactory getConfigEntityFactory() {
        return configEntityFactory;
    }
    
    /**
     * @param 对configEntityFactory进行赋值
     */
    public void setConfigEntityFactory(
            ConfigEntityFactory configEntityFactory) {
        this.configEntityFactory = configEntityFactory;
    }
    
}
