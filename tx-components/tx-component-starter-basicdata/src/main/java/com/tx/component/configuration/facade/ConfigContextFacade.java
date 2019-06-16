/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月16日
 * <修改描述:>
 */
package com.tx.component.configuration.facade;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tx.component.configuration.model.ConfigProperty;
import com.tx.core.querier.model.Querier;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 配置容器门面层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ConfigContextFacade {
    
    /**
     * 修改配置项值<br/>
     * <功能详细描述>
     * @param code
     * @param value
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "修改配置属性值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "code", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "value", value = "value", required = true, dataType = "string", paramType = "path") })
    @RequestMapping(value = "/{code}/{value}", method = RequestMethod.PATCH)
    public boolean patch(
            @PathVariable(required = true, name = "code") String code,
            @PathVariable(required = false, name = "value") String value);
    
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
    @ApiOperation(value = "根据配置项编码获取配置属性实例")
    @ApiImplicitParam(name = "code", value = "编码", required = true, dataType = "string", paramType = "path")
    @RequestMapping(value = "/code/{code}", method = RequestMethod.GET)
    public ConfigProperty findByCode(
            @PathVariable(required = true, name = "code") String code);
    
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
    @ApiOperation(value = "查询配置项清单")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ConfigProperty> queryList(@RequestBody Querier querier);
    
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
    @ApiOperation(value = "查询子代节点配置项列表")
    @RequestMapping(value = "/children/{parentId}", method = RequestMethod.GET)
    public List<ConfigProperty> queryChildrenByParentId(
            @PathVariable(required = true, name = "parentId") String parentId,
            @RequestBody Querier querier);
    
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
    @ApiOperation(value = "查询后代节点配置项列表", notes = "")
    @RequestMapping(value = "/descendants/{parentId}", method = RequestMethod.GET)
    public List<ConfigProperty> queryDescendantsByParentId(
            @PathVariable(required = true, name = "parentId") String parentId,
            @RequestBody Querier querier);
}
