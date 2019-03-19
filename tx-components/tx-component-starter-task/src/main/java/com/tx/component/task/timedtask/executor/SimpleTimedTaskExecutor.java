/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年6月19日
 * <修改描述:>
 */
package com.tx.component.task.timedtask.executor;

import java.util.Date;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

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
public class SimpleTimedTaskExecutor extends
        AbstractTimedTaskExecutor<SimpleTimedTask> implements InitializingBean {
    
    /** 事务模板类 */
    private TransactionTemplate transactionTemplateOfRequiresNew;
    
    /** <默认构造函数> */
    public SimpleTimedTaskExecutor() {
        super();
    }
    
    /** <默认构造函数> */
    public SimpleTimedTaskExecutor(String beanName, SimpleTimedTask task,
            PlatformTransactionManager transactionManager) {
        super(beanName, task, transactionManager);
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
        final SimpleTimedTask finalTask = this.task;
        
        Date nextExecuteDate = this.transactionTemplateOfRequiresNew
                .execute(new TransactionCallback<Date>() {
                    @Override
                    public Date doInTransaction(TransactionStatus status) {
                        return finalTask.executeAdapter(args);
                    }
                });
        
        return nextExecuteDate;
    }
}
