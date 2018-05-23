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
import com.tx.core.util.typereference.ParameterizedTypeReference;

/**
* 任务执行器的的抽象类实现
* <功能详细描述>
* 
* @author  Tim.PengQY
* @version  [版本号, 2017年10月16日]
* @see  [相关类/方法]
* @since  [产品/模块版本]
*/
public abstract class AbstractTimedTaskExecutor<T extends TimedTask>
        extends ParameterizedTypeReference<T> implements TaskExecutor {
    
    /** 日志记录器 */
    protected Logger logger = LoggerFactory.getLogger(TaskContext.class);
    
    /** beanName */
    protected String beanName;
    
    /** 任务实例 */
    protected T task;
    
    /** 事务manager */
    protected PlatformTransactionManager transactionManager;
    
    /** <默认构造函数> */
    public AbstractTimedTaskExecutor() {
        super();
    }
    
    /** <默认构造函数> */
    public AbstractTimedTaskExecutor(String beanName, T task,
            PlatformTransactionManager transactionManager) {
        super();
        AssertUtils.notEmpty(beanName, "beanName is empty.");
        AssertUtils.notNull(task, "task is null.");
        AssertUtils.notNull(transactionManager, "transactionManager is null.");
        
        this.beanName = beanName;
        this.task = task;
        this.transactionManager = transactionManager;
    }
    
    //public void is
    
    /**
     * @param executeDate
     * @return
     */
    @Task
    public Date execute(Date executeDate) {
        AssertUtils.notNull(this.task, "task is null.");
        
        //获取任务的下次执行时间
        Date nextFireDate = TaskSessionContext.getExecution().getNextFireDate();
        if (nextFireDate != null && executeDate.compareTo(nextFireDate) < 0) {
            TaskSessionContext.getExecution().setSkip();//设置当前任务实际是被跳过执行的
            return nextFireDate;
        }
        
        //执行任务
        nextFireDate = doExecute(executeDate);
        //写入下次执行时间
        TaskSessionContext.getExecution().setNextFireDate(nextFireDate);
        
        return nextFireDate;
    }
    
    /** 子类的任务执行 */
    protected abstract Date doExecute(Date executeDate);
    
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
        AssertUtils.notEmpty(this.beanName, "beanName is empty.");
        return this.beanName;
    }
    
    /**
     * @param 对beanName进行赋值
     */
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
    
    /**
     * @param 对task进行赋值
     */
    public void setTask(T task) {
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
