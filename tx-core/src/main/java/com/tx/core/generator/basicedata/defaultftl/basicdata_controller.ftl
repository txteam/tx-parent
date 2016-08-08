/*
 * 描       述:  <描述>
 * 修  改 人:  
 * 修改时间:
 * <修改描述:>
 */
package ${view.basePackage}.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
<#list view.extentionTypeNames as typeName>
import ${typeName};
</#list> 

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tx.component.auth.annotation.CheckOperateAuth;
import ${view.basePackage}.model.${view.entitySimpleName};
import ${view.basePackage}.service.${view.entitySimpleName}Service;
import com.tx.core.paged.model.PagedList;

/**
 * ${view.entitySimpleName}显示层逻辑<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-8-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
//FIXME:指定自动生成的权限上级权限,以及对应的权限名称
@CheckOperateAuth(key = "${view.lowerCaseEntitySimpleName}_manage", name = "${view.lowerCaseEntitySimpleName}管理")
@Controller("${view.lowerCaseEntitySimpleName}Controller")
@RequestMapping("/${view.lowerCaseEntitySimpleName}")
public class ${view.entitySimpleName}Controller {
    
    @Resource(name = "${view.lowerCaseEntitySimpleName}Service")
    private ${view.entitySimpleName}Service ${view.lowerCaseEntitySimpleName}Service;
    
    /**
      * 跳转到查询${view.entitySimpleName}列表页面<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toQuery${view.entitySimpleName}List")
    public String toQuery${view.entitySimpleName}List(ModelMap response) {
        return "/${packageName}/query${view.entitySimpleName}List";
    }
    
     /**
      * 跳转到查询${view.entitySimpleName}分页列表页面<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toQuery${view.entitySimpleName}PagedList")
    public String toQuery${view.entitySimpleName}PagedList(ModelMap response) {
        return "/${packageName}/query${view.entitySimpleName}PagedList";
    }
    
    /**
      * 跳转到添加${view.entitySimpleName}页面<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toAdd${view.entitySimpleName}")
    public String toAdd${view.entitySimpleName}(ModelMap response) {
        response.put("${view.lowerCaseEntitySimpleName}", new ${view.entitySimpleName}());
        
        return "/${packageName}/add${view.entitySimpleName}";
    }
    
    /**
     * 跳转到编辑${view.entitySimpleName}页面
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @RequestMapping("/toUpdate${view.entitySimpleName}")
    public String toUpdate${view.entitySimpleName}(
    		@RequestParam("${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName}") String ${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName},
            ModelMap response) {
        ${view.entitySimpleName} res${view.entitySimpleName} = this.${view.lowerCaseEntitySimpleName}Service.findBy${view.upCaseIdPropertyName}(${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName}); 
        response.put("${view.lowerCaseEntitySimpleName}", res${view.entitySimpleName});
        
        return "/${packageName}/update${view.entitySimpleName}";
    }

<#if !ObjectUtils.isEmpty(uniqueGetterNamesArray)>
	<#list uniqueGetterNamesArray as uniqueGetterNames>
    /**
     * 判断${view.entitySimpleName}:
      	<#list uniqueGetterNames as uniqueGetterName>
     *  ${uniqueGetterName}
     	</#list>
     *
     * 是否已经被使用
		<#list uniqueGetterNames as uniqueGetterName>
	 * @param uniqueGetterName
		</#list>
     * @param exclude${view.entitySimpleName}${view.upCaseIdPropertyName}
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/validate<#list uniqueGetterNames as uniqueGetterName>${uniqueGetterName?cap_first}<#if uniqueGetterName_has_next>And</#if></#list>IsExist")
    public Map<String, String> validate<#list uniqueGetterNames as uniqueGetterName>${uniqueGetterName?cap_first}<#if uniqueGetterName_has_next>And</#if></#list>IsExist(
    		@RequestParam MultiValueMap<String, String> request,
		<#list uniqueGetterNames as uniqueGetterName>
            @RequestParam("${uniqueGetterName}") String ${uniqueGetterName},
		</#list>
            @RequestParam(value = "${view.idPropertyName}", required = false) String exclude${view.entitySimpleName}${view.upCaseIdPropertyName}) {
        
        Map<String, String> key2valueMap = new HashMap<String, String>();
		<#list uniqueGetterNames as uniqueGetterName>
        key2valueMap.put("${uniqueGetterName}", ${uniqueGetterName});
		</#list>
        
        boolean flag = this.${view.lowerCaseEntitySimpleName}Service.isExist(key2valueMap, exclude${view.entitySimpleName}${view.upCaseIdPropertyName});
        
        Map<String, String> resMap = new HashMap<String, String>();
        if (!flag) {
        	//FIXME:修改验证重复成功提示信息
            resMap.put("ok", "可用的${view.lowerCaseEntitySimpleName} <#list uniqueGetterNames as uniqueGetterName>${uniqueGetterName}<#if uniqueGetterName_has_next>,</#if></#list>");
        } else {
        	//FIXME:修改验证重复失败提示信息
            resMap.put("error", "已经存在的${view.lowerCaseEntitySimpleName} <#list uniqueGetterNames as uniqueGetterName>${uniqueGetterName}<#if uniqueGetterName_has_next>,</#if></#list>");
        }
        return resMap;
    }
    
	</#list>
</#if>
    /**
     * 查询${view.entitySimpleName}列表<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return List<${view.entitySimpleName}> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @ResponseBody
    @RequestMapping("/queryList")
    public List<${view.entitySimpleName}> queryList(
<#if StringUtils.isNotEmpty(validPropertyName)>
			@RequestParam(value="${validPropertyName}",required=false) Boolean ${validPropertyName},
</#if>
<#list view.queryConditionName2TypeNameMapping?keys as key>
	<#if validPropertyName != key>
		<#if view.queryConditionName2TypeNameMapping[key] != 'String'>
			<#if view.queryConditionName2TypeNameMapping[key] == 'boolean'>
			@RequestParam(value="${key}",required=false) Boolean ${key},
			<#else>
			@RequestParam(value="${key}",required=false) ${view.queryConditionName2TypeNameMapping[key]} ${key},
			</#if>
		</#if>
	</#if>
</#list> 
    		@RequestParam MultiValueMap<String, String> request
    	) {
        Map<String,Object> params = new HashMap<>();

<#list view.queryConditionName2TypeNameMapping?keys as key>
	<#if validPropertyName != key>
		<#if view.queryConditionName2TypeNameMapping[key] != 'String'>
		params.put("${key}",${key});
		<#else>
		params.put("${key}",request.getFirst("${key}"));
		</#if>
	</#if>
</#list> 
    	
        List<${view.entitySimpleName}> resList = this.${view.lowerCaseEntitySimpleName}Service.queryList(
<#if StringUtils.isNotEmpty(validPropertyName)>
			${validPropertyName},
</#if>
			params         
        );
  
        return resList;
    }
    
    /**
     * 查询${view.entitySimpleName}分页列表<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return List<${view.entitySimpleName}> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @ResponseBody
    @RequestMapping("/queryPagedList")
    public PagedList<${view.entitySimpleName}> queryPagedList(
<#if StringUtils.isNotEmpty(validPropertyName)>
			@RequestParam(value="${validPropertyName}",required=false) Boolean ${validPropertyName},
</#if>
<#list view.queryConditionName2TypeNameMapping?keys as key>
	<#if validPropertyName != key>
		<#if view.queryConditionName2TypeNameMapping[key] != 'String'>
			<#if view.queryConditionName2TypeNameMapping[key] == 'boolean'>
			@RequestParam(value="${key}",required=false) Boolean ${key},
			<#else>
			@RequestParam(value="${key}",required=false) ${view.queryConditionName2TypeNameMapping[key]} ${key},
			</#if>
		</#if>
	</#if>
</#list> 
    		@RequestParam MultiValueMap<String, String> request,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize
    	) {
		Map<String,Object> params = new HashMap<>();

<#list view.queryConditionName2TypeNameMapping?keys as key>
	<#if validPropertyName != key>
		<#if view.queryConditionName2TypeNameMapping[key] != 'String'>
		params.put("${key}",${key});
		<#else>
		params.put("${key}",request.getFirst("${key}"));
		</#if>
	</#if>
</#list> 

        PagedList<${view.entitySimpleName}> resPagedList = this.${view.lowerCaseEntitySimpleName}Service.queryPagedList(
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
     * 添加组织结构页面
     *<功能详细描述>
     * @param organization [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    //FIXME:修改删增加权限名称
    @CheckOperateAuth(key = "add_${view.lowerCaseEntitySimpleName}", name = "增加${view.entitySimpleName}")
    @RequestMapping("/add")
    @ResponseBody
    public boolean add(${view.entitySimpleName} ${view.lowerCaseEntitySimpleName}) {
        this.${view.lowerCaseEntitySimpleName}Service.insert(${view.lowerCaseEntitySimpleName});
        return true;
    }
    
    /**
      * 更新组织<br/>
      *<功能详细描述>
      * @param ${view.lowerCaseEntitySimpleName}
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //FIXME:修改删编辑权限名称
    @CheckOperateAuth(key = "update_${view.lowerCaseEntitySimpleName}", name = "编辑${view.entitySimpleName}")
    @RequestMapping("/update")
    @ResponseBody
    public boolean update(${view.entitySimpleName} ${view.lowerCaseEntitySimpleName}) {
        this.${view.lowerCaseEntitySimpleName}Service.updateBy${view.upCaseIdPropertyName}(${view.lowerCaseEntitySimpleName});
        
        return true;
    }
    
    /**
      * 删除指定${view.entitySimpleName}<br/> 
      *<功能详细描述>
      * @param ${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName}
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //FIXME:修改删除权限名称
    @CheckOperateAuth(key = "delete_${view.lowerCaseEntitySimpleName}", name = "删除${view.entitySimpleName}")
    @ResponseBody
    @RequestMapping("/deleteBy${view.upCaseIdPropertyName}")
    public boolean deleteBy${view.upCaseIdPropertyName}(@RequestParam(value = "${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName}") String ${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName}) {
        boolean resFlag = this.${view.lowerCaseEntitySimpleName}Service.deleteBy${view.upCaseIdPropertyName}(${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName});
        return resFlag;
    }
    
<#if StringUtils.isNotEmpty(validPropertyName)>
    /**
      * 禁用${view.entitySimpleName}
      * @param ${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName}
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //FIXME:修改禁用权限名称
    @CheckOperateAuth(key = "disable_${view.lowerCaseEntitySimpleName}", name = "禁用${view.entitySimpleName}")
    @ResponseBody
    @RequestMapping("/disableBy${view.upCaseIdPropertyName}")
    public boolean disableBy${view.upCaseIdPropertyName}(@RequestParam(value = "${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName}") String ${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName}) {
        boolean resFlag = this.${view.lowerCaseEntitySimpleName}Service.disableBy${view.upCaseIdPropertyName}(${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName});
        return resFlag;
    }
    
    /**
      * 启用${view.entitySimpleName}<br/>
      *<功能详细描述>
      * @param ${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName}
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //FIXME:修改启用权限名称
    @CheckOperateAuth(key = "enable_${view.lowerCaseEntitySimpleName}", name = "启用${view.entitySimpleName}")
    @ResponseBody
    @RequestMapping("/enableBy${view.upCaseIdPropertyName}")
    public boolean enableBy${view.upCaseIdPropertyName}(@RequestParam(value = "${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName}") String ${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName}) {
        boolean resFlag = this.${view.lowerCaseEntitySimpleName}Service.enableBy${view.upCaseIdPropertyName}(${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName});
        return resFlag;
    }
</#if>
}