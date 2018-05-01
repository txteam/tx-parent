/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年6月19日
 * <修改描述:>
 */
package com.tx.component.task.timedtask.executor;

import java.util.Date;

import com.tx.component.task.timedtask.AbstractTimedTaskExecutor;
import com.tx.component.task.timedtask.task.SimpleTimedTask;

/**
 * 简单任务执行器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年6月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SimpleTimedTaskExecutor extends AbstractTimedTaskExecutor<SimpleTimedTask> {
    
    /** <默认构造函数> */
    public SimpleTimedTaskExecutor(SimpleTimedTask task) {
        super(task);
    }
    
    /** <默认构造函数> */
    public SimpleTimedTaskExecutor() {
        super();
    }
    
    /**
     * @param executeDate
     * @return
     */
    @Override
    public Date doExecute(Date executeDate) {
        Date nextExecuteDate = this.task.execute(executeDate);
        return nextExecuteDate;
    }
}
