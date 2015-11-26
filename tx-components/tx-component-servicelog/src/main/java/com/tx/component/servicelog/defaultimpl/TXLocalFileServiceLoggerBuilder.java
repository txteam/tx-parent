/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月25日
 * 项目： tx-component-servicelog
 */
package com.tx.component.servicelog.defaultimpl;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.tx.component.servicelog.context.BaseServiceLoggerBuilder;
import com.tx.component.servicelog.context.logger.ServiceLogDecorate;
import com.tx.component.servicelog.context.logger.ServiceLogPersister;
import com.tx.component.servicelog.context.logger.ServiceLogQuerier;
import com.tx.component.servicelog.exception.ServiceLoggerInitContextException;
import com.tx.core.exceptions.util.AssertUtils;

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
    
    /** 文件格式,默认json */
    private TXLocalFileDataFormat dataformat;
    
    @Override
    public <T> boolean isSupport(Class<T> logObjectType) {
        return false;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(savepath, "log's save path is empty!");
        File dir = new File(savepath);
        if (!dir.exists() || !dir.isDirectory()) {
            try {
                dir.mkdirs();
            } catch (Exception e) {
                logger.error("创建保存目录失败 : " + e.getMessage(), e);
                throw new ServiceLoggerInitContextException("创建保存目录失败 : " + e.getMessage(), e);
            }
        }
        
        if (dataformat == null) {
            dataformat = TXLocalFileDataFormat.JSON;
        }
    }
    
    @Override
    protected <T> ServiceLogDecorate<T> buildServiceLogDecorate(Class<T> logObjectType) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    protected ServiceLogPersister buildServiceLogPersister(Class<?> logObjectType) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    protected <T> ServiceLogQuerier<T> buildServiceLogQuerier(Class<T> logObjectType) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
