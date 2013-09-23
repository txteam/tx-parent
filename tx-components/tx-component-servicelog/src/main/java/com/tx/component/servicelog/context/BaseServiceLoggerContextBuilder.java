/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-23
 * <修改描述:>
 */
package com.tx.component.servicelog.context;

import javax.sql.DataSource;

import com.tx.component.servicelog.exception.UnsupportServiceLoggerTypeException;
import com.tx.component.servicelog.logger.ServiceLogDecorate;
import com.tx.component.servicelog.logger.ServiceLogPersister;
import com.tx.component.servicelog.logger.ServiceLogQuerier;
import com.tx.component.servicelog.logger.ServiceLoggerContext;
import com.tx.core.dbscript.model.DataSourceTypeEnum;

/**
 * 基础业务日志容器构建器<br/>
 * <功能详细描述>
 * 
 * @author  bradyB
 * @version  [版本号, 2013-9-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class BaseServiceLoggerContextBuilder implements
        ServiceLoggerContextBuilder {
    
    /**
     * @param srcObjType
     * @param dataSourceType
     * @param dataSource
     * @return
     */
    @Override
    public <T> ServiceLoggerContext<T> build(Class<T> srcObjType,
            DataSourceTypeEnum dataSourceType, DataSource dataSource) {
        //查看是否支持对应类型的业务日志容器<br/>
        if (!isSupport(srcObjType)) {
            throw new UnsupportServiceLoggerTypeException(
                    "serviceLog type:{} not support auto record by serviceLogContext.",
                    new Object[] { srcObjType });
        }
        //构建业务日志持久器
        ServiceLogPersister serviceLogPersister = buildServiceLogPersister(srcObjType,
                dataSourceType,
                dataSource);
        //构建业务日志装饰器
        ServiceLogDecorate serviceLogDecorate = buildServiceLogDecorate(srcObjType);
        //构建业务日志查询器
        ServiceLogQuerier<T> serviceLogQuerier = buildServiceLogQuerier(srcObjType);
        
        //构建业务日志容器
        ServiceLoggerContext<T> context = new ServiceLoggerContextImpl<T>(
                serviceLogDecorate, serviceLogQuerier, serviceLogPersister);
        
        return context;
    }
    
    /**
     * 是否支持对应对象的日志记录<br/>
     *<功能详细描述>
     * @param srcObjType
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected abstract boolean isSupport(Class<?> srcObjType);
    
    /**
      * 构建业务日志记录器<br/>  
      *<功能详细描述>
      * @param dataSourceType
      * @param dataSource
      * @return [参数说明]
      * 
      * @return ServiceLogger<?> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract ServiceLogPersister buildServiceLogPersister(
            Class<?> srcObjType, DataSourceTypeEnum dataSourceType,
            DataSource dataSource);
    
    /**
      * 构建业务日志实例装饰器<br/>
      *<功能详细描述>
      * @param srcObjType
      * @return [参数说明]
      * 
      * @return ServiceLogDecorate [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract ServiceLogDecorate buildServiceLogDecorate(
            Class<?> srcObjType);
    
    /**
      * 构建业务日志查询器<br/> 
      *<功能详细描述>
      * @param srcObjType
      * @return [参数说明]
      * 
      * @return ServiceLogQuerier<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract <T> ServiceLogQuerier<T> buildServiceLogQuerier(
            Class<T> srcObjType);
    
}
