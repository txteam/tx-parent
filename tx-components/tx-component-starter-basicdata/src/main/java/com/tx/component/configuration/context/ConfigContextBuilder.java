/**
  * 文 件 名:  ConfigContext.java
 * 版    权:  TX Workgroup . Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  PengQingyang
 * 修改时间:  2012-10-5
 * <修改描述:>
 */
package com.tx.component.configuration.context;

import java.util.List;

import com.tx.component.configuration.model.ConfigProperty;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.querier.model.Querier;

/**
 * 配置容器基础配置吃撑类<br/>
 * 用以加载系统配置，支持动态加载系统中各配置<br/>
 * 
 * @author PengQingyang
 * @version [版本号, 2012-10-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class ConfigContextBuilder extends ConfigContextConfigurator {
    
    /**
     * @throws Exception
     */
    @Override
    protected void doBuild() throws Exception {
        AssertUtils.notNull(composite, "composite is null.");
        AssertUtils.notEmpty(this.module, "module is empty.");
    }
    
    /**
     * 根据code获取配置属性<br/>
     * <功能详细描述>
     * @param module
     * @param code
     * @return [参数说明]
     * 
     * @return ConfigProperty [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected ConfigProperty doFind(String module, String code) {
        ConfigProperty cp = this.composite.findByCode(module, code);
        return cp;
    }
    
    /**
     * 查询配置项目列表<br/>
     * <功能详细描述>
     * @param module
     * @param querier
     * @return [参数说明]
     * 
     * @return List<ConfigProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected List<ConfigProperty> doQueryList(String module, Querier querier) {
        List<ConfigProperty> cpList = this.composite.queryList(module, querier);
        return cpList;
    }
    
    /**
     * 查询配置项目列表<br/>
     * <功能详细描述>
     * @param module
     * @param parentId
     * @param querier
     * @return [参数说明]
     * 
     * @return List<ConfigProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected List<ConfigProperty> doQueryChildrenByParentId(String module,
            String parentId, Querier querier) {
        List<ConfigProperty> cpList = this.composite
                .queryChildrenByParentId(module, parentId, querier);
        return cpList;
    }
    
    /**
     * 查询配置项目列表<br/>
     * <功能详细描述>
     * @param module
     * @param parentId
     * @param querier
     * @return [参数说明]
     * 
     * @return List<ConfigProperty> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected List<ConfigProperty> doQueryDescendantsByParentId(String module,
            String parentId, Querier querier) {
        List<ConfigProperty> cpList = this.composite
                .queryDescendantsByParentId(module, parentId, querier);
        return cpList;
    }
    
}
