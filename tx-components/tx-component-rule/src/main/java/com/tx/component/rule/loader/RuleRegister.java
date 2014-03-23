/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-14
 * <修改描述:>
 */
package com.tx.component.rule.loader;

import java.util.Set;

import com.tx.component.rule.context.Rule;
import com.tx.component.rule.session.RuleSession;

/**
 * 规则注册器
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-3-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RuleRegister<R extends Rule>{
    
    /**
      * 规则注册器对应规则类型
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Class<R> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    RuleTypeEnum ruleType();
    
    /**
      * 获取对应规则项参数集合<br/> 
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return Set<RuleItemParam> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Set<RuleItemParam> ruleItemParam();
    
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
    R registe(RuleItem ruleItem);
    
    /**
      * 根据规则实例创建规则会话实例<br/> 
      *<功能详细描述>
      * @param rule
      * @return [参数说明]
      * 
      * @return RuleSession [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    RuleSession buildRuleSession(R rule);
}
