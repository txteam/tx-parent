/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-17
 * <修改描述:>
 */
package com.tx.component.configuration.context;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 基础配置容器实现的PropertyPlaceholder
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-12-17]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigContextPlaceholderConfigurer extends
        PropertyPlaceholderConfigurer {
    
    private ConfigContext configContext;
    
    /**
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory) throws BeansException {
        configContext = beanFactory.getBean(ConfigContext.class);
        super.postProcessBeanFactory(beanFactory);
    }
    
    /**
     * 从配置容器中加载所有的配置属性值映射<br/>
     * @param props
     * @throws IOException
     */
    @Override
    protected void loadProperties(Properties props) throws IOException {
        Map<String, String> allConfigPropertyMap = configContext.getAllConfigPropertyKey2ValueMap();
        if (MapUtils.isEmpty(allConfigPropertyMap)) {
            return;
        }
        
        for (Entry<String, String> entryTemp : allConfigPropertyMap.entrySet()) {
            props.setProperty(entryTemp.getKey(), entryTemp.getValue());
        }
    }

    /**
     * @param 对configContext进行赋值
     */
    public void setConfigContext(ConfigContext configContext) {
        this.configContext = configContext;
    }
}
