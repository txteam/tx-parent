/*
 * 描述: <描述>
 * 修改人:  
 * 修改时间:  
 * <修改描述:>
 */
package ${service.basePackage}.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    * 将${service.lowerCaseEntitySimpleName}实例插入数据库中保存<br />
    * 1、如果${service.lowerCaseEntitySimpleName}为空时抛出参数为空异常<br />
    * 2、如果${service.lowerCaseEntitySimpleName}中部分必要参数为非法值时抛出参数不合法异常<br />
    *
    * <功能详细描述>
    * 
    * @param district [参数说明]
    * 
    * @return void [返回类型说明]
    * @exception throws
    * @see [类、类#方法、类#成员]
    */
    @Transactional
    public void insert(${service.entitySimpleName} ${service.lowerCaseEntitySimpleName}) {
        //TODO:验证参数是否合法
        AssertUtils.notNull(${service.lowerCaseEntitySimpleName}, "${service.lowerCaseEntitySimpleName} is null.");
       	
        //TODO: 设置默认数据
        
        this.${service.lowerCaseEntitySimpleName}Dao.insert(${service.lowerCaseEntitySimpleName});
    }
      
    /**
     * 根据${service.idPropertyName}删除${service.lowerCaseEntitySimpleName}实例<br />
     * 1、如果入参数为空，则抛出异常<br />
     * 2、执行删除后，将返回数据库中被影响的条数<br />
     * 有些业务场景，如果已经被别人删除同样也可以认为是成功的<br />
     * 这里讲通用生成的业务层代码定义为返回影响的条数<br />
     *
     * @param ${service.idPropertyName}
     *
     * @return 返回删除的数据条数<br/>
     * @exception throws 
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public int deleteBy${service.upCaseIdPropertyName}(String ${service.idPropertyName}) {
        AssertUtils.notEmpty(${service.idPropertyName}, "${service.idPropertyName} is empty.");
        
        ${service.entitySimpleName} condition = new ${service.entitySimpleName}();
        condition.set${service.upCaseIdPropertyName}(${service.idPropertyName});
        return this.${service.lowerCaseEntitySimpleName}Dao.delete(condition);
    }
    
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
    public ${service.entitySimpleName} findBy${service.upCaseIdPropertyName}(String ${service.idPropertyName}) {
        AssertUtils.notEmpty(${service.idPropertyName}, "${service.idPropertyName} is empty.");
        
        ${service.entitySimpleName} condition = new ${service.entitySimpleName}();
        condition.set${service.upCaseIdPropertyName}(${service.idPropertyName});
        
        ${service.entitySimpleName} res = this.${service.lowerCaseEntitySimpleName}Dao.find(condition);
        return res;
    }
    
    /**
      * 根据${service.entitySimpleName}实体列表
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<${service.entitySimpleName}> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<${service.entitySimpleName}> queryList(Map<String,Object> params) {
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<${service.entitySimpleName}> resList = this.${service.lowerCaseEntitySimpleName}Dao.queryList(params);
        
        return resList;
    }
    
    /**
     * 分页查询${service.entitySimpleName}实体列表
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<${service.entitySimpleName}> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public PagedList<${service.entitySimpleName}> queryPagedList(Map<String,Object> params,int pageIndex,
            int pageSize) {
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<${service.entitySimpleName}> resPagedList = this.${service.lowerCaseEntitySimpleName}Dao.queryPagedList(params, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
      * 查询${service.lowerCaseEntitySimpleName}列表总条数
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int count(Map<String,Object> params){
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.${service.lowerCaseEntitySimpleName}Dao.count(params);
        
        return res;
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
        //验证参数是否合法，必填字段是否填写，
        AssertUtils.notNull(${service.lowerCaseEntitySimpleName}, "${service.lowerCaseEntitySimpleName} is null.");
        AssertUtils.notEmpty(${service.lowerCaseEntitySimpleName}.get${service.upCaseIdPropertyName}(), "${service.lowerCaseEntitySimpleName}.${service.idPropertyName} is empty.");
        
        
        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("${service.idPropertyName}", ${service.lowerCaseEntitySimpleName}.get${service.upCaseIdPropertyName}());
        
        //TODO:需要更新的字段
<#list service.sqlMapColumnList as column>
<#if !column.isId()>
<#if column.isSimpleType()>
		updateRowMap.put("${column.propertyName}", ${service.lowerCaseEntitySimpleName}.${column.getterMethodSimpleName}());	
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
}
