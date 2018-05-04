/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年5月15日
 * <修改描述:>
 */
package com.tx.component.task;

/**
 * 任务容器<br/>
 *     作用：
 *         1．监控任务，记录任务执行信息.
 *         2．避免任务重复执行.
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年5月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface TaskConstants {
    
    /** 下次执行时间关键字 */
    String KEY_NEXT_EXECUTE_DATE = "NEXT_EXECUTE_DATE";
    
    String BEAN_NAME_TASK_DEF_DAO = "taskContext.taskDefDao";
    
    String BEAN_NAME_TASK_DEF_SERVICE = "taskContext.taskDefService";
    
    String BEAN_NAME_TASK_STATUS_DAO = "taskContext.taskStatusDao";
    
    String BEAN_NAME_TASK_STATUS_SERVICE = "taskContext.taskStatusService";
    
    String BEAN_NAME_TASK_DETAIL_DAO = "taskContext.taskDetailDao";
    
    String BEAN_NAME_TASK_DETAIL_SERVICE = "taskContext.taskDetailService";
    
    String BEAN_NAME_TASK_EXECUTE_LOG_DAO = "taskContext.taskExecuteLogDao";
    
    String BEAN_NAME_TASK_EXECUTE_LOG_SERVICE = "taskContext.taskExecuteLogService";
    
    String BEAN_NAME_TASK_CONTEXT_REGISTRY = "taskContext.taskContextRegistry";
}
