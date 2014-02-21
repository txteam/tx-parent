/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-8-8
 * <修改描述:>
 */
package com.tx.component.configuration.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;

import com.tx.component.configuration.exception.NotExistException;
import com.tx.component.configuration.model.ConfigProperty;
import com.tx.component.configuration.model.ConfigPropertyGroup;
import com.tx.component.configuration.persister.ConfigPropertiesPersister;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 配置容器<br/>
 * 
 * @author  wanxin
 * @version  [版本号, 2013-8-8]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigContext extends ConfigContextConfigurator {
    
    /** 配置容器的唯一实例 */
    private static ConfigContext context;
    
    /** 配置属性映射 */
    private Map<String, ConfigProperty> configPropertyMapping = new HashMap<String, ConfigProperty>();
    
    /** 配置属性组列表 */
    private List<ConfigPropertyGroup> configPropertyGroupList = new ArrayList<ConfigPropertyGroup>();
    
    /**
     * <默认构造函数>
     */
    protected ConfigContext() {
        super();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        context = this;
        
        /**
         * 让配置属性持久器加载配置数据<br/>
         */
        if (this.configPropertiesPersisterList != null) {
            for (ConfigPropertiesPersister configPropertiesPersister : this.configPropertiesPersisterList) {
                configPropertiesPersister.load(this);
            }
        }
    }
    
    /**
      *获取配置容器的唯一实例<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return ConfigContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static ConfigContext getContext() {
        AssertUtils.notNull(context,"context is null.use it when before init.");
        return context;
    }
    
    /**
      * 添加配置属性到配置容器<br/>
      *<功能详细描述>
      * @param configProperty [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void addConfigProperty(ConfigProperty configProperty) {
        AssertUtils.notNull(configProperty, "configProperty is null.");
        String configPropertyKey = configProperty.getKey();
        //如果设置为不可以重复，则校验是否已经存在相同key的配置属性
        if (!this.isRepeatAble()) {
            AssertUtils.notTrue(this.configPropertyMapping.containsKey(configPropertyKey),
                    "configProperty is repeat.key:{}",
                    new Object[] { configPropertyKey });
        }
        this.configPropertyMapping.put(configPropertyKey, configProperty);
    }
    
    /**
      * 配置属性组<br/>
      *<功能详细描述>
      * @param configPropertyGroup [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void addConfigPropertyGroup(ConfigPropertyGroup configPropertyGroup) {
        AssertUtils.notNull(configPropertyGroup, "configPropertyGroup is null.");
        if (!this.isRepeatAble()) {
            AssertUtils.notTrue(this.configPropertyGroupList.contains(configPropertyGroup),
                    "configPropertyGroup is repeat.configPropertyGroup:{}",
                    new Object[] { configPropertyGroup });
        }
        this.configPropertyGroupList.add(configPropertyGroup);
    }
    
    /** 
      * 根据key获取对应的配置属性实例<br/>
      *<功能详细描述>
      * @param key
      * @return [参数说明]
      * 
      * @return ConfigProperty [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public ConfigProperty getConfigPropertyByKey(String key) {
        ConfigProperty configProperty = this.configPropertyMapping.get(key);
        AssertUtils.notNull(configProperty,
                "configProperty is null. key :{}",
                new Object[] { key });
        return configProperty;
    }
    
    /**
      * 获取配置属性的实际值<br/>
      *<功能详细描述>
      * @param key
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getConfigPropertyValueByKey(String key) {
        ConfigProperty configProperty = getConfigPropertyByKey(key);
        return configProperty.getValue();
    }
    
    /**
      * 获取所有配置属性key2value的映射
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return Map<String,String> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Map<String, String> getAllConfigPropertyKey2ValueMap() {
        Map<String, String> resMap = new HashMap<String, String>();
        for (Entry<String, ConfigProperty> entryTemp : this.configPropertyMapping.entrySet()) {
            resMap.put(entryTemp.getKey(), entryTemp.getValue().getValue());
        }
        return resMap;
    }
    
    /**
      * 更新属性值<br/>
      *<功能详细描述>
      * @param key
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void update(String key, String value) {
        if (!this.configPropertyMapping.containsKey(key)) {
            throw new NotExistException("configProperty key:{} not exsit.",
                    new Object[] { key });
        }
        this.configPropertyMapping.get(key).update(value);
    }
    
    /**
      * 获取所有的配置属性分组<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return List<ConfigPropertyGroup> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<ConfigPropertyGroup> getAllConfigPropertyGroup(){
        return this.configPropertyGroupList;
    }
    
    /**
      * 获取所有的配置属性映射<br/> 
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return Map<String,ConfigProperty> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public Map<String, ConfigProperty> getAllConfigPropertyMap(){
        return (Map<String, ConfigProperty>)MapUtils.unmodifiableMap(this.configPropertyMapping);
    }
    
    /**
      * 获取所有配置属性列表<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return List<ConfigProperty> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> getAllConfigProperty(){
        return new ArrayList<ConfigProperty>(this.configPropertyMapping.values());
    }
}
