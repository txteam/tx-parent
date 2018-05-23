/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年6月19日
 * <修改描述:>
 */
package com.tx.component.task.timedtask.executor;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.task.timedtask.AbstractTimedTaskExecutor;
import com.tx.component.task.timedtask.task.BatchTimedTask;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 批量任务执行器<br/>
 *     这里这个实现是有问题的，需要进行调整<br/>
 * @author  Administrator
 * @version  [版本号, 2014年6月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BatchTimedTaskExecutor<T>
        extends AbstractTimedTaskExecutor<BatchTimedTask<T>>
        implements InitializingBean {
    
    /** 事务模板类 */
    private TransactionTemplate transactionTemplate;
    
    /** <默认构造函数> */
    public BatchTimedTaskExecutor() {
        super();
    }
    
    /** <默认构造函数> */
    public BatchTimedTaskExecutor(String beanName, BatchTimedTask<T> task,
            PlatformTransactionManager transactionManager) {
        super(beanName, task, transactionManager);
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(this.beanName, "beanName is empty.");
        AssertUtils.notNull(this.task, "task is null.");
        AssertUtils.notNull(this.transactionManager,
                "transactionManager is null.");
        
        this.transactionTemplate = new TransactionTemplate(
                this.transactionManager, new DefaultTransactionDefinition(
                        TransactionDefinition.PROPAGATION_REQUIRES_NEW));
    }
    
    /**
     * @param executeDate
     * @return
     */
    @Override
    public Date doExecute(Date executeDate) {
        //获取任务总数，用以核对最后执行任务数量
        List<T> dataList = this.task.getList(executeDate);
        final Queue<T> dataQueue = new ArrayDeque<>(dataList);
        
        final BatchTimedTask<T> finalTask = this.task;
        final int batchSize = this.task.getBatchSize();
        
        while (!dataQueue.isEmpty()) {
            this.transactionTemplate
                    .execute(new TransactionCallbackWithoutResult() {
                        @Override
                        protected void doInTransactionWithoutResult(
                                TransactionStatus status) {
                            for (int i = 0; i < batchSize
                                    && !dataQueue.isEmpty(); i++) {
                                T data = dataQueue.poll();//移除并返问队列头部的元素
                                finalTask.execute(data, executeDate);
                            }
                        }
                    });
        }
        
        Date nextExecuteDate = this.task.getNextDate(executeDate);
        AssertUtils.notNull(nextExecuteDate,
                "nextExecutDate is null.task:{}",
                this.task.getCode());
        return nextExecuteDate;
    }
}
