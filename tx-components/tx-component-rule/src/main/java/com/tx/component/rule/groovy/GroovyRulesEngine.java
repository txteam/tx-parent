/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-21
 * <修改描述:>
 */
package com.tx.component.rule.groovy;

import java.util.List;

import org.springframework.core.io.Resource;


 /**
  * 用以支持groovy规则引擎
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-21]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class GroovyRulesEngine {
    
    /** 基础规则库，需要在初始化加载其他规则前，提前加载基础规则库 */
    private List<Resource> baseLibRuleLocations;
    
    /**
     * 启动时，启动drools引擎，并加载相关规则
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public void initEngine(List<?> rules){
       
   }
}
