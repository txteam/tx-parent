/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月24日
 * 项目： tx-component-servicelog
 */
package com.tx.component.servicelog.util;

import com.tx.component.servicelog.context.ServiceLoggerContext;
import com.tx.component.servicelog.logger.TXBaseServiceLog;
import com.tx.component.servicelog.logger.TxLoaclFileServiceLog;

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
     * 
     * 记录日志
     *
     * @param log
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月24日]
     * @author rain
     */
    public static void log(TXBaseServiceLog log) {
        ServiceLoggerContext.getLogger(TXBaseServiceLog.class).log(log);
    }
    
    public static void logMessage(TxLoaclFileServiceLog log){
        
    }
    
    /**
     * 构造报文日志<br />
     * 
     * @param messageid 报文id
     * @param module 报文模块
     * @param requestBody 请求体
     * @param responseCode 响应代码
     * @param responseCodeMessage 响应代码信息
     * @param responseBody 响应体
     *            
     * @version [版本号, 2015年11月24日]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     * @author rain
     */
    public static TxLoaclFileServiceLog createTxMessageLog(String messageid, String module, String requestBody, String responseCode, String responseCodeMessage, String responseBody) {
        TxLoaclFileServiceLog log = new TxLoaclFileServiceLog();
        
        
        
        return log;
    }
}
