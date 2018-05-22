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
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import com.tx.component.task.annotations.Task;
import com.tx.component.task.model.TaskDef;
import com.tx.component.task.timedtask.TimedTask;
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
    
    private ApplicationContext applicationContext;
    
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
    public TaskExecuteInterceptor newInterceptor(Map<Method, TaskDef> method2taskMap) {
        AssertUtils.notEmpty(method2taskMap, "method2taskMap is empty.");
        
        Map<Method, TaskDef> taskDefMap = new HashMap<>();
        TaskDef taskDef = new TaskDef();
        
        taskDef.setBeanName(beanName);
        taskDef.setClassName(className);
        taskDef.setMethodName(method.getName());
        
        taskDef.setCode(timedTask.getCode());
        taskDef.setParentCode(timedTask.getParentCode());
        taskDef.setName(timedTask.getName());
        taskDef.setRemark(timedTask.getRemark());
        taskDef.setOrderPriority(timedTask.getOrder());
        //如果为无参构造函数，并且没有父级任务，则可执行
        //如果有parentCode，则不能执行，//如果参数数量 == 0，则可执行，//如果参数数量 > 0,则不可执行
        taskDef.setValid(true);
        taskDef.setExecutable(StringUtils.isEmpty(timedTask.getParentCode())
                && ArrayUtils.isEmpty(method.getParameterTypes()));
        taskDefMap.put(method, taskDef);
        
        TaskExecuteInterceptor interceptor = (TaskExecuteInterceptor) this.applicationContext
                .getBean("taskContext.taskExecuteInterceptor");
        interceptor.setTaskDefMap(taskDefMap);
        interceptor.initializing();
        
        return interceptor;
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
