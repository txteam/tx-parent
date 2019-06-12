/*
 * 描       述:  <描述>
 * 修  改 人:  
 * 修改时间:
 * <修改描述:>
 */
package ${controller.basePackage}.facade;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;
import ${controller.basePackage}.model.${controller.entityTypeSimpleName};

import io.swagger.annotations.ApiOperation;

/**
 * ${controller.entityComment}接口门面层[${controller.entityTypeSimpleName}Facade]<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ${controller.entityTypeSimpleName}Facade {
    
    /**
     * 新增${controller.entityComment}<br/>
     * <功能详细描述>
     * @param ${controller.entityTypeSimpleName?uncap_first} [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "新增${controller.entityComment}")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ${controller.entityTypeSimpleName} insert(@RequestBody ${controller.entityTypeSimpleName} ${controller.entityTypeSimpleName?uncap_first});
    
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
    @ApiOperation(value = "根据${controller.pkProperty.propertyComment}删除${controller.entityComment}")
    @RequestMapping(value = "/{${controller.pkProperty.propertyName}}", method = RequestMethod.DELETE) 
    public boolean deleteBy${controller.pkProperty.propertyName?cap_first}(
    		@PathVariable(value = "${controller.pkProperty.propertyName}",required=true) ${controller.pkProperty.propertyType.getSimpleName()} ${controller.pkProperty.propertyName});
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
    @ApiOperation(value = "根据${controller.codeProperty.propertyComment}删除${controller.entityComment}")
    @RequestMapping(value = "/{${controller.codeProperty.propertyName}}", method = RequestMethod.DELETE) 
    public boolean deleteBy${controller.codeProperty.propertyName?cap_first}(
    		@PathVariable(value = "${controller.codeProperty.propertyName}",required=true) ${controller.codeProperty.propertyType.getSimpleName()} ${controller.codeProperty.propertyName});
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
    @ApiOperation(value = "修改${controller.entityComment}")
    @RequestMapping(value = "/{${controller.pkProperty.propertyName}}", method = RequestMethod.PUT)
    public boolean updateBy${controller.pkProperty.propertyName?cap_first}(@PathVariable(value = "${controller.pkProperty.propertyName}",required=true) ${controller.pkProperty.propertyType.getSimpleName()} ${controller.pkProperty.propertyName},
    		@RequestBody ${controller.entityTypeSimpleName} ${controller.entityTypeSimpleName?uncap_first});
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
	@ApiOperation(value = "禁用${controller.entityComment}")
    @RequestMapping(value = "/disable/{${controller.pkProperty.propertyName}}", method = RequestMethod.PATCH)
    public boolean disableBy${controller.pkProperty.propertyName?cap_first}(
    		@PathVariable(value = "${controller.pkProperty.propertyName}", required = true) ${controller.pkProperty.propertyType.getSimpleName()} ${controller.pkProperty.propertyName});
    
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
    @ApiOperation(value = "启用${controller.entityComment}")
    @RequestMapping(value = "/enable/{${controller.pkProperty.propertyName}}", method = RequestMethod.PATCH)
    public boolean enableBy${controller.pkProperty.propertyName?cap_first}(
    		@PathVariable(value = "${controller.pkProperty.propertyName}", required = true) ${controller.pkProperty.propertyType.getSimpleName()} ${controller.pkProperty.propertyName});
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
    @ApiOperation(value = "根据主键查询${controller.entityComment}")
    @RequestMapping(value = "/{${controller.pkProperty.propertyName}}", method = RequestMethod.GET)
    public ${controller.entityTypeSimpleName} findBy${controller.pkProperty.propertyName?cap_first}(
            @PathVariable(value = "${controller.pkProperty.propertyName}", required = true) ${controller.pkProperty.propertyType.getSimpleName()} ${controller.pkProperty.propertyName});
    
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
    @ApiOperation(value = "根据编码查询${controller.entityComment}")
    @RequestMapping(value = "/code/{code}", method = RequestMethod.GET)
    public ${controller.entityTypeSimpleName} findBy${controller.codeProperty.propertyName?cap_first}(
            @PathVariable(value = "code", required = true) ${controller.codeProperty.propertyType.getSimpleName()} ${controller.codeProperty.propertyName});
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
    @ApiOperation(value = "查询${controller.entityComment}列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<${controller.entityTypeSimpleName}> queryList(
	<#if controller.validProperty??>
			@RequestParam(value = "${controller.validProperty.propertyName}", required = false) Boolean ${controller.validProperty.propertyName},
	</#if>
    		@RequestBody Querier querier
    	);
    
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
    @ApiOperation(value = "查询${controller.entityComment}分页列表")
    @RequestMapping(value = "/pagedlist/{pageSize}/{pageNumber}", method = RequestMethod.GET)
    public PagedList<${controller.entityTypeSimpleName}> queryPagedList(
	<#if controller.validProperty??>
			@RequestParam(value = "${controller.validProperty.propertyName}", required = false) Boolean ${controller.validProperty.propertyName},
	</#if>
			@RequestBody Querier querier,
			@PathVariable(value = "pageNumber", required = true) int pageIndex,
            @PathVariable(value = "pageSize", required = true) int pageSize
    	);
    
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
    @ApiOperation(value = "查询${controller.entityComment}数量")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public int count(
	<#if controller.validProperty??>
			@RequestParam(value = "${controller.validProperty.propertyName}", required = false) Boolean ${controller.validProperty.propertyName},
	</#if>
            @RequestBody Querier querier);

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
    @ApiOperation(value = "查询${controller.entityComment}是否存在")
    @RequestMapping(value = "/exists", method = RequestMethod.GET)
    public boolean exists(
    		@RequestBody Querier querier,
            @RequestParam(value = "exclude${controller.pkProperty.propertyName?cap_first}", required = false) ${controller.pkProperty.propertyType.getSimpleName()} exclude${controller.pkProperty.propertyName?cap_first}
            );
<#if controller.parentIdProperty??>

	/**
     * 根据条件查询查询${controller.entityComment}子代列表<br/>
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
    @ApiOperation(value = "根据条件查询查询${controller.entityComment}子代列表")
    @RequestMapping(value = "/children/{${controller.parentIdProperty.propertyName}}", method = RequestMethod.GET)
    public List<${controller.entityTypeSimpleName}> queryChildrenBy${controller.parentIdProperty.propertyName?cap_first}(@PathVariable(value = "${controller.parentIdProperty.propertyName}", required = true) ${controller.parentIdProperty.propertyType.getSimpleName()} ${controller.parentIdProperty.propertyName},
	<#if controller.validProperty??>
			@RequestParam(value = "${controller.validProperty.propertyName}", required = false) Boolean ${controller.validProperty.propertyName},
	</#if>
            @RequestBody Querier querier);

	/**
     * 根据条件查询查询${controller.entityComment}后代列表<br/>
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
    @ApiOperation(value = "根据条件查询查询${controller.entityComment}后代列表")
    @RequestMapping(value = "/descendants/{${controller.parentIdProperty.propertyName}}", method = RequestMethod.GET)
    public List<${controller.entityTypeSimpleName}> queryDescendantsBy${controller.parentIdProperty.propertyName?cap_first}(@PathVariable(value = "${controller.parentIdProperty.propertyName}", required = true) ${controller.parentIdProperty.propertyType.getSimpleName()} ${controller.parentIdProperty.propertyName},
	<#if controller.validProperty??>
			@RequestParam(value = "${controller.validProperty.propertyName}", required = false) Boolean ${controller.validProperty.propertyName},
	</#if>
            @RequestBody Querier querier);
</#if>
}