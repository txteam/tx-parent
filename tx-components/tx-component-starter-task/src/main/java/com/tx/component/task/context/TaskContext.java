/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月23日
 * <修改描述:>
 */
package com.tx.component.task.context;

import com.tx.component.task.TaskConstants;
import com.tx.component.task.service.TaskDefService;
import com.tx.component.task.service.TaskDetailService;
import com.tx.component.task.service.TaskExecuteLogService;
import com.tx.component.task.service.TaskStatusService;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 获取任务执行容器<br/>
 * @author  PengQY
 * @version  [版本号, 2017年3月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TaskContext extends TaskContextBuilder {
    
    /** 交易容器 */
    protected static TaskContext context;
    
    /** 任务容器注册表 */
    private TaskContextRegistry taskContextRegistry;
    
    /** 任务定义业务层 */
    private TaskDefService taskDefService;
    
    /** 任务状态业务层 */
    private TaskStatusService taskStatusService;
    
    /** 任务详情业务层 */
    private TaskDetailService taskDetailService;
    
    /** 任务执行日志业务层 */
    private TaskExecuteLogService taskExecuteLogService;
    
    /**
     * @return 返回 applynoteContext
     */
    public static TaskContext getContext() {
        if (TaskContext.context != null) {
            return context;
        }
        synchronized (TaskContext.class) {
            TaskContext.context = (TaskContext) applicationContext.getBean(beanName);
        }
        
        AssertUtils.notNull(context, "context is null.maybe not inited.");
        return context;
    }
    
    /**
     * 获取任务容器签名<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getSignature() {
        return this.signature;
    }
    
    /**
     * 任务容器注册表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TaskContextRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public TaskContextRegistry getTaskContextRegistry() {
        if (this.taskContextRegistry == null) {
            this.taskContextRegistry = TaskContext.applicationContext
                    .getBean(TaskConstants.BEAN_NAME_TASK_CONTEXT_REGISTRY, TaskContextRegistry.class);
        }
        return this.taskContextRegistry;
    }
    
    /**
     * 任务定义业务层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TaskContextRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public TaskDefService getTaskDefService() {
        if (this.taskDefService == null) {
            this.taskDefService = TaskContext.applicationContext.getBean(TaskConstants.BEAN_NAME_TASK_DEF_SERVICE,
                    TaskDefService.class);
        }
        return this.taskDefService;
    }
    
    /**
     * 任务定义业务层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TaskContextRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public TaskStatusService getTaskStatusService() {
        if (this.taskStatusService == null) {
            this.taskStatusService = TaskContext.applicationContext.getBean(TaskConstants.BEAN_NAME_TASK_STATUS_SERVICE,
                    TaskStatusService.class);
        }
        return this.taskStatusService;
    }
    
    /**
     * 任务定义业务层<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TaskContextRegistry [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public TaskDetailService getTaskDetailService() {
        if (this.taskDetailService == null) {
            this.taskDetailService = TaskContext.applicationContext.getBean(TaskConstants.BEAN_NAME_TASK_DETAIL_SERVICE,
                    TaskDetailService.class);
        }
        return this.taskDetailService;
    }
    
    /**
     * 任务执行日志业务层<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return TaskExecuteLogService [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public TaskExecuteLogService getTaskExecuteLogService() {
        if (this.taskExecuteLogService == null) {
            this.taskExecuteLogService = TaskContext.applicationContext
                    .getBean(TaskConstants.BEAN_NAME_TASK_EXECUTE_LOG_SERVICE, TaskExecuteLogService.class);
        }
        return this.taskExecuteLogService;
    }
}
