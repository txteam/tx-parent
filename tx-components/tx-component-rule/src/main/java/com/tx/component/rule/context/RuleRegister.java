/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-14
 * <修改描述:>
 */
package com.tx.component.rule.context;

import org.springframework.core.Ordered;

import com.tx.component.rule.model.Rule;
import com.tx.component.rule.model.RuleTypeEnum;
import com.tx.component.rule.model.SimplePersistenceRule;


 /**
  * 规则验证器接口
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-3-14]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface RuleRegister<R extends Rule> extends Ordered{
    
    /**
      * 验证类型<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Class<R> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    RuleTypeEnum ruleType();
    
    /**
      * 验证规则合法性<br/>
      *     1、如果不合法在该方法中设置规则状态为error态<br/>
      * <功能详细描述>
      * @param rule [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    void validate(Rule rule);
    
    /**
      * 向容器中注册规则实体
      *<功能详细描述>
      * @param rule
      * @return [参数说明]
      * 
      * @return Rule [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Rule registe(SimplePersistenceRule rule);
}
