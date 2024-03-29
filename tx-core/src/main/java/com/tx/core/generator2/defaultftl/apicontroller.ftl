/*
 * 描       述:  <描述>
 * 修  改 人:  
 * 修改时间:
 * <修改描述:>
 */
package ${controller.basePackage}.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;
import ${controller.basePackage}.facade.${controller.entityTypeSimpleName}Facade;
import ${controller.basePackage}.model.${controller.entityTypeSimpleName};
import ${controller.basePackage}.service.${controller.entityTypeSimpleName}Service;

import io.swagger.annotations.Api;

/**
 * ${controller.entityComment}API控制层[${controller.entityTypeSimpleName}APIController]<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RestController
@Api(tags = "${controller.entityComment}API")
@RequestMapping("/api/${controller.entityTypeSimpleName?uncap_first}")
public class ${controller.entityTypeSimpleName}APIController implements ${controller.entityTypeSimpleName}Facade {
    
    //${controller.entityComment}业务层
    @Resource(name = "${controller.entityTypeSimpleName?uncap_first}Service")
    private ${controller.entityTypeSimpleName}Service ${controller.entityTypeSimpleName?uncap_first}Service;
    
    /**
     * 新增${controller.entityComment}<br/>
     * <功能详细描述>
     * @param ${controller.entityTypeSimpleName?uncap_first} [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public ${controller.entityTypeSimpleName} insert(@RequestBody ${controller.entityTypeSimpleName} ${controller.entityTypeSimpleName?uncap_first}) {
        this.${controller.entityTypeSimpleName?uncap_first}Service.insert(${controller.entityTypeSimpleName?uncap_first});
        return ${controller.entityTypeSimpleName?uncap_first};
    }
    
    /**
     * 根据${controller.pkProperty.propertyName}删除${controller.entityComment}<br/> 
     * <功能详细描述>
     * @param ${controller.pkProperty.propertyName}
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean deleteBy${controller.pkProperty.propertyName?cap_first}(
    		@PathVariable(value = "${controller.pkProperty.propertyName}",required=true) ${controller.pkProperty.propertyType.getSimpleName()} ${controller.pkProperty.propertyName}) {
        boolean flag = this.${controller.entityTypeSimpleName?uncap_first}Service.deleteBy${controller.pkProperty.propertyName?cap_first}(${controller.pkProperty.propertyName});
        return flag;
    }
<#if controller.codeProperty??>
	
	/**
     * 根据${controller.codeProperty.propertyName}删除${controller.entityComment}<br/> 
     * <功能详细描述>
     * @param ${controller.codeProperty.propertyName}
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean deleteBy${controller.codeProperty.propertyName?cap_first}(
    		@PathVariable(value = "${controller.codeProperty.propertyName}",required=true) ${controller.codeProperty.propertyType.getSimpleName()} ${controller.codeProperty.propertyName}){
        boolean flag = this.${controller.entityTypeSimpleName?uncap_first}Service.deleteBy${controller.codeProperty.propertyName?cap_first}(${controller.codeProperty.propertyName});
        return flag;    
    }
</#if>
    
    /**
     * 更新${controller.entityComment}<br/>
     * <功能详细描述>
     * @param ${controller.entityTypeSimpleName?uncap_first}
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean updateBy${controller.pkProperty.propertyName?cap_first}(@PathVariable(value = "${controller.pkProperty.propertyName}",required=true) ${controller.pkProperty.propertyType.getSimpleName()} ${controller.pkProperty.propertyName},
    		@RequestBody ${controller.entityTypeSimpleName} ${controller.entityTypeSimpleName?uncap_first}) {
        boolean flag = this.${controller.entityTypeSimpleName?uncap_first}Service.updateBy${controller.pkProperty.propertyName?cap_first}(${controller.pkProperty.propertyName},${controller.entityTypeSimpleName?uncap_first});
        return flag;
    }
    
<#if controller.validProperty??>
    /**
     * 禁用${controller.entityComment}<br/>
     * @param ${controller.pkProperty.propertyName}
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
	@Override
    public boolean disableBy${controller.pkProperty.propertyName?cap_first}(
    		@PathVariable(value = "${controller.pkProperty.propertyName}", required = true) ${controller.pkProperty.propertyType.getSimpleName()} ${controller.pkProperty.propertyName}) {
        boolean flag = this.${controller.entityTypeSimpleName?uncap_first}Service.disableBy${controller.pkProperty.propertyName?cap_first}(${controller.pkProperty.propertyName});
        return flag;
    }
    
    /**
     * 启用${controller.entityComment}<br/>
     * <功能详细描述>
     * @param ${controller.pkProperty.propertyName}
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean enableBy${controller.pkProperty.propertyName?cap_first}(
    		@PathVariable(value = "${controller.pkProperty.propertyName}", required = true) ${controller.pkProperty.propertyType.getSimpleName()} ${controller.pkProperty.propertyName}) {
        boolean flag = this.${controller.entityTypeSimpleName?uncap_first}Service.enableBy${controller.pkProperty.propertyName?cap_first}(${controller.pkProperty.propertyName});
        return flag;
    }
</#if>

    /**
     * 根据主键查询${controller.entityComment}<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return ${controller.entityTypeSimpleName} [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public ${controller.entityTypeSimpleName} findBy${controller.pkProperty.propertyName?cap_first}(
            @PathVariable(value = "${controller.pkProperty.propertyName}", required = true) ${controller.pkProperty.propertyType.getSimpleName()} ${controller.pkProperty.propertyName}) {
        ${controller.entityTypeSimpleName} res = this.${controller.entityTypeSimpleName?uncap_first}Service.findBy${controller.pkProperty.propertyName?cap_first}(${controller.pkProperty.propertyName});
        
        return res;
    }
<#if controller.codeProperty??>

    /**
     * 根据编码查询${controller.entityComment}<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return ${controller.entityTypeSimpleName} [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public ${controller.entityTypeSimpleName} findBy${controller.codeProperty.propertyName?cap_first}(
            @PathVariable(value = "code", required = true) ${controller.codeProperty.propertyType.getSimpleName()} ${controller.codeProperty.propertyName}) {
        ${controller.entityTypeSimpleName} res = this.${controller.entityTypeSimpleName?uncap_first}Service.findBy${controller.codeProperty.propertyName?cap_first}(${controller.codeProperty.propertyName});
        
        return res;
    }
</#if>

    /**
     * 查询${controller.entityComment}实例列表<br/>
     * <功能详细描述>
     <#if controller.validProperty??>
     * @param valid
     </#if>
     * @param querier
     * @return [参数说明]
     * 
     * @return List<${controller.entityTypeSimpleName}> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<${controller.entityTypeSimpleName}> queryList(
<#if controller.validProperty??>
			@RequestParam(value = "${controller.validProperty.propertyName}", required = false) Boolean ${controller.validProperty.propertyName},
</#if>
    		@RequestBody Querier querier
    	) {
        List<${controller.entityTypeSimpleName}> resList = this.${controller.entityTypeSimpleName?uncap_first}Service.queryList(
<#if controller.validProperty??>
			${controller.validProperty.propertyName},
</#if>
			querier         
        );
  
        return resList;
    }
    
    /**
     * 查询${controller.entityComment}分页列表<br/>
     * <功能详细描述>
     <#if controller.validProperty??>
     * @param valid
     </#if>
     * @param pageIndex
     * @param pageSize
     * @param querier
     * @return [参数说明]
     * 
     * @return PagedList<${controller.entityTypeSimpleName}> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public PagedList<${controller.entityTypeSimpleName}> queryPagedList(
<#if controller.validProperty??>
			@RequestParam(value = "${controller.validProperty.propertyName}", required = false) Boolean ${controller.validProperty.propertyName},
</#if>
			@RequestBody Querier querier,
			@PathVariable(value = "pageNumber", required = true) int pageIndex,
            @PathVariable(value = "pageSize", required = true) int pageSize
    	) {
        PagedList<${controller.entityTypeSimpleName}> resPagedList = this.${controller.entityTypeSimpleName?uncap_first}Service.queryPagedList(
<#if controller.validProperty??>
			${controller.validProperty.propertyName},
</#if>
			querier,
			pageIndex,
			pageSize
        );
        return resPagedList;
    }
    
	/**
     * 查询${controller.entityComment}数量<br/>
     * <功能详细描述>
     <#if controller.validProperty??>
     * @param valid
     </#if>
     * @param querier
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public int count(
<#if controller.validProperty??>
			@RequestParam(value = "${controller.validProperty.propertyName}", required = false) Boolean ${controller.validProperty.propertyName},
</#if>
            @RequestBody Querier querier) {
        int count = this.${controller.entityTypeSimpleName?uncap_first}Service.count(
<#if controller.validProperty??>
			${controller.validProperty.propertyName},
</#if>
        	querier);
        
        return count;
    }

	/**
     * 查询${controller.entityComment}是否存在<br/>
	 * @param exclude${controller.pkProperty.propertyName?cap_first}
     * @param querier
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean exists(@RequestBody Querier querier,
            @RequestParam(value = "exclude${controller.pkProperty.propertyName?cap_first}", required = false) ${controller.pkProperty.propertyType.getSimpleName()} exclude${controller.pkProperty.propertyName?cap_first}) {
        boolean flag = this.${controller.entityTypeSimpleName?uncap_first}Service.exists(querier, exclude${controller.pkProperty.propertyName?cap_first});
        
        return flag;
    }
<#if controller.parentIdProperty??>

	/**
     * 根据条件查询基础数据分页列表<br/>
     * <功能详细描述>
     * @param parentId
     <#if controller.validProperty??>
     * @param valid
     </#if>
     * @param querier
     * @return [参数说明]
     * 
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<${controller.entityTypeSimpleName}> queryChildrenBy${controller.parentIdProperty.propertyName?cap_first}(@PathVariable(value = "${controller.parentIdProperty.propertyName}", required = true) ${controller.parentIdProperty.propertyType.getSimpleName()} ${controller.parentIdProperty.propertyName},
	<#if controller.validProperty??>
			@RequestParam(value = "${controller.validProperty.propertyName}", required = false) Boolean ${controller.validProperty.propertyName},
	</#if>
            Querier querier){
        List<${controller.entityTypeSimpleName}> resList = this.${controller.entityTypeSimpleName?uncap_first}Service.queryChildrenBy${controller.parentIdProperty.propertyName?cap_first}(${controller.parentIdProperty.propertyName},
	<#if controller.validProperty??>
			${controller.validProperty.propertyName},
	</#if>
			querier         
        );
  
        return resList;
    }

	/**
     * 根据条件查询基础数据分页列表<br/>
     * <功能详细描述>
     * @param parentId
     <#if controller.validProperty??>
     * @param valid
     </#if>
     * @param querier
     * @return [参数说明]
     * 
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<${controller.entityTypeSimpleName}> queryDescendantsBy${controller.parentIdProperty.propertyName?cap_first}(@PathVariable(value = "${controller.parentIdProperty.propertyName}", required = true) ${controller.parentIdProperty.propertyType.getSimpleName()} ${controller.parentIdProperty.propertyName},
	<#if controller.validProperty??>
			@RequestParam(value = "${controller.validProperty.propertyName}", required = false) Boolean ${controller.validProperty.propertyName},
	</#if>
            Querier querier){
        List<${controller.entityTypeSimpleName}> resList = this.${controller.entityTypeSimpleName?uncap_first}Service.queryDescendantsBy${controller.parentIdProperty.propertyName?cap_first}(${controller.parentIdProperty.propertyName},
	<#if controller.validProperty??>
			${controller.validProperty.propertyName},
	</#if>
			querier         
        );
  
        return resList;
    }
</#if>
}