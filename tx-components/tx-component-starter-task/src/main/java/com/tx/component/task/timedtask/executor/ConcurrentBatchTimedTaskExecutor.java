/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年6月19日
 * <修改描述:>
 */
package com.tx.component.task.timedtask.executor;

import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.task.timedtask.AbstractTimedTaskExecutor;
import com.tx.component.task.timedtask.task.ConcurrentBatchTimedTask;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 批量任务执行器<br/>
 *     这里这个实现是有问题的，需要进行调整<br/>
 * @author  Administrator
 * @version  [版本号, 2014年6月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConcurrentBatchTimedTaskExecutor<T>
        extends AbstractTimedTaskExecutor<ConcurrentBatchTimedTask<T>>
        implements InitializingBean {
    
    /** 事务模板类 */
    private TransactionTemplate transactionTemplateOfRequiresNew;
    
    /** 任务执行器 */
    private ThreadPoolTaskExecutor taskExecutor;
    
    /** <默认构造函数> */
    public ConcurrentBatchTimedTaskExecutor() {
        super();
    }
    
    /** <默认构造函数> */
    public ConcurrentBatchTimedTaskExecutor(String taskBeanName,
            ConcurrentBatchTimedTask<T> task,
            PlatformTransactionManager transactionManager) {
        super(taskBeanName, task, transactionManager);
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(this.taskBeanName, "beanName is empty.");
        AssertUtils.notNull(this.task, "task is null.");
        AssertUtils.notNull(this.transactionManager,
                "transactionManager is null.");
        
        this.transactionTemplateOfRequiresNew = new TransactionTemplate(
                this.transactionManager, new DefaultTransactionDefinition(
                        TransactionDefinition.PROPAGATION_REQUIRES_NEW));
        
        this.taskExecutor = new ThreadPoolTaskExecutor();
        this.taskExecutor.setCorePoolSize(
                this.task.getPoolSize() <= 1 ? 1 : this.task.getPoolSize());//核心线程数
        this.taskExecutor.afterPropertiesSet();
    }
    
    /**
     * @param executeDate
     * @return
     */
    @Override
    public Date doExecute(final Object... args) {
        //获取任务总数，用以核对最后执行任务数量
        List<T> dataList = this.task.getListAdapter(args);
        int count = dataList.size();
        final int batchSize = this.task.getPoolSize();
        final Queue<T> dataQueue = new ConcurrentLinkedQueue<>(dataList);
        AtomicInteger activeThreadNumber = new AtomicInteger(0);
        int batchCount = count % batchSize == 0 ? count / batchSize
                : (count / batchSize + 1);
        
        for (int batchIndex = 0; batchIndex < batchCount; batchIndex++) {
            final ConcurrentBatchTimedTask<T> finalTask = this.task;
            final ThreadPoolTaskExecutor finalTaskExecutor = this.taskExecutor;
            
            final TransactionTemplate finalFT = this.transactionTemplateOfRequiresNew;
            
            activeThreadNumber.getAndIncrement();//增加计数器
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    finalTaskExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                finalFT.execute(
                                        new TransactionCallbackWithoutResult() {
                                            @Override
                                            protected void doInTransactionWithoutResult(
                                                    TransactionStatus status) {
                                                //增加
                                                for (int i = 0; i < batchSize
                                                        && !dataQueue
                                                                .isEmpty(); i++) {
                                                    T data = dataQueue.poll();//移除并返问队列头部的元素
                                                    finalTask.executeAdapter(data,
                                                            args);
                                                }
                                            }
                                        });
                            } finally {
                                //减少
                                activeThreadNumber.getAndDecrement();
                            }
                        }
                    });
                }
            });
        }
        
        while (activeThreadNumber.intValue() > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //doNothing
            }
        }
        
        Date nextExecuteDate = this.task.getNextDateAdapter(args);
        
        return nextExecuteDate;
    }
}
