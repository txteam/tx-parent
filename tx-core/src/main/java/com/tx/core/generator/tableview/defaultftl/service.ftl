/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package ${service.basePackage}.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
<#list service.extentionTypeNames as typeName>
import ${typeName};
</#list> 

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ${service.basePackage}.dao.${service.entitySimpleName}Dao;
import ${service.basePackage}.model.${service.entitySimpleName};
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * ${service.entitySimpleName}的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("${service.lowerCaseEntitySimpleName}Service")
public class ${service.entitySimpleName}Service {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(${service.entitySimpleName}Service.class);
    
    @Resource(name = "${service.lowerCaseEntitySimpleName}Dao")
    private ${service.entitySimpleName}Dao ${service.lowerCaseEntitySimpleName}Dao;
    
    /**
     * 根据${service.upCaseIdPropertyName}查询${service.entitySimpleName}实体
     * 1、当${service.idPropertyName}为empty时抛出异常
     * <功能详细描述>
     * @param ${service.idPropertyName}
     * @return [参数说明]
     * 
     * @return ${service.entitySimpleName} [返回类型说明]
     * @exception throws 可能存在数据库访问异常DataAccessException
     * @see [类、类#方法、类#成员]
     */
    public ${service.entitySimpleName} find${service.entitySimpleName}By${service.upCaseIdPropertyName}(String ${service.idPropertyName}) {
        AssertUtils.notEmpty(${service.idPropertyName}, "${service.idPropertyName} is empty.");
        
        ${service.entitySimpleName} condition = new ${service.entitySimpleName}();
        condition.set${service.upCaseIdPropertyName}(${service.idPropertyName});
        
        ${service.entitySimpleName} res = this.${service.lowerCaseEntitySimpleName}Dao.find${service.entitySimpleName}(condition);
        return res;
    }
    
    /**
     * 查询${service.entitySimpleName}实体列表
     * <功能详细描述>
<#list service.queryConditionName2TypeNameMapping?keys as key>
     * @param ${key}
</#list> 
     *       
     * @return [参数说明]
     * 
     * @return List<${service.entitySimpleName}> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<${service.entitySimpleName}> query${service.entitySimpleName}List(
<#list service.queryConditionName2TypeNameMapping?keys as key>
		${service.queryConditionName2TypeNameMapping[key]} ${key}<#if key_has_next>,</#if>
</#list>    
    	) {
        //判断条件合法性
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
<#list service.queryConditionName2TypeNameMapping?keys as key>
		params.put("${key}",${key});
</#list>
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<${service.entitySimpleName}> resList = this.${service.lowerCaseEntitySimpleName}Dao.query${service.entitySimpleName}List(params);
        
        return resList;
    }
    
    /**
     * 分页查询${service.entitySimpleName}实体列表
	 <#list service.queryConditionName2TypeNameMapping?keys as key>
	 * param ${key}
	 </#list>
     * @param pageIndex 当前页index从1开始计算 @param pageSize 每页显示行数
     * @return [参数说明]
     * 
     * @return List<${service.entitySimpleName}> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<${service.entitySimpleName}> query${service.entitySimpleName}PagedList(
<#list service.queryConditionName2TypeNameMapping?keys as key>
			${service.queryConditionName2TypeNameMapping[key]} ${key},
</#list>
    		int pageIndex,
            int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
<#list service.queryConditionName2TypeNameMapping?keys as key>
		params.put("${key}",${key});
</#list>
 
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<${service.entitySimpleName}> resPagedList = this.${service.lowerCaseEntitySimpleName}Dao.query${service.entitySimpleName}PagedList(params, pageIndex, pageSize);
        
        return resPagedList;
    }
}
