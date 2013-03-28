/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-15
 * <修改描述:>
 */
package com.tx.component.rule.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tx.component.rule.model.RuleTypeEnum;
import com.tx.component.rule.model.SimpleRuleParamEnum;



 /**
  * 规则属性参数业务层
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-3-15]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Component("simpleRulePropertyParamService")
public class SimpleRulePropertyParamService implements InitializingBean{
    
    private MultiValueMap<RuleTypeEnum, SimpleRuleParamEnum> paramMultiMap;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        paramMultiMap = new LinkedMultiValueMap<RuleTypeEnum, SimpleRuleParamEnum>();
        
        //将参数放入map中
        for(SimpleRuleParamEnum paramTemp : SimpleRuleParamEnum.values()){
            paramMultiMap.add(paramTemp.getRuleType(), paramTemp);
        }
    }

    /**
      * 根据规则的类型、获取规则对应的参数集
      * <功能详细描述>
      * @param ruleType
      * @return [参数说明]
      * 
      * @return List<SimpleRuleParamEnum> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<SimpleRuleParamEnum> queryParamsByRuleType(RuleTypeEnum ruleType){
        //如果尚未被装载，则先执行装载
        List<SimpleRuleParamEnum> resList = new ArrayList<SimpleRuleParamEnum>();
        
        //返回参数列表
        if(paramMultiMap.get(ruleType) != null){
            resList.addAll(paramMultiMap.get(ruleType));
        }
        RuleTypeEnum parentRuleType = ruleType.getParentType();
        while(parentRuleType != null){
            if(paramMultiMap.get(parentRuleType) != null){
                resList.addAll(paramMultiMap.get(parentRuleType));
            }
            parentRuleType = parentRuleType.getParentType();
        }
        
        return resList;
    } 
}
