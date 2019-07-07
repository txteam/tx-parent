/*
 * 描       述:  <描述>
 * 修  改 人:  
 * 修改时间:
 * <修改描述:>
 */
package com.tx.component.auth.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;
import com.tx.component.auth.facade.AuthItemFacade;
import com.tx.component.auth.model.AuthItem;
import com.tx.component.auth.service.AuthItemService;

import io.swagger.annotations.Api;

/**
 * 权限项API控制层[AuthItemAPIController]<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RestController
@Api(tags = "权限项API")
@RequestMapping("/api/authItem")
public class AuthItemAPIController implements AuthItemFacade {
    
    //权限项业务层
    @Resource(name = "authItemService")
    private AuthItemService authItemService;
    
    /**
     * 新增权限项<br/>
     * <功能详细描述>
     * @param authItem [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public AuthItem insert(@RequestBody AuthItem authItem) {
        this.authItemService.insert(authItem);
        return authItem;
    }
    
    /**
     * 根据id删除权限项<br/> 
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
        boolean flag = this.authItemService.deleteById(id);
        return flag;
    }
    
    /**
     * 更新权限项<br/>
     * <功能详细描述>
     * @param authItem
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean updateById(@PathVariable(value = "id",required=true) String id,
    		@RequestBody AuthItem authItem) {
        boolean flag = this.authItemService.updateById(id,authItem);
        return flag;
    }
    

    /**
     * 根据主键查询权限项<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return AuthItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public AuthItem findById(
            @PathVariable(value = "id", required = true) String id) {
        AuthItem res = this.authItemService.findById(id);
        
        return res;
    }

    /**
     * 查询权限项实例列表<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return List<AuthItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<AuthItem> queryList(
    		@RequestBody Querier querier
    	) {
        List<AuthItem> resList = this.authItemService.queryList(
			querier         
        );
  
        return resList;
    }
    
    /**
     * 查询权限项分页列表<br/>
     * <功能详细描述>
     * @param pageIndex
     * @param pageSize
     * @param querier
     * @return [参数说明]
     * 
     * @return PagedList<AuthItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public PagedList<AuthItem> queryPagedList(
			@RequestBody Querier querier,
			@PathVariable(value = "pageNumber", required = true) int pageIndex,
            @PathVariable(value = "pageSize", required = true) int pageSize
    	) {
        PagedList<AuthItem> resPagedList = this.authItemService.queryPagedList(
			querier,
			pageIndex,
			pageSize
        );
        return resPagedList;
    }
    
	/**
     * 查询权限项数量<br/>
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
        int count = this.authItemService.count(
        	querier);
        
        return count;
    }

	/**
     * 查询权限项是否存在<br/>
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
        boolean flag = this.authItemService.exists(querier, excludeId);
        
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
    public List<AuthItem> queryChildrenByParentId(@PathVariable(value = "parentId", required = true) String parentId,
            Querier querier){
        List<AuthItem> resList = this.authItemService.queryChildrenByParentId(parentId,
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
    public List<AuthItem> queryDescendantsByParentId(@PathVariable(value = "parentId", required = true) String parentId,
            Querier querier){
        List<AuthItem> resList = this.authItemService.queryDescendantsByParentId(parentId,
			querier         
        );
  
        return resList;
    }
}