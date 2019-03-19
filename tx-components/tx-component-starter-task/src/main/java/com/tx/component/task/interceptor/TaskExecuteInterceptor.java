/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月29日
 * <修改描述:>
 */
package com.tx.component.task.interceptor;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.tx.component.task.context.TaskSessionContext;
import com.tx.component.task.delegate.TaskDelegateExecution;
import com.tx.component.task.model.TaskDef;
import com.tx.component.task.model.TaskExecuteLog;
import com.tx.component.task.model.TaskResultEnum;
import com.tx.component.task.model.TaskStatus;
import com.tx.component.task.model.TaskStatusEnum;
import com.tx.component.task.service.TaskDefService;
import com.tx.component.task.service.TaskExecuteLogService;
import com.tx.component.task.service.TaskStatusService;
import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 业务层拦截器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年11月29日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TaskExecuteInterceptor
        implements MethodInterceptor, InitializingBean {
    
    /** 日志记录器 */
    private Logger logger = LoggerFactory
            .getLogger(TaskExecuteInterceptor.class);
    
    /** 签名 */
    private String signature;
    
    /** 任务定义业务层 */
    private TaskDefService taskDefService;
    
    /** 任务状态业务层 */
    private TaskStatusService taskStatusService;
    
    /** 任务执行日志业务层 */
    private TaskExecuteLogService taskExecuteLogService;
    
    /** transactionManager */
    private PlatformTransactionManager transactionManager;
    
    /** 任务定义映射 */
    private Map<Method, TaskDef> taskDefMap;
    
    /** 事务句柄 */
    private TransactionTemplate transactionTemplate;
    
    /** 事务句柄 */
    private TransactionTemplate taskStatusTT;
    
    /** 事务句柄 */
    private TransactionTemplate taskExecuteLogTT;
    
    /** <默认构造函数> */
    public TaskExecuteInterceptor() {
        super();
    }
    
    /** <默认构造函数> */
    public TaskExecuteInterceptor(String signature,
            TaskDefService taskDefService, TaskStatusService taskStatusService,
            TaskExecuteLogService taskExecuteLogService,
            PlatformTransactionManager transactionManager,
            Map<Method, TaskDef> taskDefMap) {
        super();
        AssertUtils.notEmpty(signature, "signature is empty.");
        AssertUtils.notEmpty(taskDefMap, "taskDefMap is empty.");
        AssertUtils.notNull(taskDefService, "taskDefService is null.");
        AssertUtils.notNull(taskStatusService, "taskStatusService is null.");
        AssertUtils.notNull(taskExecuteLogService,
                "taskExecuteLogService is null.");
        AssertUtils.notNull(transactionManager, "transactionManager is null.");
        
        this.signature = signature;
        this.taskDefService = taskDefService;
        this.taskStatusService = taskStatusService;
        this.taskExecuteLogService = taskExecuteLogService;
        this.transactionManager = transactionManager;
        
        this.taskDefMap = taskDefMap;
        
        afterPropertiesSet();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {
        this.transactionTemplate = new TransactionTemplate(
                this.transactionManager, new DefaultTransactionDefinition(
                        TransactionDefinition.PROPAGATION_REQUIRED));
        
        this.taskStatusTT = new TransactionTemplate(this.transactionManager,
                new DefaultTransactionDefinition(
                        TransactionDefinition.PROPAGATION_REQUIRES_NEW));
        this.taskExecuteLogTT = new TransactionTemplate(this.transactionManager,
                new DefaultTransactionDefinition(
                        TransactionDefinition.PROPAGATION_REQUIRES_NEW));
        
        initializing();
    }
    
    /**
     * 初始化任务<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void initializing() {
        AssertUtils.notEmpty(this.taskDefMap, "taskDefMap is empty.");
        
        for (Entry<Method, TaskDef> entryTemp : this.taskDefMap.entrySet()) {
            //初始化任务定义
            TaskDef taskTemp = initTaskDef(entryTemp.getValue());
            this.taskDefMap.put(entryTemp.getKey(), taskTemp);
            
            //初始化任务状态
            initTaskStatus(taskTemp);
        }
    }
    
    /**
      * 获取任务定义<br/>
      * <功能详细描述>
      * @param method
      * @param beanName
      * @return [参数说明]
      * 
      * @return TaskDef [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private TaskDef initTaskDef(TaskDef taskDef) {
        AssertUtils.notNull(taskDef, "taskDef is null.");
        AssertUtils.notEmpty(taskDef.getCode(), "taskDef.code is empty.");
        
        TaskDef hisTaskDef = this.taskDefService.findByCode(taskDef.getCode());
        if (hisTaskDef != null) {
            if (!StringUtils.equals(taskDef.getModule(), hisTaskDef.getModule())
                    || !StringUtils.equals(taskDef.getParentCode(),
                            hisTaskDef.getParentCode())
                    || !StringUtils.equals(taskDef.getBeanName(),
                            hisTaskDef.getBeanName())
                    || !StringUtils.equals(taskDef.getClassName(),
                            hisTaskDef.getClassName())
                    || !StringUtils.equals(taskDef.getMethodName(),
                            hisTaskDef.getMethodName())
                    || !StringUtils.equals(taskDef.getName(),
                            hisTaskDef.getName())
                    || !StringUtils.equals(taskDef.getRemark(),
                            hisTaskDef.getRemark())
                    || taskDef.getOrderPriority() != hisTaskDef
                            .getOrderPriority()/* 任务优先级不等  */
                    || !taskDef.isValid() /* 任务无效  */) {
                //如果任务注解内容有所变化
                hisTaskDef.setModule(taskDef.getModule());
                hisTaskDef.setParentCode(taskDef.getParentCode());
                hisTaskDef.setName(taskDef.getName());
                hisTaskDef.setBeanName(taskDef.getBeanName());
                hisTaskDef.setClassName(taskDef.getClassName());
                hisTaskDef.setMethodName(taskDef.getMethodName());
                hisTaskDef.setRemark(taskDef.getRemark());
                hisTaskDef.setOrderPriority(taskDef.getOrderPriority());
                hisTaskDef.setExecutable(taskDef.isExecutable());
                hisTaskDef.setValid(taskDef.isValid());
                
                this.taskDefService.updateById(hisTaskDef);
            }
            return hisTaskDef;
        }
        
        //插入任务定义
        this.taskDefService.insert(taskDef);
        
        return taskDef;
    }
    
    /**
     * 初始化任务状态<br/>
     * <功能详细描述>
     * @param taskDef [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void initTaskStatus(TaskDef taskDef) {
        AssertUtils.notNull(taskDef, "taskDef is null.");
        AssertUtils.notEmpty(taskDef.getId(), "taskDef.id is empty.");
        
        Date now = new Date();
        
        String taskId = taskDef.getId();
        TaskStatus taskStatus = this.taskStatusService.findByTaskId(taskId);
        if (taskStatus != null) {
            if (StringUtils.isEmpty(taskStatus.getSignature())) {
                if (TaskStatusEnum.WAIT_EXECUTE
                        .equals(taskStatus.getStatus())) {
                    //如果任务签名为空，状态为待执行，则不用进行更新,
                    return;
                } else {
                    //如果签名为空，而状态非待执行，表示系统出现异常.此时应该抛出异常
                    AssertUtils.isTrue(
                            TaskStatusEnum.WAIT_EXECUTE
                                    .equals(taskStatus.getStatus()),
                            "taskId:{}.signature is empty.status should is 'WAIT_EXECUTE'.but status is :{}",
                            new Object[] { taskId, taskStatus.getStatus() });
                }
            } else if (!this.signature.equals(taskStatus.getSignature())) {
                //签名不一致，不能进行修改
                return;
            } else if (!StringUtils.isEmpty(taskStatus.getSignature())
                    && this.signature.equals(taskStatus.getSignature())
                    && TaskStatusEnum.EXECUTING
                            .equals(taskStatus.getStatus())) {
                //当前容器刚启动，如果发现状态为执行中，则认为上一次待执行的任务结果为未完成，状态更新为待执行
                taskStatus.setStatus(TaskStatusEnum.WAIT_EXECUTE);
                taskStatus.setResult(TaskResultEnum.UNCOMPLETED);
                taskStatus.setEndDate(now);
            }
            //如果有签名，则系统都需要將系统签名修改为空
            taskStatus.setLastUpdateDate(now);
            taskStatus.setSignature(null);//将签名更新为空
            this.taskStatusService.updateById(taskStatus);
            return;
        }
        
        taskStatus = new TaskStatus();
        taskStatus.setTaskId(taskDef.getId());
        taskStatus.setStatus(TaskStatusEnum.WAIT_EXECUTE);
        taskStatus.setResult(null);
        taskStatus.setCreateDate(now);
        taskStatus.setLastUpdateDate(now);
        
        this.taskStatusService.insert(taskStatus);
    }
    
    /**
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        Method m = invocation.getMethod();
        if (!this.taskDefMap.containsKey(m)) {
            //如果是非代理方法，则执行透传执行
            return invocation.proceed();
        }
        
        //如果是具有Task注解的方法:
        final TaskDef finalTask = this.taskDefMap.get(m);
        Object res = null;
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            res = doInvokeTask(invocation, finalTask);
        } else {
            res = this.transactionTemplate
                    .execute(new TransactionCallback<Object>() {
                        @Override
                        public Object doInTransaction(
                                TransactionStatus status) {
                            return doInvokeTask(invocation, finalTask);
                        }
                    });
        }
        return res;
    }
    
    /** 
     * 注入执行方法<br/>
     * <功能详细描述>
     * @param invocation
     * @return
     * @throws Throwable [参数说明]
     * 
     * @return Object [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private Object doInvokeTask(MethodInvocation invocation, TaskDef task) {
        String taskId = task.getId();
        
        //根据任务id添加行级锁
        TaskDef lockTask = this.taskDefService.findAndlockById(taskId);
        AssertUtils.notNull(lockTask, "lockTask is null.");
        
        //查询并锁定任务状态
        Date startDate = new Date();
        Map<String, Object> statusUpdateRowMap = new HashMap<>();
        statusUpdateRowMap.put("startDate", startDate);
        statusUpdateRowMap.put("endDate", null);
        statusUpdateRowMap.put("consuming", null);
        statusUpdateRowMap.put("result", TaskResultEnum.UNCOMPLETED);
        TaskStatus taskStatus = updateTaskStatusWithNewRequire(taskId,
                TaskStatusEnum.WAIT_EXECUTE,
                TaskStatusEnum.EXECUTING,
                this.signature,
                statusUpdateRowMap);
        
        boolean isSuccess = false;
        Object res = null;
        
        //事务开始执行,如果在开启期间出现异常，则不需要配对的关闭
        TaskSessionContext.open(task, taskStatus);
        try {
            //调度处理
            res = invocation.proceed();
            
            isSuccess = true;
        } catch (SILException e) {
            logger.error(e.getErrorMessage());
            
            isSuccess = false;
            throw e;
        } catch (Throwable e) {
            logger.error(e.getMessage());
            
            isSuccess = false;
            throw new SILException(e.getMessage(), e);
        } finally {
            TaskDelegateExecution execution = TaskSessionContext.close();
            AssertUtils.isTrue(task == execution.getTask(), "线程变量中的任务应该为同一对象.");
            AssertUtils.isTrue(taskStatus == execution.getTaskStatus(), "线程变量中的任务状态应该为同一对象.");
            
            Date nextFireDate = execution.getNextFireDate();
            String taskStatusAttributes = execution.getTaskStatusAttributes();
            
            Date endDate = new Date();
            long consuming = endDate.getTime() - startDate.getTime();
            statusUpdateRowMap.put("startDate", startDate);
            statusUpdateRowMap.put("endDate", endDate);
            statusUpdateRowMap.put("consuming", consuming);
            
            if(!execution.isSkip()){
                statusUpdateRowMap.put("result",
                        isSuccess ? TaskResultEnum.SUCCESS : TaskResultEnum.FAIL);//运行时结果
                statusUpdateRowMap.put("executeCount",
                        taskStatus.getExecuteCount() + 1);//执行次数+1
                statusUpdateRowMap.put("attributes", taskStatusAttributes);
                statusUpdateRowMap.put("nextFireDate", nextFireDate);
                
                if (isSuccess) {
                    statusUpdateRowMap.put("successStartDate", startDate);
                    statusUpdateRowMap.put("successEndDate", endDate);
                    statusUpdateRowMap.put("successConsuming", consuming);
                    statusUpdateRowMap.put("successCount",
                            taskStatus.getSuccessCount() + 1);
                } else {
                    statusUpdateRowMap.put("failStartDate", startDate);
                    statusUpdateRowMap.put("failEndDate", endDate);
                    statusUpdateRowMap.put("failConsuming", consuming);
                    statusUpdateRowMap.put("failCount",
                            taskStatus.getFailCount() + 1);
                }
            }else{
                statusUpdateRowMap.put("result",TaskResultEnum.UNNEED_EXECUTED);//运行时结果
            }
            
            //更新任务状态
            updateTaskStatusWithNewRequire(taskId,
                    TaskStatusEnum.EXECUTING,
                    TaskStatusEnum.WAIT_EXECUTE,
                    null,//将签名更新为空
                    statusUpdateRowMap);
            
            //记录执行日志
            logWithNewRequire(lockTask,
                    startDate,
                    endDate,
                    taskStatusAttributes,
                    isSuccess ? TaskResultEnum.SUCCESS : TaskResultEnum.FAIL,
                    this.signature);
        }
        return res;
    }
    
    /**
      * 独立事务更新任务状态<br/>
      * <功能详细描述>
      * @param taskId
      * @param sourceStatus
      * @param targetStatus
      * @param statusUpdateRowMap [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private TaskStatus updateTaskStatusWithNewRequire(final String taskId,
            final TaskStatusEnum sourceStatus,
            final TaskStatusEnum targetStatus, final String signature,
            final Map<String, Object> statusUpdateRowMap) {
        final Map<String, Object> finalStatusUpdateRowMap = statusUpdateRowMap == null
                ? new HashMap<String, Object>() : statusUpdateRowMap;
        final TaskStatusService finalTaskStatusService = this.taskStatusService;
        TaskStatus status = this.taskStatusTT
                .execute(new TransactionCallback<TaskStatus>() {
                    /**
                     * @param status
                     * @return
                     */
                    @Override
                    public TaskStatus doInTransaction(
                            TransactionStatus status) {
                        AssertUtils.notEmpty(taskId, "taskId is empty.");
                        AssertUtils.notNull(targetStatus,
                                "targetStatus is empty.");
                        
                        TaskStatus taskStatus = finalTaskStatusService
                                .findAndlockByTaskId(taskId);
                        //如果原状态不为空，则需要判断原状态是否和传入的一致(如果原状态为空，或，非空的原状 ==实际状态)
                        AssertUtils.isTrue(
                                sourceStatus == null || sourceStatus
                                        .equals(taskStatus.getStatus()),
                                "任务状态应为待执行.taskId:{} ; taskStatus.id:{} ; sourceStatus:{} ; taskStatus.status:{}",
                                new Object[] { taskStatus.getTaskId(),
                                        taskStatus.getId(), sourceStatus,
                                        taskStatus.getStatus() });
                        
                        BeanWrapper taskStatusBW = PropertyAccessorFactory
                                .forBeanPropertyAccess(taskStatus);
                        for (Entry<String, Object> entryTemp : finalStatusUpdateRowMap
                                .entrySet()) {
                            String key = entryTemp.getKey();
                            Object value = entryTemp.getValue();
                            if ("id".equals(key)) {
                                continue;
                            }
                            if (!taskStatusBW.isWritableProperty(key)) {
                                continue;
                            }
                            taskStatusBW.setPropertyValue(key, value);
                        }
                        taskStatus.setStatus(targetStatus);
                        taskStatus.setSignature(signature);
                        
                        finalTaskStatusService.updateById(taskStatus);
                        return taskStatus;
                    }
                    
                });
        return status;
    }
    
    /**
     * 记录任务执行日志<br/>
     * <功能详细描述>
     * @param taskDef
     * @param startDate
     * @param endDate
     * @param jobDataMap
     * @param result
     * @param signature [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void logWithNewRequire(final TaskDef taskDef, final Date startDate,
            final Date endDate, final String taskStatusAttributes,
            final TaskResultEnum result, final String signature) {
        final TaskExecuteLogService finalTaskExecuteLogService = this.taskExecuteLogService;
        try {
            this.taskExecuteLogTT
                    .execute(new TransactionCallbackWithoutResult() {
                        
                        @Override
                        protected void doInTransactionWithoutResult(
                                TransactionStatus status) {
                            TaskExecuteLog log = new TaskExecuteLog();
                            log.setTaskId(taskDef.getId());
                            log.setCode(taskDef.getCode());
                            log.setName(taskDef.getName());
                            log.setRemark(taskDef.getRemark());
                            
                            log.setStartDate(startDate);
                            log.setEndDate(endDate);
                            log.setConsuming(
                                    endDate.getTime() - endDate.getTime());
                            log.setAttributes(taskStatusAttributes);
                            log.setResult(result);
                            log.setSignature(signature);
                            
                            finalTaskExecuteLogService.insert(log);
                        }
                    });
        } catch (TransactionException e) {
            logger.error("log error.errorMessage:" + e.getMessage(), e);
        }
    }
    
    /**
     * @param 对taskDefService进行赋值
     */
    public void setTaskDefService(TaskDefService taskDefService) {
        this.taskDefService = taskDefService;
    }
    
    /**
     * @param 对taskStatusService进行赋值
     */
    public void setTaskStatusService(TaskStatusService taskStatusService) {
        this.taskStatusService = taskStatusService;
    }
    
    /**
     * @param 对signature进行赋值
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }
    
    /**
     * @param 对taskExecuteLogService进行赋值
     */
    public void setTaskExecuteLogService(
            TaskExecuteLogService taskExecuteLogService) {
        this.taskExecuteLogService = taskExecuteLogService;
    }
    
    /**
     * @param 对transactionManager进行赋值
     */
    public void setTransactionManager(
            PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    
    /**
     * @param 对taskDefMap进行赋值
     */
    public void setTaskDefMap(Map<Method, TaskDef> taskDefMap) {
        this.taskDefMap = taskDefMap;
    }
    
    public static void main(String[] args) {
        Map<String, Object> testMap = new HashMap<>();
        testMap.put("createDate", new Date());
        
        String json = JSONObject.toJSONString(testMap);
        System.out.println(json);
        
        Object createDate = JSONObject.parseObject(json).get("createDate");
        System.out.println(createDate);
    }
}
