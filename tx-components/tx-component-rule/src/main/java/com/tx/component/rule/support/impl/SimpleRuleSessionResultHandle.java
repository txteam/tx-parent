/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-24
 * <修改描述:>
 */
package com.tx.component.rule.support.impl;

import com.tx.component.rule.support.RuleSessionResultHandle;


 /**
  * 简单的规则回话结果句柄实现
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-24]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class SimpleRuleSessionResultHandle<R> implements
        RuleSessionResultHandle<R> {

    private R value;
    
    /**
     * @return
     */
    @Override
    public R getValue() {
        return this.value;
    }

    /**
     * @param value
     */
    @Override
    public void setValue(R value) {
        this.value = value;
    }
}
