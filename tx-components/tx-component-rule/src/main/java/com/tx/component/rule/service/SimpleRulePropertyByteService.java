/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.rule.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tx.component.rule.dao.SimpleRulePropertyByteDao;
import com.tx.component.rule.model.SimpleRuleParamEnum;
import com.tx.component.rule.model.SimpleRulePropertyByte;
import com.tx.core.exceptions.parameter.ParameterIsEmptyException;

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
      * 根据SimpleRulePropertyByte实体列表
      *     1、根据规则id查询对应的规则属性值映射map
      * 
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<SimpleRulePropertyByte> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public MultiValueMap<SimpleRuleParamEnum, SimpleRulePropertyByte> querySimpleRulePropertyByteMultiMap(
            String ruleId) {
        if (StringUtils.isEmpty(ruleId)) {
            throw new ParameterIsEmptyException("ruleId is emtpy.");
        }
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ruleId", ruleId);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<SimpleRulePropertyByte> resList = this.simpleRulePropertyByteDao.querySimpleRulePropertyByteList(params);
        
        //返回的map映射
        MultiValueMap<SimpleRuleParamEnum, SimpleRulePropertyByte> resMap = new LinkedMultiValueMap<SimpleRuleParamEnum, SimpleRulePropertyByte>();
        if (resList != null) {
            //排序
            Collections.sort(resList);
            for (SimpleRulePropertyByte srpbTemp : resList) {
                //压入multiMap
                resMap.add(srpbTemp.getSimpleRulePropertyParam(), srpbTemp);
            }
        }
        return resMap;
    }
    
    /**
      * 保存规则属性值
      * <功能详细描述>
      * @param propertyValuesMap [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insertSimpleRulePropertyByte(
            MultiValueMap<SimpleRuleParamEnum, SimpleRulePropertyByte> propertyValuesMap){
        
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
    public void insertSimpleRulePropertyByte(
            SimpleRulePropertyByte simpleRulePropertyByte) {
        //验证参数是否合法，必填字段是否填写，
        //如果没有填写抛出parameterIsEmptyException,
        //如果有参数不合法ParameterIsInvalruleIdException
        if (simpleRulePropertyByte == null
                || StringUtils.isEmpty(simpleRulePropertyByte.getRuleId())
                || StringUtils.isEmpty(simpleRulePropertyByte.getParamKey())
                || simpleRulePropertyByte.getSimpleRulePropertyParam() == null) {
            throw new ParameterIsEmptyException(
                    "simpleRulePropertyByte or .id or .paramKey isEmpty.");
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
}
