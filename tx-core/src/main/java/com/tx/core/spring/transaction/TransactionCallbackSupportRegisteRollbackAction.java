/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年7月31日
 * <修改描述:>
 */
package com.tx.core.spring.transaction;

import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 方法将返回一个Rollback后执行的Action<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年7月31日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class TransactionCallbackSupportRegisteRollbackAction<T>
        implements TransactionCallback<T> {
    
    private static final String ACTION_WHEN_ROLLBACK_KEY = "ACTION_WHEN_ROLLBACK_KEY";
    
    /**
      * 获取ActionWhenRollbackKey
      * <功能详细描述>
      * @param tx
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private String getActionWhenRollbackKey() {
        StringBuilder sb = new StringBuilder(ACTION_WHEN_ROLLBACK_KEY);
        sb.append("_");
        sb.append(Math.abs(this.hashCode()));
        return sb.toString();
    }
    
    /**
      * 注册RollbackAction(允许重复注册，最后一次注册生效)
      * <功能详细描述>
      * @param actionWhenRollback [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void registeRollbackAction(
            TransactionCallbackWithoutResult actionWhenRollback) {
        //该逻辑应该在事务环境中执行<br/>
        AssertUtils.isTrue(TransactionSynchronizationManager.isSynchronizationActive(),
                "该逻辑应在事务环境中执行.");
        
        if (actionWhenRollback != null) {
            TransactionSynchronizationManager.unbindResourceIfPossible(getActionWhenRollbackKey());
            TransactionSynchronizationManager.bindResource(getActionWhenRollbackKey(),
                    actionWhenRollback);
        }
    }
    
    /**
      * 获取回滚时应执行的逻辑<br/>
      * <功能详细描述>
      * @param tx
      * @return [参数说明]
      * 
      * @return TransactionCallbackWithoutResult [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public TransactionCallbackWithoutResult getActionWhenRollback() {
        Object obj = TransactionSynchronizationManager.getResource(getActionWhenRollbackKey());
        if (obj == null) {
            return null;
        }
        AssertUtils.isInstanceOf(TransactionCallbackWithoutResult.class,
                obj,
                "obj should is instance of TransactionCallbackWithoutResult.class.but is :{}",
                new Object[] { obj.getClass() });
        
        TransactionCallbackWithoutResult actionWhenRollback = (TransactionCallbackWithoutResult) obj;
        return actionWhenRollback;
    }
}
