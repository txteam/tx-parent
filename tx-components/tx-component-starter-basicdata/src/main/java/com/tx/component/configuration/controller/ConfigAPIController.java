/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年2月19日
 * <修改描述:>
 */
package com.tx.component.configuration.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tx.component.configuration.model.ConfigPropertyItem;
import com.tx.component.configuration.service.ConfigPropertyItemService;
import com.tx.core.exceptions.util.AssertUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

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
@Api(value = "/api/config", tags = "配置容器API")
@RequestMapping(value = "/api/config")
public class ConfigAPIController implements InitializingBean {
    
    /** 当前项目所属模块 */
    private String module;
    
    /** 配置属性项业务层 */
    @Resource
    private ConfigPropertyItemService configPropertyItemService;
    
    /** <默认构造函数> */
    public ConfigAPIController(String module,
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
     * 根据唯一键获取配置项实例<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return ConfigPropertyItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "根据配置项id获取配置属性实例", notes = "")
    @ApiImplicitParam(name = "id", value = "唯一键", required = true, dataType = "string", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ConfigPropertyItem findById(
            @PathVariable(required = true, name = "id") String id) {
        ConfigPropertyItem configProperty = this.configPropertyItemService
                .findById(id);
        
        return configProperty;
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
    @ApiOperation(value = "根据配置项编码获取配置属性实例", notes = "")
    @ApiImplicitParam(name = "code", value = "编码", required = true, dataType = "string", paramType = "path")
    @RequestMapping(value = "/code/{code}", method = RequestMethod.GET)
    public ConfigPropertyItem findByCode(
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
    @ApiOperation(value = "查询配置项清单", notes = "")
    @ApiImplicitParam(name = "params", value = "参数", required = true, dataType = "string", paramType = "path")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ConfigPropertyItem> queryList(
            @RequestParam Map<String, String> params) {
        Map<String, Object> queryParams = new HashMap<>();
        if (MapUtils.isEmpty(params)) {
            for (Entry<String, String> entryTemp : params.entrySet()) {
                queryParams.put(entryTemp.getKey(), entryTemp.getValue());
            }
        }
        List<ConfigPropertyItem> cpiList = this.configPropertyItemService
                .queryList(module, queryParams);
        return cpiList;
    }
    
}
