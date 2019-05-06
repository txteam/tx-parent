/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-8-8
 * <修改描述:>
 */
package com.tx.component.configuration.context;

import java.util.List;
import java.util.Map;

import com.tx.component.configuration.model.ConfigProperty;
import com.tx.core.exceptions.util.AssertUtils;

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
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(code, "code is empty.");
        
        ConfigProperty p = doFind(module, code);
        return p;
    }
    
    /**
     * 查询配置属性<br/>
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return List<ConfigProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> queryList(Map<String, Object> params) {
        List<ConfigProperty> resList = doQueryList(this.module, params);
        return resList;
    }
    
    /**
     * 查询配置属性<br/>
     * <功能详细描述>
     * @param module
     * @param params
     * @return [参数说明]
     * 
     * @return List<ConfigProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> queryList(String module,
            Map<String, Object> params) {
        AssertUtils.notEmpty(module, "module is empty.");
        
        List<ConfigProperty> resList = doQueryList(module, params);
        return resList;
    }
    
    /**
     * 根父节点查询子节点<br/>
     * <功能详细描述>
     * @param parentId
     * @param params
     * @return [参数说明]
     * 
     * @return List<ConfigPropertyItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> queryChildrenByParentId(String parentId,
            Map<String, Object> params) {
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        List<ConfigProperty> resList = doQueryChildrenByParentId(this.module,
                parentId,
                params);
        return resList;
    }
    
    /**
     * 根父节点查询子节点<br/>
     * <功能详细描述>
     * @param module
     * @param parentId
     * @param params
     * @return [参数说明]
     * 
     * @return List<ConfigPropertyItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> queryChildrenByParentId(String module,
            String parentId, Map<String, Object> params) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        List<ConfigProperty> resList = doQueryChildrenByParentId(module,
                parentId,
                params);
        return resList;
    }
    
    /**
     * 嵌套查询子级配置项<br/>
     * <功能详细描述>
     * @param parentId
     * @param params
     * @return [参数说明]
     * 
     * @return List<ConfigPropertyItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> queryDescendantsByParentId(String parentId,
            Map<String, Object> params) {
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        List<ConfigProperty> resList = doQueryDescendantsByParentId(this.module,
                parentId,
                params);
        return resList;
    }
    
    /**
     * 嵌套查询子级配置项<br/>
     * <功能详细描述>
     * @param module
     * @param parentId
     * @param params
     * @return [参数说明]
     * 
     * @return List<ConfigPropertyItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<ConfigProperty> queryDescendantsByParentId(String module,
            String parentId, Map<String, Object> params) {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        List<ConfigProperty> resList = doQueryDescendantsByParentId(module,
                parentId,
                params);
        return resList;
    }
}
