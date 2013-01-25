/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-24
 * <修改描述:>
 */
package com.tx.component.rule.support.impl;

import java.util.Map;

import com.tx.component.rule.support.RuleSession;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-24]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class DefaultRuleSession implements RuleSession {
    
    /**
     * @return
     */
    @Override
    public String rule() {
        return null;
    }
    
    
    
    /**
     * @param globals
     */
    @Override
    public void start(Map<String, Object> globals) {
        // TODO Auto-generated method stub
        
    }

    /**
     * @param fact
     */
    @Override
    public void execute(Map<String, Object> fact) {
        // TODO Auto-generated method stub
        
    }



    /**
     * 
     */
    @Override
    public void close() {
        // TODO Auto-generated method stub
        
    }
    
}
