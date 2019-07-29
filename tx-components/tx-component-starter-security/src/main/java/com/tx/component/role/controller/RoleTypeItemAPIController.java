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
import com.tx.component.role.facade.RoleTypeItemFacade;
import com.tx.component.role.model.RoleTypeItem;
import com.tx.component.role.service.RoleTypeItemService;

import io.swagger.annotations.Api;

/**
 * RoleTypeItemAPI控制层[RoleTypeItemAPIController]<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RestController
@Api(tags = "角色类型API")
@RequestMapping("/api/roleTypeItem")
public class RoleTypeItemAPIController implements RoleTypeItemFacade {
    
    //RoleTypeItem业务层
    @Resource(name = "roleTypeItemService")
    private RoleTypeItemService roleTypeItemService;
    
    /**
     * 新增RoleTypeItem<br/>
     * <功能详细描述>
     * @param roleTypeItem [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public RoleTypeItem insert(@RequestBody RoleTypeItem roleTypeItem) {
        this.roleTypeItemService.insert(roleTypeItem);
        return roleTypeItem;
    }
    
    /**
     * 根据id删除RoleTypeItem<br/> 
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
        boolean flag = this.roleTypeItemService.deleteById(id);
        return flag;
    }
    
    /**
     * 更新RoleTypeItem<br/>
     * <功能详细描述>
     * @param roleTypeItem
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean updateById(
            @PathVariable(value = "id", required = true) String id,
            @RequestBody RoleTypeItem roleTypeItem) {
        boolean flag = this.roleTypeItemService.updateById(id, roleTypeItem);
        return flag;
    }
    
    /**
     * 根据主键查询RoleTypeItem<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return RoleTypeItem [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public RoleTypeItem findById(
            @PathVariable(value = "id", required = true) String id) {
        RoleTypeItem res = this.roleTypeItemService.findById(id);
        
        return res;
    }
    
    /**
     * 查询RoleTypeItem实例列表<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return List<RoleTypeItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<RoleTypeItem> queryList(@RequestBody Querier querier) {
        List<RoleTypeItem> resList = this.roleTypeItemService
                .queryList(querier);
        
        return resList;
    }
    
    /**
     * 查询RoleTypeItem分页列表<br/>
     * <功能详细描述>
     * @param pageIndex
     * @param pageSize
     * @param querier
     * @return [参数说明]
     * 
     * @return PagedList<RoleTypeItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public PagedList<RoleTypeItem> queryPagedList(@RequestBody Querier querier,
            @PathVariable(value = "pageNumber", required = true) int pageIndex,
            @PathVariable(value = "pageSize", required = true) int pageSize) {
        PagedList<RoleTypeItem> resPagedList = this.roleTypeItemService
                .queryPagedList(querier, pageIndex, pageSize);
        return resPagedList;
    }
    
    /**
     * 查询RoleTypeItem数量<br/>
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
        int count = this.roleTypeItemService.count(querier);
        
        return count;
    }
    
    /**
     * 查询RoleTypeItem是否存在<br/>
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
        boolean flag = this.roleTypeItemService.exists(querier, excludeId);
        
        return flag;
    }
}