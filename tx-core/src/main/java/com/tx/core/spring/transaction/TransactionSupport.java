/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年7月31日
 * <修改描述:>
 */
package com.tx.core.spring.transaction;

import java.lang.reflect.UndeclaredThrowableException;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.support.CallbackPreferringPlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 事务支撑类<br/>
 *     如果利用TransactionSynchronizeManager.registerxxx去实现
 *     在事务回滚的时执行逻辑的代码，由于在Mybatis底层中存在，也存在registe的逻辑
 *     在对应的实现类的Order顺序不对的时候，可能造成链接泄露，此处新增该封装，代替原逻辑中此类实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年7月31日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TransactionSupport extends TransactionTemplate {
    
    /** 注释内容 */
    private static final long serialVersionUID = -176363138633681622L;
    
    /** <默认构造函数> */
    public TransactionSupport() {
        super();
    }
    
    /** <默认构造函数> */
    public TransactionSupport(PlatformTransactionManager transactionManager,
            TransactionDefinition transactionDefinition) {
        super(transactionManager, transactionDefinition);
    }
    
    /** <默认构造函数> */
    public TransactionSupport(PlatformTransactionManager transactionManager) {
        super(transactionManager);
    }
    
    public <T> T execute(TransactionCallback<T> action,
            TransactionCallbackWithoutResult exceptionAction)
            throws TransactionException {
        if (getTransactionManager() instanceof CallbackPreferringPlatformTransactionManager) {
            return ((CallbackPreferringPlatformTransactionManager) getTransactionManager()).execute(this,
                    action);
        } else {
            TransactionStatus status = getTransactionManager().getTransaction(this);
            T result;
            try {
                result = action.doInTransaction(status);
            } catch (RuntimeException ex) {
                // Transactional code threw application exception -> rollback
                rollbackOnException(status, ex, exceptionAction);
                throw ex;
            } catch (Error err) {
                // Transactional code threw error -> rollback
                rollbackOnException(status, err, exceptionAction);
                throw err;
            } catch (Exception ex) {
                // Transactional code threw unexpected exception -> rollback
                rollbackOnException(status, ex, exceptionAction);
                throw new UndeclaredThrowableException(ex,
                        "TransactionCallback threw undeclared checked exception");
            }
            getTransactionManager().commit(status);
            return result;
        }
    }
    
    /**
     * Perform a rollback, handling rollback exceptions properly.
     * @param status object representing the transaction
     * @param ex the thrown application exception or error
     * @throws TransactionException in case of a rollback error
     */
    private void rollbackOnException(TransactionStatus status, Throwable ex,
            TransactionCallbackWithoutResult exceptionAction)
            throws TransactionException {
        logger.debug("Initiating transaction rollback on application exception",
                ex);
        try {
            getTransactionManager().rollback(status);
        } catch (TransactionSystemException ex2) {
            logger.warn("Application exception overridden by rollback exception",
                    ex);
            ex2.initApplicationException(ex);
            throw ex2;
        } catch (RuntimeException ex2) {
            logger.warn("Application exception overridden by rollback exception",
                    ex);
            throw ex2;
        } catch (Error err) {
            logger.warn("Application exception overridden by rollback error",
                    ex);
            throw err;
        } finally{
            executeWhenException(exceptionAction);
        }
    }
    
    /**
      * 异常存在的时候执行逻辑<br/>
      * <功能详细描述>
      * @param exceptionAction
      * @throws TransactionException [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void executeWhenException(
            TransactionCallbackWithoutResult exceptionAction)
            throws TransactionException {
        TransactionStatus status = getTransactionManager().getTransaction(this);
        try {
            exceptionAction.doInTransaction(status);
        } catch (RuntimeException ex) {
            rollbackOnExceptionWhenRollback(status, ex);
            throw ex;
        } catch (Error err) {
            rollbackOnExceptionWhenRollback(status, err);
            throw err;
        } catch (Exception ex) {
            rollbackOnExceptionWhenRollback(status, ex);
            throw new UndeclaredThrowableException(ex,
                    "TransactionCallback threw undeclared checked exception");
        }
        getTransactionManager().commit(status);
    }
    
    /**
     * Perform a rollback, handling rollback exceptions properly.
     * @param status object representing the transaction
     * @param ex the thrown application exception or error
     * @throws TransactionException in case of a rollback error
     */
    private void rollbackOnExceptionWhenRollback(TransactionStatus status,
            Throwable ex) throws TransactionException {
        logger.debug("Initiating transaction rollback on application exception",
                ex);
        try {
            getTransactionManager().rollback(status);
        } catch (TransactionSystemException ex2) {
            logger.error("Application exception overridden by rollback exception",
                    ex);
            ex2.initApplicationException(ex);
            throw ex2;
        } catch (RuntimeException ex2) {
            logger.error("Application exception overridden by rollback exception",
                    ex);
            throw ex2;
        } catch (Error err) {
            logger.error("Application exception overridden by rollback error",
                    ex);
            throw err;
        }
    }
}
