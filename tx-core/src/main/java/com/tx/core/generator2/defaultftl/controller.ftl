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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ${controller.basePackage}.model.${controller.entityTypeSimpleName};
import ${controller.basePackage}.service.${controller.entityTypeSimpleName}Service;
import com.tx.core.paged.model.PagedList;

/**
 * ${controller.entityTypeSimpleName}显示层逻辑<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-8-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("/${controller.entityTypeSimpleName?uncap_first}")
public class ${controller.entityTypeSimpleName}Controller {
    
    @Resource(name = "${controller.entityTypeSimpleName?uncap_first}Service")
    private ${controller.entityTypeSimpleName}Service ${controller.entityTypeSimpleName?uncap_first}Service;
    
    /**
     * 跳转到查询${controller.entityTypeSimpleName}列表页面<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toQuery${controller.entityTypeSimpleName}List")
    public String toQuery${controller.entityTypeSimpleName}List(ModelMap response) {
        <#list fieldViewMapping?values as fieldView>
            <#if fieldcontroller.javaType.enum >
        response.put("${fieldcontroller.fieldName}List", ${fieldcontroller.javaType.simpleName}.values());
            </#if>
        </#list>

        return "/${packageName}/query${controller.entityTypeSimpleName}List";
    }
    
     /**
      * 跳转到查询${controller.entityTypeSimpleName}分页列表页面<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toQuery${controller.entityTypeSimpleName}PagedList")
    public String toQuery${controller.entityTypeSimpleName}PagedList(ModelMap response) {
        <#list fieldViewMapping?values as fieldView>
            <#if fieldcontroller.javaType.enum >
        response.put("${fieldcontroller.fieldName}List", ${fieldcontroller.javaType.simpleName}.values());
            </#if>
        </#list>

        return "/${packageName}/query${controller.entityTypeSimpleName}PagedList";
    }
    
    /**
      * 跳转到添加${controller.entityTypeSimpleName}页面<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toAdd${controller.entityTypeSimpleName}")
    public String toAdd${controller.entityTypeSimpleName}(ModelMap response) {
        response.put("${controller.entityTypeSimpleName?uncap_first}", new ${controller.entityTypeSimpleName}());

        <#list fieldViewMapping?values as fieldView>
        <#if fieldcontroller.javaType.enum >
        response.put("${fieldcontroller.fieldName}List", ${fieldcontroller.javaType.simpleName}.values());
        </#if>
        </#list>

        return "/${packageName}/add${controller.entityTypeSimpleName}";
    }
    
    /**
     * 跳转到编辑${controller.entityTypeSimpleName}页面
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @RequestMapping("/toUpdate${controller.entityTypeSimpleName}")
    public String toUpdate${controller.entityTypeSimpleName}(
    		@RequestParam("${controller.entityTypeSimpleName?uncap_first}${controller.pkColumn.propertyName?cap_first}") String ${controller.entityTypeSimpleName?uncap_first}${controller.pkColumn.propertyName?cap_first},
            ModelMap response) {
        ${controller.entityTypeSimpleName} res${controller.entityTypeSimpleName} = this.${controller.entityTypeSimpleName?uncap_first}Service.findBy${controller.pkColumn.propertyName?cap_first}(${controller.entityTypeSimpleName?uncap_first}${controller.pkColumn.propertyName?cap_first}); 
        response.put("${controller.entityTypeSimpleName?uncap_first}", res${controller.entityTypeSimpleName});

<#list fieldViewMapping?values as fieldView>
    <#if fieldcontroller.javaType.enum >
        response.put("${fieldcontroller.fieldName}List", ${fieldcontroller.javaType.simpleName}.values());
    </#if>
</#list>
        
        return "/${packageName}/update${controller.entityTypeSimpleName}";
    }

    /**
    * 跳转到编辑${controller.entityTypeSimpleName}页面
    *<功能详细描述>
    * @return [参数说明]
    *
    * @return String [返回类型说明]
    * @exception throws [异常类型] [异常说明]
    * @see [类、类#方法、类#成员]
    */
    @RequestMapping("/toView${controller.entityTypeSimpleName}")
    public String toViewById${controller.entityTypeSimpleName}(
    @RequestParam("${controller.entityTypeSimpleName?uncap_first}${controller.pkColumn.propertyName?cap_first}") String ${controller.entityTypeSimpleName?uncap_first}${controller.pkColumn.propertyName?cap_first},
    ModelMap response) {
${controller.entityTypeSimpleName} res${controller.entityTypeSimpleName} = this.${controller.entityTypeSimpleName?uncap_first}Service.findBy${controller.pkColumn.propertyName?cap_first}(${controller.entityTypeSimpleName?uncap_first}${controller.pkColumn.propertyName?cap_first});
    response.put("${controller.entityTypeSimpleName?uncap_first}", res${controller.entityTypeSimpleName});

    return "/${packageName}/detail${controller.entityTypeSimpleName}";
    }

