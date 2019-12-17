/*
 * 描       述:  <描述>
 * 修  改 人:  
 * 修改时间:
 * <修改描述:>
 */
package com.tx.component.plugin.facade;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tx.component.plugin.model.PluginInstance;
import com.tx.core.querier.model.Querier;

import io.swagger.annotations.ApiOperation;

/**
 * 插件实例e接口门面层[PluginInstanceFacade]<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface PluginContextFacade {
    
    /**
     * 查询插件实例实例列表<br/>
     * <功能详细描述>
     * @param valid
     * @param querier
     * @return [参数说明]
     * 
     * @return List<PluginInstance> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "查询插件实例列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<PluginInstance> queryList(
            @RequestParam(value = "valid", required = false) Boolean valid,
            @RequestBody Querier querier);
    
    /**
     * 新增插件实例<br/>
     * <功能详细描述>
     * @param pluginInstance [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "安装指定插件")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public boolean install(
            @PathVariable(value = "id", required = true) String id);
    
    /**
     * 根据id删除插件实例<br/> 
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "卸载指定插件")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean uninstall(
            @PathVariable(value = "id", required = true) String id);
    
    /**
     * 更新插件实例<br/>
     * <功能详细描述>
     * @param pluginInstance
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "配置插件实例")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public boolean setting(
            @PathVariable(value = "id", required = true) String id,
            @RequestParam HashMap<String, String> requestMap);
    
    /**
     * 启用PluginInstance<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "启用插件")
    @RequestMapping(value = "/enable/{id}", method = RequestMethod.PATCH)
    public boolean enableById(
            @PathVariable(value = "id", required = true) String id);
    
    /**
     * 禁用PluginInstance<br/>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "禁用插件")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.PATCH)
    public boolean disableById(
            @PathVariable(value = "id", required = true) String id);
    
    /**
     * 根据主键查询插件实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return PluginInstance [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "根据主键查询插件实例")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PluginInstance findById(
            @PathVariable(value = "id", required = true) String id);
    
    /**
     * 查询插件实例是否存在<br/>
     * @param excludeId
     * @param querier
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "查询插件实例是否存在")
    @RequestMapping(value = "/exists", method = RequestMethod.GET)
    public boolean exists(@RequestBody Querier querier,
            @RequestParam(value = "excludeId", required = false) String excludeId);
}