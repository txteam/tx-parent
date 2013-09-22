/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-22
 * <修改描述:>
 */
package com.tx.component.servicelog.context;

import javax.sql.DataSource;

import com.tx.component.servicelog.logger.ServiceLogDecorate;
import com.tx.component.servicelog.logger.ServiceLogQuerier;
import com.tx.component.servicelog.logger.ServiceLogPersister;
import com.tx.core.dbscript.model.DataSourceTypeEnum;

/**
 * 业务日志对象工厂<br/>
 *     用于组装系统中使用到的业务日志数据<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ServiceLoggerContextBuilder {
    
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
    public boolean isSupport(Class<?> srcObjType);
    
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
    public ServiceLogPersister buildServiceLogPersister(Class<?> srcObjType,
            DataSourceTypeEnum dataSourceType, DataSource dataSource);
    
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
    public ServiceLogDecorate buildServiceLogDecorate(Class<?> srcObjType);
    
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
    public <T> ServiceLogQuerier<T> buildServiceLogQuerier(Class<T> srcObjType);
}
