/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-23
 * <修改描述:>
 */
package com.tx.component.rule.model;

/**
  * 规则回话结果句柄<br/>
  *     用以保存提取规则会话期间产生的结果值<br/>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-24]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public interface RuleSessionResultHandle<R> {
    
    /**
      * 获取规则回话结果值(可能为推论结果或符合规则的事实)<br/>
      * @return [参数说明]
      * 
      * @return R [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    R getValue();
    
    /**
      * 设置规则回话结果值(可能为推论结果或符合规则的事实)<br/>
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    void setValue(R value);
}