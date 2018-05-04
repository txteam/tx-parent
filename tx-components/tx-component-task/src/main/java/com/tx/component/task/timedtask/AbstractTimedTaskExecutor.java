/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年10月15日
 * <修改描述:>
 */
package com.tx.component.task.timedtask;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.component.task.context.TaskContext;
import com.tx.component.task.context.TaskSessionContext;
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
public abstract class AbstractTimedTaskExecutor<T extends TimedTask> implements TimedTaskExecutor {
    
    /** 日志记录器 */
    protected Logger logger = LoggerFactory.getLogger(TaskContext.class);
    
    /** 任务实例 */
    protected T task;
    
    /** <默认构造函数> */
    public AbstractTimedTaskExecutor() {
        super();
    }
    
    /** <默认构造函数> */
    public AbstractTimedTaskExecutor(T task) {
        super();
        this.task = task;
    }
    
    /**
     * @param executeDate
     * @return
     */
    @Override
    public Date execute(Date executeDate) {
        AssertUtils.notNull(this.task, "task is null.");
        
        //获取任务的下次执行时间
        Date nextFireDate = TaskSessionContext.getSession().getNextFireDate();
        if(nextFireDate != null && executeDate.compareTo(nextFireDate) < 0){
            //下次执行时间未到，则能够执行
            nextFireDate = executeDate;
        }else{
            nextFireDate = doExecute(executeDate);
            //写入下次执行时间
            TaskSessionContext.getSession().setNextFireDate(nextFireDate);
        }
        
        return nextFireDate;
    }
    
    /** 子类的任务执行 */
    protected abstract Date doExecute(Date executeDate);
    
    /**
     * @param 对task进行赋值
     */
    public void setTask(T task) {
        this.task = task;
    }
    
    /**
     * @return
     */
    @Override
    public TimedTask getTask() {
        return this.task;
    }
    
}
