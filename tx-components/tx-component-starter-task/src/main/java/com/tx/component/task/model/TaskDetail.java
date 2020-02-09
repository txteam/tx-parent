/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年9月18日
 * <修改描述:>
 */
package com.tx.component.task.model;

/**
 * 任务详情<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年9月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TaskDetail {
    
    /** 任务 */
    private TaskDef task;
    
    /** 任务状态 */
    private TaskStatus status;
    
    /**
     * @return 返回 task
     */
    public TaskDef getTask() {
        return task;
    }
    
    /**
     * @param 对task进行赋值
     */
    public void setTask(TaskDef task) {
        this.task = task;
    }
    
    /**
     * @return 返回 status
     */
    public TaskStatus getStatus() {
        return status;
    }
    
    /**
     * @param 对status进行赋值
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
