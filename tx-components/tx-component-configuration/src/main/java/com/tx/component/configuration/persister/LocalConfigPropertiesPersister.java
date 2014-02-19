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
import com.tx.component.configuration.exception.NotExistException;
import com.tx.component.configuration.exception.UnModifyAbleException;
import com.tx.component.configuration.model.ConfigProperty;
import com.tx.component.configuration.model.ConfigPropertyProxy;

/**
 * 基础配置属性解析器
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2014-2-7]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class LocalConfigPropertiesPersister extends
        BaseConfigPropertiesPersister {
    
    /**
     * 构建配置属性实例<br/>
     *<功能详细描述>
     * @param configPropertyParse
     * @param configGroupParse
     * @return [参数说明]
     * 
     * @return ConfigProperty [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected ConfigProperty buildConfigProperty(ConfigContext configContext,
            ConfigPropertyParse configPropertyParse,
            ConfigGroupParse configGroupParse) {
        //构建配置属性<br/>
        ConfigProperty configProperty = new ConfigPropertyProxy(configContext,
                this, configGroupParse, configPropertyParse);
        //压栈入缓存中
        this.cache.put(new Element(configPropertyParse.getKey(),
                configPropertyParse.getValue()));
        return configProperty;
    }
    
    /**
     * @param key
     * @return
     */
    @Override
    public String getConfigPropertyValueByKey(String key) {
        if (this.cache.get(key) == null) {
            throw new NotExistException("配置属性不存在。key:{}", new Object[] { key });
        }
        String value = (String) this.cache.get(key).getValue();
        return value;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSupportModifyAble() {
        return false;
    }
    
    /**
     * @param configProperty
     */
    @Override
    public void updateConfigProperty(ConfigProperty configProperty) {
        throw new UnModifyAbleException("本地配置属性不支持更新");
    }
    
    /**
     * 
     */
    @Override
    protected void afterPropertyConfigsLoaded() {
        @SuppressWarnings("unchecked")
        List<String> keys = (List<String>)this.cache.getKeys();
        for(String key : keys){
            logger.info("   加载本地系统参数:  {}={}",new Object[]{key,this.cache.get(key).getValue()});
        }
    }

    /**
     * 
     */
    @Override
    protected void postPropertyConfigsLoaded() {
    }
}
