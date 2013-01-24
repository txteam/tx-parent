/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-23
 * <修改描述:>
 */
package com.tx.component.rule.exceptions;

import com.tx.core.exceptions.ErrorCodeConstant;


 /**
  * 规则模块错误号常量
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-23]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface RuleErrorCodeConstant extends ErrorCodeConstant{
    
    /** 参数异常 第四个封装模块 所以是 rule-004 */
    String RULE_EXCEPTION = "RULE-004-000-000-000";
}
