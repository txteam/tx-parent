/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年3月16日
 * <修改描述:>
 */
package com.tx.component.task.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.task.model.TaskDef;
import com.tx.component.task.service.TaskDefService;
import com.tx.component.task.service.TaskExecuteLogService;
import com.tx.component.task.service.TaskStatusService;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 任务执行拦截器工厂<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年3月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TaskExecuteInterceptorFactory implements ApplicationContextAware {
    
    /** spring 容器句柄 */
    protected ApplicationContext applicationContext;
    
    /** 当前机器的签名 */
    private String signature;
    
    /** 任务定义业务层 */
    private TaskDefService taskDefService;
    
    /** 任务状态业务层 */
    private TaskStatusService taskStatusService;
    
    /** 任务执行日志业务层 */
    private TaskExecuteLogService taskExecuteLogService;
    
    /** transactionManager */
    private PlatformTransactionManager transactionManager;
    
    /** <默认构造函数> */
    public TaskExecuteInterceptorFactory() {
        super();
    }
    
    /** <默认构造函数> */
    public TaskExecuteInterceptorFactory(String signature,
            TaskDefService taskDefService, TaskStatusService taskStatusService,
            TaskExecuteLogService taskExecuteLogService,
            PlatformTransactionManager transactionManager) {
        super();
        this.signature = signature;
        this.taskDefService = taskDefService;
        this.taskStatusService = taskStatusService;
        this.taskExecuteLogService = taskExecuteLogService;
        this.transactionManager = transactionManager;
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /**
     * 构建拦截器<br/>
     * <功能详细描述>
     * @param beanName
     * @param type
     * @param method
     * @param code
     * @param parentCode
     * @param name
     * @param remark
     * @param orderPriority
     * @return [参数说明]
     * 
     * @return TaskExecuteInterceptor [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public TaskExecuteInterceptor newInterceptor(
            Map<Method, TaskDef> method2taskMap) {
        AssertUtils.notEmpty(method2taskMap, "method2taskMap is empty.");
        
        Map<Method, TaskDef> taskDefMap = new HashMap<>();
        
        TaskExecuteInterceptor interceptor = new TaskExecuteInterceptor(
                signature, taskDefService, taskStatusService,
                taskExecuteLogService, transactionManager, taskDefMap);
        
        return interceptor;
    }
    
    /**
     * @param 对signature进行赋值
     */
    public void setSignature(String signature) {
        this.signature = signature;
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
    
    //    /**
    //     * 创建拦截器实例<br/>
    //     * <功能详细描述>
    //     * @param beanName
    //     * @param methodSet
    //     * @return [参数说明]
    //     * 
    //     * @return TaskExecuteInterceptor [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public TaskExecuteInterceptor newInterceptor(String beanName, String className, Set<Method> methodSet) {
    //        AssertUtils.notEmpty(beanName, "beanName is empty.");
    //        AssertUtils.notEmpty(className, "className is empty.");
    //        AssertUtils.notEmpty(methodSet, "methodSet is empty.");
    //        
    //        Map<Method, TaskDef> taskDefMap = new HashMap<>();
    //        for (Method methodTemp : methodSet) {
    //            TaskDef taskDef = new TaskDef();
    //            
    //            taskDef.setBeanName(beanName);
    //            taskDef.setClassName(className);
    //            taskDef.setMethodName(methodTemp.getName());
    //            
    //            Task taskAnnotation = AnnotationUtils.getAnnotation(methodTemp, Task.class);
    //            AssertUtils.notNull(taskAnnotation, "taskAnnotation is null.");
    //            taskDef.setCode(taskAnnotation.code());
    //            taskDef.setParentCode(taskAnnotation.parentCode());
    //            taskDef.setName(taskAnnotation.name());
    //            taskDef.setRemark(taskAnnotation.remark());
    //            taskDef.setOrderPriority(taskAnnotation.order());
    //            //如果为无参构造函数，并且没有父级任务，则可执行
    //            //如果有parentCode，则不能执行，//如果参数数量 == 0，则可执行，//如果参数数量 > 0,则不可执行
    //            taskDef.setValid(true);
    //            taskDef.setExecutable(StringUtils.isEmpty(taskAnnotation.parentCode())
    //                    && ArrayUtils.isEmpty(methodTemp.getParameterTypes()));
    //            
    //            taskDefMap.put(methodTemp, taskDef);
    //        }
    //        
    //        TaskExecuteInterceptor interceptor = (TaskExecuteInterceptor) this.applicationContext
    //                .getBean("taskContext.taskExecuteInterceptor");
    //        interceptor.setTaskDefMap(taskDefMap);
    //        interceptor.initializing();
    //        
    //        return interceptor;
    //    }
    //    
    //    /**
    //     * 构建拦截器<br/>
    //     * <功能详细描述>
    //     * @param beanName
    //     * @param type
    //     * @param method
    //     * @param code
    //     * @param parentCode
    //     * @param name
    //     * @param remark
    //     * @param orderPriority
    //     * @return [参数说明]
    //     * 
    //     * @return TaskExecuteInterceptor [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public TaskExecuteInterceptor newInterceptor(String beanName, String className, Method method,
    //            TimedTask timedTask) {
    //        AssertUtils.notEmpty(beanName, "beanName is empty.");
    //        AssertUtils.notEmpty(className, "className is empty.");
    //        AssertUtils.notNull(method, "method is empty.");
    //        AssertUtils.notNull(timedTask, "timedTask is empty.");
    //        
    //        Map<Method, TaskDef> taskDefMap = new HashMap<>();
    //        TaskDef taskDef = new TaskDef();
    //        
    //        taskDef.setBeanName(beanName);
    //        taskDef.setClassName(className);
    //        taskDef.setMethodName(method.getName());
    //        
    //        taskDef.setCode(timedTask.getCode());
    //        taskDef.setParentCode(timedTask.getParentCode());
    //        taskDef.setName(timedTask.getName());
    //        taskDef.setRemark(timedTask.getRemark());
    //        taskDef.setOrderPriority(timedTask.getOrder());
    //        //如果为无参构造函数，并且没有父级任务，则可执行
    //        //如果有parentCode，则不能执行，//如果参数数量 == 0，则可执行，//如果参数数量 > 0,则不可执行
    //        taskDef.setValid(true);
    //        taskDef.setExecutable(
    //                StringUtils.isEmpty(timedTask.getParentCode()) && ArrayUtils.isEmpty(method.getParameterTypes()));
    //        taskDefMap.put(method, taskDef);
    //        
    //        TaskExecuteInterceptor interceptor = (TaskExecuteInterceptor) this.applicationContext
    //                .getBean("taskContext.taskExecuteInterceptor");
    //        interceptor.setTaskDefMap(taskDefMap);
    //        interceptor.initializing();
    //        
    //        return interceptor;
    //    }
}
