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

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ${service.basePackage}.dao.${service.entityTypeSimpleName}Dao;
import ${service.basePackage}.model.${service.entityTypeSimpleName};
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * ${service.entityTypeSimpleName}的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("${service.entityTypeSimpleName?uncap_first}Service")
public class ${service.entityTypeSimpleName}Service {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(${service.entityTypeSimpleName}Service.class);
    
    @Resource(name = "${service.entityTypeSimpleName?uncap_first}Dao")
    private ${service.entityTypeSimpleName}Dao ${service.entityTypeSimpleName?uncap_first}Dao;
    
    /**
     * 将${service.entityTypeSimpleName?uncap_first}插入数据库中保存
     * 1、如果${service.entityTypeSimpleName?uncap_first} 为空时抛出参数为空异常
     * 2、如果${service.entityTypeSimpleName?uncap_first} 中部分必要参数为非法值时抛出参数不合法异常
     * 
     * @param ${service.entityTypeSimpleName?uncap_first} [参数说明]
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insert(${service.entityTypeSimpleName} ${service.entityTypeSimpleName?uncap_first}) {
        //验证参数是否合法
        AssertUtils.notNull(${service.entityTypeSimpleName?uncap_first}, "${service.entityTypeSimpleName?uncap_first} is null.");
<#list service.columnList as column>
	<#if !column.isNullable() && !column.isPrimaryKey()>
		<#if "valid" != column.propertyName && "createDate" != column.propertyName && "lastUpdateDate" != column.propertyName>
		AssertUtils.notEmpty(${service.entityTypeSimpleName?uncap_first}.${column.getPropertyDescriptor().getReadMethod().getName()}(), "${service.entityTypeSimpleName?uncap_first}.${column.propertyName} is empty.");
		</#if>
	</#if>
</#list>
           
        //FIXME:为添加的数据需要填入默认值的字段填入默认值
<#list service.columnList as column>
	<#if "valid" == column.propertyName>
		${service.entityTypeSimpleName?uncap_first}.setValid(true);
	<#elseif "createDate" == column.propertyName>
		${service.entityTypeSimpleName?uncap_first}.setCreateDate(new Date());
	<#elseif "lastUpdateDate" == column.propertyName>
		${service.entityTypeSimpleName?uncap_first}.setLastUpdateDate(new Date());
	</#if>
</#list>
        
        //调用数据持久层对实体进行持久化操作
        this.${service.entityTypeSimpleName?uncap_first}Dao.insert(${service.entityTypeSimpleName?uncap_first});
    }
    
    /**
     * 根据${service.pkColumn.propertyName}删除${service.entityTypeSimpleName}实例
     * 1、如果入参数为空，则抛出异常
     * 2、执行删除后，将返回数据库中被影响的条数 > 0，则返回true
     *
     * @param ${service.pkColumn.propertyName}
     * @return boolean 删除的条数>0则为true [返回类型说明]
     * @exception throws 
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean deleteBy${service.pkColumn.propertyName?cap_first}(String ${service.pkColumn.propertyName}) {
        AssertUtils.notEmpty(${service.pkColumn.propertyName}, "${service.pkColumn.propertyName} is empty.");
        
        ${service.entityTypeSimpleName} condition = new ${service.entityTypeSimpleName}();
        condition.set${service.pkColumn.propertyName?cap_first}(${service.pkColumn.propertyName});
        
        int resInt = this.${service.entityTypeSimpleName?uncap_first}Dao.delete(condition);
        boolean flag = resInt > 0;
        return flag;
    }
    
    /**
     * 根据${service.pkColumn.propertyName}查询${service.entityTypeSimpleName}实体
     * 1、当${service.pkColumn.propertyName}为empty时抛出异常
     *
     * @param ${service.pkColumn.propertyName}
     * @return ${service.entityTypeSimpleName} [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public ${service.entityTypeSimpleName} findBy${service.pkColumn.propertyName?cap_first}(String ${service.pkColumn.propertyName}) {
        AssertUtils.notEmpty(${service.pkColumn.propertyName}, "${service.pkColumn.propertyName} is empty.");
        
        ${service.entityTypeSimpleName} condition = new ${service.entityTypeSimpleName}();
        condition.set${service.pkColumn.propertyName?cap_first}(${service.pkColumn.propertyName});
        
        ${service.entityTypeSimpleName} res = this.${service.entityTypeSimpleName?uncap_first}Dao.find(condition);
        return res;
    }
    
<#if service.codeColumn??>
    /**
     * 根据${service.codeColumn.propertyName}查询${service.entityTypeSimpleName}实体
     * 1、当${service.codeColumn.propertyName}为empty时抛出异常
     *
     * @param ${service.codeColumn.propertyName}
     * @return ${service.entityTypeSimpleName} [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public ${service.entityTypeSimpleName} findBy${service.codeColumn.propertyName?cap_first}(String ${service.codeColumn.propertyName}) {
        AssertUtils.notEmpty(${service.codeColumn.propertyName}, "${service.codeColumn.propertyName} is empty.");
        
        ${service.entityTypeSimpleName} condition = new ${service.entityTypeSimpleName}();
        condition.set${service.codeColumn.propertyName?cap_first}(${service.codeColumn.propertyName});
        
        ${service.entityTypeSimpleName} res = this.${service.entityTypeSimpleName?uncap_first}Dao.find(condition);
        return res;
    }
</#if>
    
    /**
     * 查询${service.entityTypeSimpleName}实体列表
     * <功能详细描述>
<#if service.validColumn??>
     * @param ${service.validColumn.propertyName}
</#if>
     * @param params      
     * @return [参数说明]
     * 
     * @return List<${service.entityTypeSimpleName}> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<${service.entityTypeSimpleName}> queryList(
<#if service.validColumn??>
		Boolean ${service.validColumn.propertyName},
</#if>
		Map<String,Object> params   
    	) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
<#if service.validColumn??>
		params.put("${service.validColumn.propertyName}",${service.validColumn.propertyName});
</#if>

        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<${service.entityTypeSimpleName}> resList = this.${service.entityTypeSimpleName?uncap_first}Dao.queryList(params);
        
        return resList;
    }
    
	/**
     * 分页查询${service.entityTypeSimpleName}实体列表
     * <功能详细描述>
<#if service.validColumn??>
     * @param ${service.validColumn.propertyName}
</#if>
     * @param params    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<${service.entityTypeSimpleName}> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<${service.entityTypeSimpleName}> queryPagedList(
<#if service.validColumn??>
		Boolean ${service.validColumn.propertyName},
</#if>
		Map<String,Object> params,
    	int pageIndex,
        int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
<#if service.validColumn??>
		params.put("${service.validColumn.propertyName}",${service.validColumn.propertyName});
</#if>
 
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<${service.entityTypeSimpleName}> resPagedList = this.${service.entityTypeSimpleName?uncap_first}Dao.queryPagedList(params, pageIndex, pageSize);
        
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
    public boolean exists(Map<String,String> key2valueMap, String exclude${service.pkColumn.propertyName?cap_first}) {
        AssertUtils.notEmpty(key2valueMap, "key2valueMap is empty");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(key2valueMap);
        params.put("exclude${service.pkColumn.propertyName?cap_first}", exclude${service.pkColumn.propertyName?cap_first});
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.${service.entityTypeSimpleName?uncap_first}Dao.count(params);
        
        return res > 0;
    }
    
    /**
     * 根据${service.pkColumn.propertyName}更新对象
     * <功能详细描述>
     * @param ${service.entityTypeSimpleName?uncap_first}
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateBy${service.pkColumn.propertyName?cap_first}(${service.entityTypeSimpleName} ${service.entityTypeSimpleName?uncap_first}) {
        //验证参数是否合法，必填字段是否填写
        AssertUtils.notNull(${service.entityTypeSimpleName?uncap_first}, "${service.entityTypeSimpleName?uncap_first} is null.");
        AssertUtils.notEmpty(${service.entityTypeSimpleName?uncap_first}.${service.pkColumn.getPropertyDescriptor().getReadMethod().getName()}(), "${service.entityTypeSimpleName?uncap_first}.${service.pkColumn.propertyName} is empty.");
<#list service.columnList as column>
	<#if !column.isNullable() && !column.isPrimaryKey() && column.isUpdatable() && "valid" != column.propertyName && "createDate" != column.propertyName && "lastUpdateDate" != column.propertyName>
		AssertUtils.notEmpty(${service.entityTypeSimpleName?uncap_first}.${column.getPropertyDescriptor().getReadMethod().getName()}(), "${service.entityTypeSimpleName?uncap_first}.${column.propertyName} is empty.");
	</#if>
</#list>

        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("${service.pkColumn.propertyName}", ${service.entityTypeSimpleName?uncap_first}.${service.pkColumn.getPropertyDescriptor().getReadMethod().getName()}());
        
        //FIXME:需要更新的字段
<#list service.columnList as column>
	<#if !column.isPrimaryKey() && column.isUpdatable() && "createDate" != column.propertyName && "lastUpdateDate" != column.propertyName>
		updateRowMap.put("${column.propertyName}", ${service.entityTypeSimpleName?uncap_first}.${column.getPropertyDescriptor().getReadMethod().getName()}());
	</#if>
</#list>
<#list service.columnList as column>
	<#if "lastUpdateDate" == column.propertyName>
		updateRowMap.put("${column.propertyName}", new Date());
	</#if>
</#list>

        int updateRowCount = this.${service.entityTypeSimpleName?uncap_first}Dao.update(updateRowMap); 
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }

<#if service.validColumn??>
     /**
      * 根据${service.pkColumn.propertyName}禁用${service.entityTypeSimpleName}<br/>
      * <功能详细描述>
      * @param ${service.pkColumn.propertyName}
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean disableBy${service.pkColumn.propertyName?cap_first}(String ${service.pkColumn.propertyName}) {
        AssertUtils.notEmpty(${service.pkColumn.propertyName}, "${service.pkColumn.propertyName} is empty.");
        
        //生成条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("${service.pkColumn.propertyName}", ${service.pkColumn.propertyName});
        params.put("${service.validColumn.propertyName}", false);
        
        this.${service.entityTypeSimpleName?uncap_first}Dao.update(params);
        
        return true;
    }
    
    /**
      * 根据${service.pkColumn.propertyName}启用${service.entityTypeSimpleName}<br/>
      * <功能详细描述>
      * @param ${service.pkColumn.propertyName}
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean enableBy${service.pkColumn.propertyName?cap_first}(String ${service.pkColumn.propertyName}) {
        AssertUtils.notEmpty(${service.pkColumn.propertyName}, "${service.pkColumn.propertyName} is empty.");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("${service.pkColumn.propertyName}", ${service.pkColumn.propertyName});
        params.put("${service.validColumn.propertyName}", true);
        
        this.${service.entityTypeSimpleName?uncap_first}Dao.update(params);
        
        return true;
    }
</#if>
}
