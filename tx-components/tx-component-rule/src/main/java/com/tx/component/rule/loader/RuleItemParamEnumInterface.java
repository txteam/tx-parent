/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月17日
 * <修改描述:>
 */
package com.tx.component.rule.loader;

import com.tx.component.rule.context.Rule;


 /**
  * 规则项目参数枚举型基础接口<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年3月17日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface RuleItemParamEnumInterface<R extends Rule>{
    
    /**
      * 规则项参数类型
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return RuleItemParamTypeEnum [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public RuleItemParamTypeEnum getType();
    
    /**
      * 对应规则项参数是否为多值<br/> 
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isMultiple();
    
    /**
      * 对应参数项是否必填<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isRequired();
    
    /**
      * 获取验证表达式<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getValidateExpression();
    
    /**
      * 获取验证错误消息<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getErrorMessage();
    

}
