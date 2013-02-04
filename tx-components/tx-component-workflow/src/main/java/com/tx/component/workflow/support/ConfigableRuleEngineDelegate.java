/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-4
 * <修改描述:>
 */
package com.tx.component.workflow.support;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;


 /**
  * 可配置的规则引擎代理
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-2-4]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Component("configableRuleEngineDelegate")
public class ConfigableRuleEngineDelegate implements JavaDelegate {

    /**
     * @param execution
     * @throws Exception
     */
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("test");
    }
    
    
}
