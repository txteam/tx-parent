/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年2月19日
 * <修改描述:>
 */
package com.tx.component.configuration.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tx.component.configuration.context.ConfigContext;
import com.tx.component.configuration.model.ConfigProperty;
import com.tx.component.configuration.service.ConfigPropertyItemService;

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
public class ConfigAPIController {
    
    /** 配置属性项业务层 */
    @Resource
    private ConfigPropertyItemService configPropertyItemService;
    
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
    @RequestMapping(value = "code/{code}", method = RequestMethod.GET)
    public ConfigProperty findByCode(
            @PathVariable(required = true, name = "code") String code) {
        ConfigProperty configProperty = this.configPropertyItemService.findByCode(module, code);
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
    @ApiOperation(value = "根据配置项编码获取配置属性实例", notes = "")
    @ApiImplicitParam(name = "code", value = "编码", required = true, dataType = "string", paramType = "path")
    @RequestMapping(value = "/queryList", method = RequestMethod.GET)
    public List<ConfigProperty> queryList(Map<String, Object> params) {
        return null;
    }
    
}
