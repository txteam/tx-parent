/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.workflow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.workflow.dao.ProTaskDefinitionDao;
import com.tx.component.workflow.model.ProTaskDefinition;
import com.tx.core.exceptions.parameter.ParameterIsEmptyException;
import com.tx.core.paged.model.PagedList;

/**
 * ProTaskDefinition的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("proTaskDefinitionService")
public class ProTaskDefinitionService {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(ProTaskDefinitionService.class);
    
    @SuppressWarnings("unused")
    private Logger serviceLogger;
    
    @Resource(name = "proTaskDefinitionDao")
    private ProTaskDefinitionDao proTaskDefinitionDao;
    
    /**
      * 根据Id查询ProTaskDefinition实体
      * 1、当id为empty时返回null
      * <功能详细描述>
      * @param id
      * @return [参数说明]
      * 
      * @return ProTaskDefinition [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    public ProTaskDefinition findProTaskDefinitionById(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        
        ProTaskDefinition condition = new ProTaskDefinition();
        condition.setId(id);
        return this.proTaskDefinitionDao.findProTaskDefinition(condition);
    }
    
    /**
      * 根据ProTaskDefinition实体列表
      * TODO:补充说明
      * 
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<ProTaskDefinition> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<ProTaskDefinition> queryProTaskDefinitionList(/*TODO:自己定义条件*/) {
        //TODO:判断条件合法性
        
        //TODO:生成查询条件
        Map<String, Object> params = new HashMap();
        
        //TODO:根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<ProTaskDefinition> resList = this.proTaskDefinitionDao.queryProTaskDefinitionList(params);
        
        return resList;
    }
    
    /**
     * 分页查询ProTaskDefinition实体列表
     * TODO:补充说明
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<ProTaskDefinition> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public PagedList<ProTaskDefinition> queryProTaskDefinitionPagedList(/*TODO:自己定义条件*/int pageIndex,
            int pageSize) {
        //TODO:判断条件合法性
        
        //TODO:生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        
        //TODO:根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<ProTaskDefinition> resPagedList = this.proTaskDefinitionDao.queryProTaskDefinitionPagedList(params, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
      * 查询proTaskDefinition列表总条数
      * TODO:补充说明
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int countProTaskDefinition(/*TODO:自己定义条件*/){
        //TODO:判断条件合法性
        
        //TODO:生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        
        //TODO:根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.proTaskDefinitionDao.countProTaskDefinition(params);
        
        return res;
    }
    
    /**
      * 将proTaskDefinition实例插入数据库中保存
      * 1、如果proTaskDefinition为空时抛出参数为空异常
      * 2、如果proTaskDefinition中部分必要参数为非法值时抛出参数不合法异常
      * <功能详细描述>
      * @param proTaskDefinition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insertProTaskDefinition(ProTaskDefinition proTaskDefinition) {
        //TODO:验证参数是否合法，必填字段是否填写，
        //如果没有填写抛出parameterIsEmptyException,
        //如果有参数不合法ParameterIsInvalidException
        if (proTaskDefinition == null /*TODO:|| 其他参数验证*/) {
            throw new ParameterIsEmptyException(
                    "ProTaskDefinitionService.insertProTaskDefinition proTaskDefinition isNull.");
        }
        
        this.proTaskDefinitionDao.insertProTaskDefinition(proTaskDefinition);
    }
    
    /**
      * 根据id删除proTaskDefinition实例
      * 1、如果入参数为空，则抛出异常
      * 2、执行删除后，将返回数据库中被影响的条数
      * @param id
      * @return 返回删除的数据条数，<br/>
      * 有些业务场景，如果已经被别人删除同样也可以认为是成功的
      * 这里讲通用生成的业务层代码定义为返回影响的条数
      * @return int [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public int deleteById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new ParameterIsEmptyException(
                    "ProTaskDefinitionService.deleteById id isEmpty.");
        }
        
        ProTaskDefinition condition = new ProTaskDefinition();
        condition.setId(id);
        return this.proTaskDefinitionDao.deleteProTaskDefinition(condition);
    }
    
    /**
      * 根据id更新对象
      * <功能详细描述>
      * @param proTaskDefinition
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(ProTaskDefinition proTaskDefinition) {
        //TODO:验证参数是否合法，必填字段是否填写，
        //如果没有填写抛出parameterIsEmptyException,
        //如果有参数不合法ParameterIsInvalidException
        if (proTaskDefinition == null || StringUtils.isEmpty(proTaskDefinition.getId())) {
            throw new ParameterIsEmptyException(
                    "ProTaskDefinitionService.updateById proTaskDefinition or proTaskDefinition.id is empty.");
        }
        
        //TODO:生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("id", proTaskDefinition.getId());
        
        //TODO:需要更新的字段
		updateRowMap.put("taskType", proTaskDefinition.getTaskType());	
		updateRowMap.put("wftkdId", proTaskDefinition.getWftkdId());	
		updateRowMap.put("serviceType", proTaskDefinition.getServiceType());	
		updateRowMap.put("parentId", proTaskDefinition.getParentId());	
		updateRowMap.put("order", proTaskDefinition.getOrder());	
		updateRowMap.put("viewAble", proTaskDefinition.isViewAble());	
		updateRowMap.put("name", proTaskDefinition.getName());	
		updateRowMap.put("alise", proTaskDefinition.getAlise());	
		updateRowMap.put("wfdId", proTaskDefinition.getWfdId());	
		updateRowMap.put("key", proTaskDefinition.getKey());	
        
        int updateRowCount = this.proTaskDefinitionDao.updateProTaskDefinition(updateRowMap);
        
        //TODO:如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }
}
