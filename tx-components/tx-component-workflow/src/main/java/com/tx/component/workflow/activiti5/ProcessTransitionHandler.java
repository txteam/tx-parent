/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-8
 * <修改描述:>
 */
package com.tx.component.workflow.activiti5;

import org.activiti.engine.delegate.DelegateExecution;

/**
 * 根据操作名扭转工作流
 * 
 * @author  brady
 * @version  [版本号, 2013-3-8]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ProcessTransitionHandler {
    
    /**
      * 是否选择对应线路<br/>
      *     1、用以动态判断工作流流转条件<br/>
      * <功能详细描述>
      * @param execution
      * @param variables
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean handle(DelegateExecution execution,String processName){
        System.out.println(processName);
        return false;
    }
}
