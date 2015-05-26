/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-2-7
 * <修改描述:>
 */
package com.tx.component.configuration.persister;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;

import com.thoughtworks.xstream.XStream;
import com.tx.component.configuration.config.ConfigContextConfig;
import com.tx.component.configuration.config.ConfigGroupParse;
import com.tx.component.configuration.config.ConfigPropertyParse;
import com.tx.component.configuration.context.ConfigContext;
import com.tx.component.configuration.model.ConfigProperty;
import com.tx.component.configuration.model.ConfigPropertyGroup;
import com.tx.component.configuration.model.ConfigPropertyGroupProxy;
import com.tx.component.configuration.model.ConfigPropertyTypeEnum;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;
import com.tx.core.util.XstreamUtils;

/**
 * 基础配置属性解析器
 * 
 * @author brady
 * @version [版本号, 2014-2-7]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class BaseConfigPropertiesPersister implements
        ConfigPropertiesPersister, ApplicationContextAware, InitializingBean,
        BeanNameAware {
    
    /** 系统日志记录器 */
    protected Logger logger = LoggerFactory.getLogger(BaseConfigPropertiesPersister.class);
    
    /** 配置文件解析句柄 */
    private static XStream configContextParser = XstreamUtils.getXstream(ConfigContextConfig.class);
    
    /** 配置文件路径 */
    private String location;
    
    /** 缓存管理类 */
    private CacheManager cacheManager;
    
    /** 缓存实例 */
    protected Cache cache;
    
    /** applicationContext实例 */
    private ApplicationContext applicationContext;
    
    /** 注册的bean名，会被当做缓存名进行存放 */
    private String name;
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        this.name = name;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.cache == null) {
            if (this.cacheManager == null) {
                this.cacheManager = CacheManager.create();
            }
            if (this.cacheManager.getCache(this.name) == null) {
                this.cacheManager.addCache(this.name);
            }
            this.cache = this.cacheManager.getCache(this.name);
        }
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    @Transactional
    @Override
    public void load(ConfigContext configContext) {
        //属性解析前置事件
        postPropertyConfigsLoaded();
        
        //加载配置的资源
        Resource[] resources = null;
        try {
            resources = applicationContext.getResources(location);
        } catch (IOException e) {
            throw ExceptionWrapperUtils.wrapperIOException(e,
                    "getResource:{} exception:{}",
                    new Object[] { location, e.toString() });
        }
        
        List<ConfigContextConfig> configContextConfigList = new ArrayList<ConfigContextConfig>();
        //解析配置资源
        if (resources == null || resources.length == 0) {
            return;
        }
        for (Resource resTemp : resources) {
            if (!resTemp.exists() || !resTemp.isReadable()) {
                logger.warn("resource:{} is not exist or not readable.",
                        new Object[] { resTemp });
                continue;
            }
            
            InputStream in = null;
            try {
                in = resTemp.getInputStream();
                ConfigContextConfig configContextTemp = (ConfigContextConfig) configContextParser.fromXML(in);
                configContextConfigList.add(configContextTemp);
            } catch (IOException e) {
                throw ExceptionWrapperUtils.wrapperIOException(e,
                        "getInputStream:{} exception:{}",
                        new Object[] { location, e.toString() });
            } finally {
                IOUtils.closeQuietly(in);
            }
        }
        
        //解析配置容器配置
        parseConfigContextConfig(configContext, configContextConfigList);
        
        //在属性配置文件加载成功之后执行逻辑
        afterPropertyConfigsLoaded();
    }
    
    /**
     * 解析配置容器配置<br/>
     * 
     * @param configContext
     * @param configContextConfigList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void parseConfigContextConfig(ConfigContext configContext,
            List<ConfigContextConfig> configContextConfigList) {
        if (CollectionUtils.isEmpty(configContextConfigList)) {
            return;
        }
        
        //迭代循环生成配置属性
        for (ConfigContextConfig configContextConfig : configContextConfigList) {
            //解析篇日志容器配置文件中，无配置组的属性集合
            List<ConfigPropertyParse> configPropertyParseListTemp = configContextConfig.getConfigs();
            if (!CollectionUtils.isEmpty(configPropertyParseListTemp)) {
                for (ConfigPropertyParse configPropertyParseTemp : configPropertyParseListTemp) {
                    //将配置属性添加到配置容器中
                    configContext.addConfigProperty(buildConfigProperty(configContext,
                            configPropertyParseTemp,
                            null));
                }
            }
            
            //解析配置容器文件中，配置组集合
            List<ConfigGroupParse> configGroupParseListTemp = configContextConfig.getConfigGroups();
            parseConfigGroup(configContext, null, configGroupParseListTemp);
        }
    }
    
    /**
     * 解析配置组<br/>
     * 
     * @param configContext
     * @param configGroupParseList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void parseConfigGroup(ConfigContext configContext,
            ConfigGroupParse parentConfigGroupParse,
            List<ConfigGroupParse> configGroupParseList) {
        if (CollectionUtils.isEmpty(configGroupParseList)) {
            return;
        }
        
        for (ConfigGroupParse configGroupParse : configGroupParseList) {
            //解析配置属性组
            ConfigPropertyGroup configPropertyGroup = buildConfigPropertyGroup(parentConfigGroupParse,
                    configGroupParse);
            configContext.addConfigPropertyGroup(configPropertyGroup);
            
            //解析配置组解析结果中，无配置组的属性集合
            List<ConfigPropertyParse> configPropertyParseListTemp = configGroupParse.getConfigs();
            if (!CollectionUtils.isEmpty(configPropertyParseListTemp)) {
                for (ConfigPropertyParse configPropertyParseTemp : configPropertyParseListTemp) {
                    //将配置属性添加到配置容器中
                    configContext.addConfigProperty(buildConfigProperty(configContext,
                            configPropertyParseTemp,
                            configGroupParse));
                }
            }
            
            //迭代解析配置组
            List<ConfigGroupParse> configGroupParseListTemp = configGroupParse.getConfigGroups();
            parseConfigGroup(configContext,
                    configGroupParse,
                    configGroupParseListTemp);
        }
    }
    
    /**
     * 
      * 构建配置属性组<br/>
      * 
      * @param parentConfigGroupParse
      * @param configGroupParse
      * 
      * @return ConfigPropertyGroup [返回类型说明]
      * @exception [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected ConfigPropertyGroup buildConfigPropertyGroup(
            ConfigGroupParse parentConfigGroupParse,
            ConfigGroupParse configGroupParse) {
        ConfigPropertyGroup configPropertyGroup = new ConfigPropertyGroupProxy(
                configPropertyType(), parentConfigGroupParse, configGroupParse);
        return configPropertyGroup;
    }
    
    /**
     * 
     * 构建配置属性实例<br/>
     * 
     * @param configContext
     * @param configPropertyParse
     * @param configGroupParse
     * 
     * @return ConfigProperty [返回类型说明]
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected abstract ConfigProperty buildConfigProperty(
            ConfigContext configContext,
            ConfigPropertyParse configPropertyParse,
            ConfigGroupParse configGroupParse);
    
    /**
     * 属性配置加载
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected abstract ConfigPropertyTypeEnum configPropertyType();
    
    /**
     * 属性配置加载
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected abstract void postPropertyConfigsLoaded();
    
    /**
     * 属性加载后执行的方法<br/>
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected abstract void afterPropertyConfigsLoaded();
    
    /**
     * @param 对location进行赋值
     */
    public void setLocation(String location) {
        this.location = location;
    }
    
    /**
     * @param 对cacheManager进行赋值
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
    
    /**
     * @param 对cache进行赋值
     */
    public void setCache(Cache cache) {
        this.cache = cache;
    }
}
