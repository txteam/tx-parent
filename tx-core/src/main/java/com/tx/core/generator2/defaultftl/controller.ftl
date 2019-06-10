/*
 * 描       述:  <描述>
 * 修  改 人:  
 * 修改时间:
 * <修改描述:>
 */
package ${controller.basePackage}.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ${controller.basePackage}.model.${controller.entityTypeSimpleName};
import ${controller.basePackage}.service.${controller.entityTypeSimpleName}Service;
import com.tx.core.paged.model.PagedList;

<#list controller.propertyList as property>
    <#if property.propertyType.isEnum() >
import ${property.propertyType.getName()};
    </#if>
</#list>

/**
 * ${controller.entityComment}控制层<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("/${controller.entityTypeSimpleName?uncap_first}")
public class ${controller.entityTypeSimpleName}Controller {
    
    //${controller.entityComment}业务层
    @Resource(name = "${controller.entityTypeSimpleName?uncap_first}Service")
    private ${controller.entityTypeSimpleName}Service ${controller.entityTypeSimpleName?uncap_first}Service;
    
<#if !(viewType??) || viewType == "LIST">
    /**
     * 跳转到查询${controller.entityComment}列表页面<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toQueryList")
    public String toQueryList(ModelMap response) {
<#list controller.propertyList as property>
    <#if property.propertyType.isEnum() >
		response.put("${property.propertyName}s", ${property.propertyType.getSimpleName()}.values());
    </#if>
</#list>

        return "/${packageName}/query${controller.entityTypeSimpleName}List";
    }
</#if>
    
<#if (viewType??) && viewType == "PAGEDLIST">
    /**
     * 跳转到查询${controller.entityComment}分页列表页面<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toQueryPagedList")
    public String toQueryPagedList(ModelMap response) {
<#list controller.propertyList as property>
    <#if property.propertyType.isEnum() >
		response.put("${property.propertyName}s", ${property.propertyType.getSimpleName()}.values());
    </#if>
</#list>

        return "/${packageName}/query${controller.entityTypeSimpleName}PagedList";
    }
</#if>
    
    /**
     * 跳转到新增${controller.entityComment}页面<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toAdd")
    public String toAdd(ModelMap response) {
    	response.put("${controller.entityTypeSimpleName?uncap_first}", new ${controller.entityTypeSimpleName}());
    	
<#list controller.propertyList as property>
    <#if property.propertyType.isEnum() >
		response.put("${property.propertyName}s", ${property.propertyType.getSimpleName()}.values());
    </#if>
</#list>

        return "/${packageName}/add${controller.entityTypeSimpleName}";
    }
    
    /**
     * 跳转到编辑${controller.entityComment}页面
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(
    		@RequestParam("${controller.pkProperty.propertyName}") ${controller.pkProperty.propertyType.getSimpleName()} ${controller.pkProperty.propertyName},
            ModelMap response) {
        ${controller.entityTypeSimpleName} ${controller.entityTypeSimpleName?uncap_first} = this.${controller.entityTypeSimpleName?uncap_first}Service.findBy${controller.pkProperty.propertyName?cap_first}(${controller.pkProperty.propertyName}); 
        response.put("${controller.entityTypeSimpleName?uncap_first}", ${controller.entityTypeSimpleName?uncap_first});

<#list controller.propertyList as property>
    <#if property.propertyType.isEnum() >
		response.put("${property.propertyName}s", ${property.propertyType.getSimpleName()}.values());
    </#if>
</#list>
        
        return "/${packageName}/update${controller.entityTypeSimpleName}";
    }

    /**
     * 查询${controller.entityComment}实例列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<${controller.entityTypeSimpleName}> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/queryList")
    public List<${controller.entityTypeSimpleName}> queryList(
<#if controller.validProperty??>
			@RequestParam(value="${controller.validProperty.propertyName}",required=false) Boolean ${controller.validProperty.propertyName},
</#if>
    		@RequestParam MultiValueMap<String, String> request
    	) {
        Map<String,Object> params = new HashMap<>();
        //params.put("",request.getFirst(""));
    	
        List<${controller.entityTypeSimpleName}> resList = this.${controller.entityTypeSimpleName?uncap_first}Service.queryList(
<#if controller.validProperty??>
			${controller.validProperty.propertyName},
</#if>
			params         
        );
  
        return resList;
    }
    
    /**
     * 查询${controller.entityComment}实例分页列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<${controller.entityTypeSimpleName}> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/queryPagedList")
    public PagedList<${controller.entityTypeSimpleName}> queryPagedList(
<#if controller.validProperty??>
			@RequestParam(value="${controller.validProperty.propertyName}",required=false) Boolean ${controller.validProperty.propertyName},
</#if>
			@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam MultiValueMap<String, String> request
    	) {
		Map<String,Object> params = new HashMap<>();
		//params.put("",request.getFirst(""));

        PagedList<${controller.entityTypeSimpleName}> resPagedList = this.${controller.entityTypeSimpleName?uncap_first}Service.queryPagedList(
<#if controller.validProperty??>
			${controller.validProperty.propertyName},
</#if>
			params,
			pageIndex,
			pageSize
        );
        return resPagedList;
    }
    
    /**
     * 新增${controller.entityComment}实例
     * <功能详细描述>
     * @param ${controller.entityTypeSimpleName?uncap_first} [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/add")
    public boolean add(${controller.entityTypeSimpleName} ${controller.entityTypeSimpleName?uncap_first}) {
        this.${controller.entityTypeSimpleName?uncap_first}Service.insert(${controller.entityTypeSimpleName?uncap_first});
        return true;
    }
    
    /**
     * 更新${controller.entityComment}实例<br/>
     * <功能详细描述>
     * @param ${controller.entityTypeSimpleName?uncap_first}
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/update")
    public boolean update(${controller.entityTypeSimpleName} ${controller.entityTypeSimpleName?uncap_first}) {
        boolean flag = this.${controller.entityTypeSimpleName?uncap_first}Service.updateBy${controller.pkProperty.propertyName?cap_first}(${controller.entityTypeSimpleName?uncap_first});
        return flag;
    }
    
    /**
     * 根据主键查询${controller.entityComment}实例<br/> 
     * <功能详细描述>
     * @param ${controller.pkProperty.propertyName}
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/findBy${controller.pkProperty.propertyName?cap_first}")
    public ${controller.entityTypeSimpleName} findBy${controller.pkProperty.propertyName?cap_first}(@RequestParam(value = "${controller.pkProperty.propertyName}") ${controller.pkProperty.propertyType.getSimpleName()} ${controller.pkProperty.propertyName}) {
        ${controller.entityTypeSimpleName} ${controller.entityTypeSimpleName?uncap_first} = this.${controller.entityTypeSimpleName?uncap_first}Service.findBy${controller.pkProperty.propertyName?cap_first}(${controller.pkProperty.propertyName});
        return ${controller.entityTypeSimpleName?uncap_first};
    }

<#if controller.codeProperty??>
	/**
     * 根据编码查询${controller.entityComment}实例<br/> 
     * <功能详细描述>
     * @param ${controller.codeProperty.propertyName}
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/findBy${controller.codeProperty.propertyName?cap_first}")
    public ${controller.entityTypeSimpleName} findBy${controller.codeProperty.propertyName?cap_first}(@RequestParam(value = "${controller.codeProperty.propertyName}") ${controller.codeProperty.propertyType.getSimpleName()} ${controller.codeProperty.propertyName}) {
        ${controller.entityTypeSimpleName} ${controller.entityTypeSimpleName?uncap_first} = this.${controller.entityTypeSimpleName?uncap_first}Service.findBy${controller.codeProperty.propertyName?cap_first}(${controller.codeProperty.propertyName});
        return ${controller.entityTypeSimpleName?uncap_first};
    }
    
</#if>
    /**
     * 删除${controller.entityComment}实例<br/> 
     * <功能详细描述>
     * @param ${controller.pkProperty.propertyName}
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/deleteBy${controller.pkProperty.propertyName?cap_first}")
    public boolean deleteBy${controller.pkProperty.propertyName?cap_first}(@RequestParam(value = "${controller.pkProperty.propertyName}") ${controller.pkProperty.propertyType.getSimpleName()} ${controller.pkProperty.propertyName}) {
        boolean flag = this.${controller.entityTypeSimpleName?uncap_first}Service.deleteBy${controller.pkProperty.propertyName?cap_first}(${controller.pkProperty.propertyName});
        return flag;
    }
    
<#if controller.validProperty??>
    /**
     * 禁用${controller.entityComment}实例
     * @param ${controller.pkProperty.propertyName}
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/disableBy${controller.pkProperty.propertyName?cap_first}")
    public boolean disableBy${controller.pkProperty.propertyName?cap_first}(@RequestParam(value = "${controller.pkProperty.propertyName}") ${controller.pkProperty.propertyType.getSimpleName()} ${controller.pkProperty.propertyName}) {
        boolean flag = this.${controller.entityTypeSimpleName?uncap_first}Service.disableBy${controller.pkProperty.propertyName?cap_first}(${controller.pkProperty.propertyName});
        return flag;
    }
    
    /**
     * 启用${controller.entityComment}实例<br/>
     * <功能详细描述>
     * @param ${controller.pkProperty.propertyName}
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/enableBy${controller.pkProperty.propertyName?cap_first}")
    public boolean enableBy${controller.pkProperty.propertyName?cap_first}(@RequestParam(value = "${controller.pkProperty.propertyName}") ${controller.pkProperty.propertyType.getSimpleName()} ${controller.pkProperty.propertyName}) {
        boolean flag = this.${controller.entityTypeSimpleName?uncap_first}Service.enableBy${controller.pkProperty.propertyName?cap_first}(${controller.pkProperty.propertyName});
        return flag;
    }
</#if>

	/**
     * 校验参数对应实例是否重复
	 * @param exclude${controller.pkProperty.propertyName?cap_first}
     * @param params
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/check/{exclude${controller.pkProperty.propertyName?cap_first}}")
    public Map<String, String> check(
            @PathVariable(value = "exclude${controller.pkProperty.propertyName?cap_first}", required = false) ${controller.pkProperty.propertyType.getSimpleName()} exclude${controller.pkProperty.propertyName?cap_first},
            @RequestParam Map<String, String> params) {
        boolean flag = this.${controller.entityTypeSimpleName?uncap_first}Service.exists(params, exclude${controller.pkProperty.propertyName?cap_first});
        
        Map<String, String> resMap = new HashMap<String, String>();
        if (!flag) {
            resMap.put("ok", "");
        } else {
            resMap.put("error", "重复值");
        }
        return resMap;
    }
}