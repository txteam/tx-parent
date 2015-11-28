/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月25日
 * 项目： tx-component-servicelog
 */
package com.tx.component.servicelog.defaultimpl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import com.tx.component.servicelog.context.BaseServiceLoggerBuilder;
import com.tx.component.servicelog.context.ServiceLoggerSessionContext;
import com.tx.component.servicelog.context.logger.ServiceLogDecorate;
import com.tx.component.servicelog.context.logger.ServiceLogPersister;
import com.tx.component.servicelog.context.logger.ServiceLogQuerier;
import com.tx.component.servicelog.exception.ServiceLoggerException;
import com.tx.component.servicelog.exception.UnsupportServiceLoggerTypeException;
import com.tx.component.servicelog.logger.TXServiceLog;
import com.tx.component.servicelog.logger.TxLoaclFileServiceLog;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.reflection.JpaMetaClass;
import com.tx.core.util.UUIDUtils;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.ContextBase;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.encoder.EchoEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

/**
 * 本地文件日志建造者
 * 
 * @author rain
 * @version [版本号, 2015年11月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TXLocalFileServiceLoggerBuilder extends BaseServiceLoggerBuilder implements InitializingBean {
    
    /** 日志 */
    private Logger logger = LoggerFactory.getLogger(TXLocalFileServiceLoggerBuilder.class);
    
    /** 日志保存目录 */
    private String savepath;
    
    /** 文件格式,默认 TXT */
    private TXLocalFileDataFormat dataformat;
    
    private Map<Class<?>, String> log2savepath = new HashMap<Class<?>, String>();
    
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(savepath, "log's save path is empty!");
        
        if (dataformat == null) {
            dataformat = TXLocalFileDataFormat.TXT;
        }
    }
    
    @Override
    public <T> boolean isSupport(Class<T> logObjectType) {
        if (ClassUtils.isAssignable(logObjectType, TxLoaclFileServiceLog.class)) {
            return true;
        }
        return false;
    }
    
    @Override
    public int order() {
        return -1000;
    }
    
    @Override
    protected <T> ServiceLogDecorate<T> buildServiceLogDecorate(Class<T> logObjectType) {
        return new ServiceLogDecorate<T>() {
            @Override
            public T decorate(T srcObj) {
                AssertUtils.notNull(srcObj, "srcObj is null");
                
                if (srcObj instanceof TXServiceLog) {
                    TxLoaclFileServiceLog other = (TxLoaclFileServiceLog) srcObj;
                    
                    ServiceLoggerSessionContext context = ServiceLoggerSessionContext.getContext();
                    other.setId(UUIDUtils.generateUUID());
                    other.setClientIpAddress((String) context.getAttribute("clientIpAddress"));
                    other.setOperatorId((String) context.getAttribute("operatorId"));
                    other.setOrganizationId((String) context.getAttribute("organizationId"));
                    other.setVcid((String) context.getAttribute("vcid"));
                    other.setCreateDate(new Date());
                    other.setOperatorName((String) context.getAttribute("operatorName"));
                    other.setOperatorLoginName((String) context.getAttribute("operatorLoginName"));
                    
                    if (StringUtils.isEmpty(other.getMessageid())) {
                        other.setMessageid(UUIDUtils.generateUUID());
                    }
                } else {
                    throw new UnsupportServiceLoggerTypeException("srcObject:{} not support.", srcObj.toString());
                }
                
                return srcObj;
            }
        };
    }
    
    @Override
    protected <T> ServiceLogPersister<T> buildServiceLogPersister(Class<T> logObjectType) {
        return new ServiceLogPersister<T>() {
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
                Class<?> clazz = log.getClass();
                String string = log2savepath.get(clazz);
                if (StringUtils.isEmpty(string)) {
                    TxLoaclFileServiceLog txLog = (TxLoaclFileServiceLog) log;
                    String module = txLog.getModule();
                    
                    StringBuilder path = new StringBuilder(128);
                    path.append(savepath).append('/');
                    path.append(StringUtils.isEmpty(module) ? "null" : module).append('/');
                    string = path.toString();
                    log2savepath.put(clazz, string);
                }
                return string;
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
                Object requestBody = null;
                Object responseBody = null;
                Map<String, Object> values = new HashMap<String, Object>();
                JpaMetaClass<?> jpaMetaClass = JpaMetaClass.forClass(log.getClass());
                Set<String> getterNames = jpaMetaClass.getGetterNames();
                for (String getterMethod : getterNames) {
                    try {
                        Object invokeMethod = MethodUtils.invokeMethod(log, "get" + StringUtils.capitalize(getterMethod));
                        if ("requestBody".equals(getterMethod)) {
                            requestBody = invokeMethod;
                        } else if ("responseBody".equals(getterMethod)) {
                            responseBody = invokeMethod;
                        } else {
                            values.put(getterMethod, invokeMethod);
                        }
                    } catch (Exception e) {
                        logger.warn("日志数据读取异常 : " + e.getMessage(), e);
                    }
                }
                
                // 保存
                StringBuilder sb = new StringBuilder(1024);
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
                Context context = new ContextBase();
                TimeBasedRollingPolicy<String> policy = new TimeBasedRollingPolicy<>();
                policy.setFileNamePattern(saveDirectory.concat("/").concat(String.valueOf(txLog.getModule())).concat(".%d{yyyy-MM-dd}.log.zip"));
                policy.setContext(context);
                
                EchoEncoder<String> encoder = new EchoEncoder<String>() {
                    @Override
                    public void doEncode(String event) throws IOException {
                        StringBuilder sb = new StringBuilder(event.length() + 512);
                        // 第一部分 : 当前日期 模块名 id
                        sb.append(DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss:SSS")).append(' ');
                        sb.append('[').append(txLog.getModule()).append("] ");
                        sb.append(txLog.getId()).append(CoreConstants.LINE_SEPARATOR);
                        // 第二部分 : 日志内容
                        sb.append(event).append(CoreConstants.LINE_SEPARATOR).append(CoreConstants.LINE_SEPARATOR).append(CoreConstants.LINE_SEPARATOR);
                        outputStream.write(sb.toString().getBytes());
                    }
                }; 
                encoder.setContext(context);
                
                RollingFileAppender<String> appender = new RollingFileAppender<String>();
                policy.setParent(appender);
                appender.setRollingPolicy(policy);
                appender.setContext(context);
                appender.setFile(saveDirectory.concat("/").concat(String.valueOf(txLog.getModule())).concat(".current.").concat(".log"));
                appender.setAppend(true);
                appender.setName(txLog.getModule());
                appender.setEncoder(encoder);
                
                policy.start();
                appender.start();
                appender.doAppend(messagelog);
                appender.stop();
            }
        };
    }
    
    @Override
    protected <T> ServiceLogQuerier<T> buildServiceLogQuerier(Class<T> logObjectType) {
        return new ServiceLogQuerier<T>() {
            @Override
            public PagedList<T> queryPagedList(Map<String, Object> params, int pageIndex, int pageSize) {
                throw new ServiceLoggerException("文件日志不支持查询!");
            }
            
            @Override
            public T find(String id) {
                throw new ServiceLoggerException("文件日志不支持查询!");
            }
        };
    }
    
    /**
     * 本地文件数据格式
     * 
     * @author rain
     * @version [版本号, 2015年11月25日]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     */
    public static enum TXLocalFileDataFormat {
        //        XML, JSON, 
        TXT,
    }
    
    /** @return 返回 savepath */
    public String getSavepath() {
        return savepath;
    }
    
    /** @param 对 savepath 进行赋值 */
    public void setSavepath(String savepath) {
        this.savepath = savepath;
    }
    
    /** @return 返回 dataformat */
    public TXLocalFileDataFormat getDataformat() {
        return dataformat;
    }
    
    /** @param 对 dataformat 进行赋值 */
    public void setDataformat(TXLocalFileDataFormat dataformat) {
        this.dataformat = dataformat;
    }
    
}
