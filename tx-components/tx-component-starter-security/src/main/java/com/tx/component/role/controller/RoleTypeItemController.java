/*
 * 描       述:  <描述>
 * 修  改 人:  
 * 修改时间:
 * <修改描述:>
 */
package com.tx.component.role.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tx.component.role.model.RoleTypeItem;
import com.tx.component.role.service.RoleTypeItemService;
import com.tx.core.paged.model.PagedList;


/**
 * RoleTypeItem控制层<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("/roleTypeItem")
public class RoleTypeItemController {
    
    //RoleTypeItem业务层
    @Resource(name = "roleTypeItemService")
    private RoleTypeItemService roleTypeItemService;
    
    /**
     * 跳转到查询RoleTypeItem列表页面<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toQueryList")
    public String toQueryList(ModelMap response) {

        return "/role/queryRoleTypeItemList";
    }
    /**
     * 跳转到查询RoleTypeItem列表页面<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toQueryTreeList")
    public String toQueryTreeList(ModelMap response) {

        return "/role/queryRoleTypeItemTreeList";
    }
    
    /**
     * 跳转到新增RoleTypeItem页面<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toAdd")
    public String toAdd(ModelMap response) {
    	response.put("roleTypeItem", new RoleTypeItem());
    	

        return "/role/addRoleTypeItem";
    }
    
    /**
     * 跳转到编辑RoleTypeItem页面
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(
    		@RequestParam("id") String id,
            ModelMap response) {
        RoleTypeItem roleTypeItem = this.roleTypeItemService.findById(id); 
        response.put("roleTypeItem", roleTypeItem);

        
        return "/role/updateRoleTypeItem";
    }

    /**
     * 查询RoleTypeItem实例列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<RoleTypeItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/queryList")
    public List<RoleTypeItem> queryList(
    		@RequestParam MultiValueMap<String, String> request
    	) {
        Map<String,Object> params = new HashMap<>();
        //params.put("",request.getFirst(""));
    	
        List<RoleTypeItem> resList = this.roleTypeItemService.queryList(
			params         
        );
  
        return resList;
    }
    
    /**
     * 查询RoleTypeItem实例分页列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<RoleTypeItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/queryPagedList")
    public PagedList<RoleTypeItem> queryPagedList(
			@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam MultiValueMap<String, String> request
    	) {
		Map<String,Object> params = new HashMap<>();
		//params.put("",request.getFirst(""));

        PagedList<RoleTypeItem> resPagedList = this.roleTypeItemService.queryPagedList(
			params,
			pageIndex,
			pageSize
        );
        return resPagedList;
    }
    
    /**
     * 新增RoleTypeItem实例
     * <功能详细描述>
     * @param roleTypeItem [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/add")
    public boolean add(RoleTypeItem roleTypeItem) {
        this.roleTypeItemService.insert(roleTypeItem);
        return true;
    }
    
    /**
     * 更新RoleTypeItem实例<br/>
     * <功能详细描述>
     * @param roleTypeItem
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/update")
    public boolean update(RoleTypeItem roleTypeItem) {
        boolean flag = this.roleTypeItemService.updateById(roleTypeItem);
        return flag;
    }
    
    /**
     * 根据主键查询RoleTypeItem实例<br/> 
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/findById")
    public RoleTypeItem findById(@RequestParam(value = "id") String id) {
        RoleTypeItem roleTypeItem = this.roleTypeItemService.findById(id);
        return roleTypeItem;
    }

    /**
     * 删除RoleTypeItem实例<br/> 
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/deleteById")
    public boolean deleteById(@RequestParam(value = "id") String id) {
        boolean flag = this.roleTypeItemService.deleteById(id);
        return flag;
    }
    

	/**
     * 校验参数对应实例是否重复
	 * @param excludeId
     * @param params
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/validate")
    public Map<String, String> check(
            @RequestParam(value = "excludeId", required = false) String excludeId,
            @RequestParam Map<String, String> params) {
        boolean flag = this.roleTypeItemService.exists(params, excludeId);
        
        Map<String, String> resMap = new HashMap<String, String>();
        if (!flag) {
            resMap.put("ok", "");
        } else {
            resMap.put("error", "重复值");
        }
        return resMap;
    }
}