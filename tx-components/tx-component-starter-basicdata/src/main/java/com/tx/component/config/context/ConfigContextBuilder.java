/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-8-8
 * <修改描述:>
 */
package com.tx.component.config.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

import com.thoughtworks.xstream.XStream;
import com.tx.component.config.config.ConfigPropertyParser;
import com.tx.component.config.exception.ConfigNotExistException;
import com.tx.component.config.model.ConfigProperty;
import com.tx.component.config.model.ConfigRepositorySource;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.MessageUtils;
import com.tx.core.util.XstreamUtils;

/**
 * 配置容器<br/>
 * 
 * @author wanxin
 * @version [版本号, 2013-8-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class ConfigContextBuilder implements InitializingBean,ResourceLoaderAware {
    
    /** 配置容器 */
    private static Logger logger = LoggerFactory.getLogger(ConfigContext.class);
    
    /** 配置解析器实例 */
    private static XStream configXStream = XstreamUtils
            .getXstream(ConfigPropertyParser.class);
    
    /** 资源加载器 */
    private ResourceLoader resourceLoader;
    
    /** 当前系统所属模块 */
    private String module;
    
    /** 配置容器属性 */
    private String configLocation;
    
    /** 配置容器服务器url */
    private String serverUrl;
    
    /** 本地：配置属性映射 */
    private Map<String, ConfigProperty> localPropertyMapping = new HashMap<String, ConfigProperty>();
    
    /** 仓库源集合 */
    private List<ConfigRepositorySource> repositorySources = new ArrayList<>();
    
    /** 配置容器构造方法 */
    protected ConfigContextBuilder() {
        super();
    }
    
    /**
     * @param resourceLoader
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 初始化方法<br/>
     * @throws Exception
     */
    @Override
    public final void afterPropertiesSet() throws Exception {
        doBuild();
        
        initializeContext();
    }
    
    /**
     * 构建配置容器实例<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected void doBuild() {
        
    }
    
    /**
     * 解析配置容器<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected void doParseConfig() {
        //if(StringUtils.isEmpty(cs))
    }
    
    /**
     * 初始化容器<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected abstract void initializeContext();
    
    /**
     * 根据key获取对应的配置属性实例
     * 
     * @param key
     * 
     * @return ConfigProperty [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected ConfigProperty getByCode(String code) {
        ConfigProperty configProperty = this.configPropertyMapping.get(key);
        AssertUtils.notNull(configProperty,
                "configProperty is null. key :{}",
                new Object[] { key });
        return configProperty;
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
     * 更新属性值
     * 
     * @param key
     * @param value [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void update(String key, String value) {
        if (!this.configPropertyMapping.containsKey(key)) {
            throw new ConfigNotExistException(MessageUtils.format(
                    "configProperty key:{} not exsit.", new Object[] { key }));
        }
        this.configPropertyMapping.get(key).update(value);
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
}
