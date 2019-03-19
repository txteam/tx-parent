/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年5月10日
 * <修改描述:>
 */
package com.tx.component.strategy.context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.ReflectionUtils;

/**
  * 会话注入<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2017年5月10日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class StrategySessionInvocationHandler implements InvocationHandler {
    
    /** 事务句柄 */
    private TransactionTemplate transactionTemplate;
    
    /** 实际目标对象 */
    private Object targetBean;
    
    /** <默认构造函数> */
    public StrategySessionInvocationHandler(Object targetBean, TransactionTemplate transactionTemplate) {
        super();
        this.transactionTemplate = transactionTemplate;
        this.targetBean = targetBean;
    }
    
    /**
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
        Object result = null;
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            result = doInvoke(method, args);
        } else {
            result = transactionTemplate.execute(new TransactionCallback<Object>() {
                @Override
                public Object doInTransaction(TransactionStatus status) {
                    return doInvoke(method, args);
                }
            });
        }
        return result;
    }
    
    /**
      * 调用实际的策略方法<br/>
      * <功能详细描述>
      * @param method
      * @param args
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private Object doInvoke(Method method, Object[] args) {
        Object result = null;
        try {
            StrategySessionContext.open(args);
            result = ReflectionUtils.invokeMethod(method, this.targetBean, args);
        } finally {
            StrategySessionContext.close();
        }
        return result;
    }
}
