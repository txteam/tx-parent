/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年3月22日
 * <修改描述:>
 */
package com.tx.component.configuration.persister.impl;

import java.util.List;
import java.util.Map;

import com.tx.component.configuration.model.ConfigProperty;
import com.tx.component.configuration.persister.ConfigPropertyPersister;

/**
 * 配置属性持久化责任链模式实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年3月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigPropertyPersisterComposite
        implements ConfigPropertyPersister {
    
    /**
     * @param module
     * @return
     */
    @Override
    public boolean supportsModule(String module) {
        return getConfigPropertyPersister(module) != null;
    }
    
    /**
     * @param code
     * @return
     */
    public ConfigProperty findByCode(String module, String code) {
        ConfigPropertyPersister persister = getConfigPropertyPersister(module);
        
        ConfigProperty cp = persister.findByCode(module, code);
        return cp;
    }
    
    /**
     * @param module
     * @param params
     * @return
     */
    @Override
    public List<ConfigProperty> queryList(String module,
            Map<String, Object> params) {
        ConfigPropertyPersister persister = getConfigPropertyPersister(module);
        
        List<ConfigProperty> resList = persister.queryList(module, params);
        return resList;
    }
    
    /**
     * @param module
     * @param parentId
     * @param params
     * @return
     */
    @Override
    public List<ConfigProperty> queryNestedListByParentId(String module,
            String parentId, Map<String, Object> params) {
        ConfigPropertyPersister persister = getConfigPropertyPersister(module);
        
        List<ConfigProperty> resList = persister
                .queryNestedListByParentId(module, parentId, params);
        return resList;
    }
    
    /**
     * @param module
     * @param code
     * @param value
     * @return
     */
    @Override
    public boolean update(String module, String code, String value) {
        ConfigPropertyPersister persister = getConfigPropertyPersister(module);
        
        boolean res = persister.update(module, code, value);
        return res;
    }
    
    /**
     * 根据方法参数对象获取对应的方法参数解析器对象<br/> 
     * <功能详细描述>
     * @param parameter
     * @return [参数说明]
     * 
     * @return HandlerMethodArgumentResolver [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private ConfigPropertyPersister getConfigPropertyPersister(String module) {
        //        MethodArgumentResolver result = this.argumentResolverCache
        //                .get(parameter);
        //        if (result == null) {
        //            //初始化参数名获取方式<br/>
        //            parameter.initParameterNameDiscovery(this.parameterNameDiscoverer);
        //            
        //            //查找方法参数对应的参数解析器<br/>
        //            for (MethodArgumentResolver methodArgumentResolver : this.argumentResolvers) {
        //                if (logger.isTraceEnabled()) {
        //                    logger.trace("Testing if argument resolver ["
        //                            + methodArgumentResolver + "] supports ["
        //                            + parameter.getGenericParameterType() + "]");
        //                }
        //                
        //                if (methodArgumentResolver.supportsParameter(parameter)) {
        //                    result = methodArgumentResolver;
        //                    this.argumentResolverCache.put(parameter, result);
        //                    break;
        //                }
        //            }
        //        }
        return null;
    }
}
