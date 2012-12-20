/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-16
 * <修改描述:>
 */
package com.tx.component.config.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.DefaultPropertiesPersister;

import com.thoughtworks.xstream.XStream;
import com.tx.component.config.setting.ConfigContextSetting;
import com.tx.component.config.setting.ConfigLocationSetting;
import com.tx.component.config.setting.ConfigPropertiesSettings;
import com.tx.component.config.setting.ConfigResourceSetting;
import com.tx.core.util.XstreamUtils;

/**
 * <配置加载器>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-16]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigContextPropertiesPersister extends
        DefaultPropertiesPersister {
    
    private static Logger logger = LoggerFactory.getLogger(ConfigContextPropertiesPersister.class);
    
    private static XStream configContextParse = XstreamUtils.getXstream(ConfigContextSetting.class);
    
    private static XStream configPropertiesParse = XstreamUtils.getXstream(ConfigPropertiesSettings.class);
    
    private ResourcePatternResolver resourcesLoader = new PathMatchingResourcePatternResolver();
    
    /**
     * @param props
     * @param is
     * @throws IOException
     */
    @Override
    public void loadFromXml(Properties props, InputStream is)
            throws IOException {
        logger.info("PropertiesPersister start load properties from one of resources.............");
        
        //检查参数合法性
        if (is == null || props == null) {
            logger.info("loadProperties from {} fail. the configContextCft is not exist.");
            return;
        }
        
        //解析
        ConfigContextSetting ccs = (ConfigContextSetting) configContextParse.fromXML(is);
        
        if (ccs == null) {
            logger.info("loadProperties from {} fail. the configContextCft is empty.");
            return;
        }
        
        loadConfigResource(ccs.getConfigResourceSettingSet());
        Set<Resource> configResources = loadConfigResources(ccs.getConfigLocationSetting(),
                props);
        loadConfig(configResources);
        
        logger.info("PropertiesPersister end load properties from one of resources.............");
    }
    
    /**
      *<解析配置源>
      *<功能详细描述>
      * @param configResource [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void loadConfigResource(Set<ConfigResourceSetting> configResource) {
        //如果没有需要被解析的
        if (configResource == null || configResource.size() == 0) {
            return;
        }
        
        //将存在的加载源存放于配置容器
    }
    
    /**
      *<加载系统实际配置资源>
      *<功能详细描述>
      * @param locationSetting
      * @param props
      * @return
      * @throws IOException [参数说明]
      * 
      * @return Set<Resource> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private Set<Resource> loadConfigResources(
            ConfigLocationSetting locationSetting, Properties props)
            throws IOException {
        //如果没有配置需要解析
        if (locationSetting == null
                || locationSetting.getConfigLocationSet() == null
                || locationSetting.getConfigLocationSet().size() == 0) {
            return null;
        }
        
        //
        Set<Resource> resourceSet = new HashSet<Resource>();
        for (String configLocationTemp : locationSetting.getConfigLocationSet()) {
            Resource[] resourcesTemp = this.resourcesLoader.getResources(configLocationTemp);
            
            if (resourcesTemp == null || resourcesTemp.length == 0) {
                continue;
            }
            
            for (Resource resTemp : resourcesTemp) {
                resourceSet.add(resTemp);
            }
        }
        
        return resourceSet;
    }
    
    /**
      *<加载系统实际配置>
      *<功能详细描述>
      * @param loadConfigResource [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void loadConfig(Set<Resource> configResources) {
        
    }
    
}
