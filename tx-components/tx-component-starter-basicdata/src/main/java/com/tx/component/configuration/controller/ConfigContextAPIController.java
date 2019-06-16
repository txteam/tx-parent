/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年2月19日
 * <修改描述:>
 */
package com.tx.component.configuration.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tx.component.configuration.facade.ConfigContextFacade;
import com.tx.component.configuration.model.ConfigProperty;
import com.tx.component.configuration.model.ConfigPropertyItem;
import com.tx.component.configuration.service.ConfigPropertyItemService;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.querier.model.Querier;

import io.swagger.annotations.Api;

/**
 * 配置属性控制器 <br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年2月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@RestController
@Api(tags = "配置容器API")
@RequestMapping(value = "/api/config")
public class ConfigContextAPIController
        implements ConfigContextFacade, InitializingBean {
    
    /** 当前项目所属模块 */
    private String module;
    
    /** 配置属性项业务层 */
    @Resource
    private ConfigPropertyItemService configPropertyItemService;
    
    /** <默认构造函数> */
    public ConfigContextAPIController(String module,
            ConfigPropertyItemService configPropertyItemService) {
        super();
        this.module = module;
        this.configPropertyItemService = configPropertyItemService;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notNull(configPropertyItemService,
                "configPropertyItemService is empty.");
    }
    
    /**
     * 修改配置属性值<br/>
     * <功能详细描述>
     * @param code
     * @param value
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean patch(
            @PathVariable(required = true, name = "code") String code,
            @PathVariable(required = false, name = "value") String value) {
        boolean res = this.configPropertyItemService.patch(this.module,
                code,
                value);
        return res;
    }
    
    /**
     * 根据配置项编码获取配置属性实例<br/>
     * <功能详细描述>
     * @param code
     * @return [参数说明]
     * 
     * @return ConfigProperty [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public ConfigProperty findByCode(
            @PathVariable(required = true, name = "code") String code) {
        ConfigPropertyItem configProperty = this.configPropertyItemService
                .findByCode(this.module, code);
        return configProperty;
    }
    
    /**
     * 获取配置属性树节点<br/>
     * <功能详细描述>
     * @param configPropertyGroupName
     * @return [参数说明]
     * 
     * @return List<TreeNode> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<ConfigProperty> queryList(@RequestBody Querier querier) {
        List<ConfigProperty> cpiList = this.configPropertyItemService
                .queryList(this.module, querier)
                .stream()
                .collect(Collectors.toList());
        return cpiList;
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
    @Override
    public List<ConfigProperty> queryChildrenByParentId(
            @PathVariable(required = true, name = "parentId") String parentId,
            @RequestBody Querier querier) {
        List<ConfigProperty> cpiList = this.configPropertyItemService
                .queryChildrenByParentId(this.module, parentId, querier)
                .stream()
                .collect(Collectors.toList());
        return cpiList;
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
    @Override
    public List<ConfigProperty> queryDescendantsByParentId(
            @PathVariable(required = true, name = "parentId") String parentId,
            @RequestBody Querier querier) {
        List<ConfigProperty> cpiList = this.configPropertyItemService
                .queryDescendantsByParentId(this.module, parentId, querier)
                .stream()
                .collect(Collectors.toList());
        return cpiList;
    }
    
}
