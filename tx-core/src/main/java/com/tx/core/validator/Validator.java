/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-19
 * <修改描述:>
 */
package com.tx.core.validator;


/**
 * 验证器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-12-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface Validator {
    
    /**
      * 根据规则校验信息<br/>
      *<功能详细描述>
      * @param value
      * @param ruleExpression
      * @return [参数说明]
      * 
      * @return ValidateResult [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public ValidateResult validate(Object value, String ruleExpression);
    
    /**
      * 根据规则校验信息<br/>
      *<功能详细描述>
      * @param value
      * @param ruleExpression
      * @param placeholder
      * @return [参数说明]
      * 
      * @return ValidateResult [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public ValidateResult validate(Object value, String ruleExpression,
            String placeholder);
}
