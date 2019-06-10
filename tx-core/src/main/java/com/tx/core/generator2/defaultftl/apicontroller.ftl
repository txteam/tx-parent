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

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tx.core.paged.model.PagedList;
import ${controller.basePackage}.model.${controller.entityTypeSimpleName};
import ${controller.basePackage}.service.${controller.entityTypeSimpleName}Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * ${controller.entityComment}API控制层<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RestController
@Api(tags = "${controller.entityComment}API")
@RequestMapping("/api/${controller.entityTypeSimpleName}?uncap_first")
public class ${controller.entityTypeSimpleName}APIController {
    
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
    @ApiOperation(value = "新增${controller.entityComment}")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public boolean insert(@RequestBody ${controller.entityTypeSimpleName} ${controller.entityTypeSimpleName?uncap_first}) {
        this.${controller.entityTypeSimpleName?uncap_first}Service.insert(${controller.entityTypeSimpleName?uncap_first});
        return true;
    }
    
    /**
     * 删除${controller.entityComment}<br/> 
     * <功能详细描述>
     * @param ${controller.pkProperty.propertyName}
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "删除${controller.entityComment}")
    @RequestMapping(value = "/{${controller.pkProperty.propertyName}}", method = RequestMethod.DELETE) 
    public boolean deleteBy${controller.pkProperty.propertyName?cap_first}(
    	@PathVariable(value = "${controller.pkProperty.propertyName}",required=true) String ${controller.pkProperty.propertyName}) {
        boolean flag = this.${controller.entityTypeSimpleName?uncap_first}Service.deleteBy${controller.pkProperty.propertyName?cap_first}(${controller.pkProperty.propertyName});
        return flag;
    }
    
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
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public boolean update(@RequestBody ${controller.entityTypeSimpleName} ${controller.entityTypeSimpleName?uncap_first}) {
        boolean flag = this.${controller.entityTypeSimpleName?uncap_first}Service.updateBy${controller.pkProperty.propertyName?cap_first}(${controller.entityTypeSimpleName?uncap_first});
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
	@ApiOperation(value = "禁用${controller.entityComment}")
    @RequestMapping(value = "/disable/{${controller.pkProperty.propertyName}}", method = RequestMethod.PATCH)
    public boolean disableBy${controller.pkProperty.propertyName?cap_first}(
    		@PathVariable(value = "${controller.pkProperty.propertyName}", required = true) String ${controller.pkProperty.propertyName}) {
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
    @ApiOperation(value = "启用${controller.entityComment}")
    @RequestMapping(value = "/enable/{${controller.pkProperty.propertyName}}", method = RequestMethod.PATCH)
    public boolean enableBy${controller.pkProperty.propertyName?cap_first}(
    		@PathVariable(value = "${controller.pkProperty.propertyName}", required = true) String ${controller.pkProperty.propertyName}) {
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
    @ApiOperation(value = "根据主键查询${controller.entityComment}")
    @RequestMapping(value = "/{${controller.pkProperty.propertyName}}", method = RequestMethod.GET)
    public ${controller.entityTypeSimpleName} findById(
            @PathVariable(value = "${controller.pkProperty.propertyName}", required = true) String ${controller.pkProperty.propertyName}) {
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
    @ApiOperation(value = "根据编码查询${controller.entityComment}")
    @RequestMapping(value = "/code/{code}", method = RequestMethod.GET)
    public ${controller.entityTypeSimpleName} findByCode(
            @PathVariable(value = "code", required = true) String code) {
        ${controller.entityTypeSimpleName} res = this.${controller.entityTypeSimpleName?uncap_first}Service.findByCode(code);
        
        return res;
    }
</#if>

	/**
     * 查询${controller.entityComment}实例列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<${controller.entityTypeSimpleName}> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "查询${controller.entityComment}列表")
    @RequestMapping(value = "/list/{valid}", method = RequestMethod.GET)
    public List<${controller.entityTypeSimpleName}> queryList(
<#if controller.validProperty??>
			@PathVariable(value = "${controller.validProperty.propertyName}", required = false) Boolean ${controller.validProperty.propertyName},
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
     * 查询${controller.entityComment}分页列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<${controller.entityTypeSimpleName}> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "查询${controller.entityComment}分页列表")
    @RequestMapping(value = "/pagedlist/{pageSize}/{pageNumber}/{valid}", method = RequestMethod.GET)
    public PagedList<${controller.entityTypeSimpleName}> queryPagedList(
<#if controller.validProperty??>
			@PathVariable(value = "${controller.validProperty.propertyName}", required = false) Boolean ${controller.validProperty.propertyName},
</#if>
			@PathVariable(value = "pageNumber", required = true) int pageIndex,
            @PathVariable(value = "pageSize", required = true) int pageSize,
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
     * 查询${controller.entityComment}是否存在<br/>
	 * @param exclude${controller.pkProperty.propertyName?cap_first}
     * @param params
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "查询${controller.entityComment}是否存在")
    @RequestMapping(value = "/exists/{exclude${controller.pkProperty.propertyName?cap_first}}", method = RequestMethod.GET)
    public boolean exists(
            @PathVariable(value = "exclude${controller.pkProperty.propertyName?cap_first}", required = false) String exclude${controller.pkProperty.propertyName?cap_first},
            @RequestParam Map<String, String> params) {
        boolean flag = this.${controller.entityTypeSimpleName?uncap_first}Service.exists(params, exclude${controller.pkProperty.propertyName?cap_first});
        
        return flag;
    }
}