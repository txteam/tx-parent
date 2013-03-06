/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-5
 * <修改描述:>
 */
package com.tx.component.workflow.model.impl;

import org.activiti.engine.runtime.ProcessInstance;


 /**
  * activiti流程实例bean的实现<br/>
  *     用以屏蔽流程引擎内部实现 <br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-3-5]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ActivitiProcessInstance implements com.tx.component.workflow.model.ProcessInstance {
    
    private ProcessInstance processInstance;

    /**
     * <默认构造函数>
     */
    public ActivitiProcessInstance(ProcessInstance processInstance) {
        super();
        this.processInstance = processInstance;
    }

    /**
     * @return
     */
    @Override
    public String getId() {
        return processInstance.getId();
    }



    /**
     * @return
     */
    @Override
    public String getProcessDefinitionId() {
        return processInstance.getId();
    }

    /**
     * @return
     */
    @Override
    public String getBusinessKey() {
        return processInstance.getBusinessKey();
    }

    /**
     * @return
     */
    @Override
    public boolean isSuspended() {
        return processInstance.isSuspended();
    }
    
}
