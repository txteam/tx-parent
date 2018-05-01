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

import javax.annotation.Resource;

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
public class ConcurrentBatchTimedTaskExecutor<T> extends AbstractTimedTaskExecutor<ConcurrentBatchTimedTask<T>>
        implements InitializingBean {
    
    /** 事务管理器 */
    @Resource(name = "taskContext.transactionManager")
    private PlatformTransactionManager transactionManager;
    
    /** 事务模板类 */
    private TransactionTemplate transactionTemplate;
    
    /** 任务执行器 */
    private ThreadPoolTaskExecutor taskExecutor;
    
    /** <默认构造函数> */
    public ConcurrentBatchTimedTaskExecutor(ConcurrentBatchTimedTask<T> task) {
        super(task);
    }
    
    /** <默认构造函数> */
    public ConcurrentBatchTimedTaskExecutor() {
        super();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.transactionTemplate = new TransactionTemplate(this.transactionManager,
                new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
        this.taskExecutor = new ThreadPoolTaskExecutor();
        this.taskExecutor.setCorePoolSize(this.task.getPoolSize() <= 1 ? 1 : this.task.getPoolSize());//核心线程数
        this.taskExecutor.afterPropertiesSet();
    }
    
    /**
     * @param executeDate
     * @return
     */
    @Override
    public Date doExecute(final Date executeDate) {
        //获取任务总数，用以核对最后执行任务数量
        List<T> dataList = this.task.getList(executeDate);
        int count = dataList.size();
        final int batchSize = this.task.getPoolSize();
        final Queue<T> dataQueue = new ConcurrentLinkedQueue<>(dataList);
        AtomicInteger activeThreadNumber = new AtomicInteger(0);
        int batchCount = count % batchSize == 0 ? count / batchSize : (count / batchSize + 1);
        
        for (int batchIndex = 0; batchIndex < batchCount; batchIndex++) {
            final ConcurrentBatchTimedTask<T> finalTask = this.task;
            final ThreadPoolTaskExecutor finalTaskExecutor = this.taskExecutor;
            
            final TransactionTemplate finalFT = this.transactionTemplate;
            
            activeThreadNumber.getAndIncrement();//增加计数器
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    finalTaskExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                finalFT.execute(new TransactionCallbackWithoutResult() {
                                    @Override
                                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                                        //增加
                                        for (int i = 0; i < batchSize && !dataQueue.isEmpty(); i++) {
                                            T data = dataQueue.poll();//移除并返问队列头部的元素
                                            finalTask.execute(data, executeDate);
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
        Date nextExecuteDate = this.task.getNextDate(executeDate);
        AssertUtils.notNull(nextExecuteDate, "nextExecutDate is null.task:{}", this.task.getCode());
        
        return nextExecuteDate;
    }
    
    //    static abstract class TestRunable implements Runnable {
    //        
    //        protected int index;
    //        
    //        /** <默认构造函数> */
    //        public TestRunable(int index) {
    //            super();
    //            this.index = index;
    //        }
    //        
    //        /**
    //         * 
    //         */
    //        @Override
    //        public void run() {
    //            doRun();
    //        }
    //        
    //        public abstract void doRun();
    //    }
    //    
    //    public static void main(String[] args) {
    //        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    //        taskExecutor.setDaemon(false);
    //        taskExecutor.setCorePoolSize(10);//核心线程数
    //        taskExecutor.afterPropertiesSet();
    //        AtomicInteger activeThreadNumber = new AtomicInteger(0);
    //        
    //        for (int i = 0; i < 100; i++) {
    //            final int index = i;
    //            System.out.println(Thread.currentThread().getName() + "run thread: i:" + index + " load.");
    //            
    //            activeThreadNumber.getAndIncrement();//增加计数器
    //            taskExecutor.execute(new TestRunable(index) {
    //                
    //                @Override
    //                public void doRun() {
    //                    System.out
    //                            .println(Thread.currentThread().getName() + "run thread: index:" + this.index + " start.");
    //                    
    //                    try {
    //                        try {
    //                            Thread.sleep(500);
    //                        } catch (InterruptedException e) {
    //                            // TODO Auto-generated catch block
    //                            e.printStackTrace();
    //                        }
    //                    } finally {
    //                        //减少
    //                        activeThreadNumber.getAndDecrement();
    //                        
    //                        System.out.println(
    //                                Thread.currentThread().getName() + "run thread: index:" + this.index + " end.");
    //                    }
    //                }
    //            });
    //        }
    //        
    //        while (activeThreadNumber.intValue() > 0) {
    //            try {
    //                Thread.sleep(1000);
    //            } catch (InterruptedException e) {
    //                //doNothing
    //            }
    //        }
    //        
    //        System.out.println("main end");
    //        taskExecutor.shutdown();
    //    }
}
