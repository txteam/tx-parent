/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-19
 * <修改描述:>
 */
package com.tx.core.validator;


/**
 * 验证规则<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-12-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ValidateRule {
    
    /**
      * 验证器表达式<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String[] [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String[] validateExpression();
    
    /**
      * 根据验证规则表达式生成验证规则实例<br/>
      *<功能详细描述>
      * @param expression
      * @return [参数说明]
      * 
      * @return ValidateRule [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public ValidateRule newInstance(String expression);
    
    /**
      * 规则实例环绕 
      *<功能详细描述>
      * @param nextRule
      * @return [参数说明]
      * 
      * @return ValidateRule [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void wrap(ValidateRule nextValidateRule);
    
    /**
      * 验证信息 
      *<功能详细描述>
      * @param message
      * @param placeHolder
      * @return [参数说明]
      * 
      * @return ValidateResult [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public ValidateResult validate(Object message, String placeHolder);
}
