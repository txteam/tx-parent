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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.rule.dao.SimpleRulePropertyByteDao;
import com.tx.component.rule.model.SimpleRulePropertyByte;
import com.tx.core.exceptions.parameter.ParameterIsEmptyException;
import com.tx.core.paged.model.PagedList;

/**
 * SimpleRulePropertyByte的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("simpleRulePropertyByteService")
public class SimpleRulePropertyByteService {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(SimpleRulePropertyByteService.class);
    
    @SuppressWarnings("unused")
    //@Resource(name = "serviceLogger")
    private Logger serviceLogger;
    
    @Resource(name = "simpleRulePropertyByteDao")
    private SimpleRulePropertyByteDao simpleRulePropertyByteDao;
    
    /**
      * 根据RuleId查询SimpleRulePropertyByte实体
      * 1、当ruleId为empty时返回null
      * <功能详细描述>
      * @param ruleId
      * @return [参数说明]
      * 
      * @return SimpleRulePropertyByte [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    public SimpleRulePropertyByte findSimpleRulePropertyByteByRuleId(String ruleId) {
        if (StringUtils.isEmpty(ruleId)) {
            return null;
        }
        
        SimpleRulePropertyByte condition = new SimpleRulePropertyByte();
        condition.setRuleId(ruleId);
        return this.simpleRulePropertyByteDao.findSimpleRulePropertyByte(condition);
    }
    
    /**
      * 根据SimpleRulePropertyByte实体列表
      * TODO:补充说明
      * 
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<SimpleRulePropertyByte> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<SimpleRulePropertyByte> querySimpleRulePropertyByteList(/*TODO:自己定义条件*/) {
        //TODO:判断条件合法性
        
        //TODO:生成查询条件
        Map<String, Object> params = new HashMap();
        
        //TODO:根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<SimpleRulePropertyByte> resList = this.simpleRulePropertyByteDao.querySimpleRulePropertyByteList(params);
        
        return resList;
    }
    
    /**
     * 分页查询SimpleRulePropertyByte实体列表
     * TODO:补充说明
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<SimpleRulePropertyByte> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public PagedList<SimpleRulePropertyByte> querySimpleRulePropertyBytePagedList(/*TODO:自己定义条件*/int pageIndex,
            int pageSize) {
        //TODO:判断条件合法性
        
        //TODO:生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        
        //TODO:根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<SimpleRulePropertyByte> resPagedList = this.simpleRulePropertyByteDao.querySimpleRulePropertyBytePagedList(params, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
      * 查询simpleRulePropertyByte列表总条数
      * TODO:补充说明
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int countSimpleRulePropertyByte(/*TODO:自己定义条件*/){
        //TODO:判断条件合法性
        
        //TODO:生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        
        //TODO:根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.simpleRulePropertyByteDao.countSimpleRulePropertyByte(params);
        
        return res;
    }
    
    /**
      * 将simpleRulePropertyByte实例插入数据库中保存
      * 1、如果simpleRulePropertyByte为空时抛出参数为空异常
      * 2、如果simpleRulePropertyByte中部分必要参数为非法值时抛出参数不合法异常
      * <功能详细描述>
      * @param simpleRulePropertyByte [参数说明]
      * 
      * @return voruleId [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insertSimpleRulePropertyByte(SimpleRulePropertyByte simpleRulePropertyByte) {
        //TODO:验证参数是否合法，必填字段是否填写，
        //如果没有填写抛出parameterIsEmptyException,
        //如果有参数不合法ParameterIsInvalruleIdException
        if (simpleRulePropertyByte == null /*TODO:|| 其他参数验证*/) {
            throw new ParameterIsEmptyException(
                    "SimpleRulePropertyByteService.insertSimpleRulePropertyByte simpleRulePropertyByte isNull.");
        }
        
        this.simpleRulePropertyByteDao.insertSimpleRulePropertyByte(simpleRulePropertyByte);
    }
    
    /**
      * 根据ruleId删除simpleRulePropertyByte实例
      * 1、如果入参数为空，则抛出异常
      * 2、执行删除后，将返回数据库中被影响的条数
      * @param ruleId
      * @return 返回删除的数据条数，<br/>
      * 有些业务场景，如果已经被别人删除同样也可以认为是成功的
      * 这里讲通用生成的业务层代码定义为返回影响的条数
      * @return int [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public int deleteByRuleId(String ruleId) {
        if (StringUtils.isEmpty(ruleId)) {
            throw new ParameterIsEmptyException(
                    "SimpleRulePropertyByteService.deleteByRuleId ruleId isEmpty.");
        }
        
        SimpleRulePropertyByte condition = new SimpleRulePropertyByte();
        condition.setRuleId(ruleId);
        return this.simpleRulePropertyByteDao.deleteSimpleRulePropertyByte(condition);
    }
    
    /**
      * 根据ruleId更新对象
      * <功能详细描述>
      * @param simpleRulePropertyByte
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateByRuleId(SimpleRulePropertyByte simpleRulePropertyByte) {
        //TODO:验证参数是否合法，必填字段是否填写，
        //如果没有填写抛出parameterIsEmptyException,
        //如果有参数不合法ParameterIsInvalruleIdException
        if (simpleRulePropertyByte == null || StringUtils.isEmpty(simpleRulePropertyByte.getRuleId())) {
            throw new ParameterIsEmptyException(
                    "SimpleRulePropertyByteService.updateByRuleId simpleRulePropertyByte or simpleRulePropertyByte.ruleId is empty.");
        }
        
        //TODO:生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("ruleId", simpleRulePropertyByte.getRuleId());
        
        //TODO:需要更新的字段
		updateRowMap.put("simpleRulePropertyParam", simpleRulePropertyByte.getSimpleRulePropertyParam());	
		updateRowMap.put("paramKey", simpleRulePropertyByte.getParamKey());	
		updateRowMap.put("paramValue", simpleRulePropertyByte.getParamValue());	
		updateRowMap.put("paramValueOrdered", simpleRulePropertyByte.getParamValueOrdered());	
        
        int updateRowCount = this.simpleRulePropertyByteDao.updateSimpleRulePropertyByte(updateRowMap);
        
        //TODO:如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }
}
