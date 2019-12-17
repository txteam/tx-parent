/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-8-8
 * <修改描述:>
 */
package com.tx.component.configuration.context;

import java.util.List;

import com.tx.component.configuration.model.ConfigProperty;
import com.tx.component.configuration.util.ConfigContextUtils;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.querier.model.Querier;

/**
 * 配置容器<br/>
 * 
 * @author wanxin
 * @version [版本号, 2013-8-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ConfigContext extends ConfigContextBuilder {
    
    /** 配置容器的唯一实例 */
    protected static ConfigContext context;
    
    /** 配置容器构造函数 */
    protected ConfigContext() {
        super();
    }
    
    /**
     * @throws Exception
     */
    @Override
    protected void doInitContext() throws Exception {
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
     * 根据code获取对应的配置属性实例<br/>
     * <功能详细描述>
     * @param prefix
     * @param configEntityType
     * @param property
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String code(String prefix, Class<?> configEntityType,
            String property) {
        AssertUtils.notNull(configEntityType, "configEntityType is null.");
        AssertUtils.notEmpty(property, "property is empty.");
        
        prefix = ConfigContextUtils.preprocessPrefix(prefix, configEntityType);
        String code = prefix + property;
        
        return code;
    }
    
    /**
     * 解析配置类返回配置属性集合<br/>
     * <功能详细描述>
     * @param prefix
     * @param configEntityType
     * @return [参数说明]
     * 
     * @return List<ConfigProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> setupConfigProperties(String prefix,
            Class<?> configEntityType) {
        AssertUtils.notNull(configEntityType, "configEntityType is null.");
        
        prefix = ConfigContextUtils.preprocessPrefix(prefix, configEntityType);
        List<ConfigProperty> resList = this.configEntityFactory.parse(prefix,
                configEntityType);
        return resList;
    }
    
    /**
     * 安装配置实例<br/>
     * <功能详细描述>
     * @param prefix
     * @param configEntityType
     * @return [参数说明]
     * 
     * @return INS [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public <INS> INS setupConfigEntity(String prefix,
            Class<INS> configEntityType) {
        AssertUtils.notNull(configEntityType, "configEntityType is null.");
        prefix = ConfigContextUtils.preprocessPrefix(prefix, configEntityType);
        
        INS configEntity = this.configEntityFactory.setup(prefix,
                configEntityType);
        return configEntity;
    }
    
    /**
     * 卸载配置实例<br/>
     * <功能详细描述>
     * @param configEntity
     * @return [参数说明]
     * 
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public <INS> void uninstall(String prefix, Class<INS> configEntityType) {
        AssertUtils.notNull(configEntityType, "configEntityType is null.");
        prefix = ConfigContextUtils.preprocessPrefix(prefix, configEntityType);
        
        this.configEntityFactory.uninstall(prefix, configEntityType);
    }
    
    /**
     * 根据code获取对应的配置属性实例
     * @param code
     * 
     * @return ConfigProperty [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean patch(String code, String value) {
        AssertUtils.notEmpty(code, "code is empty.");
        value = value == null ? "" : value;
        
        boolean flag = this.composite.patch(this.module, code, value);
        return flag;
    }
    
    /**
     * 根据code获取对应的配置属性实例
     * @param code
     * 
     * @return ConfigProperty [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean patch(String module, String code, String value) {
        AssertUtils.notEmpty(code, "code is empty.");
        
        boolean flag = this.composite.patch(module, code, value);
        return flag;
    }
    
    /**
     * 根据code获取对应的配置属性实例
     * @param code
     * 
     * @return ConfigProperty [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public ConfigProperty find(String code) {
        AssertUtils.notEmpty(code, "code is empty.");
        
        ConfigProperty p = doFind(this.module, code);
        return p;
    }
    
    /**
     * 根据code获取对应的配置属性实例
     * @param module
     * @param code
     * 
     * @return ConfigProperty [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public ConfigProperty find(String module, String code) {
        AssertUtils.notEmpty(code, "code is empty.");
        
        ConfigProperty p = doFind(module, code);
        return p;
    }
    
    /**
     * 查询配置属性<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return List<ConfigProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> queryList(Querier querier) {
        List<ConfigProperty> resList = doQueryList(this.module, querier);
        return resList;
    }
    
    /**
     * 查询配置属性<br/>
     * <功能详细描述>
     * @param module
     * @param querier
     * @return [参数说明]
     * 
     * @return List<ConfigProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> queryList(String module, Querier querier) {
        List<ConfigProperty> resList = doQueryList(module, querier);
        return resList;
    }
    
    /**
     * 根父节点查询子节点<br/>
     * <功能详细描述>
     * @param parentId
     * @param querier
     * @return [参数说明]
     * 
     * @return List<ConfigPropertyItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> queryChildrenByParentId(String parentId,
            Querier querier) {
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        List<ConfigProperty> resList = doQueryChildrenByParentId(this.module,
                parentId,
                querier);
        return resList;
    }
    
    /**
     * 根父节点查询子节点<br/>
     * <功能详细描述>
     * @param module
     * @param parentId
     * @param querier
     * @return [参数说明]
     * 
     * @return List<ConfigPropertyItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> queryChildrenByParentId(String module,
            String parentId, Querier querier) {
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        List<ConfigProperty> resList = doQueryChildrenByParentId(module,
                parentId,
                querier);
        return resList;
    }
    
    /**
     * 嵌套查询子级配置项<br/>
     * <功能详细描述>
     * @param parentId
     * @param querier
     * @return [参数说明]
     * 
     * @return List<ConfigPropertyItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> queryDescendantsByParentId(String parentId,
            Querier querier) {
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        List<ConfigProperty> resList = doQueryDescendantsByParentId(this.module,
                parentId,
                querier);
        return resList;
    }
    
    /**
     * 嵌套查询子级配置项<br/>
     * <功能详细描述>
     * @param module
     * @param parentId
     * @param querier
     * @return [参数说明]
     * 
     * @return List<ConfigPropertyItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> queryDescendantsByParentId(String module,
            String parentId, Querier querier) {
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        List<ConfigProperty> resList = doQueryDescendantsByParentId(module,
                parentId,
                querier);
        return resList;
    }
}
