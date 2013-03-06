/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-5
 * <修改描述:>
 */
package com.tx.component.workflow.model.impl;

import org.activiti.engine.impl.pvm.process.ActivityImpl;

import com.tx.component.workflow.model.ProTaskDefinition;


 /**
  * 流程环节定义实例
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-3-5]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ActivitiProTaskDefinition implements ProTaskDefinition{

    private ActivityImpl activityImpl;
    
    /**
     * <默认构造函数>
     */
    public ActivitiProTaskDefinition(ActivityImpl activityImpl) {
        super();
        this.activityImpl = activityImpl;
    }

    /**
     * @return
     */
    @Override
    public String getName() {
        return (String)activityImpl.getProperty("name");
    }

    /**
     * @return
     */
    @Override
    public String getDescription() {
        return (String)activityImpl.getProperty("description");
    }

    /**
     * @return
     */
    @Override
    public String getProcessDefinitionId() {
        return activityImpl.getProcessDefinition().getId();
    }

    /**
     * @return
     */
    @Override
    public String getTaskDefinitionKey() {
        return activityImpl.getId();
    }
}
