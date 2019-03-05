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
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.tx.component.configuration.exceptions.NotExistException;
import com.tx.component.configuration.model.ConfigProperty;
import com.tx.component.configuration.model.ConfigPropertyGroup;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.MessageUtils;

/**
 * 配置容器<br/>
 * 
 * @author wanxin
 * @version [版本号, 2013-8-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ConfigContext extends ConfigContextConfigurator {
    
    /** 配置容器的唯一实例 */
    protected static ConfigContext context;
    
    /** 配置属性映射 */
    private Map<String, ConfigProperty> configPropertyMapping = new HashMap<String, ConfigProperty>();
    
    /** 配置属性组列表 */
    private List<ConfigPropertyGroup> configPropertyGroupList = new ArrayList<ConfigPropertyGroup>();
    
    protected ConfigContext() {
        super();
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        
        // 让配置属性持久器加载配置数据
        if (this.configPropertiesPersisterList != null) {
            for (ConfigPropertyFinder configPropertiesPersister : this.configPropertiesPersisterList) {
                configPropertiesPersister.load(this);
            }
        }
    }
    
    /**
     * 获取配置容器的唯一实例
     * 
     * @return ConfigContext [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static ConfigContext getContext() {
        if (ConfigContext.context != null) {
            return ConfigContext.context;
        }
        synchronized (ConfigContext.class) {
            ConfigContext.context = applicationContext.getBean(beanName,
                    ConfigContext.class);
        }
        AssertUtils.notNull(ConfigContext.context, "context is null.");
        return ConfigContext.context;
    }
    
    /**
     * 根据key获取对应的配置属性实例
     * 
     * @param key
     * 
     * @return ConfigProperty [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public ConfigProperty get(String code) {
        AssertUtils.notEmpty(code, "code is empty.");
        
        return null;
    }
    
    /**
     * 获取配置属性的实际值
     * 
     * @param key
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getValue(String code) {
        ConfigProperty configProperty = getConfigPropertyByKey(key);
        return configProperty.getValue();
    }
    
    /**
     * 获取所有配置属性key2value的映射
     * 
     * @return Map<String,String> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Map<String, String> getAllConfigPropertyKey2ValueMap() {
        Map<String, String> resMap = new HashMap<String, String>();
        for (Entry<String, ConfigProperty> entryTemp : this.configPropertyMapping
                .entrySet()) {
            resMap.put(entryTemp.getKey(), entryTemp.getValue().getValue());
        }
        return resMap;
    }
    
    /**
     * 获取所有的配置属性映射
     * 
     * @return Map<String,ConfigProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public Map<String, ConfigProperty> getAllConfigPropertyMap() {
        return (Map<String, ConfigProperty>) MapUtils
                .unmodifiableMap(this.configPropertyMapping);
    }
    
    /**
     * 获取所有配置属性列表
     * 
     * @return List<ConfigProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> getAllConfigProperty() {
        return new ArrayList<ConfigProperty>(
                this.configPropertyMapping.values());
    }
    
    /**
     * 
     * 获取配置属性的实际值
     * 
     * @param key 键
     * 
     * @return String 值
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getValue(String key) {
        return getContext().getConfigPropertyValueByKey(key);
    }
    
    /**
     * 
     * 获取配置属性的实际值
     * 
     * @param key 键
     * @param defaultString 默认值,getValue(String)获取失败时,返回此值
     * 
     * @return String 值
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getValue(String key, String defaultString) {
        try {
            return getValue(key);
        } catch (Exception e) {
            return defaultString;
        }
    }
    
    /**
     * 获取Integer类型的值
     * 
     * @param key 键
     * 
     * @return Integer 值,可能为null
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static Integer getValueInteger(String key) {
        String value = getValue(key);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * 获取int类型的值
     * 
     * @param key 键
     * @param defaultLong 默认值
     * 
     * @return int 值
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static int getValueInteger(String key, int defaultInt) {
        try {
            return getValueInteger(key).intValue();
        } catch (Exception e) {
            return defaultInt;
        }
    }
    
    /**
     * 获取Long类型的值
     * 
     * @param key 键
     * 
     * @return Long 值,可能为null
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static Long getLongValue(String key) {
        String value = getValue(key);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * 获取long类型的值
     * 
     * @param key 键
     * @param defaultLong 默认值
     * 
     * @return long 值
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static long getLongValue(String key, long defaultLong) {
        try {
            return getValueLong(key).longValue();
        } catch (Exception e) {
            return defaultLong;
        }
    }
    
    /**
     * 获取Boolean类型的值
     * 
     * <pre>
     *   getValueBoolean(null)    = null
     *   getValueBoolean("true")  = Boolean.TRUE
     *   getValueBoolean("false") = Boolean.FALSE
     *   getValueBoolean("on")    = Boolean.TRUE
     *   getValueBoolean("ON")    = Boolean.TRUE
     *   getValueBoolean("yEs")   = Boolean.TRUE
     *   getValueBoolean("off")   = Boolean.FALSE
     *   getValueBoolean("oFf")   = Boolean.FALSE
     *   getValueBoolean("1")     = Boolean.TRUE
     *   getValueBoolean("0")     = Boolean.FALSE
     *   getValueBoolean("blue")  = null
     * </pre>
     * 
     * @param key 键
     * 
     * @return Boolean 值,可能为null
     * @exception [异常类型] [异常说明]
     * @see org.apache.commons.lang3.BooleanUtils#toBooleanObject(String)
     */
    public static Boolean getBooleanValue(String key) {
        String value = getValue(key);
        // 优先考虑value最可能出现的情况:字符串"true"和"false"
        if ("true".equals(value)) {
            return Boolean.TRUE;
        } else if ("false".equals(value)) {
            return Boolean.FALSE;
        } else
        // BooleanUtils.toBooleanObject(String) 方法没有考虑到 "0" 和 "1" 的问题.
        if ("1".equals(value)) {
            return Boolean.TRUE;
        } else if ("0".equals(value)) {
            return Boolean.FALSE;
        } else {
            return BooleanUtils.toBooleanObject(value);
        }
    }
    
    /**
     * 获取boolean类型的值
     * 
     * <pre>
     *   getValueBoolean(null)    = defaultBoolean
     *   getValueBoolean("true")  = Boolean.TRUE
     *   getValueBoolean("false") = Boolean.FALSE
     *   getValueBoolean("on")    = Boolean.TRUE
     *   getValueBoolean("ON")    = Boolean.TRUE
     *   getValueBoolean("yEs")   = Boolean.TRUE
     *   getValueBoolean("off")   = Boolean.FALSE
     *   getValueBoolean("oFf")   = Boolean.FALSE
     *   getValueBoolean("1")     = Boolean.TRUE
     *   getValueBoolean("0")     = Boolean.FALSE
     *   getValueBoolean("blue")  = defaultBoolean
     * </pre>
     * 
     * @param key 键
     * @param defaultBoolean 默认值
     * 
     * @return boolean 值
     * @exception [异常类型] [异常说明]
     * @see org.apache.commons.lang3.BooleanUtils#toBooleanObject(String)
     */
    public static boolean getBooleanValue(String key, boolean defaultBoolean) {
        try {
            return getBooleanValue(key).booleanValue();
        } catch (Exception e) {
            return defaultBoolean;
        }
    }
}
