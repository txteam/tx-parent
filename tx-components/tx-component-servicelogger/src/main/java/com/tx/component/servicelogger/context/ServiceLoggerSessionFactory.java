/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月2日
 * <修改描述:>
 */
package com.tx.component.servicelogger.context;

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
public class ServiceLoggerSessionFactory {
    
    public static final ServiceLoggerSessionFactory INSTANCE = new ServiceLoggerSessionFactory();
    
    /** <默认构造函数> */
    private ServiceLoggerSessionFactory() {
        super();
    }
    
    public static ServiceLoggerSession getLoggerSession() {
        Object loggerSession = TransactionSynchronizationManager
                .getResource(INSTANCE);
        
        if (loggerSession != null) {
            AssertUtils.isTrue(
                    ServiceLoggerSession.class.isInstance(loggerSession),
                    "logger session should is LoggerSession instance.");
            
            return (ServiceLoggerSession) loggerSession;
        }
        
        return null;
    }
}
