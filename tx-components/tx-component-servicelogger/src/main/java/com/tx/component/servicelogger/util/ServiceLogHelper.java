/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月24日
 * 项目： tx-component-servicelog
 */
package com.tx.component.servicelogger.util;

import com.tx.component.servicelogger.context.ServiceLoggerContext;
import com.tx.component.servicelogger.model.AbstractServiceLog;

/**
 * 日志助手类
 * 
 * @author rain
 * @version [版本号, 2015年11月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ServiceLogHelper {
    
    /**
     * 记录日志<br/>
     *
     * @param log
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月24日]
     * @author rain
     */
    public static void log(Object log) {
        ServiceLoggerContext.getLogger(AbstractServiceLog.class).log(log);
    }
}
