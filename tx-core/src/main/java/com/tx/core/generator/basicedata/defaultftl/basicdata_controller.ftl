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
    public String toQuery${view.entitySimpleName}List() {
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
    public String toQuery${view.entitySimpleName}PagedList() {
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
            ModelMap modelMap) {
        ${view.entitySimpleName} res${view.entitySimpleName} = this.${view.lowerCaseEntitySimpleName}Service.find${view.entitySimpleName}By${view.upCaseIdPropertyName}(${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName}); 
        modelMap.put("${view.lowerCaseEntitySimpleName}", res${view.entitySimpleName});
        
        return "/${packageName}/update${view.entitySimpleName}";
    }

<#if !ObjectUtils.isEmpty(uniqueGetterNamesArray)>
<#list uniqueGetterNamesArray as uniqueGetterNames>

    /**
      * 判断${view.entitySimpleName}:<#list uniqueGetterNames as uniqueGetterName>${uniqueGetterName}<#if uniqueGetterName_has_next>,</#if></#list>是否已经被使用
      * <功能详细描述>
<#list uniqueGetterNames as uniqueGetterName>
	  * @param uniqueGetterName
</#list>
      * @param code
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
    
<#if !StringUtils.isEmpty(validPropertyName)>

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
    @RequestMapping("/query${view.entitySimpleName}ListIncludeInvalid")
    public List<${view.entitySimpleName}> query${view.entitySimpleName}ListIncludeInvalid(
    		@RequestParam MultiValueMap<String, String> request,
<#list view.queryConditionName2TypeNameMapping?keys as key>
<#if validPropertyName != key>
			@RequestParam(value="${key}",required=false) ${view.queryConditionName2TypeNameMapping[key]} ${key}<#if key_has_next>,</#if>
</#if>
</#list>  
    	) {
        List<${view.entitySimpleName}> resList = this.${view.lowerCaseEntitySimpleName}Service.query${view.entitySimpleName}ListIncludeInvalid(
<#list view.queryConditionName2TypeNameMapping?keys as key>
<#if validPropertyName != key>
			${key}<#if key_has_next>,</#if>
</#if>
</#list>          
        );
        return resList;
    }
    
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
    @RequestMapping("/query${view.entitySimpleName}ListIncludeAppoint")
    public List<${view.entitySimpleName}> query${view.entitySimpleName}ListIncludeAppoint${view.upCaseIdPropertyName}(
    		@RequestParam MultiValueMap<String, String> request,
<#list view.queryConditionName2TypeNameMapping?keys as key>
<#if validPropertyName != key>
			@RequestParam(value="${key}",required=false) ${view.queryConditionName2TypeNameMapping[key]} ${key},
</#if>
</#list>
			@RequestParam(value="appoint${view.upCaseIdPropertyName}") String appoint${view.upCaseIdPropertyName}
    	) {
        List<${view.entitySimpleName}> resList = this.${view.lowerCaseEntitySimpleName}Service.query${view.entitySimpleName}ListIncludeAppoint${view.upCaseIdPropertyName}(
<#list view.queryConditionName2TypeNameMapping?keys as key>
<#if validPropertyName != key>
			${key},
</#if>
</#list>
			appoint${view.upCaseIdPropertyName}       
        );
        return resList;
    }

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
    @RequestMapping("/query${view.entitySimpleName}List")
    public List<${view.entitySimpleName}> query${view.entitySimpleName}List(
    		@RequestParam MultiValueMap<String, String> request,
<#list view.queryConditionName2TypeNameMapping?keys as key>
<#if validPropertyName != key>
			@RequestParam(value="${key}",required=false) ${view.queryConditionName2TypeNameMapping[key]} ${key}<#if key_has_next>,</#if>
</#if>
</#list>  
    	) {
        List<${view.entitySimpleName}> resList = this.${view.lowerCaseEntitySimpleName}Service.query${view.entitySimpleName}List(
<#list view.queryConditionName2TypeNameMapping?keys as key>
<#if validPropertyName != key>
			${key}<#if key_has_next>,</#if>
</#if>
</#list>          
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
    @RequestMapping("/query${view.entitySimpleName}PagedList")
    public PagedList<${view.entitySimpleName}> query${view.entitySimpleName}PagedList(
    		@RequestParam MultiValueMap<String, String> request,
<#list view.queryConditionName2TypeNameMapping?keys as key>
<#if validPropertyName != key>
			@RequestParam(value="${key}",required=false) ${view.queryConditionName2TypeNameMapping[key]} ${key},
</#if>
</#list>  
			@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize
    	) {
        PagedList<${view.entitySimpleName}> resPagedList = this.${view.lowerCaseEntitySimpleName}Service.query${view.entitySimpleName}PagedList(
<#list view.queryConditionName2TypeNameMapping?keys as key>
<#if validPropertyName != key>
			${key},
</#if>
</#list>          
			pageIndex,
			pageSize
        );
        return resPagedList;
    }
    
<#if !StringUtils.isEmpty(validPropertyName)>
    /**
     * 查询${view.entitySimpleName}分页列表(包含无效的实体)<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return PagedList<${view.entitySimpleName}> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @ResponseBody
    @RequestMapping("/query${view.entitySimpleName}PagedListIncludeInvalid")
    public PagedList<${view.entitySimpleName}> query${view.entitySimpleName}PagedListIncludeInvalid(
    		@RequestParam MultiValueMap<String, String> request,
<#list view.queryConditionName2TypeNameMapping?keys as key>
<#if validPropertyName != key>
			@RequestParam(value="${key}",required=false) ${view.queryConditionName2TypeNameMapping[key]} ${key},
</#if>
</#list>  
			@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize
    	) {
        PagedList<${view.entitySimpleName}> resPagedList = this.${view.lowerCaseEntitySimpleName}Service.query${view.entitySimpleName}PagedListIncludeInvalid(
<#list view.queryConditionName2TypeNameMapping?keys as key>
<#if validPropertyName != key>
			${key},
</#if>
</#list>          
			pageIndex,
			pageSize
        );
        return resPagedList;
    }
</#if>
    
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
    @RequestMapping("/add${view.entitySimpleName}")
    @ResponseBody
    public boolean add${view.entitySimpleName}(${view.entitySimpleName} ${view.lowerCaseEntitySimpleName}) {
        this.${view.lowerCaseEntitySimpleName}Service.insert${view.entitySimpleName}(${view.lowerCaseEntitySimpleName});
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
    @RequestMapping("/update${view.entitySimpleName}")
    @ResponseBody
    public boolean update${view.entitySimpleName}(${view.entitySimpleName} ${view.lowerCaseEntitySimpleName}) {
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
    @RequestMapping("/delete${view.entitySimpleName}By${view.upCaseIdPropertyName}")
    public boolean delete${view.entitySimpleName}By${view.upCaseIdPropertyName}(@RequestParam(value = "${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName}") String ${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName}) {
        boolean resFlag = this.${view.lowerCaseEntitySimpleName}Service.deleteBy${view.upCaseIdPropertyName}(${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName});
        return resFlag;
    }
    
<#if !StringUtils.isEmpty(validPropertyName)>
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
    @RequestMapping("/disable${view.entitySimpleName}By${view.upCaseIdPropertyName}")
    public boolean disable${view.entitySimpleName}By${view.upCaseIdPropertyName}(@RequestParam(value = "${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName}") String ${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName}) {
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
    @RequestMapping("/enable${view.entitySimpleName}By${view.upCaseIdPropertyName}")
    public boolean enable${view.entitySimpleName}By${view.upCaseIdPropertyName}(@RequestParam(value = "${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName}") String ${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName}) {
        boolean resFlag = this.${view.lowerCaseEntitySimpleName}Service.enableBy${view.upCaseIdPropertyName}(${view.lowerCaseEntitySimpleName}${view.upCaseIdPropertyName});
        return resFlag;
    }
    
</#if>
}
