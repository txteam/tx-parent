/*
 * 描       述:  <描述>
 * 修  改 人:  
 * 修改时间:
 * <修改描述:>
 */
package com.tx.component.role.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;
import com.tx.component.role.facade.RoleRefItemFacade;
import com.tx.component.role.model.RoleRefItem;
import com.tx.component.role.service.RoleRefItemService;

import io.swagger.annotations.Api;

/**
 * 角色引用API控制层[RoleRefItemAPIController]<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RestController
@Api(tags = "角色引用API")
@RequestMapping("/api/roleRefItem")
public class RoleRefItemAPIController implements RoleRefItemFacade {
    
    //角色引用业务层
    @Resource(name = "roleRefItemService")
    private RoleRefItemService roleRefItemService;
    
    /**
     * 新增角色引用<br/>
     * <功能详细描述>
     * @param roleRefItem [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public RoleRefItem insert(@RequestBody RoleRefItem roleRefItem) {
        this.roleRefItemService.insert(roleRefItem);
        return roleRefItem;
    }
    
    /**
     * 根据id删除角色引用<br/> 
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean deleteById(
    		@PathVariable(value = "id",required=true) String id) {
        boolean flag = this.roleRefItemService.deleteById(id);
        return flag;
    }
    
    /**
     * 更新角色引用<br/>
     * <功能详细描述>
     * @param roleRefItem
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean updateById(@PathVariable(value = "id",required=true) String id,
    		@RequestBody RoleRefItem roleRefItem) {
        boolean flag = this.roleRefItemService.updateById(id,roleRefItem);
        return flag;
    }
    

    /**
     * 根据主键查询角色引用<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleRefItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public RoleRefItem findById(
            @PathVariable(value = "id", required = true) String id) {
        RoleRefItem res = this.roleRefItemService.findById(id);
        
        return res;
    }

    /**
     * 查询角色引用实例列表<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return List<RoleRefItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<RoleRefItem> queryList(
    		@RequestBody Querier querier
    	) {
        List<RoleRefItem> resList = this.roleRefItemService.queryList(
			querier         
        );
  
        return resList;
    }
    
    /**
     * 查询角色引用分页列表<br/>
     * <功能详细描述>
     * @param pageIndex
     * @param pageSize
     * @param querier
     * @return [参数说明]
     * 
     * @return PagedList<RoleRefItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public PagedList<RoleRefItem> queryPagedList(
			@RequestBody Querier querier,
			@PathVariable(value = "pageNumber", required = true) int pageIndex,
            @PathVariable(value = "pageSize", required = true) int pageSize
    	) {
        PagedList<RoleRefItem> resPagedList = this.roleRefItemService.queryPagedList(
			querier,
			pageIndex,
			pageSize
        );
        return resPagedList;
    }
    
	/**
     * 查询角色引用数量<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public int count(
            @RequestBody Querier querier) {
        int count = this.roleRefItemService.count(
        	querier);
        
        return count;
    }

	/**
     * 查询角色引用是否存在<br/>
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
        boolean flag = this.roleRefItemService.exists(querier, excludeId);
        
        return flag;
    }
}