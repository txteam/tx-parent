/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-3-4
 * <修改描述:>
 */
package com.tx.component.workflow.model.impl;

import java.util.Date;

import org.activiti.engine.task.Task;

import com.tx.component.workflow.model.ProTaskInstance;


 /**
  * 任务环节实例activiti的实现
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2013-3-4]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ActivitiProTaskInstance implements ProTaskInstance {
    
    /** 注释内容 */
    private static final long serialVersionUID = -5200062162337090431L;
    /**
     * 流程定义
     */
    private Task activitiTaskDelegate;
    
    /** <默认构造函数> */
    public ActivitiProTaskInstance(Task activitiTaskDelegate) {
        this.activitiTaskDelegate = activitiTaskDelegate;
    }
    
    /**
     * @return
     */
    @Override
    public String getId() {
        return this.activitiTaskDelegate.getId();
    }
    
    /**
     * @return
     */
    @Override
    public String getName() {
        return this.activitiTaskDelegate.getName();
    }
    
    /**
     * @return
     */
    @Override
    public String getDescription() {
        return this.activitiTaskDelegate.getDescription();
    }
    
    /**
     * @return
     */
    @Override
    public String getProcessInstanceId() {
        return this.activitiTaskDelegate.getProcessInstanceId();
    }
    
    /**
     * @return
     */
    @Override
    public String getExecutionId() {
        return this.activitiTaskDelegate.getExecutionId();
    }
    
    /**
     * @return
     */
    @Override
    public String getProcessDefinitionId() {
        return this.activitiTaskDelegate.getProcessDefinitionId();
    }
    
    /**
     * @return
     */
    @Override
    public Date getCreateTime() {
        return this.activitiTaskDelegate.getCreateTime();
    }
    
    /**
     * @return
     */
    @Override
    public String getTaskDefinitionKey() {
        return this.activitiTaskDelegate.getTaskDefinitionKey();
    }
    
    /**
     * @return
     */
    @Override
    public String getParentTaskId() {
        return this.activitiTaskDelegate.getParentTaskId();
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSuspended() {
        return this.activitiTaskDelegate.isSuspended();
    }
    
}
