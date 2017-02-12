/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月25日
 * 项目： tx-component-servicelog
 */
package com.tx.component.servicelog.defaultimpl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import com.tx.component.servicelog.context.BaseServiceLoggerBuilder;
import com.tx.component.servicelog.context.ServiceLoggerSessionContext;
import com.tx.component.servicelog.context.logger.ServiceLogDecorate;
import com.tx.component.servicelog.context.logger.ServiceLogPersister;
import com.tx.component.servicelog.context.logger.ServiceLogQuerier;
import com.tx.component.servicelog.defaultimpl.persister.TXLocalFileServiceLogPersister;
import com.tx.component.servicelog.exceptions.ServiceLoggerException;
import com.tx.component.servicelog.exceptions.UnsupportedServiceLoggerTypeException;
import com.tx.component.servicelog.logger.TXServiceLog;
import com.tx.component.servicelog.logger.TxLoaclFileServiceLog;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.util.MessageUtils;
import com.tx.core.util.UUIDUtils;

/**
 * 本地文件日志建造者
 * 
 * @author rain
 * @version [版本号, 2015年11月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TXLocalFileServiceLoggerBuilder extends BaseServiceLoggerBuilder
        implements InitializingBean {
    
    /** 日志 */
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(TXLocalFileServiceLoggerBuilder.class);
    
    /** 日志保存目录 */
    private String savepath;
    
    /** 文件格式,默认 TXT */
    private TXLocalFileDataFormat dataformat;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(savepath, "log's save path is empty!");
        
        if (dataformat == null) {
            dataformat = TXLocalFileDataFormat.TXT;
        }
    }
    
    @Override
    public boolean isSupport(Class<?> logObjectType) {
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
    protected <T> ServiceLogDecorate<T> buildServiceLogDecorate(
            Class<T> logObjectType) {
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
                    throw new UnsupportedServiceLoggerTypeException(
                            MessageUtils.format("srcObject:{} not support.",
                                    srcObj.toString()));
                }
                
                return srcObj;
            }
        };
    }
    
    @Override
    protected <T> ServiceLogPersister<T> buildServiceLogPersister(
            Class<T> logObjectType) {
        return new TXLocalFileServiceLogPersister<T>(dataformat, savepath);
    }
    
    @Override
    protected <T> ServiceLogQuerier<T> buildServiceLogQuerier(
            Class<T> logObjectType) {
        return new ServiceLogQuerier<T>() {
            @Override
            public PagedList<T> queryPagedList(Map<String, Object> params,
                    int pageIndex, int pageSize) {
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
