/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-24
 * <修改描述:>
 */
package com.tx.component.rule.support;

import com.tx.component.rule.model.RuleSessionInitContext;


 /**
  * 规则回话执行回调函数<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-24]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface RuleSessionCallback<T> {
    
    public void init(RuleSessionInitContext<T> context);
    
    
    
    public void colse();
}
