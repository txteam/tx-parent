/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-16
 * <修改描述:>
 */
package com.tx.component.config.context;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;

import com.tx.component.config.model.ConfigProperty;
import com.tx.component.config.setting.ConfigPropertySetting;

/**
 * <配置属性容器>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-16]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigPropertiesContext implements FactoryBean<ConfigPropertiesContext>,BeanNameAware{
    
    private static Map<String, ConfigPropertiesContext> contextMap = new HashMap<String, ConfigPropertiesContext>();
    
    /** 配置属性容器的bean名 */
    private String beanName;
    
    /** 配置容器的配置  */
    private final ConfigContextCfg contextCfg = new ConfigPropertiesContext.ConfigContextCfg();
    
    /** key与配置属性的映射关系*/
    private final Map<String, ConfigProperty> configPropertyMap = new HashMap<String, ConfigProperty>();
    
    /** <默认构造函数> */
    private ConfigPropertiesContext() {}
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    /**
     * @return
     * @throws Exception
     */
    public ConfigPropertiesContext getObject() throws Exception {
        if(contextMap.containsKey(this.beanName)){
            return contextMap.get(this.beanName);
        }
        ConfigPropertiesContext newContext = new ConfigPropertiesContext();
        contextMap.put(this.beanName, newContext);
        return newContext;
    }

    /**
     * @return
     */
    public Class<?> getObjectType() {
        return ConfigPropertiesContext.class;
    }

    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return false;
    }

    
    
    /**
      * <配置容器配置>
      * <功能详细描述>
      * 
      * @author  PengQingyang
      * @version  [版本号, 2012-10-18]
      * @see  [相关类/方法]
      * @since  [产品/模块版本]
     */
    private static class ConfigContextCfg {
        
        /** 是否处于开发模式  开发模式中 getValue 将优先获取 developValue */
        private boolean isDevelop = false;
        
        /** 配置是否可重复 */
        private boolean repeatAble = false;

        /**
         * @return 返回 isDevelop
         */
        public boolean isDevelop() {
            return isDevelop;
        }

        /**
         * @param 对isDevelop进行赋值
         */
        public void setDevelop(boolean isDevelop) {
            this.isDevelop = isDevelop;
        }

        /**
         * @return 返回 repeatAble
         */
        public boolean isRepeatAble() {
            return repeatAble;
        }

        /**
         * @param 对repeatAble进行赋值
         */
        public void setRepeatAble(boolean repeatAble) {
            this.repeatAble = repeatAble;
        }
    }
    
    /**
      * <默认的配置属性实现>
      * <功能详细描述>
      * 
      * @author  PengQingyang
      * @version  [版本号, 2012-10-16]
      * @see  [相关类/方法]
      * @since  [产品/模块版本]
     */
    private class DefaultConfigProperty extends ConfigPropertySetting implements
            ConfigProperty {
        
        /** 配置属值 */
        private String value = "";
        /**
         * @return
         */
        public String getValue() {
            if(contextCfg.isDevelop || !StringUtils.isEmpty(getDevelopValue())){
                return getDevelopValue();
            }
            if(!StringUtils.isEmpty(this.value)){
                return this.value;
            }
            if(getDefaultValue() == null){
                setDefaultValue("");
                this.value = "";
            }
            return this.value;
        }
    }
}
