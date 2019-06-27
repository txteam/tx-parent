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
import com.tx.component.role.facade.RoleItemFacade;
import com.tx.component.role.model.RoleItem;
import com.tx.component.role.service.RoleItemService;

import io.swagger.annotations.Api;

/**
 * 角色API控制层[RoleItemAPIController]<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RestController
@Api(tags = "角色API")
@RequestMapping("/api/roleItem")
public class RoleItemAPIController implements RoleItemFacade {
    
    //角色业务层
    @Resource(name = "roleItemService")
    private RoleItemService roleItemService;
    
    /**
     * 新增角色<br/>
     * <功能详细描述>
     * @param roleItem [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public RoleItem insert(@RequestBody RoleItem roleItem) {
        this.roleItemService.insert(roleItem);
        return roleItem;
    }
    
    /**
     * 根据id删除角色<br/> 
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
        boolean flag = this.roleItemService.deleteById(id);
        return flag;
    }
    
    /**
     * 更新角色<br/>
     * <功能详细描述>
     * @param roleItem
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean updateById(@PathVariable(value = "id",required=true) String id,
    		@RequestBody RoleItem roleItem) {
        boolean flag = this.roleItemService.updateById(id,roleItem);
        return flag;
    }
    

    /**
     * 根据主键查询角色<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public RoleItem findById(
            @PathVariable(value = "id", required = true) String id) {
        RoleItem res = this.roleItemService.findById(id);
        
        return res;
    }

    /**
     * 查询角色实例列表<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return List<RoleItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<RoleItem> queryList(
    		@RequestBody Querier querier
    	) {
        List<RoleItem> resList = this.roleItemService.queryList(
			querier         
        );
  
        return resList;
    }
    
    /**
     * 查询角色分页列表<br/>
     * <功能详细描述>
     * @param pageIndex
     * @param pageSize
     * @param querier
     * @return [参数说明]
     * 
     * @return PagedList<RoleItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public PagedList<RoleItem> queryPagedList(
			@RequestBody Querier querier,
			@PathVariable(value = "pageNumber", required = true) int pageIndex,
            @PathVariable(value = "pageSize", required = true) int pageSize
    	) {
        PagedList<RoleItem> resPagedList = this.roleItemService.queryPagedList(
			querier,
			pageIndex,
			pageSize
        );
        return resPagedList;
    }
    
	/**
     * 查询角色数量<br/>
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
        int count = this.roleItemService.count(
        	querier);
        
        return count;
    }

	/**
     * 查询角色是否存在<br/>
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
        boolean flag = this.roleItemService.exists(querier, excludeId);
        
        return flag;
    }

	/**
     * 根据条件查询基础数据分页列表<br/>
     * <功能详细描述>
     * @param parentId
     * @param querier
     * @return [参数说明]
     * 
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<RoleItem> queryChildrenByParentId(@PathVariable(value = "parentId", required = true) String parentId,
            Querier querier){
        List<RoleItem> resList = this.roleItemService.queryChildrenByParentId(parentId,
			querier         
        );
  
        return resList;
    }

	/**
     * 根据条件查询基础数据分页列表<br/>
     * <功能详细描述>
     * @param parentId
     * @param querier
     * @return [参数说明]
     * 
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<RoleItem> queryDescendantsByParentId(@PathVariable(value = "parentId", required = true) String parentId,
            Querier querier){
        List<RoleItem> resList = this.roleItemService.queryDescendantsByParentId(parentId,
			querier         
        );
  
        return resList;
    }
}