/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-3-3
 * <修改描述:>
 */
package com.tx.component.workflow.model.impl;

import org.activiti.engine.repository.ProcessDefinition;

/**
 * 流程定义实例的activiti实现
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-3-3]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ActivitiProcessDefinition implements
        com.tx.component.workflow.model.ProcessDefinition {
    
    /** 注释内容 */
    private static final long serialVersionUID = -9128041376553836296L;
    
    /**
     * 流程定义
     */
    private ProcessDefinition activitiProcessDefDelegate;
    
    /**
     * <默认构造函数>
     */
    public ActivitiProcessDefinition(
            org.activiti.engine.repository.ProcessDefinition activitiProcessDefDelegate) {
        super();
        this.activitiProcessDefDelegate = activitiProcessDefDelegate;
    }
    
    /**
     * @return
     */
    @Override
    public String getId() {
        return activitiProcessDefDelegate.getId();
    }
    
    /**
     * @return
     */
    @Override
    public String getCategory() {
        return activitiProcessDefDelegate.getCategory();
    }
    
    /**
     * @return
     */
    @Override
    public String getName() {
        return activitiProcessDefDelegate.getName();
    }
    
    /**
     * @return
     */
    @Override
    public String getKey() {
        return activitiProcessDefDelegate.getKey();
    }
    
    /**
     * @return
     */
    @Override
    public String getDescription() {
        return activitiProcessDefDelegate.getDescription();
    }
    
    /**
     * @return
     */
    @Override
    public int getVersion() {
        return activitiProcessDefDelegate.getVersion();
    }
    
    /**
     * @return
     */
    @Override
    public String getResourceName() {
        return activitiProcessDefDelegate.getResourceName();
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSuspended() {
        return activitiProcessDefDelegate.isSuspended();
    }
    
}
