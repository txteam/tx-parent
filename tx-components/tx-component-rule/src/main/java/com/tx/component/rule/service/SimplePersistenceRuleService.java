/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.rule.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.rule.dao.SimplePersistenceRuleDao;
import com.tx.component.rule.model.RuleStateEnum;
import com.tx.component.rule.model.RuleTypeEnum;
import com.tx.component.rule.model.SimplePersistenceRule;
import com.tx.core.exceptions.parameter.ParameterIsEmptyException;
import com.tx.core.paged.model.PagedList;

/**
 * SimplePersistenceRule的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("simplePersistenceRuleService")
public class SimplePersistenceRuleService {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(SimplePersistenceRuleService.class);
    
    @SuppressWarnings("unused")
    //@Resource(name = "serviceLogger")
    private Logger serviceLogger;
    
    @Resource(name = "simplePersistenceRuleDao")
    private SimplePersistenceRuleDao simplePersistenceRuleDao;
    
    @Resource(name = "simpleRulePropertyByteService")
    private SimpleRulePropertyByteService simpleRulePropertyByteService;
    
    @Resource(name = "simpleRulePropertyValueService")
    private SimpleRulePropertyValueService simpleRulePropertyValueService;
    
    @Resource(name = "simpleRulePropertyParamService")
    private SimpleRulePropertyParamService simpleRulePropertyParamService;
    
    /**
      * 装在规则参数
      * <功能详细描述>
      * @param rule [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void setupRuleParam(SimplePersistenceRule rule) {
        //如果参数为空，装载参数
        if (CollectionUtils.isEmpty(rule.getParams())) {
            rule.setParams(this.simpleRulePropertyParamService.queryParamsByRuleType(rule.getRuleType()));
        }
        
        //装载参数值
        //装载byte类型值
        if (rule.isHasByteParam()) {
            rule.setBytePropertyValues(this.simpleRulePropertyByteService.querySimpleRulePropertyByteMultiMap(rule.getId()));
        }
        //装载value类型值
        if (rule.isHasValueParam()) {
            rule.setStringPropertyValues(this.simpleRulePropertyValueService.querySimpleRulePropertyValueMultiMap(rule.getId()));
        }
    }
    
    /**
     * 根据RuleType查询SimplePersistenceRule实体列表
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<SimplePersistenceRule> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public List<SimplePersistenceRule> querySimplePersistenceRuleListByRuleType(
            RuleTypeEnum ruleType) {
        //判断条件合法性
        if (ruleType == null) {
            throw new ParameterIsEmptyException(
                    "SimplePersistenceRuleService.querySimplePersistenceRuleListByRuleType ruleType is empty.");
        }
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ruleType", ruleType);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<SimplePersistenceRule> resList = this.simplePersistenceRuleDao.querySimplePersistenceRuleList(params);
        
        //如果列表为空直接返回
        if (resList == null) {
            return resList;
        }
        
        //列表不为空，设置参数集合，以及查询对应的参数值
        for (SimplePersistenceRule ruleTemp : resList) {
            //装载参数
            setupRuleParam(ruleTemp);
        }
        
        return resList;
    }
    
    /**
      * 根据规则id改变规则当前状态
      * <功能详细描述>
      * @param ruleId
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean changeRuleStateById(String ruleId, RuleStateEnum state) {
        if (state == null || StringUtils.isEmpty(ruleId)) {
            throw new ParameterIsEmptyException(
                    "SimplePersistenceRuleService.changeRuleStateById ruleId or state.id is empty.");
        }
        
        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("id", ruleId);
        
        //需要更新的字段
        updateRowMap.put("state", state);
        
        int updateRowCount = this.simplePersistenceRuleDao.updateSimplePersistenceRule(updateRowMap);
        
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }
    
    /**
      * 根据规则实体的联合唯一键，更新规则状态
      * <功能详细描述>
      * @param rule
      * @param serviceType
      * @param ruleType
      * @param state
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean changeRuleStateByRule(String rule, String serviceType,
            RuleTypeEnum ruleType, RuleStateEnum state) {
        if (state == null || StringUtils.isEmpty(rule) || ruleType == null
                || StringUtils.isEmpty(serviceType)) {
            throw new ParameterIsEmptyException(
                    "rule or serviceType or ruleType or state is emtpy.");
        }
        
        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("rule", rule);
        updateRowMap.put("ruleType", ruleType);
        updateRowMap.put("serviceType", serviceType);
        
        //需要更新的字段
        updateRowMap.put("state", state);
        
        int updateRowCount = this.simplePersistenceRuleDao.updateSimplePersistenceRule(updateRowMap);
        
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }
    
    /**
      * 根据Id查询SimplePersistenceRule实体
      * 1、当id为empty时返回null
      * <功能详细描述>
      * @param id
      * @return [参数说明]
      * 
      * @return SimplePersistenceRule [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    public SimplePersistenceRule findSimplePersistenceRuleById(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        
        SimplePersistenceRule condition = new SimplePersistenceRule();
        condition.setId(id);
        SimplePersistenceRule res = this.simplePersistenceRuleDao.findSimplePersistenceRule(condition);
        
        if (res != null) {
            setupRuleParam(res);
        }
        
        return res;
    }
    
    /**
      * 分页查询SimplePersistenceRule实体列表<br/>
      *<功能详细描述>
      * @param serviceType
      * @param ruleTypeEnum
      * @param ruleStateEnum
      * @param name
      * @param pageIndex
      * @param pageSize
      * @return [参数说明]
      * 
      * @return PagedList<SimplePersistenceRule> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public PagedList<SimplePersistenceRule> querySimplePersistenceRulePagedList(
            String serviceType, RuleTypeEnum ruleTypeEnum,
            RuleStateEnum ruleStateEnum, String name, int pageIndex,
            int pageSize) {
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("serviceType", serviceType);
        params.put("ruleTypeEnum", ruleTypeEnum);
        params.put("ruleStateEnum", ruleStateEnum);
        params.put("name", name);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<SimplePersistenceRule> resPagedList = this.simplePersistenceRuleDao.querySimplePersistenceRulePagedList(params,
                pageIndex,
                pageSize);
        
        return resPagedList;
    }
    
    /**
      * 将simplePersistenceRule实例插入数据库中保存
      * 1、如果simplePersistenceRule为空时抛出参数为空异常
      * 2、如果simplePersistenceRule中部分必要参数为非法值时抛出参数不合法异常
      * <功能详细描述>
      * @param simplePersistenceRule [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insertSimplePersistenceRule(
            SimplePersistenceRule simplePersistenceRule) {
        //验证参数是否合法，必填字段是否填写，
        //如果没有填写抛出parameterIsEmptyException,
        //如果有参数不合法ParameterIsInvalidException
        if (simplePersistenceRule == null
                || StringUtils.isEmpty(simplePersistenceRule.rule())
                || simplePersistenceRule.getState() == null
                || StringUtils.isEmpty(simplePersistenceRule.getServiceType())) {
            throw new ParameterIsEmptyException(
                    "simplePersistenceRule or state or serviceType is empty.");
        }
        
        this.simplePersistenceRuleDao.insertSimplePersistenceRule(simplePersistenceRule);
    }
    
    /**
      * 根据id删除simplePersistenceRule实例
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
                    "SimplePersistenceRuleService.deleteById id isEmpty.");
        }
        
        SimplePersistenceRule condition = new SimplePersistenceRule();
        condition.setId(id);
        return this.simplePersistenceRuleDao.deleteSimplePersistenceRule(condition);
    }
}
