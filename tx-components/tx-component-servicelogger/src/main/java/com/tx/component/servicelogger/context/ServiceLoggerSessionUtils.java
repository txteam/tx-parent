/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月2日
 * <修改描述:>
 */
package com.tx.component.servicelogger.context;

import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 业务日志会话工厂<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ServiceLoggerSessionUtils {
    
    public static final ServiceLoggerSessionUtils INSTANCE = new ServiceLoggerSessionUtils();
    
    /** <默认构造函数> */
    private ServiceLoggerSessionUtils() {
        super();
    }
    
    /**
     * 获取日志会话<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return ServiceLoggerSession [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static ServiceLoggerSession getLoggerSession() {
        AssertUtils.isTrue(
                TransactionSynchronizationManager.isSynchronizationActive(),
                "日志会话应存在于事务开启后调用.");
        
        Object loggerSessionObject = TransactionSynchronizationManager
                .getResource(INSTANCE);
        //从线程变量中获取业务日志会话
        if (loggerSessionObject != null) {
            AssertUtils.isTrue(
                    ServiceLoggerSession.class.isInstance(loggerSessionObject),
                    "logger session should is LoggerSession instance.");
            
            return (ServiceLoggerSession) loggerSessionObject;
        }
        
        ServiceLoggerSession loggerSession = new ServiceLoggerSession();
        TransactionSynchronizationManager.bindResource(INSTANCE, loggerSession);
        
        //如果事务的现成变量中
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronizationAdapter() {
                    
                    @Override
                    public void afterCompletion(int status) {
                        TransactionSynchronizationManager
                                .unbindResource(INSTANCE);
                    }
                });
        
        return loggerSession;
    }
}
