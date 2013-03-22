/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-22
 * <修改描述:>
 */
package com.tx.component.rule.support;

import org.springframework.transaction.support.ResourceHolder;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-3-22]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class RuleSessionContextHolder implements ResourceHolder {
    
    /**
     * 
     */
    @Override
    public void reset() {
        
    }
    
    /**
     * 
     */
    @Override
    public void unbound() {
        
    }
    
    /**
     * @return
     */
    @Override
    public boolean isVoid() {
        return false;
    }
    
}
