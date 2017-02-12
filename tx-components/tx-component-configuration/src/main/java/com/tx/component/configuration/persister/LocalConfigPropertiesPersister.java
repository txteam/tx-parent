/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-2-7
 * <修改描述:>
 */
package com.tx.component.configuration.persister;

import java.util.List;

import net.sf.ehcache.Element;

import com.tx.component.configuration.config.ConfigGroupParse;
import com.tx.component.configuration.config.ConfigPropertyParse;
import com.tx.component.configuration.context.ConfigContext;
import com.tx.component.configuration.exceptions.NotExistException;
import com.tx.component.configuration.exceptions.UnModifyAbleException;
import com.tx.component.configuration.model.ConfigProperty;
import com.tx.component.configuration.model.ConfigPropertyProxy;
import com.tx.component.configuration.model.ConfigPropertyTypeEnum;
import com.tx.core.util.MessageUtils;

/**
 * 基础配置属性解析器
 * 
 * @author brady
 * @version [版本号, 2014-2-7]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LocalConfigPropertiesPersister extends
        BaseConfigPropertiesPersister {
    
    @Override
    protected ConfigPropertyTypeEnum configPropertyType() {
        return ConfigPropertyTypeEnum.本地配置项;
    }
    
    @Override
    protected ConfigProperty buildConfigProperty(ConfigContext configContext,
            ConfigPropertyParse configPropertyParse,
            ConfigGroupParse configGroupParse) {
        //构建配置属性<br/>
        ConfigProperty configProperty = new ConfigPropertyProxy(configContext,
                this, configPropertyType(), configGroupParse,
                configPropertyParse);
        //压栈入缓存中
        this.cache.put(new Element(configPropertyParse.getKey(),
                configPropertyParse.getValue()));
        return configProperty;
    }
    
    @Override
    public String getConfigPropertyValueByKey(String key) {
        if (this.cache.get(key) == null) {
            throw new NotExistException(MessageUtils.format("配置属性不存在。key:{}",
                    new Object[] { key }));
        }
        String value = (String) this.cache.get(key).getValue();
        return value;
    }
    
    @Override
    public boolean isSupportModifyAble() {
        return false;
    }
    
    @Override
    public void updateConfigProperty(String key, String value) {
        throw new UnModifyAbleException("本地配置属性不支持更新");
    }
    
    @Override
    protected void afterPropertyConfigsLoaded() {
        @SuppressWarnings("unchecked")
        List<String> keys = (List<String>) this.cache.getKeys();
        for (String key : keys) {
            logger.info("   加载本地系统参数:  {}={}", new Object[] { key,
                    this.cache.get(key).getValue() });
        }
    }
    
    @Override
    protected void postPropertyConfigsLoaded() {
    }
}
