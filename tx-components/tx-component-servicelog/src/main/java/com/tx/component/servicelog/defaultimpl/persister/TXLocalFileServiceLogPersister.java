/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月27日
 * 项目： tx-component-servicelog
 */
package com.tx.component.servicelog.defaultimpl.persister;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.tx.component.servicelog.context.logger.ServiceLogPersister;
import com.tx.component.servicelog.defaultimpl.TXLocalFileServiceLoggerBuilder;
import com.tx.component.servicelog.defaultimpl.TXLocalFileServiceLoggerBuilder.TXLocalFileDataFormat;
import com.tx.component.servicelog.exception.ServiceLoggerException;
import com.tx.component.servicelog.logger.TxLoaclFileServiceLog;
import com.tx.core.reflection.JpaMetaClass;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.ContextBase;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.encoder.EchoEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

/**
 * 文件日志记录器
 * 
 * @author rain
 * @version [版本号, 2015年11月27日]
 * @param <T>
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TXLocalFileServiceLogPersister<T> implements ServiceLogPersister<T> {
    
    /** 日志 */
    private Logger logger = LoggerFactory.getLogger(TXLocalFileServiceLoggerBuilder.class);
    
    /** 本地文件数据格式 */
    protected TXLocalFileDataFormat dataformat;
    
    /** 保存目录路径 */
    protected String savepath;
    
    private Context context = new ContextBase();
    
    private EchoEncoder<String> encoder = new EchoEncoder<String>();
    
    /**
     * 
     * 构造文件日志记录器
     * 
     * @param dataformat 本地文件数据格式
     * @param savepath 保存目录路径
     *            
     * @version [版本号, 2015年11月27日]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     * @author rain
     */
    public TXLocalFileServiceLogPersister(TXLocalFileDataFormat dataformat, String savepath) {
        super();
        this.dataformat = dataformat;
        this.savepath = savepath;
        encoder.setContext(context);
    }
    
    @Override
    public void persist(T logInstance) {
        switch (dataformat) {
            case TXT:
                persistLogByTxt(logInstance);
                break;
            default:
                throw new ServiceLoggerException("dataformat:{} not support.", dataformat.name());
                
        }
    }
    
    /**
     * 
     * 返回日志保存文件
     *
     * @param log
     *            
     * @return File [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月26日]
     * @author rain
     */
    private String getSaveDirectory(T log) {
        TxLoaclFileServiceLog txLog = (TxLoaclFileServiceLog) log;
        String module = txLog.getModule();
        
        StringBuilder path = new StringBuilder(128);
        path.append(savepath).append('/');
        path.append(StringUtils.isEmpty(module) ? "null" : module).append('/');
        path.append(DateFormatUtils.format(new Date(), "yyyy")).append('/');
        return path.toString();
    }
    
    /**
     * 
     * 持久化日志
     *
     * @param savepath 保存目录
     * @param logInstance 日志
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月26日]
     * @author rain
     */
    private void persistLogByTxt(T log) {
        TxLoaclFileServiceLog txLog = (TxLoaclFileServiceLog) log;
        String requestBody = "";
        String responseBody = "";
        Map<String, Object> values = new HashMap<String, Object>();
        JpaMetaClass<?> jpaMetaClass = JpaMetaClass.forClass(log.getClass());
        Set<String> getterNames = jpaMetaClass.getGetterNames();
        for (String getterMethod : getterNames) {
            try {
                Object invokeMethod = MethodUtils.invokeMethod(log, "get" + StringUtils.capitalize(getterMethod));
                if ("requestBody".equals(getterMethod)) {
                    requestBody = String.valueOf(invokeMethod);
                } else if ("responseBody".equals(getterMethod)) {
                    responseBody = String.valueOf(invokeMethod);
                } else {
                    values.put(getterMethod, invokeMethod);
                }
            } catch (Exception e) {
                logger.warn("日志数据读取异常 : " + e.getMessage(), e);
            }
        }
        
        // 保存
        StringBuilder sb = new StringBuilder(requestBody.length() + responseBody.length() + 1024);
        // 第一部分 : 当前日期 模块名 id
        sb.append(DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss:SSS")).append(' ');
        sb.append('[').append(txLog.getModule()).append("] ");
        sb.append(txLog.getId()).append(CoreConstants.LINE_SEPARATOR);
        // 第二部分 : 日志内容
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String key = org.apache.commons.lang3.StringUtils.rightPad(entry.getKey(), 22, ' ');
            String value = null;
            Object valueObj = entry.getValue();
            if (valueObj instanceof Date) {
                value = DateFormatUtils.format((Date) valueObj, "yyyy-MM-dd HH:mm:ss");
            } else {
                value = valueObj == null ? "" : String.valueOf(valueObj);
            }
            sb.append(key).append("-| ").append(value).append("\r\n");
        }
        sb.append("\r\n--requestBody--\r\n").append(requestBody).append("\r\n");
        sb.append("\r\n--responseBody--\r\n").append(responseBody).append("\r\n");
        sb.append("\r\n\r\n\r\n");
        
        saveLog(log, sb.toString());
    }
    
    /**
     * 
     * 保存日志<br />
     * // XXX 这里使用了 logback 的实现.以后会抽象出来,形成可配置的
     *
     * @param saveDirectory 保存目录
     * @param messagelog 日志
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月26日]
     * @author rain
     */
    private void saveLog(T log, String messagelog) {
        final TxLoaclFileServiceLog txLog = (TxLoaclFileServiceLog) log;
        String saveDirectory = getSaveDirectory(log);
        
        TimeBasedRollingPolicy<String> policy = new TimeBasedRollingPolicy<>();
        policy.setFileNamePattern(saveDirectory.concat("/").concat(String.valueOf(txLog.getModule())).concat(".%d{yyyy-MM-dd}.log.zip"));
        policy.setContext(context);
        
        RollingFileAppender<String> appender = new RollingFileAppender<String>();
        policy.setParent(appender);
        appender.setRollingPolicy(policy);
        appender.setContext(context);
        appender.setFile(saveDirectory.concat("/").concat(String.valueOf(txLog.getModule())).concat(".current").concat(".log"));
        appender.setAppend(true);
        appender.setName(txLog.getModule());
        appender.setEncoder(encoder);
        
        policy.start();
        appender.start();
        appender.doAppend(messagelog);
        appender.stop();
    }
    
}
