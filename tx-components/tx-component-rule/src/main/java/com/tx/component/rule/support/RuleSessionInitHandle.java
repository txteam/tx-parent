/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-23
 * <修改描述:>
 */
package com.tx.component.rule.support;


 /**
  * 规则运行时环境句柄<br/>
  *     用以支持规则环境初始化<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-23]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface RuleSessionInitHandle<FACT_TYPE>{
    
    /**
      * 规则回话容器初始化<br/>
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void initContext(RuleSessionInitContext<FACT_TYPE> context);
}
