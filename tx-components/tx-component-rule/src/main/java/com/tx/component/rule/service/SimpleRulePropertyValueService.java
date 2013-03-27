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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tx.component.rule.dao.SimpleRulePropertyValueDao;
import com.tx.component.rule.model.SimpleRuleParamEnum;
import com.tx.component.rule.model.SimpleRulePropertyValue;
import com.tx.core.exceptions.parameter.ParameterIsEmptyException;

/**
 * SimpleRulePropertyValue的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("simpleRulePropertyValueService")
public class SimpleRulePropertyValueService {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(SimpleRulePropertyValueService.class);
    
    @SuppressWarnings("unused")
    //@Resource(name = "serviceLogger")
    private Logger serviceLogger;
    
    @Resource(name = "simpleRulePropertyValueDao")
    private SimpleRulePropertyValueDao simpleRulePropertyValueDao;
    
    /**
     * 根据SimpleRulePropertyValue实体列表
     *     1、根据规则id查询对应的规则属性值映射map
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<SimpleRulePropertyByte> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public MultiValueMap<SimpleRuleParamEnum, SimpleRulePropertyValue> querySimpleRulePropertyValueMultiMap(
           String ruleId) {
       if (StringUtils.isEmpty(ruleId)) {
           throw new ParameterIsEmptyException("ruleId is emtpy.");
       }
       
       //生成查询条件
       Map<String, Object> params = new HashMap<String, Object>();
       params.put("ruleId", ruleId);
       
       //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
       List<SimpleRulePropertyValue> resList = this.simpleRulePropertyValueDao.querySimpleRulePropertyValueList(params);
       
       //返回的map映射
       MultiValueMap<SimpleRuleParamEnum, SimpleRulePropertyValue> resMap = new LinkedMultiValueMap<SimpleRuleParamEnum, SimpleRulePropertyValue>();
       if (resList != null) {
           //排序
           Collections.sort(resList);
           for (SimpleRulePropertyValue srpbTemp : resList) {
               //压入multiMap
               resMap.add(srpbTemp.getSimpleRulePropertyParam(), srpbTemp);
           }
       }
       return resMap;
   }
}
