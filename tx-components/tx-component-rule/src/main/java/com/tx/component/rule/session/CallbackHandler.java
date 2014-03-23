/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-23
 * <修改描述:>
 */
package com.tx.component.rule.session;


/**
  * 规则回话结果句柄<br/>
  *     用以保存提取规则会话期间产生的结果值<br/>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-24]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public interface CallbackHandler<R> extends ValueWrapper<R>{
    
    /**
      * 获取实际结果值的具体类型<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return Class<?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Class<?> getRowType();
}