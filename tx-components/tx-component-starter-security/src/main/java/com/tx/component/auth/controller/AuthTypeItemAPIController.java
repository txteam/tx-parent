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
import com.tx.component.auth.facade.AuthTypeItemFacade;
import com.tx.component.auth.model.AuthTypeItem;
import com.tx.component.auth.service.AuthTypeItemService;

import io.swagger.annotations.Api;

/**
 * 权限类型API控制层[AuthTypeItemAPIController]<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RestController
@Api(tags = "权限类型API")
@RequestMapping("/api/authTypeItem")
public class AuthTypeItemAPIController implements AuthTypeItemFacade {
    
    //权限类型业务层
    @Resource(name = "authTypeItemService")
    private AuthTypeItemService authTypeItemService;
    
    /**
     * 新增权限类型<br/>
     * <功能详细描述>
     * @param authTypeItem [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public AuthTypeItem insert(@RequestBody AuthTypeItem authTypeItem) {
        this.authTypeItemService.insert(authTypeItem);
        return authTypeItem;
    }
    
    /**
     * 根据id删除权限类型<br/> 
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
            @PathVariable(value = "id", required = true) String id) {
        boolean flag = this.authTypeItemService.deleteById(id);
        return flag;
    }
    
    /**
     * 更新权限类型<br/>
     * <功能详细描述>
     * @param authTypeItem
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean updateById(
            @PathVariable(value = "id", required = true) String id,
            @RequestBody AuthTypeItem authTypeItem) {
        boolean flag = this.authTypeItemService.updateById(id, authTypeItem);
        return flag;
    }
    
    /**
     * 根据主键查询权限类型<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return AuthTypeItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public AuthTypeItem findById(
            @PathVariable(value = "id", required = true) String id) {
        AuthTypeItem res = this.authTypeItemService.findById(id);
        
        return res;
    }
    
    /**
     * 查询权限类型实例列表<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return List<AuthTypeItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<AuthTypeItem> queryList(@RequestBody Querier querier) {
        List<AuthTypeItem> resList = this.authTypeItemService
                .queryList(querier);
        
        return resList;
    }
    
    /**
     * 查询权限类型分页列表<br/>
     * <功能详细描述>
     * @param pageIndex
     * @param pageSize
     * @param querier
     * @return [参数说明]
     * 
     * @return PagedList<AuthTypeItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public PagedList<AuthTypeItem> queryPagedList(@RequestBody Querier querier,
            @PathVariable(value = "pageNumber", required = true) int pageIndex,
            @PathVariable(value = "pageSize", required = true) int pageSize) {
        PagedList<AuthTypeItem> resPagedList = this.authTypeItemService
                .queryPagedList(querier, pageIndex, pageSize);
        return resPagedList;
    }
    
    /**
     * 查询权限类型数量<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public int count(@RequestBody Querier querier) {
        int count = this.authTypeItemService.count(querier);
        
        return count;
    }
    
    /**
     * 查询权限类型是否存在<br/>
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
        boolean flag = this.authTypeItemService.exists(querier, excludeId);
        
        return flag;
    }
}