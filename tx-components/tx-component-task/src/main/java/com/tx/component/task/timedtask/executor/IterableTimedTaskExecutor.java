/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年6月19日
 * <修改描述:>
 */
package com.tx.component.task.timedtask.executor;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.task.timedtask.AbstractTimedTaskExecutor;
import com.tx.component.task.timedtask.task.IterableTimedTask;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 批量任务执行器<br/>
 *     这里这个实现是有问题的，需要进行调整<br/>
 * @author  Administrator
 * @version  [版本号, 2014年6月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class IterableTimedTaskExecutor<T> extends AbstractTimedTaskExecutor<IterableTimedTask<T>>
        implements InitializingBean {
    
    /** 事务管理器 */
    @Resource(name = "taskContext.transactionManager")
    private PlatformTransactionManager transactionManager;
    
    /** 事务模板类 */
    private TransactionTemplate transactionTemplate;
    
    /** <默认构造函数> */
    public IterableTimedTaskExecutor(IterableTimedTask<T> task) {
        super(task);
    }
    
    /** <默认构造函数> */
    public IterableTimedTaskExecutor() {
        super();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.transactionTemplate = new TransactionTemplate(this.transactionManager,
                new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
    }
    
    /**
     * @param executeDate
     * @return
     */
    @Override
    public Date doExecute(Date executeDate) {
        //获取任务总数，用以核对最后执行任务数量
        int count = this.task.count(executeDate);
        int executeCount = 0;
        
        while (this.task.hasNext(executeDate)) {
            List<T> dataList = this.task.next(executeDate);
            executeCount += dataList.size();
            AssertUtils.isTrue(executeCount <= count,
                    "executeCount:{} should min than count:{}",
                    new Object[] { executeCount, count });
            
            final IterableTimedTask<T> finalTask = this.task;
            this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    for (T data : dataList) {
                        finalTask.execute(data, executeDate);
                    }
                }
            });
        }
        
        if (this.task.isNeedCheckCount()) {
            AssertUtils.isTrue(count == executeCount,
                    "执行前检测到的待执行任务与最终执行了的任务数量不同.count:{} executeCount:{}",
                    new Object[] { count, executeCount });
        }
        Date nextExecuteDate = this.task.getNextDate(executeDate);
        AssertUtils.notNull(nextExecuteDate, "nextExecutDate is null.task:{}", this.task.getCode());
        return nextExecuteDate;
    }
}