<#if !ObjectUtils.isEmpty(uniqueGetterNamesArray)>
	<#list uniqueGetterNamesArray as uniqueGetterNames>
    /**
     * 判断${controller.entityTypeSimpleName}:
      	<#list uniqueGetterNames as uniqueGetterName>
     *  ${uniqueGetterName}
     	</#list>
     *
     * 是否已经被使用
		<#list uniqueGetterNames as uniqueGetterName>
	 * @param uniqueGetterName
		</#list>
     * @param exclude${controller.entityTypeSimpleName}${controller.pkColumn.propertyName?cap_first}
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/validate<#list uniqueGetterNames as uniqueGetterName>${uniqueGetterName?cap_first}<#if uniqueGetterName_has_next>And</#if></#list>IsExist")
    public Map<String, String> validate<#list uniqueGetterNames as uniqueGetterName>${uniqueGetterName?cap_first}<#if uniqueGetterName_has_next>And</#if></#list>IsExist(
		<#list uniqueGetterNames as uniqueGetterName>
            @RequestParam("${uniqueGetterName}") String ${uniqueGetterName},
		</#list>
            @RequestParam(value = "${controller.idPropertyName}", required = false) String exclude${controller.entityTypeSimpleName}${controller.pkColumn.propertyName?cap_first},
            @RequestParam MultiValueMap<String, String> request) {
        
        Map<String, String> key2valueMap = new HashMap<String, String>();
		<#list uniqueGetterNames as uniqueGetterName>
        key2valueMap.put("${uniqueGetterName}", ${uniqueGetterName});
		</#list>
        
        boolean flag = this.${controller.entityTypeSimpleName?uncap_first}Service.isExist(key2valueMap, exclude${controller.entityTypeSimpleName}${controller.pkColumn.propertyName?cap_first});
        
        Map<String, String> resMap = new HashMap<String, String>();
        if (!flag) {
        	//FIXME:修改验证重复成功提示信息
            resMap.put("ok", "可用的${controller.entityTypeSimpleName?uncap_first} <#list uniqueGetterNames as uniqueGetterName>${uniqueGetterName}<#if uniqueGetterName_has_next>,</#if></#list>");
        } else {
        	//FIXME:修改验证重复失败提示信息
            resMap.put("error", "已经存在的${controller.entityTypeSimpleName?uncap_first} <#list uniqueGetterNames as uniqueGetterName>${uniqueGetterName}<#if uniqueGetterName_has_next>,</#if></#list>");
        }
        return resMap;
    }
    
	</#list>
</#if>
    /**
     * 查询${controller.entityTypeSimpleName}列表<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return List<${controller.entityTypeSimpleName}> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @ResponseBody
    @RequestMapping("/queryList")
    public List<${controller.entityTypeSimpleName}> queryList(
<#if StringUtils.isNotEmpty(validPropertyName)>
			@RequestParam(value="${validPropertyName}",required=false) Boolean ${validPropertyName},
</#if>
<#list controller.queryConditionName2TypeNameMapping?keys as key>
	<#if validPropertyName != key>
		<#if controller.queryConditionName2TypeNameMapping[key] != 'String'>
			<#if controller.queryConditionName2TypeNameMapping[key] == 'boolean'>
			@RequestParam(value="${key}",required=false) Boolean ${key},
			<#else>
			@RequestParam(value="${key}",required=false) ${controller.queryConditionName2TypeNameMapping[key]} ${key},
			</#if>
		</#if>
	</#if>
</#list> 
    		@RequestParam MultiValueMap<String, String> request
    	) {
        Map<String,Object> params = new HashMap<>();

<#list controller.queryConditionName2TypeNameMapping?keys as key>
	<#if validPropertyName != key>
		<#if controller.queryConditionName2TypeNameMapping[key] != 'String'>
		params.put("${key}",${key});
		<#else>
		params.put("${key}",request.getFirst("${key}"));
		</#if>
	</#if>
</#list> 
    	
        List<${controller.entityTypeSimpleName}> resList = this.${controller.entityTypeSimpleName?uncap_first}Service.queryList(
<#if StringUtils.isNotEmpty(validPropertyName)>
			${validPropertyName},
</#if>
			params         
        );
  
        return resList;
    }
    
    /**
     * 查询${controller.entityTypeSimpleName}分页列表<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return List<${controller.entityTypeSimpleName}> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @ResponseBody
    @RequestMapping("/queryPagedList")
    public PagedList<${controller.entityTypeSimpleName}> queryPagedList(
<#if StringUtils.isNotEmpty(validPropertyName)>
			@RequestParam(value="${validPropertyName}",required=false) Boolean ${validPropertyName},
</#if>
<#list controller.queryConditionName2TypeNameMapping?keys as key>
	<#if validPropertyName != key>
		<#if controller.queryConditionName2TypeNameMapping[key] != 'String'>
			<#if controller.queryConditionName2TypeNameMapping[key] == 'boolean'>
			@RequestParam(value="${key}",required=false) Boolean ${key},
			<#else>
			@RequestParam(value="${key}",required=false) ${controller.queryConditionName2TypeNameMapping[key]} ${key},
			</#if>
		</#if>
	</#if>
</#list>
			@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam MultiValueMap<String, String> request
    	) {
		Map<String,Object> params = new HashMap<>();

<#list controller.queryConditionName2TypeNameMapping?keys as key>
	<#if validPropertyName != key>
		<#if controller.queryConditionName2TypeNameMapping[key] != 'String'>
		params.put("${key}",${key});
		<#else>
		params.put("${key}",request.getFirst("${key}"));
		</#if>
	</#if>
</#list> 

        PagedList<${controller.entityTypeSimpleName}> resPagedList = this.${controller.entityTypeSimpleName?uncap_first}Service.queryPagedList(
<#if StringUtils.isNotEmpty(validPropertyName)>
			${validPropertyName},
</#if>
			params,
			pageIndex,
			pageSize
        );
        return resPagedList;
    }
    
    /**
     * 新增${controller.entityTypeSimpleName}
     * <功能详细描述>
     * @param organization [参数说明]
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
     * 更新${controller.entityTypeSimpleName}<br/>
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
        boolean flag = this.${controller.entityTypeSimpleName?uncap_first}Service.updateBy${controller.pkColumn.propertyName?cap_first}(${controller.entityTypeSimpleName?uncap_first});
        return flag;
    }
    
    /**
     * 删除${controller.entityTypeSimpleName}<br/> 
     * <功能详细描述>
     * @param ${controller.entityTypeSimpleName?uncap_first}${controller.pkColumn.propertyName?cap_first}
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/deleteBy${controller.pkColumn.propertyName?cap_first}")
    public boolean deleteBy${controller.pkColumn.propertyName?cap_first}(@RequestParam(value = "${controller.entityTypeSimpleName?uncap_first}${controller.pkColumn.propertyName?cap_first}") String ${controller.entityTypeSimpleName?uncap_first}${controller.pkColumn.propertyName?cap_first}) {
        boolean flag = this.${controller.entityTypeSimpleName?uncap_first}Service.deleteBy${controller.pkColumn.propertyName?cap_first}(${controller.entityTypeSimpleName?uncap_first}${controller.pkColumn.propertyName?cap_first});
        return flag;
    }
    
<#if controller.validColumn??>
    /**
     * 禁用${controller.entityTypeSimpleName}
     * @param ${controller.entityTypeSimpleName?uncap_first}${controller.pkColumn.propertyName?cap_first}
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/disableBy${controller.pkColumn.propertyName?cap_first}")
    public boolean disableBy${controller.pkColumn.propertyName?cap_first}(@RequestParam(value = "${controller.entityTypeSimpleName?uncap_first}${controller.pkColumn.propertyName?cap_first}") String ${controller.entityTypeSimpleName?uncap_first}${controller.pkColumn.propertyName?cap_first}) {
        boolean flag = this.${controller.entityTypeSimpleName?uncap_first}Service.disableBy${controller.pkColumn.propertyName?cap_first}(${controller.entityTypeSimpleName?uncap_first}${controller.pkColumn.propertyName?cap_first});
        return flag;
    }
    
    /**
     * 启用${controller.entityTypeSimpleName}<br/>
     * <功能详细描述>
     * @param ${controller.entityTypeSimpleName?uncap_first}${controller.pkColumn.propertyName?cap_first}
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/enableBy${controller.pkColumn.propertyName?cap_first}")
    public boolean enableBy${controller.pkColumn.propertyName?cap_first}(@RequestParam(value = "${controller.entityTypeSimpleName?uncap_first}${controller.pkColumn.propertyName?cap_first}") String ${controller.entityTypeSimpleName?uncap_first}${controller.pkColumn.propertyName?cap_first}) {
        boolean flag = this.${controller.entityTypeSimpleName?uncap_first}Service.enableBy${controller.pkColumn.propertyName?cap_first}(${controller.entityTypeSimpleName?uncap_first}${controller.pkColumn.propertyName?cap_first});
        return flag;
    }
</#if>
}