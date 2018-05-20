/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月3日
 * <修改描述:>
 */
package com.tx.component.task.delegate;

import com.tx.component.task.model.TaskDef;
import com.tx.component.task.model.TaskStatus;

/**
 * 任务委托执行环境<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface TaskDelegateExecution {
    
    /**
     * 获取任务id<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getTaskId();
    
    /**
     * 获取任务定义<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TaskDef [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    TaskDef getTask();
    
    /**
     * 任务状态<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TaskStatus [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    TaskStatus getTaskStatus();
}
