/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-4
 * <修改描述:>
 */
package com.tx.component.workflow.model;


 /**
  * 流程实例接口<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-3-4]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface ProcessIns {
    
    /**
     * The id of the process definition of the process instance.
     */
    String getProcessDefinitionId();
    
    /**
     * The business key of this process instance.
     */
    String getBusinessKey();
    
    /**
     * returns true if the process instance is suspended
     */
    boolean isSuspended();
}
