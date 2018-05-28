/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年6月19日
 * <修改描述:>
 */
package com.tx.component.task.timedtask.executor;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
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
public class IterableTimedTaskExecutor<T>
        extends AbstractTimedTaskExecutor<IterableTimedTask<T>>
        implements InitializingBean {
    
    /** 事务模板类 */
    private TransactionTemplate transactionTemplateOfRequiresNew;
    
    /** <默认构造函数> */
    public IterableTimedTaskExecutor() {
        super();
    }
    
    /** <默认构造函数> */
    public IterableTimedTaskExecutor(String taskBeanName,
            IterableTimedTask<T> task,
            PlatformTransactionManager transactionManager) {
        super(taskBeanName, task, transactionManager);
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.transactionTemplateOfRequiresNew = new TransactionTemplate(
                this.transactionManager, new DefaultTransactionDefinition(
                        TransactionDefinition.PROPAGATION_REQUIRES_NEW));
    }
    
    /**
     * @param executeDate
     * @return
     */
    @Override
    public Date doExecute(final Object... args) {
        //获取任务总数，用以核对最后执行任务数量
        boolean hasNextAdapter = this.task.hasNextAdapter(args);
        
        //迭代取值
        int batchIndex = 0;
        while (hasNextAdapter) {
            final IterableTimedTask<T> finalTask = this.task;
            
            hasNextAdapter = this.transactionTemplateOfRequiresNew
                    .execute(new TransactionCallback<Boolean>() {
                        //考虑到mysql的事务隔离性，nextAdapter,以及hasNextAdapter应该放入事务中进行取值
                        @Override
                        public Boolean doInTransaction(
                                TransactionStatus status) {
                            List<T> dataList = finalTask.nextAdapter(
                                    finalTask.getBatchSize(), args);
                            
                            for (T data : dataList) {
                                finalTask.executeAdapter(data, args);
                            }
                            return finalTask.hasNextAdapter(args);
                        }
                    });
            
            batchIndex = batchIndex + 1;
            
            AssertUtils.isTrue(batchIndex < this.task.getMaxBatchCount(),
                    "任务执行异常，超过预期最大次数.需要修改batchSize或maxBatchCount.或是程序中出现异常导致无限循环.");
        }
        
        Date nextExecuteDate = this.task.getNextDateAdapter(args);
        
        return nextExecuteDate;
    }
}
