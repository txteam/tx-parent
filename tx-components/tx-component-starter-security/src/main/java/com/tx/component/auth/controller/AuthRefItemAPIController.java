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
import com.tx.component.auth.facade.AuthRefItemFacade;
import com.tx.component.auth.model.AuthRefItem;
import com.tx.component.auth.service.AuthRefItemService;

import io.swagger.annotations.Api;

/**
 * 权限引用API控制层[AuthRefItemAPIController]<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RestController
@Api(tags = "权限引用API")
@RequestMapping("/api/authRefItem")
public class AuthRefItemAPIController implements AuthRefItemFacade {
    
    //权限引用业务层
    @Resource(name = "authRefItemService")
    private AuthRefItemService authRefItemService;
    
    /**
     * 新增权限引用<br/>
     * <功能详细描述>
     * @param authRefItem [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public AuthRefItem insert(@RequestBody AuthRefItem authRefItem) {
        this.authRefItemService.insert(authRefItem);
        return authRefItem;
    }
    
    /**
     * 根据id删除权限引用<br/> 
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
        boolean flag = this.authRefItemService.deleteById(id);
        return flag;
    }
    
    /**
     * 更新权限引用<br/>
     * <功能详细描述>
     * @param authRefItem
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean updateById(@PathVariable(value = "id",required=true) String id,
    		@RequestBody AuthRefItem authRefItem) {
        boolean flag = this.authRefItemService.updateById(id,authRefItem);
        return flag;
    }
    

    /**
     * 根据主键查询权限引用<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return AuthRefItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public AuthRefItem findById(
            @PathVariable(value = "id", required = true) String id) {
        AuthRefItem res = this.authRefItemService.findById(id);
        
        return res;
    }

    /**
     * 查询权限引用实例列表<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return List<AuthRefItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<AuthRefItem> queryList(
    		@RequestBody Querier querier
    	) {
        List<AuthRefItem> resList = this.authRefItemService.queryList(
			querier         
        );
  
        return resList;
    }
    
    /**
     * 查询权限引用分页列表<br/>
     * <功能详细描述>
     * @param pageIndex
     * @param pageSize
     * @param querier
     * @return [参数说明]
     * 
     * @return PagedList<AuthRefItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public PagedList<AuthRefItem> queryPagedList(
			@RequestBody Querier querier,
			@PathVariable(value = "pageNumber", required = true) int pageIndex,
            @PathVariable(value = "pageSize", required = true) int pageSize
    	) {
        PagedList<AuthRefItem> resPagedList = this.authRefItemService.queryPagedList(
			querier,
			pageIndex,
			pageSize
        );
        return resPagedList;
    }
    
	/**
     * 查询权限引用数量<br/>
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
        int count = this.authRefItemService.count(
        	querier);
        
        return count;
    }

	/**
     * 查询权限引用是否存在<br/>
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
        boolean flag = this.authRefItemService.exists(querier, excludeId);
        
        return flag;
    }
}