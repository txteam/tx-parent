/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.workflow.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.workflow.dao.ProcessDefinitionDao;
import com.tx.component.workflow.model.ProcessDefinition;
import com.tx.core.exceptions.parameter.ParameterIsEmptyException;
import com.tx.core.paged.model.PagedList;

/**
 * ProcessDefinition的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("processDefinitionService")
public class ProcessDefinitionService {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(ProcessDefinitionService.class);
    
    @SuppressWarnings("unused")
    //@Resource(name = "serviceLogger")
    private Logger serviceLogger;
    
    @Resource(name = "processDefinitionDao")
    private ProcessDefinitionDao processDefinitionDao;
    
    /**
     * 将processDefinition实例插入数据库中保存
     * 1、如果processDefinition为空时抛出参数为空异常
     * 2、如果processDefinition中部分必要参数为非法值时抛出参数不合法异常
     * <功能详细描述>
     * @param processDefinition [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws 可能存在数据库访问异常DataAccessException
     * @see [类、类#方法、类#成员]
    */
    @Transactional
    public void insertProcessDefinition(ProcessDefinition processDefinition) {
        if (processDefinition == null
                || StringUtils.isEmpty(processDefinition.getKey())
                || StringUtils.isEmpty(processDefinition.getName())
                || StringUtils.isEmpty(processDefinition.getCategory())
                || StringUtils.isEmpty(processDefinition.getWfdId())) {
            throw new ParameterIsEmptyException(
                    "ProcessDefinitionService.insertProcessDefinition definition or key or name or category or wfdId is empty.");
        }
        
        this.processDefinitionDao.insertProcessDefinition(processDefinition);
    }
    
    /**
      * 根据Id查询ProcessDefinition实体
      * 1、当id为empty时返回null
      * <功能详细描述>
      * @param id
      * @return [参数说明]
      * 
      * @return ProcessDefinition [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    public ProcessDefinition findProcessDefinitionById(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        
        ProcessDefinition condition = new ProcessDefinition();
        condition.setId(id);
        return this.processDefinitionDao.findProcessDefinition(condition);
    }
    
    /**
      * 根据ProcessDefinition实体列表
      * TODO:补充说明
      * 
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<ProcessDefinition> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<ProcessDefinition> queryProcessDefinitionList(/*TODO:自己定义条件*/) {
        //TODO:判断条件合法性
        
        //TODO:生成查询条件
        Map<String, Object> params = new HashMap();
        
        //TODO:根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<ProcessDefinition> resList = this.processDefinitionDao.queryProcessDefinitionList(params);
        
        return resList;
    }
    
    /**
     * 分页查询ProcessDefinition实体列表
     * TODO:补充说明
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<ProcessDefinition> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public PagedList<ProcessDefinition> queryProcessDefinitionPagedList(
    /*TODO:自己定义条件*/int pageIndex, int pageSize) {
        //TODO:判断条件合法性
        
        //TODO:生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        
        //TODO:根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<ProcessDefinition> resPagedList = this.processDefinitionDao.queryProcessDefinitionPagedList(params,
                pageIndex,
                pageSize);
        
        return resPagedList;
    }
    
    /**
      * 查询processDefinition列表总条数
      * TODO:补充说明
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int countProcessDefinition(/*TODO:自己定义条件*/) {
        //TODO:判断条件合法性
        
        //TODO:生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        
        //TODO:根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.processDefinitionDao.countProcessDefinition(params);
        
        return res;
    }
    
    /**
      * 根据id删除processDefinition实例
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
                    "ProcessDefinitionService.deleteById id isEmpty.");
        }
        
        ProcessDefinition condition = new ProcessDefinition();
        condition.setId(id);
        return this.processDefinitionDao.deleteProcessDefinition(condition);
    }
    
    /**
      * 根据id更新对象
      * <功能详细描述>
      * @param processDefinition
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(ProcessDefinition processDefinition) {
        //TODO:验证参数是否合法，必填字段是否填写，
        //如果没有填写抛出parameterIsEmptyException,
        //如果有参数不合法ParameterIsInvalidException
        if (processDefinition == null
                || StringUtils.isEmpty(processDefinition.getId())) {
            throw new ParameterIsEmptyException(
                    "ProcessDefinitionService.updateById processDefinition or processDefinition.id is empty.");
        }
        
        //TODO:生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("id", processDefinition.getId());
        
        //TODO:需要更新的字段
        updateRowMap.put("category", processDefinition.getCategory());
        updateRowMap.put("name", processDefinition.getName());
        updateRowMap.put("state", processDefinition.getState());
        updateRowMap.put("wfdId", processDefinition.getWfdId());
        updateRowMap.put("serviceType", processDefinition.getServiceType());
        updateRowMap.put("key", processDefinition.getKey());
        updateRowMap.put("version", processDefinition.getVersion());
        
        int updateRowCount = this.processDefinitionDao.updateProcessDefinition(updateRowMap);
        
        //TODO:如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }
}
