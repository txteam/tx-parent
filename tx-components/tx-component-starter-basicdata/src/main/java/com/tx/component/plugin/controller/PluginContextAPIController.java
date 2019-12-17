/*
 * 描       述:  <描述>
 * 修  改 人:  
 * 修改时间:
 * <修改描述:>
 */
package com.tx.component.plugin.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tx.component.plugin.context.Plugin;
import com.tx.component.plugin.context.PluginContext;
import com.tx.component.plugin.facade.PluginContextFacade;
import com.tx.component.plugin.model.PluginInstance;
import com.tx.component.plugin.service.PluginInstanceService;
import com.tx.core.querier.model.Querier;

import io.swagger.annotations.Api;

/**
 * PluginInstanceAPI控制层[PluginInstanceAPIController]<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RestController
@Api(tags = "插件容器API")
@RequestMapping("/api/plugin")
public class PluginContextAPIController implements PluginContextFacade {
    
    private PluginInstanceService pluginInstanceService;
    
    /** <默认构造函数> */
    public PluginContextAPIController() {
        super();
    }
    
    /** <默认构造函数> */
    public PluginContextAPIController(
            PluginInstanceService pluginInstanceService) {
        super();
        this.pluginInstanceService = pluginInstanceService;
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    public boolean install(
            @PathVariable(value = "id", required = true) String id) {
        Plugin<?> plugin = PluginContext.getContext().getPlugin(id);
        if (plugin == null) {
            return false;
        }
        boolean flag = plugin.install();
        return flag;
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    public boolean uninstall(
            @PathVariable(value = "id", required = true) String id) {
        Plugin<?> plugin = PluginContext.getContext().getPlugin(id);
        if (plugin == null) {
            return false;
        }
        boolean flag = plugin.uninstall();
        return flag;
    }
    
    /**
     * @param id
     * @param requestMap
     * @return
     */
    @Override
    public boolean setting(
            @PathVariable(value = "id", required = true) String id,
            @RequestParam HashMap<String, String> requestMap) {
        Plugin<?> plugin = PluginContext.getContext().getPlugin(id);
        if (plugin == null) {
            return false;
        }
        boolean flag = plugin.setting(requestMap);
        return flag;
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    public boolean enableById(
            @PathVariable(value = "id", required = true) String id) {
        Plugin<?> plugin = PluginContext.getContext().getPlugin(id);
        if (plugin == null) {
            return false;
        }
        boolean flag = plugin.enable();
        return flag;
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    public boolean disableById(
            @PathVariable(value = "id", required = true) String id) {
        Plugin<?> plugin = PluginContext.getContext().getPlugin(id);
        if (plugin == null) {
            return false;
        }
        boolean flag = plugin.disable();
        return flag;
    }
    
    /**
     * 查询PluginInstance实例列表<br/>
     * <功能详细描述>
     * @param valid
     * @param querier
     * @return [参数说明]
     * 
     * @return List<PluginInstance> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<PluginInstance> queryList(
            @RequestParam(value = "valid", required = false) Boolean valid,
            @RequestBody Querier querier) {
        List<PluginInstance> resList = this.pluginInstanceService
                .queryList(valid, querier);
        return resList;
    }
    
    /**
     * 根据主键查询PluginInstance<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return PluginInstance [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public PluginInstance findById(
            @PathVariable(value = "id", required = true) String id) {
        PluginInstance res = this.pluginInstanceService.findById(id);
        
        return res;
    }
    
    /**
     * 查询PluginInstance是否存在<br/>
     * @param excludeId
     * @param querier
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean exists(@RequestBody Querier querier,
            @RequestParam(value = "excludeId", required = false) String excludeId) {
        boolean flag = this.pluginInstanceService.exists(querier, excludeId);
        
        return flag;
    }
}