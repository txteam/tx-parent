/*
 ，
 
  * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年10月15日
 * <修改描述:>
 */
package com.tx.component.task.timedtask;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.task.annotations.Task;
import com.tx.component.task.context.TaskContext;
import com.tx.component.task.context.TaskSessionContext;
import com.tx.component.task.interfaces.TaskExecutor;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 任务执行器的的抽象类实现
 * <功能详细描述>
 * 
 * @author  Tim.PengQY
 * @version  [版本号, 2017年10月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractTimedTaskExecutor<TASK extends TimedTask>
        implements TaskExecutor {
    
    /** 日志记录器 */
    protected Logger logger = LoggerFactory.getLogger(TaskContext.class);
    
    /** beanName */
    protected String taskBeanName;
    
    /** 任务实例 */
    protected TASK task;
    
    /** 事务manager */
    protected PlatformTransactionManager transactionManager;
    
    /** <默认构造函数> */
    public AbstractTimedTaskExecutor() {
        super();
    }
    
    /** <默认构造函数> */
    public AbstractTimedTaskExecutor(String taskBeanName, TASK task,
            PlatformTransactionManager transactionManager) {
        super();
        AssertUtils.notEmpty(taskBeanName, "taskBeanName is empty.");
        AssertUtils.notNull(task, "task is null.");
        AssertUtils.notNull(transactionManager, "transactionManager is null.");
        
        this.taskBeanName = taskBeanName;
        this.task = task;
        this.transactionManager = transactionManager;
    }
    
    /**
     * @param executeDate
     * @return
     */
    @Task
    public void execute(final Object... args) {
        AssertUtils.notNull(this.task, "task is null.");
        
        //任务执行以后，并返回下次执行时间
        Date nextFireDate = doExecute(args);
        
        //设置下次执行时间
        TaskSessionContext.getExecution().setNextFireDate(nextFireDate);
    }
    
    /** 子类的任务执行 */
    protected abstract Date doExecute(Object... args);
    
    /**
     * @return
     */
    @Override
    public int order() {
        AssertUtils.notNull(this.task, "task is null.");
        return this.task.getOrder();
    }
    
    /**
     * @return
     */
    @Override
    public String code() {
        AssertUtils.notNull(this.task, "task is null.");
        return this.task.getCode();
    }
    
    /**
     * @return
     */
    @Override
    public String parentCode() {
        AssertUtils.notNull(this.task, "task is null.");
        return this.task.getParentCode();
    }
    
    /**
     * @return
     */
    @Override
    public String name() {
        AssertUtils.notNull(this.task, "task is null.");
        return this.task.getName();
    }
    
    /**
     * @return
     */
    @Override
    public String remark() {
        AssertUtils.notNull(this.task, "task is null.");
        return this.task.getRemark();
    }
    
    /**
     * @return
     */
    @Override
    public String className() {
        AssertUtils.notNull(this.task, "task is null.");
        return AopUtils.getTargetClass(this.task).getName();
    }
    
    /**
     * @return
     */
    @Override
    public String beanName() {
        AssertUtils.notEmpty(this.taskBeanName, "beanName is empty.");
        return this.taskBeanName;
    }
    
    /**
     * @param 对beanName进行赋值
     */
    public void setTaskBeanName(String taskBeanName) {
        this.taskBeanName = taskBeanName;
    }
    
    /**
     * @param 对task进行赋值
     */
    public void setTask(TASK task) {
        this.task = task;
    }
    
    /**
     * @param 对transactionManager进行赋值
     */
    public void setTransactionManager(
            PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
