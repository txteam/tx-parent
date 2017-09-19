/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package ${service.basePackage}.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     * 将${service.lowerCaseEntitySimpleName}实例插入数据库中保存
     * 1、如果${service.lowerCaseEntitySimpleName} 为空时抛出参数为空异常
     * 2、如果${service.lowerCaseEntitySimpleName} 中部分必要参数为非法值时抛出参数不合法异常
     * 
     * @param ${service.lowerCaseEntitySimpleName} [参数说明]
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insert(${service.entitySimpleName} ${service.lowerCaseEntitySimpleName}) {
        //FIXME:验证参数是否合法
        AssertUtils.notNull(${service.lowerCaseEntitySimpleName}, "${service.lowerCaseEntitySimpleName} is null.");
                
        //FIXME:为添加的数据需要填入默认值的字段填入默认值
<#if !StringUtils.isEmpty(validPropertyName)>
		${service.lowerCaseEntitySimpleName}.set${validPropertyName?cap_first}(true);
</#if>
        ${service.lowerCaseEntitySimpleName}.setCreateDate(new Date());
        //调用数据持久层对实体进行持久化操作
        this.${service.lowerCaseEntitySimpleName}Dao.insert(${service.lowerCaseEntitySimpleName});
    }
    
    /**
     * 根据${service.idPropertyName}删除${service.lowerCaseEntitySimpleName}实例
     * 1、如果入参数为空，则抛出异常
     * 2、执行删除后，将返回数据库中被影响的条数 > 0，则返回true
     *
     * @param ${service.idPropertyName}
     * @return boolean 删除的条数>0则为true [返回类型说明]
     * @exception throws 
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean deleteBy${service.upCaseIdPropertyName}(String ${service.idPropertyName}) {
        AssertUtils.notEmpty(${service.idPropertyName}, "${service.idPropertyName} is empty.");
        
        ${service.entitySimpleName} condition = new ${service.entitySimpleName}();
        condition.set${service.upCaseIdPropertyName}(${service.idPropertyName});
        int resInt = this.${service.lowerCaseEntitySimpleName}Dao.delete(condition);
        
        boolean flag = resInt > 0;
        return flag;
    }
    
    /**
     * 根据${service.upCaseIdPropertyName}查询${service.entitySimpleName}实体
     * 1、当${service.idPropertyName}为empty时抛出异常
     *
     * @param ${service.idPropertyName}
     * @return ${service.entitySimpleName} [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public ${service.entitySimpleName} findBy${service.upCaseIdPropertyName}(String ${service.idPropertyName}) {
        AssertUtils.notEmpty(${service.idPropertyName}, "${service.idPropertyName} is empty.");
        
        ${service.entitySimpleName} condition = new ${service.entitySimpleName}();
        condition.set${service.upCaseIdPropertyName}(${service.idPropertyName});
        
        ${service.entitySimpleName} res = this.${service.lowerCaseEntitySimpleName}Dao.find(condition);
        return res;
    }
    
    /**
     * 查询${service.entitySimpleName}实体列表
     * <功能详细描述>
<#if !StringUtils.isEmpty(validPropertyName)>
     * @param ${validPropertyName}
</#if>
     * @param params      
     * @return [参数说明]
     * 
     * @return List<${service.entitySimpleName}> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<${service.entitySimpleName}> queryList(
<#if !StringUtils.isEmpty(validPropertyName)>
		Boolean ${validPropertyName},
</#if>
		Map<String,Object> params   
    	) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
<#if !StringUtils.isEmpty(validPropertyName)>
		params.put("${validPropertyName}",${validPropertyName});
</#if>

        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<${service.entitySimpleName}> resList = this.${service.lowerCaseEntitySimpleName}Dao.queryList(params);
        
        return resList;
    }
    
    /**
     * 分页查询${service.entitySimpleName}实体列表
     * <功能详细描述>
<#if !StringUtils.isEmpty(validPropertyName)>
     * @param ${validPropertyName}
</#if>
      * @param params    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<${service.entitySimpleName}> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<${service.entitySimpleName}> queryPagedList(
<#if !StringUtils.isEmpty(validPropertyName)>
		Boolean ${validPropertyName},
</#if>
		Map<String,Object> params,
    	int pageIndex,
        int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
<#if !StringUtils.isEmpty(validPropertyName)>
		params.put("${validPropertyName}",${validPropertyName});
</#if>
 
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<${service.entitySimpleName}> resPagedList = this.${service.lowerCaseEntitySimpleName}Dao.queryPagedList(params, pageIndex, pageSize);
        
        return resPagedList;
    }
    
     /**
      * 判断是否已经存在<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isExist(Map<String,String> key2valueMap, String exclude${service.upCaseIdPropertyName}) {
        AssertUtils.notEmpty(key2valueMap, "key2valueMap is empty");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(key2valueMap);
        params.put("exclude${service.upCaseIdPropertyName}", exclude${service.upCaseIdPropertyName});
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.${service.lowerCaseEntitySimpleName}Dao.count(params);
        
        return res > 0;
    }
    
    /**
      * 根据${service.idPropertyName}更新对象
      * <功能详细描述>
      * @param ${service.lowerCaseEntitySimpleName}
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateBy${service.upCaseIdPropertyName}(${service.entitySimpleName} ${service.lowerCaseEntitySimpleName}) {
        //FIXME:验证参数是否合法，必填字段是否填写，
        AssertUtils.notNull(${service.lowerCaseEntitySimpleName}, "${service.lowerCaseEntitySimpleName} is null.");
        AssertUtils.notEmpty(${service.lowerCaseEntitySimpleName}.get${service.upCaseIdPropertyName}(), "${service.lowerCaseEntitySimpleName}.${service.idPropertyName} is empty.");
        
        
        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("${service.idPropertyName}", ${service.lowerCaseEntitySimpleName}.get${service.upCaseIdPropertyName}());
        
        //FIXME:需要更新的字段
<#list service.updateAbleName2SqlMapColumnMapping?values as column>
	<#if !column.isId()>
		<#if column.isSimpleType()>
			<#if column.propertyName != validPropertyName>
		updateRowMap.put("${column.propertyName}", ${service.lowerCaseEntitySimpleName}.${column.getterMethodSimpleName}());	
			</#if>
		<#else>
		
		//type:${column.javaType.name}
		updateRowMap.put("${column.propertyName}", ${service.lowerCaseEntitySimpleName}.${column.getterMethodSimpleName}());
		</#if>
	</#if>
</#list>
        int updateRowCount = this.${service.lowerCaseEntitySimpleName}Dao.update(updateRowMap);
        
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }

<#if !StringUtils.isEmpty(validPropertyName)>
     /**
      * 根据${service.idPropertyName}禁用${service.entitySimpleName}<br/>
      * <功能详细描述>
      * @param ${service.idPropertyName}
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean disableBy${service.upCaseIdPropertyName}(String ${service.idPropertyName}) {
        AssertUtils.notEmpty(${service.idPropertyName}, "${service.idPropertyName} is empty.");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("${service.idPropertyName}", ${service.idPropertyName});
        params.put("${validPropertyName}", false);
        
        this.${service.lowerCaseEntitySimpleName}Dao.update(params);
        
        return true;
    }
    
    /**
      * 根据${service.idPropertyName}启用${service.entitySimpleName}<br/>
      * <功能详细描述>
      * @param postId
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean enableBy${service.upCaseIdPropertyName}(String ${service.idPropertyName}) {
        AssertUtils.notEmpty(${service.idPropertyName}, "${service.idPropertyName} is empty.");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("${service.idPropertyName}", ${service.idPropertyName});
        params.put("${validPropertyName}", true);
        
        this.${service.lowerCaseEntitySimpleName}Dao.update(params);
        
        return true;
    }
</#if>
}
